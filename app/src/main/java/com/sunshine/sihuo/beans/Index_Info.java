package com.sunshine.sihuo.beans;

/**
 * Created by Administrator on 2015/5/1.
 */
public class Index_Info {

    private String avatar;
    private String uName;
    private String photo;
    private String price;
    private String ori_price;
    private String desc;

    private int pNum;
    private int cNum;
    private int goods_id;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOri_price() {
        return ori_price;
    }

    public void setOri_price(String ori_price) {
        this.ori_price = ori_price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    @Override
    public String toString() {
        return "Index_Info{" +
                "avatar='" + avatar + '\'' +
                ", uName='" + uName + '\'' +
                ", photo='" + photo + '\'' +
                ", price='" + price + '\'' +
                ", ori_price='" + ori_price + '\'' +
                ", desc='" + desc + '\'' +
                ", pNum=" + pNum +
                ", cNum=" + cNum +
                '}';
    }
}
