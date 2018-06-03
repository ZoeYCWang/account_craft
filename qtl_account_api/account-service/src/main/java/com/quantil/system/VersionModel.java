package com.quantil.system;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * VersionModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/8/24
 */
@Component("rdc.system.version.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VersionModel {
    private String commit;
    @JSONField(name = "package")
    private String pk = "1D";
    private String version;

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
