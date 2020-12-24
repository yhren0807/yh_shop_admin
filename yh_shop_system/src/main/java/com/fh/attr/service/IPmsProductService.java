package com.fh.attr.service;

import com.fh.attr.entity.Bo.ProductBo;
import com.fh.attr.entity.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author yhren
 * @since 2020-12-10
 */
public interface IPmsProductService extends IService<PmsProduct> {

    void saveData(ProductBo productBo);

    ProductBo queryProductAll(Long productId);
}
