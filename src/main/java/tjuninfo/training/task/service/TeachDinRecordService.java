package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.TeachDiningRecordForDiningStatisticsDTO;
import tjuninfo.training.task.entity.TeachDiningFaceRecord;
import tjuninfo.training.task.entity.TeachDiningRecord;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;
import tjuninfo.training.task.vo.TeachDiningRecordVO;
import java.util.List;


/**
 * @Auther: win7
 * @Date: 2018/10/12 11:20
 * @Description:就餐统计业务层接口
 */
public interface TeachDinRecordService extends IBaseService<TeachDiningRecord> {

    /**查看教师就餐统计**/
    List<TeachDiningRecordVO> findSum(String area, Integer classRoom,String className,Integer userId,Integer roleId);

    /**根据学员ID编号查看学员就餐详情**/
    List<TeachDiningRecord> findList(String num);

    //分页
    List<TeachDiningRecord> findList(BTView<TeachDiningRecord> bt, String num,Integer classId);

    //根据班级ID查找教师流水号实际就餐次数
    Integer sumByClassId(Long classId);

    //根据班级ID查找教师人脸识别实际就餐次数
    Integer faceByClassId(Long id);

    //超级管理员查看信息
    List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> btView, String area, Integer classRoom, String className,String month);

    //用户查看信息
    List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> btView, String area, Integer classRoom, String className, Integer userId,String month);
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

    /**
     *
     * @param diningPlace
     * @param classId
     * @param startTime
     * @param endTime
     * @return
     */
    TeachDiningRecordForDiningStatisticsDTO getTeachDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime);
    TeachDiningRecordForDiningStatisticsDTO getTeachFirstDayDiningRecordStatistics(String diningPlace,String classId, String startTime, String endTime);

}
