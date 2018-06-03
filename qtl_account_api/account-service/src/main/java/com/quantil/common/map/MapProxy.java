package com.quantil.common.map;

import com.zoe.snow.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/20.
 */
public class MapProxy {

    Map<String,Object> map = new HashMap<>();
    Map<String,ValueInfo> strMap = new HashMap<>();

    public MapProxy addKeyValue(String key,Object value){
        if (!Validator.isEmpty(key)){
            map.put(key,value);
        }
        return this;
    }

    public MapProxy addKeyValueInfo(String key,Object value,boolean isValigue){
        if (!Validator.isEmpty(key)){
            ValueInfo valueInfo = new ValueInfo(value,isValigue);
            strMap.put(key,valueInfo);
        }
        return this;
    }

    public Map<String,Object> toGenerate(){
        return map;
    }

    public Map<String,ValueInfo> toGenerateInfo(){
        return strMap;
    }
}
