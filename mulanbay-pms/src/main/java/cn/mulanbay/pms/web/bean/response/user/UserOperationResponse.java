package cn.mulanbay.pms.web.bean.response.user;

import cn.mulanbay.pms.persistent.enums.UserBehaviorType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserOperationResponse {

    private int id;

    private String title;

    private List<UserOperationVo> operations = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserOperationVo> getOperations() {
        return operations;
    }

    public void setOperations(List<UserOperationVo> operations) {
        this.operations = operations;
    }

    /**
     * 添加操作
     *
     * @param date
     * @param content
     */
    public void addUserOperation(Date date, String content, String title, UserBehaviorType behaviorType) {
        UserOperationVo uor = new UserOperationVo();
        uor.setOccurTime(date);
        uor.setContent(content);
        uor.setTitle(title);
        uor.setBehaviorType(behaviorType);
        operations.add(uor);
    }
}
