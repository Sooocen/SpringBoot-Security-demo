package com.sooocen.exception;

import com.sooocen.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(SpringSecurityDemoException.class)
    @ResponseBody
    public Result error(SpringSecurityDemoException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
