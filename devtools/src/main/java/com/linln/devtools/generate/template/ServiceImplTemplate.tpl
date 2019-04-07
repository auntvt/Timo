##Service层模板

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小懒虫
 * @date 2019/4/4
 */
@Service
public class #{entity}ServiceImpl implements #{entity}Service {

    @Autowired
    private #{entity}Repository #{name}Repository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public #{entity} getById(Long id) {
        return #{name}Repository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<#{entity}> getPageList(Example<#{entity}> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return #{name}Repository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param #{name} 实体对象
     */
    @Override
    public #{entity} save(#{entity} #{name}){
        return #{name}Repository.save(#{name});
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList){
        return #{name}Repository.updateStatus(statusEnum.getCode(), idList) > 0;
    }
}