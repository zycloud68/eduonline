package com.atguigu.staservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {
    @ApiModelProperty(value = "教师名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "状态 Noraml 已发布  Draft 未发布")
    private Integer status;
}
