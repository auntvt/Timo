package com.linln.admin.system.controller;

import com.linln.admin.system.validator.RoleValid;
import com.linln.common.constant.AdminConst;
import com.linln.common.enums.ResultEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.exception.ResultException;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.actionLog.action.RoleAction;
import com.linln.component.actionLog.action.StatusAction;
import com.linln.component.actionLog.annotation.ActionLog;
import com.linln.component.actionLog.annotation.EntityParam;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.Menu;
import com.linln.modules.system.domain.Role;
import com.linln.modules.system.service.MenuService;
import com.linln.modules.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
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
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("system:role:index")
    public String index(Model model, Role role){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching().
                withMatcher("title", match -> match.contains());

        // 获取角色列表
        Example<Role> example = Example.of(role, matcher);
        Page<Role> list = roleService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/role/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:role:add")
    public String toAdd(){
        return "/system/role/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:role:edit")
    public String toEdit(@PathVariable("id") Role role, Model model){
        model.addAttribute("role", role);
        return "/system/role/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     * @param role 实体对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:role:add", "system:role:edit"})
    @ResponseBody
    @ActionLog(key = RoleAction.ROLE_SAVE, action = RoleAction.class)
    public ResultVo save(@Validated RoleValid valid, @EntityParam Role role){
        // 不允许操作管理员角色数据
        if (role.getId() !=null && role.getId().equals(AdminConst.ADMIN_ROLE_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }

        // 判断角色编号是否重复
        if (roleService.repeatByName(role)) {
            throw new ResultException(ResultEnum.ROLE_EXIST);
        }

        // 复制保留无需修改的数据
        if(role.getId() != null){
            Role beRole = roleService.getById(role.getId());
            String[] fields = {"users", "menus"};
            EntityBeanUtil.copyProperties(beRole, role, fields);
        }

        // 保存数据
        roleService.save(role);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到授权页面
     */
    @GetMapping("/auth")
    @RequiresPermissions("system:role:auth")
    public String toAuth(@RequestParam(value = "ids") Long id, Model model){
        model.addAttribute("id", id);
        return "/system/role/auth";
    }

    /**
     * 获取权限资源列表
     */
    @GetMapping("/authList")
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    public ResultVo authList(@RequestParam(value = "ids") Role role){
        // 获取指定角色权限资源
        Set<Menu> authMenus = role.getMenus();
        // 获取全部菜单列表
        List<Menu> list = menuService.getListBySortOk();
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
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    @ActionLog(key = RoleAction.ROLE_AUTH, action = RoleAction.class)
    public ResultVo auth(
            @RequestParam(value = "id", required = true) Role role,
            @RequestParam(value = "authId", required = false) HashSet<Menu> menus){
        // 不允许操作管理员角色数据
        if (role.getId().equals(AdminConst.ADMIN_ROLE_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }

        // 更新角色菜单
        role.setMenus(menus);

        // 保存数据
        roleService.save(role);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:role:detail")
    public String toDetail(@PathVariable("id") Role role, Model model){
        model.addAttribute("role", role);
        return "/system/role/detail";
    }

    /**
     * 跳转到拥有该角色的用户列表页面
     */
    @GetMapping("/userList/{id}")
    @RequiresPermissions("system:role:detail")
    public String toUserList(@PathVariable("id") Role role, Model model){
        model.addAttribute("list", role.getUsers());
        return "/system/role/userList";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("system:role:status")
    @ResponseBody
    @ActionLog(name = "角色状态", action = StatusAction.class)
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids){
        // 不能修改超级管理员角色状态
        if(ids.contains(AdminConst.ADMIN_ROLE_ID)){
            throw new ResultException(ResultEnum.NO_ADMINROLE_STATUS);
        }

        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (roleService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}
