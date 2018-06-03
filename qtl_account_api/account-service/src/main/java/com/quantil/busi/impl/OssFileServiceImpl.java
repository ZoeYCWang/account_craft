package com.quantil.busi.impl;


import com.quantil.busi.api.OssFileService;
import com.quantil.common.client.OssClient;
import com.quantil.common.map.StringMapUtil;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.context.aop.annotation.Statistics;
import com.zoe.snow.crud.Result;
import com.zoe.snow.message.Reply;
import com.zoe.snow.util.Generator;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/23.
 */

@Component("craft.service.impl.oss.file")
public class OssFileServiceImpl implements OssFileService {
    @Override
    @NoNeedVerify
    @Statistics
    public Result putFile(String fileName, InputStream inputStream, String key,String token) {
        return Result.reply(() ->{
            String path = "craft/" + key + "/" + Generator.uuid() + "/" +  fileName;
            OssClient.putImg(path,inputStream);
            path =  OssClient.baseOssUrl + path;
            return StringMapUtil.createMap().addKeyValue("name",fileName).addKeyValue("url",path).toGenerate();
        });
    }
}
