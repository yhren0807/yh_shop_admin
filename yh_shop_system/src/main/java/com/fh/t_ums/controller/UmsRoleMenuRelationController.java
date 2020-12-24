package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.t_ums.entity.UmsRoleMenuRelation;
import com.fh.t_ums.service.IUmsRoleMenuRelationService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * 后台角色菜单关系表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
@RestController
@RequestMapping("umsRoleMenuController")
public class UmsRoleMenuRelationController {

    @Autowired
    private IUmsRoleMenuRelationService iUmsRoleMenuRelationService;

    @GetMapping("queryUmsRoleIdMenu")
    public CommonReturn queryUmsRoleIdMenu(Integer id){
        QueryWrapper<UmsRoleMenuRelation> queryWrapper=new QueryWrapper<UmsRoleMenuRelation>();
        queryWrapper.eq("role_id",id);
        List<UmsRoleMenuRelation> list = iUmsRoleMenuRelationService.list(queryWrapper);
        return CommonReturn.success(list);
    }

    @PostMapping("saveUmsRoleIdMenu")
    public CommonReturn saveUmsRoleIdMenu(@RequestBody List<UmsRoleMenuRelation> list){
        iUmsRoleMenuRelationService.remove(new QueryWrapper<UmsRoleMenuRelation>().eq("role_id",list.get(0).getRoleId()));
        iUmsRoleMenuRelationService.saveBatch(list);
        return CommonReturn.success();
    }

}
