package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.pms.web.bean.request.schedule.TaskTriggerCategorySearch;
import cn.mulanbay.schedule.domain.TaskLog;
import cn.mulanbay.schedule.domain.TaskServer;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.enums.TriggerStatus;
import cn.mulanbay.schedule.impl.HibernatePersistentProcessor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 调度
 * 需要全部复写父类的方法，否则会报：Could not obtain transaction-synchronized Session for current thread
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class PmsScheduleService extends HibernatePersistentProcessor {

    @Override
    public List<TaskTrigger> getActiveTaskTrigger(String deployId, boolean supportDistri) {
        return super.getActiveTaskTrigger(deployId, supportDistri);
    }

    @Override
    public void updateTaskTriggerForNewJob(TaskTrigger taskTrigger) {
        super.updateTaskTriggerForNewJob(taskTrigger);
    }

    @Override
    public void saveTaskLog(TaskLog taskLog) {
        super.saveTaskLog(taskLog);
    }

    @Override
    public TaskLog selectTaskLog(Long logId) {
        return super.selectTaskLog(logId);
    }

    @Override
    public TaskTrigger selectTaskTrigger(Long triggerId) {
        return super.selectTaskTrigger(triggerId);
    }

    @Override
    public void updateTaskLog(TaskLog taskLog) {
        super.updateTaskLog(taskLog);
    }

    @Override
    public void updateTaskTriggerForRedoJob(TaskTrigger taskTrigger) {
        super.updateTaskTriggerForRedoJob(taskTrigger);
    }

    @Override
    public void updateTaskTriggerStatus(Long triggerId, TriggerStatus status) {
        super.updateTaskTriggerStatus(triggerId, status);
    }

    @Override
    public boolean isTaskLogExit(Long taskTriggerId, Date bussDate) {
        return super.isTaskLogExit(taskTriggerId, bussDate);
    }

    @Override
    public boolean isTaskLogExit(String scheduleIdentityId) {
        return super.isTaskLogExit(scheduleIdentityId);
    }

    @Override
    public List<TaskLog> getAutoRedoTaskLogs(String deployId, boolean supportDistri, Date startDate, Date endDate) {
        return super.getAutoRedoTaskLogs(deployId, supportDistri, startDate, endDate);
    }

    /**
     * 重置调度总执行次数
     *
     * @param id
     */
    public void resetTaskTriggerTotalCount(Long id) {
        try {
            TaskTrigger tt = this.selectTaskTrigger(id);
            tt.setTotalCount(0L);
            this.updateEntity(tt);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "重置调度总执行次数异常", e);
        }
    }

    /**
     * 重置调度总失败次数
     *
     * @param id
     */
    public void resetTaskTriggerFailCount(Long id) {
        try {
            TaskTrigger tt = this.selectTaskTrigger(id);
            tt.setFailCount(0L);
            this.updateEntity(tt);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_UPDATE_ERROR,
                    "重置调度总失败次数异常", e);
        }
    }

    /**
     * 更新调度参数
     *
     * @param id
     * @param triggerParas
     */
    public void updateTaskTriggerPara(Long id, String triggerParas) {
        try {
            TaskTrigger tt = this.selectTaskTrigger(id);
            tt.setTriggerParas(triggerParas);
            tt.setModifyTime(new Date());
            this.updateEntity(tt);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "更新调度参数异常", e);
        }
    }

    /**
     * 获取最近一次的调度日志
     *
     * @param taskTriggerId
     */
    public TaskLog getLastTaskLog(Long taskTriggerId) {
        try {
            String hql = "from TaskLog where taskTrigger.id=?0 order by startTime desc";
            return (TaskLog) this.getEntityForOne(hql, taskTriggerId);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取最近一次的调度日志异常", e);
        }
    }

    /**
     * 获取分类列表，统计聚合
     *
     * @return
     */
    public List<String> getTaskTriggerCategoryList(TaskTriggerCategorySearch sf) {
        try {
            PageRequest pr = sf.buildQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("select distinct ");
            sb.append(sf.getGroupField());
            sb.append(" from TaskTrigger ");
            sb.append(pr.getParameterString());
            sb.append(" order by ");
            sb.append(sf.getGroupField());
            return this.getEntityListHQL(sb.toString(), 0, 0, pr.getParameterValue());
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取分类列表异常", e);
        }
    }

    /**
     * 采用override是解决hibernate的事务问题
     * 异常：Could not obtain transaction-synchronized Session for current thread
     *
     * @param taskServer
     */
    @Override
    public void updateTaskServer(TaskServer taskServer) {
        super.updateTaskServer(taskServer);
    }

    @Override
    public TaskServer selectTaskServer(String deployId) {
        return super.selectTaskServer(deployId);
    }

    /**
     * 最近的调度(24小时内)
     *
     * @return
     */
    public List<TaskTrigger> getRecentSchedules() {
        try {
            Date now = new Date();
            Date end = new Date(now.getTime()+24*3600*1000L);
            String hql ="from TaskTrigger where nextExecuteTime>=?0 and nextExecuteTime<=?1 and triggerStatus=?2 and triggerType in (4,5,6,7,8) order by nextExecuteTime ";
            return this.getEntityListNoPageHQL(hql,now,end,TriggerStatus.ENABLE);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "最近的调度异常", e);
        }
    }
}
