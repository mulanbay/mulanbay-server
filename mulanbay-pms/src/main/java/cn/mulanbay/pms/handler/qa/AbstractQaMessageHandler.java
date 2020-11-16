package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.handler.CacheHandler;
import cn.mulanbay.pms.handler.qa.bean.QaConfigBean;
import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import cn.mulanbay.pms.handler.qa.bean.QaMatchCache;
import cn.mulanbay.pms.persistent.enums.QaResultType;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * QA消息处理基类
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public abstract class AbstractQaMessageHandler extends BaseHandler {

    private String code;

    @Autowired
    protected BaseService baseService;

    @Autowired
    protected CacheHandler cacheHandler;

    public AbstractQaMessageHandler() {

    }

    public AbstractQaMessageHandler(String code, String name) {
        super(name);
        this.code = code;
    }

    /**
     * 处理匹配
     *
     * @return
     */
    public String handleMatch(QaMatch match) {
        setMatchCache(match);
        QaConfigBean qcb = match.getLastMathQa();
        QaResultType resultType = qcb.getResultType();
        if (resultType == QaResultType.SQL) {
            //SQL查询
            return handleSqlReplay(qcb, match.getKeywords(), match.getUserId(), 1);
        } else {
            return handleMsg(match);
        }
    }

    private void setMatchCache(QaMatch match) {
        QaConfigBean qcb = match.getFirstMathQa();
        if (true == qcb.getMatchCache()) {
            //设置缓存
            QaMatchCache matchCache = new QaMatchCache();
            matchCache.setMatch(match);
            putCache(matchCache);
        }
    }

    /**
     * 设置缓存
     *
     * @param matchCache
     */
    protected void putCache(QaMatchCache matchCache) {
        String key = CacheKey.getKey(CacheKey.QA_MATCH_CACHE, matchCache.getMatch().getSessionId());
        cacheHandler.set(key, matchCache, 5 * 3600);
    }

    /**
     * 利用SQL配置
     *
     * @param qcb
     * @param userId
     * @return
     */
    protected String handleSqlReplay(QaConfigBean qcb, String keywords, Long userId, int page) {
        Date[] ds = this.parseDate(keywords);
        if (ds == null) {
            //默认最近90天的
            ds = new Date[2];
            Date now = new Date();
            ds[0] = DateUtil.getDate(-90, now);
            ds[1] = now;
        }
        String sql = qcb.getReplayContent();
        //添加额外条件
        sql = this.addExtraCondition(sql, keywords);
        //todo 更新缓存
        List<Object[]> list = baseService.getBeanListSQL(sql, page, 10, userId, ds[0], ds[1]);
        StringBuffer sb = new StringBuffer();
        if (StringUtil.isEmpty(list)) {
            sb.append(page <= 1 ? "未找到相关数据" : "无更多数据");
        } else {
            String title = qcb.getReplayTitle() == null ? "数据如下" : qcb.getReplayTitle();
            if (page > 1) {
                title += "(第" + page + "页)";
            }
            sb.append(title + ":\n");
            for (Object[] oo : list) {
                List paras = new ArrayList();
                for (Object p : oo) {
                    paras.add(p == null ? "无" : p.toString());
                }
                String line = MessageFormat.format(qcb.getColumnTemplate(), paras.toArray());
                sb.append("*" + line + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * 增加额外的条件
     *
     * @param sql      必须包含extraCondition替换字符
     * @param keywords
     * @return
     */
    private String addExtraCondition(String sql, String keywords) {
        if (sql.contains("extraCondition")) {
            String ec = this.parseExtraCondition(keywords);
            if (ec != null) {
                sql = sql.replaceAll("\\{extraCondition\\}", ec);
            } else {
                sql = sql.replaceAll("\\{extraCondition\\}", "");
            }
        }
        return sql;
    }

    /**
     * 解析出额外的条件，具体的业务类实现，其实一般由第一级匹配到的Handler来处理
     *
     * @param keywords
     * @return
     */
    public String parseExtraCondition(String keywords) {
        return null;
    }

    /**
     * 具体的处理逻辑,子类复写
     * @param match
     * @return
     */
    public abstract String handleMsg(QaMatch match);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 解析日期
     *
     * @param keywords
     * @return
     */
    protected Date[] parseDate(String keywords) {
        Date[] ds = new Date[2];
        if (keywords.contains("今日") || keywords.contains("今天")) {
            ds[0] = DateUtil.getDate(0);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(ds[0]);
        } else if (keywords.contains("昨天")) {
            ds[0] = DateUtil.getDate(-1);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(ds[0]);
        } else if (keywords.contains("明天")) {
            ds[0] = DateUtil.getDate(1);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(ds[0]);
        } else if (keywords.contains("这个月") || keywords.contains("本月")) {
            Date now = new Date();
            ds[0] = DateUtil.getFirstDayOfMonth(now);
            Date last = DateUtil.getLastDayOfMonth(now);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(last);
        } else if (keywords.contains("上个月") || keywords.contains("上月")) {
            Date now = new Date();
            Date begin = DateUtil.getDateMonth(-1, now);
            ds[0] = DateUtil.getFirstDayOfMonth(begin);
            Date last = DateUtil.getLastDayOfMonth(begin);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(last);
        } else if (keywords.contains("下个月") || keywords.contains("下月")) {
            Date now = new Date();
            Date begin = DateUtil.getDateMonth(1, now);
            ds[0] = DateUtil.getFirstDayOfMonth(begin);
            Date last = DateUtil.getLastDayOfMonth(begin);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(last);
        } else if (keywords.contains("今年")) {
            Date now = new Date();
            ds[0] = DateUtil.getYearFirst(now);
            Date last = DateUtil.getLastDayOfYear(now);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(last);
        } else if (keywords.contains("去年")) {
            String year = DateUtil.getFormatDate(new Date(), "yyyy");
            ds[0] = DateUtil.getYearFirst(Integer.valueOf(year) - 1);
            Date last = DateUtil.getLastDayOfYear(ds[0]);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(last);
        } else if (keywords.contains("明年")) {
            String year = DateUtil.getFormatDate(new Date(), "yyyy");
            ds[0] = DateUtil.getYearFirst(Integer.valueOf(year) + 1);
            Date last = DateUtil.getLastDayOfYear(ds[0]);
            ds[1] = DateUtil.getTodayTillMiddleNightDate(last);
        } else {
            List<String> ss = this.parseDateKey(keywords);
            if (ss.isEmpty()) {
                ds = null;
            } else {
                ds[0] = DateUtil.getDate(ss.get(0), "yyyyMMdd");
                if (ss.size() > 1) {
                    Date last = DateUtil.getDate(ss.get(1), "yyyyMMdd");
                    ds[1] = DateUtil.getTodayTillMiddleNightDate(last);
                } else {
                    ds[1] = DateUtil.getTodayTillMiddleNightDate(ds[0]);
                }
            }
        }
        return ds;
    }

    /**
     * 由正则表达式提取时间
     *
     * @param str
     * @return 格式：20200101,即yyyyMMdd
     */
    private List<String> parseDateKey(String str) {
        List<String> ds = new ArrayList<>();
        String reg = "[1-9]\\d{3}(((0[13578]|1[02])([0-2]\\d|3[01]))|((0[469]|11)([0-2]\\d|30))|(02([01]\\d|2[0-8])))";
        Pattern pattern = Pattern.compile(reg);
        //去掉所有的横杠
        Matcher matcher = pattern.matcher(str.replace("-", ""));
        while (matcher.find()) {
            String s = matcher.group();
            //System.out.println (s);
            ds.add(s);
        }
        return ds;
    }

    /**
     * 获取日期时间段标题前缀
     *
     * @param ds
     * @return
     */
    protected String getDateStringPrefix(Date[] ds) {
        String s1 = DateUtil.getFormatDate(ds[0], DateUtil.FormatDay1);
        String s2 = DateUtil.getFormatDate(ds[1], DateUtil.FormatDay1);
        if (s1.equals(s2)) {
            return s1;
        } else {
            return s1 + "~" + s2;
        }
    }
}
