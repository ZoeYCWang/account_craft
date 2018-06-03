package com.quantil.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * ConfigModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/30
 */
@Entity
@Table(name = "system_config")
@Component("quantil.system.config.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConfigModel implements Model, ValidFlag, UserAtBy {
    @JSONField(name = com.quantil.account.Description.ID)
    @Column(name = com.quantil.account.Description.ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    private String id;

    @Column(name = Description.NAME)
    @JSONField(name = Description.KEY)
    private String name;

    @Column(name = "value")
    @JSONField(serialize = false)
    private String value;

    @JSONField(name = "value")
    @JsonProperty("value")
    @Transient
    private Object jsonValue;

    @Column(name = "data_type")
    @JSONField(name = "data_type")
    private String dataType;

    @JSONField(name = com.quantil.account.Description.DESC)
    @Column(name = com.quantil.account.Description.DESC)
    private String description;

    @JSONField(name = com.quantil.account.Description.DELETED)
    @Column(name = com.quantil.account.Description.DELETED)
    private int deleted;

    @JSONField(name = com.quantil.account.Description.CREATED_AT)
    @Column(name = com.quantil.account.Description.CREATED_AT)
    private Date createdAt;

    @Column(name = com.quantil.account.Description.CREATED_BY)
    @JSONField(name = com.quantil.account.Description.CREATED_BY)
    private String createdBy;

    @JSONField(name = com.quantil.account.Description.UPDATED_AT)
    @Column(name = com.quantil.account.Description.UPDATED_AT)
    private Date updatedAt;

    @JSONField(name = com.quantil.account.Description.UPDATED_BY)
    @Column(name = com.quantil.account.Description.UPDATED_BY)
    private String updatedBy;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getJsonValue() {
        if (this.value != null)
            if (dataType.equals("json"))
                return (Object) JSONArray.parse(this.value.toString());
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
}
