package com.fh.t_ums.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.t_ums.entity.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.entity.UmsRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author yhren
 * @since 2020-12-07
 */
public interface IUmsAdminService extends IService<UmsAdmin> {

    List<UmsResource> queryResourceByAdmin(Long id, String username);

    List<UmsRole> queryRoleByAdmin(Long id, String username);

    String login(String username, String password);

    UmsAdmin queryAdminByUserName(QueryWrapper<UmsAdmin> username,String username1);

    List<Map<String,Object>> queryMenu(String username);

    void loginOut(String username1);
}
