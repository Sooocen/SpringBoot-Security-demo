package com.sooocen.filter;

import com.sooocen.exception.SpringSecurityDemoException;
import com.sooocen.pojo.LoginUser;
import com.sooocen.result.ResultCodeEnum;
import com.sooocen.utils.JwtUtil;
import com.sooocen.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            // 直接放行
            filterChain.doFilter(request,response);
            return;// 过滤器链再次执行时 跳过执行
        }
        // 解析token 获取userid
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpringSecurityDemoException(ResultCodeEnum.PERMISSION);//登陆 非法token
        }
        // 读取redis中的信息
        String key = "login:"+userid;
        LoginUser user = redisCache.getCacheObject(key);
        if(Objects.isNull(user)){
            throw new SpringSecurityDemoException(ResultCodeEnum.LOGIN_AUTH);//登陆 用户未登录
        }
        // 将用户信息存入SecurityContextHolder中
        // 获取权限 封装authentication中
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 放行
        filterChain.doFilter(request,response);
    }
}
