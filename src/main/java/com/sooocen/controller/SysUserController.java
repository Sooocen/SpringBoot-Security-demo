package com.sooocen.controller;


import com.sooocen.pojo.SysUser;
import com.sooocen.result.Result;
import com.sooocen.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Sooocen
 * @since 2022-07-05
 */
@RestController
@RequestMapping("/sys-user")
@CrossOrigin
public class SysUserController {
    @Autowired
    ISysUserService userService;

    @RequestMapping("/getUserById/{id}")
    public Result getUserById(@PathVariable int id) {
        SysUser user = userService.getUserById(id);
        return Result.ok(user);
    }
}
