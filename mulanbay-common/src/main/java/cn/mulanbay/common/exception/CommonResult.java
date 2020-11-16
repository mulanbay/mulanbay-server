package cn.mulanbay.common.exception;

/**
 * 通用处理结果
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CommonResult {

    private int code = ErrorCode.SUCCESS;

    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
