package com.quantil.busi.model;

import com.quantil.busi.desc.CodeInfoDesc;
import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.annotation.Property;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author
 * @Description 标准代码信息表
 * @date 2018-6-3 1:39:50
 */
@Entity
@Table(name = CodeInfoDesc.m_code_info_name)
@Component("com.craft.entity.CodeInfo")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Property(name = CodeInfoDesc.m_code_info_property)
public class CodeInfoModel extends BaseModelSupport {
    private String name;    //名称

    private String value;    //值

    private String dictCode;    //字典名称

    private String description;    //备注描述

    private String isUse;    //是否可用，0：关闭使用，1：可以使用


    @Jsonable
    @Column(name = CodeInfoDesc.name_name)
    @Property(name = CodeInfoDesc.name_property)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = CodeInfoDesc.value_name)
    @Property(name = CodeInfoDesc.value_property)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Jsonable
    @Column(name = CodeInfoDesc.dict_code_name)
    @Property(name = CodeInfoDesc.dict_code_property)
    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    @Jsonable
    @Column(name = CodeInfoDesc.description_name)
    @Property(name = CodeInfoDesc.description_property)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Jsonable
    @Column(name = CodeInfoDesc.is_use_name)
    @Property(name = CodeInfoDesc.is_use_property)
    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

}