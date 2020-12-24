package com.fh.t_ums.mapper;

import com.fh.t_ums.entity.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.t_ums.entity.UmsMenu;
import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.entity.UmsRole;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author yhren
 * @since 2020-12-07
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    List<UmsResource> queryResourceByAdmin(Long id);

    List<UmsRole> queryRoleByAdmin(Long id);

    List<UmsMenu> queryMenuByAdmin(String username);
}
