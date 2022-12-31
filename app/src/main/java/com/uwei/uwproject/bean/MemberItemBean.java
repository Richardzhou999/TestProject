package com.uwei.uwproject.bean;

/**
 * @Author Charlie
 * @Date 2022/7/29 19:00
 */
public class MemberItemBean {

    private int imageUrl;
    private String subTitle;
    private String content;

    public MemberItemBean(int imageUrl, String subTitle, String content) {
        this.imageUrl = imageUrl;
        this.subTitle = subTitle;
        this.content = content;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


}
