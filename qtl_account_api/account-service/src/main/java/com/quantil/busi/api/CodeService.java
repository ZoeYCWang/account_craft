package com.quantil.busi.api;

import com.quantil.busi.model.CodeInfoModel;
import com.zoe.snow.crud.Result;

import java.util.List;

/**
 * Created by Administrator on 2018/6/3.
 */
public interface CodeService {
    Result getCodeInfoList(String dictCode,String token);
}
