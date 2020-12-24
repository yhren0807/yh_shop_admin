package com.fh.attr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.attr.entity.PmsProductAttribute;
import com.fh.attr.entity.vo.PmsAttrIbuteVo;
import com.fh.attr.service.IPmsProductAttributeService;
import com.fh.utils.returns.CommonReturn;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-10
 */
@RestController
@RequestMapping("productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private IPmsProductAttributeService attributeService;

    @GetMapping("queryAttributeSetting")
    public CommonReturn queryAttributeSetting(Page<PmsProductAttribute> page, PmsAttrIbuteVo ibuteVo){
        QueryWrapper<PmsProductAttribute> queryWrapper=new QueryWrapper<>();
        if (ibuteVo.getPid()!=null){
            queryWrapper.eq("product_attribute_category_id",ibuteVo.getPid());
        }
        if (ibuteVo.getType()!=null){
            queryWrapper.eq("type",ibuteVo.getType());
        }
        Page<PmsProductAttribute> page1 = attributeService.page(page,queryWrapper);
        return CommonReturn.success(page1);
    }

    @PostMapping("saveAttributeSetting")
    public CommonReturn saveAttributeSetting(PmsProductAttribute attribute){
        attributeService.save(attribute);
        return CommonReturn.success();
    }

    @PutMapping("updateAttributeSetting")
    public CommonReturn updateAttributeSetting(PmsProductAttribute attribute){
        attributeService.updateById(attribute);
        return CommonReturn.success();
    }

    @GetMapping("queryAttributeSettingById")
    public CommonReturn queryAttributeSettingById(Integer id){
        PmsProductAttribute byId = attributeService.getById(id);
        return CommonReturn.success(byId);
    }




}
