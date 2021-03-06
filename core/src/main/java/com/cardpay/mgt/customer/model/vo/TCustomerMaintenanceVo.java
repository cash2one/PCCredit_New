package com.cardpay.mgt.customer.model.vo;

import com.cardpay.basic.base.model.GenericEntity;
import com.cardpay.mgt.customer.model.TCustomerBasic;
import com.cardpay.mgt.user.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Column;


/**
 * 客户维护信息Vo
 * @author chenkai 2016/12/22 13:30
 */
@Lazy
@ApiModel(value = "客户维护信息Vo")
public class TCustomerMaintenanceVo extends GenericEntity<Integer> {
    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id", required = true)
    private Integer id;

    /**
     *证件号码
     */
    @ApiModelProperty(value = "证件号码", required = true)
    private String certificateNumber;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式", required = true)
    private String tel;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称", required = true)
    private String cname;

    /**
     * 维护人id(用户id)
     */
    @ApiModelProperty(value="维护人id(用户id)",required = true)
    private Integer operationId;

    /**
     * 维护人信息
     */
    @ApiModelProperty(value="维护人信息",required = true)
    private User operation;

    /**
     * 客户类型
     */
    private Integer customerType;

    /**
     * 企业法人姓名
     */
    private String enterpriseCname;

    /**
     * 企业法人身份证号
     */
    private String enterpriseIdCard;

    /**
     * 客户信息
     */
    private TCustomerBasic customer;

    public TCustomerBasic getCustomer() {
        return customer;
    }

    public void setCustomer(TCustomerBasic customer) {
        this.customer = customer;
    }

    public String getEnterpriseCname() {
        return enterpriseCname;
    }

    public void setEnterpriseCname(String enterpriseCname) {
        this.enterpriseCname = enterpriseCname;
    }

    public String getEnterpriseIdCard() {
        return enterpriseIdCard;
    }

    public void setEnterpriseIdCard(String enterpriseIdCard) {
        this.enterpriseIdCard = enterpriseIdCard;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }


    public User getOperation() {
        return operation;
    }

    public void setOperation(User operation) {
        this.operation = operation;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public Integer getPK() {
        return id;
    }
}
