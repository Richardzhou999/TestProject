package com.uwei.uwproject.bean;

import java.math.BigDecimal;

/**
 * @Author Charlie
 * @Date 2022/7/30 18:16
 */
public class MemberBean {

    private String voice;
    private String voicePriceDesc;
    private String subProduct;
    private String itemName;
    private BigDecimal itemOriginalPrice;
    private BigDecimal remainRecharge;
    private String remainFlow;
    private String cardState;
    private String message;
    private String flow;
    private String messagePriceDesc;
    private String remainVoice;

    public String getVoice() {
        return voice;
    }

    public String getVoicePriceDesc() {
        return voicePriceDesc;
    }

    public String getSubProduct() {
        return subProduct;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemOriginalPrice() {
        return itemOriginalPrice;
    }

    public BigDecimal getRemainRecharge() {
        return remainRecharge;
    }

    public String getRemainFlow() {
        return remainFlow;
    }

    public String getCardState() {
        return cardState;
    }

    public String getMessage() {
        return message;
    }

    public String getFlow() {
        return flow;
    }

    public String getMessagePriceDesc() {
        return messagePriceDesc;
    }

    public String getRemainVoice() {
        return remainVoice;
    }
}
