package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.UserMessage;
import cn.mulanbay.pms.persistent.enums.MessageSendStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserRemindMessageService extends BaseHibernateDao {


    /**
     * 获取需要发送的消息列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<UserMessage> getNeedSendMessage(int page, int pageSize, int maxFail, Date compareDate) {
        try {
            String hql = "from UserMessage where (sendStatus=?0 or (sendStatus=?1 and failCount<?2)) ";
            List args = new ArrayList();
            args.add(MessageSendStatus.UN_SEND);
            args.add(MessageSendStatus.SEND_FAIL);
            args.add(maxFail);
            if (compareDate != null) {
                hql += "and expectSendTime<=?3";
                args.add(compareDate);
            }
            return this.getEntityListHQL(hql, page, pageSize, args.toArray());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取需要发送的消息列表异常", e);
        }
    }

}
