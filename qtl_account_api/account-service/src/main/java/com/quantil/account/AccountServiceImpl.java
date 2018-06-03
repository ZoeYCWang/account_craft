package com.quantil.account;

import com.alibaba.fastjson.JSONObject;
import com.quantil.account.permission.PermissionAccessService;
import com.quantil.account.role.Account2RoleModel;
import com.quantil.account.role.RoleModel;
import com.quantil.account.role.RoleService;
import com.quantil.common.QueryParam;
import com.quantil.system.ConfigService;
import com.zoe.snow.Global;
import com.zoe.snow.auth.*;
import com.zoe.snow.auth.service.BaseUserService;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.cache.Cache;
import com.zoe.snow.cache.ExpirationWay;
import com.zoe.snow.conf.AuthenticationConf;
import com.zoe.snow.context.session.Session;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.json.DJson;
import com.zoe.snow.log.Logger;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.model.enums.Rules;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.Security;
import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

/**
 * AccountServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/27
 */
@Service("account.service")
public class AccountServiceImpl implements BaseUserService, AccountService {
    private static String existTip = "{0} exists, please change another.";
    @Autowired
    private CrudService crudService;
    @Autowired
    private AuthenticationConf conf;
    @Autowired
    private RoleService roleService;
    @Autowired
    private Session session;
    @Autowired
    private AccountOnStart accountOnStart;
    @Autowired
    private PermissionAccessService permissionAccessService;
    // @Qualifier("checker.default")
    private Checker checker = BeanFactory.getBean("checker.default");

