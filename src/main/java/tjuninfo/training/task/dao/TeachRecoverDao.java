package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.entity.TeacherCard;
import tjuninfo.training.task.vo.TeachCardVO;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/25 10:41
 * @Description:
 */
public interface TeachRecoverDao extends IBaseDao<TeacherCard> {

    /**查找教师流水号*/
    public List<TeachCardVO> findList();

    /**根据流水号查找教师*/
    public void recoverTeachList(String number);

    /**根据班级ID回收整班教师信息*/
    public void recoverClassList(BigInteger classId);

    /**根据流水号查找教师*/
    TeacherCard findByNum(String number);

    /**分页*/
    List<TeachCardVO> findList(BTView<TeachCardVO> bt,Integer userId);

    /**根据流水号查找教师*/
    List<TeacherCard> findNum(String number);

    /**根据流水号同步更新教师姓名*/
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
     * 根据流水号、教师姓名、绑定时间查找绑定信息
     * @param number 流水号
     * @param teacherName
     * @param time 流水号绑定教师的时间
     * @return
     */
    List<TeachDining> findByTimeNum(String number,String teacherName,String time);

    /**
     *  查找 教师最后就餐安排日期
     * @param number
     * @param teacherName
     * @param time
     * @return
     */
    TeachDining findMax(String number,String teacherName,String time);
    //根据当前日期到教师证教师就餐记录表中去查实到人数
    Long byTimeAndClassId(String nowDate, ClassDining classDining,Integer classID);

    /**
     * 根据班级id查找绑定信息
     * @return
     */
    List<TeachDining> findByClass(Long id);
}
