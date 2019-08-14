package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ClassInfoDao;
import tjuninfo.training.task.dao.IBasicParametersDao;
import tjuninfo.training.task.dao.StudentRecoverDao;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.StudentRecoverService;
import tjuninfo.training.task.vo.StudentCardVO;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/25 14:50
 * @Description:学员流水号业务实现层
 */
@Service
public class StudentRecoverServiceImpl extends BaseServiceImpl<StudentCard> implements StudentRecoverService {

    private StudentRecoverDao studentRecoverDao;
    private ClassInfoDao  ClassInfoDao;
    private IBasicParametersDao basicParametersDao;
    @Resource
    public void setDao(StudentRecoverDao studentRecoverDao) {
        this.studentRecoverDao = studentRecoverDao;
 //       this.dao = studentRecoverDao;
    }
    @Resource()
    public void setDao(ClassInfoDao ClassInfoDao) {
        this.ClassInfoDao = ClassInfoDao;
    }
    @Resource
    public void setDao(IBasicParametersDao basicParametersDao) {
        this.basicParametersDao = basicParametersDao;
    }


    @Override
    /**查找学员流水号**/
    public List<StudentCardVO> findList() {
        return studentRecoverDao.findList();
    }

    @Override
    /**根据流水号回收学员对应信息**/
    public void recoverStuList(String number) {
         studentRecoverDao.recoverStuList(number);
    }

    @Override
    /**根据班级ID回收对应的学员流水号**/
    public void recoverClassList(BigInteger classId) {
//        studentRecoverDao.recoverClassList(classId);
        Long id = classId.longValue();
        //根据班级id查找对应的流水号集合
        List<StudentCard> list = studentRecoverDao.findByClassId(id);
        //遍历集合  更新流水号
        for(StudentCard c:list ){
            recoverStuList(c.getNumber());
        }
        ClassInfo c = ClassInfoDao.get(id);
        c.setState("1");
        ClassInfoDao.saveOrUpdate(c);
    }

    @Override
    /**查找学员流水号**/
    public Integer sumByClassId(Long classId) {
        List<StuDiningRecord> stuDiningRecordList = studentRecoverDao.findAllByClassId(classId);
        List<BasicParameters> basicParametersList = basicParametersDao.getAll();
        String eatStandard = basicParametersList.get(0).getEatStandard();
        String[] eatStandards = eatStandard.split("[，,]");
        //获取就餐标准
        Integer breakfastStandard = Integer.valueOf(eatStandards[0].trim());//早餐标准
        Integer lunchStandard = Integer.valueOf(eatStandards[1].trim());//中餐标准
        Integer dinnerStandard = Integer.valueOf(eatStandards[2].trim());//晚餐标准
        Integer studentDinding = 0 ;//学生实际就餐费用
        //获取就餐标准
        Integer breakfast = 0 ;
        Integer lunch = 0 ;
        Integer dinner = 0 ;
        for(StuDiningRecord stuDiningRecord : stuDiningRecordList){
            if(stuDiningRecord != null){
                if(stuDiningRecord.getBreakfast() == 1){
                    breakfast += stuDiningRecord.getBreakfast();
                }
                if(stuDiningRecord.getLunch() == 1){
                    lunch += stuDiningRecord.getLunch();
                }
                if(stuDiningRecord.getDinner() == 1){
                    dinner += stuDiningRecord.getDinner();
                }
            }
        }
        studentDinding = breakfast*breakfastStandard+lunch*lunchStandard+dinner*dinnerStandard;
        return studentDinding;
    }

    @Override
    /**分页**/
    public List<StudentCardVO> findList(BTView<StudentCardVO> bt,Integer userId) {
        return studentRecoverDao.findList(bt,userId);
    }

    /**根据流水号查找流水号绑定信息**/
    @Override
    public StudentCard findCardBynumb(String number) {
        return studentRecoverDao.findCardBynumb(number);
    }

    @Override
    public Long byTimeAndClassId(int i, String nowDate, ClassDining classDining) {
        return studentRecoverDao.byTimeAndClassId(i,nowDate,classDining);
    }
}
