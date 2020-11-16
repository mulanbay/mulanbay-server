package cn.mulanbay.schedule;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.schedule.domain.TaskTrigger;
import cn.mulanbay.schedule.enums.TriggerType;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 调度工厂类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class TriggerFactory {

	private static final Logger logger = LoggerFactory.getLogger(TriggerFactory.class);

	/**
	 * 创建触发器
	 * @param ts
	 * @return
	 */
	public static Trigger createTrigger(TaskTrigger ts) {
		Trigger trigger = null;
		try {
			TriggerType triggerType = ts.getTriggerType();
			Date startTime = getStartTime(ts);
			// todo 每个调度可以定义最后的执行时间，目前TaskTrigger没有定义
			Date endTime = null;
			int interval = getInterval(ts);
			if (triggerType==TriggerType.SECOND) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.startAt(startTime)
						.endAt(endTime)
						.withSchedule(
								calendarIntervalSchedule()
										.withIntervalInSeconds(interval))
						.build();
				logger.debug("bulid seconds triggerType");
			} else if (triggerType==TriggerType.MINUTE) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.startAt(startTime)
						.endAt(endTime)
						.withSchedule(
								calendarIntervalSchedule()
										.withIntervalInMinutes(interval))
						.build();
				logger.debug("bulid minutes triggerType");
			} else if (triggerType==TriggerType.HOUR) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.startAt(startTime)
						.endAt(endTime)
						.withSchedule(
								calendarIntervalSchedule().withIntervalInHours(
										interval)).build();
				logger.debug("bulid hours triggerType");
			} else if (triggerType==TriggerType.DAY) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.startAt(startTime)
						.endAt(endTime)
						.withSchedule(
								calendarIntervalSchedule().withIntervalInDays(
										interval)).build();
				logger.debug("bulid DAY triggerType");
			} else if (triggerType==TriggerType.WEEK) {
				String daysString = ts.getCronExpression();
				if (daysString == null || "".equals(daysString)) {
					// 没有设置，则默认星期一
					daysString = "2";
				}
				String[] days = daysString.split(",");
				Set<Integer> ds = new HashSet<Integer>();
				for (String d : days) {
					ds.add(Integer.valueOf(d));
				}
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.startAt(startTime)
						.endAt(endTime)
						.withSchedule(
								dailyTimeIntervalSchedule().onDaysOfTheWeek(ds))
						.build();
				logger.debug("bulid WEEK triggerType");
			} else if (triggerType==TriggerType.MONTH) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.startAt(startTime)
						.endAt(endTime)
						.withSchedule(
								calendarIntervalSchedule()
										.withIntervalInMonths(interval))
						.build();
				logger.debug("bulid MONTH triggerType");
			} else if (triggerType==TriggerType.YEAR) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.startAt(startTime)
						.endAt(endTime)
						.withSchedule(
								calendarIntervalSchedule().withIntervalInYears(
										interval)).build();
				logger.debug("bulid YEAR triggerType");
			} else if (triggerType==TriggerType.CRON) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName()).startAt(startTime)
						.withSchedule(cronSchedule(ts.getCronExpression()))
						.build();
				logger.debug("bulid cron triggerType");
			} else if (triggerType==TriggerType.NOW) {
				trigger = newTrigger()
						.withIdentity(ts.getId().toString(),
								ts.getGroupName())
						.withSchedule(
								dailyTimeIntervalSchedule().withRepeatCount(
										interval)).build();
				logger.debug("bulid NOW triggerType");
			} else {
				logger.error("找不到调度类型：" + triggerType);
			}
		} catch (Exception e) {
			logger.error(ts.getName() + "[" + ts.getId()
					+ "]生成触发器异常", e);
			throw new ApplicationException(ScheduleErrorCode.TRIGGER_CREATE_ERROR,ts.getName() + "[" + ts.getId()
					+ "]生成触发器异常",e);
		}
		return trigger;
	}

	/**
	 * 获取一个调度的开始执行时间
	 * 该方法主要针对服务器被重启后还是能按照原来的调度模式继续运行，不需要重新设置
	 * 1. 如果没有被执行过为设置的首次执行时间
	 * 2. 如果已经被执行过为下一次执行时间
	 *
	 * @param ts
	 * @return
	 */
	private static Date getStartTime(TaskTrigger ts) {
		if (ts.getNextExecuteTime() == null) {
			return ts.getFirstExecuteTime();
		} else {
			return ts.getNextExecuteTime();
		}
	}

	private static int getInterval(TaskTrigger ts) {
		if (ts.getTriggerInterval() == null) {
			return 1;
		}
		{
			return ts.getTriggerInterval().intValue();
		}
	}
}
