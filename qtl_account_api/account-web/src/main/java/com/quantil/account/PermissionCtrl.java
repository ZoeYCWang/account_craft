package com.quantil.account;

import com.quantil.account.permission.*;

import com.quantil.account.permission.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.quantil.common.BaseCtrl;
import com.quantil.common.QueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Map;

/**
 * permissionCtrl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/27
 */
@Controller("account.permission.ctrl")
@RequestMapping("/api/0.1/permissions")
@RestController
@Api(value = "permissions", description = " ", tags = { "permissions" })
public class PermissionCtrl extends BaseCtrl {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionAccessService permissionAssignService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "get permission objects by filters or query param", notes = Description.LIST_COMMENTS)
    public Object list(@ApiParam(name = "filters", value = "filter column") @RequestParam(required = false) String filters,
            @ApiParam(name = "sortby", value = "the field name sort by") @RequestParam(required = false) String sortby,
            @ApiParam(name = "order", value = "desc or asc") @RequestParam(required = false) String order,
            @ApiParam(name = "page", value = "page num") @RequestParam(required = false) Integer page,
            @ApiParam(name = "size", value = "each page size") @RequestParam(required = false) Integer size) {
        QueryParam queryParam = new QueryParam(page, size, sortby, order);
        // DJson.parseJson(filters.toJSONString(),Map<String,List<String>>.class);
        Map<String, List<String>> map = filter(filters);
        return reply(permissionService.list(queryParam, map, getToken()));
    }

    @RequestMapping(value = "/{id:.+}/roles", method = RequestMethod.PUT)
    @ApiOperation(value = "assign the permission")
    public Object assign(@ApiParam(name = "id", value = "role object primary id") @PathVariable String id,
            @ApiParam(name = "roles ", value = "role id list") @RequestBody List<String> roles) {
        return reply(permissionAssignService.assignRoleToPermission(id, roles, getToken()));
    }

    @RequestMapping(value = "/{id:.+}/roles", method = RequestMethod.GET)
    @ApiOperation(value = "assign the permission")
    public Object getRolesByPermission(@ApiParam(name = "id", value = "role object primary id") @PathVariable String id,
            @ApiParam(name = "filters", value = "filter column") @RequestParam(required = false) String filters) {
        return reply(permissionAssignService.getRoleByPermission(id, filter(filters), getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "get permission by id", notes = Description.GET_COMMENT)
    public Object get(@ApiParam(name = "id", value = "the permission id") @PathVariable("id") String id) {
        return reply(permissionService.get(id, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "add the permission object", notes = Description.ADD_UPDATE_COMMENT)
    public Object update(@ApiParam(name = "id", value = "permission id") @PathVariable("id") String id,
            @ApiParam(name = "permission", value = "the permission model") @RequestBody PermissionViewModel permissionViewModel) {
        return reply(permissionService.update(id, permissionViewModel, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "add a new permission object", notes = Description.ADD_UPDATE_COMMENT)
    public Object add(@ApiParam(name = "permission", value = "the permission model") @RequestBody PermissionViewModel permission) {
        return reply(permissionService.add(permission, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "delete a permission object", notes = Description.DELETE_COMMENT)
    public Object delete(@ApiParam(name = "id", value = "permission id", required = true) @PathVariable("id") String id) {
        return reply(permissionService.delete(id, getToken()));
    }
}
