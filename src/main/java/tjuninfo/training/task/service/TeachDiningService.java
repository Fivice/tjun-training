package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.TeachDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.TeachDiningVO;

import java.text.ParseException;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/17 11:53
 * @Description:
 */
public interface TeachDiningService extends IBaseService<TeachDining> {

    /**根据就餐地点、月份、安排人查*/
    List<TeachDining> findTeachDiningList(String schoolName,String number,String month,String arranger);

    List<TeachDining> findTeachDiningList(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger);

    List<TeachDining> findTeachDiningList1(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger,Integer classId);

    /**根据流水号查*/
    List<TeachDining> findTeachDiningList(String number);

    List<TeachDining> findDiningList(String number);

    /**根据班级ID去查教师就餐安排*/
    List<TeachDining> findTeachDinList(Integer id);

    /**更新保存*/
    void saveOrUpdate(TeachDining teachDining);

    void updateByNumber(String teacherName,Integer classId,String area,String number,String time);

    /**根据id删除*/
    void deleteByNid(String number,String time);

    void deleteByNumber(String number,String time);

    /**分页*/
    List<TeachDining> findDiningList(BTView<TeachDining> btView, String number,String time);

    /**根据教师流水号及流水号绑定时间查*/
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
    //根据当前系统时间和查询出来的班级id去教师证就餐安排表中查询所有班级的id和就餐次数(并分组)
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
    //根据班级编号与就餐时间查询教师就餐
     List<TeachDining> TeachDiningPrepList(String classNum, String time,Integer b,Integer l,Integer d);

    //根据班级id查询班级人数
    public long getCountByClassIdFromTeachDining(long classId,String startStopTime);

    //分页处理
    public Pages teachDiningList1(int pageSize, int pageIndex, String schoolName, String number, String month,Integer classId);

    CmsResult init(String id , String number, String time) throws ParseException;
}
