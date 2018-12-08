package com.linln.admin.system.controller;

import com.linln.admin.core.web.TimoExample;
import com.linln.admin.system.domain.ActionLog;
import com.linln.admin.system.service.ActionLogService;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/actionLog")
public class ActionLogController {

    @Autowired
    private ActionLogService actionLogService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("/actionLog/index")
    public String index(Model model, ActionLog actionLog){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取日志列表
        Example<ActionLog> example = TimoExample.of(actionLog, matcher);
        Page<ActionLog> list = actionLogService.getPageList(example);

        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);
        return "/system/actionLog/index";
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("/actionLog/detail")
    public String toDetail(@PathVariable("id") Long id, Model model){
        ActionLog actionLog = actionLogService.getId(id);
        model.addAttribute("actionLog",actionLog);
        return "/system/actionLog/detail";
    }

    /**
     * 删除指定的日志
     */
    @RequestMapping("/status/delete")
    @RequiresPermissions("/actionLog/delete")
    @ResponseBody
    public ResultVo delete(
            @RequestParam(value = "ids", required = false) Long id){
        if (id != null){
            actionLogService.deleteId(id);
            return ResultVoUtil.success("删除日志成功");
        }else {
            actionLogService.emptyLog();
            return ResultVoUtil.success("清空日志成功");
        }
    }
}
