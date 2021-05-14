package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-20
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    // 前台用户注册
    void register(RegisterVo registerVo);
    // 前台用户登录
    String login(RegisterVo registerVo);
    // 微信生成二维码
    UcenterMember getOpenIdMember(String openid);
}
