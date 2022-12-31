package com.uwei.uwproject.bean;

/**
 * @Author Charlie
 * @Date 2022/8/19 14:49
 */
public class VersionBean {

    private int id;
    private String releaseVersion;
    private int buildVersion;
    private String note;
    private String url;

    public int getId() {
        return id;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public int getBuildVersion() {
        return buildVersion;
    }

    public String getNote() {
        return note;
    }

    public String getUrl() {
        return url;
    }
}
