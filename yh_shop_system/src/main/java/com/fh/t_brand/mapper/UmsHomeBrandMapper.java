package com.fh.t_brand.mapper;

import com.fh.t_brand.entity.UmsHomeBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.t_brand.entity.page.PageBean;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 Mapper 接口
 * </p>
 *
 * @author yhren
 * @since 2020-12-08
 */
public interface UmsHomeBrandMapper extends BaseMapper<UmsHomeBrand> {

    Long selectUserLong();

    List<UmsHomeBrand> queryBrand(@Param("pageBean") PageBean pageBean);
}
