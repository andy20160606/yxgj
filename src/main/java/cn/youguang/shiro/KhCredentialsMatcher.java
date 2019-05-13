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

public class KhCredentialsMatcher extends SimpleCredentialsMatcher {


    @Autowired
    private KhService khService;


    @Override
    @SuppressWarnings(value = "all")
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        MyUsernamePasswordToken upt = (MyUsernamePasswordToken) token;


        String user = upt.getUsername();
        String pass = String.valueOf(upt.getPassword());
        String loginType = upt.getLoginType();
        try {
            Kh kh = khService.findByLoginnameAndLoginpass(user, pass);

            if (kh != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }


        return false;
    }

}
