package com.quantil.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.quantil.account.role.RoleModel;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.IdStrategy;
import com.zoe.snow.model.support.UserAtBy;
import com.zoe.snow.model.support.ValidFlag;
import com.zoe.snow.model.support.user.BaseUserModel;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * AccountModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/26
 */
@Entity
@Table(name = "account")
@Component("quantil.account.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccountModel implements Model, ValidFlag, UserAtBy, BaseUserModel {
    @JSONField(name = Description.ID)
    @Column(name = Description.ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    private String id;

    @Column(name = Description.USERNAME)
    @JSONField(name = Description.USERNAME)
    private String username;

    @Column(name = Description.EMAIL)
    private String email;

    @Column(name = Description.COMPANY_ID)
    private String companyId;

    @JSONField(name = Description.P_STR, serialize = false)
    @Column(name = Description.P_STR)
    private String password;

    @JSONField(name = "role")
    @Transient
    private RoleModel roleModel;

    @Column(name = Description.STATUS)
    // @JSONField(name = Description.STATUS, serialize = false)
    private int status;

    @JSONField(name = Description.LAST_LOGIN_AT)
    @Column(name = Description.LAST_LOGIN_AT)
    private Date lastLoginAt;

    @Column(name = Description.LAST_LOGIN_IP)
    @JSONField(name = Description.LAST_LOGIN_IP)
    private String lastLoginIp;

    @JSONField(name = Description.DESC)
    @Column(name = Description.DESC)
    private String description;

    @JSONField(name = Description.DELETED)
    @Column(name = Description.DELETED)
    private int deleted;

    @JSONField(name = Description.CREATED_AT)
    @Column(name = Description.CREATED_AT)
    private Date createdAt;

    @Column(name = Description.CREATED_BY)
    @JSONField(name = Description.CREATED_BY)
    private String createdBy;

    @JSONField(name = Description.UPDATED_AT)
    @Column(name = Description.UPDATED_AT)
    private Date updatedAt;

    @JSONField(name = Description.UPDATED_BY)
    @Column(name = Description.UPDATED_BY)
    private String updatedBy;

    @Column(name = Description.TENANT_ID)
    // @JSONField(name = Description.APP_ID)
    private String appId;

    @Transient
    @JSONField(serialize = false)
    private String token;
    @Transient
    private boolean locked = false;

    @JSONField(serialize = false)
    private String customization;

    @Transient
    @JSONField(name = "custom")
    private JSONObject customizationObj;

    @Transient
    @JSONField(name = Description.STATUS_MSG)
    private String statusMessage;

    @Transient
    @JSONField(name = Description.IS_ADMIN)
    private boolean isAdmin;
    @Transient
    @JSONField(name = Description.IS_SUPER_ADMIN)
    private boolean isSuperAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public JSONObject getCustomizationObj() {
        return JSON.parseObject(this.getCustomization());
    }

    public String getCustomization() {
        return customization;
    }

    public void setCustomization(String customization) {
        this.customization = customization;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @JSONField(serialize = false)
    @Override
    public boolean getLocked() {
        return status == 1;
    }

    @Override
    public void setLocked(boolean locked) {
        this.status = locked ? 1 : 0;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    @JSONField(serialize = false)
    public boolean getIsAdmin() {
        return false;
    }

    @Override
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getStatusMessage() {
        return Enums.Status.get(this.status).getTypeString();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    @JSONField(serialize = false)
    public int getValidFlag() {
        return deleted;
    }

    @Override
    public void setValidFlag(int validFlag) {
        this.deleted = validFlag;
    }

    @Override
    @JSONField(serialize = false)
    public String getValidFlagName() {
        return "deleted";
    }

    @Override
    @JSONField(serialize = false)
    public String getDomain() {
        return this.appId;
    }

    @Override
    public void setDomain(String domain) {
        this.appId = domain;
    }

    @Override
    @JSONField(serialize = false)
    public String getDomainName() {
        return "appId";
    }
}
