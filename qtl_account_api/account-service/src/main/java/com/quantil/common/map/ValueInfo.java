package com.quantil.common.map;

/**
 * Created by Administrator on 2018/5/20.
 */
public class ValueInfo {
    private Object value;
    private boolean isValigue;

    public ValueInfo(Object value, boolean isValigue) {
        this.value = value;
        this.isValigue = isValigue;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isValigue() {
        return isValigue;
    }

    public void setValigue(boolean valigue) {
        isValigue = valigue;
    }
}
