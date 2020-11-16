package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.CommandConfig;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommandService extends BaseHibernateDao {

    /**
     * 通过code查询CommandConfig
     *
     * @param code
     * @return
     */
    public CommandConfig getCommandConfigByCode(String code) {
        try {
            String hql = "from CommandConfig where code = ?0 ";
            CommandConfig cc = (CommandConfig) this.getEntityForOne(hql, code);
            return cc;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "通过code查询CommandConfig异常", e);
        }
    }

    /**
     * 通过scode查询CommandConfig
     *
     * @param scode
     * @return
     */
    public CommandConfig getCommandConfigByScode(String scode) {
        try {
            String hql = "from CommandConfig where scode = ?0 ";
            CommandConfig cc = (CommandConfig) this.getEntityForOne(hql, scode);
            return cc;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "通过scode查询CommandConfig异常", e);
        }
    }

}
