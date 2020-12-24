package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.t_ums.entity.UmsAdmin;
import com.fh.t_ums.entity.UmsAdminRoleRelation;
import com.fh.t_ums.entity.UmsRole;
import com.fh.t_ums.service.IUmsRoleService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-16
 */
@RestController
@RequestMapping("umsRoleController")
public class UmsRoleController {

    @Autowired
    private IUmsRoleService iUmsRoleService;

    @GetMapping("queryUmsRole")
    public CommonReturn queryUmsRoleController(Page<UmsRole> umsRolePage){
        Page<UmsRole> page = iUmsRoleService.page(umsRolePage);
        return  CommonReturn.success(page);
    }

    @GetMapping("queryUmsRoleById")
    public CommonReturn queryUmsAdminById(Integer id){
        UmsRole byId = iUmsRoleService.getById(id);
        return CommonReturn.success(byId);
    }


    @PostMapping("saveUmsRole")
    public CommonReturn saveUmsRole(UmsRole umsRole){
        iUmsRoleService.save(umsRole);
        return CommonReturn.success();
    }

    @PutMapping("updateUmsRole")
    public CommonReturn updateUmsRole(UmsRole umsRole){
        iUmsRoleService.updateById(umsRole);
        return CommonReturn.success();
    }

    @DeleteMapping("deleteUmsRole")
    public CommonReturn deleteUmsRole(Integer id){
        iUmsRoleService.removeById(id);
        return CommonReturn.success();
    }

}
