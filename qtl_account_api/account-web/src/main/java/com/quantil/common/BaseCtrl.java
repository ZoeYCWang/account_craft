package com.quantil.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zoe.snow.Global;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.context.response.Response;
import com.zoe.snow.context.session.Session;
import com.zoe.snow.crud.Result;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * BaseCtrl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/31
 */
public class BaseCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected Session session;
    @Autowired
    protected Response response;

    protected String getToken() {
        // String token = request.getHeader(Constants.AUTHORIZATION);
        // return request.getHeader();;

        String token = request.getHeader("Authorization");
        if (Validator.isEmpty(token))
            return "";

        String[] ts = token.split("\\s+");
        if (ts.length > 1)
            return ts[1];
        else
            return token;
    }

    protected Object reply(Result result) {
        if (Validator.isNumeric(result.getCode())) {
            if (result.getCode().startsWith("400"))
                response.setStatusCode(400);
            if (result.getCode().startsWith("200"))
                response.setStatusCode(200);
            else
                response.setStatusCode(Converter.toInt(result.getCode()));
        }

        return result;
    }

    protected Map<String, List<String>> filter(String filters) {
        JSONObject jsonObject = null;
        String backFilters = filters;
        try {
            filters = new String(Base64Utils.decodeFromString(filters));
            jsonObject = JSON.parseObject(filters);
        } catch (Exception e) {
            filters = backFilters;
        }
        Map<String, List<String>> map = new HashMap<>();
        if (filters != null) {
            if (jsonObject != null) {
                jsonObject.forEach((k, v) -> {
                    if (v instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) v;
                        List<String> values = new ArrayList<>();// jsonArray.toJavaList(String.class);
                        jsonArray.forEach(c -> {
                            if (Validator.isEmpty(c))
                                values.add(Global.Constants.STRING_NONE);
                            else
                                values.add(String.valueOf(c));
                        });
                        map.put(k, values);
                    } else if (v instanceof JSONObject) {
                        List<String> values = new ArrayList<>();
                        values.add(Global.Constants.STRING_NONE);
                        map.put(k, values);
                    } else {
                        List<String> values = new ArrayList<>();
                        if (Validator.isEmpty(v))
                            values.add(Global.Constants.STRING_NONE);
                        else
                            values.add(String.valueOf(v));
                        map.put(k, values);
                    }
                });
            } else {
                List<String> values = new ArrayList<>();
                values.add(Global.Constants.STRING_NONE);
                map.put("id", values);
            }
        }
        return map;
    }

    protected MultipartFile getMultiFile(HttpServletRequest r) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(r.getSession().getServletContext());
        //设置编码
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        //判断 request 是否有文件上传,即多部分请求...
        if (commonsMultipartResolver.isMultipart(r)) {
            //转换成多部分request
            MultipartHttpServletRequest multipartRequest =
                    commonsMultipartResolver.resolveMultipart(r);

            // file 是指 文件上传标签的 name=值
            // 根据 name 获取上传的文件...
            MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
            System.out.println(map);
            if (Validator.isEmpty(map) || map.size() == 0) {
                return null;
            }
            System.out.print(map.size());
            Set<String> sets = map.keySet();
            System.out.print(sets.iterator().next());
            String name = sets.iterator().next();
            return map.getFirst(name);
        }
        return null;
    }
}
