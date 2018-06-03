package com.quantil.account.permission;

import com.quantil.common.QueryParam;
import com.zoe.snow.crud.Result;

import java.util.List;
import java.util.Map;

/**
 * PermissionService
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/23
 */
public interface PermissionService {
    /**
     * adding a new permission object, such as url and about its properties
     * only the role of admin can access it.
     * 
     * @param permissionViewModel
     *            view model
     *            type: {account,gslb,portal}
     *            method: {put,delete,list,get,post}
     *            url: {the request permission]
     * @param token
     *     the token who invokes the interface must login firstly.
     * @return
     * 
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
                    "url": "permission url here"
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
           else  bad request
            {
                "code": "400",
                "data": null,
                "message": "some messages here",
                "paging": null,
                "success": false
            }
     *
     */
    Result add(PermissionViewModel permissionViewModel, String token);

    /**
     * get permission object by its id
     * just only for the role of admin
     * @param id 
     *      permission primary id
     * @param token
     *      the token who invokes the interface must login firstly.
     * @return
     * 
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
            else if bad request
            {
                "code": "400XX",
                "data": null,
                "message": "some messages here",
                "paging": null,
                "success": false
            }

            else not fund
            {
                "code": "200",
                "data": null,
                "message": "",
                "paging": null,
                "success": true
            }
     */
    Result get(String id, String token);

    /**
     * get permission by some queryParams or filters
     * just only for the role of admin
     * @param queryParam
     *      query params are some general condition, such as below
     *      page: { page: 1, size: 1 }
     *          ---ps if page equals '-1' the return result page will be null
     *      order: { order: asc, sortBy: key}
     * @param filters
     *      filters are the properties of permission column
     *      filter by ids: [id1, id2]
     *      filter by urls: [url1, urls, ...]
     *      filter by methods: [method1, method2, ...]
     *      filter by types: [type1, type2, ...]
     *      so the filters format is
     *      {
     *          "ids":[],
     *          "urls":[],
     *          "methods":[],
     *          "types":[]
     *      }
     *      or
     *      {
     *          "id":"",
     *          "url":"",
     *          "method":"",
     *          "type":""
     *      }
     * @param token
     *      the token who invokes the interface, that must login firstly.
     * @return
     *
            if success
            {
                "code": "200",
                "data": {
                    [
                        {
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
                         ......
                    ]
                },
                "message": "ok",
                "paging": {
                     page: 1 // current page
                    "count": 4, // total counts
                    "page_size": 10, // each page size
                    "pages": 1  // page numbers
                },
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
            else if bad request
            {
                "code": "400",
                "data": null,
                "message": "some messages here",
                "paging": null,
                "success": false
            }
            else not fund
            {
                "code": "200",
                "data": null,
                "message": "",
                "paging": null,
                "success": true
            }
     *
     */
    Result list(QueryParam queryParam, Map<String, List<String>> filters, String token);

    /**
     * delete the permission by its id
     * just only for the role of admin
     * @param id
     *      permission object primary id
     * @param token
     *      the token who invokes the interface must login firstly.
     * @return
        if success
        {
            "code": "200",
            "data": null,
            "message": "",
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
        else
        {
            "code": "400xx",
            "data": null,
            "message": "some messages here",
            "paging": null,
            "success": false
        }
    
     */
    Result delete(String id, String token);

    /**
     * adding a new permission object, such as url and about its properties
     * only the role of admin can access it.
     *
     * @param permissionViewModel
     *            view model
     *            type: {account,gslb,portal}
     *            method: {put,delete,list,get,post}
     *            url: {the request permission]
     * @param token
     *     the token who invokes the interface must login firstly.
     * @return
     *
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
                    "url": ""
                    "deleted": ""
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
                "code": "400",
                "data": null,
                "message": "some messages here",
                "paging": null,
                "success": false
            }
     *
     */
    Result update(String id, PermissionViewModel permissionViewModel, String token);
}
