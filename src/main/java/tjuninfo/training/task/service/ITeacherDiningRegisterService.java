package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.TeacherDiningRegister;

public interface ITeacherDiningRegisterService extends IBaseService<TeacherDiningRegister> {

    /**
     * 通过教师id查询该教师就餐绑定的班级
     * @param teacherId
     * @return
     */
    public TeacherDiningRegister getTeacherBindClassInfo(int teacherId);
    /**
     * 通过班级id和教室Id查询教师注册信息
     * @param teacherId
     * @param classId
     * @return
     */
    public TeacherDiningRegister getTeacherDiningRegister(int teacherId,long classId);
}
