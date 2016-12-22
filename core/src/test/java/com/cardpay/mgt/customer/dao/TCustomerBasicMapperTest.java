package com.cardpay.mgt.customer.dao;

import com.cardpay.basic.common.interceptor.mapper.ReturnMapParam;
import com.cardpay.mgt.customer.model.TCustomerBasic;
import com.cardpay.mgt.user.model.User;
import com.cardpay.util.TestEnv;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * 客户基本信息测试
 * @author chenkai 2016/12/20 11:39
 */
public class TCustomerBasicMapperTest extends TestEnv{
    @Autowired
    private TCustomerBasicMapper tCustomerBasicMapper;

    @Test
    public void getProspectiveCustomers() throws Exception {
        List<TCustomerBasic> prospectiveCustomers = tCustomerBasicMapper.getProspectiveCustomers(1);
        assertTrue(prospectiveCustomers.size() > 0);
    }

    @Test
    public void isIdCardExist() throws Exception {
        Integer idCardExist = tCustomerBasicMapper.isIdCardExist(11);
        assertTrue(idCardExist > 0);
    }

    @Test
    @Ignore
    public void queryCustomer() throws Exception {
        ReturnMapParam returnMapParam = new ReturnMapParam("id", "cname");
        returnMapParam.put("managerId", 1);
    }

    @Test
    @Ignore
    public void updateStatus() throws Exception {
        Map<String, Object> map = new HashedMap();
        map.put("status", 1);
        List<Integer> list = new ArrayList<Integer>(){{
            add(1);
            add(2);
        }};
        map.put("customerIds", list);
        map.put("managerId", "1");
        int i = tCustomerBasicMapper.updateStatus(map);
        assertTrue(i > 0);
    }

    @Test
    public void queryCustomerByCondition() throws Exception {
        TCustomerBasic tCustomerBasic = new TCustomerBasic();
        tCustomerBasic.setCustomerManagerId(1);
        tCustomerBasic.setCname("1asd");
        tCustomerBasic.setCertificateNumber("11");
        List<TCustomerBasic> tCustomerBasics = tCustomerBasicMapper.queryCustomerByCondition(tCustomerBasic);
        assertTrue(tCustomerBasics.size() > 0);
    }
}