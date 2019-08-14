package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.*;

import java.util.List;

/**
 * 报名管理服务层
 * @author wubin
 * @date 2018年10月17号
 */
public interface ISignUpManagerService{

    //根据班级id查询班级人数
    public long getCountByClassIdFromRegister(long classId);



//    /**
//     * 通过班级id获取这个班级的已报到学员信息
//     * @param pageSize
//     * @param pageIndex
//     * @param classId
//     * @return
//     */
//
//    public SignUpStudentListVO getSinUpStudentList1(int pageSize, int pageIndex, long classId);

    /**
     * 通过班级id获取这个班级的已报到学员信息,并添加学员姓名和支付状态的参数来过滤信息
     * @param pageSize
     * @param pageIndex
     * @param classId
     * @param siName
     * @param pay
     * @return
     */
    public Page getSinUpStudentList(int pageSize, int pageIndex, long classId, String siName,String unitName, int pay,int dining,int hotel);

    /**
     * 通过班级id查询班级信息
     * @param classId
     * @return
     */

    public ClassInfo getClassInfoByClassId(long classId);

    /**
     * 通过班级Id查询班级信息
     * @param classId
     * @return
     */
    public ClassInfoForRegisterBySelfVO getClassInfoByClassIdForSelfRegister(long classId);

    /**
     * 查询符合条件的班级列表
     * @param pageSize
     * @param pageIndex
     * @param classNumber
     * @param className
     * @param teacherName
     * @param startStopTime
     * @param userId 登录角色Id
     * @return
     */

    public SignUpManagerListVO getClassInfoList(int pageSize, int pageIndex, String classNumber, String className, String teacherName, String startStopTime, int userId,String regPlace,String entryStartTime,String orderName,String orderBy);

    /**
     * 判断当前登录角色id是不是对应某一个角色称谓
     * @param roleId
     * @param roleValue
     * @return
     */
    public boolean judgeUserIsRole(long roleId, String roleValue);

    public UserRole getUserRole(int userId);
    /**
     * 判断学生是否支付费用
     * @param studnetId
     * @return
     */

    public boolean judgeStudentHadPay(int studnetId);

    /**
     * 判断studentId对应的学生是否在classId对应的班级里报道
     * @param studentId
     * @param classId
     * @return
     */

    public boolean judgeStudentIdAndClassIdInRegister(int studentId,long classId);

    /**
     * 修改classId对应班级里的studentId对应的学生的支付状态
     * pay带入1则修改为已支付，2则修改为未支付
     * @param studentId
     * @param classId
     * @param pay
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
    public boolean studentPayConfirm(int studentId, long classId, int pay, double trainingExpense, double otherCharges, double hotelFee, double diningFee);
    /**
     * 通过班级Id查找班级对应日程安排
     * @param id
     * @return
     */
    public List<Scheduling> getClassSchedulingList(String id);

    /**
     * 返回单位多级表
     * @return
     */
    public List<UnitVO> getUnitGradList();

    /**
     * 查找register表中有没有报名信息，没有则保存，有则修改
     * @param siId
     * @param ClassId
     * @return
     */
    public boolean studentRegisterBySelf(int siId,long ClassId);

    /**
     * 获取一个班级注册信息实体
     * @param classId
     * @return
     */
    public Register getRegisterInfo(long classId);

    /**
     * 获取学生信息
     * @param siId
     * @return
     */
    public Student getStudentInfo(Integer siId);

    /**
     * 通过学员Id来获取培训课程历史
     * @param siId
     * @return
     */
    public List<StudentTrainingHistoryVO> getStudentTrainingHistory(Integer siId);

    /**
     * 通过学员id和班级id为学员在报名表Register里添加信息，务必不要添加已存在的报名信息，虽然不会报错，但是会扰乱表信息
     * @param siId
     * @param classId
     */
    public void saveRegisterInfo(String siId,String classId);

    /**
     * 通过班级ID和住宿就餐状态选择计算住宿费用和就餐费用
     * @param classId
     * @param dining
     * @param hotel
     * @return
     */
    public PayInfoForDiningAndHotelChangeVO calculateDiningAndHotelPayInfo(long classId, int dining, int hotel);

    /**
     * 通过班级Id计算就餐费
     * @param classId
     * @return
     */
    public double getClassDiningFee(long classId);

    /**
     * 根据id获取单位信息
     * @param id
     * @return
     */
    public Unit getUnitInfo(Integer id);

    /**
     * 获取角色信息
     * @param userId
     * @return
     */
    public SysUser getRoleInfo(Integer userId);

    /**
     * 通过流水号获取对应的流水号信息
     * @param number
     * @return
     */
    public List<StudentCard> getStudentCardInfo(String number);

    /**
     * 存储流水号信息
     * 0:Register为空,即报名信息为空
     * 1:报名信息报名时间为空
     * 2:查询流水号表结果集为空
     * 3:查询流水号表结果集唯一,存储成功
     * 4:流水号已经分配给当前角色
     * 5:流水号已经被他人占用
     * 6:结果集不唯一,说明流水号表里存在多个相同流水号,建议不使用这种流水号,交给管理员处理删除重复流水号
     * 7:Register报名信息里存在流水号
     * @param number
     * @param studentId
     * @param classId
     * @return
     */
    public int updateStudentCard(String number,String studentId,String classId);

    /**
     * 通过班级id和学员id查询表，计算学员实际需要住宿的天数
     * 如果学员时正常报道缴费则按照班级安排计算，如果缴费日期是开班时间段里的其他日期则按照其他计算方式计算，如下：
     * 如果是某一天早上10点前缴费则计算早餐，早上10-下午3点算中餐，3点后算晚餐
     * @param classId
     * @param siId
     * @return
     */
    Double calculateHotelDay(long classId,int siId);
}
