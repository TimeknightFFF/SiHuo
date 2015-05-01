package com.sunshine.sihuo.utils;

import android.util.Log;

import com.sunshine.sihuo.beans.Banner;
import com.sunshine.sihuo.beans.Hot_category;
import com.sunshine.sihuo.beans.Special_topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/4/30.
 */
public class Parser_Find_list {

    //  把数据放到HashMap里面
    private static HashMap<String, List> map = new HashMap<>();

    public static HashMap<String, List> parserList(String json) {

        try {
            // 获取object 对象
            JSONObject object = new JSONObject(json).getJSONObject("d");

            // TODO 获取第一个数据集合
            JSONArray banners = object.getJSONArray("banner");
            List<Banner> listOfBan = new ArrayList<>();
            Banner banner;
            for (int i = 0; i < banners.length(); i++) {
                banner = new Banner();
                JSONObject jsonObject = banners.getJSONObject(i);

                banner.setSrc(jsonObject.getString("src"));
                banner.setUrl(jsonObject.getString("url"));

                listOfBan.add(banner);
            }

            if (listOfBan.size() > 0) {
                map.put("banner", listOfBan);
            }

            // TODO 获取第二个集合数据

            JSONArray hot_categorys = object.getJSONArray("hot_category");
            List<Hot_category> listOfHot = new ArrayList<>();
            Hot_category hot;
            for (int i = 0; i < hot_categorys.length(); i++) {
                hot = new Hot_category();
                JSONObject jsonObject = hot_categorys.getJSONObject(i);

                hot.setIcon(jsonObject.getString("icon"));
                hot.setName(jsonObject.getString("name"));

                listOfHot.add(hot);
            }

            if (listOfHot.size() > 0) {
                map.put("hot_category", listOfHot);
            }


            // TODO 获取第三组的数据
            JSONArray topics = object.getJSONArray("special_topic");
            List<Special_topic> listOfTopic = new ArrayList<>();
            Special_topic topic;
            for (int i = 0; i < topics.length(); i++) {
                topic = new Special_topic();
                JSONObject jsonObject = topics.getJSONObject(i);

                topic.setName(jsonObject.getString("name"));
                topic.setIcon(jsonObject.getString("icon"));
                topic.setUrl(jsonObject.getString("url"));

                listOfTopic.add(topic);
            }
            if(listOfTopic.size()>0){
                map.put("special_topic",listOfTopic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(map.size()>0){
            return map;
        }else {
            return null;
        }
    }
}
