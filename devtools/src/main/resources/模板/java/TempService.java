package #{path}.service;

import #{path}.domain.#{obj};
import com.linln.admin.core.enums.StatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface #{obj}Service {

    Page<#{obj}> getPageList(Example<#{obj}> example, Integer pageIndex, Integer pageSize);

    #{obj} getId(Long id);

    #{obj} save(#{obj} #{var});

    @Transactional
    Integer updateStatus(StatusEnum statusEnum, List<Long> idList);
}
