package com.quantil.common;

import java.util.ArrayList;
import java.util.List;

/**
 * ListViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/20
 */
public class ListViewModel {
    private List<Object> data = new ArrayList<>();

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
