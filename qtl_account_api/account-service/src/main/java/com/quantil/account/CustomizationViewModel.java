package com.quantil.account;

import com.zoe.snow.model.Validatable;

/**
 * CustomizationViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/29
 */
public class CustomizationViewModel  implements Validatable{
    private String custom;

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }
}
