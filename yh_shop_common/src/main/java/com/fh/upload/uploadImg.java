package com.fh.upload;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class uploadImg {

    public static String aliyunFile(String maker, MultipartFile file) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com/";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G8ETjDEV3qBCBTZ6FUm";
        String accessKeySecret = "YO9n3FAMU9z8zRJqX9uKTpypY3zyo3";
        String bucketName = "yhren";
        String phoneFile="https://yhren.oss-cn-beijing.aliyuncs.com/";
        String fileName=file.getOriginalFilename();
        String newHome = fileName.substring(fileName.lastIndexOf("."));
         String newFileName=UUID.randomUUID().toString()+newHome;
         String path=maker+"/"+newFileName;


// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(bucketName, path, file.getInputStream());
// 关闭OSSClient。
        ossClient.shutdown();
        return phoneFile+path;
    }


}
