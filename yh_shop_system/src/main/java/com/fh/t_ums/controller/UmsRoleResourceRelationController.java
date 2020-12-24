package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.t_ums.entity.UmsRoleResourceRelation;
import com.fh.t_ums.service.IUmsRoleResourceRelationService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台角色资源关系表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
@RestController
@RequestMapping("umsRoleResource")
public class UmsRoleResourceRelationController {

    @Autowired
    private IUmsRoleResourceRelationService iUmsRoleResourceRelationService;

    @GetMapping("queryUmsRoleResourceByRoleId")
    public CommonReturn queryUmsRoleResourceByRoleId(Integer id){
        List<UmsRoleResourceRelation> list = iUmsRoleResourceRelationService.list(new QueryWrapper<UmsRoleResourceRelation>().eq("role_id", id));
        return CommonReturn.success(list);
    }

    @PostMapping("saveUmsRoleResourceByRoleId")
    public CommonReturn saveUmsRoleResourceByRoleId(@RequestBody List<UmsRoleResourceRelation> list){
        iUmsRoleResourceRelationService.remove(new QueryWrapper<UmsRoleResourceRelation>().eq("role_id",list.get(0).getRoleId()));
        iUmsRoleResourceRelationService.saveBatch(list);
        return CommonReturn.success();
    }

}
