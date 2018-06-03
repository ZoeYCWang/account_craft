package com.quantil.account.permission;

import com.quantil.account.AccountService;
import com.quantil.account.Token;
import com.quantil.account.role.Account2RoleModel;
import com.quantil.account.role.RoleModel;
import com.quantil.common.QueryParam;
import com.zoe.snow.Global;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.auth.PermissionBean;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PermissionServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/23
 */
@Service("qtl.account.permission.service")
public class PermissionServiceImpl implements PermissionService, PermissionAccessService {
    @Autowired
    private Permission2RoleService permission2RoleService;
    @Autowired
    private CrudService crudService;
    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public Result add(PermissionViewModel permissionViewModel, String token) {
        return Result.reply(() -> {
            Serializable x = check(permissionViewModel, null);
            if (x != null)
                return x;
            PermissionModel permissionModel;
            permissionModel = viewToModel(permissionViewModel, token);
            permissionModel.setId(Generator.random(32));
            crudService.save(permissionModel, InterventionType.INSERT);
            return permissionModel;
        });
    }

    private Message check(PermissionViewModel permissionViewModel, String exceptId) {
        if (permissionViewModel == null)
            return Message.BadRequest.setArgs("permission object must not be null");
        else if (Validator.isEmpty(permissionViewModel.getUrl()))
            return Message.BadRequest.setArgs("permission url must not be empty");
        else if (Validator.isEmpty(permissionViewModel.getType()))
            return Message.BadRequest.setArgs("permission typ must not be empty");
        if (exits(permissionViewModel, exceptId))
            return Message.BadRequest.setArgs("permission object is exits");
        return null;
    }

    private boolean exits(PermissionViewModel permissionViewModel, String exceptId) {
        return crudService.query().from(PermissionModel.class).where(Description.ID, Criterion.NotEqual, exceptId)
                .where(Description.METHOD, permissionViewModel.getMethod()).where(Description.URL, permissionViewModel.getUrl())
                .where(Description.TYPE, permissionViewModel.getType()).one() != null;
    }

    private PermissionModel viewToModel(PermissionViewModel permissionViewModel, String token) {
        PermissionModel permissionModel = new PermissionModel();
        Token loginUser = accountService.verify(token).getData();
        permissionModel.setCreatedAt(new Date());
        if (loginUser != null)
            permissionModel.setCreatedBy(loginUser.getUid());
        permissionModel.setDeleted(1);
        permissionModel.setDescription(permissionViewModel.getDescription());
        permissionModel.setMethod(permissionViewModel.getMethod());
        permissionModel.setType(permissionViewModel.getType());
        permissionModel.setUrl(permissionViewModel.getUrl());
        return permissionModel;
    }

