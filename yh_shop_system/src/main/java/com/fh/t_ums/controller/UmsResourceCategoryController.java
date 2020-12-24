package com.fh.t_ums.controller;


import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.entity.UmsResourceCategory;
import com.fh.t_ums.service.IUmsResourceCategoryService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
@RestController
@RequestMapping("umsResourceCate")
public class UmsResourceCategoryController {

    @Autowired
    private IUmsResourceCategoryService iUmsResourceCategoryService;

    @GetMapping("queryResourceCate")
    public CommonReturn queryResourceCate(){
        List<UmsResourceCategory> list = iUmsResourceCategoryService.list();
        return CommonReturn.success(list);
    }

    @DeleteMapping("deleteResourceCate")
    public CommonReturn deleteUmsResource(Integer id){
        iUmsResourceCategoryService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("queryUmsResourceCateById")
    public CommonReturn queryUmsResourceById(Integer id){
        UmsResourceCategory byId = iUmsResourceCategoryService.getById(id);
        return CommonReturn.success(byId);
    }


    @PostMapping("saveUmsResourceCate")
    public CommonReturn saveUmsResource(UmsResourceCategory umsResourceCategory){
        iUmsResourceCategoryService.save(umsResourceCategory);
        return CommonReturn.success();
    }

    @PutMapping("updateUmsResourceCate")
    public CommonReturn updateUmsResource(UmsResourceCategory umsResourceCategory){
        iUmsResourceCategoryService.updateById(umsResourceCategory);
        return CommonReturn.success();
    }


}
