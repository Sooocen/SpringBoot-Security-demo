package com.sooocen.service.impl;

import com.sooocen.exception.SpringSecurityDemoException;
import com.sooocen.pojo.LoginUser;
import com.sooocen.pojo.SysUser;
import com.sooocen.mapper.SysUserMapper;
import com.sooocen.result.Result;
import com.sooocen.result.ResultCodeEnum;
import com.sooocen.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sooocen.utils.JwtUtil;
import com.sooocen.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Sooocen
 * @since 2022-07-05
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(SysUser user) {
        // 获取AuthenticationManager的authenticate方法进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证未通过抛出异常
        if(Objects.isNull(authenticate)){
            throw new SpringSecurityDemoException(ResultCodeEnum.PARAM_ERROR);
        }
        // 认证通过生成JWT
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        // 将用户信息存入Redis key=userid
        redisCache.setCacheObject("login:"+id,loginUser);
        // JWT存入Result返回
        return Result.ok(jwt);
    }

    @Override
    public Result logout() {
        // 获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginuser = (LoginUser) authentication.getPrincipal();
        Long id = loginuser.getUser().getId();
        //删除Redis中的用户信息
        redisCache.deleteObject("login:"+id);
        return Result.ok();
    }

    @Override
    public SysUser getUserById(int id) {
        return baseMapper.selectById(id);
    }
}
