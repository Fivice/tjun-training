package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.StuDiningRecordStatisticsDTO;
import tjuninfo.training.task.entity.StuDiningRecord;
import tjuninfo.training.task.vo.StuDiningRecordVO;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:41
 * @Description:就餐统计数据层接口
 */
public interface StuDinRecordDao extends IBaseDao<StuDiningRecord> {

    /**根据学员ID编号查看学员就餐详情**/
    List<StuDiningRecord> findAll(Integer student);

    /**查看学员就餐统计**/
    List<StuDiningRecordVO> findSum(String area,Integer classRoom,String className,Integer userId,Integer roleId,String month);

    List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> bt,String area, Integer classRoom, String className,String studentName,String month);

    List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> bt,String area, Integer classRoom, String className,String studentName, Integer userId,String month);

    List<StuDiningRecord> findList(BTView<StuDiningRecord> bt, int student,Integer classId);

    /**根据日期查询 某个学生就餐情况**/
    StuDiningRecord findByTime (String time,Integer student, Integer classRoom);
    List<StuDiningRecord> findCount(String classId,String type);

    StuDiningRecordStatisticsDTO getStuDiningRecordStatistics(String diningPlace,String classId,String startTime,String endTime);

    StuDiningRecordStatisticsDTO getStuDiningFirstDayRecordStatistics(String diningPlace,String classId);
}
