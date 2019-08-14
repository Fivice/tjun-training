package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.util.Pages;

import java.util.List;
/**
 * 教师信息业务层接口
 * @author
 * @date 2018年5月17日1
 */
public interface TeacherInfoService extends IBaseService<TeacherInfo>{
    //查询所有列表
    public Pages findTeacherInfoList(int pageSize, int pageIndex, String subject, String tiName, String tiDepartment);

    //根据id删除数据
    public  void deleteById(Integer id);

    //添加数据
    public  void saveTeacherInfo(TeacherInfo teacherInfo);

    //根据id查询数据
    public  TeacherInfo selectById(Integer id);

    List<TeacherInfo> list();
    /**
     * 获取当天有就餐安排的教师
     * @return
     */
    List<TeacherInfo> getTodayDining();

    //根据身份证和教师id查询教师信息
    public TeacherInfo getBysiIDNumber(String siIDNumber, String tiId);
    /**
     * 查询当天有就餐安排的教师，并判断idCard是否在其中
     * @param time
     * @param idCard
     * @return
     */
    boolean ifExist(String time,String idCard,Integer dinner);
    /**
     * 查询是否是教师
     * @param idCard
     * @return
     */
    boolean ifExist2(String idCard);
}

