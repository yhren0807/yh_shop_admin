package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.t_ums.entity.UmsAdminRoleRelation;
import com.fh.t_ums.entity.UmsRole;
import com.fh.t_ums.service.IUmsAdminRoleRelationService;
import com.fh.t_ums.service.IUmsRoleService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-16
 */
@RestController
@RequestMapping("umsAdminRoleRelationController")
public class UmsAdminRoleRelationController {


    @Autowired
    private IUmsAdminRoleRelationService iUmsAdminRoleRelationService;
    @Autowired
    private IUmsRoleService iUmsRoleService;

    @GetMapping("queryByAdminIdUmsRole")
    public CommonReturn queryByAdminIdUmsRole(Integer id){
        List<UmsAdminRoleRelation> admin_id = iUmsAdminRoleRelationService.list(new QueryWrapper<UmsAdminRoleRelation>().eq("admin_id", id));
        QueryWrapper<UmsRole> queryWrapper=new QueryWrapper<UmsRole>();
        List<Long> list=new ArrayList<Long>();
        admin_id.forEach(UmsAdminRoleRelation->{
            Long roleId = UmsAdminRoleRelation.getRoleId();
            list.add(roleId);
        });
        queryWrapper.in("id",list);
        List<UmsRole> list1 = iUmsRoleService.list(queryWrapper);
        List<Long> adminIdList=new ArrayList<Long>();
        list1.forEach(UmsRole->{
            adminIdList.add(UmsRole.getId());
        });
        return CommonReturn.success(adminIdList);
    }

    @PostMapping("saveAdminUmsRole")
    public CommonReturn saveAdminUmsRole(@RequestBody List<UmsAdminRoleRelation> umsAdminRoleRelation){
        iUmsAdminRoleRelationService.remove(new QueryWrapper<UmsAdminRoleRelation>().eq("admin_id",umsAdminRoleRelation.get(0).getAdminId()));
        iUmsAdminRoleRelationService.saveBatch(umsAdminRoleRelation);
        return CommonReturn.success();
    }



}
