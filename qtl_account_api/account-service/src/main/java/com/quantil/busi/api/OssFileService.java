package com.quantil.busi.api;

import com.zoe.snow.crud.Result;

import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/23.
 */
public interface OssFileService {
    Result putFile(String fileName, InputStream inputStream, String key, String token);
}
