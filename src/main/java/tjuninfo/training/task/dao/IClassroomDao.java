package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Classroom;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 区域及教室表数据层接口
 */
public interface IClassroomDao extends IBaseDao<Classroom>{
    List<Classroom> findByType(Integer type);

    List<Classroom> getBySjId(Integer id);

    /**查找校区*/
    List<Classroom> findName();

    List<Classroom> list(String name, String classType, String className);

    List<Classroom> getClassroomBySchool(Integer newSname);

    //根据校区id查询教室
    List<Classroom> getClassroomByClassId(String classId);

    Pages list(int pageSize, int pageNumber, String schoolName, String classType, String className);
    //根据名称查询教室
    List<Classroom> findByClassName(String className);

    //根据班级校区id，教室名称，教室id，查询教室信息
    public Classroom getByProp(String schoolId,String className,String classroomId);
}
