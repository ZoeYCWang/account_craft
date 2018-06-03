package com.quantil.account.role;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.IdStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Account2RoleModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/26
 */
@Entity
@Table(name = "account_role")
@Component("account.2role.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Account2RoleModel implements Model{

    private static final String UUID = "uuid";

    @JSONField(name = com.quantil.account.Description.ID)
    @Column(name = com.quantil.account.Description.ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    private String id;

    @JSONField(name = Description.ROLE_ID)
    @Column(name = Description.ROLE_ID)
    private String roleId;

    @Column(name = Description.ACCOUNT_ID)
    @JSONField(name = Description.ACCOUNT_ID)
    private String accountId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
