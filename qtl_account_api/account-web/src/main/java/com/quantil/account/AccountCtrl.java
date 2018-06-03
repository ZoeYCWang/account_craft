package com.quantil.account;

import com.quantil.account.permission.PermissionAccessService;
import com.quantil.account.permission.PermissionViewModel;
import com.quantil.common.BaseCtrl;
import com.quantil.common.Notes;
import com.quantil.common.QueryParam;
import com.zoe.snow.auth.AccountViewModel;
import com.zoe.snow.auth.PermissionBean;
import com.zoe.snow.crud.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * AccountCtrl
 *
 * @author <a href="mailto:wenqing.dai@quantil.com">daiwenqing</a>
 * @date 2017/4/15
 */
@Controller("account.ctrl")
@RequestMapping("/api/0.1/accounts")
@RestController
@Api(value = "accounts", description = " ", tags = { "accounts" }, hidden = true)
public class AccountCtrl extends BaseCtrl {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionAccessService permissionAssignService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD + Description.LOGIN_RESPONSE)
    public Object login(@ApiParam(name = "account", value = "json", required = true) @RequestBody AccountViewModel account) {
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

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object delAccount(@ApiParam(name = "id", value = "user id", required = true) @PathVariable("id") String id) {
        return reply(accountService.delUser(id, getToken()));
    }

    @RequestMapping(value = "/{id:.+}/password", method = RequestMethod.PUT)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object resetPassword(@ApiParam(name = "id", value = "user id", required = true) @PathVariable("id") String id) {
        return reply(accountService.resetUserPassword(id, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object listAccount(@ApiParam(name = "sortby", value = "the field name sort by") @RequestParam(required = false) String sortby,
            @ApiParam(name = "order", value = "desc or asc") @RequestParam(required = false) String order,
            @ApiParam(name = "page", value = "page num") @RequestParam(required = false) Integer page,
            @ApiParam(name = "size", value = "each page size") @RequestParam(required = false) Integer size) {
        QueryParam queryParam = new QueryParam(page, size, sortby, order);
        return reply(accountService.listUser(queryParam, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "get account by id", notes = Notes.RESPONSE_HEAD)
    public Object getAccount(@ApiParam(name = "id", value = "account id", required = true) @PathVariable("id") String id) {
        return reply(accountService.getAccount(id, getToken()));
    }

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "test", hidden = true)
    public Object test(@PathVariable("id") String id) {
        return reply(accountService.getUser(id, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD + Description.VERIFY_RESPONSE)
    public Object add(@ApiParam(name = "account model", value = "account", required = true) @RequestBody CreateAccountViewModel account) {
        return reply(accountService.addUser(account, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object update(@ApiParam(name = "id", value = "account id") @PathVariable("id") String id,
            @ApiParam(name = "account", value = "account", required = true) @RequestBody UpdateAccountViewModel account) {
        return reply(accountService.updateAccount(id, account, getToken()));
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object listStatus() {
        return reply(accountService.listStatus(getToken()));
    }

    @RequestMapping(value = "/{id:.+}/permissions", method = RequestMethod.GET)
    @ApiOperation(value = "get permission by user id")
    public Object getPermissionByUser(@ApiParam(name = "id", value = "user object primary id") @PathVariable String id,
            @ApiParam(name = "filters", value = "filter column") @RequestParam(required = false) String filters) {
        return reply(permissionAssignService.getPermissionsByUser(id, filter(filters), getToken()));
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD + Description.VERIFY_RESPONSE)
    public Object verify(@ApiParam(name = "token", value = "token", required = true) @RequestBody TokenView token) {
        return reply(accountService.verify(token.getToken()));
    }

}
