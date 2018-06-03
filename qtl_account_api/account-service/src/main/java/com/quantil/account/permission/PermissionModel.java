package com.quantil.account.permission;

import com.alibaba.fastjson.annotation.JSONField;
import com.quantil.common.Notes;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.IdStrategy;
import com.zoe.snow.model.support.UserAtBy;
import com.zoe.snow.model.support.ValidFlag;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * PermissionModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/22
 */
@Entity
@Table(name = "permission")
@Component("account.permission.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PermissionModel implements Model, UserAtBy, ValidFlag {
    @JSONField(name = Description.ID)
    @Column(name = Description.ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    private String id;

    @Column(name = Description.URL)
    @JSONField(name = Description.URL)
    private String url;

    @Column(name = Description.METHOD)
    private String method;
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

    @JSONField(name = Description.TYPE)
    @Column(name = Description.TYPE)
    private String type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @JSONField(serialize = false)
    @Override
    public String getValidFlagName() {
        return "deleted";
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