    @Override
    public Result get(String id, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("permission id must not null.");
            PermissionModel permissionModel = crudService.query().from(PermissionModel.class).where("id", id).one();
            return permissionModel;
        });
    }

    @Override
    public Result list(QueryParam queryParam, Map<String, List<String>> filters, String token) {
        return Result.reply(() -> {
            QueryProxy queryProxy = crudService.query().from(PermissionModel.class);
            if (queryParam != null)
                queryProxy.paging(queryParam.getPage(), queryParam.getSize()).order(queryParam.getSortBy(), queryParam.getOrder());
            filter("", filters, queryProxy);
            if (queryParam.getPage() == -1 || queryParam.getSize() == -1)
                return queryProxy.list();
            return queryProxy.pageList();
        });
    }

    private void filter(String prefix, Map<String, List<String>> filters, QueryProxy queryProxy) {
        boolean flag = false;
        if (filters != null) {
            if (filters.size() > 0) {
                flag = true;
                if (!Validator.isEmpty(prefix))
                    prefix = MessageFormat.format("{0}.", prefix);
                if (filters.get(Description.IDS) != null) {
                    queryProxy.where(Description.ID, Criterion.In, String.join(",", filters.get(Description.IDS)));
                    flag = false;
                } else if (filters.get(Description.ID) != null) {
                    queryProxy.where(Description.ID, String.join(",", filters.get(Description.ID)));
                    flag = false;
                }
                // filter by url
                if (filters.get(Description.URLS) != null) {
                    queryProxy.where(prefix + Description.URL, Criterion.In, String.join(",", filters.get(Description.URLS)));
                    flag = false;
                } else if (filters.get(Description.URL) != null) {
                    queryProxy.where(prefix + Description.URL, String.join(",", filters.get(Description.URL)));
                    flag = false;
                }
                // filter by type
                if (filters.get(Description.TYPES) != null) {
                    queryProxy.where(prefix + Description.TYPE, Criterion.In, String.join(",", filters.get(Description.TYPES)));
                    flag = false;
                } else if (filters.get(Description.TYPE) != null) {
                    queryProxy.where(prefix + Description.TYPE, String.join(",", filters.get(Description.TYPE)));
                    flag = false;
                }
                // filter by method
                if (filters.get(Description.METHODS) != null) {
                    queryProxy.where(prefix + Description.METHOD, Criterion.In, String.join(",", filters.get(Description.METHODS)));
                    flag = false;
                } else if (filters.get(Description.METHOD) != null) {
                    queryProxy.where(prefix + Description.METHOD, String.join(",", filters.get(Description.METHOD)));
                    flag = false;
                }
                if (flag)
                    queryProxy.where(Description.ID, Global.Constants.STRING_NONE);
            }
        }
    }

    @Override
    public Result delete(String id, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("permission id must not be empty");
            if (permission2RoleService.ifInUsingPermission(id, token))
                return Message.BadRequest.setArgs("this permission is in using, can not be deleted");
            return crudService.deleteById(PermissionModel.class, id);
        });
    }

    @Override
    public Result update(String id, PermissionViewModel permissionViewModel, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("permission id must not be empty.");
            Serializable x = check(permissionViewModel, id);
            if (x != null)
                return x;
            PermissionModel permissionModel = crudService.query().from(PermissionModel.class).where(Description.ID, id).one();
            if (permissionModel == null)
                return Message.BadRequest.setArgs("permission id not correct.");
            permissionModel.setType(permissionViewModel.getType());
            permissionModel.setMethod(permissionViewModel.getMethod());
            permissionModel.setUrl(permissionViewModel.getUrl());
            permissionModel.setDescription(permissionViewModel.getDescription());
            Token loginUser = accountService.verify(token).getData();
            permissionModel.setCreatedAt(new Date());
            if (loginUser != null)
                permissionModel.setUpdatedBy(loginUser.getUid());
            crudService.save(permissionModel, InterventionType.UPDATE);
            return permissionModel;
        });
    }

    @Override
    @Transactional
    public Result assignRoleToPermission(String permissionId, List<String> roleIds, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(permissionId))
                return Message.MustNotEmpty.setArgs(Description.TIP_PERMISSION_ID);
            boolean re = true;
            crudService.execute().remove(Permission2RoleModel.class).where("permission.id", permissionId).invoke();
            if (roleIds != null) {
                for (String id : roleIds) {
                    // Permission2RoleModel permission2RoleModel =
                    // getPermission2RoleModel(id, permissionId);
                    /*
                     * if (permission2RoleModel != null) return
                     * Message.Exist.setArgs(Description.PERMISSION);
                     */
                    re = re && assign(permissionId, id);
                }
            }
            return re;
        });
    }

    @Transactional
    @Override
    public Result assignPermissionToRole(String roleId, List<String> permissionIds, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(roleId))
                return Message.MustNotEmpty.setArgs(Description.TIP_ROLE_ID);
            boolean re = true;
            crudService.execute().remove(Permission2RoleModel.class).where("role.id", roleId).invoke();
            if (permissionIds != null) {
                for (String id : permissionIds) {
                    /*
                     * if (permission2RoleModel != null) return
                     * Message.Exist.setArgs(Description.PERMISSION);
                     */
                    re = re && assign(id, roleId);
                }
            }
            return re;
        });
    }

    public boolean assign(String permissionId, String roleId) {
        Permission2RoleModel permission2RoleModel = new Permission2RoleModel();
        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setId(permissionId);
        RoleModel roleModel = new RoleModel();
        roleModel.setId(roleId);
        permission2RoleModel.setRole(roleModel);
        permission2RoleModel.setPermission(permissionModel);
        permission2RoleModel.setId(Generator.random(32));
        return crudService.save(permission2RoleModel, InterventionType.INSERT);
    }

    @Override
    public Result getPermissionsByRole(String roleId, Map<String, List<String>> filters, String token) {
        return Result.reply(() -> {
            Token loginUser = accountService.verify(token).getData();
            if (Validator.isEmpty(roleId))
                return Message.MustNotEmpty.setArgs(Description.TIP_ROLE_ID);
            QueryProxy queryProxy = crudService.query().from(Permission2RoleModel.class);

            filter(Description.PERMISSION, filters, queryProxy);
            List<Permission2RoleModel> role2PermissionResults = queryProxy.where("role.id", roleId).list();
            if (role2PermissionResults.size() == 0)
                return null;
            List<PermissionModel> permissionModels = new ArrayList<>();
            role2PermissionResults.forEach(c -> {
                if (c.getPermission() != null)
                    permissionModels.add(c.getPermission());
            });
            return permissionModels;
        });
    }

    @Override
    public Result getPermissionsByUser(String userId, Map<String, List<String>> filters, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(userId))
                return Message.MustNotEmpty.setArgs(Description.TIP_USER_ID);
            List<Account2RoleModel> roleModels = crudService.query().from(Account2RoleModel.class).where("accountId", userId).list();
            List<PermissionModel> permissionModels = new ArrayList<>();
            if (roleModels.size() > 0) {
                List<String> roleIds = roleModels.parallelStream().map(Account2RoleModel::getRoleId).collect(Collectors.toList());
                if (roleIds.size() < 1)
                    return null;
                // Token loginUser = accountService.verify(token).getData();
                QueryProxy queryProxy = crudService.query().from(Permission2RoleModel.class).where("role.id", Criterion.In, String.join(",", roleIds));
                filter(Description.PERMISSION, filters, queryProxy);
                PageList<Permission2RoleModel> permission2RoleModelPageList = queryProxy.pageList();
                permission2RoleModelPageList.forEach(c -> {
                    permissionModels.add(c.getPermission());
                });
                return permissionModels;
            }
            return null;
        });
    }

    @Override
    public Result getRoleByPermission(String permissionId, Map<String, List<String>> filters, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(permissionId))
                return Message.MustNotEmpty.setArgs(Description.TIP_PERMISSION_ID);
            Token loginUser = accountService.verify(token).getData();
            QueryProxy queryProxy = crudService.query().from(Permission2RoleModel.class).where("permission.id", permissionId);
            filter(Description.PERMISSION, filters, queryProxy);
            List<Permission2RoleModel> role2PermissionResults = queryProxy.list();
            if (role2PermissionResults.size() == 0)
                return null;
            List<RoleModel> roleModels = new ArrayList<>();
            role2PermissionResults.forEach(c -> {
                if (c.getPermission() != null)
                    roleModels.add(c.getRole());
            });
            return roleModels;
        });
    }

    @Override
    @NoNeedVerify
    public Result getPermissionsByToken(Map<String, List<String>> filters, String token) {
        Token loginUser = accountService.verify(token).getData();
        if (loginUser != null)
            return getPermissionsByUser(loginUser.getUid(), filters, token);
        return Result.reply(() -> null);
    }

    @Override
    @NoNeedVerify
    public Result verify(PermissionBean permissionBean, String token) {
        return Result.reply(() -> {
            Token loginUser = accountService.verify(token).getData();
            if (loginUser.isSuperAdmin())
                return true;
            if (Validator.isEmpty(permissionBean))
                return Message.MustNotEmpty.setArgs(Description.PERMISSION);
            if (Validator.isEmpty(permissionBean.getUrl()))
                return Message.MustNotEmpty.setArgs(Description.URL);
            if (Validator.isEmpty(permissionBean.getType()))
                return Message.MustNotEmpty.setArgs(Description.TYPE);
            if (Validator.isEmpty(permissionBean.getMethod()))
                return Message.MustNotEmpty.setArgs(Description.METHOD);

            if (loginUser == null)
                return Message.UnAuthorized;
            else if (loginUser.getRoleModel() == null)
                return Message.UnAuthorized;
            Permission2RoleModel permission2RoleModel = crudService.query().from(Permission2RoleModel.class).where("permission.url", permissionBean.getUrl())
                    .where("permission.method", permissionBean.getMethod()).where("permission.type", permissionBean.getType())
                    .where("role.id", loginUser.getRoleModel().getId()).one();
            if (permission2RoleModel != null)
                return true;
            else {
                Map<String, List<String>> map = new HashMap<>();
                List<String> types = new ArrayList<>();
                types.add(permissionBean.getType());
                map.put(Description.TYPE, types);
                List<String> methods = new ArrayList<>();
                methods.add(permissionBean.getMethod());
                map.put(Description.METHOD, methods);
                List<PermissionModel> permissionModels = (List<PermissionModel>) getPermissionsByToken(map, token).getData();
                for (PermissionModel p : permissionModels) {
                    if (permissionBean.getUrl().matches(p.getUrl()))
                        if (permissionBean.getMethod().toLowerCase().equals(p.getMethod().toLowerCase())
                                && permissionBean.getType().toLowerCase().equals(p.getType().toLowerCase()))
                            return true;
                }
            }
            return Message.UnAuthorized;
        });
    }
}
