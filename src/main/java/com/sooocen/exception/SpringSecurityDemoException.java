package com.sooocen.exception;

import com.sooocen.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义全局异常类
 *
 * @author qy
 */
@Data
public class SpringSecurityDemoException extends RuntimeException {

    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public SpringSecurityDemoException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public SpringSecurityDemoException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "TestSystemException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
