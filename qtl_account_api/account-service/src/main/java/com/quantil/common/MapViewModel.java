package com.quantil.common;

import java.util.HashMap;
import java.util.Map;

/**
 * MapViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/22
 */
public class MapViewModel<T> {
    private Map<String, T> data = new HashMap<>();

    public Map<String, T> getData() {
        return data;
    }

    public void setData(Map<String, T> data) {
        this.data = data;
    }
}
