package com.atguigu.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultResponse {
    @ApiModelProperty(value="是否成功")
    private Boolean success;

    @ApiModelProperty(value="返回码")
    private  Integer code;

    @ApiModelProperty(value="返回信息")
    private String message;

    @ApiModelProperty(value="返回数据")
    private Map<String,Object> data = new HashMap<String,Object>();

    // 把构造方法私有话，禁止其他类访问
    private ResultResponse(){}

    // 成功静态化
    public static ResultResponse ok(){
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(true);
        resultResponse.setCode(ResultCode.SUCCESS);
        resultResponse.setMessage("成功");
        return  resultResponse ;
    }

    // 失败静态化
    public static ResultResponse error(){
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(false);
        resultResponse.setCode(ResultCode.ERROR);
        resultResponse.setMessage("失败");
        return  resultResponse ;
    }

    public ResultResponse success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultResponse message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultResponse code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultResponse data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public ResultResponse data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
