package cn.mulanbay.schedule.enums;

/**
 * 调度类型
 * 
 * @author fh
 * 
 */
public enum TriggerType {

	NOW(0,"立刻"),
	SECOND(1,"秒"),
	MINUTE(2,"分钟"),
	HOUR(3,"小时"),
	DAY(4,"天"),
	WEEK(5, "周"),
	MONTH(6,"月"),
	SEASON(7,"季度"),
	YEAR(8,"年"),
	CRON(9,"CRON");

	private Integer value;

	private String name;

	TriggerType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
