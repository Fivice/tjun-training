package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Campus;
import tjuninfo.training.task.entity.Classroom;

import java.util.List;

/**
 * 校区业务层接口
 */
public interface ICampusService extends IBaseService<Campus>{
    List<Campus> list();

    //根据名称查询校区
    List<Campus> findBySchoolName(Integer schoolName);
    //根据名称查询校区
    List<Campus> findBySchoolName1(String school);
    //根据id和名称查询校区信息
    List<Campus> getByIdAndSchoolName(String id,String schoolName);
}
