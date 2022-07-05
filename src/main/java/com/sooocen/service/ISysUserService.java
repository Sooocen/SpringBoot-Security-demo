package com.sooocen.service;

import com.sooocen.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Sooocen
 * @since 2022-07-05
 */
public interface ISysUserService extends IService<SysUser> {

    //通过ID获取用户
    SysUser getUserById(int id);
}
