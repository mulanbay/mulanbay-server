package cn.mulanbay.schedule.job;

import cn.mulanbay.schedule.para.AbstractTriggerPara;
import cn.mulanbay.schedule.para.EditType;
import cn.mulanbay.schedule.para.JobParameter;

public class FileClearJobPara extends AbstractTriggerPara {

    //源文件夹，即需要清理的文件夹
    @JobParameter(name = "源文件夹",dataType = String.class,desc = "即需要清理的文件夹",editType = EditType.TEXT)
    private String sourcePath;

    //被清理文件的备份文件夹
    @JobParameter(name = "备份文件夹",dataType = String.class,desc = "清理后的备份路径",editType = EditType.TEXT)
    private String backupPath;

    //清理时是否需要备份
    @JobParameter(name = "清理时是否需要备份",editType = EditType.BOOLEAN,dataType = Boolean.class)
    private boolean backup;

    //备份时是否要压缩
    @JobParameter(name = "备份时是否要压缩",editType = EditType.BOOLEAN,dataType = Boolean.class)
    private boolean zip;

    //压缩文件夹的时间格式
    @JobParameter(name = "压缩文件夹的时间格式",dataType = String.class,desc = "例如:yyyy-MM-dd",editType = EditType.TEXT)
    private String backupDateFormat;

    @JobParameter(name = "备份文件保存天数",dataType = Integer.class,desc = "推荐7天",editType = EditType.NUMBER)
    private int keepDays=7;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public boolean isBackup() {
        return backup;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public boolean isZip() {
        return zip;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }

    public String getBackupDateFormat() {
        return backupDateFormat;
    }

    public void setBackupDateFormat(String backupDateFormat) {
        this.backupDateFormat = backupDateFormat;
    }

    public int getKeepDays() {
        return keepDays;
    }

    public void setKeepDays(int keepDays) {
        this.keepDays = keepDays;
    }
}
