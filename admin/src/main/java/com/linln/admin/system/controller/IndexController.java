package com.linln.admin.system.controller;

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
        model.addAttribute("qq", "<option value ='volvo'><script>alert(111)</script>Volvo</option>");
        model.addAttribute("one", 1);
        int[] list = {111,222,333};
        model.addAttribute("list", list);
        return "/system/main/index";
    }
}
