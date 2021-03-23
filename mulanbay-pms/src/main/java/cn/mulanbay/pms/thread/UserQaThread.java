package cn.mulanbay.pms.thread;

import cn.mulanbay.common.util.BeanFactoryUtil;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.handler.qa.bean.QaConfigBean;
import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import cn.mulanbay.pms.handler.qa.bean.QaMatchDetail;
import cn.mulanbay.pms.handler.qa.bean.QaMatchLog;
import cn.mulanbay.pms.persistent.domain.UserQa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: QA的记录
 * @Author: fenghong
 * @Create : 2021/3/22
 */
public class UserQaThread extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(UserQaThread.class);

    private UserQa userQa;

    private QaMatch match;

    public UserQaThread(UserQa userQa, QaMatch match) {
        super("用户QA保存线程");
        this.userQa = userQa;
        this.match = match;
    }

    @Override
    public void run() {
        try {
            if(match!=null){
                List<QaMatchDetail> matchDetails = match.getMatchDetails();
                if (StringUtil.isNotEmpty(matchDetails)) {
                    List<QaMatchLog> mls = new ArrayList<>();
                    for (QaMatchDetail md : matchDetails) {
                        QaMatchLog ml = new QaMatchLog();
                        ml.setLevel(md.getLevel());
                        QaConfigBean qcb = md.getQaConfig();
                        if (qcb == null) {
                            ml.setQaConfigName("未找到匹配");
                        } else {
                            ml.setQaConfigId(md.getQaConfig().getId());
                            ml.setQaConfigName( md.getQaConfig().getName());
                            ml.setMatchDegree(md.getMatchDegree());
                        }
                        mls.add(ml);
                    }
                    userQa.setMatchInfo(JsonUtil.beanToJson(mls));
                } else {
                    logger.warn("没有任何匹配");
                }
            }
            userQa.setCreatedTime(new Date());
            BaseService baseService = BeanFactoryUtil.getBean(BaseService.class);
            baseService.saveObject(userQa);
        } catch (Exception e) {
            logger.error("添加用户Qa日志异常",e);
        }
    }
}
