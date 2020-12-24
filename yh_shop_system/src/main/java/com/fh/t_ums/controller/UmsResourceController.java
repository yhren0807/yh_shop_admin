package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.service.IUmsResourceService;
import com.fh.utils.returns.CommonReturn;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
@RestController
@RequestMapping("umsResourceController")
public class UmsResourceController {

    @Autowired
    private IUmsResourceService iUmsResourceService;

    @GetMapping("queryResourcesByCatePory")
    public CommonReturn queryResourcesByCatePory(){
       List<Map<String,Object>> maps=iUmsResourceService.queryResourcesByCatePory();
        return CommonReturn.success(maps);
    }

    @GetMapping("queryResourceByRoleId")
    public CommonReturn queryResourceByRoleId(Integer id){
        List<UmsResource> categoryList = iUmsResourceService.list(new QueryWrapper<UmsResource>().eq("category_id", id));
        return CommonReturn.success(categoryList);
    }


    @GetMapping("queryUmsResources")
    public CommonReturn queryUmsResources(Page<UmsResource> page){
        Page<UmsResource> page1 = iUmsResourceService.page(page);
        return CommonReturn.success(page1);
    }

    @DeleteMapping("deleteUmsResource")
    public CommonReturn deleteUmsResource(Integer id){
        iUmsResourceService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("queryUmsResourceById")
    public CommonReturn queryUmsResourceById(Integer id){
        UmsResource byId = iUmsResourceService.getById(id);
        return CommonReturn.success(byId);
    }


    @PostMapping("saveUmsResource")
    public CommonReturn saveUmsResource(UmsResource umsResource){
        iUmsResourceService.save(umsResource);
        return CommonReturn.success();
    }

    @PutMapping("updateUmsResource")
    public CommonReturn updateUmsResource(UmsResource umsResource){
        iUmsResourceService.updateById(umsResource);
        return CommonReturn.success();
    }

}
