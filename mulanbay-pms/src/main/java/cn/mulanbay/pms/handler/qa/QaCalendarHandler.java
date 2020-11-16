package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.pms.handler.UserCalendarHandler;
import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import cn.mulanbay.pms.web.bean.request.usercalendar.UserCalendarListSearch;
import cn.mulanbay.pms.web.bean.response.calendar.UserCalendarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * QA的日历相关逻辑处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class QaCalendarHandler extends AbstractQaMessageHandler {

    @Autowired
    UserCalendarHandler userCalendarHandler;

    public QaCalendarHandler() {
        super("cld", "QA的日历处理器");
    }

    @Override
    public String handleMsg(QaMatch match) {
        String keywords = match.getKeywords();
        Date[] ds = this.parseDate(keywords);
        if (ds == null) {
            ds = new Date[2];
            if (keywords.length() <= 3) {
                //默认为今天
                ds[0] = DateUtil.getDate(0);
            } else {
                String date = keywords.substring(2, 10);
                ds[0] = DateUtil.getDate(date, "yyyyMMdd");
            }
            ds[1] = DateUtil.getTodayTillMiddleNightDate(ds[0]);
        }
        UserCalendarListSearch sf = new UserCalendarListSearch();
        Date start = ds[0];
        sf.setStartDate(start);
        sf.setEndDate(ds[1]);
        sf.setUserId(match.getUserId());
        sf.setNeedFinished(false);
        sf.setNeedPeriod(true);
        sf.setNeedBudget(true);
        sf.setNeedTreatDrug(true);
        sf.setNeedBandLog(false);
        List<UserCalendarVo> ucList = userCalendarHandler.getUserCalendarList(sf);
        StringBuffer sb = new StringBuffer();
        sb.append(DateUtil.getFormatDate(ds[0], DateUtil.FormatDay1) + "日历列表:\n");
        for (int i = 0; i < ucList.size(); i++) {
            UserCalendarVo cc = ucList.get(i);
            sb.append((i + 1) + ":" + cc.getContent() + "\n");

        }
        return sb.toString();
    }
}
