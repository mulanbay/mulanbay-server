package cn.mulanbay.ai.ml.dataset.bean;

/**
 * 模型文件
 *
 * @author fenghong
 * @create 2023-06-21
 */
public class ModuleFile {

    /**
     * 唯一标识代码
     */
    private String code;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 是否区分用户
     */
    private Boolean du;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getDu() {
        return du;
    }

    public void setDu(Boolean du) {
        this.du = du;
    }
}
