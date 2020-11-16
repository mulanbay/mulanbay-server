package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.handler.DietHandler;
import cn.mulanbay.pms.handler.PmsNotifyHandler;
import cn.mulanbay.pms.persistent.domain.DietVarietyLog;
import cn.mulanbay.pms.persistent.enums.DietType;
import cn.mulanbay.pms.persistent.service.DietService;
import cn.mulanbay.pms.web.bean.request.diet.DietVarietySearch;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 饮食多样性统计
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class DietVarietyStatJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(DietVarietyStatJob.class);

    private DietVarietyStatJobPara para;

    private DietService dietService;

    private BaseService baseService;

    private DietHandler dietHandler;

    private PmsNotifyHandler pmsNotifyHandler;

    @Override
    public TaskResult doTask() {
        TaskResult tr = new TaskResult();
        dietService = BeanFactoryUtil.getBean(DietService.class);
        baseService = BeanFactoryUtil.getBean(BaseService.class);
        dietHandler = BeanFactoryUtil.getBean(DietHandler.class);
        pmsNotifyHandler = BeanFactoryUtil.getBean(PmsNotifyHandler.class);
        Date bussDay = this.getBussDay();
        Date endDate = DateUtil.getTodayTillMiddleNightDate(bussDay);
        Date startDate = DateUtil.getDate(0 - para.getDays(), bussDay);
        List<Long> userIds = dietService.getUserIdList(startDate, endDate);
        if (StringUtil.isEmpty(userIds)) {
            tr.setExecuteResult(JobExecuteResult.SKIP);
            tr.setComment("没有需要统计的用户");
        } else {
            for (Long userId : userIds) {
                stat(startDate, endDate, userId, DietType.BREAKFAST);
                stat(startDate, endDate, userId, DietType.LUNCH);
                stat(startDate, endDate, userId, DietType.DINNER);
                stat(startDate, endDate, userId, null);
            }
            tr.setExecuteResult(JobExecuteResult.SUCCESS);
            tr.setComment("统计了" + userIds.size() + "个用户");
        }
        return tr;
    }

    private void stat(Date startDate, Date endDate, Long userId, DietType dietType) {
        try {
            DietVarietySearch sf = new DietVarietySearch();
            sf.setUserId(userId);
            sf.setStartDate(startDate);
            sf.setEndDate(endDate);
            if (dietType != null) {
                sf.setDietType((short) dietType.ordinal());
            }
            sf.setOrderByField(para.getOrderByField());
            float v = dietHandler.getFoodsAvgSimilarity(sf);
            //发送消息
            String sv = PriceUtil.changeToString(0, v * 100);
            String dietTypeName = dietType == null ? "" : dietType.getName();
            String title = dietTypeName + "多样性统计";
            String ds = DateUtil.getFormatDate(startDate, DateUtil.FormatDay1) + "~" + DateUtil.getFormatDate(endDate, DateUtil.FormatDay1);
            String content = ds + dietTypeName + "的重复度为:" + sv + "%";
            pmsNotifyHandler.addNotifyMessage(PmsErrorCode.DIET_VARIETY_STAT, title, content,
                    userId, null);
            //加入统计历史
            addStatLog(startDate, endDate, userId, dietType, v);
        } catch (Exception e) {
            logger.error("饮食多样性统计异常", e);
        }
    }

    private void addStatLog(Date startDate, Date endDate, Long userId, DietType dietType, float v) {
        try {
            DietVarietyLog log = new DietVarietyLog();
            log.setUserId(userId);
            log.setDietType(dietType);
            log.setStartDate(startDate);
            log.setEndDate(endDate);
            log.setVariety((double) v);
            log.setCreatedTime(new Date());
            log.setRemark("调度统计自动生成");
            baseService.saveObject(log);
        } catch (Exception e) {
            logger.error("增加饮食多样性统计日志异常", e);
        }
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        ParaCheckResult pcr = new ParaCheckResult();
        para = this.getTriggerParaBean();
        return pcr;
    }

    @Override
    public Class getParaDefine() {
        return DietVarietyStatJobPara.class;
    }
}
