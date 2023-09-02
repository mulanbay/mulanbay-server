package cn.mulanbay.pms.handler;

import cn.mulanbay.ai.ml.processor.DietPriceEvaluateMProcessor;
import cn.mulanbay.ai.nlp.processor.NLPProcessor;
import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.persistent.dto.DietPriceAnalyse2Stat;
import cn.mulanbay.pms.persistent.enums.DietSource;
import cn.mulanbay.pms.persistent.enums.PeriodType;
import cn.mulanbay.pms.persistent.service.DietService;
import cn.mulanbay.pms.web.bean.request.diet.DietPriceAnalyseSearch;
import cn.mulanbay.pms.web.bean.request.diet.DietVarietySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 饮食处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class DietHandler extends BaseHandler {

    @Autowired
    DietService dietService;

    @Autowired
    NLPProcessor nlpProcessor;

    @Autowired
    DietPriceEvaluateMProcessor dietPriceEvaluateMProcessor;

    public DietHandler() {
        super("饮食处理器");
    }

    /**
     * 获取食物平均相似度
     *
     * @param sf
     * @return
     */
    public float getFoodsAvgSimilarity(DietVarietySearch sf) {
        List<String> foodsList = this.getFoodsList(sf);
        return nlpProcessor.avgSentenceSimilarity(foodsList);
    }

    private List<String> getFoodsList(DietVarietySearch sf) {
        if (sf.getDietType() != null) {
            return dietService.getFoodsList(sf);
        } else {
            List<Object[]> list = dietService.getFoodsListWithAllDietType(sf);
            List<String> res = new ArrayList<>();
            for (Object[] ss : list) {
                String foods = "";
                for (Object s : ss) {
                    foods += s.toString() + ",";
                }
                res.add(foods);
            }
            return res;
        }
    }

    /**
     * 预测消费价格
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    public double predictPrice(Long userId,int month, Date startDate, Date endDate, PeriodType period){
        DietPriceAnalyseSearch sf = new DietPriceAnalyseSearch();
        sf.setUserId(userId);
        sf.setStartDate(startDate);
        sf.setEndDate(DateUtil.getTodayTillMiddleNightDate(endDate));
        sf.setDateGroupTypeStr("DIET_SOURCE");
        List<DietPriceAnalyse2Stat> list = dietService.statDietPriceAnalyseByType(sf);
        //计算总价
        BigDecimal totalPrice = new BigDecimal(0);
        for(DietPriceAnalyse2Stat dp:list){
            totalPrice = totalPrice.add(dp.getTotalPrice());
        }
        double smRate =0;
        double rtRate=0;
        double toRate=0;
        double mkRate=0;
        double otRate=0;
        for(DietPriceAnalyse2Stat dp:list){
            String name = dp.getName();
            double rate = (dp.getTotalPrice().divide(totalPrice,BigDecimal.ROUND_HALF_UP)).doubleValue();
            if(name.equals(DietSource.SELF_MADE.getName())){
                smRate = rate;
            }else if(name.equals(DietSource.RESTAURANT.getName())){
                rtRate = rate;
            }else if(name.equals(DietSource.TAKE_OUT.getName())){
                toRate = rate;
            }else if(name.equals(DietSource.MARKET.getName())){
                mkRate = rate;
            }else if(name.equals(DietSource.OTHER.getName())){
                otRate = rate;
            }
        }
        if(period==PeriodType.MONTHLY){
            return dietPriceEvaluateMProcessor.evaluate(month,smRate,rtRate,toRate,mkRate,otRate);
        }
        return 0;
    }

}
