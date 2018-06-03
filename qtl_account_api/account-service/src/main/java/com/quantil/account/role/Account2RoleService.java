package com.quantil.account.role;

import com.zoe.snow.crud.Result;

/**
 * Account2RoleService
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/26
 */
public interface Account2RoleService {
    Result getAccount2Role(String accountId, String roleId);
}
