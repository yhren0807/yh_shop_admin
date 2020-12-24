package com.fh.t_categorp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.t_categorp.entity.UmsMemberLevel;
import com.fh.t_categorp.service.IUmsMemberLevelService;
import com.fh.utils.returns.CommonReturn;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 会员等级表 前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-13
 */
@RestController
@RequestMapping("umsMemLevel")
public class UmsMemberLevelController {

    @Autowired
    private IUmsMemberLevelService iUmsMemberLevelService;

    @GetMapping("queryMemberLevel")
    public CommonReturn queryMemberLevel(@RequestParam Integer id){
        QueryWrapper<UmsMemberLevel> wrapper=new QueryWrapper<>();
        wrapper.eq("default_status",id);
        List<UmsMemberLevel> list = iUmsMemberLevelService.list(wrapper);
        return CommonReturn.success(list);
    }

}
