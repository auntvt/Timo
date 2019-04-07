##Service层模板

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2019/4/4
 */
public interface #{entity}Service {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<#{entity}> getPageList(Example<#{entity}> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    #{entity} getById(Long id);

    /**
     * 保存数据
     * @param #{name} 实体对象
     */
    #{entity} save(#{entity} #{name});

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);
}
