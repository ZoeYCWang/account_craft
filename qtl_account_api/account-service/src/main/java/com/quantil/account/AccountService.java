package com.quantil.account;

import com.quantil.account.role.RoleModel;
import com.quantil.common.QueryParam;
import com.zoe.snow.auth.Authentication;
import com.zoe.snow.auth.Local;
import com.zoe.snow.crud.Result;

/**
 * AccountService
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/27
 */
public interface AccountService extends Authentication, Local, UserService {
    Result getUser(String userId, String token);

    Result addUser(CreateAccountViewModel createUser, String token);

    Result delUser(String id, String token);

    Result exist(String username, String token);

    /**
     * administrator and reset someone password
     *
     * @param id
     * @param token
     * @return
     */
    Result resetUserPassword(String id, String token);

    Result listUser(QueryParam queryParam, String token);

    Result updateAccount(String id, UpdateAccountViewModel account, String token);

    Result listStatus(String token);

    Result addRole(String accountId, RoleModel roleModel, String token);

    Result updateRole(String accountId, String roleId, String token);

    Result getAccount(String accountId, String token);

}
