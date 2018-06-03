package com.quantil.busi.vo;

/**
 * Created by Administrator on 2018/6/1.
 */
public class CreateCompanyViewModel implements ViewModel {

    private String name;    //名称

    private String companyDescription;    //公司简介描述

    private String companyAddr;    //公司地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }
}
