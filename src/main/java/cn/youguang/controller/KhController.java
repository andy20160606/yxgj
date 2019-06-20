package cn.youguang.controller;


import cn.youguang.dto.ModLoginpassDto;
import cn.youguang.entity.Kh;
import cn.youguang.service.*;
import cn.youguang.util.PageInfo;
import cn.youguang.util.Result;
import cn.youguang.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description：状态管理
 */
@Controller
@RequestMapping("/kh")
@Api(value = "状态Controller", tags = {"客户操作接口"})
public class KhController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(KhController.class);


    @Autowired
    private KhService khService;

    @Autowired
    private CpService cpService;

    @Autowired
    private KhcpService khcpService;


    /**
     * 添加客户
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestBody Kh kh) {

        Result result = new Result();
        try {


            Kh khdb = khService.findByLoginname(kh.getLoginname());


            if (khdb != null) {
                result.setMsg("客户登录名重复不可添加");
                return result;
            }
            //    ztService.save(zt);
            kh.setLoginpass("123456");
            kh.setWybs(UUID.randomUUID().toString());
            khService.save(kh);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    /**
     * 修改客户密码
     *
     * @return
     */
    @RequestMapping(value = "/modLoginpassById", method = RequestMethod.POST)
    @ResponseBody
    public Result modLoginpassById(@RequestBody ModLoginpassDto modLoginpassDto) {

        Result result = new Result();
        try {

            Kh khdb = khService.findById(modLoginpassDto.getId());
            if (!khdb.getLoginpass().equals(modLoginpassDto.getOldpass())) {
                result.setMsg("老密码错误");
                return result;
            }
            khdb.setLoginpass(modLoginpassDto.getNewpass());
            khService.save(khdb);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }

    /**
     * 修改客户密码 通过用户名
     *
     * @return
     */
    @RequestMapping(value = "/modLoginpassByLoginname", method = RequestMethod.POST)
    @ResponseBody
    public Result modLoginpassByLoginname(@RequestBody ModLoginpassDto modLoginpassDto) {

        Result result = new Result();
        try {

            Kh khdb = khService.findByLoginname(modLoginpassDto.getLoginname());
            if (khdb == null) {
                result.setMsg("没有该用户，请确认用户名");
                return result;
            }


            if (!khdb.getLoginpass().equals(modLoginpassDto.getOldpass())) {
                result.setMsg("老密码错误");
                return result;
            }
            khdb.setLoginpass(modLoginpassDto.getNewpass());
            khService.save(khdb);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    /**
     * 更新
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Kh kh) {

        Result result = new Result();
        try {
            //    ztService.save(zt);
            khService.save(kh);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    /**
     * 更新
     *
     * @return
     */
    @RequestMapping(value = "/becomeViper", method = RequestMethod.POST)
    @ResponseBody
    public Result becomeViper(@RequestBody Long khId) {

        Result result = new Result();
        try {
            //    ztService.save(zt);
            Kh kh = khService.findById(khId);
            kh.setVip(1);
            khService.save(kh);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    @ApiOperation(value = "分页获取用户信息", notes = "")
    @RequestMapping(value = "findDataTables", method = RequestMethod.GET)
    @ResponseBody
    public Result findDataTables(@RequestParam(required = false) Integer type, @RequestParam(required = false) String khmc, @RequestParam(required = false) String sjhm, @ModelAttribute PageInfo pageInfo) {

        Result result = new Result();
        Map<String, Object> condition = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(khmc)) {
            condition.put("khmc", khmc.trim());
        }
        if (StringUtils.isNotBlank(sjhm)) {
            condition.put("sjhm", sjhm.trim());
        }
        if (type != null) {
            condition.put("type", type);
        }
        pageInfo.setCondition(condition);

        try {
            khService.findDataTables(pageInfo);
            result.setObj(pageInfo);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @ApiOperation(value = "获取用户未买和买过的产品", notes = "获取用户未买和买过的产品")
    @RequestMapping(value = "findKhExistAndOtherCps", method = RequestMethod.GET)
    @ResponseBody
    public Result findKhExistAndOtherCps(@RequestParam Long khId) {

        Result result = new Result();
        Map<String, Object> data = new HashMap<String, Object>();

        Kh kh = khService.findById(khId);


        try {
            data.put("exist", khcpService.findByKhId(khId));
            data.put("other", khService.initKhOtherCps(khId));
            result.setObj(data);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    /**
     * 得到当前登录用户
     *
     * @return
     */
    @RequestMapping(value = "/getCurrentLoginKh", method = RequestMethod.GET)
    @ResponseBody
    public Result getCurLoginKh() {

        Result result = new Result();
        try {
            String wybs = (String) SecurityUtils.getSubject().getPrincipal();
            //    ztService.save(zt);
            Kh kh = khService.findByWybs(wybs);
            result.setSuccess(true);
            result.setObj(kh);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


}
