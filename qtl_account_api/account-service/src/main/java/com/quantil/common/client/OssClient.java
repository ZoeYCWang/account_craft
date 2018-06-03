package com.quantil.common.client;

import com.aliyun.oss.OSSClient;
import com.zoe.snow.util.Validator;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/22.
 */
public class OssClient {

    public static final String defaultPhotoAddr = "craft/default/11.jpg";
    public static final String baseOssUrl = "http://craft-test.oss-cn-beijing.aliyuncs.com/";
    /**
     *  endpoint
     */
    public static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";

    /**
     * accessKey请登录https://ak-console.aliyun.com/#/查看
     */
    public static final String accessKeyId = "LTAIyaX6ELhRYm2k";
    public static final String accessKeySecret = "uMqsdvuF15ZIFXbdgTiuVI794ZgLY1";

    private static OSSClient ossClient;

    /**
     * 上传图片
     * @param fileName 文件名加路径
     * @param localFile 本地路径
     */
    public static void putImg(String fileName,String localFile){
        if (Validator.isEmpty(ossClient)){
            ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        }
        // 上传文件
        ossClient.putObject("craft-test", fileName, new File(localFile));
    }

    public static void putImg(String fileName, InputStream inputStream){
        if (Validator.isEmpty(ossClient)){
            ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        }
        // 上传文件
        ossClient.putObject("craft-test", fileName, inputStream);
    }

    public static void main(String[] args) {
        try {
            putImg("craft/default/11.jpg", "D:\\11.jpg");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
