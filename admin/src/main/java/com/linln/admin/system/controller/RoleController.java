package com.linln.admin.system.controller;

import com.linln.admin.core.constant.AdminConst;
import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.log.action.RoleAction;
import com.linln.admin.core.log.action.StatusAction;
import com.linln.admin.core.log.annotation.ActionLog;
import com.linln.admin.core.shiro.ShiroUtil;
import com.linln.admin.core.web.TimoExample;
import com.linln.admin.system.domain.Menu;
import com.linln.admin.system.domain.Role;
import com.linln.admin.system.service.MenuService;
import com.linln.admin.system.service.RoleService;
import com.linln.admin.system.validator.RoleForm;
import com.linln.core.utils.FormBeanUtil;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("/role/index")
    public String index(Model model, Role role){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching().
                withMatcher("title", match -> match.contains());

        // 获取角色列表
        Example<Role> example = TimoExample.of(role, matcher);
        Page<Role> list = roleService.getPageList(example);

        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);
        return "/system/role/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("/role/add")
    public String toAdd(){
        return "/system/role/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("/role/edit")
    public String toEdit(@PathVariable("id") Long id, Model model){
        Role role = roleService.getId(id);
        model.addAttribute("role",role);
        return "/system/role/add";
    }

    /**
     * 保存添加/修改的数据
     * @param roleForm 表单验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"/role/add","/role/edit"})
    @ResponseBody
    @ActionLog(key = RoleAction.ROLE_SAVE, action = RoleAction.class)
    public ResultVo save(@Validated RoleForm roleForm){
        // 不允许操作管理员角色数据
        if (roleForm.getId() !=null && roleForm.getId().equals(AdminConst.ADMIN_ROLE_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }

        // 将验证的数据复制给实体类
        Role role = new Role();
        if(roleForm.getId() != null){
            role = roleService.getId(roleForm.getId());
        }
        String[] ignore = {"users", "menus"};
        FormBeanUtil.copyProperties(roleForm, role, ignore);

        // 保存数据
        roleService.save(role);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到授权页面
     */
    @GetMapping("/auth")
    @RequiresPermissions("/role/auth")
    public String toAuth(@RequestParam(value = "ids") Long id, Model model){
        model.addAttribute("id",id);
        return "/system/role/auth";
    }

    /**
     * 获取权限资源列表
     */
    @GetMapping("/authList")
    @RequiresPermissions("/role/auth")
    @ResponseBody
    public ResultVo authList(@RequestParam(value = "ids") Long id){
        // 获取指定角色权限资源
        Role role = roleService.getId(id);
        Set<Menu> authMenus = role.getMenus();
        // 获取全部菜单列表
        Sort sort = new Sort(Sort.Direction.ASC, "type", "sort");
        List<Menu> list = menuService.getList(sort);
        // 融合两项数据
        list.forEach(menu -> {
            if(authMenus.contains(menu)){
                menu.setRemark("auth:true");
            }else {
                menu.setRemark("");
            }
        });
        return ResultVoUtil.success(list);
    }

    /**
     * 保存授权信息
     */
    @PostMapping("/auth")
    @RequiresPermissions("/role/auth")
    @ResponseBody
    @ActionLog(key = RoleAction.ROLE_AUTH, action = RoleAction.class)
    public ResultVo auth(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "authId", required = false) List<Long> authIds){
        // 不允许操作管理员角色数据
        if (id.equals(AdminConst.ADMIN_ROLE_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }

        // 将查询的数据关联起来
        Role role = roleService.getId(id);
        List<Menu> menuList = null;
        if(authIds != null){
            menuList = menuService.getIdList(authIds);
            role.setMenus(new HashSet<>(menuList));
        }else {
            role.setMenus(null);
        }

        // 保存数据
        roleService.save(role);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("/role/detail")
    public String toDetail(@PathVariable("id") Long id, Model model){
        Role role = roleService.getId(id);
        model.addAttribute("role",role);
        return "/system/role/detail";
    }

    /**
     * 跳转到拥有该角色的用户列表页面
     */
    @GetMapping("/userList/{id}")
    @RequiresPermissions("/role/detail")
    public String toUserList(@PathVariable("id") Long id, Model model){
        Role role = roleService.getId(id);
        model.addAttribute("list",role.getUsers());
        return "/system/role/user_list";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("/role/status")
    @ResponseBody
    @ActionLog(name = "角色状态", action = StatusAction.class)
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> idList){
        // 不能修改超级管理员角色状态
        if(idList.contains(AdminConst.ADMIN_ROLE_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_STATUS);
        }

        try {
            // 获取状态StatusEnum对象
            StatusEnum statusEnum = StatusEnum.valueOf(param.toUpperCase());
            // 更新状态
            Integer count = roleService.updateStatus(statusEnum,idList);
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
