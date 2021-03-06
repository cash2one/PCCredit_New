package com.cardpay.controller.customermanager;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.basic.util.datatable.DataTablePage;
import com.cardpay.controller.base.BaseController;
import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.customer.model.TCustomerBasic;
import com.cardpay.mgt.customermanager.basic.model.TCustomerManager;
import com.cardpay.mgt.customermanager.basic.service.CustomerManagerService;
import com.cardpay.mgt.customermanager.level.model.TCustomerManagerLevel;
import com.cardpay.mgt.customermanager.level.service.CustomerManagerLevelService;
import com.cardpay.mgt.datadictionary.model.TDataDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户经理级别controller
 *
 * @author yanweichen
 */
@Api(value = "/api/customerManagerLevel", description = "客户经理级别")
@RestController
@RequestMapping("/api/customerManagerLevel")
public class CustomerManagerLevelController extends BaseController<TCustomerManagerLevel> {

    @Autowired
    private CustomerManagerLevelService customerManagerLevelService;

    @Autowired
    private CustomerManagerService customerManagerService;

    @GetMapping("/all")
    @ApiOperation(value = "获取客户经理级别列表", notes = "获取客户经理级别列表",  httpMethod = "GET")
    public ResultTo All(){
        ResultTo resultTo = new ResultTo();
        resultTo.setData(customerManagerLevelService.getCustomerManagerLevel());
        return resultTo;
    }

    /**
     * 获取客户经理级别分页数据
     *
     * @return 分页数据
     */
    @RequestMapping("/pageList")
    @ApiOperation(value = "获取客户经理级别分页数据", notes = "获取客户经理级别分页数据",  httpMethod = "GET")
    public DataTablePage pageList(){
        return dataTablePage();
    }

    /**
     * 添加客户经理级别接口
     *
     * @param tCustomerManagerLevel 客户经理级别信息
     * @return 页面
     */
    @PostMapping()
    @ApiOperation(value = "添加客户经理级别接口", notes = "添加客户经理级别",  httpMethod = "POST")
    public ResultTo add(@ApiParam("客户经理级别信息") @ModelAttribute TCustomerManagerLevel tCustomerManagerLevel){
        tCustomerManagerLevel.setCreateBy(ShiroKit.getUserId());
        tCustomerManagerLevel.setCreateTime(new Date());
        tCustomerManagerLevel.setModifyBy(ShiroKit.getUserId());
        tCustomerManagerLevel.setModifyTime(new Date());
        Integer result = customerManagerLevelService.insert(tCustomerManagerLevel);
        ResultTo resultTo = new ResultTo();
        resultTo.setIsSuccess(result);
        return resultTo;
    }

    /**
     * 删除客户经理级别接口
     *
     * @param levelId 客户经理级别id
     * @return 删除结果
     */
    @DeleteMapping("/{levelId}")
    @ApiOperation(value = "删除客户经理级别接口", notes = "删除客户经理级别",  httpMethod = "DELETE")
    public ResultTo delete(@PathVariable("levelId") Integer levelId){
        ResultTo resultTo = new ResultTo();
        TCustomerManager customerManager = new TCustomerManager();
        customerManager.setLevelId(levelId);
        //该级别下如果有客户经理则不能删除
        Integer count = customerManagerService.selectCount(customerManager);
        if(count > 0){
            resultTo.setMsg(ResultEnum.OPERATION_FAILED.getValue(),"该级别下含有客户经理,无法删除");
            return resultTo;
        }
        Integer result = customerManagerLevelService.deleteByPrimaryKey(levelId);
        resultTo.setIsSuccess(result);
        return resultTo;
    }

    /**
     * 根据id获取客户经理级别
     *
     * @param levelId 客户经理级别id
     * @return 页面和数据
     */
    @GetMapping(value = "/{levelId}")
    @ApiOperation(value = "前往更新客户经理级别接口", notes = "根据ID获取客户经理级别信息,前往更新客户经理级别",  httpMethod = "GET")
    public ResultTo get(@PathVariable("levelId") Integer levelId){
        TCustomerManagerLevel customerManagerLevel = customerManagerLevelService.selectByPrimaryKey(levelId);
        ResultTo resultTo = new ResultTo();
        resultTo.setData(customerManagerLevel);
        return resultTo;
    }

    /**
     * 更新客户经理级别接口
     *
     * @param customerManagerLevel 客户经理级别信息
     * @return 页面
     */
    @PutMapping()
    @ApiOperation(value = "更新客户经理级别接口", notes = "根据ID更新客户经理级别信息",  httpMethod = "PUT")
    public ResultTo update(@ModelAttribute TCustomerManagerLevel customerManagerLevel){
        Integer result = customerManagerLevelService.updateSelectiveByPrimaryKey(customerManagerLevel);
        ResultTo resultTo = new ResultTo();
        resultTo.setIsSuccess(result);
        return resultTo;
    }

}
