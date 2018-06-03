package com.quantil.busi.api;

import com.quantil.busi.model.CodeInfoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/2.
 */
public interface CodeHelp {
    List<CodeInfoModel> getCodeInfoList(String dictCode);

    Map<String,String> getCodeInfoMap(String dictCode);

    String getName(String dictCode,String value);
}
