package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Classroom;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 区域及教室业务层接口
 */
public interface IClassroomService extends IBaseService<Classroom>{
    /**
     * 查找所有的区域及教室资源
     */
    List<Classroom> list();

    List<Classroom> getBySjId(Integer id);

    /**
     * 查找校区
     * @return
     */
    List<Classroom> findName();

    List<Classroom> list(String name, String classType, String className);

    List<Classroom> getClassroomBySchool(Integer newSname);

    //根据校区id查询教室
    List<Classroom> getClassroomByClassId(String classId);

    Pages list(int pageSize, int pageNumber, String schoolName, String classType, String className);
    //根据名称查询教室
    List<Classroom> findByClassName(String className);

    public Classroom getByProp(String schoolId, String className, String classroomId);

}
