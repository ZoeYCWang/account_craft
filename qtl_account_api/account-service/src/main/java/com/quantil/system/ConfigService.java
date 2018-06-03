package com.quantil.system;

import com.quantil.account.CustomizationViewModel;
import com.quantil.common.QueryParam;
import com.zoe.snow.crud.Result;

import java.util.List;
import java.util.Map;

/**
 * ConfigService
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/30
 */
public interface ConfigService {
    Result getConfigByName(String name, String token);

    Result updateConfigByName(String name, String dataType, CustomizationViewModel configModel, String token);

    /**
     * get system config by filters
     * @param queryParam
     *      query params are some general condition, such as below
     *      page: { page: 1, size: 1 }
     *          ---ps if page equals '-1' the return result page will be null
     *      order: { order: asc, sortBy: key}
     * @param filters
     *      filters are the properties of permission column
     *      filter by ids: [id1, id2]
     *      filter by urls: [url1, urls, ...]
     *      filter by methods: [method1, method2, ...]
     *      filter by types: [type1, type2, ...]
     *      so the filters format is
     *      {
     *          "keys":[]
     *      }
     *      or
     *      {
     *          "key":""
     *      }
     * @param token
     *      the token who invokes the interface, that must login firstly.
     * @return
     */
    Result list(QueryParam queryParam, Map<String, List<String>> filters, String token);

    Result get(String key, String token);

    Result add(ConfigViewModel configViewModel, String token);

    Result delete(String id, String key, String token);

    Result update(String id, ConfigViewModel configViewModel, String token);
}
