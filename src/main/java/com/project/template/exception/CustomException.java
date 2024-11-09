package com.project.template.exception;

import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class CustomException extends RuntimeException{
//    定义一个名为CustomException的自定义异常类，它继承自RuntimeException，表示这是一个运行时异常

    private Integer code;
//    定义一个私有整数变量code，用于存储自定义的错误码
    public CustomException(Integer code,String msg){
//        定义一个构造方法，接受错误码和错误消息作为参数。它调用父类的构造方法super(msg)来设置错误消息，并将错误码赋值给code变量
        super(msg);
        this.code = code;
    }
    public CustomException(String msg){
//        定义另一个构造方法，只接受错误消息作为参数。它将错误码默认设置为 500，并调用父类的构造方法super(msg)来设置错误消息
        super(msg);
        this.code=500;
    }

}
