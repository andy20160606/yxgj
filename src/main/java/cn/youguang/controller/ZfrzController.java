package cn.youguang.controller;


import cn.youguang.entity.*;
import cn.youguang.service.*;
import cn.youguang.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description：转发日志
 */
@Controller
@RequestMapping("/zfrz")
@Api(value = "转发日志Controller", tags = {"转发日志操作接口"})
public class ZfrzController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ZfrzController.class);


    @Autowired
    private ZfrzService zfrzService;

    @Autowired
    private UserService userService;

    @Autowired
    private CpService cpService;

    @Autowired
    private YhhdService yhhdService;

    @Autowired
    private CpddService cpddService;


    /**
     * 添加产品
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestParam(name = "userId") Long userId, @RequestParam(name = "cpId", required = false) Long cpId, @RequestParam(name = "hdId", required = false) Long hdId, @RequestParam(name = "cpddId", required = false) Long cpddId, @RequestBody Zfrz zfrz) {

        Result result = new Result();
        try {

            User user = userService.findUserById(userId);
            Cp cp = cpId == null ? null : cpService.findById(cpId);
            Yhhd yhhd = hdId == null ? null : yhhdService.findById(hdId);
            Cpdd cpdd = cpddId == null ? null : cpddService.findById(cpddId);
            zfrz.setZfr(user);
            zfrz.setZfsj(new Date());
            zfrz.setCp(cp);
            zfrz.setYhhd(yhhd);
            zfrz.setLlcs(1L);
            zfrz.setCpdd(cpdd);
            zfrzService.save(zfrz);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list() {
        Result result = new Result();
        try {


            List<Zfrz> zfrzs = zfrzService.findAll();

            result.setSuccess(true);
            result.setObj(zfrzs);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }
        return result;
    }


    @RequestMapping(value = "/findByUserId", method = RequestMethod.GET)
    @ResponseBody
    public Result findByUserId(@RequestParam Long userId) {
        Result result = new Result();
        try {


            List<Zfrz> zfrzs = zfrzService.findByUserId(userId);

            result.setSuccess(true);
            result.setObj(zfrzs);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }
        return result;
    }


    @ApiOperation(value = "分享浏览次数累加", notes = "必须给定唯一标识")
    @RequestMapping(value = "/ppLlcsByWybs", method = RequestMethod.GET)
    @ResponseBody
    public Result ppLlcsByWybs(@RequestParam String wybs) {
        Result result = new Result();
        try {


            Zfrz zfrz = zfrzService.findByWybs(wybs);
            zfrz.setLlcs(zfrz.getLlcs() + 1L);
            zfrzService.save(zfrz);

            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }
        return result;
    }


}
