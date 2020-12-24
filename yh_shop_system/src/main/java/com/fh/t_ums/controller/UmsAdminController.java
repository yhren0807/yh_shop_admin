package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.t_ums.entity.UmsAdmin;
import com.fh.t_ums.service.IUmsAdminService;
import com.fh.utils.SecurityJwtUtils;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-07
 */
@RestController
@RequestMapping("umsAdminController")
public class UmsAdminController {


    @Autowired
    private SecurityJwtUtils securityJwtUtils;

    @Autowired
    private IUmsAdminService umsAdminService;

    @GetMapping("queryUmsAdmin")
    public CommonReturn queryUmsAdmin(Page<UmsAdmin> page){
        Page<UmsAdmin> page1 = umsAdminService.page(page);
        return CommonReturn.success(page1);
    }

    @GetMapping("queryUmsAdminById")
    public CommonReturn queryUmsAdminById(Integer id){
        UmsAdmin byId = umsAdminService.getById(id);
        return CommonReturn.success(byId);
    }

    @PostMapping("saveUmsAdmin")
    public CommonReturn saveUmsAdmin(UmsAdmin umsAdmin){
        umsAdminService.save(umsAdmin);
        return CommonReturn.success();
    }

    @PutMapping("updateUmsAdmin")
    public CommonReturn updateUmsAdmin(UmsAdmin umsAdmin){
        umsAdminService.updateById(umsAdmin);
        return CommonReturn.success();
    }

    @DeleteMapping("deleteUmsAdmin")
    public CommonReturn deleteUmsAdmin(Integer id){
        umsAdminService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("queryMenu")
    public CommonReturn queryMenu(String username){

        String username1 = securityJwtUtils.getUsername(username);
        List<Map<String,Object>> list=umsAdminService.queryMenu(username1);


        return CommonReturn.success(list);
    }

    @DeleteMapping("loginOut")
    public CommonReturn loginOut(String token){
        String username1 = securityJwtUtils.getUsername(token);

        umsAdminService.loginOut(username1);


        return CommonReturn.success();
    }


}
