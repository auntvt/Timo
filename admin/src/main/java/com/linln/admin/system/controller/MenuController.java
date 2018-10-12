package com.linln.admin.system.controller;


import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.thymeleaf.utility.DictUtil;
import com.linln.admin.system.validator.MenuForm;
import com.linln.admin.core.utils.TimoExample;
import com.linln.admin.system.domain.Menu;
import com.linln.admin.system.service.MenuService;
import com.linln.core.utils.FormBeanUtils;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 跳转到列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("/menu/index")
    public String index(Model model, Menu menu){
        String search = "";
        if(menu.getStatus() != null){
            search += "status=" + menu.getStatus();
        }
        if(menu.getTitle() != null){
            search += "&title=" + menu.getTitle();
        }
        if(menu.getUrl() != null){
            search += "&url=" + menu.getUrl();
        }
        model.addAttribute("search", search);
        return "/system/menu/index";
    }

    /**
     * 菜单数据列表
     */
    @GetMapping("/list")
    @RequiresPermissions("/menu/index")
    @ResponseBody
    public ResultVo list(Menu menu){
        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching().
                withMatcher("title", match -> match.contains());

        // 获取用户列表
        Example<Menu> example = TimoExample.of(menu, matcher);
        Sort sort = new Sort(Sort.Direction.ASC, "type", "sort");
        List<Menu> list = menuService.getList(example, sort);
        list.forEach(editMenu -> {
            String type = String.valueOf(editMenu.getType());
            editMenu.setRemark(DictUtil.enumValue("ENUM_MENU_TYPE", type));
        });
        return ResultVoUtil.success(list);
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping({"/add", "/add/{pid}"})
    @RequiresPermissions("/menu/add")
    public String toAdd(@PathVariable(value = "pid",required = false) Long pid, Model model){
        if(pid != null){
            Menu pmenu = menuService.getId(pid);
            model.addAttribute("pmenu",pmenu);
        }
        return "/system/menu/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("/menu/edit")
    public String toEdit(@PathVariable("id") Long id, Model model){
        Menu menu = menuService.getId(id);
        Menu pmenu = menuService.getId(menu.getPid());
        if(pmenu == null){
            Menu newMenu = new Menu();
            newMenu.setId((long) 0);
            newMenu.setTitle("顶级菜单");
            pmenu = newMenu;
        }
        model.addAttribute("menu",menu);
        model.addAttribute("pmenu",pmenu);
        return "/system/menu/add";
    }

    /**
     * 保存添加/修改的数据
     * @param menuForm 表单验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"/menu/add","/menu/edit"})
    @ResponseBody
    public ResultVo save(@Validated MenuForm menuForm){
        if(menuForm.getId() == null){
            // 添加最后的排序
            Integer sortMax = menuService.getSortMax(menuForm.getPid());
            if(sortMax == null){
                sortMax = 0;
            }
            menuForm.setSort(sortMax+1);

            // 添加全部上级序号
            if(menuForm.getPid() != 0){
                Menu pMenu = menuService.getId(menuForm.getPid());
                menuForm.setPids(pMenu.getPids() + ",[" + menuForm.getPid() + "]");
            }else {
                menuForm.setPids("[0]");
            }
        }

        // 将验证的数据复制给实体类
        Menu menu = new Menu();
        if(menuForm.getId() != null){
            menu = menuService.getId(menuForm.getId());
            menuForm.setPids(menu.getPids());
            menuForm.setSort(menu.getSort());
        }
        FormBeanUtils.copyProperties(menuForm, menu);

        // 保存数据
        Menu save = menuService.save(menu);
        if(save != null){
            return ResultVoUtil.success("保存成功");
        }else{
            return ResultVoUtil.error("保存失败，请重新输入");
        }
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("/menu/detail")
    public String toDetail(@PathVariable("id") Long id, Model model){
        Menu menu = menuService.getId(id);
        model.addAttribute("menu",menu);
        return "/system/menu/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("/menu/status")
    @ResponseBody
    public ResultVo delete(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> idList){
        try {
            // 获取状态StatusEnum对象
            StatusEnum statusEnum = StatusEnum.valueOf(param.toUpperCase());
            // 更新状态
            Integer count = menuService.updateStatus(statusEnum,idList);
            if (count > 0){
                return ResultVoUtil.success(statusEnum.getMessage()+"成功");
            }else{
                return ResultVoUtil.error(statusEnum.getMessage()+"失败，请重新操作");
            }
        } catch (IllegalArgumentException e){
            throw new ResultException(ResultEnum.STATUS_ERROR);
        }
    }


}
