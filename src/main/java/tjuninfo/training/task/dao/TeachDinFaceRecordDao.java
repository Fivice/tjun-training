package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.StuDiningRecord;
import tjuninfo.training.task.entity.TeachDiningFaceRecord;
import tjuninfo.training.task.entity.TeacherDiningRegister;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/12/7 14:37
 * @Description:
 */
public interface TeachDinFaceRecordDao extends IBaseDao<TeachDiningFaceRecord> {

    List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, String month);
    List<TeachDiningFaceRecordVO> findList(BTView<TeachDiningFaceRecordVO> btView, String teacherId,Integer classId);

    List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, Integer userId, String month);

    /**
     * 根据日期、教师id查询 就餐情况
     * @param time
     * @param teacher
     * @return
     */
    TeachDiningFaceRecord findByTime (String time, Integer teacher);

    /**
     * 根据日期、教师id查询 查找班级id
     * @param time
     * @param teacher
     * @return
     */
    TeacherDiningRegister findClass (String time, Integer teacher);

    /**
     * 根据教师id和班级id查询记录
     * @param teacherId
     * @param classId
     * @return
     */
    public List<TeachDiningFaceRecord> getTeachDiningFaceRecord(int teacherId,int classId);
}
