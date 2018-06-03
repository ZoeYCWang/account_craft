package com.quantil.busi.impl;

import com.quantil.busi.api.CodeHelp;
import com.quantil.busi.api.CodeService;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.crud.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/6/3.
 */
@Component("com.quantil.busi.code.service")
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeHelp codeHelp;

    @Override
    @NoNeedVerify
    public Result getCodeInfoList(String dictCode,String token) {
        return Result.reply(()-> codeHelp.getCodeInfoList(dictCode)
        );
    }
}
