package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.ClassDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/29 10:30
 * @Description:就餐安排管理数据层接口
 */
public interface ClassDiningDao extends IBaseDao<ClassDining> {

    //查询所有的班级就餐信息
    List<ClassDining> findAll();

    //根据班级编号查询相应的班级就餐信息
    List<ClassDining> findClassDiningList(String classNum);
    //根据班级编号日期查询相应的班级就餐信息
    List<ClassDining> findClassDiningList(String classNum,String time);

    //根据id号删除信息
    void deleteById(int id);

    void deleteByClassNumber(String classNumber);

    Pages findClassDiningList(int pageSize, int pageNumber, String s);

    //根据班级id  查找最后就餐安排日期
    ClassDining findMax(String classId);

    /**
     * 查询当前时间有没有就餐安排
     * @param classId
     * @param day
     * @return
     */
    boolean ifDing(String classId,String day, Integer dinner);
    //根据当前系统时间和查询出来的班级id去班级就餐安排表中查询所有班级的id和就餐次数
    List<ClassDining> byTime(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList);
    //根据班级ID到班级就餐安排表中查询数据
    List<ClassDining> byClassID(Long id);

    /**
     * 获取指定班级在指定食堂指定时间的就餐安排
     * @param diningPlace
     * @param classId
     * @param startTime
     * @param endTime
     * @return
     */
    ClassDiningDataForDiningStatisticsDTO getClassDining(String diningPlace,String classId,String startTime,String endTime);

    /**
     *
     * @param diningPlace
     * @param classId
     * @return
     */
    ClassDiningDataForDiningStatisticsDTO getClassFirstDayDining(String diningPlace,String classId);

    /**
     * 根据班级id分页查询就餐安排
     * @param pageSize
     * @param pageNumber
     * @param classId
     * @return
     */
    Pages findClassDiningListByClassId(int pageSize, int pageNumber, String classId);
}