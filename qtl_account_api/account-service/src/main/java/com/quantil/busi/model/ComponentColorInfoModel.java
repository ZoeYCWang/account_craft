package com.quantil.busi.model;

import com.quantil.busi.desc.ComponentColorInfoDesc;
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
 * @Description 组成成分颜色信息
 * @date 2018-6-3 1:39:53
 */
@Entity
@Table(name = ComponentColorInfoDesc.m_component_color_info_name)
@Component("com.craft.entity.ComponentColorInfo")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Property(name = ComponentColorInfoDesc.m_component_color_info_property)
public class ComponentColorInfoModel extends BaseModelSupport {
    private String name;    //名称

    private String code;    //编码

    private Double unitPrice;    //单价

    private String process;    //加工处理

    private String photo;    //图片

    private String value;    //颜色值（十六进制）

    private String description;    //描述

    private String type;    //基本成分类型，0：铁，1：木板，2：玻璃，3：大理石，5皮垫

    private String isUse;    //是否可用，0：关闭使用，1：可以使用

    private String companyId;    //公司Id


    @Jsonable
    @Column(name = ComponentColorInfoDesc.name_name)
    @Property(name = ComponentColorInfoDesc.name_property)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.code_name)
    @Property(name = ComponentColorInfoDesc.code_property)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.unit_price_name)
    @Property(name = ComponentColorInfoDesc.unit_price_property)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.process_name)
    @Property(name = ComponentColorInfoDesc.process_property)
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.photo_name)
    @Property(name = ComponentColorInfoDesc.photo_property)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.value_name)
    @Property(name = ComponentColorInfoDesc.value_property)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.description_name)
    @Property(name = ComponentColorInfoDesc.description_property)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.type_name)
    @Property(name = ComponentColorInfoDesc.type_property)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.is_use_name)
    @Property(name = ComponentColorInfoDesc.is_use_property)
    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    @Jsonable
    @Column(name = ComponentColorInfoDesc.company_id_name)
    @Property(name = ComponentColorInfoDesc.company_id_property)
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

}