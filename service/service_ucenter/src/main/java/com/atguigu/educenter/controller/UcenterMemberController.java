package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ResultResponse;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    /**
     * 前台用户注册
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public ResultResponse register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return ResultResponse.ok();

    }

    /**
     * 前台用户登录
     * @param registerVo
     * @return
     */
    @PostMapping("login")
    public ResultResponse loginUser(@RequestBody RegisterVo registerVo){
        String token  = memberService.login(registerVo);
        return ResultResponse.ok().data("token",token);
    }

    // 获取token信息
    @GetMapping("/getMemberInfo")
    public ResultResponse getMemberInfo(HttpServletRequest request){
        // 调用jwt工具类,根据request对象获取头信息,返回id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据中用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return ResultResponse.ok().data("userInfo",member);
    }
    // 根据用户id获取用户信息
    @GetMapping("/getOrderInfo/{id}")
    public UcenterMemberOrder getOrderInfo(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        // 将member的信息复制到UcenterMemberOrder对象中去
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }


}

