package com.cardpay.mgt.file.service;

import com.cardpay.mgt.file.model.TFile;

import java.util.List;

/**
 * 文件Service类
 * Created by chenkai on 2016/11/25.
 */
public interface TFileService {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<TFile> list);
}
