package com.cardpay.mgt.user.service;

import com.cardpay.basic.base.service.BaseService;
import com.cardpay.mgt.user.model.UserOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户和机构关联接口
 *
 * @author rankai
 *         createTime 2016-12-2016/12/23 14:40
 */
public interface UserOrganizationService extends BaseService<UserOrganization> {

    /**
     * 查询此机构是否含有用户
     * @param organizationId 机构id
     * @return 用户数量
     */
    int queryUserOrg(int organizationId);

    /**
     * 按进件Id查询所属机构信息
     * @param applicationId 进件id
     * @return 用户机构信息
     */
    List<UserOrganization> queryOrgByAppId(int applicationId);
}
