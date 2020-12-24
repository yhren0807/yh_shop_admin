package com.fh.attr.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.attr.entity.PmsProductAttributeCategory;
import com.fh.attr.service.IPmsProductAttributeCategoryService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-10
 */
@RestController
@RequestMapping("attributeCategory")
public class PmsProductAttributeCategoryController {

    @Autowired
    private IPmsProductAttributeCategoryService attributeCategoryService;

    @GetMapping("queryAttributeCategory")
    public CommonReturn queryAttributeCategory(Page<PmsProductAttributeCategory> page){
        Page<PmsProductAttributeCategory> page1 = attributeCategoryService.page(page);
        return CommonReturn.success(page1);
    }

    @PostMapping("saveAttributeCategory")
    public CommonReturn saveAttributeCategory(PmsProductAttributeCategory attributeCategory){
        attributeCategory.setAttributeCount(0);
        attributeCategory.setParamCount(0);
        attributeCategoryService.save(attributeCategory);
        return CommonReturn.success();
    }

}
