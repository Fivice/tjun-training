package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.StuDiningRecordStatisticsDTO;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.vo.StuDiningRecordVO;

import java.text.ParseException;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:37
 * @Description:就餐统计业务层接口
 */
public interface StuDinRecordService extends IBaseService<StuDiningRecord> {

  /**根据学员ID编号查看学员就餐详情**/
  List<StuDiningRecord> findList(Integer student);

  /**查看学员就餐统计**/
  List<StuDiningRecordVO> findSum(String area,Integer classRoom,String className,Integer userId,Integer roleId,String month);

  /**分页**/
  List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> btView,String area, Integer classRoom, String className,String studentName,String month);

  List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> btView,String area, Integer classRoom, String className,String studentName,Integer userId,String month);

  /**分页**/
  List<StuDiningRecord> findList(BTView<StuDiningRecord> btView, int student,Integer classId);

  /**根据日期查询 某个学生就餐情况**/
  StuDiningRecord findByTime (String time,Integer student, Integer classRoom);

  List<StuDiningRecord> findCount(String classId,String type);

  /**
   * 存储人脸就餐数据
   * @param time
   * @param s
   * @param sdr
   * @param c
   * @return
   * @throws ParseException
   */
  CmsResult saveData(String time, Student s,StuDiningRecord sdr,ClassInfo c) throws ParseException;

  /**
   * 查询某一个就餐地点班级实际就餐人次
   * @param diningPlace
   * @param classId
   * @param startTime
   * @param endTime
   * @return
   */
  StuDiningRecordStatisticsDTO getStuDiningRecordStatistics(String diningPlace,String classId,String startTime,String endTime);

  /**
   * 查询一个班级在就餐地点首日就餐情况
   * @param diningPlace
   * @param classId
   * @return
   */
  public StuDiningRecordStatisticsDTO getStuDiningFirstDayRecordStatistics(String diningPlace, String classId);
}
