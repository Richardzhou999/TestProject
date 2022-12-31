package com.uwei.uwproject.bean;

/**
 * @Author Charlie
 * @Date 2022/7/22 16:48
 */
public class MealDetailBean {

    private int id;
    private int productId;
    private int mealPrice;
    private int domesticFlow;
    private int domesticCallDuration;
    private boolean select;

    public MealDetailBean(int id, int productId, int mealPrice, int domesticFlow, int domesticCallDuration, boolean select) {
        this.id = id;
        this.productId = productId;
        this.mealPrice = mealPrice;
        this.domesticFlow = domesticFlow;
        this.domesticCallDuration = domesticCallDuration;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(int mealPrice) {
        this.mealPrice = mealPrice;
    }

    public int getDomesticFlow() {
        return domesticFlow;
    }

    public void setDomesticFlow(int domesticFlow) {
        this.domesticFlow = domesticFlow;
    }

    public int getDomesticCallDuration() {
        return domesticCallDuration;
    }

    public void setDomesticCallDuration(int domesticCallDuration) {
        this.domesticCallDuration = domesticCallDuration;
    }

    @Override
    public String toString() {
        return "MealDetailBean{" +
                "mealPrice=" + mealPrice +
                ", domesticFlow=" + domesticFlow +
                ", domesticCallDuration=" + domesticCallDuration +
                '}';
    }
}
