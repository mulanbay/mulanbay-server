package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 数据字典
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class DictService extends BaseHibernateDao {

    /**
     * 删除数据字典组数据
     * @param id
     */
    public void deleteDictGroup(Long id) {
        try {
            String hql = "delete from DictItem where group.id = ?0 ";
            this.updateEntities(hql,id);
            String hql2 = "delete from DictGroup where id = ?0 ";
            this.updateEntities(hql2,id);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "删除数据字典组数据异常", e);
        }
    }

}
