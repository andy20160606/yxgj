package cn.youguang.cfg;

import cn.youguang.shiro.*;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 可以选择性的将此类注释掉 @Configuration注释掉即可
 * Created by Andy丶 on 2017/2/20.
 */


@Configuration
public class ShiroCfg {

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    private static Map<String, Filter> filters = new LinkedHashMap<String, Filter>();


    @Bean("userRealm")
    public UserRealm getUserRealm() {


        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(getUserCredentialsMatcher());

        return userRealm;


    }

    @Bean("khRealm")
    public KhRealm getKhRealm() {


        KhRealm khRealm = new KhRealm();
        khRealm.setCredentialsMatcher(getKhCredentialsMatcher());
        return khRealm;


    }

    @Bean("userCredentialsMatcher")
    public UserCredentialsMatcher getUserCredentialsMatcher() {
        return new UserCredentialsMatcher();
    }

    @Bean("khCredentialsMatcher")
    public KhCredentialsMatcher getKhCredentialsMatcher() {
        return new KhCredentialsMatcher();
    }


    @Bean(name = "shiroEhcacheManager")
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");

        return em;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        realms.add(getUserRealm());
        realms.add(getKhRealm());
        dwsm.setRealms(realms);
        dwsm.setCacheManager(getEhCacheManager());
        dwsm.setRememberMeManager(rememberMeManager());
        dwsm.setSessionManager(new DefaultWebSessionManager());

        return dwsm;
    }


    /**
     * 记住我设置
     *
     * @return
     */

    @Bean
    public SimpleCookie rememberMeCookie() {

        System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(86400);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {

        System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /* *
     * 一个神奇的地方若 将       filterChainDefinitionMap.put("/login", "anon"); 放在        filterChainDefinitionMap.put("/**", "authc"); 的后面
     * 那么将需要点两次登录按钮
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();


        shiroFilterFactoryBean
                .setSecurityManager(getDefaultWebSecurityManager());

        KickoutSessionControlFilter kscf = getKickoutSessionControlFilter();


        shiroFilterFactoryBean.setFilters(filters);


        shiroFilterFactoryBean.getFilters().put("kickout", kscf);


        //       shiroFilterFactoryBean.setLoginUrl("/loginRedirect.html");
        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterChainDefinitionMap.put("/Mall/Login.html", "anon");
        filterChainDefinitionMap.put("/Mall/css/**", "anon");
        filterChainDefinitionMap.put("/Mall/js/**", "anon");
        filterChainDefinitionMap.put("/Mall/image/**", "anon");
        filterChainDefinitionMap.put("/Mall/image/**/**", "anon");
        filterChainDefinitionMap.put("/Mall/newer.html", "anon");//新手必读
        filterChainDefinitionMap.put("/Mall/sussecc.html", "anon");//成功案例
        filterChainDefinitionMap.put("/Mall/about.html", "anon");//关于我们
        filterChainDefinitionMap.put("/ptloginKickout.html", "anon"); //踢出页面
        filterChainDefinitionMap.put("/product/login.html", "anon");
        filterChainDefinitionMap.put("/product/css/**", "anon");
        filterChainDefinitionMap.put("/product/fonts/**", "anon");
        filterChainDefinitionMap.put("/product/img/**", "anon");
        filterChainDefinitionMap.put("/product/js/**", "anon");
        filterChainDefinitionMap.put("/product/lib/**", "anon");
        filterChainDefinitionMap.put("/product/lib/**/**", "anon");
        filterChainDefinitionMap.put("/product/pages/**", "anon");
        filterChainDefinitionMap.put("/XGJM/dist/index.html", "anon");
        filterChainDefinitionMap.put("/XGJM/dist/js/**", "anon");
        filterChainDefinitionMap.put("/XGJM/dist/src/imgs/**", "anon");
        filterChainDefinitionMap.put("/XGJM/dist/src/imgs/**/**", "anon");
        filterChainDefinitionMap.put("/XGJM/dist/js/**/**", "anon");
        filterChainDefinitionMap.put("/ptlogin/**", "anon");
        filterChainDefinitionMap.put("/mylogin/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/wxlogin", "anon");
        filterChainDefinitionMap.put("/verifyCode", "anon");
        filterChainDefinitionMap.put("/checkKhcpYxq", "anon");//外部校验客户产品有效期
        filterChainDefinitionMap.put("/khLsrz", "anon");//子系统客户临时认证
        filterChainDefinitionMap.put("/**", "user,kickout");
//        filterChainDefinitionMap.put("/**", "user");
        //       filterChainDefinitionMap.put("/**", " authc");
//        filterChainDefinitionMap.put("/js*//**//**", "anon");
        shiroFilterFactoryBean
                .setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public KickoutSessionControlFilter getKickoutSessionControlFilter() {
        KickoutSessionControlFilter kscf = new KickoutSessionControlFilter();
        kscf.setCacheManager(getEhCacheManager());
        kscf.setSessionManager(getDefaultWebSecurityManager().getSessionManager());


        return kscf;

    }


    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(getDefaultWebSecurityManager());
        return new AuthorizationAttributeSourceAdvisor();
    }


}
