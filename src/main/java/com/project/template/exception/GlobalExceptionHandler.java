package com.project.template.exception;

import com.project.template.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 全局异常拦截
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 运行时异常处理
     */


    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public Result CustomExceptionHandler(CustomException e){
//        定义一个方法来处理自定义异常CustomException
        log.error("错误原因为："+e.getMessage());
//        记录错误信息到日志中
        return new Result<>().error(e.getCode(),e.getMessage());
//        返回一个封装了错误信息的Result对象给客户端，其中包含错误码和错误消息
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    注解表示这个方法用于处理MethodArgumentNotValidException类型的异常，即参数校验失败时抛出的异常
    @ResponseBody
    public Result MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
//        定义一个方法来处理参数校验失败的异常
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        获取参数校验失败的字段错误列表
        String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//        获取第一个字段错误的默认错误消息
        log.error("错误原因为："+defaultMessage);
//记录错误信息到日志中
        return new Result<>().error(defaultMessage);
//        返回一个封装了错误信息的Result对象给客户端，其中包含第一个字段错误的默认错误消息
    }


}
