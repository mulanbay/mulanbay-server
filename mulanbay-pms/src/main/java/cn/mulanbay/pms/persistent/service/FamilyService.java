package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.Family;
import cn.mulanbay.pms.persistent.domain.FamilyUser;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.enums.FamilyUserStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 家庭service
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class FamilyService extends BaseHibernateDao {

    /**
     * 获取身份为管理员的家庭用户
     *
     * @param userId
     * @return
     */
    public FamilyUser getAdminFamilyUser(Long userId) {
        try {
            String hql = "from FamilyUser where admin=1 and userId=?0 and status=?1 ";
            FamilyUser fu = (FamilyUser) this.getEntityForOne(hql, userId, FamilyUserStatus.PASSED);
            return fu;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取身份为管理员的家庭用户异常", e);
        }
    }

    /**
     * 获取身家庭用户（一个人只能在一个家庭里）
     *
     * @param userId
     * @return
     */
    public FamilyUser getFamilyUser(Long userId) {
        try {
            String hql = "from FamilyUser where userId=?0 ";
            FamilyUser fu = (FamilyUser) this.getEntityForOne(hql, userId);
            return fu;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取身家庭用户异常", e);
        }
    }

    /**
     * 解散
     *
     * @param familyId
     * @return
     */
    public void dismissFamily(Long familyId) {
        try {
            //删除成员
            String hql = "delete from FamilyUser where familyId=?0 ";
            this.updateEntities(hql, familyId);
            //删除家庭
            String hql2 = "delete from Family where id=?0 ";
            this.updateEntities(hql2, familyId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "解散家庭异常", e);
        }
    }

    /**
     * 新增家庭
     *
     * @param family
     * @return
     */
    public void createFamily(Family family) {
        try {
            family.setCreatedTime(new Date());
            this.saveEntity(family);
            //创建默认家庭成员
            FamilyUser fu = new FamilyUser();
            fu.setAdmin(true);
            User user = (User) this.getEntityById(User.class, family.getUserId());
            fu.setAliasName(user.getNickname());
            fu.setCreatedTime(new Date());
            fu.setFamilyId(family.getId());
            fu.setStatus(FamilyUserStatus.PASSED);
            fu.setRemark("家庭创建时自动创建");
            fu.setUserId(family.getUserId());
            this.saveEntity(fu);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_ADD_ERROR,
                    "新增家庭异常", e);
        }
    }

    /**
     * 获取家庭用户列表
     *
     * @param familyId
     * @param status
     * @return
     */
    public List<FamilyUser> getFamilyUserList(Long familyId, FamilyUserStatus status) {
        try {
            String hql = "from FamilyUser where familyId=?0 ";
            if (status != null) {
                hql += "and status=" + status.ordinal();
            }
            return this.getEntityListNoPageHQL(hql, familyId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取家庭用户列表异常", e);
        }
    }

    /**
     * 获取家庭用户列表
     *
     * @param familyId
     * @return
     */
    public List<FamilyUser> getFamilyUserList(Long familyId) {
        try {
            String hql = "from FamilyUser where familyId=?0 ";
            return this.getEntityListNoPageHQL(hql, familyId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取家庭用户列表异常", e);
        }
    }
}
