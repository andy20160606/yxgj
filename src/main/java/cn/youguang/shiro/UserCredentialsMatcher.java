package cn.youguang.shiro;

import cn.youguang.entity.Kh;
import cn.youguang.entity.User;
import cn.youguang.service.KhService;
import cn.youguang.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class UserCredentialsMatcher extends SimpleCredentialsMatcher {


    @Autowired
    private UserService userService;

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.openid.sign.login}")
    private String wxsign;


    @Autowired
    private KhService khService;


    public boolean wxConnect(String wxopenid) {
        return true;
    }

    @Override
    @SuppressWarnings(value = "all")
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        MyUsernamePasswordToken upt = (MyUsernamePasswordToken) token;
        System.out.println("upt-username=" + upt.getUsername());
        System.out.println("upt.getPassword="
                + String.valueOf(upt.getPassword()));
        System.out.println("upt.getCredentials" + upt.getCredentials());
        System.out.println("upt.getPrincipal=" + upt.getPrincipal());

        String user = upt.getUsername();
        String pass = String.valueOf(upt.getPassword());
        String loginType = upt.getLoginType();


        if (loginType.equals("wxlogin")) {  //如果是微信标识登录
            return wxConnect(pass);
        }

        if (loginType.equals("ptlogin")) {
            return ptConnect(user, pass);
        }


//        if (loginType.equals("khlogin")) {
//            return khConnect(user, pass);
//        }

//		if ("root".equals(user) && "pass".equals(pass))
//			return true;
//		else
//			return connect("121.41.41.30", "389", user, pass);

        return false;
    }

    private boolean ptConnect(String user, String pass) {

        User u = userService.findUserByLoginName(user);
        if (u != null && u.getLoginpass().equals(pass)) {
            return true;
        }
        return false;

    }

//    private boolean khConnect(String user, String pass) {
//
//        Kh kh = khService.findByLoginnameAndLoginpass(user, pass);
//
//
//        if (kh != null) {
//            return true;
//        }
//        return false;
//    }

}
