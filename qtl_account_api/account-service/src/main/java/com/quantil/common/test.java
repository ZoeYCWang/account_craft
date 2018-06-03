package com.quantil.common;

import com.zoe.snow.util.Generator;

/**
 * Created by Administrator on 2018/6/3.
 */
public class test {
    public static void main(String[] args) {
        for(int index = 0; index < 10;index++){
            System.out.println(Generator.uuid());
        }
    }
}
