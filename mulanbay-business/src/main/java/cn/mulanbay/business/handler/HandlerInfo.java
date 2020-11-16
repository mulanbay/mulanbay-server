package cn.mulanbay.business.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 * Handler详情
 *
 * @author fenghong
 * @create 2017-10-19 9:50
 **/
public class HandlerInfo {

    private String handlerName;

    private List<HandlerInfoDetail> details;

    public HandlerInfo(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public List<HandlerInfoDetail> getDetails() {
        return details;
    }

    public void setDetails(List<HandlerInfoDetail> details) {
        this.details = details;
    }

    /**
     * 增加明细
     * @param key
     * @param value
     */
    public void addDetail(String key,String value){
        if (details == null) {
            details = new ArrayList<>();
        }
        HandlerInfoDetail hie = new HandlerInfoDetail(key,value);
        details.add(hie);
    }

}
