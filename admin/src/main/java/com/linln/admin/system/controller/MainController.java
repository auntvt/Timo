package com.linln.admin.system.controller;

import com.linln.admin.core.constant.AdminConst;
import com.linln.admin.core.enums.MenuTypeEnum;
import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.shiro.ShiroUtil;
import com.linln.admin.system.domain.Menu;
import com.linln.admin.system.domain.Upload;
import com.linln.admin.system.domain.User;
import com.linln.admin.system.service.MenuService;
import com.linln.admin.system.service.UserService;
import com.linln.admin.system.validator.UserForm;
import com.linln.core.enums.TimoResultEnum;
import com.linln.core.utils.FormBeanUtil;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.utils.SpringContextUtil;
import com.linln.core.vo.ResultVo;
import com.linln.core.wraps.URL;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
public class MainController{

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 后台主体内容
     */
    @GetMapping("/")
    @RequiresPermissions("/index")
    public String main(Model model){
        // 获取当前登录的用户
        User user = ShiroUtil.getSubject();

        // 菜单键值对(ID->菜单)
        Map<Long,Menu> keyMenu = new HashMap<>();

        // 管理员实时更新菜单
        if(user.getId().equals(AdminConst.ADMIN_ID)){
            Sort sort = new Sort(Sort.Direction.ASC, "sort");
            List<Menu> menus = menuService.getList(sort);
            menus.forEach(menu -> keyMenu.put(menu.getId(), menu));
        }else{
            // 其他用户需从相应的角色中获取菜单资源
            user.getRoles().forEach(role -> {
                role.getMenus().forEach(menu -> {
                    if(menu.getStatus().equals(StatusEnum.OK.getCode())){
                        keyMenu.put(menu.getId(), menu);
                    }
                });
            });
        }

        // 封装菜单树形数据
        Map<Long,Menu> treeMenu = new HashMap<>();
        keyMenu.forEach((id, menu) -> {
            if(!menu.getType().equals(MenuTypeEnum.NOT_MENU.getCode())){
                if(keyMenu.get(menu.getPid()) != null){
                    keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
                }else{
                    if(menu.getType().equals(MenuTypeEnum.TOP_LEVEL.getCode())){
                        treeMenu.put(Long.valueOf(menu.getSort()), menu);
                    }
                }
            }
        });

        model.addAttribute("user", user);
        model.addAttribute("treeMenu", treeMenu);
        return "/main";
    }

    /**
     * 主页
     */
    @GetMapping("/index")
    @RequiresPermissions("/index")
    public String index(Model model){

        return "/system/main/index";
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
    public ResultVo userPicture(@RequestParam("picture") MultipartFile picture, HttpServletResponse response){
        UploadController uploadController = SpringContextUtil.getBean(UploadController.class);
        ResultVo imageResult = uploadController.uploadPicture(picture);
        if(imageResult.getCode().equals(TimoResultEnum.SUCCESS.getCode())){
            User subject = ShiroUtil.getSubject();
            subject.setPicture(((Upload) imageResult.getData()).getPath());
            userService.save(subject);
            ShiroUtil.resetCookieRememberMe();
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
        String[] ignore = {"id", "password", "salt", "picture", "dept", "roles", "isRole"};
        FormBeanUtil.copyProperties(userForm, user, ignore);

        // 保存数据
        userService.save(user);
        ShiroUtil.resetCookieRememberMe();
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
}
