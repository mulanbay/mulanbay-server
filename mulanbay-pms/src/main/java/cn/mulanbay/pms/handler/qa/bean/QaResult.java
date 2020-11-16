package cn.mulanbay.pms.handler.qa.bean;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.pms.persistent.domain.QaConfig;

/**
 * Qa结果
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class QaResult {

    private int code = ErrorCode.SUCCESS;

    //回复内容
    private String res;

    private QaConfig qa;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public QaConfig getQa() {
        return qa;
    }

    public void setQa(QaConfig qa) {
        this.qa = qa;
    }
}
