package cn.mulanbay.pms.handler.job;

import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.util.FtpUtil;
import cn.mulanbay.schedule.ParaCheckResult;
import cn.mulanbay.schedule.TaskResult;
import cn.mulanbay.schedule.enums.JobExecuteResult;
import cn.mulanbay.schedule.job.AbstractBaseJob;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 向远程FTP文件夹同步
 * 把本地文件夹下的文件全部同步到对应的远程文件夹下
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class FtpSyncToRemoteJob extends AbstractBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(FtpSyncToRemoteJob.class);

    private FtpSyncToRemoteJobPara para;

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        FTPFile[] ftpFiles = FtpUtil.listFiles(para.getServerHost(), para.getPort(),
                para.getUsername(), para.getPassword(), para.getRemotePath());
        File localFilePath = new File(para.getLocalPath());
        File[] localFiles = localFilePath.listFiles();
        if (localFiles == null || localFiles.length == 0) {
            result.setExecuteResult(JobExecuteResult.SKIP);
            result.setComment("本地路径[" + para.getLocalPath() + "]无文件，不需要同步");
        } else {
            int syncs = 0;
            for (File f : localFiles) {
                if (f.isDirectory()) {
                    logger.warn("还无法同步文件夹，路径：" + f.getAbsolutePath());
                } else {
                    boolean ex = isRemoteExit(ftpFiles, f);
                    if (!ex) {
                        try {
                            //同步
                            InputStream input = new FileInputStream(f);
                            FtpUtil.uploadFile(para.getServerHost(), para.getPort(), para.getUsername(),
                                    para.getPassword(), para.getRemotePath(), f.getName(), input);
                            syncs++;
                        } catch (ApplicationException ae) {
                            result.setComment("异常信息:" + ae.getMessageDetail());
                            logger.error("向远程文件夹[" + para.getRemotePath() + "]同步文件[" + f.getAbsolutePath() + "]异常", ae);
                        } catch (Exception e) {
                            result.setComment("异常信息:" + e.getMessage());
                            logger.error("向远程文件夹[" + para.getRemotePath() + "]同步文件[" + f.getAbsolutePath() + "]异常", e);
                        }

                    }
                }
            }
            result.setExecuteResult(JobExecuteResult.SUCCESS);
            result.setComment("" + result.getComment() + "一共同步了" + syncs + "个文件");
        }
        return result;
    }

    /**
     * 判断远程文件夹下是否已经有该文件
     *
     * @param ftpFiles
     * @param localFile
     * @return
     */
    private boolean isRemoteExit(FTPFile[] ftpFiles, File localFile) {
        if (ftpFiles == null || ftpFiles.length == 0) {
            return false;
        } else {
            for (FTPFile f : ftpFiles) {
                if (f.getName().equals(localFile.getName())) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * @return
     */
    @Override
    public ParaCheckResult checkTriggerPara() {
        ParaCheckResult pcr = new ParaCheckResult();
        para = this.getTriggerParaBean();
        return pcr;
    }

    @Override
    public Class getParaDefine() {
        return FtpSyncToRemoteJobPara.class;
    }
}
