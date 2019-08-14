package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.TeachDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.TeachDiningVO;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/17 11:57
 * @Description:
 */
public interface TeachDiningDao extends IBaseDao<TeachDining> {

    List<TeachDining> findTeachDiningList(String schoolName,String number,String month,String arranger);

    List<TeachDining> findTeachDiningList(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger);

    List<TeachDining> findTeachDiningList1(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger,Integer classId);

    List<TeachDining> findTeachDiningList(String number);

    List<TeachDining> findDiningList(String number);

    List<TeachDining> findTeachDinList(Integer id);

    void deleteByNid(String number,String time);

    void updateByNumber(String teacherName,Integer classId,String area,String number,String time);

    void deleteByNumber(String number,String time);

    List<TeachDining> findDiningList(BTView<TeachDining> bt, String number,String time);

    List<TeachDining> findTeachDiningList(String number, String time);

    /**
     * 查询当前时间有没有就餐安排
     * @param number
     * @param teacherName
     * @param time
     * @param day
     * @param dinner
     * @return
     */
    boolean ifDing(String number,String teacherName,String time,String day, Integer dinner);
    //根据当前系统时间和查询出来的班级id去教师证就餐安排表中查询所有班级的id和就餐次数
    List<TeachDining> byTime(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList);
    //根据当前系统时间和查询出来的班级id去教师证就餐安排表中查询所有班级的id和就餐次数（并分组）
    List<TeachDiningVO> byTime2(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList);

    /**
     *
     * @param diningPlace
     * @param classId
     * @param startTime
     * @param endTime
     * @return
     */
    TeachDiningDataForDiningStatisticsDTO getTeachDining(String diningPlace, String classId, String startTime, String endTime);

    TeachDiningDataForDiningStatisticsDTO getTeachFirstDayDining(String diningPlace, String classId, String startTime, String endTime);

    //根据班级编号日期查询相应的教师就餐信息
    List<TeachDining> TeachDiningPrepList(String classNum,String time,Integer b,Integer l,Integer d);

    /**
     *
     * @param classId 班级Id
     * @return 如果没有这个教师则返回-1
     */
    public long getCountByClassIdFromTeachDining(long classId,String startStopTime);

    Pages teachDiningList1(int pageSize, int pageIndex, String schoolName, String number, String month,Integer classId);
}
