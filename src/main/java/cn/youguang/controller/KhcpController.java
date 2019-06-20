package cn.youguang.controller;


import cn.youguang.entity.Cp;
import cn.youguang.entity.Kh;
import cn.youguang.entity.Khcp;
import cn.youguang.entity.Zt;
import cn.youguang.service.*;
import cn.youguang.util.PageInfo;
import cn.youguang.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description：状态管理
 */
@Controller
@RequestMapping("/khcp")
@Api(value = "khcpController", tags = {"客户产品操作接口"})
public class KhcpController {

    private Long khId;
    private Long cpId;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(KhcpController.class);


    @Autowired
    private HyService hyService;


    @Autowired
    private ZtService ztService;


    @Autowired
    private KhcpService khcpService;

    @Autowired
    private KhService khService;

    @Autowired
    private CpService cpService;


    /**
     * 添加用户产品
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestParam Long cpId, @RequestParam Long khId, @RequestBody Khcp khcp) {

        Result result = new Result();
        try {
            Cp cp = cpService.findById(cpId);
            Kh kh = khService.findById(khId);
            khcp.setCp(cp);
            khcp.setKh(kh);
            khcpService.save(khcp);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }

    /**
     * 续费用户产品
     *
     * @return
     */
    @RequestMapping(value = "/xf", method = RequestMethod.POST)
    @ResponseBody
    public Result xf(@RequestBody Khcp khcp) {

        Result result = new Result();
        try {
            Khcp khcpdb = khcpService.findById(khcp.getId());
            khcpdb.setStoptime(khcp.getStoptime());
            khcpService.save(khcpdb);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    /**
     * 删除状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Long id) {
        Result result = new Result();
        try {
            ztService.deleteById(id);
            result.setMsg("删除成功！");
            result.setSuccess(true);
        } catch (RuntimeException e) {
            LOGGER.error("删除失败：{}", e);
            result.setMsg(e.getMessage());

        }
        return result;
    }


    /**
     * @param
     * @return
     */
    @RequestMapping(value = "/checkKhcpYxq", method = RequestMethod.GET)
    @ResponseBody
    public Result checkKhcpYxq(@RequestParam Long khId, @RequestParam Long cpId) {
        this.khId = khId;
        this.cpId = cpId;
        Result result = new Result();
        try {
            Kh kh = khService.findById(khId);
            Cp cp = cpService.findById(cpId);
            Date now = new Date();
            List<Khcp> khcps = khcpService.findByKhAndCpAnAndStarttimeAfterAndStoptimeBefore(kh, cp, now, now);

            if (khcps != null && khcps.size() > 0) {
                result.setSuccess(true);
                result.setMsg("客户产品在有效期内");
                result.setObj(khcps);
            }

        } catch (RuntimeException e) {
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
