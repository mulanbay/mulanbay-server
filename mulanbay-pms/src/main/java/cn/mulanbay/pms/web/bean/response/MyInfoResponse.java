package cn.mulanbay.pms.web.bean.response;

public class MyInfoResponse {

    private String username;
    private String nickname;
    /**
     * 系统版本号
     */
    private String version;

    /**
     * 今日行程数
     */
    private int todayCalendars = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getTodayCalendars() {
        return todayCalendars;
    }

    public void setTodayCalendars(int todayCalendars) {
        this.todayCalendars = todayCalendars;
    }
}
