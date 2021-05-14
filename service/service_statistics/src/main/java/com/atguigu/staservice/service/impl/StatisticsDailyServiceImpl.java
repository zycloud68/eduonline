package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-05-10
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {
        // 在添加日期之前,我们险删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        // 远程调用得到某一天的注册人数
        ResultResponse registerR = ucenterClient.countRegister(day);
        Integer  countRegister = (Integer) registerR.getData().get("countRegister");
        //将数据添加到数据库中,统计分析表里面的内容
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);
        sta.setDateCalculated(day);
        sta.setVideoViewNum(RandomUtils.nextInt(100,200)); // 这里我们是设置的是固定值,我们可以改变
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);


    }

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     * 显示两部分,一部分显示日期数据,一部分显示日期对应的数量
     * 前端需要接受json数据,后端是list集合
     * 创建两个list集合,一个存放日期,一个存放日期对应的数量
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String,Object> getShowData(String type, String begin, String end) {
        //根据条件查询所有的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type); // 根据date_calculated字段中type类型查询信息
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        //前端需要接受json数据,后端是list集合
        //创建两个list集合,一个存放日期,一个存放日期对应的数量
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        // 遍历查询所有数据list集合staList,进行封装
        for (int i= 0; i<staList.size();i++){
            StatisticsDaily statisticsDaily = staList.get(i); // 获取所有的数值
            date_calculatedList.add(statisticsDaily.getDateCalculated());
            // 封装数量
            switch (type) {
                case "login_num":
                    numDataList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }

        }
        // 封装数据,将list集合放到map中去
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
