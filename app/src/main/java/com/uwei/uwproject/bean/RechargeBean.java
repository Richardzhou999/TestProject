package com.uwei.uwproject.bean;

/**
 * @Author Charlie
 * @Date 2022/7/21 11:30
 */
public class RechargeBean {

    private int id;
    private int money;
    private boolean select;

    public RechargeBean(int id,int money, boolean select) {
        this.id = id;
        this.money = money;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
