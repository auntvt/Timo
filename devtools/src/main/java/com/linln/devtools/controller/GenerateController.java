package com.linln.devtools.controller;

import com.linln.admin.system.domain.User;
import com.linln.core.utils.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/code")
public class GenerateController {

    @GetMapping("index")
    public String index(){
        User user = new User();
        String projectPath = ToolUtil.getProjectPath();

        return "/devtools/code/index";
    }


}
