var jsonData = {
    "swagger": "2.0", "info": {
        "title": "Swagger API", "version": ""
    }
    ,
    "paths": {
        "/api/0.1/CVs/": {
            "get": {
                "description": "List all CVs.  \nNo filters supported yet. An empty list would be returned if no data found.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}],
                "tags": ["CVs"],
                "summary": "List all CVs.  ",
                "operationId": "CVs_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
            ,
            "post": {
                "description": "Create CV.\n### Request Example\n    {\n        \"name\": \"euna\",\n        \"SRs\": [\"cn\", \"apac\"],\n        \"NonSRs\": [\"nc\", \"af\"],\n        \"VIPs\": [\"220.243.230.62\"],\n        \"NonVIPs\": [\"220.243.199.113\"]\n    }"
                , "parameters": [{
                    "schema": {
                        "required": ["name"],
                        "type": "object",
                        "properties": {
                            "NonVIPs": {"type": "string", "description": ""},
                            "VIPs": {"type": "string", "description": ""},
                            "name": {"type": "string", "description": ""},
                            "deleted": {"type": "boolean", "description": ""},
                            "SRs": {"type": "string", "description": ""},
                            "NonSRs": {"type": "string", "description": ""}
                        }
                    }, "name": "data", "in": "body"
                }], "tags": ["CVs"], "summary": "Create CV.", "operationId": "CVs_create", "consumes": ["application/json"], "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/CVs/{name}/": {
            "get": {
                "description": "Get detailed CV.", "parameters": [{"description": "", "required": true, "type": "string", "name": "name", "in": "path"}], "tags": ["CVs"], "summary": "Get detailed CV.", "operationId": "CVs_read", "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/DNs/": {
            "get": {
                "description": "List or filter DNs.  \nAdmin privilege required. Currently we support name and type filters for fuzzy query. An empty list would be returned if no data found.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}, {"description": "", "required": false, "type": "string", "name": "name", "in": "query"}, {"description": "", "required": false, "type": "string", "name": "type", "in": "query"}],
                "tags": ["DNs"],
                "summary": "List or filter DNs.  ",
                "operationId": "DNs_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
            ,
            "post": {
                "description": "Create a DN.  \nAdmin privilege required. A serialized domain with domain_id would be returned.\nWe support 2 types of DN configurations:  \n*  Accelerate Domain: DNS-Resolved by CDN DNS, including common DNS definition and gslb DNS definition. It MUST be under-controlled by CDN DNS.  \n*  Customer Domain: DNS-Resolved by Customer DNS. The final entry of CDN. Normally configure a CNAME to Accelerate Domain. It MAY be out-of-controlled by CDN DNS.  \n\nAn accelerate Domain may be also a Custom Domain.  \nTo unify the definition of Accelerate Domain and Custom Domain:  \n*  A domain contains any DNS record configure will be considered as an Accelerate Domain.  \n*  A domain contains key:ACCDN configure will be considered as a Custom Domain.  \nIf type is not specified in request when creating a new domain,  accelerate will be set as default type.  \nIf the domain name already exists or the domain config is valid, failure with error code would be returned.\n### Request Example\n    {\n        \"DN\": \"cloud.apple.com.\",\n        \"type\": \"accelerate\",\n        \"CVs\": [\n            {\"name\":\"*\",\"config\":{\"A\":{\"AUTO\":{}}}},\n            {\"name\":\"sa\", \"config\":{\"A\":{\"AUTO\":{\"SR\":[\"apac\",\"euna\"]}}}},\n            {\"name\":\"apac\",\"config\": {\"A\":{\"TTL\":60,\"RDATA\":[\"1.2.3.4\",\"2.3.4.5\"]}}},\n            {\"name\":\"af\",\"config\": {\"A\":{\"AUTO\":{\"TTL\":60,\"SR\":[\"*\"],\"~SR\":[\"cn\",\"apac\"]}}}},\n            {\"name\":\"cn\",\"config\": {\"A\":{\"AUTO\":{\"TTL\":60,\"SR\":[\"nc\"]}}}}\n        ]\n    }\n### Response\n- 200, success\n- 40003, unauthorized\n- 42004, domain name already exists\n- 42005, invalid domain config",
                "parameters": [{
                    "schema": {
                        "required": ["domain_id", "name"],
                        "type": "object",
                        "properties": {"deleted": {"type": "boolean", "description": ""}, "CVs": {"type": "string", "description": ""}, "type": {"type": "string", "description": ""}, "domain_id": {"type": "string", "description": ""}, "name": {"type": "string", "description": ""}}
                    }, "name": "data", "in": "body"
                }],
                "tags": ["DNs"],
                "summary": "Create a DN.  ",
                "operationId": "DNs_create",
                "consumes": ["application/json"],
                "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/DNs/{domain_id}/": {
            "get": {
                "description": "Get detailed info for a specified DN.  \nAdmin privilege required.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": true, "type": "string", "name": "domain_id", "in": "path"}],
                "tags": ["DNs"],
                "summary": "Get detailed info for a specified DN.  ",
                "operationId": "DNs_read",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/FARMs/": {
            "get": {
                "description": "List or filter FARMs.\nAdmin privilege required. No filters supported for now.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}],
                "tags": ["FARMs"],
                "summary": "List or filter FARMs.",
                "operationId": "FARMs_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
            ,
            "post": {
                "description": "Create a FARM.\n### Request Example\n    {\n      \"farm_id\": \"1234567\",\n      \"name\": \"test_farm1\",\n      \"FBWI_VIEWCUR\": 0,\n      \"FBWI_VIEWCUR_1\": 0,\n      \"FSIGST_CURRENT\": 0,\n      \"FNLBWI_VIEWCUR\": 0,\n      \"FBWI_VIEWLIMIT\": 0,\n      \"FSIGST_CALCULATE\": 0\n    }\n### Response\n- 200, success\n- 40003, unauthorized\n- 43001, FARM id already exists",
                "parameters": [{
                    "schema": {
                        "type": "object",
                        "properties": {
                            "farm_id": {"type": "string", "description": ""},
                            "name": {"type": "string", "description": ""},
                            "FBWI_VIEWCUR": {"type": "integer", "description": ""},
                            "deleted": {"type": "boolean", "description": ""},
                            "FSIGST_CALCULATE": {"type": "number", "description": ""},
                            "FBWI_VIEWLIMIT": {"type": "integer", "description": ""},
                            "FSIGST_CURRENT": {"type": "integer", "description": ""},
                            "FBWI_VIEWCUR_1": {"type": "integer", "description": ""}
                        }
                    }, "name": "data", "in": "body"
                }],
                "tags": ["FARMs"],
                "summary": "Create a FARM.",
                "operationId": "FARMs_create",
                "consumes": ["application/json"],
                "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/FARMs/{farm_id}/": {
            "get": {
                "description": "Get detailed information for a specified FARM.  \nOnly admin has privilege to access the FARM detail.\n### Response\n- 200, success\n- 40003, Unauthorized",
                "parameters": [{"description": "", "required": true, "type": "string", "name": "farm_id", "in": "path"}],
                "tags": ["FARMs"],
                "summary": "Get detailed information for a specified FARM.  ",
                "operationId": "FARMs_read",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/POPs/": {
            "get": {
                "description": "List or filter POPs.\nAdmin privilege required. No filters supported for now.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}],
                "tags": ["POPs"],
                "summary": "List or filter POPs.",
                "operationId": "POPs_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
            ,
            "post": {
                "description": "Create a POP.\n### Request Example\n    {\n      \"IP\": \"3.4.5.6\",\n      \"PST_CURRENT\": 0,\n      \"VIPs\": [\"1.2.3.4\", \"3.4.5.6\"]\n    }\n### Response\n- 200, success\n- 40003, unauthorized\n- 43009, POP IP already exists", "parameters": [{
                    "schema": {
                        "required": ["IP"],
                        "type": "object",
                        "properties": {"deleted": {"type": "boolean", "description": ""}, "pop_id": {"type": "string", "description": ""}, "PST_CURRENT": {"type": "integer", "description": ""}, "VIPs": {"type": "string", "description": ""}, "IP": {"type": "string", "description": ""}}
                    }, "name": "data", "in": "body"
                }], "tags": ["POPs"], "summary": "Create a POP.", "operationId": "POPs_create", "consumes": ["application/json"], "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/POPs/{pop_id}/": {
            "get": {
                "description": "Get detailed information for a specified POP.  \nOnly admin has privilege to access the POP detail.\n### Response\n- 200, success\n- 40003, Unauthorized",
                "parameters": [{"description": "", "required": true, "type": "string", "name": "pop_id", "in": "path"}],
                "tags": ["POPs"],
                "summary": "Get detailed information for a specified POP.  ",
                "operationId": "POPs_read",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/SRs/": {
            "get": {
                "description": "List all SRs.  \nNo filters supported yet. An empty list would be returned if no data found.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}],
                "tags": ["SRs"],
                "summary": "List all SRs.  ",
                "operationId": "SRs_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
            ,
            "post": {
                "description": "Create SR.\n### Request Example\n    {\n        \"name\": \"euna\"\n    }",
                "parameters": [{"schema": {"required": ["name"], "type": "object", "properties": {"deleted": {"type": "boolean", "description": ""}, "name": {"type": "string", "description": ""}}}, "name": "data", "in": "body"}],
                "tags": ["SRs"],
                "summary": "Create SR.",
                "operationId": "SRs_create",
                "consumes": ["application/json"],
                "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/SRs/{name}/": {
            "get": {
                "description": "Get detailed SR.", "parameters": [{"description": "", "required": true, "type": "string", "name": "name", "in": "path"}], "tags": ["SRs"], "summary": "Get detailed SR.", "operationId": "SRs_read", "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/VIPs/": {
            "get": {
                "description": "List or filter VIPs.\nAdmin privilege required. No filters supported for now.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}],
                "tags": ["VIPs"],
                "summary": "List or filter VIPs.",
                "operationId": "VIPs_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
            ,
            "post": {
                "description": "Create a VIP.\n### Request Example\n    {\n      \"IP\": \"220.243.197.068\",\n      \"farm_id\": \"5678899\",\n      \"pop\" : \"220.243.197.000/24\",\n      \"IST_CURRENT\" : 0,\n      \"SRs\": [\"cn\", \"apac\"]\n    }\n### Response\n- 200, success\n- 40003, unauthorized\n- 43010, VIP id already exists",
                "parameters": [{
                    "schema": {
                        "required": ["IP", "FARM", "POP"],
                        "type": "object",
                        "properties": {
                            "IST_CURRENT": {"type": "integer", "description": ""},
                            "vip_id": {"type": "string", "description": ""},
                            "FARM": {"type": "string", "description": ""},
                            "IP": {"type": "string", "description": ""},
                            "deleted": {"type": "boolean", "description": ""},
                            "POP": {"type": "string", "description": ""},
                            "SRs": {"type": "string", "description": ""}
                        }
                    }, "name": "data", "in": "body"
                }],
                "tags": ["VIPs"],
                "summary": "Create a VIP.",
                "operationId": "VIPs_create",
                "consumes": ["application/json"],
                "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/VIPs/{vip_id}/": {
            "get": {
                "description": "Get detailed information for a specified VIP.  \nOnly admin has privilege to access the VIP detail.\n### Response\n- 200, success\n- 40003, Unauthorized",
                "parameters": [{"description": "", "required": true, "type": "string", "name": "vip_id", "in": "path"}],
                "tags": ["VIPs"],
                "summary": "Get detailed information for a specified VIP.  ",
                "operationId": "VIPs_read",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/accounts/": {
            "get": {
                "description": "List all accounts by page, including deleted accounts.\nFilter will be supported later. An empty list would be returned if no accounts found.\n### Response\n- 200, success\n- 40003, Unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}],
                "tags": ["accounts"],
                "summary": "List all accounts by page, including deleted accounts.",
                "operationId": "accounts_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
            ,
            "post": {
                "description": "Create an account. \nFor now, we assume there are only 2 kinds of users that would have permission to call GSLB API:  \n*  Admin: have administrator privilege for all management operations  \n*  Read-only admin*: are restricted to the calls with GET method  \n\nAdmin users can create other read-only users, so there may be hierarchy between users.\n### Request Example\n    {\n        \"username\": \"quantil_user\",\n        \"password\": \"quantil123456\",\n        \"role\": \"Regular\",\n        \"corporation\": \"quantil\",\n        \"email\": \"test@quantil.com\",\n        \"phone\": \"456789\"\n    }\n### Response\n- 200, success\n- 40003, Unauthorized\n- 41003, Username already exists\n- 41005, Invalid role name",
                "parameters": [{
                    "schema": {
                        "required": ["uid", "username", "parent_uid", "corporation"],
                        "type": "object",
                        "properties": {
                            "username": {"type": "string", "description": ""},
                            "status": {"type": "string", "description": ""},
                            "uid": {"type": "string", "description": ""},
                            "deleted": {"type": "boolean", "description": ""},
                            "corporation": {"type": "string", "description": ""},
                            "api_rate": {"type": "integer", "description": ""},
                            "parent_uid": {"type": "string", "description": ""},
                            "phone": {"type": "string", "description": ""},
                            "role": {"type": "string", "description": ""},
                            "is_admin": {"type": "boolean", "description": ""},
                            "email": {"type": "string", "description": ""}
                        }
                    }, "name": "data", "in": "body"
                }],
                "tags": ["accounts"],
                "summary": "Create an account. ",
                "operationId": "accounts_create",
                "consumes": ["application/json"],
                "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/accounts/login/": {
            "post": {
                "description": "Account login with username and password.  \nThe account information would be returned if successfully logged in.  \nThe error code and message would be returned otherwise.\n### Example Request\n    {\n        \"username\": \"quantil\",\n        \"password\": \"quantil123\"\n    }\n### Response\n- 200, success\n- 41001, Incorrect credential",
                "parameters": [{
                    "schema": {
                        "required": ["uid", "username", "parent_uid", "corporation"],
                        "type": "object",
                        "properties": {
                            "username": {"type": "string", "description": ""},
                            "status": {"type": "string", "description": ""},
                            "uid": {"type": "string", "description": ""},
                            "deleted": {"type": "boolean", "description": ""},
                            "corporation": {"type": "string", "description": ""},
                            "api_rate": {"type": "integer", "description": ""},
                            "parent_uid": {"type": "string", "description": ""},
                            "phone": {"type": "string", "description": ""},
                            "role": {"type": "string", "description": ""},
                            "is_admin": {"type": "boolean", "description": ""},
                            "email": {"type": "string", "description": ""}
                        }
                    }, "name": "data", "in": "body"
                }],
                "tags": ["accounts"],
                "summary": "Account login with username and password.  ",
                "operationId": "accounts_login",
                "consumes": ["application/json"],
                "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/accounts/logout/": {
            "get": {
                "description": "Logout an account.", "parameters": [], "tags": ["accounts"], "summary": "Logout an account.", "operationId": "accounts_logout", "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/accounts/{uid}/": {
            "get": {
                "description": "Get detailed information for a specified user.  \nOnly admin or the logged in user have privilege to access the account profile.\n### Response\n- 200, success\n- 40003, Unauthorized",
                "parameters": [{"description": "", "required": true, "type": "string", "name": "uid", "in": "path"}],
                "tags": ["accounts"],
                "summary": "Get detailed information for a specified user.  ",
                "operationId": "accounts_read",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/params/": {
            "get": {
                "description": "List or filter params.  \nNo filters supported yet. An empty list would be returned if no data found.\n### Response\n- 200, success\n- 40003, unauthorized",
                "parameters": [{"description": "", "required": false, "type": "string", "name": "page", "in": "query"}],
                "tags": ["params"],
                "summary": "List or filter params.  ",
                "operationId": "params_list",
                "responses": {
                    "200": {
                        "description": ""
                    }
                }
            }
        }
        ,
        "/api/0.1/params/ops/": {
            "post": {
                "description": "Managing lbcalc Params all in one API.  \nFor creation, if param name already exists, the error code would be returned. Otherwise the new param would be returned.  \nFor update, if requested param name doesn't exist, the error code would be returned. Otherwise the updated param would be returned.  \nFor deletion, param in mysql would be marked as deleted, while the one in redis would be removed.  \n### Request Example\n    {\n        \"action\": \"new/update/delete\",\n        \"name\": \"GainP\",\n        \"value\": 1.0,\n        \"desc\": \"xxx\"\n    }\n### Response\n- 200, success\n- 40003, unauthorized\n- 43002, Param already exists\n- 43004, Param name doesn't exist",
                "parameters": [{"schema": {"required": ["name"], "type": "object", "properties": {"param_id": {"type": "string", "description": ""}, "value": {"type": "number", "description": ""}, "name": {"type": "string", "description": ""}}}, "name": "data", "in": "body"}],
                "tags": ["params"],
                "summary": "Managing lbcalc Params all in one API.  ",
                "operationId": "params_ops",
                "consumes": ["application/json"],
                "responses": {
                    "201": {
                        "description": ""
                    }
                }
            }
        }
    }
    ,
    "securityDefinitions": {
        "basic": {
            "type": "basic"
        }
    }
}