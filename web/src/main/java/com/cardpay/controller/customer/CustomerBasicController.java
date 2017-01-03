package com.cardpay.controller.customer;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.base.model.SelectModel;
import com.cardpay.basic.common.annotation.SystemControllerLog;
import com.cardpay.basic.common.constant.ConstantEnum;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.basic.util.datatable.DataTablePage;
import com.cardpay.controller.base.BaseController;
import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.customer.model.TCustomerBasic;
import com.cardpay.mgt.customer.model.TCustomerIndustry;
import com.cardpay.mgt.customer.service.TCustomerBasicService;
import com.cardpay.mgt.customer.service.TCustomerIndustryService;
import com.cardpay.mgt.customer.service.TCustomerMaintenanceService;
import com.cardpay.mgt.customer.service.impl.TCustomerTransferServiceImpl;
import com.cardpay.mgt.industry.model.TIndustry;
import com.cardpay.mgt.industry.service.IndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户controller
 *
 * @author chenkai
 */
@Api(value = "/customerBasic", description = "客户基本信息")
@Controller
@RequestMapping("/customerBasic")
public class CustomerBasicController extends BaseController<TCustomerBasic> {
    @Autowired
    private TCustomerBasicService customerBasicService;

    @Autowired //行业信息
    private IndustryService industryService;

    @Autowired //客户行业信息
    private TCustomerIndustryService tCustomerIndustryService;

    @Autowired //客户维护服务
    private TCustomerMaintenanceService tCustomerMaintenanceService;

    @Autowired //客户移交
    private TCustomerTransferServiceImpl tCustomerTransferService;

    /**
     * 获取潜在客户列表
     *
     * @return 潜在客户列表
     */
    @ResponseBody
    @GetMapping("/prospectiveCustomers")
    @SystemControllerLog(description = "获取潜在客户列表")
    @ApiOperation(value = "查询潜在客户列表", notes = "潜在客户列表", httpMethod = "GET")
    public ResultTo getProspectiveCustomers() {
        List<TCustomerBasic> customerBasics = customerBasicService.getProspectiveCustomers(ShiroKit.getUserId());
        return new ResultTo().setData(customerBasics);
    }

    /**
     * 验证证件号码是否已经存在
     *
     * @param identityCard 证件号码
     * @return true/false
     */
    @ResponseBody
    @GetMapping("/idCardExist")
    @SystemControllerLog(description = "验证证件号码是否已经存在")
    @ApiOperation(value = "证件号码验重", notes = "证件号码验重", httpMethod = "GET")
    public ResultTo validate(@ApiParam(value = "证件号码", required = true) @RequestParam long identityCard) {
        boolean idCardExist = customerBasicService.isIdCardExist(identityCard);
        return new ResultTo().setData(idCardExist);
    }

