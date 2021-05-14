package com.atguigu.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传阿里云oss,
     * @param file
     * @return
     */
    @Override
    public String uploadFiledAvatar(MultipartFile file) {
        // 1. 工具类获取值
        String endPoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 2. 创建OSS实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();
            // 在文件中添加唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid+fileName;
            // 把文件按照日期来进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath+"/"+fileName;
            // 调用OSS方法实现上传文件
            // 第一个参数: BucketName 文件夹名称
            // 第二个参数: fileName 上传oss文件路径和文件名称
            // 第三个参数: inputStream 上传文件输入流
            ossClient.putObject(bucketName,fileName,inputStream);
            // 关闭OssClient
            ossClient.shutdown();
            // 在文件上传之后,我们需要把文件路径手动拼接出来
            String url = "https://" + bucketName + "."+endPoint+ "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

}
