package com.linln.devtools.code;

import com.linln.admin.core.constant.AdminConst;
import com.linln.admin.core.enums.MenuTypeEnum;
import com.linln.admin.system.controller.MenuController;
import com.linln.admin.system.controller.RoleController;
import com.linln.admin.system.domain.Menu;
import com.linln.admin.system.domain.Role;
import com.linln.admin.system.service.MenuService;
import com.linln.admin.system.service.RoleService;
import com.linln.admin.system.validator.MenuForm;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.utils.SpringContextUtil;
import com.linln.core.utils.ToolUtil;
import com.linln.core.vo.ResultVo;
import com.linln.devtools.code.domain.Basic;
import com.linln.devtools.code.domain.Generate;
import com.linln.devtools.code.domain.Template;
import com.linln.devtools.code.enums.FieldQuery;
import com.linln.devtools.code.enums.FieldType;
import com.linln.devtools.code.enums.FieldVerify;
import com.linln.devtools.code.template.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/code")
public class GenerateController {

    @GetMapping("index")
    public String index(Model model){
        model.addAttribute("basic", DefaultValue.getBasic());
        model.addAttribute("fieldList", DefaultValue.fieldList());
        model.addAttribute("fieldType", ToolUtil.enumToMap(FieldType.class));
        model.addAttribute("fieldQuery", ToolUtil.enumToMap(FieldQuery.class));
        model.addAttribute("fieldVerify", ToolUtil.enumToMap(FieldVerify.class));
        return "/devtools/code/index";
    }

    @PostMapping("save")
    @ResponseBody
    public ResultVo save(@RequestBody Generate generate){
        Map<String, String> fieldMap = new HashMap<>();
        if(generate.getTemplate().isEntity()){
            fieldMap.put("实体类", EntityTemplate.generate(generate));
        }
        if(generate.getTemplate().isValidator()){
            fieldMap.put("验证类", ValidatorTemplate.generate(generate));
        }
        if(generate.getTemplate().isRepository()){
            fieldMap.put("数据访问层", RepositoryTemplate.generate(generate));
        }
        if(generate.getTemplate().isService()){
            fieldMap.put("服务层", ServiceTemplate.generate(generate));
            fieldMap.put("服务实现层", ServiceImplTemplate.generate(generate));
        }
        if(generate.getTemplate().isController()){
            fieldMap.put("控制器", ControllerTemplate.generate(generate));
            menuRule(generate);
        }
        if(generate.getTemplate().isIndex()){
            fieldMap.put("列表页面", IndexHtmlTemplate.generate(generate));
        }
        if(generate.getTemplate().isAdd()){
            fieldMap.put("添加页面", AddHtmlTemplate.generate(generate));
        }
        if(generate.getTemplate().isDetail()){
            fieldMap.put("详细页面", DetailHtmlTemplate.generate(generate));
        }
        return ResultVoUtil.success(fieldMap);
    }

    /**
     * 自动生成菜单和角色权限
     */
    private void menuRule(Generate generate){
        Basic basic = generate.getBasic();
        Template template = generate.getTemplate();
        MenuService menuService = SpringContextUtil.getBean(MenuService.class);
        RoleService roleService = SpringContextUtil.getBean(RoleService.class);
        MenuController menuController = SpringContextUtil.getBean(MenuController.class);
        RoleController roleController = SpringContextUtil.getBean(RoleController.class);
        String entity = ToolUtil.lowerFirst(basic.getTableEntity());
        Role adminRole = roleService.getId(AdminConst.ADMIN_ROLE_ID);

        // 生成列表菜单
        if(template.isIndex()){
            String url = "/" + entity + "/index";
            MenuForm menu = new MenuForm();
            if(menuService.getUrl(url) == null){
                // 创建菜单对象
                menu.setTitle(basic.getGenTitle());
                menu.setUrl(url);
                menu.setPid(basic.getGenPMenu());

                // 添加菜单类型
                if(menu.getPid() != 0){
                    menu.setType(MenuTypeEnum.SUB_LEVEL.getCode());
                }else{
                    menu.setType(MenuTypeEnum.TOP_LEVEL.getCode());
                }
                menuController.save(menu);

                // 授权给管理员组角色
                adminRole.getMenus().add((Menu) menu.getEntity());

                // 子菜单通用字段
                menu.setPid(((Menu)menu.getEntity()).getId());
                menu.setType(MenuTypeEnum.NOT_MENU.getCode());

                // 生成添加/编辑菜单
                if(template.isAdd()){
                    url = "/" + entity + "/add";
                    if(menuService.getUrl(url) == null){
                        menu.setTitle("添加");
                        menu.setUrl(url);
                        menu.setSort(null);
                        menuController.save(menu);

                        // 授权给管理员组角色
                        adminRole.getMenus().add((Menu) menu.getEntity());
                    }
                    url = "/" + entity + "/edit";
                    if(menuService.getUrl(url) == null){
                        menu.setTitle("编辑");
                        menu.setUrl(url);
                        menu.setSort(null);
                        menuController.save(menu);

                        // 授权给管理员组角色
                        adminRole.getMenus().add((Menu) menu.getEntity());
                    }
                }

                // 生成详细菜单
                if(template.isDetail()){
                    url = "/" + entity + "/detail";
                    if(menuService.getUrl(url) == null){
                        menu.setTitle("详细");
                        menu.setUrl(url);
                        menu.setSort(null);
                        menuController.save(menu);

                        // 授权给管理员组角色
                        adminRole.getMenus().add((Menu) menu.getEntity());
                    }
                }

                // 生成改变状态菜单
                url = "/" + entity + "/status";
                if(menuService.getUrl(url) == null){
                    menu.setTitle("改变状态");
                    menu.setUrl(url);
                    menu.setSort(null);
                    menuController.save(menu);

                    // 授权给管理员组角色
                    adminRole.getMenus().add((Menu) menu.getEntity());
                }
            }

            // 保存管理员组角色
            List<Long> longList = new ArrayList<>();
            adminRole.getMenus().forEach(arMenu -> longList.add(arMenu.getId()));
            roleController.auth(AdminConst.ADMIN_ROLE_ID, longList);
        }
    }
}
