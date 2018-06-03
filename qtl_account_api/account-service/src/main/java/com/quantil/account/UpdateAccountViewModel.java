package com.quantil.account;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zoe.snow.model.Validatable;

/**
 * UpdateAccountViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/22
 */
public class UpdateAccountViewModel implements Validatable{
    //private String id;

    @JSONField(name = Description.USERNAME)
    private String username;
    private String email;


    //@JSONField(name = Description.APP_ID)
    //@JsonProperty(Description.APP_ID)
    //private String appId;

    @JSONField(name = Description.DESC)
    private String description;

    private int status;

    @JSONField(name = "role_id")
    @JsonProperty("role_id")
    private String roleId;

    private JSONObject custom;

    /*private JSONObject custom;

    public JSONObject getCustom() {
        return custom;
    }

    public void setCustom(JSONObject custom) {
        this.custom = custom;
    }*/

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /*public String getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }*/

    public JSONObject getCustom() {
        return custom;
    }

    public void setCustom(JSONObject custom) {
        this.custom = custom;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }*/
}
