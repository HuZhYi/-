package com.td.card.util;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//比较双方数据是否相同(不同则按照玩家1来)
public class SynGameData {

    public static int compareValue(int value1,int value2,String type){

        return value1 != value2 ? value1 : value2;
    }

    public static Map<String,Object> compareMapData(List<Map<String,Object>> data){
        return null;
    }


    public static Map<String,Object> jsonToMap(JSONObject msg){
        Map<String,Object> map = new HashMap<>();
        Iterator it = msg.keys();
        while (it.hasNext()){
            String key = (String) it.next();
            Object value = msg.get(key);
            if(value instanceof JSONObject)
            {
                jsonToMap(JSONObject.fromObject(value));
            }
            else {
                map.put(key, value);
            }
        }
        return map;
    }
}
