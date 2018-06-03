package com.quantil.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zoe.snow.model.Validatable;

import javax.persistence.Column;

/**
 * CreateAccountViewModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/21
 */
public class CreateAccountViewModel implements Validatable{
    @JSONField(name = Description.USERNAME)
    private String username;
    private String email;
    @Column(name = Description.P_STR)
    private String password;
    //@JSONField(name = Description.APP_ID)
    private String appId;

    @JsonProperty(Description.ROLE_ID)
    @JSONField(name = Description.ROLE_ID)
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
