package com.fh.t_brand.service.impl;

import com.fh.t_brand.entity.UmsHomeBrand;
import com.fh.t_brand.entity.page.PageBean;
import com.fh.t_brand.mapper.UmsHomeBrandMapper;
import com.fh.t_brand.service.IUmsHomeBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页推荐品牌表 服务实现类
 * </p>
 *
 * @author yhren
 * @since 2020-12-08
 */
@Service
public class UmsHomeBrandServiceImpl extends ServiceImpl<UmsHomeBrandMapper, UmsHomeBrand> implements IUmsHomeBrandService {

    @Autowired
    private UmsHomeBrandMapper umsHomeBrandMapper;

    @Override
    public PageBean queryBrand(PageBean pageBean) {
        //查询数据总条数
        Long count=umsHomeBrandMapper.selectUserLong();
        pageBean.setCount(count);
        //计算起始下标
        pageBean.setStartIndex((pageBean.getPage()-1)*pageBean.getLimit());
        //得出查询数据
        List<UmsHomeBrand> list=umsHomeBrandMapper.queryBrand(pageBean);
        pageBean.setCode(0);
        pageBean.setData(list);
        return pageBean;
    }


}
