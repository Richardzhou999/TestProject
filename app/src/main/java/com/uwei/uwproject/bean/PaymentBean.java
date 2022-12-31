package com.uwei.uwproject.bean;

/**
 * @Author Charlie
 * @Date 2022/7/26 11:50
 */
public class PaymentBean {

    private String appId;
    private String uid;
    private String iccid;
    private String itemId;
    private String operatorProductId;
    /**
     * 收货人
     */
    private String realName;
    /**
     * 收货人电话
     */
    private String userPhone;
    private String province;
    private String city;
    private String district;
    private String addressDetail;
    private String goodsDetail;
    /**
     * 是否分期 0：分期 1:不分期
     */
    private int handlingMethod;

    private String goodsType;
    private String payType;
    private String cardSn = "-";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOperatorProductId() {
        return operatorProductId;
    }

    public void setOperatorProductId(String operatorProductId) {
        this.operatorProductId = operatorProductId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public int getHandlingMethod() {
        return handlingMethod;
    }

    public void setHandlingMethod(int handlingMethod) {
        this.handlingMethod = handlingMethod;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }
}
