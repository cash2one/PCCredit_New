package com.cardpay.core.fastdfs;


import com.cardpay.core.shiro.common.ShiroKit;
import com.cardpay.mgt.file.model.TFile;
import com.cardpay.mgt.file.service.TFileService;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FastDFSFile文件上传下载通用类
 * @author chenkai
 */
@Component
public class FileManager implements FileManagerConfig {
    @Autowired
    private TFileService tFileService;

    private static TrackerClient trackerClient;
    private static StorageClient storageClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;

    static {
        try {
            // TODO: 2016/11/29 此处config路径加载临时解决方案
            /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            String classPath = request.getServletContext().getRealPath("/WEB-INF/config");
            String dfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;*/
            String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
            String fastDfsClientConfigFilePath = classPath + "../../../\\resources\\main" + File.separator + CLIENT_CONFIG_FILE;
            ClientGlobal.init(fastDfsClientConfigFilePath);

            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param file       FastDFSFile文件
     * @param valuePairs 文件分卷信息
     * @return 文件路径
     */
    private static String upload(FastDFSFile file, NameValuePair[] valuePairs) {
        String[] uploadResults;
        String groupName = null;
        String remoteFileName = null;
        try {
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), valuePairs);
            groupName = uploadResults[0];
            remoteFileName = uploadResults[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupName + "," + remoteFileName;
    }

    /**
     * 下载文件
     *
     * @param groupName      文件所在组名称
     * @param remoteFileName 文件名称
     * @param specFileName   文件类型
     * @return ResponseEntity对象
     */
    public ResponseEntity<byte[]> download(String groupName, String remoteFileName, String specFileName) {
        HttpHeaders headers = new HttpHeaders();
        byte[] content = null;
        try {
            content = storageClient.download_file(groupName, remoteFileName);
            if (content.length > 0){
                updateFile(remoteFileName);
            }
            headers.setContentDispositionFormData("attachment",
                    new String(specFileName.getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(content, headers, HttpStatus.CREATED);
    }

    /**
     * 下载文件(返回InputStream)
     *
     * @param groupName      组名称
     * @param remoteFileName 文件名
     * @return InputStream对象
     */
    public InputStream downLoadToFile(String groupName, String remoteFileName) {
        byte[] content = null;
        try {
            content = storageClient.download_file(groupName, remoteFileName);
            if (content.length > 0){
                updateFile(remoteFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(content);
    }

    /**
     * 更新TFile信息
     *
     * @param remoteFileName 文件名
     * @return 变动数量
     */
    private int updateFile(String remoteFileName) {
        TFile tFile = new TFile();
        tFile.setFastName(remoteFileName);
        TFile fileEntity = tFileService.selectOne(tFile);
        fileEntity.setRemark("downLoad");
        return tFileService.updateByPrimaryKey(fileEntity);
    }

    /**
     * 删除文件
     *
     * @param groupName      分组名称
     * @param remoteFileName 文件名称
     * @return 1成功, 0失败
     */
    public int deleteFile(String groupName, String remoteFileName) {
        try {
            TFile tFile = new TFile();
            tFile.setFastName(remoteFileName);
            Integer flag = tFileService.delete(tFile);
            return null != flag ? storageClient.delete_file(groupName, remoteFileName) : 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询文件
     *
     * @param groupName      分组名称
     * @param remoteFileName 文件名称
     * @return FileInfo
     */
    public static FileInfo queryFile(String groupName, String remoteFileName) {
        FileInfo fileInfo = null;
        try {
            fileInfo = storageClient.query_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    /**
     * 单文件上传
     *
     * @param file 上传文件
     * @return 文件信息
     */
    public String upLoad(MultipartFile file) {
        return upLoadCore(file);
    }

    /**
     * 文件批量上传接口
     *
     * @param files 文件
     * @return 文件名称
     */
    public List<String> upLoad(MultipartFile[] files) {
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String upLoadCore = upLoadCore(file);
            list.add(upLoadCore);
        }
        return list;
    }

    /**
     * 文件上传核心方法
     *
     * @param file 文件
     * @return fastDfs返回值
     */
    private String upLoadCore(MultipartFile file) {
        String fileName = null;
        List<TFile> tFiles = new ArrayList<>();
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        try {
            FastDFSFile fastDFSFile = new FastDFSFile(file.getBytes(), ext);
            NameValuePair[] metaList = new NameValuePair[4];
            metaList[0] = new NameValuePair("fileName", file.getOriginalFilename());
            metaList[1] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
            metaList[2] = new NameValuePair("fileExt", ext);
            metaList[3] = new NameValuePair("fileAuthor", FileManagerConfig.FILE_DEFAULT_AUTHOR);
            fileName = upload(fastDFSFile, metaList);
            //插入数据库t_file表
            if (!fileName.isEmpty()) {
                String[] str = fileName.split(",");
                String userId = String.valueOf(ShiroKit.getUserId());
                TFile tFile = TFile.TFileBuilder.get()
                        .withId(1)
                        .withImageType(ext)
                        .withFileName(file.getOriginalFilename())
                        .withRemark("upload")
                        .withCreatedBy(userId)
                        .withCreatedAt(new Date())
                        .withModifiedBy(userId)
                        .withModifiedAt(new Date())
                        .withGroupName(str[0])
                        .withFastName(str[1])
                        .build();
                tFiles.add(tFile);
                tFileService.batchInsertFile(tFiles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
