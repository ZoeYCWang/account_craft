package com.quantil.busi.vo;

import com.quantil.busi.vo.annotation.MaxLength;
import com.quantil.busi.vo.annotation.Required;

/**
 * Created by Administrator on 2018/5/31.
 */
public class CreateComponentViewModel implements ViewModel{
    private String name;    //材质/规格

    private String description;    //描述

    private String photo;    //图片

    private String type;    //基本成分类型：0：铁，1：木板，2：玻璃，3：大理石，4：纸箱,5皮垫

    private Double unitPrice;    //单价

    private Double unitWeight;    //重量

    private Double thick;    //厚度

    @MaxLength
    @Required
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @MaxLength
    @Required
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @MaxLength
    @Required
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @MaxLength
    @Required
    public Double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }

    @MaxLength
    @Required
    public Double getThick() {
        return thick;
    }

    public void setThick(Double thick) {
        this.thick = thick;
    }
}
