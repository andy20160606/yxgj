package cn.youguang.controller;


import cn.youguang.dto.LoginDto;
import cn.youguang.entity.Kh;
import cn.youguang.entity.User;
import cn.youguang.service.KhService;
import cn.youguang.service.UserService;
import cn.youguang.shiro.MyUsernamePasswordToken;
import cn.youguang.util.Result;
import cn.youguang.util.VerifyCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @description：登录退出
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @Autowired
    private KhService khService;


    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }


    /**
     * GET 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Result login() {

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("loginRedirect.html");

//        return "loginRedirect";
        Result result = new Result();
        result.setMsg("请进行登录操作");
        return result;
    }


    /**
     * POST 登录 shiro 写法
     *
     * @param username 用户名
     * @param password 密码
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = "all")
    public Result loginPost(@RequestBody LoginDto loginDto) {

        LOGGER.info("POST请求登录");
        HttpServletRequest request = null;
        if (StringUtils.isNotBlank(loginDto.getVerifycode())) {
            request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        }

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        // String loginpass = Hex.encodeHexString(password.getBytes());//变原文密码为密文密码
        String wxcode = loginDto.getWxcode();
        String logintype = loginDto.getLogintype();
        String verifycode = loginDto.getVerifycode();

        Result result = new Result();
        Subject user = SecurityUtils.getSubject();
        MyUsernamePasswordToken token = new MyUsernamePasswordToken();
        User userdb = new User();
        try {
            if ("wxlogin".equals(logintype.trim())) {
                SnsToken snsToken = SnsAPI.oauth2AccessToken(appid, secret, wxcode);
                weixin.popular.bean.user.User wxuser = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN");
                if (wxuser.isSuccess()) {
                    userdb = userService.findByOpenid(wxuser.getOpenid());
                    if (userdb == null) {
                        userdb = userService.initUserFromWxUser(wxuser);
                        userdb = userService.saveUser(userdb);
                    }
                    token = new MyUsernamePasswordToken(userdb.getWxopenid(), userdb.getWxopenid(), logintype);
                    user.getSession().setTimeout(1000 * 60 * 60 * 24); //24小时

                    user.login(token);
                } else {
                    result.setMsg("微信获取信息失败");
                    return result;
                }
            }
            if ("ptlogin".equals(logintype)) {
                if (StringUtils.isBlank(username)) {
                    result.setMsg("用户名不能为空");
                    return result;
                }
                if (StringUtils.isBlank(password)) {
                    result.setMsg("密码不能为空");
                    return result;
                }
                if (StringUtils.isBlank(verifycode)) {
                    result.setMsg("验证码不能为空");
                    return result;
                }
                if (!StringUtils.isBlank(verifycode)) {
                    String verifyCode = (String) request.getSession().getAttribute("verifyCode");
                    if (!verifyCode.equals(verifycode)) {
                        result.setMsg("验证码错误");
                        return result;
                    }

                }

                userdb = userService.findUserByLoginName(username);
                token = new MyUsernamePasswordToken(username.trim(), password.trim(), logintype);
                token.setRememberMe(loginDto.getRememberMe() == null ? false : loginDto.getRememberMe());

                user.login(token);
            }
            if ("khlogin".equals(logintype)) {
                if (StringUtils.isBlank(username)) {
                    result.setMsg("用户名不能为空");
                    return result;
                }
                if (StringUtils.isBlank(password)) {
                    result.setMsg("密码不能为空");
                    return result;
                }


                Kh kh = khService.findByLoginnameAndLoginpass(username, password);
                token = new MyUsernamePasswordToken(username, password, logintype);
                token.setRememberMe(loginDto.getRememberMe() == null ? false : loginDto.getRememberMe());
                user.login(token);
                result.setSuccess(true);
                result.setObj(kh);
                return result;
            }


        } catch (UnknownAccountException e) {
            LOGGER.error("账号不存在：{}", e);
            result.setMsg("账号不存在");
            return result;
        } catch (DisabledAccountException e) {
            LOGGER.error("账号未启用：{}", e);
            result.setMsg("账号未启用");
            return result;
        } catch (IncorrectCredentialsException e) {
            LOGGER.error("密码错误：{}", e);
            result.setMsg("密码错误");
            return result;
        } catch (Exception e) {
            result.setMsg("登录失败{ " + e.getMessage() + "}");
            return result;
        }
        result.setSuccess(true);
        result.setObj(userdb);
        return result;
    }


    /**
     * 未授权
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/unauth", method = RequestMethod.GET)
    @ResponseBody
    public Result unauth(Model model) {
        Result result = new Result();
        result.setObj("未授权");
        result.setSuccess(false);
        return result;
    }


    /**
     * IoException 处理页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/ioexception", method = RequestMethod.GET)
    @ResponseBody
    public Result ioexception(Model model) {
        Result result = new Result();
        result.setObj("io错误");
        result.setSuccess(false);
        return result;
    }


    /**
     * 退出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Result logout(HttpServletRequest request) {
        LOGGER.info("登出");
        Subject subject = SecurityUtils.getSubject();
        Result result = new Result();
        subject.logout();
        result.setSuccess(true);
        return result;
    }

    /**
     * 强制踢出的跳转信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    public String kickout() {

        return "/ptloginKickout.html";
    }


    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 10, true, Color.WHITE, Color.BLACK, null);
        request.getSession().setAttribute("verifyCode", verifyCode);
        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", out);
            try {
                out.flush();
            } finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/khLsrz", method = RequestMethod.GET)
    @ResponseBody
    public Result khLsrz(@RequestParam String khwybs, @RequestParam String lsrzm) {
        Result result = new Result();

        try {

            Kh kh = khService.findbyWybsAndLsrzm(khwybs, lsrzm);
            if (kh != null) {
                result.setSuccess(true);
                result.setObj(kh);
                result.setMsg("认证成功");
            } else {
                result.setMsg("认证失败");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}

