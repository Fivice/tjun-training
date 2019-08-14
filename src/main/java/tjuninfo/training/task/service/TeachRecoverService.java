package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.entity.TeacherCard;
import tjuninfo.training.task.vo.StudentCardVO;
import tjuninfo.training.task.vo.TeachCardVO;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/25 10:37
 * @Description:
 */
public interface TeachRecoverService extends IBaseService<TeacherCard> {

    /**查找教师流水号*/
    List<TeacherCard> list();

    /**查找教师流水号*/
    public List<TeachCardVO> findList();

    /**根据流水号查找教师*/
    public List<TeacherCard> findNum(String number);

    /**根据流水号回收对应教师的信息*/
    public void recoverTeachList(String number);

    /**根据班级ID回收整班教师信息*/
    public void recoverClassList(BigInteger classId);

    /**根据流水号查找教师*/
    TeacherCard findByNum(String number);

    /**分页*/
    List<TeachCardVO> findList(BTView<TeachCardVO> btView,Integer userId);

    /**同步更新*/
    void updateByNumber(String number, String teacherName,String time);

    //根据流水号查询集合
    List<TeacherCard> findListBy(String number);
    /**
     * 根据流水号查找一条数据
     * @param number
     * @return
     */
    TeacherCard findBynumber(String number);

    /**
     * 教师是否有就餐安排
     * @param number
     * @param teacherName
     * @param time
     * @return
     */
    boolean whetherArr(String number,String teacherName,String time);

    /**
     *  查找 教师最后就餐安排日期
     * @param number
     * @param teacherName
     * @param time
     * @return
     */
    TeachDining findMax(String number, String teacherName, String time);
    //根据当前日期到教师证教师就餐记录表中去查实到人数
    Long byTimeAndClassId(String nowDate, ClassDining classDining , Integer classID);
}
