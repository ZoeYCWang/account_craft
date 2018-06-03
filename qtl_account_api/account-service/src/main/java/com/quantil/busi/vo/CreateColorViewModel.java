package com.quantil.busi.vo;

import org.springframework.web.servlet.View;

/**
 * Created by Administrator on 2018/6/1.
 */
public class CreateColorViewModel implements ViewModel{
    private String name;    //名称

    private Double unitPrice;    //单价

    private String process;    //加工处理

    private String photo;    //图片

    private String description;    //描述

    private String type;    //基本成分类型，0：铁，1：木板，2：玻璃，3：大理石，5皮垫

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
