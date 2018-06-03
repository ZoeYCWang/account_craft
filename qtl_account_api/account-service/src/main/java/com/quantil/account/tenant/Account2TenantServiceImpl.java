package com.quantil.account.tenant;

import com.zoe.snow.crud.CrudService;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Account2TenantServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/26
 */
/*@Service("quantil.rdc.account.2-tenant.service")
public class Account2TenantServiceImpl implements BaseTenantService {
    @Autowired
    private CrudService crudService;

    @Override
    public Tenant getTenant(String tenantId, String accountId) {
        if (Validator.isEmpty(tenantId) || Validator.isEmpty(accountId))
            return null;
        return crudService.query().from(Account2TenantModel.class).where("tenantId", tenantId)
                .where("accountId", accountId).one();

    }
}*/
