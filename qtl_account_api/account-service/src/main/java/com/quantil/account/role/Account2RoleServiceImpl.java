package com.quantil.account.role;

import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Account2RoleServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/26
 */
@Service("account.2role.service")
public class Account2RoleServiceImpl implements Account2RoleService {
    @Autowired
    private CrudService crudService;

    @Override
    public Result getAccount2Role(String accountId, String roleId) {
        return Result.reply(() -> {
            if (Validator.isEmpty(accountId) || Validator.isEmpty(roleId))
                return null;
            return crudService.query().from(Account2RoleModel.class)
                    .where("accountId", accountId)
                    .where("roleId", roleId).one();
        });
    }
}
