package com.fh.attr.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.attr.entity.Bo.ProductBo;
import com.fh.attr.entity.PmsProduct;
import com.fh.attr.service.IPmsProductService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-10
 */
@RestController
@RequestMapping("pmsProductGroup")
public class PmsProductController {

    @Autowired
    private IPmsProductService iPmsProductService;

    @GetMapping("queryCatePoryGrop")
    public CommonReturn queryCatePoryGrop(Page<PmsProduct> productPage){
        Page<PmsProduct> page = iPmsProductService.page(productPage);
        return CommonReturn.success(page);
    }

    @PostMapping("saveProduct")
    public CommonReturn saveProduct(@RequestBody ProductBo productBo){
        PmsProduct pmsProduct=productBo;
        iPmsProductService.saveData(productBo);
        return CommonReturn.success();
    }

    @GetMapping("queryProductAll")
    public CommonReturn  queryProductAll(Long productId){
        ProductBo productBo=iPmsProductService.queryProductAll(productId);
        return CommonReturn.success(productBo);
    }








}
