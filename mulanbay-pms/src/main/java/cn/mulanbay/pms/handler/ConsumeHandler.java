package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.queue.LimitQueue;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.handler.bean.BuyRecordMatchBean;
import cn.mulanbay.pms.handler.bean.GoodsLifetimeMatchBean;
import cn.mulanbay.pms.handler.qa.AhaNLPHandler;
import cn.mulanbay.pms.persistent.domain.BuyRecord;
import cn.mulanbay.pms.persistent.domain.GoodsLifetime;
import cn.mulanbay.pms.persistent.domain.GoodsType;
import cn.mulanbay.pms.persistent.service.BuyRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费处理类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class ConsumeHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeHandler.class);

    @Value("${buyRecord.cacheQueue.expireDays}")
    int expireDays;

    @Value("${buyRecord.cacheQueue.size}")
    int queueSize;

    @Value("${buyRecord.similarity.maxMatchDegree}")
    float maxMatchDegree;

    @Value("${buyRecord.similarity.minMatchDegree}")
    float minMatchDegree;

    @Value("${goodsType.matchList.expireTime}")
    int goodsTypeExpireTime;

    @Autowired
    BaseService baseService;

    @Autowired
    BuyRecordService buyRecordService;

    @Autowired
    CacheHandler cacheHandler;

    @Autowired
    AhaNLPHandler ahaNLPHandler;

    public ConsumeHandler() {
        super("消费处理类");
    }

    /**
     * 加入到缓存中
     * @param b
     */
    public void addToCache(BuyRecord b){
        try {
            String key = CacheKey.getKey(CacheKey.BUY_RECORD_CACHE_QUEUE,b.getUserId().toString());
            LimitQueue<BuyRecord> queue = cacheHandler.get(key, LimitQueue.class);
            if (queue == null) {
                queue = new LimitQueue<BuyRecord>(queueSize);
            }
            queue.offer(b);
            cacheHandler.set(key, queue, expireDays*24*3600);
        } catch (Exception e) {
            logger.error("添加消费记录到缓存中异常",e);
        }
    }

    /**
     * 寻找匹配
     * 该方法针对已经与第三方系统的数据同步比较有意义
     * @param userId
     * @param goodsName
     * @return
     */
    public BuyRecordMatchBean match(Long userId, String goodsName){
        //先从缓存中比对，看以前是否已经保存过类似的消费
        BuyRecordMatchBean bean = this.matchInCache(userId,goodsName);
        bean.setGoodsName(goodsName);
        if(bean.getMatch()>=maxMatchDegree){
            return bean;
        }
        //没有或者匹配度很低，再从商品类别中比对
        List<BuyRecordMatchBean> list  = this.getGoodsTypeList(userId);
        for(BuyRecordMatchBean br : list){
            String name = br.getGoodsTypeName();
            if(br.getSubGoodsTypeName()!=null){
                name+=","+br.getSubGoodsTypeName();
            }
            float m = ahaNLPHandler.sentenceSimilarity(goodsName,name);
            if(m>bean.getMatch()){
                bean.setGoodsTypeId(br.getGoodsTypeId());
                bean.setSubGoodsTypeId(br.getSubGoodsTypeId());
                bean.setCompareId(null);
                bean.setMatch(m);
                if(m>=maxMatchDegree){
                    return bean;
                }
            }
        }
        if(bean.getMatch()<minMatchDegree){
            return null;
        }
        return bean;
    }

    /**
     * 通过缓存中的匹配
     * @param userId
     * @param goodsName
     * @return
     */
    private BuyRecordMatchBean matchInCache(Long userId, String goodsName){
        BuyRecordMatchBean bean = new BuyRecordMatchBean();
        String key = CacheKey.getKey(CacheKey.BUY_RECORD_CACHE_QUEUE,userId.toString());
        LimitQueue<BuyRecord> queue = cacheHandler.get(key, LimitQueue.class);
        if (queue == null) {
            return bean;
        }else{
            for(BuyRecord br : queue.getList()){
                float m = ahaNLPHandler.sentenceSimilarity(goodsName,br.getGoodsName());
                if(m>bean.getMatch()){
                    bean.setGoodsTypeId(br.getGoodsType().getId());
                    if(br.getSubGoodsType()!=null){
                        bean.setSubGoodsTypeId(br.getSubGoodsType().getId());
                    }
                    bean.setCompareId(br.getId());
                    bean.setShopName(br.getShopName());
                    bean.setBrand(br.getBrand());
                    bean.setMatch(m);
                    if(m>=maxMatchDegree){
                        logger.debug("在历史消费记录中匹配到，goodsName:"+br.getGoodsName());
                        return bean;
                    }
                }
            }
        }
        return bean;
    }

    /**
     * 获取商品类型的匹配列表
     * @param userId
     * @return
     */
    private List<BuyRecordMatchBean> getGoodsTypeList(Long userId){
        String key = CacheKey.getKey(CacheKey.GOODS_TYPE_MATCH_LIST,userId.toString());
        List<BuyRecordMatchBean> list = cacheHandler.get(key,List.class);
        if(StringUtil.isEmpty(list)){
            list = new ArrayList<>();
            List<GoodsType> goodsTypeList = buyRecordService.getGoodsTypeList(userId);
            for(GoodsType gt : goodsTypeList){
                if(0==gt.getParentId()){
                    //寻找子类
                    int cn=0;
                    for(GoodsType child : goodsTypeList){
                        if(gt.getId().intValue()==child.getParentId().intValue()){
                            cn++;
                            BuyRecordMatchBean mb = new BuyRecordMatchBean();
                            mb.setGoodsTypeId(gt.getId());
                            mb.setGoodsTypeName(gt.getName());
                            mb.setSubGoodsTypeId(child.getId());
                            mb.setSubGoodsTypeName(child.getName());
                            list.add(mb);
                        }
                    }
                    if(cn==0){
                        //没有子类
                        BuyRecordMatchBean mb = new BuyRecordMatchBean();
                        mb.setGoodsTypeId(gt.getId());
                        mb.setGoodsTypeName(gt.getName());
                        list.add(mb);
                    }
                }
            }
        }
        cacheHandler.set(key, list, goodsTypeExpireTime);
        return list;
    }

    /**
     * 商品寿命匹配
     * @param goodsName
     * @return
     */
    public GoodsLifetimeMatchBean matchLifetime(String goodsName){
        GoodsLifetimeMatchBean bean = new GoodsLifetimeMatchBean();
        List<GoodsLifetime> list = this.getGoodsLifetimeList();
        for(GoodsLifetime lt : list){
            float m = ahaNLPHandler.sentenceSimilarity(goodsName,lt.getKeywords());
            if(m>bean.getMatch()){
                BeanCopy.copyProperties(lt,bean);
                bean.setMatch(m);
                if(m>=maxMatchDegree){
                    logger.debug("快速寻找到商品寿命匹配:"+lt.getName());
                    return bean;
                }
            }
        }
        if(bean.getMatch()<minMatchDegree){
            logger.warn("商品寿命匹配度过低:"+bean.getMatch());
            return null;
        }
        return bean;
    }

    /**
     * 获取商品寿命配置列表
     * @return
     */
    private List<GoodsLifetime> getGoodsLifetimeList(){
        String key = CacheKey.getKey(CacheKey.GOODS_LIFETIME_LIST);
        List<GoodsLifetime> list = cacheHandler.get(key,List.class);
        if(StringUtil.isEmpty(list)){
            //从数据库加载
            list = baseService.getBeanList(GoodsLifetime.class, PageRequest.NO_PAGE,0,null);
            if(list.size()>0){
                cacheHandler.set(key, list, goodsTypeExpireTime);
            }
        }
        return list;
    }
}
