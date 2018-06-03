package com.quantil.account.permission;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quantil.common.Notes;
import com.zoe.snow.model.enums.IdStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/*
  *Description
 
  *@author <a href="mailto:dwq676@126.com">daiwenqing</a>
  *@date 2017/11/22
 */
public class Description {
    public static final String PERMISSION_ID = "permission_id";
    public static final String ROLE_ID = "role_id";
    public static final String DESCRIPTION = "description";
    public static final String USER_ID = "user_id";

    public static final String METHOD = "method";
    public static final String TYPE = "type";
    public static final String ID = "id";
    public static final String URL = "url";

    public static final String METHODS = "methods";
    public static final String TYPES = "types";
    public static final String IDS = "ids";
    public static final String URLS = "urls";

    public static final String PERMISSION="permission";
    public static final String ROLE="role";
    public static final String ROLES="roles";
    public static final String PERMISSIONS="permissions";

    public static final String TIP_PERMISSION_ID = "permission primary id";
    public static final String TIP_USER_ID = "user primary id";
    public static final String TIP_ROLE_ID = "role primary id";
    public static final String TIP_PERMISSION_URL = "permission url";


    public static final String LIST_COMMENTS = " \n get permission by some queryParams or filters\n" +
            "     \n just only for the role of admin\n" +
            "     \n### @param queryParam\n" +
            "     \n      query params are some general condition, such as below\n" +
            "     \n      page: { page: 1, size: 1 }\n" +
            "     \n          ---ps if page equals '-1' the return result page will be null\n" +
            "     \n      order: { order: asc, sortBy: key}\n" +
            "     \n### @param filters\n" +
            "     \n      filters are the properties of permission column\n" +
            "     \n      filter by ids: [id1, id2]\n" +
            "     \n      filter by urls: [url1, urls, ...]\n" +
            "     \n      filter by methods: [method1, method2, ...]\n" +
            "     \n      filter by types: [type1, type2, ...]\n" +
            "     \n so the filters format is\n" +
            "\n```java\n{\n" +
            "   \"ids\":[],\n" +
            "   \"urls\":[],\n" +
            "   \"methods\":[],\n" +
            "  \"types\":[]\n" +
            "}\n```\n" +
            "\nor\n" +
            "\n```java\n{\n" +
            "   \"id\":\"\",\n" +
            "   \"url\":\"\",\n" +
            "   \"method\":\"\",\n" +
            "   \"type\":\"\"\n" +
            "}\n```\n"+
            "     \n### @param token\n" +
            "     \n      the token who invokes the interface must login firstly.\n" +
            "     \n### @return     \n" +
            "     \n-      if success\n" +
            "\n```java\n{\n" +
            "    \"code\": \"200\",\n" +
            "    \"data\": {\n" +
            "        [\n" +
            "             {\n" +
            "                \"id\": \"\",\n" +
            "                \"created_at\": ,\n" +
            "                \"created_by\": \"\",\n" +
            "                \"method\": \"\",\n" +
            "                \"type\": \"\",\n" +
            "                \"updated_at\": ,\n" +
            "                \"updated_by\": \"\",\n" +
            "                \"url\": \"url here\",\n" +
            "                \"deleted\": \"1\"\n" +
            "             },\n" +
            "             ......\n" +
            "        ]\n" +
            "    },\n" +
            "    \"message\": \"ok\",\n" +
            "    \"paging\": {\n" +
            "         page: 1 // current page\n" +
            "        \"count\": 4, // total counts\n" +
            "        \"page_size\": 10, // each page size\n" +
            "        \"pages\": 1  // page numbers\n" +
            "    },\n" +
            "    \"success\": true\n" +
            " }\n```\n" +
            "\n- else if unauthorized\n" +
            "\n```java\n{\n" +
            "    \"code\": \"401\",\n" +
            "    \"data\": null,\n" +
            "    \"message\": \"Unauthorized\",\n" +
            "    \"paging\": null,\n" +
            "    \"success\": false\n" +
            "}\n```\n" +
            "\n- else if bad request\n" +
            "\n```java{\n" +
            "    \"code\": \"400xx\",\n" +
            "    \"data\": null,\n" +
            "    \"message\": \"\",\n" +
            "    \"paging\": null,\n" +
            "    \"success\": false\n" +
            "}\n```\n" +
            "\n- else not fund\n" +
            "\n```java\n{\n" +
            "    \"code\": \"200\",\n" +
            "    \"data\": null,\n" +
            "    \"message\": \"\",\n" +
            "    \"paging\": null,\n" +
            "    \"success\": true\n" +
            "}\n```\n";

