package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    // 生成订单的方法
    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        // 通过远程调用根据课程id获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        // 远程调用课程用户id查询用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getOrderInfo(memberIdByJwtToken);
        // 创建Order对象,向order对象里面设置需要的参数
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); //设置订单号
        order.setCourseId(courseId); // 课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName()); //获取讲师名称
        order.setTotalFee(courseInfoOrder.getPrice()); // 设置消费金额
        order.setMemberId(memberIdByJwtToken); // 获取会员id
        order.setMobile(userInfoOrder.getMobile()); // 获取用户手机号
        order.setNickname(userInfoOrder.getNickname()); // 获取用户昵称
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        return order.getOrderNo();
    }
}
