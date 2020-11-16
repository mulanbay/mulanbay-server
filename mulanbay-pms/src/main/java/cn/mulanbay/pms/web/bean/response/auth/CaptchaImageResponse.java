package cn.mulanbay.pms.web.bean.response.auth;

public class CaptchaImageResponse {

    private String uuid;

    private String img;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
