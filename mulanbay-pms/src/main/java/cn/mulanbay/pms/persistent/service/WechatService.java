package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.UserWxpayInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WechatService extends BaseHibernateDao {

    /**
     * 获取用户微信支付信息
     *
     * @param userId
     * @return
     */
    public UserWxpayInfo getUserWxpayInfo(Long userId, String appId) {
        try {
            String hql = "from UserWxpayInfo where userId = ?0 and appId=?1 ";
            return (UserWxpayInfo) this.getEntityForOne(hql, userId, appId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取用户微信支付信息异常", e);
        }
    }

    /**
     * 获取用户微信支付信息
     *
     * @return
     */
    public UserWxpayInfo getUserWxpayInfo(String appId, String openId) {
        try {
            String hql = "from UserWxpayInfo where appId = ?0 and openId=?1 ";
            return (UserWxpayInfo) this.getEntityForOne(hql, appId, openId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取用户微信支付信息异常", e);
        }
    }

    @Cacheable(value = "UserCache", key = "('pms:userWxpayInfo:').concat(#userId).concat(':').concat(#appId)")
    public UserWxpayInfo getUserWxpayInfoForCache(Long userId, String appId) {
        return this.getUserWxpayInfo(userId, appId);
    }

    /**
     * 保存微信信息
     *
     * @param uw
     */
    public void saveOrUpdateUserWxpayInfo(UserWxpayInfo uw) {
        try {
            if (uw.getId() == null) {
                this.saveEntity(uw);
            } else {
                this.updateEntity(uw);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "保存微信信息异常", e);
        }
    }
}
