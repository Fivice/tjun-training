package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ICampusDao;
import tjuninfo.training.task.dao.IClassroomDao;
import tjuninfo.training.task.entity.Campus;
import tjuninfo.training.task.entity.Classroom;
import tjuninfo.training.task.service.ICampusService;
import tjuninfo.training.task.service.IClassroomService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 校区业务层接口实现层
 */
@Service
public class CampusServiceImpl extends BaseServiceImpl<Campus> implements ICampusService {
    private ICampusDao campusDao;
    @Resource
    public void setSysAuthorityDao(ICampusDao campusDao){
        this.campusDao = campusDao;
        this.dao = campusDao;
    }

    @Override
    public List<Campus> list() {
        return campusDao.getAll();
    }

    @Override
    public List<Campus> findBySchoolName(Integer schoolName) {
        return campusDao.findBySchoolName(schoolName);
    }

    @Override
    public List<Campus> findBySchoolName1(String school) {
        return campusDao.findBySchoolName1(school);
    }

    @Override
    public List<Campus> getByIdAndSchoolName(String id, String schoolName) {
        return campusDao.getByIdAndSchoolName(id,schoolName);
    }
}
