package com.cardpay.mgt.customer.customermaintenance.model.po;

import com.cardpay.basic.base.model.GenericEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

/**
 * 客户维护实体类
 * @author wangpeng
 */
@Table(name = "T_CUSTOMER_MAINTENANCE")
@ApiModel(value="客户维护记录")
public class TCustomerMaintenance extends GenericEntity<Long> {
    /**
     * 客户id(需要生成规则生成)
     */
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select CUSTOMER_MAINTENANCE_SEQ.nextval from dual")
    @ApiModelProperty(value="客户id(需要生成规则生成)",required = true)
    private Long id;

    /**
     * 维护名称
     */
    @Column(name = "CUSTOMER_CNAME")
    @ApiModelProperty(value="维护名称",required = true)
    private String customerCname;

    /**
     * 维护类型(0 电话联系 1 上门拜访 2 其他渠道)
     */
    @Column(name = "MAINTENANCE_TYPE")
    @ApiModelProperty(value="维护类型(0 电话联系 1 上门拜访 2 其他渠道)",required = true)
    private Integer maintenanceType;

    /**
     * 维护纪要内容
     */
    @Column(name = "MAINTENNACE_SUMMARY")
    @ApiModelProperty(value="维护纪要内容",required = true)
    private String maintennaceSummary;

    /**
     * 维护人id(用户id)
     */
    @Column(name = "OPERATION_ID")
    @ApiModelProperty(value="维护人id(用户id)",required = true)
    private Integer operationId;

    /**
     * 维护人name(用户name)
     */
    @Column(name = "OPERATION_NAME")
    @ApiModelProperty(value="维护人name(用户name)",required = true)
    private String operationName;

    /**
     * 维护时间
     */
    @Column(name = "OPERATION_TIME")
    @ApiModelProperty(value="维护时间",required = true)
    private Date operationTime;

    /**
     * 获取客户id(需要生成规则生成)
     *
     * @return ID - 客户id(需要生成规则生成)
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户id(需要生成规则生成)
     *
     * @param id 客户id(需要生成规则生成)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取维护名称
     *
     * @return CUSTOMER_CNAME - 维护名称
     */
    public String getCustomerCname() {
        return customerCname;
    }

    /**
     * 设置维护名称
     *
     * @param customerCname 维护名称
     */
    public void setCustomerCname(String customerCname) {
        this.customerCname = customerCname;
    }

    /**
     * 获取维护类型(0 电话联系 1 上门拜访 2 其他渠道)
     *
     * @return MAINTENANCE_TYPE - 维护类型(0 电话联系 1 上门拜访 2 其他渠道)
     */
    public Integer getMaintenanceType() {
        return maintenanceType;
    }

    /**
     * 设置维护类型(0 电话联系 1 上门拜访 2 其他渠道)
     *
     * @param maintenanceType 维护类型(0 电话联系 1 上门拜访 2 其他渠道)
     */
    public void setMaintenanceType(Integer maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    /**
     * 获取维护纪要内容
     *
     * @return MAINTENNACE_SUMMARY - 维护纪要内容
     */
    public String getMaintennaceSummary() {
        return maintennaceSummary;
    }

    /**
     * 设置维护纪要内容
     *
     * @param maintennaceSummary 维护纪要内容
     */
    public void setMaintennaceSummary(String maintennaceSummary) {
        this.maintennaceSummary = maintennaceSummary;
    }

    /**
     * 获取维护人id(用户id)
     *
     * @return OPERATION_ID - 维护人id(用户id)
     */
    public Integer getOperationId() {
        return operationId;
    }

    /**
     * 设置维护人id(用户id)
     *
     * @param operationId 维护人id(用户id)
     */
    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    /**
     * 获取维护人name(用户name)
     *
     * @return OPERATION_NAME - 维护人name(用户name)
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * 设置维护人name(用户name)
     *
     * @param operationName 维护人name(用户name)
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
     * 获取维护时间
     *
     * @return OPERATION_TIME - 维护时间
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * 设置维护时间
     *
     * @param operationTime 维护时间
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    public Long getPK() {
        return id;
    }
}