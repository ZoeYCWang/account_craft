package com.quantil.account.permission;

import javax.persistence.*;

import com.quantil.account.role.RoleModel;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.IdStrategy;

/**
 * Account2RoleModel
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/26
 */
@Entity
@Table(name = "permission_role")
@Component("account.2permission.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Permission2RoleModel implements Model {
    @JSONField(name = com.quantil.account.Description.ID)
    @Column(name = com.quantil.account.Description.ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    private String id;

    @JSONField(name = Description.ROLE)
    @JoinColumn(name = Description.ROLE_ID)
    @ManyToOne
    private RoleModel role;

    @JoinColumn(name = Description.PERMISSION_ID)
    @ManyToOne
    @JSONField(name = Description.PERMISSION)
    private PermissionModel permission;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public PermissionModel getPermission() {
        return permission;
    }

    public void setPermission(PermissionModel permission) {
        this.permission = permission;
    }
}
