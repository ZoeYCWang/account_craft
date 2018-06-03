package com.quantil.account.permission;

import com.zoe.snow.crud.Result;

/**
 * Permission2RoleService
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/23
 */
public interface Permission2RoleService {
    boolean ifInUsingPermission(String permissionId, String token);
}
