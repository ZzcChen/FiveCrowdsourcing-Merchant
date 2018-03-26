package com.example.administrator.fivecrowdsourcing_merchant.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/10.
 */

public class DeliveryOrder implements Serializable {
    private Long delorderid;//配送单id
    private Long merchantid;//商户id
    private Long delmethodid;//配送方式id
    private Long estimatedtime;//预估时间
    private Double estimatedtotalprice;//预估总价格
    private int distance;//距离
    private String ordertime;//下单时间
    private Long runnerid;//跑腿人id
    private String intime;//接单时间
    private String outtime;//送达时间
    private Double extraprice;//加价费
    private Double trueweight;//真实质量
    private int creditpoints;//信用分
    private int integral;//积分
    private String cusName;//客户名
    private String cusPhone;//客户电话
    private String cusAddress;//客户地址
    private String things;//内容
    private int status;//状态：1：新建；2：待抢单；3：待取单；4：配送中；5：配送完成
    private double cuslat;//客户纬度
    private double cuslog;//客户经度
    private String storeName;//商店名
    private  String storeAddress;//商店地址
    private double storeLat;//商店纬度
    private double storeLog;//商店经度
    private double runlat;// 跑腿人纬度
    private double runlon;// 跑腿人经度
    private String runName;//跑腿人姓名
    private String runPhone;//跑腿人电话

    public DeliveryOrder() {
    }

    public DeliveryOrder(Long delorderid, Long merchantid, Long delmethodid, Long estimatedtime, Double estimatedtotalprice, int distance, String ordertime, Long runnerid, String intime, String outtime, Double extraprice, Double trueweight, int creditpoints, int integral, String cusName, String cusPhone, String cusAddress, String things, int status) {
        this.delorderid = delorderid;
        this.merchantid = merchantid;
        this.delmethodid = delmethodid;
        this.estimatedtime = estimatedtime;
        this.estimatedtotalprice = estimatedtotalprice;
        this.distance = distance;
        this.ordertime = ordertime;
        this.runnerid = runnerid;
        this.intime = intime;
        this.outtime = outtime;
        this.extraprice = extraprice;
        this.trueweight = trueweight;
        this.creditpoints = creditpoints;
        this.integral = integral;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusAddress = cusAddress;
        this.things = things;
        this.status = status;
    }

    public Long getDelorderid() {
        return delorderid;
    }

    public void setDelorderid(Long delorderid) {
        this.delorderid = delorderid;
    }

    public Long getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Long merchantid) {
        this.merchantid = merchantid;
    }

    public Long getDelmethodid() {
        return delmethodid;
    }

    public void setDelmethodid(Long delmethodid) {
        this.delmethodid = delmethodid;
    }

    public Long getEstimatedtime() {
        return estimatedtime;
    }

    public void setEstimatedtime(Long estimatedtime) {
        this.estimatedtime = estimatedtime;
    }

    public Double getEstimatedtotalprice() {
        return estimatedtotalprice;
    }

    public void setEstimatedtotalprice(Double estimatedtotalprice) {
        this.estimatedtotalprice = estimatedtotalprice;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Long getRunnerid() {
        return runnerid;
    }

    public void setRunnerid(Long runnerid) {
        this.runnerid = runnerid;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public Double getExtraprice() {
        return extraprice;
    }

    public void setExtraprice(Double extraprice) {
        this.extraprice = extraprice;
    }

    public Double getTrueweight() {
        return trueweight;
    }

    public void setTrueweight(Double trueweight) {
        this.trueweight = trueweight;
    }

    public int getCreditpoints() {
        return creditpoints;
    }

    public void setCreditpoints(int creditpoints) {
        this.creditpoints = creditpoints;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCuslat() {
        return cuslat;
    }

    public void setCuslat(double cuslat) {
        this.cuslat = cuslat;
    }

    public double getCuslog() {
        return cuslog;
    }

    public void setCuslog(double cuslog) {
        this.cuslog = cuslog;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public double getStoreLat() {
        return storeLat;
    }

    public void setStoreLat(double storeLat) {
        this.storeLat = storeLat;
    }

    public double getStoreLog() {
        return storeLog;
    }

    public void setStoreLog(double storeLog) {
        this.storeLog = storeLog;
    }

    public double getRunlat() {
        return runlat;
    }

    public void setRunlat(double runlat) {
        this.runlat = runlat;
    }

    public double getRunlon() {
        return runlon;
    }

    public void setRunlon(double runlon) {
        this.runlon = runlon;
    }

    public String getRunName() {
        return runName;
    }

    public void setRunName(String runName) {
        this.runName = runName;
    }

    public String getRunPhone() {
        return runPhone;
    }

    public void setRunPhone(String runPhone) {
        this.runPhone = runPhone;
    }
}
