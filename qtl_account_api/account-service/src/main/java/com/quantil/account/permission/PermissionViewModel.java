package com.quantil.account.permission;

/**
 * Permission
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/23
 */
public class PermissionViewModel {
    private String type;
    private String method;
    private String url;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
