package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.ClassDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.StuDiningDataStatisticsVO;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/29 10:28
 * @Description:就餐安排业务层接口
 */
public interface ClassDiningService extends IBaseService<ClassDining> {

    //查询所有的班级就餐信息
    List<ClassDining> findClassDiningList();

    //根据班级编号查询相应的班级就餐信息
    List<ClassDining> findClassDiningList(String classNum);
    public List<ClassDining> findClassDiningList(String classNum, String time);
    //根据id号删除信息
    void deleteByNid(int id);

    //保存
    void saveOrUpdate(ClassDining cd);

    void deleteByClassNumber(String classNumber);

    Pages findClassDiningList(int pageSize, int pageNumber, String s);

    //根据班级id  查找最后就餐安排日期
    ClassDining findMax(String classId);

    /**
     * 取安排的最后一餐的时间
     * @param c
     * @return
     */
    String timeMax(ClassDining c);
    //查询当前时间有没有就餐安排
    boolean ifDing(String classId,String day,Integer dinner);
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
    public ClassDiningDataForDiningStatisticsDTO getClassDining(String diningPlace, String classId,String startTime,String endTime);

    /**
     * 合计指定时间段，指定一个地点，一些班级的就餐统计数据
     * @param diningPlace
     * @param classIdArr
     * @param startTime
     * @param endTime
     * @return
     */
    public StuDiningDataStatisticsVO getStuDiningStatisticsData(String diningPlace, String[] classIdArr, String startTime, String endTime);

    /**
     * 合计指定时间段，指定地点或全部地点，一些班级的就餐统计数据
     * @param diningPlace
     * @param classIdArr
     * @param startTime
     * @param endTime
     * @return
     */
    public List<StuDiningDataStatisticsVO> getStuDiningStatisticsDataList(String diningPlace, String[] classIdArr, String startTime, String endTime);

    /**
     * 统计指定就餐地点该班级首日餐信息
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
