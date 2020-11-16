package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.CommonRecordType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class CommonRecordService extends BaseHibernateDao {

    /**
     * 删除通用记录类型
     *
     * @param commonRecordType
     */
    public void deleteCommonRecordType(CommonRecordType commonRecordType) {
        try {
            //删除曲子
            String hql = "delete from CommonRecord where commonRecordType.id=?0";
            this.updateEntities(hql, commonRecordType.getId());
            //删除记录
            this.removeEntity(commonRecordType);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "删除通用记录类型异常", e);
        }
    }
}
