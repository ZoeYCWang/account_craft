package com.quantil.busi.impl;

import com.quantil.busi.api.CompanyInfoService;
import com.quantil.busi.model.CompanyInfoModel;
import com.quantil.busi.model.ComponentInfoModel;
import com.quantil.busi.vo.CreateCompanyViewModel;
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
import java.util.concurrent.ConcurrentHashMap;

/**
* @author 
* @date 2018-5-20 11:39:56
*/
@Service("com.craft.service.CompanyInfo.impl")
public class CompanyInfoServiceImpl extends SuportServiceImpl<CompanyInfoModel> implements CompanyInfoService {

    private Map<String,String> idNameMapCache = new ConcurrentHashMap<>();

    @Autowired
    private CrudService crudService;


    @Override
    public String getNameById(String id) {
        return null;
    }

    @Override
    public void preAddProcess(CompanyInfoModel companyInfoModel, String token) {

    }

    @Override
    public void preUpdateProcess(CompanyInfoModel companyInfoModel, String token) {

    }

    @Override
    public List afterQueryProcess(List<CompanyInfoModel> list, String token) {
        return null;
    }

    @Override
    public void afterFindByIdProcess(CompanyInfoModel companyInfoModel, String token) {

    }

    @Override
    public void preQueryProcess(Map<String, ValueInfo> map, QueryProxy queryProxy, String token) {

    }
}
