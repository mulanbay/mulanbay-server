package cn.mulanbay.pms.web.controller;

import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.UserReportConfig;
import cn.mulanbay.pms.persistent.dto.ReportResult;
import cn.mulanbay.pms.persistent.service.ReportService;
import cn.mulanbay.pms.web.bean.request.report.ReportStatSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表统计
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/reportStat")
public class ReportStatController extends BaseController {

    @Autowired
    ReportService reportService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(ReportStatSearch sf) {
        PageRequest pr = sf.buildQuery();
        Sort s = new Sort("orderIndex", Sort.ASC);
        pr.addSort(s);
        pr.setBeanClass(UserReportConfig.class);
        PageResult<UserReportConfig> unResult = baseService.getBeanResult(pr);
        List<ReportResult> list = new ArrayList<>();
        int i = 1;
        for (UserReportConfig rc : unResult.getBeanList()) {
            ReportResult nr = reportService.getReportResult(rc, sf.getUserId(), sf.getYear(), i);
            list.add(nr);
            i++;
        }
        PageResult<ReportResult> res = new PageResult<>(sf.getPage(), sf.getPageSize());
        res.setBeanList(list);
        res.setMaxRow(unResult.getMaxRow());
        return callbackDataGrid(res);
    }
}
