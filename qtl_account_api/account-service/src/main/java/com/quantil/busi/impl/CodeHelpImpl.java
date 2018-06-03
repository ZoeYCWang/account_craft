package com.quantil.busi.impl;

import com.quantil.busi.api.CodeHelp;
import com.quantil.busi.desc.CodeInfoDesc;
import com.quantil.busi.model.CodeInfoModel;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.log.Logger;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.OrderBy;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/6/2.
 */
@Service("com.quantil.busi.code.help")
public class CodeHelpImpl implements CodeHelp {
    @Autowired
    private CrudService crudService;

    private Map<String,String> map = new ConcurrentHashMap<>();
    private Integer load = 1;

    @Override
    public List<CodeInfoModel> getCodeInfoList(String dictCode) {
        QueryProxy queryProxy =crudService.query().from(CodeInfoModel.class);
        queryProxy.where(CodeInfoDesc.dict_code_field, Criterion.Equals,dictCode);
        queryProxy.order("sort", OrderBy.Asc);
        queryProxy.setExcludeDomain(true);
        return queryProxy.list();
    }

    @Override
    public Map<String, String> getCodeInfoMap(String dictCode) {
        List<CodeInfoModel> list = this.getCodeInfoList(dictCode);
        Map<String,String> map = new HashMap<>();
        if (!Validator.isEmpty(list)){
            for (CodeInfoModel codeInfoModel:list){
                String name = codeInfoModel.getName();
                String value = codeInfoModel.getValue();
                map.put(value,name);
            }
        }
        return map;
    }

    @Override
    public String getName(String dictCode,String value) {
        if (Validator.isEmpty(map) || load ==1){
            map = this.getCodeInfoMap(dictCode);
            load =2;
        }
        if (Validator.isEmpty(map)){
            Logger.info("dict" + dictCode + "not setting values");
            return null;
        }
        return map.get(value);
    }
}
