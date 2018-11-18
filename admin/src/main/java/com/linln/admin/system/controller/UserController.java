package com.linln.admin.system.controller;


import com.linln.admin.core.config.properties.ProjectProperties;
import com.linln.admin.core.constant.AdminConst;
import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.enums.UserIsRoleEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.log.action.StatusAction;
import com.linln.admin.core.log.action.UserAction;
import com.linln.admin.core.log.annotation.ActionLog;
import com.linln.admin.system.validator.UserForm;
import com.linln.admin.core.shiro.ShiroUtil;
import com.linln.admin.system.domain.Role;
import com.linln.admin.system.domain.User;
import com.linln.admin.system.service.RoleService;
import com.linln.admin.system.service.UserService;
import com.linln.core.utils.FormBeanUtil;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.vo.ResultVo;
import com.linln.admin.core.utils.TimoExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 列表页面
     *
     * @param pageIndex 页码
     * @param pageSize  获取数据长度
     */
    @GetMapping("/index")
    @RequiresPermissions("/user/index")
    public String index(Model model, User user,
                        @RequestParam(value = "page", defaultValue = "1") int pageIndex,
                        @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching().
                withMatcher("nickname", match -> match.contains()).
                withIgnorePaths("password", "salt", "roles", "isRole");

        // 获取用户列表
        Example<User> example = TimoExample.of(user, matcher);
        Page<User> list = userService.getPageList(example, pageIndex, pageSize);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/user/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("/user/add")
    public String toAdd() {
        return "/system/user/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("/user/edit")
    public String toEdit(@PathVariable("id") Long id, Model model) {
        User user = userService.getId(id);
        model.addAttribute("user", user);
        return "/system/user/add";
    }

    /**
     * 保存添加/修改的数据
     *
     * @param userForm 表单验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"/user/add", "/user/edit"})
    @ResponseBody
    @ActionLog(key = UserAction.USER_SAVE, action = UserAction.class)
    public ResultVo save(@Validated UserForm userForm) {

        // 验证数据是否合格
        if (userForm.getId() == null) {
            // 判断账号是否重复
            User soleUser = userService.getByName(userForm.getUsername(), StatusEnum.FREEZED.getCode());
            if (soleUser != null) {
                throw new ResultException(ResultEnum.USER_EXIST);
            }

            // 判断密码是否为空
            if (userForm.getPassword().isEmpty() || "".equals(userForm.getPassword().trim())) {
                throw new ResultException(ResultEnum.USER_PWD_NULL);
            }

            // 判断两次密码是否一致
            if (!userForm.getPassword().equals(userForm.getConfirm())) {
                throw new ResultException(ResultEnum.USER_INEQUALITY);
            }

            // 对密码进行加密
            String salt = ShiroUtil.getRandomSalt();
            String encrypt = ShiroUtil.encrypt(userForm.getPassword(), salt);
            userForm.setPassword(encrypt);
            userForm.setSalt(salt);
        }

        // 将验证的数据复制给实体类
        User user = new User();
        if (userForm.getId() != null) {
            // 不允许操作超级管理员数据
            if (userForm.getId().equals(AdminConst.ADMIN_ID) &&
                    !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
                throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
            }
            // 判断账号是否重复
            user = userService.getByName(userForm.getUsername(), StatusEnum.FREEZED.getCode());
            if (user != null && !user.getId().equals(userForm.getId())) {
                throw new ResultException(ResultEnum.USER_EXIST);
            }
            String[] ignore = {"password", "salt", "picture", "roles", "isRole"};
            FormBeanUtil.copyProperties(userForm, user, ignore);
        } else {
            FormBeanUtil.copyProperties(userForm, user);
        }

        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("/user/detail")
    public String toDetail(@PathVariable("id") Long id, Model model) {
        User user = userService.getId(id);
        model.addAttribute("user", user);
        return "/system/user/detail";
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/pwd")
    @RequiresPermissions("/user/pwd")
    public String toEditPassword(Model model,
                                 @RequestParam(value = "ids", required = false) List<Long> idList) {
        model.addAttribute("idList", idList);
        return "/system/user/pwd";
    }

    /**
     * 修改密码
     */
    @PostMapping("/pwd")
    @RequiresPermissions("/user/pwd")
    @ResponseBody
    @ActionLog(key = UserAction.EDIT_PWD, action = UserAction.class)
    public ResultVo editPassword(String password, String confirm,
                                 @RequestParam(value = "ids", required = false) List<Long> idList) {

        // 判断密码是否为空
        if (password.isEmpty() || "".equals(password.trim())) {
            throw new ResultException(ResultEnum.USER_PWD_NULL);
        }

        // 判断两次密码是否一致
        if (!password.equals(confirm)) {
            throw new ResultException(ResultEnum.USER_INEQUALITY);
        }

        // 不允许操作超级管理员数据
        if (idList.contains(AdminConst.ADMIN_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }

        // 修改密码，对密码进行加密
        List<User> userList = userService.getIdList(idList);
        userList.forEach(user -> {
            String salt = ShiroUtil.getRandomSalt();
            String encrypt = ShiroUtil.encrypt(password, salt);
            user.setPassword(encrypt);
            user.setSalt(salt);
        });

        // 保存数据
        userService.save(userList);
        return ResultVoUtil.success("修改成功");
    }

    /**
     * 跳转到角色分配页面
     */
    @GetMapping("/role")
    @RequiresPermissions("/user/role")
    public String toRole(@RequestParam(value = "ids") Long id, Model model) {
        // 获取指定用户角色列表
        User user = userService.getId(id);
        Set<Role> authRoles = user.getRoles();
        // 获取全部菜单列表
        Sort sort = new Sort(Sort.Direction.ASC, "createDate");
        List<Role> list = roleService.getList(sort);
        // 融合两项数据
        list.forEach(role -> {
            if (authRoles.contains(role)) {
                role.setRemark("auth:true");
            } else {
                role.setRemark("");
            }
        });

        model.addAttribute("id", id);
        model.addAttribute("list", list);
        return "/system/user/role";
    }

    /**
     * 保存角色分配信息
     */
    @PostMapping("/role")
    @RequiresPermissions("/user/role")
    @ResponseBody
    @ActionLog(key = UserAction.EDIT_ROLE, action = UserAction.class)
    public ResultVo auth(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "roleId", required = false) List<Long> roleIds) {

        // 不允许操作超级管理员数据
        if (id.equals(AdminConst.ADMIN_ID) &&
                !ShiroUtil.getSubject().getId().equals(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }

        // 将查询的数据关联起来
        User user = userService.getId(id);
        List<Role> roleList = null;
        if (roleIds != null) {
            roleList = roleService.getIdList(roleIds);
            user.setRoles(new HashSet<>(roleList));
            user.setIsRole(UserIsRoleEnum.YES.getCode());
        } else {
            user.setRoles(null);
            user.setIsRole(UserIsRoleEnum.NO.getCode());
        }

        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 获取用户头像
     */
    @GetMapping("/picture")
    public void picture(String p, HttpServletResponse response, ProjectProperties properties) throws IOException {
        String defaultPath = "/images/user-picture.jpg";
        if (!(StringUtils.isEmpty(p) || p.equals(defaultPath))) {
            String fuPath = properties.getFileUploadPath();
            String spPath = properties.getStaticPathPattern().replace("*", "");
            String s = fuPath + p.replace(spPath, "");
            File file = new File(fuPath + p.replace(spPath, ""));
            if(file.exists()){
                FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
                return;
            }
        }
        Resource resource = new ClassPathResource("static" + defaultPath);
        FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("/user/status")
    @ResponseBody
    @ActionLog(name = "用户状态", action = StatusAction.class)
    public ResultVo delete(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> idList) {
        // 不能修改超级管理员状态
        if (idList.contains(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_STATUS);
        }

        try {
            // 获取状态StatusEnum对象
            StatusEnum statusEnum = StatusEnum.valueOf(param.toUpperCase());
            // 更新状态
            Integer count = userService.updateStatus(statusEnum, idList);
            if (count > 0) {
                return ResultVoUtil.success(statusEnum.getMessage() + "成功");
            } else {
                return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
            }
        } catch (IllegalArgumentException e) {
            throw new ResultException(ResultEnum.STATUS_ERROR);
        }
    }


}
