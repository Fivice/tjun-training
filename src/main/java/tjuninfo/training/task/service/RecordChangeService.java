package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Campus;
import tjuninfo.training.task.entity.RecordChange;

import java.util.List;

/**
 * 校区业务层接口
 */
public interface RecordChangeService extends IBaseService<RecordChange>{
    //根据班级ID查找所有录改表中的信息
    List<RecordChange> list(Long classId);


}
