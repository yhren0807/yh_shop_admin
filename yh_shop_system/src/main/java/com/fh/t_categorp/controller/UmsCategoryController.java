package com.fh.t_categorp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.t_categorp.entity.UmsCategory;
import com.fh.t_categorp.entity.vo.UmsCategorpVo;
import com.fh.t_categorp.service.IUmsCategoryService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-09
 */
@RestController
@RequestMapping("umsCategorp")
public class UmsCategoryController {

    @Autowired
    private IUmsCategoryService umsCategoryService;

    @GetMapping("queryCategory")
    public CommonReturn queryCategory(Page<UmsCategory> page, UmsCategorpVo umsCategorpVo){
        QueryWrapper<UmsCategory> wrapper=new QueryWrapper<UmsCategory>();
        wrapper.eq("level",umsCategorpVo.getLevelVo());
        if (umsCategorpVo.getParentId()!=null){
            wrapper.eq("parent_id",umsCategorpVo.getParentId());
        }else{
            wrapper.eq("parent_id",0);
        }
        IPage<UmsCategory> page1 = umsCategoryService.page(page,wrapper);
        return CommonReturn.success(page1);
    }

    @GetMapping("queryParent")
    public CommonReturn queryParent(){
        QueryWrapper<UmsCategory> wrapper=new QueryWrapper<UmsCategory>();
        wrapper.eq("parent_id",0);
        List<UmsCategory> list = umsCategoryService.list(wrapper);
        return CommonReturn.success(list);
    }

    @PostMapping("saveCategory")
    public CommonReturn saveCategory( UmsCategory umsCategory){
        umsCategoryService.save(umsCategory);
        return CommonReturn.success();
    }
    @GetMapping("queryById")
    public CommonReturn queryById(@RequestParam("id") Integer id){
        UmsCategory one = umsCategoryService.getById(id);
        return CommonReturn.success(one);
    }
    @PutMapping("updateCategory")
    public CommonReturn updateCategory( UmsCategory umsCategory){
        umsCategoryService.updateById(umsCategory);
        return CommonReturn.success();
    }

    @GetMapping("queryCateOptions")
    public CommonReturn queryCateOptions(){
        List<Map<String,Object>> list=umsCategoryService.queryCateOptions();
        return CommonReturn.success(list);
    }


}
