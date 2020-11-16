package cn.mulanbay.pms.persistent.dto;

import java.math.BigInteger;

public class RoleFunctionDto {

    private BigInteger functionId;

    private String functionName;

    private String functionShortName;

    private BigInteger pid;

    private BigInteger roleFunctionId;

    public BigInteger getFunctionId() {
        return functionId;
    }

    public void setFunctionId(BigInteger functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionShortName() {
        return functionShortName;
    }

    public void setFunctionShortName(String functionShortName) {
        this.functionShortName = functionShortName;
    }

    public BigInteger getPid() {
        return pid;
    }

    public void setPid(BigInteger pid) {
        this.pid = pid;
    }

    public BigInteger getRoleFunctionId() {
        return roleFunctionId;
    }

    public void setRoleFunctionId(BigInteger roleFunctionId) {
        this.roleFunctionId = roleFunctionId;
    }
}
