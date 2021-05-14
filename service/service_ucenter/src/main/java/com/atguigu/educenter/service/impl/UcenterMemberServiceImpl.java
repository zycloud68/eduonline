package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    // 前台用户注册信息验证
    @Override
    public void register(RegisterVo registerVo) {
     // 1. 获取注册信息
        String code = registerVo.getCode();  // 验证码
        String nickname = registerVo.getNickname(); //昵称
        String mobile = registerVo.getMobile(); // 手机号
        String password = registerVo.getPassword(); //密码
        // 对上述信息进行判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code) ){
            throw new GuliException(20001,"用户注册信息失败");
        }
        // 判断验证码
        // 从redis中获取验证码
        Object redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){ // 判断redisCode跟code是否相同
            throw new GuliException(20001,"用户注册信息失败");
        }
        // 对手机号进行重复判断
        // 数据库中进行查询
        QueryWrapper<UcenterMember> wrapper  = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper); // 只需要查询,并不需要返回数据
        if (count>0){
            throw new GuliException(20001,"用户注册信息失败");
        }
        // 以上都ok,那么将上述数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));  //记住密码需要进行加密处理
        member.setIsDisabled(false); //用户不禁用处理
        member.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/cover/default.gif"); //头像
        baseMapper.insert(member); //插入一条数据
    }
    // 前台用户登录信息验证
    @Override
    public String login(RegisterVo registerVo) {
        // 从数据表中获取手机号和密码字段
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        // 对获取的字段进行非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ){
            throw new GuliException(20001,"登录失败,请重试!");
        }
        // 判断登录手机号码是否为空
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        // 判断查询的条件是否为空
        if (mobileMember == null){
            throw new GuliException(20001,"登录失败,请重试!");
        }
        // 因为数据库的密码是加密的,我们需要判断密码跟数据库的密码是否相同
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"登录失败,请重试!");
        }
        // 判断用户是否被禁用
        if (mobileMember.getIsDisabled()){ //默认是false,未被禁用
            throw new GuliException(20001,"登录失败,请重试!");
        }
        // 以上信息全部通过,代表登录成功,那么我们需要生成token字符,使用jwt工具类
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return token;
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;

    }
}
