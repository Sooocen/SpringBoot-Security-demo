package com.sooocen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sooocen.pojo.SysUser;
import com.sooocen.mapper.SysUserMapper;
import com.sooocen.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public SysUser getUserById(int id) {
        return baseMapper.selectById(id);
    }
}
