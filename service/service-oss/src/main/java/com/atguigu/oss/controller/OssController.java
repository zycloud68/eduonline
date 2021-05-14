package com.atguigu.oss.controller;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/eduoss/fileoss")
@EnableSwagger2
@CrossOrigin
public class OssController {

    @Autowired
    private  OssService ossService;

   //上传头像的方法
    @PostMapping()
    public ResultResponse uploadFile(MultipartFile file){
        // 获取上传文件，MultipartFile
        String url = ossService.uploadFiledAvatar(file);
        return ResultResponse.ok().data("url",url);
    }


}