    public static final String ADD_UPDATE_COMMENT =
            "     \n adding a new permission object, such as url and about its properties\n" +
            "     \n only the role of admin can access it.\n" +
            "     \n### @param permissionViewModel\n" +
            "     \n      view model\n" +
            "     \n      type: {account,gslb,portal}\n" +
            "     \n      method: {put,delete,list,get,post}\n" +
            "     \n      url: {the request permission]\n" +
            "     \n### @param token\n" +
            "     \n      the token who invokes the interface must login firstly.\n" +
            "     \n### @return\n" +
            "\n- if success\n" +
            "\n```java\n{\n" +
            "    \"code\": \"200\",\n" +
            "    \"data\": {\n" +
            "        \"id\": \"\",\n" +
            "        \"created_at\": ,\n" +
            "        \"created_by\": \"\",\n" +
            "        \"method\": \"\",\n" +
            "        \"type\": \"\",\n" +
            "        \"updated_at\": ,\n" +
            "        \"updated_by\": \"\",\n" +
            "        \"url\": \"permission url here\"\n" +
            "        \"deleted\": \"1\"\n" +
            "    },\n" +
            "    \"message\": \"ok\",\n" +
            "    \"paging\": null,\n" +
            "    \"success\": true\n" +
            "}\n```\n" +
            "\n- else if unauthorized\n" +
            " \n```java\n{\n" +
            "    \"code\": \"401\",\n" +
            "    \"data\": null,\n" +
            "    \"message\": \"Unauthorized\",\n" +
            "    \"paging\": null,\n" +
            "    \"success\": false\n" +
            "}\n```\n" +
            "\n- else  bad request\n" +
            "\n```java{\n" +
            "    \"code\": \"400xx\",\n" +
            "    \"data\": null,\n" +
            "    \"message\": \"some messages here\",\n" +
            "    \"paging\": null,\n" +
            "    \"success\": false\n" +
            "}\n```\n";

    public static final String GET_COMMENT=
                    "     \n get permission object by its id\n" +
                    "     \n just only for the role of admin\n" +
                    "     \n### @param id \n" +
                    "     \n      permission primary id\n" +
                    "     \n### @param token\n" +
                    "     \n      the token who invokes the interface must login firstly.\n" +
                    "     \n### @return\n" +
                    "\n- if success\n" +
                    "\n```java\n{\n" +
                    "   \"code\": \"200\",\n" +
                    "   \"data\": {\n" +
                    "        \"id\": \"\",\n" +
                    "        \"created_at\": ,\n" +
                    "        \"created_by\": \"\",\n" +
                    "        \"method\": \"\",\n" +
                    "        \"type\": \"\",\n" +
                    "        \"updated_at\": ,\n" +
                    "        \"updated_by\": \"\",\n" +
                    "        \"url\": \"url here\"\n" +
                    "        \"deleted\": \"1\"\n" +
                    "    },\n" +
                    "    \"message\": \"ok\",\n" +
                    "    \"paging\": null,\n" +
                    "    \"success\": true\n" +
                    "}\n```\n" +
                    "\n- else if unauthorized\n" +
                    "\n```java\n{\n" +
                    "   \"code\": \"401\",\n" +
                    "   \"data\": null,\n" +
                    "   \"message\": \"Unauthorized\",\n" +
                    "   \"paging\": null,\n" +
                    "   \"success\": false\n" +
                    "}\n```\n" +
                    "\n- else if bad request\n" +
                    "\n```java\n{\n" +
                    "   \"code\": \"400xx\",\n" +
                    "   \"data\": null,\n" +
                    "   \"message\": \"some messages here\",\n" +
                    "   \"paging\": null,\n" +
                    "   \"success\": false\n" +
                    "}\n```\n" +
                    "\n- else not fund\n" +
                    "\n```java\n{\n" +
                    "    \"code\": \"200\",\n" +
                    "    \"data\": null,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"paging\": null,\n" +
                    "    \"success\": true\n" +
                    "}\n```\n";
    public static final String DELETE_COMMENT =
                    "     \n delete the permission by its id\n" +
                    "     \n just only for the role of admin\n" +
                    "     \n### @param id\n" +
                    "     \n      permission object primary id\n" +
                    "     \n### @param token\n" +
                    "     \n      the token who invokes the interface must login firstly.\n" +
                    "     \n### @return\n" +
                    "\n- if success\n" +
                    "\n```java\n{\n" +
                    "    \"code\": \"200\",\n" +
                    "    \"data\": null,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"paging\": null,\n" +
                    "    \"success\": true\n" +
                    "}\n```\n" +
                    "\n- else if unauthorized\n" +
                    "\n```java\n{\n" +
                    "   \"code\": \"401\",\n" +
                    "   \"data\": null,\n" +
                    "   \"message\": \"Unauthorized\",\n" +
                    "   \"paging\": null,\n" +
                    "   \"success\": false\n" +
                    "}\n```\n" +
                    "\n- else\n" +
                    "\n```java\n{\n" +
                    "   \"code\": \"400xx\",\n" +
                    "   \"data\": null,\n" +
                    "   \"message\": \"some messages here\",\n" +
                    "   \"paging\": null,\n" +
                    "   \"success\": false\n" +
                    "\n}\n```\n";
}
