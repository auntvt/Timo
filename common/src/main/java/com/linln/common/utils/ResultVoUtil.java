package com.linln.common.utils;

import com.linln.common.enums.ResultEnum;
import com.linln.common.vo.ResultVo;
import com.linln.common.data.URL;

/**
 * 响应数据(结果)最外层对象工具
 * @author 小懒虫
 * @date 2018/10/15
 */
public class ResultVoUtil {

    public static ResultVo SAVE_SUCCESS = success("保存成功");

    /**
     * 操作成功
     * @param msg 提示信息
     * @param object 对象
     */
    public static ResultVo success(String msg, Object object){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setMsg(msg);
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setData(object);
        return resultVo;
    }

    /**
     * 操作成功，返回url地址
     * @param msg 提示信息
     * @param url URL包装对象
     */
    public static ResultVo success(String msg, URL url){
        return success(msg, url.getUrl());
    }

    /**
     * 操作成功，使用默认的提示信息
     * @param object 对象
     */
    public static ResultVo success(Object object){
        String message = ResultEnum.SUCCESS.getMessage();
        return success(message, object);
    }

    /**
     * 操作成功，返回提示信息，不返回数据
     */
    public static ResultVo success(String msg){
        Object object = null;
        return success(msg, object);
    }

    /**
     * 操作成功，不返回数据
     */
    public static ResultVo success(){
        return success(null);
    }

    /**
     * 操作有误
     * @param code 错误码
     * @param msg 提示信息
     */
    public static ResultVo error(Integer code, String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg);
        resultVo.setCode(code);
        return resultVo;
    }

    /**
     * 操作有误，使用默认400错误码
     * @param msg 提示信息
     */
    public static ResultVo error(String msg){
        Integer code = ResultEnum.ERROR.getCode();
        return error(code, msg);
    }

    /**
     * 操作有误，只返回默认错误状态码
     */
    public static ResultVo error(){
        return error(null);
    }

}
