package com.uwei.uwproject.bean;

import java.util.ArrayList;

/**
 * @Author Charlie
 * @Date 2022/7/29 19:00
 */
public class MemberListBean {


    private String title;
    private ArrayList<MemberItemBean> beans;

    public MemberListBean(String title, ArrayList<MemberItemBean> beans) {
        this.title = title;
        this.beans = beans;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<MemberItemBean> getBeans() {
        return beans;
    }

    public void setBeans(ArrayList<MemberItemBean> beans) {
        this.beans = beans;
    }
}
