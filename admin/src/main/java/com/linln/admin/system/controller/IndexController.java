package com.linln.admin.system.controller;

import com.linln.admin.core.log.action.UserAction;
import com.linln.admin.core.log.annotation.ActionLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
public class IndexController {
    /**
     * 主页
     */
    @GetMapping("/index")
    @RequiresPermissions("/index")
    public String index(Model model){

        return "/system/main/index";
    }
}
