package com.quantil.common;

import com.quantil.account.AccountService;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.Configuration;
import com.zoe.snow.context.request.HttpServletRequestAware;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.context.session.HttpSessionAware;
import com.zoe.snow.context.session.Session;
import com.zoe.snow.context.session.SessionAdapter;
import com.zoe.snow.context.session.SessionAdapterAware;
import com.zoe.snow.crud.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.request.RequestContextListener;
import org.testng.annotations.BeforeClass;

import javax.servlet.ServletRequestEvent;

/**
 * AbstractTestNGTest
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/15
 */
@ContextConfiguration({ "classpath:spring.xml" })
public abstract class AbstractTestNGTest extends AbstractTestNGSpringContextTests {
    @Autowired
    protected CrudService crudService;
    private MockHttpServletRequest request;
    private MockHttpSession session;
    private MockServletContext context;

    @BeforeClass
    public void before() {
        RequestContextListener listener = new RequestContextListener();
        context = new MockServletContext();
        request = new MockHttpServletRequest(context);
        listener.requestInitialized(new ServletRequestEvent(context, request));
        session = new MockHttpSession();
        request.setSession(session);

        Request r = BeanFactory.getBean(Request.class);
        ((HttpServletRequestAware) r).setHttpServletRequest(request);

        Configuration configuration = BeanFactory.getBean(Configuration.class);
        SessionAdapter sessionAdapter = BeanFactory.getBean("snow.context.session." + configuration.getSessionName());
        ((HttpSessionAware) sessionAdapter).set(request.getSession());

        Session session = BeanFactory.getBean(Session.class);
        ((SessionAdapterAware) session).setSession(sessionAdapter);
        // session.setExpiration(Converter.toInt(CoreConfig.getContextProperty("snow.session.time-out"))
        // * 60);
    }
}
