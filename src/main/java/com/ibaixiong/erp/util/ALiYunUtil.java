package com.ibaixiong.erp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 阿里oss 上传
 * baixiong.com Inc.
 * Copyright (c) 1999-2001 All Rights Reserved.
 * 
 * @author yaoweiguo
 * @Email  yaoweiguo@ibaixiong.com
 * @Description TODO
 * @date 2015年9月22日
 *
 */
public class ALiYunUtil {

	public static final String ACCESS_ID = "1sYcyoLnA4RwF1b1";
	public static final String ACCESS_KEY = "eRYfPK1PoYP6U7Kz9E8l5ifxi7bQSR";
    public static final String OSS_ENDPOINT = "http://image.ibaixiong.com/";
    public static final String BUCKET_NAME="baixiongbasicimage";
    
    
    
    public String  putObject(String bucketName,MultipartFile file){

        // 初始化OSSClient
        OSSClient client = new OSSClient(OSS_ENDPOINT,ACCESS_ID, ACCESS_KEY);

        // 获取指定文件的输入流
        InputStream content = null;
		try {
			content = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(file.getSize());
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, ACCESS_KEY, content, meta);
        // 打印ETag
        System.out.println(result.getETag());

        return result.toString();
    }
    
    // 创建Bucket.
    static void ensureBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException{

        try {
            // 创建bucket
            client.createBucket(bucketName);
        } catch (ServiceException e) {
            if (!OSSErrorCode.BUCKES_ALREADY_EXISTS.equals(e.getErrorCode())) {
                // 如果Bucket已经存在，则忽略
                throw e;
            }
        }
    }

    // 删除一个Bucket和其中的Objects 
    public static void deleteBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException {

        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing
                .getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }
    
    // 删除某个Objects 
    public static void deleteObject(OSSClient client, String bucketName,String key)
            throws OSSException, ClientException {
            // 如果不为空，先删除bucket下的文件
        client.deleteObject(bucketName, key);
    }

    // 把Bucket设置为所有人可读
//    private static void setBucketPublicReadable(OSSClient client, String bucketName)
//            throws OSSException, ClientException {
//        //创建bucket
//        client.createBucket(bucketName);
//
//        //设置bucket的访问权限，public-read-write权限
//        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
//    }

    // 上传文件
    public static PutObjectResult uploadFile(OSSClient client, String bucketName, String key, MultipartFile file)
            throws OSSException, ClientException, IOException {

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.getSize());
        // 可以在metadata中标记文件类型
        objectMeta.setContentType("image/jpeg");

        InputStream input = file.getInputStream();
        return client.putObject(bucketName, key, input, objectMeta);
        
    }

    // 下载文件
    static void downloadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException {
        client.getObject(new GetObjectRequest(bucketName, key),
                new File(filename));
    }
    public static void main(String[] args) throws FileNotFoundException {
    	OSSClient client = new OSSClient(ALiYunUtil.OSS_ENDPOINT,ALiYunUtil.ACCESS_ID, ALiYunUtil.ACCESS_KEY);
    	File file =new File("D:\\Documents\\Pictures\\bd3eb13533fa828b772b75eaff1f4134970a5aab.jpg");
    	InputStream in=new FileInputStream(file);
    	ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        objectMeta.setContentType("image/jpeg");

       PutObjectResult result=client.putObject(BUCKET_NAME, "mall/test.jpg", in, objectMeta);
       System.out.println(result);
	}
}
