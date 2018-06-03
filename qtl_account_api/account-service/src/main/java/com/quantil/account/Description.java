package com.quantil.account;

import java.util.Date;

/**
 * Description
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/26
 */
public class Description {
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String P_STR = "password";
    public static final String STATUS = "status";
    public static final String LAST_LOGIN_AT = "last_login_at";
    public static final String LAST_LOGIN_IP = "last_login_ip";
    public static final String DESC = "description";
    public static final String DELETED = "deleted";
    public static final String CREATED_AT = "created_at";
    public static final String CREATED_BY = "created_by";
    public static final String UPDATED_AT = "updated_at";
    public static final String UPDATED_BY = "updated_by";
    public static final String APP_ID = "app_id";
    public static final String TENANT_ID = "tenant_id";
    public static final String EMAIL = "email";
    public static final String COMPANY_ID = "company_id";
    public static final String STATUS_MSG = "status_msg";
    public static final String ROLE_ID = "role_id";
    public static final String IS_ADMIN = "is_admin";
    public static final String IS_SUPER_ADMIN = "is_super_admin";

    public static final String LOGIN_RESPONSE =
            "\n```json \n{\n" +
                    "  \"code\": \"200\",\n" +
                    "  \"data\": {\n" +
                    "    \"appid\": \"rdc\",\n" +
                    "    \"expired_in\": 172800000,\n" +
                    "    \"remember_me\": false,\n" +
                    "    \"role\": {\n" +
                    "      \"code\": \"\",\n" +
                    "      \"description\": \"Regular user\",\n" +
                    "      \"id\": \"f88dec8241bc11e7827f782bcb048924\",\n" +
                    "      \"name\": \"user\",\n" +
                    //"      \"parent_id\": \"\"\n" +
                    "    },\n" +
                    "    \"token\": \"NGEzNTQ0MTlhNmJkNDNiOWY0NDEyODRjNjBlM2QyZjg\",\n" +
                    "    \"uid\": \"208e460a41bd11e7827f782bcb048924\",\n" +
                    "    \"name\": \"quantil\"\n" +
                    "  },\n" +
                    "  \"message\": \"ok\",\n" +
                    "  \"success\": true\n" +
                    "} \n ```";

    public static final String LOGOUT_RESPONSE =
            "\n```json \n{\n" +
                    "  \"code\": \"200\",\n" +
                    "  \"data\": null,\n" +
                    "  \"message\": \"ok\",\n" +
                    "  \"success\": true\n" +
                    "} \n ```";

    public static final String VERIFY_RESPONSE =
            "\n if ok \n" +
                    "\n```json \n{\n" +
                    "  \"code\": \"200\",\n" +
                    "  \"data\": {\n" +
                    "    \"appid\": \"rdc\",\n" +
                    "    \"expired_in\": 172800000,\n" +
                    "    \"role\": {\n" +
                    "      \"code\": \"\",\n" +
                    "      \"description\": \"Regular user\",\n" +
                    "      \"id\": \"f88dec8241bc11e7827f782bcb048924\",\n" +
                    "      \"name\": \"user\",\n" +
                    "      \"parent_id\": \"\"\n" +
                    "    },\n" +
                    "    \"token\": \"NGEzNTQ0MTlhNmJkNDNiOWY0NDEyODRjNjBlM2QyZjg\",\n" +
                    "    \"uid\": \"208e460a41bd11e7827f782bcb048924\"\n" +
                    "  },\n" +
                    "  \"message\": \"ok\",\n" +
                    "  \"success\": true\n" +
                    "} \n ``` \n" +
                    "if failed \n" +
                    "\n```json \n{\n" +
                    "  \"code\": \"401\",\n" +
                    "  \"data\": null,\n" +
                    "  \"message\": \"unauthorized \",\n" +
                    "  \"success\": false\n" +
                    "} \n ```";

    public static final String LOGIN_REQUEST =
            "\n if ok \n" +
                    "  \"appid\": \"rdc\",\n" +
                    "  \"password\": \"dwq\",\n" +
                    "  \"username\": \"dwq\"\n" +
                    "} \n ```";
}
