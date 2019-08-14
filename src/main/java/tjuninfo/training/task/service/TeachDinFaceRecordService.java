package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.TeachDiningFaceRecord;
import tjuninfo.training.task.entity.TeacherDiningRegister;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;
import tjuninfo.training.task.entity.*;

import java.text.ParseException;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/12/7 14:31
 * @Description:
 */
public interface TeachDinFaceRecordService extends IBaseService<TeachDiningFaceRecord> {

    List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, String month);

    List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, Integer userId, String month);

    List<TeachDiningFaceRecordVO> findList(BTView<TeachDiningFaceRecordVO> btView, String teacherId,Integer classId);
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

    /**
     * 教师人脸就餐记录保存
     * @param time
     * @param t
     * @param tdfr
     * @param tdr
     * @param c
     * @return
     */
    CmsResult saveData(String time, TeacherInfo t , TeachDiningFaceRecord tdfr, TeacherDiningRegister tdr,ClassInfo c) throws ParseException;
}
