package com.quantil.account;

import com.quantil.account.permission.PermissionAccessService;
import com.quantil.account.permission.PermissionViewModel;
import com.quantil.common.BaseCtrl;
import com.quantil.common.Notes;
import com.zoe.snow.auth.AccountViewModel;
import com.zoe.snow.auth.PermissionBean;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.crud.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * UserCtrl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/27
 */
@Controller("account.user.ctrl")
@RequestMapping("/api/0.1/user")
@RestController
@Api(value = "user", description = " ", tags = { "user" })
public class UserCtrl extends BaseCtrl {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionAccessService permissionAssignService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD + Description.LOGIN_RESPONSE)
    public Object login(@ApiParam(name = "account", value = "json", required = true) @RequestBody AccountViewModel account) {
        if (account == null)
            account = new AccountViewModel();
        Result<Token> tokenResult = accountService.login(request.getIp(), account);
        if (tokenResult.getData() != null)
            response.addHeader("token", tokenResult.getData().getToken());
        return reply(tokenResult);
    }



    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD + Description.LOGOUT_RESPONSE)
    public Object logout(@ApiParam(name = "token", value = "token", required = true) @RequestBody TokenView token) {
        return reply(accountService.logout(token.getToken()));
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD + Description.VERIFY_RESPONSE)
    public Object verify(@ApiParam(name = "token", value = "token", required = true) @RequestBody TokenView token) {
        return reply(accountService.verify(token.getToken()));
    }

    @RequestMapping(value = "/permission/verify", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object verifyPermission(@ApiParam(name = "permission", value = "permission object", required = true) @RequestBody PermissionBean permissionBean) {
        return reply(permissionAssignService.verify(permissionBean, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object update(@ApiParam(name = "account", value = "account", required = true) @RequestBody UpdateUserViewModel account) {
        return reply(accountService.updateUser(account, getToken()));
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    @ApiOperation(value = "id", notes = Notes.RESPONSE_HEAD)
    public Object changePassword(@ApiParam(name = "PasswordModel", value = "PasswordModel", required = true) @RequestBody PasswordViewModel password) {
        return reply(accountService.changePassword(password, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "get login user info", notes = Notes.RESPONSE_HEAD)
    public Object getUser() {
        return reply(accountService.getUser(getToken()));
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    @ApiOperation(value = "get permission by token")
    public Object getPermissionByToken(@ApiParam(name = "filters", value = "filter column") @RequestParam(required = false) String filters) {
        return reply(permissionAssignService.getPermissionsByToken(filter(filters), getToken()));
    }

    /*
     * @RequestMapping(value = "/customization", method = RequestMethod.PUT)
     * 
     * @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD) public Object
     * updateCustomization(@ApiParam(name = "customization", value =
     * "custom json") @RequestBody CustomizationViewModel customization) {
     * return reply(accountService.updateCustomizationValue(customization,
     * getToken())); }
     */
}
