package com.quantil.common.map;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/20.
 */
public class StringMapUtil {

    public static MapProxy createMap(){
        return new MapProxy();
    }

    public static void main(String [] args){
        Map<String,Object> map = StringMapUtil.createMap().addKeyValue("dd","dfa").addKeyValue("fadsf","fadsf").toGenerate();
        System.out.print(map);
    }
}
