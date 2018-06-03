package com.quantil.account.role;

import com.zoe.snow.model.annotation.Unique;
import com.zoe.snow.model.Validatable;

import javax.persistence.Column;

/**
 * CreateRoleViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/10/12
 */
public class CreateRoleViewModel implements Validatable{

    protected String name;

    protected String description;

    protected boolean as_default = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAs_default() {
        return as_default;
    }

    public void setAs_default(boolean as_default) {
        this.as_default = as_default;
    }
}
