package com.cardpay.mgt.riskblack.dao;

import com.cardpay.basic.base.mapper.BasicMapper;
import com.cardpay.mgt.riskblack.model.RiskBlackCustomerApprove;
import com.cardpay.mgt.riskblack.model.vo.RiskBlackCustomerApproveVo;

import java.util.List;
import java.util.Map;

public interface RiskBlackCustomerApproveMapper extends BasicMapper<RiskBlackCustomerApprove> {
    /**
     * 获取审批分页列表
     *
     * @param map 参数map
     * @return 审批分页列表
     */
    List<RiskBlackCustomerApproveVo> riskBlackCustomerApprovePageList(Map<String, Object> map);

    /**
     * 根据用户ID获取风险审批信息
     *
     * @param map 参数Map
     * @return 风险审批信息
     */
    RiskBlackCustomerApproveVo getCustomerApprove(Map<String, Object> map);
}