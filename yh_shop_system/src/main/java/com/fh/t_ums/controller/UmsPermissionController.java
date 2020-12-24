package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.t_ums.entity.UmsPermission;
import com.fh.t_ums.entity.UmsRole;
import com.fh.t_ums.service.IUmsPermissionService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台用户权限表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-16
 */
@RestController
@RequestMapping("umsPermissionController")
public class UmsPermissionController {

    @Autowired
    private IUmsPermissionService iUmsPermissionService;

    @GetMapping("queryUmsPermission")
    public CommonReturn queryUmsPermission(Page<UmsPermission> page){
        Page<UmsPermission> page1 = iUmsPermissionService.page(page);
        return CommonReturn.success(page1);
    }

    @GetMapping("queryUmsPermissionById")
    public CommonReturn queryUmsAdminById(Integer id){
        UmsPermission byId = iUmsPermissionService.getById(id);
        return CommonReturn.success(byId);
    }

    @PostMapping("saveUmsPermission")
    public CommonReturn saveUmsRole(UmsPermission umsPermission){
        iUmsPermissionService.save(umsPermission);
        return CommonReturn.success();
    }

    @PutMapping("updateUmsPermission")
    public CommonReturn updateUmsRole(UmsPermission umsPermission){
        iUmsPermissionService.updateById(umsPermission);
        return CommonReturn.success();
    }

    @DeleteMapping("deleteUmsPermission")
    public CommonReturn deleteUmsRole(Integer id){
        iUmsPermissionService.removeById(id);
        return CommonReturn.success();
    }

}
