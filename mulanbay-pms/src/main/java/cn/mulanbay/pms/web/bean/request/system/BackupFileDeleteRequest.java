package cn.mulanbay.pms.web.bean.request.system;

import javax.validation.constraints.NotEmpty;

public class BackupFileDeleteRequest {

    @NotEmpty(message = "{validate.backup.fileName.notEmpty}")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
