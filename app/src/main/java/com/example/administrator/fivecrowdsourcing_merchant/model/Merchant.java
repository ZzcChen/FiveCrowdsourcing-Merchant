package com.example.administrator.fivecrowdsourcing_merchant.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/13.
 */

public class Merchant implements Serializable {
    private Long merchantid;//商户id
    private Long tofgid;//待配送物件种类id
    private String name;//联系人姓名
    private String idcardnumber;//联系人身份证号
    private String idcardphoto;//联系人身份证照片存储地址
    private String password;//密码
    private String storename;//店铺名
    private String phone;//电话
    private String address;//地址
    private String buslicensephoto;//工商营业执照存储地址
    private String foodbuslicensephoto;//食品经营许可证存储地址
    private Long margin;//保证金提交状态：1：提交；2：未提交
    private double longitude;//经度
    private double latitude;//纬度

    public Merchant() {
    }
    public Merchant(Long merchantid, Long tofgid, String name, String idcardnumber, String idcardphoto, String password, String storename, String phone, String address, String buslicensephoto, String foodbuslicensephoto, Long margin, double longitude, double latitude) {
        this.merchantid = merchantid;
        this.tofgid = tofgid;
        this.name = name;
        this.idcardnumber = idcardnumber;
        this.idcardphoto = idcardphoto;
        this.password = password;
        this.storename = storename;
        this.phone = phone;
        this.address = address;
        this.buslicensephoto = buslicensephoto;
        this.foodbuslicensephoto = foodbuslicensephoto;
        this.margin = margin;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Long merchantid) {
        this.merchantid = merchantid;
    }

    public Long getTofgid() {
        return tofgid;
    }

    public void setTofgid(Long tofgid) {
        this.tofgid = tofgid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcardnumber() {
        return idcardnumber;
    }

    public void setIdcardnumber(String idcardnumber) {
        this.idcardnumber = idcardnumber;
    }

    public String getIdcardphoto() {
        return idcardphoto;
    }

    public void setIdcardphoto(String idcardphoto) {
        this.idcardphoto = idcardphoto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuslicensephoto() {
        return buslicensephoto;
    }

    public void setBuslicensephoto(String buslicensephoto) {
        this.buslicensephoto = buslicensephoto;
    }

    public String getFoodbuslicensephoto() {
        return foodbuslicensephoto;
    }

    public void setFoodbuslicensephoto(String foodbuslicensephoto) {
        this.foodbuslicensephoto = foodbuslicensephoto;
    }

    public Long getMargin() {
        return margin;
    }

    public void setMargin(Long margin) {
        this.margin = margin;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
