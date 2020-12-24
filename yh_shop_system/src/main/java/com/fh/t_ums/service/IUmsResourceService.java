package com.fh.t_ums.service;

import com.fh.t_ums.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author yhren
 * @since 2020-12-17
 */
public interface IUmsResourceService extends IService<UmsResource> {

    List<Map<String,Object>> queryResourcesByCatePory();
}
