package #{path}.service;

import #{path}.domain.#{obj};
import org.springframework.transaction.annotation.Transactional;

public interface #{obj}Service {

    Page<#{obj}> getPageList(Example<#{obj}> example, Integer pageIndex, Integer pageSize);

    #{obj} getId(Long id);

    #{obj} save(#{obj} #{var});

    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
