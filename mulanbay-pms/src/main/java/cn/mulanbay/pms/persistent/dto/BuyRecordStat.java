package cn.mulanbay.pms.persistent.dto;

public class BuyRecordStat {

    private Long totalCount;

    private Double totalShipment;

    private Double totalPrice;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Double getTotalShipment() {
        return totalShipment;
    }

    public void setTotalShipment(Double totalShipment) {
        this.totalShipment = totalShipment;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
