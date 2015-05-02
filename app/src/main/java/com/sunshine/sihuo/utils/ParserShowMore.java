package com.sunshine.sihuo.utils;

import com.sunshine.sihuo.beans.MoreBeans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/3.
 */
public class ParserShowMore {

    public static List<MoreBeans> listOfMore = new LinkedList<>();

    public static List<MoreBeans> getMoreList(String json){

        try {
            JSONArray array = new JSONObject(json).getJSONArray("d");
            MoreBeans more;

            for (int i = 0; i <array.length() ; i++) {
                more = new MoreBeans();
                JSONObject object = array.getJSONObject(i);

                more.setTitle(object.getString("name"));
                more.setImage(object.getString("icon"));

                List<String> listOfName=new ArrayList<>();
                JSONArray jsonArray = object.getJSONArray("child");

                for (int j = 0; j <jsonArray.length() ; j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                    listOfName.add(jsonObject.getString("name"));
                }

                if(listOfName.size()>0){
                    more.setNames(listOfName);
                }

                listOfMore.add(more);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(listOfMore.size()>0){
            return listOfMore;
        }else {
            return null;
        }
    }
}
