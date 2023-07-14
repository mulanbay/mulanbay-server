package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.City;
import cn.mulanbay.pms.persistent.domain.Country;
import cn.mulanbay.pms.persistent.domain.District;
import cn.mulanbay.pms.persistent.domain.Province;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * GEO
 *
 * @author fenghong
 * @create 2018-02-17 22:53
 */
@Service
@Transactional
public class GeoService extends BaseHibernateDao {

    /**
     * 获取省份列表
     *
     * @return
     */
    public List<Province> getProvinceList() {
        try {
            String hql = "from Province ";
            List<Province> list = this.getEntityListNoPageHQL(hql);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取省份列表异常", e);
        }
    }

    /**
     * 获取国家列表
     *
     * @return
     */
    public List<Country> getCountryList() {
        try {
            String hql = "from Country where status=1 order by orderIndex ";
            List<Country> list = this.getEntityListNoPageHQL(hql);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取获取国家列表异常", e);
        }
    }

    /**
     * 获取城市列表
     *
     * @return
     */
    public List<City> getCityList(Integer provinceId) {
        try {
            String hql = "from City where provinceId=?0 ";
            List<City> list = this.getEntityListNoPageHQL(hql, provinceId);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取城市列表异常", e);
        }
    }

    /**
     * 获取县（地区）列表
     *
     * @return
     */
    public List<District> getDistrictList(Integer cityId) {
        try {
            String hql = "from District where cityId=?0 ";
            List<District> list = this.getEntityListNoPageHQL(hql, cityId);
            return list;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取县（地区）列表异常", e);
        }
    }
}
