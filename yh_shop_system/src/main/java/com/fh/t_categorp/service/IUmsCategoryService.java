package com.fh.t_categorp.service;

import com.fh.t_categorp.entity.UmsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author yhren
 * @since 2020-12-09
 */
public interface IUmsCategoryService extends IService<UmsCategory> {
    List<Map<String,Object>> queryCateOptions();
}
