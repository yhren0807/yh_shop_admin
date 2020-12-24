package com.fh.t_ums.service.impl;

import com.fh.t_ums.entity.UmsMenu;
import com.fh.t_ums.mapper.UmsMenuMapper;
import com.fh.t_ums.service.IUmsMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {

    @Override
    public List<Map<String, Object>> queryZtreeUmsMenu() {
        List<UmsMenu> list = super.list();
        return getUmsMenu(list,0l);
    }

    private List<Map<String,Object>> getUmsMenu(List<UmsMenu> list, Long pid) {
        List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
        list.forEach(UmsMenu->{
         Map<String,Object> map=null;
         if (UmsMenu.getParentId()==pid){
             map=new HashMap<>();
             map.put("id",UmsMenu.getId());
             map.put("label",UmsMenu.getTitle());
             map.put("children",getUmsMenu(list,UmsMenu.getId()));
         }
         if (map!=null){
             list1.add(map);
         }
        });
        return list1;
    }
}
