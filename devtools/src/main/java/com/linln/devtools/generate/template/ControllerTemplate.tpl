## 控制器模板

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

/**
 * @author 小懒虫
 * @date 2019/4/4
 */
@Controller
@RequestMapping("#{requestMapping}")
public class #{entity}Controller {

    @Autowired
    private #{entity}Service #{name}Service;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("#{permissions}:index")
    public String index(Model model, #{entity} #{name}){

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<#{entity}> example = Example.of(#{name}, matcher);
        Page<#{entity}> list = #{name}Service.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "#{requestMapping}/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("#{permissions}:add")
    public String toAdd(){
        return "#{requestMapping}/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("#{permissions}:edit")
    public String toEdit(@PathVariable("id") #{entity} #{name}, Model model){
        model.addAttribute("#{name}", #{name});
        return "#{requestMapping}/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"#{permissions}:add", "#{permissions}:edit"})
    @ResponseBody
    public ResultVo save(@Validated #{entity}Valid valid, #{entity} #{name}){
        // 复制保留无需修改的数据
        if(#{name}.getId() != null){
            #{entity} be#{entity} = #{name}Service.getById(#{name}.getId());
            EntityBeanUtil.copyProperties(be#{entity}, #{name});
        }

        // 保存数据
        #{name}Service.save(#{name});
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("#{permissions}:detail")
    public String toDetail(@PathVariable("id") #{entity} #{name}, Model model){
        model.addAttribute("#{name}",#{name});
        return "#{requestMapping}/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("#{permissions}:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids){
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (#{name}Service.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}
