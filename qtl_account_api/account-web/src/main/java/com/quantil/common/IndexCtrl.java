package com.quantil.common;

import com.quantil.account.AccountService;
import com.quantil.account.Token;
import com.zoe.snow.auth.AccountViewModel;
import com.zoe.snow.auth.Authentication;
import com.zoe.snow.cache.Cache;
import com.zoe.snow.crud.Result;
import com.zoe.snow.util.Base64Utils;
import com.zoe.snow.util.Generator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * IndexCtrl
 *
 * @author <a href="mailto:wenqing.dai@quantil.com">daiwenqing</a>
 * @date 2017/3/27
 */
@Controller
@RestController
@RequestMapping(value = "")
@Api(hidden = true)
public class IndexCtrl extends BaseCtrl {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/docs")
    @ApiOperation(value = "", hidden = true)
    public ModelAndView index() {
        /*session.put("path", "../");
        session.put("time", System.currentTimeMillis());
        return new ModelAndView("index");*/

        ModelAndView modelAndView = null;
        if (session.get("token") != null)
            modelAndView = new ModelAndView("index");
        else
            modelAndView = new ModelAndView("redirect:/auth.html?time=" + System.currentTimeMillis());
        return modelAndView;
    }


    @RequestMapping(value = "/accounts/v/login", method = RequestMethod.POST)
    @ApiOperation(value = "login", hidden = true)
    public ModelAndView loginTo(String username, String password, String appId) {
        ModelAndView modelAndView = null;
        //Authentication authentication = (Authentication) remote;
        AccountViewModel accountViewModel = new AccountViewModel();
        accountViewModel.setUsername(username);
        accountViewModel.setPassword(password);
        accountViewModel.setAppid(appId);
        accountViewModel.setRememberMe(false);
        Result<Token> result = accountService.login(request.getIp(), accountViewModel);
        //JSONObject jsonObject = JSONObject.fromObject(object);
        if (result != null) {
            if (result.isSuccess()) {
                modelAndView = new ModelAndView("index");
                //JSONObject jo = jsonObject.getJSONObject("data");
                //String token = Generator.uuid();
                session.put("token", result.getData().getToken());
                session.put("is_authenticated", true);
                session.put("username", username);
                session.put(result.getData().getToken(), result.getData());
                session.put("time", System.currentTimeMillis());
                session.put("CSRFToken", Base64Utils.encode((Generator.uuid() + System.currentTimeMillis()).getBytes()));
                session.put("path", "../../");
            }
        } else {
            modelAndView = new ModelAndView("redirect:/auth.html");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/accounts/v/logout")
    @ApiOperation(value = "logout", hidden = true)
    public ModelAndView logoutTo() {
        if (session.get("is_authenticated")) {
            //session.put("token", "");
            accountService.logout(session.get("token"));
            session.put("is_authenticated", false);
            session.put("path", "");
            session.remove("username");
            session.remove(session.get("token"));
            session.remove("CSRFToken");
            session.remove("time");
            Cache.getInstance().remove(session.get("token"));
            session.remove("token");
        }
        return new ModelAndView("redirect:/auth.html");
    }
}
