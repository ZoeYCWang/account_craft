package com.quantil.account.tenant;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.model.RawModel;
import com.zoe.snow.model.annotation.Datasource;
import com.zoe.snow.model.enums.IdStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Account2TenantModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/26
 */
/*
@Entity
@Table(name = "account_tenant")
@Component("quantil.rdc.account.2-tenant.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Datasource("account")
public class Account2TenantModel implements Tenant {

    @JSONField(name = Description.ACCOUNT_ID)
    @Column(name = Description.ACCOUNT_ID)
    private String accountId;
    @Column(name = Description.TENANT_ID)
    @JSONField(name = Description.TENANT_ID)
    private String tenantId;

    @JSONField(name = com.quantil.rdc.account.Description.ID)
    @Column(name = com.quantil.rdc.account.Description.ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
*/
