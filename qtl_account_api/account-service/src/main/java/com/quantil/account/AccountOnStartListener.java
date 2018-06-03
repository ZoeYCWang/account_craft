package com.quantil.account;

import com.quantil.account.role.RoleModel;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.listener.ContextRefreshedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AccountOnStartListener
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/10/2
 */
@Component("quantil.account.listener")
public class AccountOnStartListener implements ContextRefreshedListener, AccountOnStart {
    @Autowired
    private CrudService crudService;
    private RoleModel supperRoleModel;

    @Override
    public int getContextRefreshedSort() {
        return 101;
    }

    @Override
    public void onContextRefreshed() {
        supperRoleModel = crudService.query().from(RoleModel.class)
                .where("name", "root").one();

    }

    @Override
    public String getName() {
        return "Account";
    }

    @Override
    public RoleModel getSupperAdmin() {
        return supperRoleModel;
    }
}
