package com.linln.admin.system.controller;

import com.linln.admin.core.enums.MenuTypeEnum;
import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.enums.UserIsRoleEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.log.action.UserAction;
import com.linln.admin.core.log.annotation.ActionLog;
import com.linln.admin.core.utils.EhCacheUtil;
import com.linln.admin.system.domain.File;
import com.linln.admin.system.validator.UserForm;
import com.linln.admin.core.shiro.ShiroUtil;
import com.linln.admin.system.domain.Menu;
import com.linln.admin.system.domain.User;
import com.linln.admin.system.service.UserService;
import com.linln.core.enums.TimoResultEnum;
import com.linln.core.utils.FormBeanUtil;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.utils.SpringContextUtil;
import com.linln.core.vo.ResultVo;
import com.linln.core.wraps.URL;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    /**
     * 后台主体内容
     */
    @GetMapping("/")
    @RequiresPermissions("/index")
    public String main(Model model){
        User User = ShiroUtil.getSubject();
        // 封装菜单树形数据
        Map<Long,Menu> keyMenu = new HashMap<>();
        User.getRoles().forEach(role -> {
            role.getMenus().forEach(menu -> {
                if(menu.getStatus().equals(StatusEnum.OK.getCode())){
                    keyMenu.put(menu.getId(), menu);
                }
            });
        });
        Map<Long,Menu> treeMenu = new HashMap<>();
        keyMenu.forEach((id, menu) -> {
            if(!menu.getType().equals(MenuTypeEnum.NOT_MENU.getCode())){
                if(keyMenu.get(menu.getPid()) != null){
                    keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
                }else{
                    treeMenu.put(Long.valueOf(menu.getSort()), menu);
                }
            }
        });

        model.addAttribute("user",User);
        model.addAttribute("treeMenu",treeMenu);
        return "/main";
    }


    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/user_info")
    @RequiresPermissions("/index")
    public String toUserInfo(Model model){
        User user = ShiroUtil.getSubject();
        model.addAttribute("user", user);
        return "/system/main/user_info";
    }

    /**
     * 修改用户头像
     */
    @PostMapping("/user_picture")
    @RequiresPermissions("/index")
    @ResponseBody
    public ResultVo userPicture(@RequestParam("picture") MultipartFile picture){
        FileController fileController = SpringContextUtil.getBean(FileController.class);
        ResultVo imageResult = fileController.uploadPicture(picture);
        if(imageResult.getCode().equals(TimoResultEnum.SUCCESS.getCode())){
            User subject = ShiroUtil.getSubject();
            subject.setPicture(((File) imageResult.getData()).getPath());
            userService.save(subject);
            return ResultVoUtil.SAVE_SUCCESS;
        }else {
            return imageResult;
        }
    }

    /**
     * 保存修改个人信息
     */
    @PostMapping("/user_info")
    @RequiresPermissions("/index")
    @ResponseBody
    public ResultVo userInfo(@Validated UserForm userForm){
        // 不允许修改用户名
        User user = ShiroUtil.getSubject();
        if(!user.getUsername().equals(userForm.getUsername())){
            throw new ResultException(ResultEnum.STATUS_ERROR);
        }

        // 将验证的数据复制给实体类
        String[] ignore = {"id", "password", "salt", "roles", "isRole"};
        FormBeanUtil.copyProperties(userForm, user, ignore);

        // 保存数据
        userService.save(user);
        return ResultVoUtil.success("保存成功", new URL("/user_info"));
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/edit_pwd")
    @RequiresPermissions("/index")
    public String toEditPwd(){
        return "/system/main/edit_pwd";
    }

    /**
     * 保存修改密码
     */
    @PostMapping("/edit_pwd")
    @RequiresPermissions("/index")
    @ResponseBody
    public ResultVo editPwd(String original, String password, String confirm){
        // 判断原来密码是否有误
        User oldPwdUser = ShiroUtil.getSubject();
        String oldPwd = ShiroUtil.encrypt(original, oldPwdUser.getSalt());
        if (original.isEmpty() || "".equals(original.trim()) || !oldPwd.equals(oldPwdUser.getPassword())) {
            throw new ResultException(ResultEnum.USER_OLD_PWD_ERROR);
        }

        // 判断密码是否为空
        if (password.isEmpty() || "".equals(password.trim())) {
            throw new ResultException(ResultEnum.USER_PWD_NULL);
        }

        // 判断两次密码是否一致
        if (!password.equals(confirm)) {
            throw new ResultException(ResultEnum.USER_INEQUALITY);
        }

        // 修改密码，对密码进行加密
        User newPwdUser = userService.getId(oldPwdUser.getId());
        String salt = ShiroUtil.getRandomSalt();
        String encrypt = ShiroUtil.encrypt(password, salt);
        newPwdUser.setPassword(encrypt);
        newPwdUser.setSalt(salt);

        // 保存数据
        userService.save(newPwdUser);
        return ResultVoUtil.success("修改成功");
    }

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String toLogin(){
        return "/login";
    }

    /**
     * 实现登录
     */
    @PostMapping("/login")
    @ResponseBody
    @ActionLog(key = UserAction.USER_LOGIN, action = UserAction.class)
    public ResultVo login(String username, String password, String rememberMe){
        // 判断账号密码是否为空
        if(username.isEmpty() || "".equals(username.trim()) ||
                password.isEmpty() || "".equals(password.trim())){
            throw new ResultException(ResultEnum.USER_NAME_PWD_NULL);
        }

        // 1.获取Subject主体对象
        Subject subject = SecurityUtils.getSubject();

        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken( username, password );

        // 3.执行登录，进入自定义Realm类中
        try {
            // 判断是否自动登录
            if (rememberMe != null){
                token.setRememberMe(true);
            }else{
                token.setRememberMe(false);
            }
            subject.login(token);
            // 判断是否拥有后台角色
            User user = ShiroUtil.getSubject();
            if(user.getIsRole().equals(UserIsRoleEnum.YES.getCode())){
                // 设置超时时间
                SecurityUtils.getSubject().getSession().setTimeout(5000L);
                return ResultVoUtil.success("登录成功",new URL("/"));
            }else {
                return ResultVoUtil.error("您不是后台管理员！");
            }
        } catch (AuthenticationException e){
            return ResultVoUtil.error("用户名或密码错误");
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    /**
     * 权限不足页面
     */
    @GetMapping("/noAuth")
    @ResponseBody
    public String noAuth(){
        return "您的权限不足，无法访问本页面";
    }

}