    @Override
    public Result getUser(String userId, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(userId))
                return Message.BadRequest.setArgs("user id must not null.");
            return crudService.query().from(AccountModel.class).list();
        });
    }

    @Override
    @NoNeedVerify(NoNeedEffectiveness = true)
    @Transactional
    public Result login(String ip, AccountViewModel accountViewModel) {
        return Result.reply(() -> {
            if (accountViewModel == null)
                return Message.LoginError;
            if (Validator.isEmpty(accountViewModel.getUsername()) || Validator.isEmpty(accountViewModel.getPassword()))
                return Message.LoginError;
            String sessionKey = Security.md5(accountViewModel.getAppid() + accountViewModel.getUsername());

            // 先判断是否有被锁住
            if (lock(false))
                return Message.LoginLock;

            AccountModel accountModel = this.findAccount(accountViewModel.getUsername(), accountViewModel.getAppid());
            // 匹配验证
            if (accountModel != null) {
                accountModel.setToken(TokenProcessor.getInstance().generateToken(sessionKey, true));

                boolean matches = false;
                String sourcePassword = PasswordHelper.encryptPassword(accountModel.getUsername(), accountViewModel.getPassword());
                matches = sourcePassword.equals(accountModel.getPassword());

                if (!matches) {
                    if (lock(true))
                        return Message.LoginLock;
                    return Message.LoginError;
                }

            } else {
                if (lock(false))
                    return Message.LoginLock;
                return Message.LoginError;
            }
            if (accountModel.getLocked())
                return Message.LoginLock;
            // after executing
            accountModel.setLastLoginIp(ip);
            accountModel.setLastLoginAt(new Date());
            crudService.save(accountModel);
            session.put("##user", accountModel);
            /**/
            Token token = getToken(accountViewModel.getRememberMe(), accountModel);
            AuthenticationConf conf = BeanFactory.getBean(AuthenticationConf.class);
            long timeOut = conf.getAuthExpiredIn();
            if (accountViewModel.getRememberMe())
                timeOut = conf.getAuthExpiredRemember();
            Cache.getInstance().by("redis").put(accountModel.getToken(), token, ExpirationWay.AbsoluteTime, timeOut);
            return token;
        });
    }

    private boolean lock(boolean locked) {
        int count = 1;
        String sessionKey = session.getSessionId();
        Object element = Cache.getInstance().get(sessionKey);
        if (element != null) {
            count = Integer.parseInt(element.toString());
            if (locked)
                count++;
            Cache.getInstance().put(sessionKey, count, ExpirationWay.AbsoluteTime, 300000);
        } else {
            Cache.getInstance().put(sessionKey, count);
        }

        if (count > 5) {
            return true;
        }
        return false;
    }

    private Token getToken(boolean remember, AccountModel user) {
        Token token = new Token();
        token.setAppid(user.getAppId());
        token.setToken(user.getToken());
        token.setUid(user.getId());
        token.setDescription(user.getDescription());
        token.setEmail(user.getEmail());
        token.setUsername(user.getUsername());
        long timeout = conf.getAuthExpiredIn();
        token.setCustom(user.getCustomizationObj());
        if (remember)
            timeout = conf.getAuthExpiredRemember();
        token.setExpiredIn(timeout);
        token.setRoleModel((RoleModel) roleService.findRole(user.getId()));
        if (token.getRoleModel().getName().equals(Global.Constants.auth.ADMIN) || token.getRoleModel().getName().equals(Global.Constants.auth.ROOT))
            token.setAdmin(true);
        if (token.getRoleModel().getName().equals(Global.Constants.auth.ROOT))
            token.setSuperAdmin(true);
        return token;
    }

    @Override
    @Transactional
    public Result addRole(String accountId, RoleModel roleModel, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(accountId))
                return Message.BadRequest.setArgs("account id must not be empty.");
            RoleModel role = roleModel;
            if (role == null)
                // select a default role
                role = crudService.query().from(RoleModel.class).where("asDefault", true).one();
            if (role.getId().equals(accountOnStart.getSupperAdmin().getId()))
                return Message.UnAuthorized;
            Account2RoleModel account2RoleModel = new Account2RoleModel();
            account2RoleModel.setId(Generator.random(32));
            account2RoleModel.setAccountId(accountId);
            account2RoleModel.setRoleId(role.getId());
            crudService.save(account2RoleModel, InterventionType.INSERT);
            return role;
        });
    }

    @Override
    public Result updateRole(String accountId, String roleId, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(accountId) && Validator.isEmpty(roleId))
                return Message.BadRequest.setArgs("account id or role id must not be empty.");
            Result<Token> tokenResult = this.verify(token);
            if (!tokenResult.isSuccess())
                return Message.UnAuthorized;

            Token loginUser = tokenResult.getData();
            if (!loginUser.isAdmin())
                return Message.UnAuthorized;

            Account2RoleModel account2RoleModel = crudService.query().from(Account2RoleModel.class).where("accountId", accountId).one();
            if (account2RoleModel == null)
                return Message.BadRequest.setArgs("account id is not correct.");
            if (Validator.isEmpty(roleId))
                return Message.BadRequest.setArgs("role id must not be empty.");

            // check input role is correct
            RoleModel checkRole = crudService.query().from(RoleModel.class).where("id", roleId).one();
            if (checkRole.getId().equals(accountOnStart.getSupperAdmin().getId()))
                return Message.UnAuthorized;

            if (checkRole == null)
                return Message.BadRequest.setArgs("role id is not correct.");

            account2RoleModel.setRoleId(roleId);
            crudService.save(account2RoleModel, InterventionType.UPDATE);
            return checkRole;
        });
    }

    @Override
    public Result getAccount(String accountId, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(accountId))
                return Message.BadRequest.setArgs("account id must not null");
            Token loginUser = verify(token).getData();
            AccountModel accountModel = crudService.query().from(AccountModel.class).where("id", accountId).where("appId", loginUser.getAppid())
                    .setExcludeDomain(true).one();
            // List<AccountModel> toExcludeAccModel = new ArrayList<>();
            if (accountModel == null)
                return null;
            RoleModel roleModel = (RoleModel) roleService.findRole(accountModel.getId());
            accountModel.setRoleModel(roleModel);
            if (roleModel != null)
                if (roleModel.getId().endsWith(accountOnStart.getSupperAdmin().getId()))
                    if (!loginUser.getUid().endsWith(accountId))
                        return Message.UnAuthorized;
            return accountModel;
        });
    }

    @Override
    @NoNeedVerify
    public Result logout(String token) {
        return Result.reply(() -> {
            Cache.getInstance().by("redis").remove(token);
            return Message.Success;
        });
    }

    @Override
    @NoNeedVerify(NoNeedEffectiveness = true)
    public Result<Token> verify(String token) {
        return Result.reply(() -> {
            if (!Validator.isEmpty(Cache.getInstance().by("redis").get(token))) {
                Object o = Cache.getInstance().by("redis").get(token);

                if (o instanceof JSONObject)
                    return DJson.parseJson(o.toString(), Token.class);
                else
                    return (Token) o;
            }
            Logger.info("this token is invalid: " + token);
            return Message.InvalidToken;
        });
    }

    @Override
    @NoNeedVerify
    public Object verify(PermissionBean permissionBean, String token) {
        return permissionAccessService.verify(permissionBean, token);
    }

    @Override
    @Transactional
    public Result addUser(CreateAccountViewModel createUser, String token) {
        return Result.reply(() -> {
            String msg = "{0} not allow empty";
            if (createUser == null)
                return Message.BadRequest.setArgs("account model must be not null");
            if (Validator.isEmpty(createUser.getUsername()))
                return Message.BadRequest.setArgs(MessageFormat.format(msg, Description.USERNAME));
            if (Validator.isEmpty(createUser.getPassword()))
                return Message.BadRequest.setArgs(MessageFormat.format(msg, Description.P_STR));
            if (Validator.isEmpty(createUser.getEmail()))
                return Message.BadRequest.setArgs(MessageFormat.format(msg, Description.EMAIL));

            Result<Token> loginResult = verify(token);
            if (!checkAuth(loginResult.getData(), token))
                return Message.UnAuthorized;

            // check
            Object checkResult = check(createUser);
            if (checkResult != null)
                return checkResult;

            AccountModel accountModel = new AccountModel();
            boolean re = addAccount(createUser, loginResult, accountModel, token);

            // select a default role for it
            if (re) {
                RoleModel roleModel = null;
                if (!Validator.isEmpty(createUser.getRoleId()))
                    roleModel = crudService.query().from(RoleModel.class).where(Description.ID, createUser.getRoleId()).one();
                Result roleResult = this.addRole(accountModel.getId(), roleModel, token);
                if (roleResult.isSuccess())
                    accountModel.setRoleModel((RoleModel) roleResult.getData());
                else
                    return Message.BadRequest.setArgs(roleResult.getMessage());
            }
            return accountModel;
        });
    }

    private boolean addAccount(CreateAccountViewModel createUser, Result<Token> loginResult, AccountModel accountModel, String token) {
        accountModel.setUsername(createUser.getUsername());
        accountModel.setEmail(createUser.getEmail());
        accountModel.setPassword(PasswordHelper.encryptPassword(createUser.getUsername(), createUser.getPassword()));
        accountModel.setAppId(createUser.getAppId());
        accountModel.setId(Generator.random(32));
        accountModel.setCreatedAt(new Date());
        accountModel.setValidFlag(1);
        accountModel.setStatus(0);
        accountModel.setCreatedBy(loginResult.getData().getUid());

        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        Object obj = configService.getConfigByName("custom", token);
        if (obj != null)
            if (obj instanceof Result)
                if (((Result) obj).isSuccess())
                    accountModel.setCustomization(((Result) obj).getData().toString());
        return crudService.save(accountModel, InterventionType.INSERT);
    }

    private Object check(CreateAccountViewModel createUser) {
        if (!checker.getChecker(Rules.IllegalUsername).validate(createUser.getUsername()))
            return checker.getChecker(Rules.IllegalUsername).illegal();
        if (!checker.getChecker(Rules.IllegalPassword).validate(createUser.getPassword(), 6))
            return checker.getChecker(Rules.IllegalPassword).illegal();

        if (!checker.getChecker(Rules.IllegalEmail).validate(createUser.getEmail()))
            return checker.getChecker(Rules.IllegalEmail).illegal();

        Result checkUsername = notExists(null, createUser.getAppId(), Description.USERNAME, createUser.getUsername());
        Result checkEmail = notExists(null, createUser.getAppId(), Description.EMAIL, createUser.getEmail());
        if (!checkUsername.isSuccess())
            return checkUsername.getMsgObj();
        if (!checkEmail.isSuccess())
            return checkEmail.getMsgObj();
        return null;
    }

    private Result notExists(String accountId, String appId, String key, String value) {
        return Result.reply(() -> {
            if (Validator.isEmpty(value))
                return true;
            List<AccountModel> acc = crudService.query().from(AccountModel.class).where(key, value).setExcludeDomain(true).where("appId", appId).list();
            if (Validator.isEmpty(accountId))
                if (acc.size() > 0)
                    return Message.BadRequest.setArgs(MessageFormat.format(existTip, key));
                else {
                    for (AccountModel c : acc) {
                        if (!c.getId().equals(accountId))
                            return Message.BadRequest.setArgs(MessageFormat.format(existTip, key));
                    }
                }
            return true;
        });
    }

    private boolean checkAuth(Token loginUser, String token) {
        if (loginUser == null)
            loginUser = verify(token).getData();
        if (loginUser == null)
            return false;

        if (loginUser.getRoleModel() == null)
            return false;

        return loginUser.isAdmin();
    }

    @Override
    @Transactional
    public Result delUser(String id, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("user id must be not null");
            return crudService.execute().setExcludeDomain(true).remove(AccountModel.class, id);
        });
    }

    @Override
    public Result exist(String username, String token) {
        return null;
    }

    @Override
    @Transactional
    public Result resetUserPassword(String id, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("user id must be not null");
            AccountModel accountModel = crudService.query().from(AccountModel.class).setExcludeDomain(true).where("id", id).one();
            if (accountModel == null)
                return Message.BadRequest.setArgs("user id is not correct");
            String initPW = TokenProcessor.getInstance().generateToken(Generator.random(8), true).substring(0, 7);
            accountModel.setPassword(PasswordHelper.encryptPassword(accountModel.getUsername(), initPW));
            crudService.save(accountModel, InterventionType.UPDATE);
            return initPW;
        });
    }

    @Override
    @Transactional
    @NoNeedVerify
    public Result changePassword(PasswordViewModel passwordViewModel, String token) {
        return Result.reply(() -> {
            Token loginUser = verify(token).getData();
            if (Validator.isEmpty(passwordViewModel.getOld_password()))
                return Message.BadRequest.setArgs("old password must be not null");
            if (Validator.isEmpty(passwordViewModel.getNew_password()))
                return Message.BadRequest.setArgs("new password must be not null.");
            if (!checker.getChecker(Rules.IllegalPassword).validate(passwordViewModel.getNew_password(), 6))
                return checker.getChecker(Rules.IllegalPassword).illegal();
            AccountModel accountModel = crudService.query().from(AccountModel.class).where(Description.USERNAME, loginUser.getUsername()).setExcludeDomain(true)
                    .where("password", PasswordHelper.encryptPassword(loginUser.getUsername(), passwordViewModel.getOld_password())).one();
            if (accountModel == null)
                return Message.BadRequest.setArgs("old password is not correct.");
            accountModel.setPassword(PasswordHelper.encryptPassword(loginUser.getUsername(), passwordViewModel.getNew_password()));
            return crudService.save(accountModel, InterventionType.UPDATE);
        });
    }

    @Override
    public Result listUser(QueryParam queryParam, String token) {
        return Result.reply(() -> {
            Token loginUser = verify(token).getData();
            List<AccountModel> accountModels;
            QueryProxy queryProxy = crudService.query().from(AccountModel.class).where("appId", loginUser.getAppid())
                    .paging(queryParam.getPage(), queryParam.getSize()).order(queryParam.getSortBy(), queryParam.getOrder()).setExcludeDomain(true);
            if (queryParam.getPage() > -1)
                accountModels = queryProxy.pageList();
            else
                accountModels = queryProxy.list();
            List<AccountModel> toExcludeAccModel = new ArrayList<>();
            // set role
            accountModels.forEach(c -> {
                RoleModel roleModel = (RoleModel) roleService.findRole(c.getId());
                c.setRoleModel(roleModel);
                if (roleModel != null) {
                    if (roleModel.getId().equals(accountOnStart.getSupperAdmin().getId()))
                        toExcludeAccModel.add(c);
                }
            });
            toExcludeAccModel.forEach(accountModels::remove);
            return accountModels;
        });
    }

    @Override
    @NoNeedVerify
    public Result updateUser(UpdateUserViewModel userViewModel, String token) {
        Token loginUser = verify(token).getData();
        UpdateAccountViewModel updateAccountViewModel = new UpdateAccountViewModel();
        updateAccountViewModel.setCustom(userViewModel.getCustom());
        updateAccountViewModel.setDescription(userViewModel.getDescription());
        updateAccountViewModel.setEmail(userViewModel.getEmail());
        return updateAccount(loginUser.getUid(), updateAccountViewModel, token);
    }

    @Override
    @NoNeedVerify
    public Result getUser(String token) {
        return Result.reply(() -> {
            Token loginUser = verify(token).getData();
            if (loginUser == null)
                return Message.UnAuthorized;
            AccountModel accountModel = crudService.query().from(AccountModel.class).setExcludeDomain(true).where("appId", loginUser.getAppid())
                    .where("id", loginUser.getUid()).one();
            accountModel.setIsAdmin(loginUser.isAdmin());
            accountModel.setSuperAdmin(loginUser.isSuperAdmin());
            if (accountModel != null)
                accountModel.setRoleModel((RoleModel) roleService.findRole(accountModel.getId()));
            return accountModel;
        });
    }

    @Override
    @NoNeedVerify
    public Result updateAccount(String id, UpdateAccountViewModel account, String token) {
        return Result.reply(() -> {
            // # appId is administrator, only admin account have it
            if (Validator.isEmpty(id))
                return Message.BadRequest.setArgs("account id not allow empty.");
            Token loginUser = verify(token).getData();
            // only admin can modify non-admin user, non admin can modify itself
            if (loginUser.getUid().equals(id) || loginUser.isAdmin()) {
                AccountModel accountModel = crudService.query().from(AccountModel.class).where("id", id).setExcludeDomain(true).one();
                if (accountModel == null)
                    return Message.UnAuthorized;
                accountModel.setStatus(account.getStatus());
                accountModel.setUpdatedAt(new Date());
                if (!Validator.isEmpty(account.getEmail())) {
                    if (!checker.getChecker(Rules.IllegalEmail).validate(account.getEmail().trim()))
                        return checker.getChecker(Rules.IllegalEmail).illegal();
                    accountModel.setEmail(account.getEmail().trim());
                }

                Result checkEmail = notExists(id, loginUser.getAppid(), Description.EMAIL, account.getEmail());

                if (!checkEmail.isSuccess())
                    return checkEmail.getMsgObj();
                if (!Validator.isEmpty(account.getDescription()))
                    accountModel.setDescription(account.getDescription());

                // only super admin can modify the role info
                if (!Validator.isEmpty(account.getRoleId())) {
                    if (loginUser.isAdmin()) {
                        // accountModel.setRoleModel(account.getRole());
                        Result setRoleResult = updateRole(id, account.getRoleId(), token);
                        if (!setRoleResult.isSuccess())
                            return setRoleResult.getData();
                        else
                            accountModel.setRoleModel((RoleModel) setRoleResult.getData());
                    }
                }

                if (!Validator.isEmpty(account.getCustom())) {
                    if (!Validator.isEmpty(accountModel.getCustomization())) {
                        JSONObject oldCustom = accountModel.getCustomizationObj();
                        oldCustom.keySet().forEach(k -> {
                            oldCustom.put(k, account.getCustom().get(k));
                        });
                        accountModel.setCustomization(oldCustom.toJSONString());
                    }
                }

                boolean re = crudService.save(accountModel, InterventionType.UPDATE);
                if (re)
                    return accountModel;
                return false;
            } else {
                return Message.UnAuthorized;
            }
        });
    }

    @Override
    public Result listStatus(String token) {
        return Result.reply(() -> {
            Map<String, String> map = new HashMap<>();
            for (Enums.Status status : Enums.Status.values()) {
                map.put(status.getType() + "", status.getTypeString());
            }
            return map;
        });
    }

    @Override
    @NoNeedVerify(NoNeedEffectiveness = true)
    public AccountModel findAccount(String accountRelated, String... domain) {
        // get by username
        if (Validator.isEmpty(accountRelated))
            return null;
        QueryProxy queryProxy = getQueryProxy(accountRelated, Description.USERNAME, domain);
        AccountModel accountModel = queryProxy.one();

        // get by email
        if (accountModel == null) {
            queryProxy = getQueryProxy(accountRelated, Description.EMAIL, domain);
            accountModel = queryProxy.one();
        }
        return accountModel;
    }

    private QueryProxy getQueryProxy(String accountRelated, String field, String[] domain) {
        QueryProxy queryProxy = crudService.query().from(AccountModel.class).setExcludeDomain(true).where(field, accountRelated);
        if (domain.length > 0) {
            if (!Validator.isEmpty(domain[0]))
                queryProxy.where("appId", domain[0]);
        }
        return queryProxy;
    }
}
