package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.Register;
import tjuninfo.training.task.util.Pages;

import java.math.BigInteger;
import java.util.List;


/**
 * 报到信息表业务层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface RegisterService extends IBaseService<Register>{

    //通过班级查找登记集合
    List<Register> findByClassId(Integer classId);

    //查找登记对象
    public Register findRegister(Integer siId,String number,String reportTime);

    //通过学生id查找登记对象
    public Register findStuRegister(Integer siId);


    //根据班级编号查询相应的班级就餐信息
    public List<ClassDining> findClassDiningList(String classNum);



    List<Register> getAll();
    //根据班级ID查找报到登记表中所对应的实际学生人数
    Long studentCountByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的交培训费人数
    long trainingFeeByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的住宿人数
    long hotelByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的学生的培训费
    Double trainingExpenseByRegister(Long id);
    Double hotelDayCount(Long classId);
    //根据班级ID查找报到登记表中所对应的学生的住宿费
    Double scaleFeeTotalByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的就餐人数
    long foodTotalByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的学生的就餐费
    Double foodTotalByRegister2(Long classId);
    //根据班级ID查找报到登记表中所对应的学生的其他费用
    Double otherChargesByRegister(Long classId);

    List<Register> findAllByClassId(long classId);
    //根据班级id和学生id查询相应的报到信息
    public List<Register> findRegisters(String studentId,String classId);
    //根据班级id和学生id查询相应的报到信息
    public List<Register> findRegistersBysIdAndcId(String studentId,String classId);

    Pages findAllByClassId(int pageSize, int pageNumber, long l);
    //根据班级ID查找报到登记表中所对应的已评价的学生人数
    Long evaluationStudentCountByRegister(Long id);
    public Register getStuRegister(Integer siId, Long classId);
    /**
     * 获取当天就餐的学生
     * @return
     */
    List<Register> getTodayDining();
    public BigInteger getClassDiningCount(String diningPlace, String classId);

}
