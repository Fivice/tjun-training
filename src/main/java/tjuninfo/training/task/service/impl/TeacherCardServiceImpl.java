package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ClassInfoDao;
import tjuninfo.training.task.dao.TeachRecoverDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.entity.TeacherCard;
import tjuninfo.training.task.service.TeachDiningService;
import tjuninfo.training.task.service.TeachRecoverService;
import tjuninfo.training.task.vo.StudentCardVO;
import tjuninfo.training.task.vo.TeachCardVO;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/25 10:38
 * @Description:
 */
@Service
public class TeacherCardServiceImpl extends BaseServiceImpl<TeacherCard> implements TeachRecoverService {

    private TeachRecoverDao teachRecoverDao;
    private ClassInfoDao  ClassInfoDao;
    @Resource
    public void setDao(TeachRecoverDao teachRecoverDao) {
        this.teachRecoverDao = teachRecoverDao;
        this.dao = teachRecoverDao;
    }
    @Resource()
    public void setDao(ClassInfoDao ClassInfoDao) {
        this.ClassInfoDao = ClassInfoDao;
    }

    @Override
    /**查找教师流水号*/
    public List<TeacherCard> list() {
        return teachRecoverDao.getAll();
    }

    @Override
    /**查找教师流水号*/
    public List<TeachCardVO> findList() {
        return teachRecoverDao.findList();
    }

    @Override
    /**根据流水号查找教师*/
    public List<TeacherCard> findNum(String number) {
        return teachRecoverDao.findNum(number);
    }

    @Override
    /**根据流水号回收对应教师的信息*/
    public void recoverTeachList(String number) {
        teachRecoverDao.recoverTeachList(number);
    }

    @Override
    /**根据班级ID回收整班教师信息*/
    public void recoverClassList(BigInteger classId) {
//        teachRecoverDao.recoverClassList(classId);
        //将教师卡表里的teacher = NULL,time = NULL 两字段置空
        //通过班级id照流水号
        List<TeachDining>  list =teachRecoverDao.findByClass(classId.longValue());
        for(TeachDining td :list){
            recoverTeachList(td.getNumber());
        }
        Long id = classId.longValue();
        ClassInfo c = ClassInfoDao.get(id);
        c.setState2("1");
        ClassInfoDao.saveOrUpdate(c);
    }

    @Override
    /**根据流水号查找教师*/
    public TeacherCard findByNum(String number) {
        return teachRecoverDao.findByNum(number);
    }

    @Override
    /**分页*/
    public List<TeachCardVO> findList(BTView<TeachCardVO> bt,Integer userId) {
        return teachRecoverDao.findList(bt,userId);
    }

    @Override
    /**同步更新*/
    public void updateByNumber(String number, String teacherName,String time) {
         teachRecoverDao.updateByNumber(number,teacherName,time);
    }

    @Override
    public List<TeacherCard> findListBy(String number) {
        return teachRecoverDao.findListBy(number);
    }

    @Override
    public TeacherCard findBynumber(String number) {
        return teachRecoverDao.findBynumber(number);
    }

    @Override
    public boolean whetherArr(String number, String teacherName, String time) {
        boolean bool = true;
        List<TeachDining>  list = teachRecoverDao.findByTimeNum(number,teacherName,time);
        if(list.size()<=1){
            try {
                if(list.size()==1){
                    TeachDining t = list.get(0);
                    if(t.getDay()!=null&&!t.getDay().equals("")){
                        bool = true;
                    }else {
                        bool = false;
                    }
                }else {
                    bool =  false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bool;
    }

    @Override
    public TeachDining findMax(String number, String teacherName, String time) {
        return teachRecoverDao.findMax(number, teacherName, time);
    }

    @Override
    public Long byTimeAndClassId(String nowDate, ClassDining classDining , Integer classID) {
        return teachRecoverDao.byTimeAndClassId(nowDate,classDining ,classID);
    }

}
