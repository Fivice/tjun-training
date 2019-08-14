package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.RecordChange;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/29 10:30
 * @Description:就餐安排管理数据层接口
 */
public interface RecordChangeDao extends IBaseDao<RecordChange> {

    //根据班级Id查询所有的录改表信息
    List<RecordChange> findAll(Long classId);

}
