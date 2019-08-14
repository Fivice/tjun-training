package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.TeachDiningRecordForDiningStatisticsDTO;
import tjuninfo.training.task.entity.TeachDiningFaceRecord;
import tjuninfo.training.task.entity.TeachDiningRecord;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.StuDiningRecordVO;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;
import tjuninfo.training.task.vo.TeachDiningRecordVO;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/12 11:21
 * @Description:
 */
public interface TeachDinRecordDao extends IBaseDao<TeachDiningRecord>{

    /**查看学员就餐统计**/
    List<TeachDiningRecordVO> findSum(String area, Integer classRoom,String className,Integer userId,Integer roleId);

    List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> bt, String area, Integer classRoom, String className,String month);

    List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> bt, String area, Integer classRoom, String className, Integer userId,String month);

    /**根据学员ID编号查看学员就餐详情**/
    List<TeachDiningRecord> findAll(String num);

    List<TeachDiningRecord> findList(BTView<TeachDiningRecord> bt, String num,Integer classId);

    //根据班级ID查找教师实际就餐次数
    TeachDiningRecordVO sumByClassId(Long classId);

    //根据班级Id查找对应的流水号教师的就餐次数
    List<TeachDiningRecord> findAllByClassId(Long classId);

    //根据班级Id查找对应的人脸识别教师的就餐次数
    List<TeachDiningFaceRecord> faceByClassId(Long id);

    /**
     * 查询当前日期的教师就餐情况
     * @param time 绑定时间
     * @param day 刷卡时间
     * @param number 流水号
     * @param teacherName 教师姓名
     * @return
     */
    TeachDiningRecord findByTime(String time,String day,String number,String teacherName);

    List<TeachDiningRecord> findCount(String classId,String type);
    List<TeachDiningFaceRecord> findCount2(String classId, String type);

    TeachDiningRecordForDiningStatisticsDTO getTeachDiningRecordStatistics(String diningPlace,String classId,String startTime,String endTime);

    TeachDiningRecordForDiningStatisticsDTO getTeachFirstDayDiningRecordStatistics(String diningPlace,String classId, String startTime, String endTime);

}
