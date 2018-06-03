package com.quantil.account.permission;

import com.zoe.snow.auth.PermissionBean;
import com.zoe.snow.crud.Result;

import java.util.List;
import java.util.Map;

/**
 * PermissionAssign
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/27
 */
public interface PermissionAccessService {
    /**
     * assign permission to the role
     * only the role of admin can access it.
     * @param permissionId permission primary id
     * @param permissionId role primary id
     * @param token
     *      the token who invokes the interface must login firstly
     * @return
    if success
    {
        "code": "200",
        "data": {
            "id": "",
            "permission": {
                "id": "",
                "created_at": ,
                "created_by": "",
                "method": "",
                "type": "",
                "updated_at": ,
                "updated_by": "",
                "url": ""
                "deleted": ""
            },
            "role":{
                "as_default": false,
                "code": "",
                "description": "",
                "id": "",
                "name": "",
                "parent_id": ""
            }
        },
        "message": "ok",
        "paging": null,
        "success": true
    }
    else if unauthorized
    {
        "code": "401",
        "data": null,
        "message": "Unauthorized",
        "paging": null,
        "success": false
    }
    else bad request
    {
        "code": "400xx",
        "data": null,
        "message": "some messages here",
        "paging": null,
        "success": false
    }
    
     */
    Result assignRoleToPermission(String permissionId, List<String> roleIds, String token);

    Result assignPermissionToRole(String roleId, List<String> permissionIds, String token);

    /**
     * get permission object by role primary id
     * only the role of admin can access it.
     * @param roleId role primary id
     * @param token
     *      the token who invokes the interface must login firstly
     * @return
    if success
    {
        "code": "200",
        "data": {
        "id": "",
        "permission": {
            "id": "",
            "created_at": ,
            "created_by": "",
            "method": "",
            "type": "",
            "updated_at": ,
            "updated_by": "",
            "url": ""
            "deleted": ""
        },
        "role":{
            "as_default": false,
            "code": "",
            "description": "",
            "id": "",
            "name": "",
            "parent_id": ""
        }
        },
        "message": "ok",
        "paging": null,
        "success": true
    }
    else if unauthorized
    {
        "code": "401",
        "data": null,
        "message": "Unauthorized",
        "paging": null,
        "success": false
    }
    else if not fund
    {
        "code": "200",
        "data": null,
        "message": "not fund",
        "paging": null,
        "success": true
    }
    else bad request
    {
        "code": "400xx",
        "data": null,
        "message": "some messages here",
        "paging": null,
        "success": false
    }
     */
    Result getPermissionsByRole(String roleId, Map<String, List<String>> filters, String token);

    /**
     * get permission object by user primary id
     * only the role of admin can access it.
     * @param userId user primary id
     * @param token
     *      the token who invokes the interface must login firstly
     * @return
    if success
    {
        "code": "200",
        "data": {
            "id": "",
            "permission": {
            "id": "",
            "created_at": ,
            "created_by": "",
            "method": "",
            "type": "",
            "updated_at": ,
            "updated_by": "",
            "url": ""
            "deleted": ""
        },
        "role":{
                "as_default": false,
                "code": "",
                "description": "",
                "id": "",
                "name": "",
                "parent_id": ""
            }
        },
        "message": "ok",
        "paging": null,
        "success": true
    }
    else if unauthorized
    {
        "code": "401",
        "data": null,
        "message": "Unauthorized",
        "paging": null,
        "success": false
    }
    else if not fund
    {
        "code": "200",
        "data": null,
        "message": "not fund",
        "paging": null,
        "success": true
    }
    else bad request
    {
        "code": "400xx",
        "data": null,
        "message": "some messages here",
        "paging": null,
        "success": false
    }
     */
    Result getPermissionsByUser(String userId, Map<String, List<String>> filters, String token);

    Result getRoleByPermission(String permission, Map<String, List<String>> filters, String token);

    /**
     *  get permission object by user token
     * @param token
     * @return
    if success
    {
        "code": "200",
        "data": {
            "id": "",
            "permission": {
            "id": "",
            "created_at": ,
            "created_by": "",
            "method": "",
            "type": "",
            "updated_at": ,
            "updated_by": "",
            "url": ""
            "deleted": ""
        },
        "role":{
            "as_default": false,
            "code": "",
            "description": "",
            "id": "",
            "name": "",
            "parent_id": ""
        }
        },
        "message": "ok",
        "paging": null,
        "success": true
    }
    else if unauthorized
    {
        "code": "401",
        "data": null,
        "message": "Unauthorized",
        "paging": null,
        "success": false
    }
    else if not fund
    {
        "code": "200",
        "data": null,
        "message": "not fund",
        "paging": null,
        "success": true
    }
    else bad request
    {
        "code": "400xx",
        "data": null,
        "message": "some messages here",
        "paging": null,
        "success": false
    }
     */
    Result getPermissionsByToken(Map<String, List<String>> filters, String token);

    /**
     * verify somebody have the permission by token
     * @param url
     *      the permission url
     * @param token
     *      the token who invokes the interface must login firstly
     * @return
    if success
    {
        "code": "200",
        "data": {
            "id": "",
            "created_at": ,
            "created_by": "",
            "method": "",
            "type": "",
            "updated_at": ,
            "updated_by": "",
            "url": "url here"
            "deleted": "1"
        },
        "message": "ok",
        "paging": null,
        "success": true
    }
    else if unauthorized
    {
        "code": "401",
        "data": null,
        "message": "Unauthorized",
        "paging": null,
        "success": false
    }
    else no permission
    {
        "code": "200",
        "data": null,
        "message": "Unauthorized",
        "paging": null,
        "success": false
    }
    else bad request
    {
        "code": "400xx",
        "data": null,
        "message": "some messages here",
        "paging": null,
        "success": false
    }
     */
    Result verify(PermissionBean permissionBean, String token);
}
