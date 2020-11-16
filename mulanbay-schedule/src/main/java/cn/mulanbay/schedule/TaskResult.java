package cn.mulanbay.schedule;

import cn.mulanbay.schedule.enums.JobExecuteResult;

/**
 * 调度执行结果
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class TaskResult {

	private JobExecuteResult executeResult =JobExecuteResult.SKIP;
	
	private String subTaskExecuteResults;
	
	private String comment;

	public TaskResult() {
	}

	public TaskResult(JobExecuteResult executeResult) {
		this.executeResult = executeResult;
	}

	public JobExecuteResult getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(JobExecuteResult executeResult) {
		this.executeResult = executeResult;
	}

	public String getSubTaskExecuteResults() {
		return subTaskExecuteResults;
	}

	public void setSubTaskExecuteResults(String subTaskExecuteResults) {
		this.subTaskExecuteResults = subTaskExecuteResults;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