    /**
     * 更新客户基本信息
     *
     * @param tCustomerBasic 客户基本信息
     * @return 数据库变更数量
     */
    @ResponseBody
    @PutMapping
    @SystemControllerLog(description = "更新客户基本信息")
    @ApiOperation(value = "更新客户基本信息", notes = "更新客户基本信息", httpMethod = "PUT")
    public ResultTo update(@ApiParam(value = "客户基本信息", required = true) @ModelAttribute TCustomerBasic tCustomerBasic) {
        int count = updateAndCompareBean(tCustomerBasic, "customerBasic", "客户基本信息");
        return count != 0 ? new ResultTo().setData(count) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 新建客戶经理
     *
     * @param tCustomerBasic 客户基本信息
     * @param industry       行业id
     * @return 客户id
     */
    @ResponseBody
    @PostMapping
    @SystemControllerLog(description = "新建客戶经理")
    @ApiOperation(value = "新建客戶", notes = "新建客戶经理", httpMethod = "POST")
    public ResultTo newCustomer(@ApiParam(value = "客户基本信息", required = true) @ModelAttribute TCustomerBasic tCustomerBasic
            , @ApiParam(value = "行业id(,分割)", required = true) @RequestParam String industry) {
        Integer userId = ShiroKit.getUserId();
        tCustomerBasic.setCustomerManagerId(userId);
        tCustomerBasic.setCreateBy(userId);
        Integer count = customerBasicService.insertSelective(tCustomerBasic);
        if (count != null && count != 0) {
            String[] split = industry.split(",");
            List<TCustomerIndustry> list = new ArrayList<>();
            for (String id : split) {
                int industryId = Integer.parseInt(id);
                TCustomerIndustry tCustomerIndustry = new TCustomerIndustry();
                tCustomerIndustry.setCustomerId(tCustomerBasic.getId());
                tCustomerIndustry.setIndustryId(industryId);
                list.add(tCustomerIndustry);
            }
            int insert = tCustomerIndustryService.batchInsert(list);
            return insert != 0 ? new ResultTo().setData(tCustomerBasic.getId()) : new ResultTo(ResultEnum.SERVICE_ERROR);
        }
        return new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 跳转新建客户经理页面
     *
     * @return 客户经理新建页面
     */
    @GetMapping
    @SystemControllerLog(description = "跳转新建客户经理页面")
    @ApiOperation(value = "跳转客户经理新建页面", notes = "客户经理新建页面", httpMethod = "GET")
    public ModelAndView returnNewCustomer() {
        ModelAndView modelAndView = new ModelAndView("/customer/new");
        modelAndView.addObject("dropDownList", selectInput());
        return modelAndView;
    }

    /**
     * 跳转查询客户页面
     *
     * @return 客户列表
     */
    @GetMapping("/index")
    @SystemControllerLog(description = "跳转查询客户页面")
    @ApiOperation(value = "跳转查询客户页面", notes = "查询客户页面", httpMethod = "GET")
    public ModelAndView returnCustomerList() {
        return new ModelAndView("customer/index");
    }

    /**
     * 按条件查询客户经理信息
     *
     * @return 客户信息
     */
    @ResponseBody
    @GetMapping("/condition")
    @SystemControllerLog(description = "按条件查询客户经理信息")
    @ApiOperation(value = "按条件查询客户经理信息", notes = "查询客户经理信息", httpMethod = "GET")
    public DataTablePage queryCondition() {
        Map<String, Object> map = new HashMap<>();
        map.put("customerManagerId", ShiroKit.getUserId());
        return dataTablePage("queryCustomerByCondition", map);
    }

    /**
     * 客户更新页面跳转
     *
     * @param customerId 客户id
     * @return 更新页面
     */
    @GetMapping("/{id}")
    @SystemControllerLog(description = "客户更新页面跳转")
    @ApiOperation(value = "按id查询客户基本信息", notes = "查询客户经理信息 返回参数名称:tCustomerBasic", httpMethod = "GET")
    public ModelAndView returnUpdate(@ApiParam(value = "客户id", required = true) @PathVariable("id") int customerId) {
        ModelAndView modelAndView = new ModelAndView("/customer/update");
        TCustomerBasic tCustomerBasic = customerBasicService.selectByPrimaryKey(customerId);
        TCustomerIndustry tCustomerIndustry = new TCustomerIndustry();
        tCustomerIndustry.setCustomerId(customerId);
        List<TCustomerIndustry> tCustomerIndustryList = tCustomerIndustryService.select(tCustomerIndustry);
        modelAndView.addObject("dropDownList", selectInput());
        modelAndView.addObject("tCustomerBasic", tCustomerBasic);
        modelAndView.addObject("industryList", tCustomerIndustryList);
        return modelAndView;
    }


    /**
     * 查询客户编辑页面下拉框信息
     *
     * @return 客户编辑页面下拉框信息
     */
    private Map<String, Object> selectInput() {
        Map<String, Object> dropDownList = new HashMap<>();
        List<SelectModel> cert = customerBasicService.getCert();
        List<SelectModel> educationDegree = customerBasicService.getEducationDegree();
        List<SelectModel> marriageStatus = customerBasicService.getMarriageStatus();
        List<TIndustry> tIndustries = industryService.selectAll();
        dropDownList.put("cert", cert);
        dropDownList.put("educationDegree", educationDegree);
        dropDownList.put("marriageStatus", marriageStatus);
        dropDownList.put("industries", tIndustries);
        return dropDownList;
    }

    /**
     * 批量删除用户
     *
     * @param customerIds 客户id(,分割)
     * @return 删除
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    @SystemControllerLog(description = "批量删除用户")
    @ApiOperation(value = "批量删除用户", notes = "改变用户状态将用户设为不可用", httpMethod = "DELETE")
    public ResultTo deleteCustomer(@ApiParam("客户id(,分割)") @PathVariable("id") String customerIds) {
        List<Integer> ids = new ArrayList<>();
        String[] split = customerIds.split(",");
        for (String id : split) {
            int customerId = Integer.parseInt(id);
            ids.add(customerId);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("status", ConstantEnum.CustomerStatus.STATUS3.getVal());
        map.put("customerIds", ids);
        map.put("managerId", ShiroKit.getUserId());
        int count = customerBasicService.updateStatus(map);
        return count != 0 ? new ResultTo().setData(count) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }

    /**
     * 查看客户信息
     *
     * @param customerId 客户id
     * @return 客户信息页面
     */
    @GetMapping("/customerInfo/{id}")
    @ApiOperation(value = "查看客户信息", notes = "查看客户信息, 返回参数: tCustomerBasic", httpMethod = "GET")
    public ModelAndView index(@ApiParam(value = "客户id", required = true) @PathVariable("id") int customerId) {
        ModelAndView modelAndView = new ModelAndView("/customer/customerInfo");
        TCustomerBasic tCustomerBasic = customerBasicService.selectByPrimaryKey(customerId);
        modelAndView.addObject("tCustomerBasic", tCustomerBasic);
        return modelAndView;
    }

    /**
     * 查询可删除的客户经理
     *
     * @return 可删除的客户经理信息
     */
    @GetMapping("/all")
    @ApiOperation(value = "查询可删除的客户经理", notes = "查询可删除的客户经理", httpMethod = "GET")
    public DataTablePage selectDelete() {
        Map<String, Object> map = new HashMap();
        map.put("managerId", ShiroKit.getUserId());
        return dataTablePage("selectDelete", map);
    }

    /**
     * 删除客户信息
     * @param customerId 客户id
     * @return 数据库变记录
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除客户经理信息", notes = "删除客户经理信息", httpMethod = "DELETE")
    public ResultTo delete(@ApiParam(value = "客户经理id", required = true) @RequestParam int customerId) {
        Integer count = customerBasicService.deleteCustomer(customerId);
        return count != 0 ? new ResultTo().setData(count) : new ResultTo(ResultEnum.SERVICE_ERROR);
    }
}
