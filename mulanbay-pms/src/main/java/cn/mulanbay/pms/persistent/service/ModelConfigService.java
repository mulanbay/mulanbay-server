package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.ai.ml.dataset.ModelHandle;
import cn.mulanbay.ai.ml.dataset.bean.ModelFile;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.ModelConfig;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class ModelConfigService extends BaseHibernateDao implements ModelHandle {

    /**
     * 发布模型
     * @param bean
     */
    public void publish(ModelConfig bean){
        try {
            //把其他的都设置为无效
            //todo 后期修改为同时支持多种算法类型，那么前端需要传入算法类型
            String hql = "update ModelConfig set status = ?0 where code=?1 ";
            this.updateEntities(hql, CommonStatus.DISABLE,bean.getCode());

            bean.setStatus(CommonStatus.ENABLE);
            bean.setLastModifyTime(new Date());
            this.updateEntity(bean);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "发布模型异常", e);
        }
    }

    /**
     * 查询有效的模型文件
     * @param code
     * @return
     */
    @Override
    public ModelFile getModelFile(String code) {
        try {
            String hql = "from ModelConfig where status = ?0  and code=?1  ";
            //只查第一条
            ModelConfig mc = (ModelConfig) this.getEntityForOne(hql,CommonStatus.ENABLE,code);
            if(mc==null){
                return null;
            }else{
                ModelFile mf = new ModelFile();
                BeanCopy.copyProperties(mc,mf);
                return mf;
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "查询有效的模型文件异常", e);
        }
    }
}
