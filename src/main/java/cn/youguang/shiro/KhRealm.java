package cn.youguang.shiro;


import cn.youguang.entity.Kh;
import cn.youguang.entity.Resource;
import cn.youguang.entity.Role;
import cn.youguang.entity.User;
import cn.youguang.service.KhService;
import cn.youguang.service.RoleService;
import cn.youguang.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的指定Shiro验证用户登录的类
 */
public class KhRealm extends AuthorizingRealm {


    @Autowired
    private KhService khService;

    /**
     * 为当前登录的Subject授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return new SimpleAuthorizationInfo();

    }

    /**
     * 验证当前登录的Subject
     *
     * @see :本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        // 获取基于用户名和密码的令牌
        // 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        System.out.println("Shiro开始登录认证");
        MyUsernamePasswordToken token = (MyUsernamePasswordToken) authcToken;

        String loginname = token.getUsername();
        String password = String.valueOf(token.getPassword());
        String logintype = token.getLoginType();
        Kh kh = khService.findByLoginnameAndLoginpass(loginname, password);

        // 账号不存在
        if (kh == null) {
            System.out.println("账号不存在");
            return null;
        }

        // 认证缓存信息
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(kh.getWybs(), kh.getLoginpass(), getName());

        //  this.setSession("currentUser", user);
        return authcInfo;

        // 没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常

    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
