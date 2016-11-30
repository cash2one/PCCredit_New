package com.cardpay.core.fastdfs;


import com.cardpay.core.shrio.common.ShiroKit;
import com.cardpay.mgt.file.dao.TFileMapper;
import com.cardpay.mgt.file.model.TFile;
import com.cardpay.mgt.user.model.User;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * FastDFSFile文件上传实现接口
 * Created by chenkai on 2016/11/25.
 */
@Component
public class FileManager implements FileManagerConfig {
    @Autowired
    private  TFileMapper tFileDao;

    private static TrackerClient trackerClient;
    private static StorageClient storageClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;

     static{
        try {
            // TODO: 2016/11/29 此处config路径加载临时解决方案
            /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            String classPath = request.getServletContext().getRealPath("/WEB-INF/config");
            String dfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;*/
            String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
            String fdfsClientConfigFilePath = classPath +"../../../\\resources\\main"+ File.separator + CLIENT_CONFIG_FILE;
            ClientGlobal.init(fdfsClientConfigFilePath);

            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param file FastDFSFile文件
     * @param valuePairs 文件分卷信息
     * @return 文件路径
     */
    private static String upload(FastDFSFile file, NameValuePair[] valuePairs) {
        String[] uploadResults;
        String groupName = "";
        String remoteFileName = "";
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
     * @param groupName 文件所在组名称
     * @param remoteFileName 文件名称
     * @param specFileName 文件类型
     * @return ResponseEntity对象
     */
    public static ResponseEntity<byte[]> download(String groupName, String remoteFileName, String specFileName) {
        byte[] content = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            content = storageClient.download_file(groupName, remoteFileName);
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
    public static InputStream downLoadToFile(String groupName, String remoteFileName) {
        byte[] content = null;
        try {
            content = storageClient.download_file(groupName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(content);
    }

    /**
     * 删除文件
     *
     * @param groupName      分组名称
     * @param remoteFileName 文件名称
     * @return 1成功, 0失败
     */
    public static int deleteFile(String groupName, String remoteFileName) {
        try {
            TFile tFile = new TFile();
            return storageClient.delete_file(groupName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *  批量上传上传接口
     * @param files 文件类型
     * @return 文件名称
     */
    public List<String> upLoad(MultipartFile[] files) {
        List<String> list = new ArrayList<>();
        String ext;
        String upload;
        TFile tFile = new TFile();
        List<TFile> tFiles = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            ext = files[i].getOriginalFilename().substring(files[i].getOriginalFilename().lastIndexOf(".") + 1);
            try {
                FastDFSFile file = new FastDFSFile(files[i].getBytes(), ext);
                NameValuePair[] metaList = new NameValuePair[4];
                metaList[0] = new NameValuePair("fileName", files[i].getOriginalFilename());
                metaList[1] = new NameValuePair("fileLength", String.valueOf(files[i].getSize()));
                metaList[2] = new NameValuePair("fileExt", ext);
                metaList[3] = new NameValuePair("fileAuthor", FileManagerConfig.FILE_DEFAULT_AUTHOR);
                //组名+fastdfs文件名+文件后缀+文件名
                upload = upload(file, metaList) + "," + files[i].getOriginalFilename() + ",." + ext;
                list.add(upload);

                User user = (User) ShiroKit.getPrincipal();
                String[] str = upload.split(",");
                tFile.setId(1);
                tFile.setImageType(ext);
                tFile.setFileName(files[i].getOriginalFilename());
                tFile.setRemark("upLoad");
                tFile.setCreatedBy("1");
                tFile.setCreatedAt(new Date());
                tFile.setModifiedAt(new Date());
                tFile.setModifiedBy("1");
                tFile.setGroupName(str[0]);
                tFile.setFastName(str[1]);

                tFiles.add(tFile);
                tFileDao.batchInsert(tFiles);
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        return list;
    }

}
