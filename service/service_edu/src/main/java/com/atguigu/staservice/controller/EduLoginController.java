package com.atguigu.staservice.controller;

import com.atguigu.commonutils.ResultResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginController {

    /**
     * 用户登录
     * @return
     */
    // login
    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public ResultResponse login(){
        return ResultResponse.ok().data("token","admin");
    }

    // /user/info
    @ApiOperation(value = "用户登录返回信息")
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public ResultResponse loginInfo(){
        // 图片 https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif
        return ResultResponse.ok().data("roles","欢迎您,管理员!!!!").data("name","相信美好的事,即将发生!").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
