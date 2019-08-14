package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.Register;
import tjuninfo.training.task.util.Pages;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.List;

/**
 * 报到表数据层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface RegisterDao extends IBaseDao<Register> {
    List<Register> findByClassId(Integer classId);



    //根据班级编号查询相应的班级就餐信息
    public List<ClassDining> findClassDiningList(String classNum);

    //根据班级ID查找报到登记表中所对应的实际学生人数
    long studentCountByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的交培训费人数
    long trainingFeeByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的住宿人数
    long hotelByRegister(Long classId);
    //根据班级ID查找报到登记表中所对应的学生的培训费
    Double trainingExpenseByRegister(Long classId);
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
    //根据流水号、学生id、报到时间 查询相应的报到信息
    Register findRegister(Integer siId, String number, String reportTime);

    Register findStuRegister(Integer siId);

    Pages findAllByClassId(int pageSize, int pageNumber, long l);
//    根据班级ID查找报到登记表中所对应的已评价的学生人数
    Long evaluationStudentCountByRegister(Long id);

    /**
     * 根据学员Id和班级Id查询注册实体
     * @param siId
     * @param classId
     * @return
     */
    Register getStuRegister(Integer siId,Long classId);

    /**
     * 获取当天就餐的学生
     * @return
     */
    List<Register> getTodayDining();

    /**
     * 通过就餐地点和班级筛选出当前班级在指定就餐地点的就餐人数
     * @param diningPlace
     * @param classId
     * @return
     */
    BigInteger getClassDiningCount(String diningPlace, String classId);

}

	
	
