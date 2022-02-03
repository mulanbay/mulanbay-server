package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.ClazzUtils;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.persistent.domain.User;
import cn.mulanbay.pms.persistent.enums.AccountType;
import cn.mulanbay.pms.persistent.enums.BussType;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.request.common.GetEnumTreeRequest;
import cn.mulanbay.pms.web.bean.request.common.GetYearTreeSearch;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import cn.mulanbay.web.bean.response.ResultBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 公共接口
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    /**
     * 获取月统计年份列表
     *
     * @return
     */
    @RequestMapping(value = "/getYearTree")
    public ResultBean getYearTree(GetYearTreeSearch sf) {
        try {
            User user = baseService.getObject(User.class, sf.getUserId());
            //最小年份由注册时间决定
            int minYear = Integer.valueOf(DateUtil.getFormatDate(user.getCreatedTime(), "yyyy"));
            int maxYear = Integer.valueOf(DateUtil.getFormatDate(new Date(), "yyyy"));
            //最大和最小年份之间最少间隔5个
            if(maxYear-minYear<4){
                minYear = maxYear-4;
            }
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (int i = maxYear; i >= minYear; i--) {
                TreeBean tb = new TreeBean();
                tb.setId(i + "");
                tb.setText(i + "年");
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, sf.getNeedRoot()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取年份列表树异常",
                    e);
        }
    }

    /**
     * 业务类别列表
     *
     * @return
     */
    @RequestMapping(value = "/getBussTypeTree")
    public ResultBean getBussTypeTree(Boolean needRoot) {
        try {
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (BussType bt : BussType.values()) {
                TreeBean tb = new TreeBean();
                tb.setId(bt.getBeanName());
                tb.setText(bt.getName());
                list.add(tb);
            }
            return callback(TreeBeanUtil.addRoot(list, needRoot));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.SYSTEM_ERROR, "获取业务类别列表异常",
                    e);
        }
    }

    /**
     * 根据枚举类型获取类型树
     *
     * @return
     */
    @RequestMapping(value = "/getEnumTree")
    public ResultBean getEnumTree(GetEnumTreeRequest etr) {
        List<TreeBean> list = TreeBeanUtil.createTree(etr.getEnumClass(), etr.getIdType(), etr.getNeedRoot());
        return callback(list);
    }

    /**
     * 映射实体
     *
     * @return
     */
    @RequestMapping(value = "/getEnumClassNamesTree", method = RequestMethod.GET)
    public ResultBean getDomainClassNamesTree(Boolean needRoot) {
        List<TreeBean> treeBeans = new ArrayList<>();
        //根据指定的一个枚举类
        String packageName1 = AccountType.class.getPackage().getName();
        List<String> list = ClazzUtils.getClazzName(packageName1, false);
        Collections.sort(list);
        for (String s : list) {
            TreeBean treeBean = new TreeBean();
            int n = s.lastIndexOf(".");
            String className = s.substring(n + 1, s.length());
            treeBean.setId(className);
            treeBean.setText(className);
            treeBeans.add(treeBean);
        }
        return callback(TreeBeanUtil.addRoot(treeBeans, needRoot));
    }

}
