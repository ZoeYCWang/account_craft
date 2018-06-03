package com.quantil.busi.impl;


import com.quantil.busi.api.ComponentColorInfoService;
import com.quantil.busi.model.ComponentColorInfoModel;
import com.quantil.busi.vo.CreateColorViewModel;
import com.quantil.common.map.ValueInfo;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author 
* @date 2018-5-20 22:29:06
*/
@Service("com.craft.service.ComponentColorInfo.impl")
public class ComponentColorInfoServiceImpl extends SuportServiceImpl<ComponentColorInfoModel> implements ComponentColorInfoService {


    @Override
    public void preAddProcess(ComponentColorInfoModel componentColorInfoModel, String token) {

    }

    @Override
    public void preUpdateProcess(ComponentColorInfoModel componentColorInfoModel, String token) {

    }

    @Override
    public List afterQueryProcess(List<ComponentColorInfoModel> list, String token) {
        return null;
    }

    @Override
    public void afterFindByIdProcess(ComponentColorInfoModel componentColorInfoModel, String token) {

    }

    @Override
    public void preQueryProcess(Map<String, ValueInfo> map, QueryProxy queryProxy, String token) {

    }
}
