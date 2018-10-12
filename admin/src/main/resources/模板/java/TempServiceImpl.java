package #{path}.service.impl;

import #{path}.domain.#{obj};
import #{path}.repository.#{obj}Repository;
import #{path}.service.#{obj}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class #{obj}ServiceImpl implements #{obj}Service {

    @Autowired
    private #{obj}Repository #{var}Repository;

    /**
     * 根据#{title}ID查询#{title}数据
     * @param id #{title}ID
     */
    @Override
    @Transactional
    public #{obj} getId(Long id) {
        return #{var}Repository.findByIdAndStatus(id, StatusEnum.OK.getCode());
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @param pageIndex 页码
     * @param pageSize 获取列表长度
     * @return 返回分页数据
     */
    @Override
    public Page<#{obj}> getPageList(Example<#{obj}> example, Integer pageIndex, Integer pageSize) {
        // 创建分页对象
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        PageRequest page = PageRequest.of(pageIndex-1, pageSize, sort);
        Page<#{obj}> list = #{var}Repository.findAll(example, page);
        return list;
    }

    /**
     * 保存#{title}
     * @param #{var} #{title}实体类
     */
    @Override
    public #{obj} save(#{obj} #{var}){
        return #{var}Repository.save(#{var});
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Integer updateStatus(StatusEnum statusEnum, List<Long> idList){
        return #{var}Repository.updateStatus(statusEnum.getCode(),idList);
    }
}
