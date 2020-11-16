package cn.mulanbay.pms.handler.bean;

import cn.mulanbay.pms.persistent.domain.UserSharesScoreConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 股票评分配置
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class SharesScoreConfig implements Serializable {

    private static final long serialVersionUID = -2059527472326421167L;

    private List<SharesScoreConfigDetail> priceScoreConfigList = new ArrayList<>();

    private List<SharesScoreConfigDetail> cbPriceScoreConfigList = new ArrayList<>();

    private List<SharesScoreConfigDetail> assetScoreConfigList = new ArrayList<>();

    private List<SharesScoreConfigDetail> fgScoreConfigList = new ArrayList<>();

    private List<SharesScoreConfigDetail> ssScoreConfigList = new ArrayList<>();

    private List<SharesScoreConfigDetail> turnOverScoreConfigList = new ArrayList<>();

    private List<SharesScoreConfigDetail> riskScoreConfigList = new ArrayList<>();

    /**
     * 转换
     *
     * @param ussc
     * @return
     */
    public static SharesScoreConfig change(UserSharesScoreConfig ussc) {
        SharesScoreConfig ssc = new SharesScoreConfig();
        ssc.setPriceScoreConfigList(cale(ussc.getPriceScoreConfig()));
        ssc.setCbPriceScoreConfigList(cale(ussc.getCbPriceScoreConfig()));
        ssc.setAssetScoreConfigList(cale(ussc.getAssetScoreConfig()));
        ssc.setFgScoreConfigList(cale(ussc.getFgScoreConfig()));
        ssc.setSsScoreConfigList(cale(ussc.getSsScoreConfig()));
        ssc.setTurnOverScoreConfigList(cale(ussc.getTurnOverScoreConfig()));
        ssc.setRiskScoreConfigList(cale(ussc.getRiskScoreConfig()));
        return ssc;
    }

    public static List<SharesScoreConfigDetail> cale(String data) {
        List<SharesScoreConfigDetail> list = new ArrayList<>();
        String[] psc = data.split(",");
        for (String s : psc) {
            String[] s1 = s.split("=");
            SharesScoreConfigDetail sscd = new SharesScoreConfigDetail();
            sscd.setScore(Integer.valueOf(s1[1]));
            String[] s2 = s1[0].split(">>");
            if ("min".equalsIgnoreCase(s2[0])) {
                sscd.setMin(Double.MIN_VALUE);
            } else {
                sscd.setMin(Double.valueOf(s2[0]));
            }
            if ("max".equalsIgnoreCase(s2[1])) {
                sscd.setMax(Double.MAX_VALUE);
            } else {
                sscd.setMax(Double.valueOf(s2[1]));
            }
            list.add(sscd);
        }
        return list;
    }

    public int getPriceScore(double v) {
        for (SharesScoreConfigDetail sscd : priceScoreConfigList) {
            if (v >= sscd.getMin() && v < sscd.getMax()) {
                return sscd.getScore();
            }
        }
        return 0;
    }

    /**
     * 获取最大的价格评分
     *
     * @return
     */
    public int getMaxPriceScore() {
        int m = 0;
        for (SharesScoreConfigDetail sscd : priceScoreConfigList) {
            if (m < sscd.getScore()) {
                m = sscd.getScore();
            }
        }
        return m;
    }

    public int getCbPriceScore(double v) {
        for (SharesScoreConfigDetail sscd : cbPriceScoreConfigList) {
            if (v >= sscd.getMin() && v < sscd.getMax()) {
                return sscd.getScore();
            }
        }
        return 0;
    }

    /**
     * 获取最大的持续价格评分
     *
     * @return
     */
    public int getMaxCbPriceScore() {
        int m = 0;
        for (SharesScoreConfigDetail sscd : cbPriceScoreConfigList) {
            if (m < sscd.getScore()) {
                m = sscd.getScore();
            }
        }
        return m;
    }

    public int getAssetScore(double v) {
        for (SharesScoreConfigDetail sscd : assetScoreConfigList) {
            if (v >= sscd.getMin() && v < sscd.getMax()) {
                return sscd.getScore();
            }
        }
        return 0;
    }

    /**
     * 获取最大的评分
     *
     * @return
     */
    public int getMaxAssetScore() {
        int m = 0;
        for (SharesScoreConfigDetail sscd : assetScoreConfigList) {
            if (m < sscd.getScore()) {
                m = sscd.getScore();
            }
        }
        return m;
    }

    public int getFgScore(double v) {
        for (SharesScoreConfigDetail sscd : fgScoreConfigList) {
            if (v >= sscd.getMin() && v < sscd.getMax()) {
                return sscd.getScore();
            }
        }
        return 0;
    }

    /**
     * 获取最大的评分
     *
     * @return
     */
    public int getMaxFgScore() {
        int m = 0;
        for (SharesScoreConfigDetail sscd : fgScoreConfigList) {
            if (m < sscd.getScore()) {
                m = sscd.getScore();
            }
        }
        return m;
    }

    public int getSsScore(double v) {
        for (SharesScoreConfigDetail sscd : ssScoreConfigList) {
            if (v >= sscd.getMin() && v < sscd.getMax()) {
                return sscd.getScore();
            }
        }
        return 0;
    }

    /**
     * 获取最大的评分
     *
     * @return
     */
    public int getMaxSsScore() {
        int m = 0;
        for (SharesScoreConfigDetail sscd : ssScoreConfigList) {
            if (m < sscd.getScore()) {
                m = sscd.getScore();
            }
        }
        return m;
    }

    public int getTurnOverScore(double v) {
        for (SharesScoreConfigDetail sscd : turnOverScoreConfigList) {
            if (v >= sscd.getMin() && v < sscd.getMax()) {
                return sscd.getScore();
            }
        }
        return 0;
    }

    /**
     * 获取最大的评分
     *
     * @return
     */
    public int getMaxTurnOverScore() {
        int m = 0;
        for (SharesScoreConfigDetail sscd : turnOverScoreConfigList) {
            if (m < sscd.getScore()) {
                m = sscd.getScore();
            }
        }
        return m;
    }

    public int getRiskScore(double v) {
        for (SharesScoreConfigDetail sscd : riskScoreConfigList) {
            if (v >= sscd.getMin() && v <= sscd.getMax()) {
                return sscd.getScore();
            }
        }
        return 0;
    }

    /**
     * 获取最大的评分
     *
     * @return
     */
    public int getMaxRiskScore() {
        int m = 0;
        for (SharesScoreConfigDetail sscd : riskScoreConfigList) {
            if (m < sscd.getScore()) {
                m = sscd.getScore();
            }
        }
        return m;
    }

    public List<SharesScoreConfigDetail> getPriceScoreConfigList() {
        return priceScoreConfigList;
    }

    public void setPriceScoreConfigList(List<SharesScoreConfigDetail> priceScoreConfigList) {
        this.priceScoreConfigList = priceScoreConfigList;
    }

    public List<SharesScoreConfigDetail> getCbPriceScoreConfigList() {
        return cbPriceScoreConfigList;
    }

    public void setCbPriceScoreConfigList(List<SharesScoreConfigDetail> cbPriceScoreConfigList) {
        this.cbPriceScoreConfigList = cbPriceScoreConfigList;
    }

    public List<SharesScoreConfigDetail> getAssetScoreConfigList() {
        return assetScoreConfigList;
    }

    public void setAssetScoreConfigList(List<SharesScoreConfigDetail> assetScoreConfigList) {
        this.assetScoreConfigList = assetScoreConfigList;
    }

    public List<SharesScoreConfigDetail> getFgScoreConfigList() {
        return fgScoreConfigList;
    }

    public void setFgScoreConfigList(List<SharesScoreConfigDetail> fgScoreConfigList) {
        this.fgScoreConfigList = fgScoreConfigList;
    }

    public List<SharesScoreConfigDetail> getSsScoreConfigList() {
        return ssScoreConfigList;
    }

    public void setSsScoreConfigList(List<SharesScoreConfigDetail> ssScoreConfigList) {
        this.ssScoreConfigList = ssScoreConfigList;
    }

    public List<SharesScoreConfigDetail> getTurnOverScoreConfigList() {
        return turnOverScoreConfigList;
    }

    public void setTurnOverScoreConfigList(List<SharesScoreConfigDetail> turnOverScoreConfigList) {
        this.turnOverScoreConfigList = turnOverScoreConfigList;
    }

    public List<SharesScoreConfigDetail> getRiskScoreConfigList() {
        return riskScoreConfigList;
    }

    public void setRiskScoreConfigList(List<SharesScoreConfigDetail> riskScoreConfigList) {
        this.riskScoreConfigList = riskScoreConfigList;
    }
}
