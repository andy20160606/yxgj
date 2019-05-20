package cn.youguang.controller;


import cn.youguang.entity.Cp;
import cn.youguang.entity.Cpdd;
import cn.youguang.entity.User;
import cn.youguang.entity.Yhhd;
import cn.youguang.service.CpService;
import cn.youguang.service.CpddService;
import cn.youguang.service.UserService;
import cn.youguang.service.YhhdService;
import cn.youguang.util.PageInfo;
import cn.youguang.util.Result;
import cn.youguang.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description：产品订单
 */
@Controller
@RequestMapping("/cpdd")
@Api(value = "产品订单Controller", tags = {"产品订单操作接口"})
public class CpddController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CpddController.class);


    @Autowired
    private CpService cpService;

    @Autowired
    private CpddService cpddService;


    @Autowired
    private UserService userService;

    @Autowired
    private YhhdService yhhdService;


    /**
     * 分页读取产品订单
     *
     * @param
     * @param pageInfo
     * @return
     */

    @ApiOperation(value = "分页获取产品订单信息", notes = "pageinfo必须给定nowpage（当前页），pagesize（每页的记录数信息）,sort为排序（默认id，可给可不给）")
    @RequestMapping(value = "datatables", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo dataTables(@ApiParam(name = "cpmc", value = "产品名称") @RequestParam(required = false) String ddlx, @ApiParam(name = "userId", value = "用户id") @RequestParam(required = false) Long userId, @ApiParam(name = "ddzt", value = "订单状态") @RequestParam(required = false) Integer ddzt, @ModelAttribute PageInfo pageInfo) {


        Map<String, Object> condition = new HashMap<String, Object>();
        if (ddlx != null) {
            condition.put("ddlx", ddlx);
        }
        if (userId != null) {
            condition.put("userId", userId);
        }
        if (ddzt != null) {
            condition.put("ddzt", ddzt);
        }

        pageInfo.setCondition(condition);
        cpddService.findDataTables(pageInfo);
        return pageInfo;
    }


    @ApiOperation(value = "集合获取产品订单信息", notes = "cpmc,hymc为查询条件")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@ApiParam(name = "order", value = "排序字段") @RequestParam(required = false) String order, @ApiParam(name = "sort", value = "asc or desc") @RequestParam(required = false) String sort, @ApiParam(name = "cpmc", value = "产品名称") @RequestParam(required = false) String cpmc, @ApiParam(name = "userId", value = "用户id") @RequestParam(required = false) Long userId, @ApiParam(name = "ddzt", value = "订单状态") @RequestParam(required = false) Integer ddzt, @ApiParam(name = "hdId", value = "活动ID") @RequestParam(required = false) Long hdId) {

        Result result = new Result();
        List<Cpdd> cpdds;
        Map<String, Object> condition = new HashMap<String, Object>();

        if (cpmc != null) {
            condition.put("cpmc", cpmc);
        }
        if (userId != null) {
            condition.put("userId", userId);
        }
        if (ddzt != null) {
            condition.put("ddzt", ddzt);
        }
        if (hdId != null) {
            condition.put("hdId", hdId);
        }


        if (StringUtils.isNotBlank(order)) {
            condition.put("order", order);
        }
        if (StringUtils.isNotBlank(sort)) {
            condition.put("sort", sort);
        }

        try {
            cpdds = cpddService.findList(condition);
            result.setObj(cpdds);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @ApiOperation(value = "集合获取产品订单信息", notes = "Yhhd ID 为查询条件")
    @RequestMapping(value = "listByHdId", method = RequestMethod.GET)
    @ResponseBody
    public Result listByHdId(@ModelAttribute PageInfo pageInfo, @ApiParam(name = "hdId", value = "活动Id") @RequestParam Long hdId) {

        Result result = new Result();
        List<Cpdd> cpdds;

        try {
            cpdds = cpddService.findListByHdIdAndPageInfo(hdId, pageInfo);
            result.setObj(cpdds);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @ApiOperation(value = "集合获取产品(仅限于产品)订单信息", notes = "Yhhd ID 为查询条件")
    @RequestMapping(value = "listOnlyCps", method = RequestMethod.GET)
    @ResponseBody
    public Result listOnlyCps(@ModelAttribute PageInfo pageInfo) {

        Result result = new Result();
        List<Cpdd> cpdds;

        try {
            cpdds = cpddService.findListOnlyCps(pageInfo);
            result.setObj(cpdds);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @ApiOperation(value = "集合获取产品订单信息()", notes = "dqzt 订单读取状态 0 为未读， 1 为已读为查询条件")
    @RequestMapping(value = "listByDqzt", method = RequestMethod.GET)
    @ResponseBody
    public Result listByYdzt(@RequestParam Integer ydzt) {

        Result result = new Result();
        List<Cpdd> cpdds;

        try {
            cpdds = cpddService.findListByDqzt(ydzt);
            result.setObj(cpdds);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @ApiOperation(value = "获取产品订单未读消息 做分组归类", notes = "dqzt 订单读取状态 0 为未读， 1 为已读为查询条件")
    @RequestMapping(value = "groupByYhhdAndCp", method = RequestMethod.GET)
    @ResponseBody
    public Result groupByYhhdAndCpUseYdzt(@RequestParam Integer ydzt) {

        Result result = new Result();
        List<Map<String, String>> list = new ArrayList<>();
        try {
            list = cpddService.groupByYhhdAndCpUseYdzt(ydzt);
            result.setObj(list);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @RequestMapping(value = "updateYdztToOne", method = RequestMethod.GET)
    @ResponseBody
    public Result updateYdztToOneUsingIds(@RequestParam Long[] ids) {

        Result result = new Result();

        try {
            cpddService.updateYdztToOneUsingIds(ids);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }

    @RequestMapping(value = "updateRydztToOne", method = RequestMethod.GET)
    @ResponseBody
    public Result updateRydztToOne(@RequestParam Long[] ids) {

        Result result = new Result();

        try {
            cpddService.updateRydztToOneUsingIds(ids);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @RequestMapping(value = "updateYdztToOneUsingHdId", method = RequestMethod.GET)
    @ResponseBody
    public Result updateYdztToOneUsingHdId(@RequestParam Long hdId) {

        Result result = new Result();
        Map<String, Long> data = new HashMap<>();
        try {
            cpddService.updateYdztToOneUsingHdId(hdId);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }

    @RequestMapping(value = "updateYdztToOneUsingCp", method = RequestMethod.GET)
    @ResponseBody
    public Result updateYdztToOneUsingCp() {

        Result result = new Result();
        Map<String, Long> data = new HashMap<>();
        try {
            cpddService.updateYdztToOneUsingCp();
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }
        return result;

    }


    /**
     * 添加产品订单
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@ApiParam(name = "userId", value = "用户ID") @RequestParam Long userId, @ApiParam(name = "hdId", value = "活動Id") @RequestParam(required = false) Long hdId, @ApiParam(name = "cpId", value = "产品Id") @RequestParam(required = false) Long cpId, @RequestBody Cpdd cpdd) {
        Result result = new Result();
        try {
            User user = userService.findUserById(userId);
            cpdd.setUser(user);
            if (cpId != null) {
                Cp cp = cpService.findById(cpId);
                cpdd.setCp(cp);
                cpdd.setYdzt(0);
            }
            if (hdId != null) {
                Yhhd yhhd = yhhdService.findById(hdId);
                cpdd.setYhhd(yhhd);
            }
            cpdd.setXdsj(new Date());
            cpdd = cpddService.save(cpdd);
            cpdd.setYdzt(0);
            cpdd.setActive(1);
            cpdd.setRydzt(0);
            result.setObj(cpdd);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;

    }


    /**
     * 更新产品订单
     *
     * @param
     * @return
     */
    @RequiresPermissions("/none/authc")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result edit(@ApiParam(name = "userId", value = "用户ID") Long userId, @ApiParam(name = "cpId", value = "产品Id") Long cpId, @RequestBody Cpdd cpdd) {
        Result result = new Result();
        try {
            User user = userService.findUserById(userId);
            Cp cp = cpService.findById(cpId);
            cpdd.setUser(user);
            cpdd.setCp(cp);
            cpddService.save(cpdd);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMsg(e.getMessage());

        }

        return result;
    }

    /**
     * 删除产品订单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam Long id) {
        Result result = new Result();
        try {
            Cpdd cpdd = cpddService.findById(id);
            cpdd.setActive(0);
            cpddService.save(cpdd);
            result.setMsg("隐藏成功！");
            result.setSuccess(true);
        } catch (RuntimeException e) {
            LOGGER.error("隐藏成功：{}", e);
            result.setMsg(e.getMessage());

        }
        return result;
    }


}
