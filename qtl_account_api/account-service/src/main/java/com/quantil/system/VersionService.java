package com.quantil.system;

import com.zoe.snow.crud.Result;

/**
 * VersionService
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/8/24
 */
public interface VersionService {
    Result get(String token);
}
