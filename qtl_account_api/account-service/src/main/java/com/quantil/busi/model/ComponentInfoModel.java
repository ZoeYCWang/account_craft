package com.quantil.busi.model;

import com.quantil.busi.desc.ComponentInfoDesc;
import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.annotation.Property;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * @author
 * @Description 基本组成成分材料基本信息表
 * @date 2018-6-3 1:39:54
 */
@Entity
@Table(name = ComponentInfoDesc.m_component_info_name)
@Component("com.craft.entity.ComponentInfo")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Property(name = ComponentInfoDesc.m_component_info_property)
public class ComponentInfoModel extends BaseModelSupport {
    private String name;    //材质/规格

    private String description;    //描述

    private String photo;    //图片

    private String type;    //基本成分类型：0：铁，1：木板，2：玻璃，3：大理石，4：纸箱,5皮垫

    private String isUse;    //是否使用，0：暂停使用，1：开启使用

    private Double unitPrice;    //单价

    private Double unitWeight;    //重量

    private Double thick;    //厚度

    private CompanyInfoModel companyInfoModel;


    @Jsonable
    @Column(name = ComponentInfoDesc.name_name)
    @Property(name = ComponentInfoDesc.name_property)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = ComponentInfoDesc.description_name)
    @Property(name = ComponentInfoDesc.description_property)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Jsonable
    @Column(name = ComponentInfoDesc.photo_name)
    @Property(name = ComponentInfoDesc.photo_property)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Jsonable
    @Column(name = ComponentInfoDesc.type_name)
    @Property(name = ComponentInfoDesc.type_property)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Jsonable
    @Column(name = ComponentInfoDesc.is_use_name)
    @Property(name = ComponentInfoDesc.is_use_property)
    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    @Jsonable
    @Column(name = ComponentInfoDesc.unit_price_name)
    @Property(name = ComponentInfoDesc.unit_price_property)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Jsonable
    @Column(name = ComponentInfoDesc.unit_weight_name)
    @Property(name = ComponentInfoDesc.unit_weight_property)
    public Double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }

    @Jsonable
    @Column(name = ComponentInfoDesc.thick_name)
    @Property(name = ComponentInfoDesc.thick_property)
    public Double getThick() {
        return thick;
    }

    public void setThick(Double thick) {
        this.thick = thick;
    }

    @Jsonable
    @JoinColumn(name = ComponentInfoDesc.company_id_name)
    @ManyToOne
    @Property(name = ComponentInfoDesc.company_id_property)
    public CompanyInfoModel getCompanyInfoModel() {
        return companyInfoModel;
    }

    public void setCompanyInfoModel(CompanyInfoModel companyInfoModel) {
        this.companyInfoModel = companyInfoModel;
    }
}