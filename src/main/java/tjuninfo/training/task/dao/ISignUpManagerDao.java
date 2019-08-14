package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface ISignUpManagerDao extends IBaseDao<ClassInfo> {
//    分页查询

    /**
     *
     * @param pageSize 页大小
     * @param pageIndex 页码
     * @param status 开班状态
     * @param searchContent 搜索内容
     * @param startStopTime 开班时间段
     * @return Pages
     */
    public Pages getList(int pageSize, int pageIndex,String status ,String searchContent, String startStopTime);

    /**
     * 通过ClassInfo表中的Id来查询有多少学生注册了这个班
     * @param classId 班级Id
     * @return long报班学生数，如果没有这个班则返回-1
     */
    public long getCountByClassIdFromRegister(long classId);



    /**
     * 通过班级Id号检索当前班级信息
     * @param classId 班级Id
     * @return
     */
    public ClassInfo getClassInfoByClassId(long classId);

    /**
     * 通过以下条件查询符合条件的班级信息
     * @param pageSize
     * @param pageIndex
     * @param classNumber
     * @param className
     * @param teacherName
     * @param startStopTime
     * @param userId
     * @return
     */

    public Pages getClassInfoList(int pageSize,int pageIndex,String classNumber,String className,String teacherName,String startStopTime,int userId,String regPlace,String entryStartTime,String orderName,String orderBy);

    /**
     * 通过班级里的userId判断角色的roleValue
     * @param roleId 角色Id
     * @param roleValue SysRole表中与之对应的roleValue
     * @return 是则返回true 否则返回false
     */
    public boolean judgeUserIsRole(long roleId,String roleValue);

    public UserRole getUserRole(int userId);
    /**
     * 通过studentId 判断当前学生是否缴费
     * @param studnetId 学生Id
     * @return  是则返回true 否则返回false
     */
    public boolean judgeStudentHadPay(int studnetId);

    /**
     * 带入学生id(si_id)和班级id(class_id)查看register里是否有同时满足这个条件的注册信息
     * @param studentId 学员Id
     * @param classId 班级Id
     * @return
     */
    public boolean judgeStudentIdAndClassIdInRegister(int studentId,long classId);


    /**
     * 修改指定一个班级指定的一个学生支付状态为某一支付状态
     * @param studentId 学生Id
     * @return
     */
    public boolean studentPayConfirm(int studentId,long classId,int pay);

    /**
     * 修改学员费用以及交费状态信息
     * @param studentId
     * @param classId
     * @param pay
     * @param trainingExpense
     * @param otherCharges
     * @param hotelFee
     * @param diningFee
     * @return
     */
    public boolean studentPayConfirm(int studentId,long classId,int pay,double trainingExpense,double otherCharges,double hotelFee,double diningFee);
    /**
     * 查询状态没关闭的一级单位
     * @return
     */
    public List<Unit> getFatherUnitList();

    /**
     * 根据父级id查找状态没关闭的对应子集
     * @return
     */
    public List<Unit> getChildUnitList(Integer fatherId);

    /**
     * 获取一个班级注册信息实体
     * @param classId
     * @return
     */
    public Register getRegisterInfo(long classId);
    /**
     * 获取一个班级注册信息实体
     * @param classId
     * @param siId
     * @return
     */
    public Register getStudentRegisterInfo(Integer siId,long classId);


    /**
     * 获取学生信息
     * @param siId
     * @return
     */
    public Student getStudentInfo(Integer siId);

    /**
     * 通过学生Id查找学生培训记录
     * @param siId
     * @return
     */
    public List<Register> getStudentTrainingHistoryList(Integer siId);

    /**
     * 通过班级id差就餐安排
     * @param classId
     * @return
     */
    public List<ClassDining> getClassDining(long classId);

    /**
     * 通过流水号获取对应的流水号信息
     * @param number
     * @return
     */
    public List<StudentCard> getStudentCardInfo(String number);
}
