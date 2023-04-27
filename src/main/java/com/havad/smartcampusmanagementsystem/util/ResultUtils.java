package com.havad.smartcampusmanagementsystem.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: SmartCampusManagementSystem
 * @description: 统一整个系统的返回结果
 * @author: Havad
 * @create: 2023-04-25 02:59
 **/

@Data
@ApiModel(value = "全局统一返回结果")
public class ResultUtils<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;


    /**
     * @param data 数据
     * @param <T>  数据类型
     * @return 封装好的数据
     * @description: 快速封装数据
     */
    protected static <T> ResultUtils<T> build(T data) {
        ResultUtils<T> result = new ResultUtils<>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    /**
     * @param body 提供数据
     * @param codeEnum 提供状态码以及状态信息
     * @param <T> 数据类型
     * @return 封装好的数据
     * @description: 分别提供数据,及返回码和返回信息进行封装
     */
    public static <T> ResultUtils<T> build(T body, ResultCodeEnum codeEnum) {
        ResultUtils<T> result = build(body);
        result.setCode(codeEnum.getCode());
        result.setMsg(codeEnum.getMsg());
        return result;
    }

    /**
     * @param data 操作的数据
     * @param <T> 数据类型
     * @return 封装结果
     * @description: 成功操作
     */
    public static <T> ResultUtils<T> success(T data){
        ResultUtils<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }
    public static <T> ResultUtils<T> success(){
        return ResultUtils.success(null);
    }

    /**
     * @param data 操作的数据
     * @param <T> 数据类型
     * @return 封装结果
     * @description: 失败操作
     */
    public static <T> ResultUtils<T> fail(T data){
        ResultUtils<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }
    public static <T> ResultUtils<T> fail(){
        return ResultUtils.fail(null);
    }

    public ResultUtils<T> msg(String msg){
        this.setMsg(msg);
        return this;
    }
    public ResultUtils<T> code(Integer code){
        this.setCode(code);
        return this;
    }
    public boolean isSuccess(){
        return this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue();
    }
}
