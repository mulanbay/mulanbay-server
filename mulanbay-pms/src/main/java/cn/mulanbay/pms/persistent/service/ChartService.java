package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.ChartConfig;
import cn.mulanbay.pms.persistent.domain.UserChart;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 图表配置
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Service
@Transactional
public class ChartService extends BaseHibernateDao {


    /**
     * 获取图表配置
     * 需要根据用户级别判断
     *
     * @return
     */
    public ChartConfig getChartConfig(Long id, Integer userLevel) {
        try {
            String hql = "from ChartConfig where id=?0 and level<=?1 ";
            return (ChartConfig) this.getEntityForOne(hql, id, userLevel);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_ERROR,
                    "获取图表配置异常", e);
        }
    }

    /**
     * 获取图表置列表
     *
     * @return
     */
    public List<ChartConfig> getChartConfigList(Integer minLevel) {
        try {
            String hql = "from ChartConfig where status=?0 and level<=?1 ";
            return this.getEntityListNoPageHQL(hql, CommonStatus.ENABLE, minLevel);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取图表置列表异常", e);
        }
    }

    /**
     * 获取在首页显示的图表
     *
     * @param userId
     * @param index  表示第几个
     * @return
     */
    public UserChart getShowIndexChart(Long userId, Integer index) {
        try {
            String hql = "from UserChart where showInIndex=1 and userId=?0 and status=?1 order by orderIndex ";
            List list = this.getEntityListHQL(hql, index, 1, userId, CommonStatus.ENABLE);
            if (list.isEmpty()) {
                return null;
            } else {
                return (UserChart) list.get(0);
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取在首页显示的图表异常", e);
        }
    }
}
