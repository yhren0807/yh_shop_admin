package com.fh.t_ums.service.impl;

import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.entity.UmsResourceCategory;
import com.fh.t_ums.mapper.UmsResourceMapper;
import com.fh.t_ums.service.IUmsResourceCategoryService;
import com.fh.t_ums.service.IUmsResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements IUmsResourceService {
    @Autowired
    private IUmsResourceCategoryService iUmsResourceCategoryService;

    @Override
    public List<Map<String, Object>> queryResourcesByCatePory() {
        List<UmsResource> list = super.list();
        List<UmsResourceCategory> list1 = iUmsResourceCategoryService.list();
        List<Map<String, Object>> resources=new ArrayList<Map<String, Object>>();
        for (int i = 0; i <list1.size() ; i++) {
           Map<String,Object> map=new HashMap<String,Object>();
            map.put("title",list1.get(i).getName());
            List<String> listCities=new ArrayList<String>();
            for (int j = 0; j <list.size(); j++) {
                if (list1.get(i).getId()==list.get(j).getCategoryId()){
                    listCities.add(list.get(j).getName());
                }
            }
            map.put("cities",listCities);
            map.put("checkedCities",new ArrayList<>());
            map.put("isIndeterminate", false);
            map.put("checkAll",false);
            resources.add(map);
        }
        return resources;
    }
}
