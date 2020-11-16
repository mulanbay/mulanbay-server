package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.persistent.domain.UserReportConfig;
import cn.mulanbay.pms.persistent.dto.ReportResult;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.ReportService;
import cn.mulanbay.pms.web.bean.request.report.UserReportConfigSearch;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;

import java.util.Date;
import java.util.List;

/**
 * 统计用户的年度报表
 * 每年初统计上一年的报表
 * <p>
 * 目前没有实现
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class UserReportStatJob extends AbstractBaseJob {

    BaseService baseService = null;

    ReportService reportService = null;

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        baseService = BeanFactoryUtil.getBean(BaseService.class);
        reportService = BeanFactoryUtil.getBean(ReportService.class);
        UserReportConfigSearch sf = new UserReportConfigSearch();
        sf.setStatus(CommonStatus.ENABLE);
        sf.setPage(-1);
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(UserReportConfig.class);
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        PageResult<UserReportConfig> qr = baseService.getBeanResult(pr);
        List<UserReportConfig> list = qr.getBeanList();
        if (list.isEmpty()) {
            result.setComment("可用用户计划为空");
        } else {
            Date date = this.getBussDay();
            int year = DateUtil.getYear(date);
            int success = 0;
            int fail = 0;
            for (UserReportConfig urc : list) {
                boolean b = statUserReport(urc, year);
                if (b) {
                    success++;
                } else {
                    fail++;
                }
            }
            result.setExecuteResult(JobExecuteResult.SUCCESS);
            result.setComment("一共统计了" + list.size() + "个用户报表,成功:" + success + "个,失败" + fail + "个");
        }
        return result;
    }

    private boolean statUserReport(UserReportConfig urc, int year) {
        ReportResult nr = reportService.getReportResult(urc, urc.getUserId(), year, 0);
        // todo 写报表数据
        //发送用户消息

        return true;
    }

    @Override
    public ParaCheckResult checkTriggerPara() {
        return DEFAULT_SUCCESS_PARA_CHECK;
    }

    @Override
    public Class getParaDefine() {
        return null;
    }
}
