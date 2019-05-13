package cn.youguang.controller;


import cn.youguang.entity.Cp;
import cn.youguang.entity.Hy;
import cn.youguang.entity.Zt;
import cn.youguang.service.CpService;
import cn.youguang.service.HyService;
import cn.youguang.service.ZtService;
import cn.youguang.util.PageInfo;
import cn.youguang.util.Result;
import cn.youguang.util.StringUtils;
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
 * @description：行业管理
 */
@Controller
@RequestMapping("/cp")
@Api(value = "产品Controller", tags = {"产品操作接口"})
public class CpController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CpController.class);


    @Autowired
    private CpService cpService;

    @Autowired
    private HyService hyService;
    @Autowired
    private ZtService ztService;


    /**
     * 分页读取行业列表
     *
     * @param hymc
     * @param pageInfo
     * @return
     */

    @ApiOperation(value = "分页获取产品信息", notes = "pageinfo必须给定nowpage（当前页），pagesize（每页的记录数信息）,sort为排序（默认id，可给可不给）")
    @RequestMapping(value = "datatables", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo dataTables(@ApiParam(name = "cplb", value = "产品類別") @RequestParam(required = false) String cplb, @ApiParam(name = "cpmc", value = "产品名称") @RequestParam(required = false) String cpmc, @ApiParam(name = "hymc", value = "行业名称") @RequestParam(required = false) String hymc, @ModelAttribute PageInfo pageInfo) {


        Map<String, Object> condition = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(cpmc)) {
            condition.put("cpmc", hymc);
        }
        if (StringUtils.isNotBlank(hymc)) {
            condition.put("hymc", hymc);
        }
        if (StringUtils.isNotBlank(cplb)) {
            condition.put("cplb", cplb);
        }


        pageInfo.setCondition(condition);
        cpService.findDataTables(pageInfo);
        return pageInfo;
    }


    @ApiOperation(value = "集合获取产品信息", notes = "cpmc,hymc为查询条件")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@ApiParam(name = "sort", value = "排序字段") @RequestParam(required = false) String sort, @ApiParam(name = "order", value = "升序还是降序传 asc 或者 desc") @RequestParam(required = false) String order, @ApiParam(name = "hymc", value = "行业名称") @RequestParam(required = false) String hymc, @ApiParam(name = "cpmc", value = "产品名称") @RequestParam(required = false) String cpmc) {

        Result result = new Result();
        Map<String, Object> condition = new HashMap<String, Object>();
        List<Cp> cps;

        if (cpmc != null) {
            condition.put("cpmc", cpmc);
        }
        if (hymc != null) {
            condition.put("hymc", hymc);
        }

        if (StringUtils.isNotBlank(sort)) {
            condition.put("sort", sort);
        }

        if (StringUtils.isNotBlank(sort)) {
            condition.put("order", order);
        }


        try {
            cps = cpService.findList(condition);
            result.setObj(cps);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    /**
     * 添加产品
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestParam Long[] hys, @RequestParam Long[] zts, @RequestBody Cp cp) {

        Result result = new Result();
        try {
            List<Hy> hyList = hyService.findByIds(hys);
            List<Zt> ztList = ztService.findByIds(zts);
            cp.setHys(hyList);
            cp.setZts(ztList);
            cpService.save(cp);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Result get(@RequestParam Long id) {
        Result result = new Result();
        try {
            Cp cp = cpService.findById(id);
            result.setSuccess(true);
            result.setObj(cp);
        } catch (RuntimeException e) {
            result.setMsg(e.getMessage());

        }
        return result;
    }


    /**
     * 更新产品
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/editPage", method = RequestMethod.GET)
    @ResponseBody
    public Result editPage(@RequestParam Long id) {
        Result result = new Result();
        try {
            Cp cp = cpService.findById(id);
            result.setSuccess(true);
            result.setMsg("修改成功！");
            result.setObj(cp);
        } catch (RuntimeException e) {
            result.setMsg(e.getMessage());

        }
        return result;
    }


    /**
     * 更新产品
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result edit(@RequestBody Cp cp) {
        Result result = new Result();
        try {
            cpService.save(cp);
            result.setSuccess(true);
            result.setMsg("修改成功！");
        } catch (RuntimeException e) {
            result.setMsg(e.getMessage());

        }
        return result;
    }


}
