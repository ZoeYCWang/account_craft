package com.quantil.busi.model;

import com.quantil.busi.desc.CompanyInfoDesc;
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
 * @Description 公司基本信息表
 * @date 2018-6-3 1:39:52
 */
@Entity
@Table(name = CompanyInfoDesc.m_company_info_name)
@Component("com.craft.entity.CompanyInfo")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Property(name = CompanyInfoDesc.m_company_info_property)
public class CompanyInfoModel extends BaseModelSupport {
    private String name;    //名称

    private String companyDescription;    //公司简介描述

    private String companyAddr;    //公司地址

    private String isUse;    //是否可用，0：关闭使用，1：可以使用


    @Jsonable
    @Column(name = CompanyInfoDesc.name_name)
    @Property(name = CompanyInfoDesc.name_property)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = CompanyInfoDesc.company_description_name)
    @Property(name = CompanyInfoDesc.company_description_property)
    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    @Jsonable
    @Column(name = CompanyInfoDesc.company_addr_name)
    @Property(name = CompanyInfoDesc.company_addr_property)
    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    @Jsonable
    @Column(name = CompanyInfoDesc.is_use_name)
    @Property(name = CompanyInfoDesc.is_use_property)
    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

}