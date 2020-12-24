package com.fh.t_categorp.service.impl;

import com.fh.t_brand.entity.UmsHomeBrand;
import com.fh.t_categorp.entity.UmsCategory;
import com.fh.t_categorp.mapper.UmsCategoryMapper;
import com.fh.t_categorp.service.IUmsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author yhren
 * @since 2020-12-09
 */
@Service
public class UmsCategoryServiceImpl extends ServiceImpl<UmsCategoryMapper, UmsCategory> implements IUmsCategoryService {


    @Override
    public List<Map<String, Object>> queryCateOptions() {
        List<UmsCategory> list=super.list();
        return queryCateBrand(list,0l);
    }

    public List<Map<String,Object>> queryCateBrand(List<UmsCategory> list,Long pid){
        List<Map<String,Object>> list1=new ArrayList<>();
        list.forEach(umsCategory -> {
            Map<String,Object> map=null;
            if (umsCategory.getParentId().equals(pid)){
               map=new HashMap<>();
                map.put("value",umsCategory.getId());
                map.put("label",umsCategory.getName());

                if (umsCategory.getParentId().equals(0l)){
                    map.put("children",queryCateBrand(list,umsCategory.getId()));
                }
            }
            if (map!=null){
                list1.add(map);
            }
        });
        return list1;
    }

}
