package com.cardpay.controller.customer;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.base.model.SelectModel;
import com.cardpay.controller.base.BaseController;
import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.customer.customerbasic.model.TCustomerBasic;
import com.cardpay.mgt.customer.customerbasic.service.CustomerBasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户controller
 *
 * @author wangpeng
 */
@Api(value = "/customerBasic", description = "客户")
@Controller
@RequestMapping("/customerBasic")
public class CustomerBasicController extends BaseController<TCustomerBasic, Long> {
    @Autowired
    private CustomerBasicService customerBasicService;

    /**
     * 获取证件类型列表
     *
     * @return 证件类型列表
     */
    @ResponseBody
    @GetMapping("/certList")
    @ApiOperation(value = "获取证件类型列表", notes = "证件类型列表", httpMethod = "GET")
    public ResultTo getCertList() {
        List<SelectModel> customerBasicServiceCert = customerBasicService.getCert();
        return new ResultTo().setData(customerBasicServiceCert);
    }

    /**
     * 获取文化程度
     *
     * @return 文化程度列表
     */
    @ResponseBody
    @GetMapping("/educationDegreeList")
    @ApiOperation(value = "获取文化程度", notes = "文化程度", httpMethod = "GET")
    public ResultTo getEducationDegreeList() {
        List<SelectModel> educationDegree = customerBasicService.getEducationDegree();
        return new ResultTo().setData(educationDegree);
    }

    /**
     * 获取婚姻状况
     *
     * @return 婚姻状况列表
     */
    @ResponseBody
    @GetMapping("/marriageStatusList")
    @ApiOperation(value = "获取婚姻状况", notes = "婚姻状况", httpMethod = "GET")
    public ResultTo getMarriageStatusList() {
        List<SelectModel> marriageStatus = customerBasicService.getMarriageStatus();
        return new ResultTo().setData(marriageStatus);
    }

    /**
     * 获取客户状态
     *
     * @return 客户状态列表
     */
    @ResponseBody
    @GetMapping("/customerStatusList")
    @ApiOperation(value = "客户状态列表", notes = "客户状态", httpMethod = "GET")
    public ResultTo getCustomerStatusList() {
        List<SelectModel> customerStatus = customerBasicService.getCustomerStatus();
        return new ResultTo().setData(customerStatus);
    }

    /**
     * 获取潜在客户列表
     *
     * @return 潜在客户列表
     */
    @ResponseBody
    @GetMapping("/prospectiveCustomers")
    @ApiOperation(value = "查询潜在客户列表", notes = "潜在客户列表", httpMethod = "GET")
    public ResultTo getProspectiveCustomers() {
        List<TCustomerBasic> customerBasics = customerBasicService.getProspectiveCustomers(ShiroKit.getUserId());
        return new ResultTo().setData(customerBasics);
    }

    /**
     *  验证证件号码是否已经存在
     * @param identityCard 证件号码
     * @return true/false
     */
    @ResponseBody
    @GetMapping("/idCardExist")
    @ApiOperation(value = "证件号码验重", notes = "证件号码验重", httpMethod = "GET")
    public ResultTo validate(@ApiParam(value = "证件号码", required = true) int identityCard){
        boolean idCardExist = customerBasicService.isIdCardExist(identityCard);
        return new ResultTo().setData(idCardExist);
    }

}
