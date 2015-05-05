package com.sunshine.sihuo.urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class Kind_Url {

    public static List<String> urlStr = new ArrayList<>();
    public static List<String> imgStr = new ArrayList<>();
    public static List<String> MoreStr=new ArrayList<>();

    public static String Phone_Url = "http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&category=1&sort=pub_ts_desc&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String Photo_Url = "http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&category=2&sort=pub_ts_desc&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String Computer_Url = "http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&category=3&sort=pub_ts_desc&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String Trans_Url = "http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&distance=asc&category=11&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String Watch_Url = "http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&category=18&sort=pub_ts_desc&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String Shore_Url = "http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&category=6&sort=pub_ts_desc&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String High_Url = "http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&category=5&sort=pub_ts_desc&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String More_Url = "http://www.sihuo.com/?c=category&a=getList&format=tree&v=1.3.4&os=android&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";
    public static String Image1_Url = "http://www.sihuo.com/?c=Goods&a=getClassList&tag=qm_goods_jzsh_list&top=0&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";

    public static String Image3_Url = "http://www.sihuo.com/?c=Goods&a=getClassList&tag=qm_goods_zkx_list&top=0&trust=1&token=MTQzMDM3NzY1OHyE0XI7FWUxEPWeeQxjsJHt%2F0daV5keLSSt28KOx9T6EQ%3D%3D&Dalvik/1.6.0";
    public static String Image4_Url = "http://www.sihuo.com/?c=Goods&a=getClassList&tag=qm_goods_bcj_list&top=0&token=MTQzMDM3NzY1OHyE0XI7FWUxEPWeeQxjsJHt%2F0daV5keLSSt28KOx9T6EQ%3D%3D&Dalvik/1.6.0";
    public static String Image2_Url = "http://www.sihuo.com/?c=Goods&a=getClassList&tag=qm_goods_mmbb_list&top=0&token=MTQzMDM3NzY1OHyE0XI7FWUxEPWeeQxjsJHt%2F0daV5keLSSt28KOx9T6EQ%3D%3D&Dalvik/1.6.0";
    public static String Image5_Url = "http://www.sihuo.com/?c=Goods&a=getClassList&tag=qm_goods_qxth_list&top=0&token=MTQzMDM3NzY1OHyE0XI7FWUxEPWeeQxjsJHt%2F0daV5keLSSt28KOx9T6EQ%3D%3D&Dalvik/1.6.0";
    static{
        for ( int i=1;i<=20;i++){

         String More_Info="http://www.sihuo.com/?c=search&v=1.3.4&os=android&limit=20&offset=0&category="+i+"&sort=pub_ts_desc&token=MTQzMDM2MTE5OXy8JmoibOy65tPvK%2BU%2FsXDgU7Jv2xhCDL4JpsI5pzayjg%3D%3D&Dalvik/1.6.0";
            MoreStr.add(More_Info);
        }

    }

    static {
        urlStr.add(Phone_Url);
        urlStr.add(Photo_Url);
        urlStr.add(Computer_Url);
        urlStr.add(Trans_Url);
        urlStr.add(Watch_Url);
        urlStr.add(Shore_Url);
        urlStr.add(High_Url);
        urlStr.add(More_Url);
        imgStr.add(Image1_Url);
        imgStr.add(Image2_Url);
        imgStr.add(Image3_Url);
        imgStr.add(Image4_Url);
        imgStr.add(Image5_Url);
    }
}
