package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.common.util.PriceUtil;
import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import cn.mulanbay.pms.persistent.dto.BuyRecordRealTimeStat;
import cn.mulanbay.pms.persistent.service.BuyRecordService;
import cn.mulanbay.pms.web.bean.request.GroupType;
import cn.mulanbay.pms.web.bean.request.buy.BuyRecordAnalyseStatSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * QA的消费处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class QaConsumeHandler extends AbstractQaMessageHandler {

    @Autowired
    BuyRecordService buyRecordService;

    public QaConsumeHandler() {
        super("consume", "QA的消费处理器");
    }

    @Override
    public String handleMsg(QaMatch match) {
        String keywords = match.getKeywords();
        Date[] ds = this.parseDate(keywords);
        if (ds == null) {
            return "请输入具体的消费日期，例如:今天消费了多少";
        } else {
            return stat(ds, match.getUserId());
        }
    }

    private String stat(Date[] ds, Long userId) {
        StringBuffer sb = new StringBuffer();
        sb.append(getDateStringPrefix(ds) + "消费统计\n");
        BuyRecordAnalyseStatSearch sf = new BuyRecordAnalyseStatSearch();
        sf.setType(GroupType.TOTALPRICE);
        sf.setGroupField("goods_type_id");
        sf.setStartDate(ds[0]);
        sf.setEndDate(ds[1]);
        sf.setUserId(userId);
        List<BuyRecordRealTimeStat> list = buyRecordService.getAnalyseStat(sf);
        int i = 0;
        double total = 0;
        for (BuyRecordRealTimeStat ts : list) {
            sb.append((++i) + "." + ts.getName() + ":" + PriceUtil.changeToString(2, ts.getValue()) + "元\n");
            total += ts.getValue();
        }
        sb.append(">>花费总计:" + PriceUtil.changeToString(2, total) + "元\n");
        sb.append(">>一共消费总额:" + PriceUtil.changeToString(2, total) + "元\n");
        return sb.toString();
    }
}
