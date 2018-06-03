package com.quantil.account;

import com.alibaba.fastjson.JSONObject;
import com.quantil.common.AbstractTestNGTest;
import com.zoe.snow.auth.AccountViewModel;

import com.zoe.snow.auth.Authentication;

import com.zoe.snow.auth.Remote;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.crud.Result;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * AccountServiceImplTest
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/15
 */

@WebAppConfiguration("/src/test/java")
public class AccountServiceImplTest extends AbstractTestNGTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void testLogin() {
        AccountViewModel accountViewModel = new AccountViewModel();
        accountViewModel.setRememberMe(false);
        accountViewModel.setPassword("quantil@123456");
        accountViewModel.setUsername("quantil");

        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (UnknownHostException e) {

        }

        Result<Token> result = accountService.login(ip, accountViewModel);
        Assert.assertEquals(result.isSuccess(), true);
        Assert.assertEquals(!Validator.isEmpty(result.getData().getToken()), true);

        // accountViewModel.setAppid("");
        // result = accountService.login(ip, accountViewModel);
        // Assert.assertEquals(result.isSuccess(), false);

    }

//    public void testVerify() {
//        for (int i = 0; i < 10000; i++) {
//            Remote remote = BeanFactory.getBean(Remote.class);
//            PermissionBean permissionBean = new PermissionBean();
//            permissionBean.setType("API_ACCOUNT");
//            permissionBean.setMethod("GET");
//            permissionBean.setUrl("/api/0.1/POPs/{POP_ID}");
//
//            Object tokenResult = ((Authentication) remote).verify(permissionBean, "Y2I0MWMzNjc4OGVhMDJlNGYwNmUxOGYwZjgxYzJkODA");
//            // System.out.println(i);
//            if (tokenResult == null) {
//                System.out.println(i + "    =====================");
//                System.out.println(" token is null ");
//            } else {
//                JSONObject object = JSONObject.parseObject(tokenResult.toString());
//                if (!object.getBoolean("success")) {
//                    System.out.println(i + "    ++++++++++++++++++++++");
//                    System.out.println("false");
//                }
//            }
//        }
//    }
}