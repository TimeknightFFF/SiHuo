package com.sunshine.sihuo.utils;

import com.sunshine.sihuo.beans.Index_Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/5/1.
 */
public class Parser_find_Kind {

    private static List<Index_Info> list=new ArrayList<>();

    public static List<Index_Info> getListInfo(String json){

        if(list.size()>0){
            list.clear();
        }

        try {
            JSONArray array = new JSONObject(json).getJSONObject("d").getJSONArray("list");

            Index_Info index;
            for (int i = 0; i <array.length() ; i++) {
                index=new Index_Info();
                JSONObject object = array.getJSONObject(i);

                index.setAvatar(object.getString("avatar"));
                index.setDesc(object.getString("desc"));
                index.setOri_price(object.getString("ori_price"));
                index.setPrice(object.getString("price"));
                index.setuName(object.getString("uName"));

                index.setcNum(object.getInt("cNum"));
                index.setpNum(object.getInt("pNum"));
                Object photo = object.get("photo");


                String urls = photo.toString();
                /**
                 * 分离出URL
                 */
                String url=urls.substring(urls.indexOf("/"),urls.indexOf("jpg")+3);
                Pattern compile = Pattern.compile("\\\\");
                Matcher matcher = compile.matcher(url);
                String photoUrl = matcher.replaceAll("");
                index.setPhoto(photoUrl);

                list.add(index);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(list.size()>0){
            return list;
        }else {
            return null;
        }
    }
}
