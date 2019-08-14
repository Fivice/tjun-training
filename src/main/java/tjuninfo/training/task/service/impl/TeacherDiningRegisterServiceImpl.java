package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ITeacherDiningRegisterDao;
import tjuninfo.training.task.entity.TeacherDiningRegister;
import tjuninfo.training.task.service.ITeacherDiningRegisterService;

@Service
public class TeacherDiningRegisterServiceImpl extends BaseServiceImpl<TeacherDiningRegister> implements ITeacherDiningRegisterService {

    private ITeacherDiningRegisterDao iTeacherDiningRegisterDao;

    @Autowired
    public TeacherDiningRegisterServiceImpl(ITeacherDiningRegisterDao iTeacherDiningRegisterDao) {
        this.iTeacherDiningRegisterDao = iTeacherDiningRegisterDao;
        this.dao = iTeacherDiningRegisterDao;
    }

    @Override
    public TeacherDiningRegister getTeacherBindClassInfo(int teacherId) {
        return iTeacherDiningRegisterDao.getTeacherBindClassInfo(teacherId);
    }

    @Override
    public TeacherDiningRegister getTeacherDiningRegister(int teacherId, long classId) {
        return iTeacherDiningRegisterDao.getTeacherDiningRegister(teacherId,classId);
    }
}
