package cn.mulanbay.schedule.job;

import cn.mulanbay.common.util.DateUtil;
import cn.mulanbay.common.util.FileUtil;
import cn.mulanbay.common.util.ZipUtil;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.ScheduleErrorCode;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 *
 * 文件清理
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class FileClearJob extends AbstractBaseJob {

	private static final Logger logger = LoggerFactory.getLogger(FileClearJob.class);

	public static String fileSeparate = "/";

	private FileClearJobPara para;

	@Override
	public TaskResult doTask() {
		TaskResult tr = new TaskResult();
		String desPath = null;
		if (para.isBackup()) {
			// 生成备份文件夹
			String datePath = DateUtil.getFormatDate(new Date(),
					para.getBackupDateFormat());
			desPath = para.getBackupPath() + fileSeparate + datePath;
			FileUtil.checkPathExits(desPath);
		}
		Date bussDay = this.getBussDay();
		logger.debug("开始清理文件夹[" + para.getBackupPath() + "]");
		Date compareDate = DateUtil.getDate(0-para.getKeepDays(),bussDay);
		clearFile(compareDate .getTime(), desPath, new File(para.getSourcePath()));
		logger.debug("清理文件夹[" + para.getSourcePath() + "]结束");

		if (para.isBackup() && para.isZip()) {
			logger.debug("开始压缩文件夹[" + desPath + "]");
			File zip = new File(desPath + ".zip");
			ZipUtil.ZipFiles(zip, "", new File(desPath));
			logger.debug("压缩文件夹[" + desPath + "]结束");
			// 删除文件夹
			FileUtil.deleteFolder(new File(desPath));
		}
		tr.setExecuteResult(JobExecuteResult.SUCCESS);
		return tr;
	}

	/**
	 * 清理文件
	 *
	 * @param expireDay
	 *            过期时间
	 * @param desPath
	 *            备份的目标文件夹
	 * @param file
	 *            待清理的文件或文件夹
	 */
	private void clearFile(long expireDay, String desPath, File file) {
		if (file.isFile()) {
			// 清理
			try {
				if (file.lastModified() < expireDay) {
					// 通过文件最后修改时间判断
					if (para.isBackup()) {
						File des = new File(para.getBackupPath() + fileSeparate
								+ file.getName());
						FileUtil.moveFile(file, des, true);
					} else {
						boolean b = file.delete();
						if (!b) {
							logger.error("删除文件[" + file.getAbsolutePath()
									+ "]异常");
						}
					}
				}

			} catch (Exception e) {
				logger.error("清理文件[" + file.getAbsolutePath() + "]异常", e);
			}
		} else {
			for (File f : file.listFiles()) {
				clearFile(expireDay, desPath, f);
			}

		}

	}

	@Override
	public ParaCheckResult checkTriggerPara() {
		ParaCheckResult rb = new ParaCheckResult();
		rb.setMessage("参数格式为：1. 待清理的路径 ,2. 保留天数, 3. 备份的路径,4. 是否要备份（true/false）," +
				"5. 备份时是否要打包（true/false）,6. 备份文件时间格式");
		para = this.getTriggerParaBean();
		if(para==null){
			rb.setErrorCode(ScheduleErrorCode.TRIGGER_PARA_NULL);
			return rb;
		}else{
			File sf = new File(para.getSourcePath());
			if (!sf.exists()) {
				rb.setErrorCode(ScheduleErrorCode.FILE_PATH_NOT_EXIT);
				return rb;
			}
		}
		return rb;
	}

	@Override
	public Class getParaDefine() {
		return FileClearJobPara.class;
	}

}
