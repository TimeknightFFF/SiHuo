package com.sunshine.sihuo.beans;

/**
 * Created by Administrator on 2015/4/30.
 */
public class Banner {

    private String src;
    private String url;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "src='" + src + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
