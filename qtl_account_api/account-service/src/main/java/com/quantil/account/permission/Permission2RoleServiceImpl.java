package com.quantil.account.permission;

import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Permission2RoleServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/23
 */
@Service("qtl.account.permission.2role.service")
public class Permission2RoleServiceImpl implements Permission2RoleService {
    @Autowired
    private CrudService crudService;

    @Override
    public boolean ifInUsingPermission(String permissionId, String token) {
        if (Validator.isEmpty(permissionId))
            return false;
        return crudService.query().from(Permission2RoleModel.class).where(Description.PERMISSION + "." + Description.ID, permissionId).list().size() > 0;
    }
}
