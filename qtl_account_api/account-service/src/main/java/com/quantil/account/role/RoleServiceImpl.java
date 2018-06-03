package com.quantil.account.role;

import com.quantil.account.AccountService;
import com.quantil.account.Token;
import com.quantil.common.QueryParam;
import com.zoe.snow.Global;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.model.support.user.role.BasePermissionSupport;
import com.zoe.snow.model.support.user.role.BaseRoleModel;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * RoleServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/15
 */
@Service("account.role.service")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private CrudService crudService;
    @Autowired
    private AccountService accountService;

    @Override
    public Set<BasePermissionSupport> findPermissions(String roleId) {
        return null;
    }

    @Override
    @NoNeedVerify(NoNeedEffectiveness = true)
    public BaseRoleModel findRole(String accountId) {
        Account2RoleModel account2RoleModel = crudService.query().from(Account2RoleModel.class).where("accountId", accountId).one();
        if (account2RoleModel != null) {
            return crudService.query().from(RoleModel.class).where("id", account2RoleModel.getRoleId()).one();
        }
        return null;
    }

    @Override
    @NoNeedVerify(NoNeedEffectiveness = true)
    public List<BaseRoleModel> findRoles(String accountId) {
        return null;
    }

    @Override
    @Transactional
    public Result addRole(CreateRoleViewModel role, String token) {
        return Result.reply(() -> {
            Token loginUser = accountService.verify(token).getData();
            if (role == null)
                return Message.BadRequest.setArgs("role must be not empty.");
            if (Validator.isEmpty(role.getName()))
                return Message.BadRequest.setArgs("role name must be not empty");
            if (role.getName().equals(Global.Constants.auth.ROOT))
                return Message.UnAuthorized;
            // boolean re = false;
            changeDefaultRole(role);
            /*
             * if (!re) return
             * Message.Error.setArgs("when set default role occur the problem."
             * );
             */

            RoleModel roleModel = crudService.query().from(RoleModel.class).where("name", role.getName()).one();
            if (roleModel != null)
                return Message.BadRequest.setArgs("role name is exit, please change another.");
            roleModel = new RoleModel();
            roleModel.setAsDefault(role.as_default);
            roleModel.setName(role.getName());
            roleModel.setCreatedAt(new Date());
            roleModel.setCreatedBy(loginUser.getUid());
            roleModel.setValidFlag(1);
            roleModel.setDescription(role.getDescription());
            roleModel.setId(Generator.random(32));
            crudService.save(roleModel, InterventionType.INSERT);
            return roleModel;
        });
    }

    @Override
    @Transactional
    public Result updateRole(String id, UpdateRoleViewModel role, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("role id must be not empty");
            // super admin role can't be modify
            if (role.getName().equals(Global.Constants.auth.ROOT))
                return Message.UnAuthorized;
            if (role == null)
                return Message.BadRequest.setArgs("role must be not empty.");
            if (Validator.isEmpty(role.getName()))
                return Message.BadRequest.setArgs("role name must be not empty");
            RoleModel roleModel = crudService.query().from(RoleModel.class).where("id", role.getId()).where("name", role.getName(), Operator.Or).one();
            if (roleModel == null)
                return Message.BadRequest.setArgs("role id is not correct.");
            List<RoleModel> roleModels = crudService.query().from(RoleModel.class).where("id", Criterion.NotEqual, id).where("name", role.getName()).list();
            if (roleModels.size() > 0)
                return Message.BadRequest.setArgs("role name is exit, please change another");

            changeDefaultRole(role);

            roleModel.setAsDefault(role.isAs_default());
            roleModel.setName(role.getName());
            roleModel.setDescription(role.getDescription());
            roleModel.setUpdatedAt(new Date());

            crudService.save(roleModel, InterventionType.UPDATE);
            return roleModel;
        });
    }

    private void changeDefaultRole(CreateRoleViewModel role) {
        if (role.isAs_default()) {
            RoleModel defaultRole = crudService.query().from(RoleModel.class).where("asDefault", true).one();
            if (defaultRole != null) {
                defaultRole.setAsDefault(false);
                crudService.save(defaultRole, InterventionType.UPDATE);
            }
        }
    }

    @Override
    public Result listRole(QueryParam queryParam, String token) {
        return Result.reply(() -> {
            QueryProxy queryProxy = crudService.query().from(RoleModel.class).where("name", Criterion.NotEqual, Global.Constants.auth.ROOT)
                    .paging(queryParam.getPage(), queryParam.getSize()).order(queryParam.getSortBy(), queryParam.getOrder());
            if (queryParam.getPage() == -1)
                return queryProxy.list();
            else
                return queryProxy.pageList();
        });
    }

    @Override
    @Transactional
    public Result delRole(String id, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("role id must be not null");
            // find if role is using
            RoleModel roleModel = crudService.query().from(RoleModel.class).where("id", id).one();
            if (roleModel != null) {
                if (roleModel.getName().equals(Global.Constants.auth.ROOT))
                    return Message.UnAuthorized;
                if (roleModel == null)
                    return Message.BadRequest.setArgs("role id is not correct");
                List<Account2RoleModel> account2RoleModelList = crudService.query().from(Account2RoleModel.class).where("roleId", id).list();
                if (account2RoleModelList.size() > 0)
                    return Message.BadRequest.setArgs("the role is using, can't be delete, please remove from users");
                if (roleModel.getAsDefault())
                    return Message.BadRequest.setArgs("the role is set as default and can't be delete, please migrate default to another role firstly");
                return crudService.execute().delete(RoleModel.class, id);
            }
            return true;
        });
    }

    @Override
    public Result getRole(String id, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("role id must be not null");
            RoleModel roleModel = crudService.query().from(RoleModel.class).where("id", id).one();
            return roleModel;
        });
    }

    /*
     * @Override public List<RoleModel> findRoles(String accountId,String token)
     * { return null; }
     */
}
