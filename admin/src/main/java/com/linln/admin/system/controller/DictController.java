package com.linln.admin.system.controller;

import com.linln.admin.core.enums.ResultEnum;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.exception.ResultException;
import com.linln.admin.core.utils.TimoExample;
import com.linln.admin.system.domain.Dict;
import com.linln.admin.system.service.DictService;
import com.linln.admin.system.validator.DictForm;
import com.linln.core.utils.FormBeanUtils;
import com.linln.core.utils.ResultVoUtil;
import com.linln.core.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 列表页面
     * @param pageIndex 页码
     * @param pageSize 获取数据长度
     */
    @GetMapping("/index")
    @RequiresPermissions("/dict/index")
    public String index(Model model, Dict dict,
                        @RequestParam(value="page",defaultValue="1") int pageIndex,
                        @RequestParam(value="size",defaultValue="10") int pageSize){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching().
                withMatcher("title", match -> match.contains());

        // 获取字典列表
        Example<Dict> example = TimoExample.of(dict, matcher);
        Page<Dict> list = dictService.getPageList(example, pageIndex, pageSize);

        // 封装数据
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);
        return "/system/dict/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("/dict/add")
    public String toAdd(){
        return "/system/dict/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("/dict/edit")
    public String toEdit(@PathVariable("id") Long id, Model model){
        Dict dict = dictService.getId(id);
        model.addAttribute("dict",dict);
        return "/system/dict/add";
    }

    /**
     * 保存添加/修改的数据
     * @param dictForm 表单验证对象
     */
    @PostMapping({"/add","/edit"})
    @RequiresPermissions({"/dict/add","/dict/edit"})
    @ResponseBody
    public ResultVo save(@Validated DictForm dictForm){
        // 清除字典值两边空格
        dictForm.setValue(dictForm.getValue().trim());

        // 将验证的数据复制给实体类
        Dict dict = new Dict();
        if(dictForm.getId() != null){
            dict = dictService.getId(dictForm.getId());
        }
        FormBeanUtils.copyProperties(dictForm, dict);

        // 保存数据
        Dict save = dictService.save(dict);
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
    @RequiresPermissions("/dict/detail")
    public String toDetail(@PathVariable("id") Long id, Model model){
        Dict dict = dictService.getId(id);
        model.addAttribute("dict",dict);
        return "/system/dict/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("/dict/status")
    @ResponseBody
    public ResultVo delete(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> idList){
        try {
            // 获取状态StatusEnum对象
            StatusEnum statusEnum = StatusEnum.valueOf(param.toUpperCase());
            // 更新状态
            Integer count = dictService.updateStatus(statusEnum,idList);
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
