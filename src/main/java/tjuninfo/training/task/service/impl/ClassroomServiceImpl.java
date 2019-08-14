package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IClassroomDao;
import tjuninfo.training.task.entity.Classroom;
import tjuninfo.training.task.service.IClassroomService;
import tjuninfo.training.task.util.Pages;

import javax.annotation.Resource;
import java.util.List;

/**
 * 区域及教室业务层接口实现层
 */
@Service
public class ClassroomServiceImpl extends BaseServiceImpl<Classroom> implements IClassroomService {
    private IClassroomDao classroomDao;
    @Resource
    public void setSysAuthorityDao(IClassroomDao classroomDao){
        this.classroomDao = classroomDao;
        this.dao = classroomDao;
    }

    @Override
    public List<Classroom> list() {
        return classroomDao.getAll();

    }

    @Override
    public List<Classroom> getBySjId(Integer id) {
        return classroomDao.getBySjId(id);
    }

    @Override
    public List<Classroom> findName() {
        return classroomDao.findName();
    }

    @Override
    public List<Classroom> list(String name, String classType, String className) {
        return classroomDao.list(name,classType,className);
    }

    @Override
    public List<Classroom> getClassroomBySchool(Integer newSname) {
        return classroomDao.getClassroomBySchool(newSname);
    }

    @Override
    public List<Classroom> getClassroomByClassId(String classId) {
        return classroomDao.getClassroomByClassId(classId);
    }

    @Override
    public Pages list(int pageSize, int pageNumber, String schoolName, String classType, String className) {
        return classroomDao.list(pageSize,pageNumber,schoolName,classType,className);
    }

    @Override
    public List<Classroom> findByClassName(String className) {
        return classroomDao.findByClassName(className);
    }

    @Override
    public Classroom getByProp(String schoolId, String className, String classroomId) {
        return classroomDao.getByProp(schoolId,className,classroomId);
    }
}
