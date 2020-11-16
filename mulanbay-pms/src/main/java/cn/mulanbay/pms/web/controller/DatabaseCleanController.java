package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.common.util.NumberUtil;
import cn.mulanbay.persistent.query.PageRequest;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.persistent.query.Sort;
import cn.mulanbay.pms.persistent.domain.DatabaseClean;
import cn.mulanbay.pms.persistent.service.DatabaseCleanService;
import cn.mulanbay.pms.web.bean.request.CommonBeanDeleteRequest;
import cn.mulanbay.pms.web.bean.request.data.DatabaseCleanFormRequest;
import cn.mulanbay.pms.web.bean.request.data.DatabaseCleanSearch;
import cn.mulanbay.pms.web.bean.request.data.DatabaseManualCleanRequest;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 数据库清理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/databaseClean")
public class DatabaseCleanController extends BaseController {

    private static Class<DatabaseClean> beanClass = DatabaseClean.class;

    @Autowired
    DatabaseCleanService databaseCleanService;

    /**
     * 获取列表数据
     *
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(DatabaseCleanSearch sf) {
        return callbackDataGrid(getDatabaseCleanResult(sf));
    }

    private PageResult<DatabaseClean> getDatabaseCleanResult(DatabaseCleanSearch sf) {
        PageRequest pr = sf.buildQuery();
        pr.setBeanClass(beanClass);
        Sort sort = new Sort("orderIndex", Sort.ASC);
        pr.addSort(sort);
        PageResult<DatabaseClean> qr = baseService.getBeanResult(pr);
        return qr;
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultBean create(@RequestBody @Valid DatabaseCleanFormRequest formRequest) {
        DatabaseClean bean = new DatabaseClean();
        BeanCopy.copyProperties(formRequest, bean, true);
        bean.setCreatedTime(new Date());
        baseService.saveObject(bean);
        return callback(null);
    }


    /**
     * 获取详情
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultBean get(Long id) {
        DatabaseClean br = baseService.getObject(beanClass, id);
        return callback(br);
    }

    /**
     * 手动执行
     *
     * @return
     */
    @RequestMapping(value = "/manualClean", method = RequestMethod.POST)
    public ResultBean manualClean(@RequestBody @Valid DatabaseManualCleanRequest dmc) {
        DatabaseClean br = baseService.getObject(beanClass, dmc.getId());
        int n = databaseCleanService.manualClean(br, dmc.getDays(), true);
        return callback(n);
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResultBean edit(@RequestBody @Valid DatabaseCleanFormRequest formRequest) {
        DatabaseClean bean = baseService.getObject(beanClass, formRequest.getId());
        BeanCopy.copyProperties(formRequest, bean, true);
        bean.setLastModifyTime(new Date());
        baseService.updateObject(bean);
        return callback(null);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid CommonBeanDeleteRequest deleteRequest) {
        baseService.deleteObjects(beanClass, NumberUtil.stringArrayToLongArray(deleteRequest.getIds().split(",")));
        return callback(null);
    }

    /**
     * 清空表
     *
     * @return
     */
    @RequestMapping(value = "/truncate", method = RequestMethod.GET)
    public ResultBean truncate(Long id) {
        databaseCleanService.truncateTable(id);
        return callback(null);
    }

    /**
     * 获取总记录数
     *
     * @return
     */
    @RequestMapping(value = "/getCounts", method = RequestMethod.GET)
    public ResultBean getCounts(Long id) {
        DatabaseClean br = baseService.getObject(beanClass, id);
        long n = databaseCleanService.getCounts(br.getTableName());
        return callback(n);
    }

}
