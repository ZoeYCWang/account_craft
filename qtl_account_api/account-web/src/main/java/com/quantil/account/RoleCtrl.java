package com.quantil.account;

import com.quantil.account.permission.PermissionAccessService;
import com.quantil.account.role.CreateRoleViewModel;
import com.quantil.account.role.RoleService;
import com.quantil.account.role.UpdateRoleViewModel;
import com.quantil.common.BaseCtrl;
import com.quantil.common.Notes;
import com.quantil.common.QueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RoleCtrl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/27
 */
@Controller("account.role.ctrl")
@RequestMapping("/api/0.1/roles")
@RestController
@Api(value = "roles", description = " ", tags = { "roles" })
public class RoleCtrl extends BaseCtrl {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionAccessService permissionAssignService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "", notes = Notes.RESPONSE_HEAD)
    public Object listRole(@ApiParam(name = "sortby", value = "the field name sort by") @RequestParam(required = false) String sortby,
            @ApiParam(name = "order", value = "desc or asc") @RequestParam(required = false) String order,
            @ApiParam(name = "page", value = "page num") @RequestParam(required = false) Integer page,
            @ApiParam(name = "size", value = "each page size") @RequestParam(required = false) Integer size) {
        QueryParam queryParam = new QueryParam(page, size, sortby, order);
        return reply(roleService.listRole(queryParam, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "get role by id", notes = Notes.RESPONSE_HEAD)
    public Object getRole(@ApiParam(name = "id", value = "the role id") @PathVariable("id") String id) {
        return reply(roleService.getRole(id, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "update the role info", notes = Notes.RESPONSE_HEAD)
    public Object updateRole(@ApiParam(name = "id", value = "role id") @PathVariable("id") String id,
            @ApiParam(name = "role", value = "the role model") @RequestBody UpdateRoleViewModel role) {
        return reply(roleService.updateRole(id, role, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "add a new role", notes = Notes.RESPONSE_HEAD)
    public Object addRole(@ApiParam(name = "role", value = "the role model") @RequestBody CreateRoleViewModel role) {
        return reply(roleService.addRole(role, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "delete a role", notes = Notes.RESPONSE_HEAD)
    public Object delRole(@ApiParam(name = "id", value = "user id", required = true) @PathVariable("id") String id) {
        return reply(roleService.delRole(id, getToken()));
    }

    @RequestMapping(value = "/{id:.+}/permissions", method = RequestMethod.PUT)
    @ApiOperation(value = "assign the permission")
    public Object assign(@ApiParam(name = "id", value = "role object primary id") @PathVariable String id,
            @ApiParam(name = "permissions ", value = "permission id list") @RequestBody List<String> permissions) {
        return reply(permissionAssignService.assignPermissionToRole(id, permissions, getToken()));
    }

    @RequestMapping(value = "/{id:.+}/permissions", method = RequestMethod.GET)
    @ApiOperation(value = "get permission by role id")
    public Object getPermissionByRole(@ApiParam(name = "id", value = "role object primary id") @PathVariable String id,
            @ApiParam(name = "filters", value = "filter column") @RequestParam(required = false) String filters) {
        return reply(permissionAssignService.getPermissionsByRole(id, filter(filters), getToken()));
    }
}
