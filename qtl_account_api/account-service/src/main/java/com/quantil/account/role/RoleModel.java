package com.quantil.account.role;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quantil.common.Notes;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.annotation.Unique;
import com.zoe.snow.model.enums.IdStrategy;
import com.zoe.snow.model.support.UserAtBy;
import com.zoe.snow.model.support.ValidFlag;
import com.zoe.snow.model.support.user.role.BaseRoleModel;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * RoleModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/27
 */
@Entity
@Table(name = "role")
@Component("account.role.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RoleModel implements BaseRoleModel, Model, UserAtBy, ValidFlag {

    private static final String UUID = "uuid";
    @JSONField(name = com.quantil.account.Description.ID)
    @Column(name = com.quantil.account.Description.ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    private String id;

    @Column(name = Description.NAME)

    private String name;
    @Column(name = Description.DESCRIPTION)
    private String description;
    @JSONField(name = Notes.DELETED, serialize = false)
    @Column(name = Notes.DELETED)
    private int deleted;
    @JSONField(name = Notes.CREATED_AT, serialize = false)
    @Column(name = Notes.CREATED_AT)
    private Date createdAt;
    @Column(name = Notes.CREATED_BY)
    @JSONField(name = Notes.CREATED_BY, serialize = false)
    private String createdBy;

    @JSONField(name = Notes.UPDATED_AT, serialize = false)
    @Column(name = Notes.UPDATED_AT)
    private Date updatedAt;
    @JSONField(name = Notes.UPDATED_BY, serialize = false)
    @Column(name = Notes.UPDATED_BY)
    private String updatedBy;

    @JSONField(name = Description.PARENT_ID)
    @Column(name = Description.PARENT_ID)
    private String parentId;

    @JSONField(name = Description.CODE)
    @Column(name = Description.CODE)
    private String code;

    @JSONField(name = Description.AS_DEFAULT)
    @Column(name = Description.AS_DEFAULT)
    @JsonProperty(Description.AS_DEFAULT)
    private boolean asDefault;

    public boolean getAsDefault() {
        return asDefault;
    }

    public void setAsDefault(boolean asDefault) {
        this.asDefault = asDefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JSONField(serialize = false)
    @Override
    public int getValidFlag() {
        return deleted;
    }

    @Override
    public void setValidFlag(int validFlag) {
        this.deleted = validFlag;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @JSONField(serialize = false)
    @Override
    public String getValidFlagName() {
        return "deleted";
    }

    public void setValidFlagName(String validFlagName) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
