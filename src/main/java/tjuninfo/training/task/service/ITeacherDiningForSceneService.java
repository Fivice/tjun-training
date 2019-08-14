package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.*;
import tjuninfo.training.task.entity.TeacherDiningForScene;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.ClassInfoForSceneVO;
import tjuninfo.training.task.vo.TeacherInfoForSceneVO;

import java.math.BigInteger;
import java.util.List;

public interface ITeacherDiningForSceneService extends IBaseService<TeacherDiningForScene> {
    /**
     * 获取教师信息和教师就餐绑定的正在开班的班级id
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public Page<TeacherInfoForSceneVO> getTeacherInfoForScene(int pageSize, int pageIndex,String teacherName);

    /**
     * 获取班级信息和绑定了该班级进行就餐的老师id
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public Page<ClassInfoForSceneVO> getClassInfoForScene(int pageSize, int pageIndex,String className);

    /**
     * 通过教师id和班级id查询教师的就餐安排信息表
     * @param pageSize
     * @param pageIndex
     * @param teacherId
     * @param classId
     * @return
     */
    public Page<TeacherDiningForScene> getTeacherDiningForScene(int pageSize, int pageIndex, int teacherId, long classId);

    /**
     * 初始化绑定了开班班级的教师就餐安排
     * @param teacherId
     * @param classId
     */
    public void teacherDiningInit(int teacherId,long classId,int userId,String startTime,String stopTime);

    /**
     * 通过就餐安排注册id和就餐安排时间唯一确定一条记录
     * @param teacherDiningRegId
     * @param diningDate
     * @return
     */
    public TeacherDiningForScene getTeacherDining(BigInteger teacherDiningRegId, String diningDate);
    public List<TeacherDiningForScene> getTeacherDining(BigInteger teacherDiningRegId);
    /**
     * 根据教师就餐绑定注册id删除安排表中此Id下的记录
     * @param teacherDiningRegId
     */
    public void deleteTeacherDiningForScene(long teacherDiningRegId);

    /**
     *
     * @param diningPlace
     * @param classId
     * @param startTime
     * @param endTime
     * @return
     */
    public TeachSenseDiningDataForDiningStatisticsDTO getTeachDining(String diningPlace, String classId, String startTime, String endTime);

    /**
     *
     * @param diningPlace
     * @param classId
     * @return
     */
    public TeachSenseDiningDataForDiningStatisticsDTO getTeachFirstDayDining(String diningPlace, String classId, String startTime, String endTime);

    /**
     *
     * @param diningPlace
     * @param classId
     * @param startTime
     * @param endTime
     * @return
     */
    public TeachDiningRecordForDiningStatisticsDTO getTeachDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime);

    /**
     *
     * @param diningPlace
     * @param classId
     * @return
     */
    TeachDiningRecordForDiningStatisticsDTO getTeachFirstDayDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime);

}
