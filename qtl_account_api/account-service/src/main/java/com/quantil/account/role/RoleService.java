package com.quantil.account.role;

import com.quantil.account.UpdateUserViewModel;
import com.quantil.common.QueryParam;
import com.zoe.snow.auth.service.BaseRoleService;
import com.zoe.snow.crud.Result;

import java.util.List;
import java.util.Map;

/**
 * RoleService
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/15
 */
public interface RoleService extends BaseRoleService {

    Result addRole(CreateRoleViewModel roleModel, String token);

    Result updateRole(String id, UpdateRoleViewModel roleModel, String token);

    Result listRole(QueryParam queryParam, String token);

    Result delRole(String id, String token);

    Result getRole(String id, String token);
}
