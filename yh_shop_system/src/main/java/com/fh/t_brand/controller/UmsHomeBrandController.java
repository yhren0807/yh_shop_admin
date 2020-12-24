package com.fh.t_brand.controller;


import com.fh.t_brand.entity.UmsHomeBrand;
import com.fh.t_brand.entity.page.PageBean;
import com.fh.t_brand.service.IUmsHomeBrandService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 首页推荐品牌表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-08
 */
@RestController
@RequestMapping("umsHomeBrandController")
public class UmsHomeBrandController {

    @Autowired
    private IUmsHomeBrandService iUmsHomeBrandService;

    //查询
    @GetMapping("queryUmsHomeBrand")
    public CommonReturn queryBrand(PageBean pageBean) {
        pageBean=iUmsHomeBrandService.queryBrand(pageBean);
        return CommonReturn.success(pageBean);
    }
    //删除
    @DeleteMapping("deleteUmsHomeBrand")
    public CommonReturn deleteBrand(Integer id){
        iUmsHomeBrandService.removeById(id);
        return CommonReturn.success();
    }
    //新增
    @PostMapping("addUmsHomeBrand")
    public CommonReturn addBrand(UmsHomeBrand umsHomeBrand) {
        iUmsHomeBrandService.save(umsHomeBrand);
        return CommonReturn.success();
    }

    //修改
    @PutMapping("updateUmsHomeBrand")
    public CommonReturn updateBrand(UmsHomeBrand umsHomeBrand) {
        iUmsHomeBrandService.updateById(umsHomeBrand);
        return CommonReturn.success();
    }
   //批量删除
    @DeleteMapping("deleteBathUmsHomeBrand")
    public CommonReturn deleteBathBrand(String strUmsHomeBrandId){
        String[] split = strUmsHomeBrandId.split(",");
        List<String> list = Arrays.asList(split);
        iUmsHomeBrandService.removeByIds(list);
        return  CommonReturn.success();
    }
    //查询
    @GetMapping("queryDescription")
    @PreAuthorize("hasAuthority('pms:brand')")
    public CommonReturn queryBrand() {
        List<UmsHomeBrand> list=iUmsHomeBrandService.list();
        return CommonReturn.success(list);
    }


}
