package com.fh.t_ums.service;

import com.fh.t_ums.entity.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
public interface IUmsMenuService extends IService<UmsMenu> {
    //查询除所有的菜单数据
    List<Map<String,Object>> queryZtreeUmsMenu();
}
