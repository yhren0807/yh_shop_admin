package com.fh.t_ums.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.t_ums.entity.UmsMenu;
import com.fh.t_ums.entity.vo.MenuVo;
import com.fh.t_ums.service.IUmsMenuService;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
@RestController
@RequestMapping("umsMenuController")
public class UmsMenuController {


    @Autowired
    private IUmsMenuService iUmsMenuService;

    //查询除所有的菜单数据
     @GetMapping("queryZtreeUmsMenu")
     public CommonReturn queryZtreeUmsMenu(){
         List<Map<String,Object>> maps=iUmsMenuService.queryZtreeUmsMenu();
         return CommonReturn.success(maps);
     }

     @PutMapping("updateUmsMenu")
     public CommonReturn updateUmsMenu(UmsMenu umsMenu){
         iUmsMenuService.updateById(umsMenu);
         return CommonReturn.success();
     }

    @PostMapping("saveUmsMenu")
    public CommonReturn saveUmsMenu(UmsMenu umsMenu){
        iUmsMenuService.save(umsMenu);
        return CommonReturn.success();
    }

     @DeleteMapping("deleteUmsMenu")
     public CommonReturn deleteUmsMenu(Integer id){
         iUmsMenuService.removeById(id);
         return CommonReturn.success();
     }

    @GetMapping("queryUmsMenuById")
    public CommonReturn queryUmsMenuById(Integer id){
        UmsMenu byId = iUmsMenuService.getById(id);
        return CommonReturn.success(byId);
    }

    //查询除所有的菜单数据
    @GetMapping("queryUmsMenu")
    public CommonReturn queryUmsMenu(Page<UmsMenu> page,MenuVo menuVo){
        QueryWrapper<UmsMenu> wrapper=new QueryWrapper<>();
        if (menuVo.getLevel()!=null){
            wrapper.eq("level",menuVo.getLevel());
        }
        if (menuVo.getPid()!=null){
            wrapper.eq("parent_id",menuVo.getPid());
        }
        Page<UmsMenu> page1 = iUmsMenuService.page(page,wrapper);
        return CommonReturn.success(page1);
    }


}
