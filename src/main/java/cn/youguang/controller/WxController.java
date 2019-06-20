package cn.youguang.controller;


import cn.youguang.util.QRCodeKit;
import cn.youguang.util.QrCodeCreateUtil;
import cn.youguang.util.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.ticket.Ticket;
import weixin.popular.bean.token.Token;
import weixin.popular.util.JsUtil;
import weixin.popular.util.JsonUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @description：微信信息获取接口
 */
@Controller
@RequestMapping("/wx")
@Api(value = "微信Controller", tags = {"微信操作接口"})
public class WxController {


    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;

    @Value("${file.UploadDir}")
    private String filePath;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WxController.class);


    @RequestMapping(value = "getJsCfgByUrl", method = RequestMethod.GET)
    @ResponseBody
    public Result getJsCfgByUrl(@RequestParam String url) {
        Result result = new Result();
        try {
            Token token = TokenAPI.token(appid, secret);
            Ticket ticket = TicketAPI.ticketGetticket(token.getAccess_token());
            String jsCfg = JsUtil.generateConfigJson(ticket.getTicket(), true, appid, url, null);
            HashMap<String, String> jsCfgMap = JsonUtil.parseObject(jsCfg, HashMap.class);
            result.setObj(jsCfgMap);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        return result;
    }


    @RequestMapping(value = "/shareQrcodeByContent", method = RequestMethod.GET)
    public void shareQrcodeByContent(@RequestParam String content, HttpServletResponse response) {
        try {
            BufferedImage bufferedImage = QrCodeCreateUtil.createQrCode(content);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bufferedImage, "png", out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/createQRCodeWithLogo", method = RequestMethod.GET)
    public void shareQrcodeByContent(@RequestParam String content, @RequestParam String fileName, HttpServletResponse response) {
        try {


            File logoFile = new File(filePath + fileName);
            BufferedImage bufferedImage = QRCodeKit.createQRCodeWithLogo(content, logoFile);


            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bufferedImage, "png", out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
