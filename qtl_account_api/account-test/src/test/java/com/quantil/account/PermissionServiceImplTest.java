package com.quantil.account;

import com.quantil.account.permission.PermissionService;
import com.quantil.account.permission.PermissionViewModel;
import com.quantil.common.AbstractTestNGTest;
import com.zoe.snow.crud.Result;
import com.zoe.snow.dao.Closable;
import com.zoe.snow.dao.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * PermissionServiceImplTest
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/11/24
 */
@WebAppConfiguration("/src/test/java")
public class PermissionServiceImplTest extends AbstractTestNGTest {
    @Autowired
    private PermissionService permissionService;
    @Autowired(required = false)
    private Set<Closable> cloneableSet;
    @Autowired(required = false)
    private Set<Transaction> transactionSet;

    @Test
    public void testGet() {

    }

    public void testAdd() {
        PermissionViewModel permissionViewModel = new PermissionViewModel();
        Result result = permissionService.add(null, null);
        Assert.assertEquals("400", result.getCode());
        result = permissionService.add(permissionViewModel, null);
        Assert.assertEquals("400", result.getCode());

        transactionSet.forEach(Transaction::beginTransaction);
        permissionViewModel.setUrl("/api/0.1/account/accounts");
        result = permissionService.add(permissionViewModel, null);
        Assert.assertEquals("400", result.getCode());

        permissionViewModel.setType("account");
        permissionViewModel.setMethod("get");
        result = permissionService.add(permissionViewModel, null);
        Assert.assertEquals("200", result.getCode());
        Assert.assertNotNull(result.getData());



        if (result.isSuccess()) {
            transactionSet.forEach(Transaction::commit);
            cloneableSet.forEach(Closable::close);
        }
    }

}
