package cn.mulanbay.pms.util;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.enums.EnumIdType;
import cn.mulanbay.pms.web.bean.response.TreeBean;

import java.lang.reflect.Method;
import java.text.Collator;
import java.util.*;

/**
 * 树形结构操作类
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
public class TreeBeanUtil {

    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

    /**
     * 根据枚举类型生成树
     *
     * @param enumClass
     * @param idType
     * @param needRoot
     * @return
     */
    public static List<TreeBean> createTree(String enumClass, EnumIdType idType, Boolean needRoot) {
        try {
            int index = enumClass.indexOf(".");
            if (index < 0) {
                //没有全路径的使用默认包
                enumClass = "cn.mulanbay.pms.persistent.enums." + enumClass;
            }
            Class<Enum> ec = (Class<Enum>) Class.forName(enumClass);
            Method toName = ec.getMethod("getName");
            Method toValue = ec.getMethod("getValue");
            //得到enum的所有实例
            Enum[] objs = ec.getEnumConstants();
            List<TreeBean> list = new ArrayList<TreeBean>();
            for (Enum obj : objs) {
                TreeBean tb = new TreeBean();
                if (idType == EnumIdType.ORDINAL) {
                    tb.setId(String.valueOf(obj.ordinal()));
                } else if (idType == EnumIdType.VALUE) {
                    tb.setId(String.valueOf(toValue.invoke(obj)));
                } else if (idType == EnumIdType.FIELD) {
                    tb.setId(obj.name());
                } else {
                    tb.setId("未知");
                }
                tb.setText(String.valueOf(toName.invoke(obj)));
                list.add(tb);
            }
            return TreeBeanUtil.addRoot(list, needRoot);
        } catch (Exception e) {
            throw new ApplicationException(PmsErrorCode.CREATE_TREE_ERROR, e);
        }
    }

    /**
     * 为tree添加跟节点
     *
     * @param list
     * @param needRoot
     * @return
     */
    public static List<TreeBean> addRoot(List<TreeBean> list, Boolean needRoot) {
        if (needRoot == null || needRoot == false) {
            return list;
        } else {
            TreeBean root = new TreeBean();
            root.setId("");
            root.setText("请选择");
            root.setChildren(list);
            List<TreeBean> newList = new ArrayList<TreeBean>();
            newList.add(root);
            return newList;
        }
    }

    /**
     * 转换为tree
     *
     * @param list 不含层级关系的单层list
     * @return
     */
    public static TreeBean changToTree(TreeBean root, List<TreeBean> list) {
        for (TreeBean treeBean : list) {
            if (treeBean.getPid() != null && treeBean.getPid().equals(root.getId())) {
                root.addChild(treeBean);
            }
        }
        if (root.getChildren() != null) {
            for (TreeBean cc : root.getChildren()) {
                changToTree(cc, list);
            }
        }
        return root;
    }

    /**
     * 去重，每个关键字以英文逗号分隔
     *
     * @param list
     * @return
     */
    public static Set<String> deleteDuplicate(List<String> list) {
        //去重
        Set<String> keywordsSet = new TreeSet<>(CHINA_COMPARE);
        for (String s : list) {
            if (s == null || s.isEmpty()) {
                continue;
            }
            String[] ss = s.split(",");
            for (String key : ss) {
                keywordsSet.add(key);
            }
        }
        return keywordsSet;
    }

}
