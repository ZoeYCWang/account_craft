package com.quantil.account;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.quantil.account.role.RoleModel;
import com.zoe.snow.model.support.user.role.BaseRoleModel;

import java.io.Serializable;

/**
 * Token
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/25
 */
public class Token implements Serializable {
    private String appid;
    private String uid;
    @JSONField(name = "expired_in")
    private long expiredIn;
    private String token;
    private String username;

    @JSONField(name = "role")
    private RoleModel roleModel;

    private String email;
    private String description;
    @JSONField(name = "is_admin")
    private boolean isAdmin;
    @JSONField(name = "is_super_admin")
    private boolean isSuperAdmin;
    private JSONObject custom;

    public JSONObject getCustom() {
        return custom;
    }

    public void setCustom(JSONObject custom) {
        this.custom = custom;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(long expiredIn) {
        this.expiredIn = expiredIn;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }
}
