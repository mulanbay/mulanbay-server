package cn.mulanbay.pms.web.bean.response.health;

import cn.mulanbay.common.util.BeanCopy;
import cn.mulanbay.pms.persistent.domain.TreatDrug;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: fenghong
 * @Create : 2021/3/5
 */
public class TreatDrugCalendarVo {

    private String name;

    private int startHour;

    private int endHour;

    public TreatDrugCalendarVo() {
    }

    public TreatDrugCalendarVo(String name, int startHour, int endHour) {
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    /**
     * 用药列表
     */
    private List<TreatDrugCalendarDetailVo> detailList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public List<TreatDrugCalendarDetailVo> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TreatDrugCalendarDetailVo> detailList) {
        this.detailList = detailList;
    }

    private TreatDrugCalendarDetailVo getDetailVo(Long treatDrugId){
        for(TreatDrugCalendarDetailVo vo : detailList){
            if(vo.getTreatDrugId().longValue()==treatDrugId.longValue()){
                return vo;
            }
        }
        return null;
    }

    /**
     * 新增记录
     * @param td
     */
    public void addTreatDrug(TreatDrug td){
        TreatDrugCalendarDetailVo vo = new TreatDrugCalendarDetailVo();
        BeanCopy.copyProperties(td,vo);
        vo.setTreatDrugId(td.getId());
        detailList.add(vo);
    }

    /**
     * 添加明细
     * @param treatDrugDetailId
     * @param occurTime
     */
    public boolean appendDetail(Long treatDrugDetailId, Date occurTime,Long treatDrugId ){
        TreatDrugCalendarDetailVo vo = this.getDetailVo(treatDrugId);
        if(vo!=null){
            vo.setOccurTime(occurTime);
            vo.setTreatDrugDetailId(treatDrugDetailId);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 添加明细
     * @param treatDrugDetailId
     * @param occurTime
     */
    public void addDetail(TreatDrug td,Long treatDrugDetailId, Date occurTime,Long treatDrugId ){
        TreatDrugCalendarDetailVo vo = new TreatDrugCalendarDetailVo();
        BeanCopy.copyProperties(td,vo);
        vo.setTreatDrugId(td.getId());
        vo.setOccurTime(occurTime);
        vo.setTreatDrugDetailId(treatDrugDetailId);
        vo.setMatch(false);
        detailList.add(vo);
    }
}
