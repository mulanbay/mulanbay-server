package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.Account;
import cn.mulanbay.pms.persistent.domain.AccountFlow;
import cn.mulanbay.pms.persistent.domain.AccountSnapshotInfo;
import cn.mulanbay.pms.persistent.dto.AccountStat;
import cn.mulanbay.pms.persistent.enums.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账户
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Service
@Transactional
public class AccountService extends BaseHibernateDao {

    /**
     * 删除账户
     * @param accountId
     * @param userId
     * @return
     */
    public void deleteAccount(Long accountId,Long userId) {
        try {
            //step1:删除账户流水
            String hql = "delete from AccountFlow where userId=?0 and account.id=?1 ";
            this.updateEntities(hql,userId,accountId);

            //step2:删除账户
            String hql2 = "delete from Account where userId=?0 and id=?1 ";
            this.updateEntities(hql2,userId,accountId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "删除账户异常", e);
        }
    }

    /**
     * 添加账户
     *
     * @param userId
     */
    public double selectCurrentAccountAmount(Long userId) {
        try {
            String hql = "select sum(amount) from Account where userId=?0 ";
            Object o = this.getEntityForOne(hql, userId);
            if (o == null) {
                return 0.0;
            } else {
                return Double.valueOf(o.toString());
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "添加账户异常", e);
        }
    }

    /**
     * 添加账户
     *
     * @param account
     */
    public void saveAccount(Account account) {
        try {
            this.saveEntity(account);
            //写流水
            AccountFlow af = new AccountFlow();
            af.setAccount(account);
            af.setAdjustType(AccountAdjustType.MANUAL);
            af.setBeforeAmount(account.getAmount());
            af.setAfterAmount(account.getAmount());
            af.setUserId(account.getUserId());
            af.setCreatedTime(new Date());
            af.setName(account.getName());
            af.setCardNo(account.getCardNo());
            af.setType(account.getType());
            af.setStatus(account.getStatus());
            af.setRemark("用户新增账户自动写入");
            this.saveEntity(af);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "添加账户异常", e);
        }
    }

    /**
     * 更新账户
     *
     * @param account
     */
    public void updateAccount(Account account, Double beforeAmount, String remark) {
        try {
            this.updateEntity(account);
            if (!PriceUtil.priceEquals(account.getAmount(), beforeAmount)) {
                //写流水
                AccountFlow af = new AccountFlow();
                af.setAccount(account);
                af.setAdjustType(AccountAdjustType.MANUAL);
                af.setBeforeAmount(beforeAmount);
                af.setAfterAmount(account.getAmount());
                af.setUserId(account.getUserId());
                af.setName(account.getName());
                af.setCardNo(account.getCardNo());
                af.setType(account.getType());
                af.setStatus(account.getStatus());
                af.setCreatedTime(new Date());
                af.setRemark(remark);
                this.saveEntity(af);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "更新用户账户异常", e);
        }
    }

    /**
     * 获取最新账户分析
     *
     * @param userId
     * @return
     */
    public List<AccountStat> statAccount(Long userId, AccountAnalyseType groupType, AccountType type, AccountStatus status, Long snapshotId) {
        try {
            StringBuffer sql = new StringBuffer();
            List args = new ArrayList();
            if (groupType == AccountAnalyseType.NAME) {
                sql.append("select name as name ");
            } else if (groupType == AccountAnalyseType.STATUS) {
                sql.append("select status as id ");
            } else {
                sql.append("select type as id ");
            }
            if (snapshotId == null) {
                //实时分析
                sql.append(",sum(amount) as value from account ");
            } else {
                sql.append(",sum(after_amount) as value from account_flow ");
            }
            sql.append("where user_id=?0 ");
            args.add(userId);
            int index = 1;
            if (type != null) {
                sql.append("and type=?" + (index++) + " ");
                args.add(type.getValue());
            }
            if (status != null) {
                sql.append("and status=?" + (index++) + " ");
                args.add(status.getValue());
            }
            if (snapshotId != null) {
                //实时分析
                sql.append("and snapshot_id=?" + (index++) + " ");
                args.add(snapshotId);
            }
            if (groupType == AccountAnalyseType.NAME) {
                sql.append(" group by name");
            } else if (groupType == AccountAnalyseType.STATUS) {
                sql.append(" group by status");
            } else {
                sql.append(" group by type");
            }
            List<AccountStat> list = this.getEntityListWithClassSQL(sql.toString(), 0, 0,
                    AccountStat.class, args.toArray());
            if (groupType == AccountAnalyseType.TYPE) {
                for (AccountStat aas : list) {
                    //logger.debug(aas.getId().getClass());
                    AccountType it = AccountType.getAccountType(Integer.valueOf(aas.getId().toString()));
                    if (it == null) {
                        aas.setName("未知");
                    } else {
                        aas.setName(it.getName());
                    }
                }
            } else if (groupType == AccountAnalyseType.STATUS) {
                for (AccountStat aas : list) {
                    //logger.debug(aas.getId().getClass());
                    AccountStatus it = AccountStatus.getAccountStatus(Integer.valueOf(aas.getId().toString()));
                    if (it == null) {
                        aas.setName("未知");
                    } else {
                        aas.setName(it.getName());
                    }
                }
            }
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取最新账户分析异常", e);
        }
    }

    /**
     * 生成快照
     * @param name
     * @param bussKey
     * @param remark
     * @param userId
     */
    public void createSnapshot(String name, String bussKey,PeriodType period, String remark, Long userId) {
        try {
            //step 1: 删除旧的
            String hql = "from AccountSnapshotInfo where userId=?0 and bussKey=?1 ";
            AccountSnapshotInfo asi = (AccountSnapshotInfo) this.getEntityForOne(hql,userId,bussKey);
            if(asi==null){
                asi = new AccountSnapshotInfo();
                asi.setBussKey(bussKey);
                asi.setPeriod(period);
                asi.setCreatedTime(new Date());
                asi.setName(name);
                asi.setRemark(remark);
                asi.setUserId(userId);
                this.saveEntity(asi);
            }else{
                this.execSqlUpdate("delete from account_flow where snapshot_id=? ",bussKey);
                asi.setBussKey(bussKey);
                asi.setPeriod(period);
                asi.setLastModifyTime(new Date());
                asi.setName(name);
                asi.setRemark(remark);
                this.updateEntity(asi);
            }

            //step 2: 插入新的
            StringBuffer sb = new StringBuffer();
            sb.append("insert into account_flow(user_id,account_id,name,card_no,type,before_amount,after_amount,adjust_type,snapshot_id,status,remark,created_time) ");
            sb.append("select user_id,id,name,card_no,type,amount,amount,3," + asi.getId() + ",status,'" + remark + "',now()");
            sb.append("from account ");
            if (userId != null) {
                sb.append("where user_id=" + userId);
            }
            this.execSqlUpdate(sb.toString());

        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "生成快照异常", e);
        }
    }

    /**
     * 删除快照
     *
     * @param userId
     */
    public void deleteSnapshot(Long userId, Long id) {
        try {
            String hql = "delete from AccountFlow where snapshotInfo.id=?0 and userId=?1";
            this.updateEntities(hql, id, userId);
            String hql2 = "delete from AccountSnapshotInfo where id=?0 and userId=?1";
            this.updateEntities(hql2, id, userId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "删除快照异常", e);
        }
    }
}
