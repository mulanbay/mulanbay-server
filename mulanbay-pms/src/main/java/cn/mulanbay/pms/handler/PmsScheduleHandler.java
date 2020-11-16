package cn.mulanbay.pms.handler;

import cn.mulanbay.pms.persistent.service.PmsScheduleService;
import cn.mulanbay.schedule.QuartzSource;
import cn.mulanbay.schedule.handler.ScheduleHandler;
import cn.mulanbay.schedule.lock.ScheduleLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 调度处理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class PmsScheduleHandler extends ScheduleHandler {

    @Value("${schedule.enable}")
    private boolean enableSchedule;

    @Value("${system.nodeId}")
    private String deployId;

    @Value("${schedule.supportDistri}")
    private boolean supportDistri;

    @Value("${schedule.distriTaskMinCost}")
    private int distriTaskMinCost;

    @Value("${system.threadPool.queueSize}")
    int queueSize;

    @Autowired
    PmsScheduleService pmsScheduleService;

    @Autowired
    ScheduleLocker scheduleLocker;

    @Autowired
    PmsNotifyHandler pmsNotifyHandler;

    @PostConstruct
    public void initData() {
        this.setEnableSchedule(enableSchedule);
        if (enableSchedule) {
            QuartzSource quartzSource = new QuartzSource();
            quartzSource.setDeployId(deployId);
            quartzSource.setSchedulePersistentProcessor(pmsScheduleService);
            quartzSource.setSupportDistri(supportDistri);
            quartzSource.setScheduleLocker(scheduleLocker);
            quartzSource.setDistriTaskMinCost(distriTaskMinCost);
            quartzSource.setNotifiableProcessor(pmsNotifyHandler);
            this.setQuartzSource(quartzSource);
        }

    }

}
