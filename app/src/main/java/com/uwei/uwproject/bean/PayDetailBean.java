package com.uwei.uwproject.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author Charlie
 * @Date 2022/7/25 15:43
 */
public class PayDetailBean implements Serializable {
    private static final long serialVersionUID=1L;
    private int id;
    private String name;
    private String picUrl;
    private BigDecimal unitPrice;
    private BigDecimal deduction;
    private BigDecimal originalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }



    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
}
