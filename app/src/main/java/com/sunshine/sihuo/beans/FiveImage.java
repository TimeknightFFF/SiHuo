package com.sunshine.sihuo.beans;

/**
 * Created by PLJ on 2015/5/4.
 */
public class FiveImage {

    /**
     *             "key": "42414",
     "id": 42414,
     "num": 1,
     "lock_num": 0,
     "sys_ts": 1430725352,
     "photo": [
     "/photo/76/9b/b4/91/8ea9b2bc12a5aa03d350cc7dc7308392.jpg",
     "/photo/83/4e/38/71/3ba9ef68f8596c55294d87f5281fa1e0.jpg"
     ],
     "desc": "全新汽车车用抱枕子母被空调被，[爱心]此款车用子母被是宝妈们的神器！它不仅仅是抱枕，不仅仅是空调被，里面还附带一个小枕头哦，是不是超贴心[强]妈妈出门再不用大小包被出门，车上一个抱枕就全搞定[鼓掌]质量一级棒",
     "price": "92.00",
     "ori_price": "0.00",
     "loc": "浙江省温州市",
     "freight": "0.00",
     "audio": "",
     "audio_t": 0,
     "status": 1,
     "pub_ts": 1430545441,
     "avatar": "/photo/73/f1/b4/d7/e7e65c4ce093d1d096e8ab4a893c2518.jpg",
     "uid": 276562,
     "uName": "张如意",
     "relation": {
     "type": -1,
     "friend_uid": 276562,
     "friend_name": ""
     },
     "usage": 0,
     "isPraise": false,
     "isBoutique": 0,
     "pNum": 0,
     "cNum": 0
     */

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String price;


    private String avatar;


}
