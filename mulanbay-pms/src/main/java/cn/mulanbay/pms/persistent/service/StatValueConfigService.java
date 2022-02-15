package cn.mulanbay.pms.persistent.service;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.dao.BaseHibernateDao;
import cn.mulanbay.pms.persistent.domain.DictItem;
import cn.mulanbay.pms.persistent.domain.StatValueConfig;
import cn.mulanbay.pms.persistent.dto.StatValueConfigDetail;
import cn.mulanbay.pms.persistent.dto.StatValueConfigDto;
import cn.mulanbay.pms.persistent.enums.StatValueSource;
import cn.mulanbay.pms.persistent.enums.StatValueType;
import cn.mulanbay.pms.util.TreeBeanUtil;
import cn.mulanbay.pms.web.bean.response.TreeBean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StatValueConfigService extends BaseHibernateDao {

    /**
     * 获取统计类的配置值列表
     *
     * @param fid  报表类型id或者提醒配置id或者计划配置id
     * @param type 类型
     * @return
     */
    public List<StatValueConfigDto> getStatValueConfig(Long fid, StatValueType type, Long userId) {
        try {
            String hql = "from StatValueConfig where fid=?0 and type=?1 order by orderIndex";
            List<StatValueConfig> configs = this.getEntityListNoPageHQL(hql, fid, type);
            if (configs.isEmpty()) {
                return new ArrayList<>();
            } else {
                List<StatValueConfigDto> list = new ArrayList<>();
                for (StatValueConfig svc : configs) {
                    StatValueConfigDto scb = getStatValueConfigBean(svc, null, userId);
                    list.add(scb);
                }
                return list;
            }
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取统计类的配置值列表异常", e);
        }

    }

    /**
     * 获取单个
     *
     * @param svc
     * @return
     */
    public StatValueConfigDto getStatValueConfigBean(StatValueConfig svc, String pid, Long userId) {
        StatValueConfigDto scb = new StatValueConfigDto();
        scb.setName(svc.getName());
        scb.setPromptMsg(svc.getPromptMsg());
        scb.setCasCadeType(svc.getCasCadeType());
        StatValueSource source = svc.getSource();
        List<StatValueConfigDetail> details = null;
        switch (source) {
            case SQL: {
                details = this.getSqlDetails(svc, pid, userId);
                break;
            }
            case ENUM: {
                details = this.getEnumDetails(svc);
                break;
            }
            case DATA_DICT: {
                details = this.getDataDictDetails(svc);
                break;
            }
            case JSON: {
                details = this.getJsonDetails(svc);
                break;
            }
            default:
                break;
        }
        scb.setList(details);
        return scb;
    }

    /**
     * 数据库类型
     *
     * @param svc
     * @param pid
     * @param userId
     * @return
     */
    private List<StatValueConfigDetail> getSqlDetails(StatValueConfig svc, String pid, Long userId) {
        try {
            String sql = svc.getSqlContent();
            if (!StringUtil.isEmpty(pid)) {
                sql = MessageFormat.format(sql, pid);
            }
            if (!StringUtil.isEmpty(svc.getUserField())) {
                sql = sql.replaceAll("\\{" + svc.getUserField() + "\\}", userId.toString());
            }
            List<StatValueConfigDetail> res = new ArrayList<>();
            List<Object[]> vcs = this.getEntityListNoPageSQL(sql);
            for (Object[] o : vcs) {
                StatValueConfigDetail detail = new StatValueConfigDetail();
                detail.setId(o[0].toString());
                detail.setText(o[1].toString());
                res.add(detail);
            }
            return res;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "查询配置加载项异常", e);
        }
    }

    /**
     * 枚举类型
     *
     * @param svc
     * @return
     */
    private List<StatValueConfigDetail> getEnumDetails(StatValueConfig svc) {
        List<TreeBean> list = TreeBeanUtil.createTree(svc.getEnumClass(), svc.getEnumIdType(), false);
        List<StatValueConfigDetail> res = new ArrayList<>();
        for (TreeBean tb : list) {
            StatValueConfigDetail detail = new StatValueConfigDetail();
            detail.setId(tb.getId());
            detail.setText(tb.getText());
            res.add(detail);
        }
        return res;
    }

    /**
     * 数据字典类型
     *
     * @param svc
     * @return
     */
    private List<StatValueConfigDetail> getDataDictDetails(StatValueConfig svc) {
        try {
            String hql = "from DictItem where group.code=?0 and status =1 order by orderIndex";
            List<StatValueConfigDetail> res = new ArrayList<>();
            List<DictItem> vcs = this.getEntityListNoPageHQL(hql, svc.getDictGroupCode());
            for (DictItem o : vcs) {
                StatValueConfigDetail detail = new StatValueConfigDetail();
                detail.setId(o.getCode());
                detail.setText(o.getName());
                res.add(detail);
            }
            return res;
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "根据数据字典组查询配置加载项异常", e);
        }
    }

    /**
     * 枚举类型
     *
     * @param svc
     * @return
     */
    private List<StatValueConfigDetail> getJsonDetails(StatValueConfig svc) {
        String jsonData = svc.getJsonData();
        List<StatValueConfigDetail> res = JsonUtil.jsonToBeanList(jsonData, StatValueConfigDetail.class);
        return res;
    }

    /**
     * 获取配置列表
     * @param fid
     * @param type
     * @return
     */
    public List<StatValueConfig> getConfigList(Long fid,StatValueType type) {
        try {
            String hql = "from StatValueConfig where fid=?0 and type =?1 order by orderIndex";
            return this.getEntityListNoPageHQL(hql,fid,type);
        } catch (BaseException e) {
            throw new PersistentException(ErrorCode.OBJECT_GET_LIST_ERROR,
                    "获取配置列表异常", e);
        }
    }
}
