package com.sunshine.sihuo.utils;

import com.sunshine.sihuo.beans.Index_Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class Parser_find_L {

    private static List<Index_Info> list=new ArrayList<>();

    public static List<Index_Info> getListInfo(String json){

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

                List<String> photos= (List<String>) object.get("photo");
                index.setPhoto(photos.get(0));

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
