package com.fh.t_brand.service;

import com.fh.t_brand.entity.UmsHomeBrand;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fh.t_brand.entity.page.PageBean;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 首页推荐品牌表 服务类
 * </p>
 *
 * @author yhren
 * @since 2020-12-08
 */
public interface IUmsHomeBrandService extends IService<UmsHomeBrand> {
     PageBean queryBrand(PageBean pageBean);


}
