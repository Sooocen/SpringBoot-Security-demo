package com.sooocen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sooocen.exception.SpringSecurityDemoException;
import com.sooocen.mapper.SysUserMapper;
import com.sooocen.pojo.LoginUser;
import com.sooocen.pojo.SysUser;
import com.sooocen.result.ResultCodeEnum;
import com.sooocen.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getUserName, username);
        SysUser user = userMapper.selectOne(queryWrapper);
        // 没有相关用户抛出异常
        if(Objects.isNull(user)){
            throw new SpringSecurityDemoException(ResultCodeEnum.FAIL);
        }
        //TODO 获取用户权限
        return new LoginUser(user);
    }
}

