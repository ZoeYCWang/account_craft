package com.quantil.account;

import com.zoe.snow.model.Validatable;

/**
 * PasswordViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/25
 */
public class PasswordViewModel implements Validatable{
    private String old_password;
    private String new_password;

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
