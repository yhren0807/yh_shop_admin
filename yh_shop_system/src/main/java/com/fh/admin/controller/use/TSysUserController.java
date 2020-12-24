package com.fh.admin.controller.use;


import com.fh.admin.entity.TSysUser;
import com.fh.admin.service.use.ITSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yhren
 * @since 2020-12-07
 */
@RestController
@RequestMapping("userController")
public class TSysUserController {

    @Autowired
    private ITSysUserService userService;

    @GetMapping("/queryUser")
    public List<TSysUser> queryUser(){

        return userService.list();
    }

}
