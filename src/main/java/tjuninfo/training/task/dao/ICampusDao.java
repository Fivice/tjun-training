package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Campus;
import tjuninfo.training.task.entity.Classroom;

import java.util.List;

/**
 * 校区表数据层接口
 */
public interface ICampusDao extends IBaseDao<Campus>{
    //根据名称查询校区
    List<Campus> findBySchoolName(Integer schoolName);
    //根据名称查询校区
    List<Campus> findBySchoolName1(String school);
    //根据id和名称查询校区信息
    List<Campus> getByIdAndSchoolName(String id,String schoolName);
}
