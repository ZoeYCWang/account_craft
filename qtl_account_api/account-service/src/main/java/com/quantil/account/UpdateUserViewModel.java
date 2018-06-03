package com.quantil.account;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.model.Validatable;

/**
 * UpdateUserViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/28
 */
public class UpdateUserViewModel implements Validatable{
    private String email;
    //@JSONField(name = Description.APP_ID)
    //@JsonProperty(Description.APP_ID)
    //private String appId;

    @JSONField(name = Description.DESC)
    private String description;

    private JSONObject custom;

    public JSONObject getCustom() {
        return custom;
    }

    public void setCustom(JSONObject custom) {
        this.custom = custom;
    }

    /*public String getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
