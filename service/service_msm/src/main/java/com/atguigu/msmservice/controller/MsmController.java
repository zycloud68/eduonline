package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //发送短信的方法

    @GetMapping("send/{phone}")
    public ResultResponse send(@PathVariable String phone){
        // 首先我们先从redisTemplate获取验证码,如果直接获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return ResultResponse.ok();
        }
        // 2. 如果redis中没有获取到的话,我们进行阿里云的发送
        // 生成随机值.通过阿里云进行传递
        code = RandomUtil.getFourBitRandom();
        Map<String ,Object> param = new HashMap<>();
        param.put("code",code);
        // 3. 调用方法进行发送
       boolean isSend =  msmService.send(param,phone);
       if (isSend){
           // 发送成功,把发送验证码存放到redis中去
           // 并且设置验证码在redis中的5分钟有效时间
           redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
           return ResultResponse.ok();
       }else{
           return ResultResponse.error().message("短信发送失败");
       }

    }

}
