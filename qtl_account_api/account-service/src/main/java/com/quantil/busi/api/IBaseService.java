package com.quantil.busi.api;


import com.quantil.busi.vo.ViewModel;
import com.quantil.common.map.ValueInfo;
import com.zoe.snow.crud.Result;
import com.zoe.snow.model.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/15.
 */
public interface IBaseService {
    public Result findById(String id, String token);

    public Result deleteById(String id, String token);

    public Result findCount(Map<String, ValueInfo> map, String token);

    public Result findByConditionList(Map<String, ValueInfo> map, int page, int size, String token);

    public <M extends ViewModel> Result add(M m, String token);

    public <M extends ViewModel> Result update(String id,M m, String token);
}
