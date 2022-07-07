package com.sooocen.controller;

import com.sooocen.pojo.SysUser;
import com.sooocen.result.Result;
import com.sooocen.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class LoginController {

    @Autowired
    ISysUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody SysUser user){
        return userService.login(user);
    }

    @RequestMapping("/logout")
    public Result logout(){
        return userService.logout();
    }
}
