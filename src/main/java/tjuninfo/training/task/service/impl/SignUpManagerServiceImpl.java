package tjuninfo.training.task.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.SignUpManagerConstantCode;
import tjuninfo.training.task.dao.ISignUpManagerDao;
import tjuninfo.training.task.dao.ISignUpStudentDao;
import tjuninfo.training.task.dto.SignUpStudentDTO;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.DateUtils;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 报名管理服务层
 * @author wubin
 * @date 2018年10月17号
 */

@Service
public class SignUpManagerServiceImpl implements ISignUpManagerService {
    private ISignUpManagerDao iSignUpManagerDao;
    private ISignUpStudentDao iSignUpStudentDao;
    private SchedulingService iSchedulingService;
    private final IBaseDao<Register> registerIBaseDao;
    private final IBaseService<Register> registerIBaseService;
    private final IBaseService<Unit> unitIBaseService;
    private final IBaseService<SysUser> sysUserIBaseService;

    @Autowired
    private StudentCardService studentCardService;
    @Autowired
    private RegisterService registerService;

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    public SignUpManagerServiceImpl(IBaseDao<Register> registerIBaseDao, IBaseService<Register> registerIBaseService, IBaseService<Unit> unitIBaseService, IBaseService<SysUser> sysUserIBaseService) {
        this.registerIBaseDao = registerIBaseDao;
        this.registerIBaseService = registerIBaseService;
        this.unitIBaseService = unitIBaseService;
        this.sysUserIBaseService = sysUserIBaseService;
    }


    @Autowired
    private void setSchedulingService(SchedulingService schedulingService){
        this.iSchedulingService = schedulingService;
    }

    @Autowired
    private void setiSignUpManagerDao(ISignUpManagerDao iSignUpManagerDao){
        this.iSignUpManagerDao = iSignUpManagerDao;
    }
    @Autowired void setiSignUpStudentDao(ISignUpStudentDao iSignUpStudentDao){
        this.iSignUpStudentDao = iSignUpStudentDao;
    }
    private static final Log log =
            LogFactory.getLog(SignUpManagerServiceImpl.class);


    @Override
    public long getCountByClassIdFromRegister(long classId) {
        return iSignUpManagerDao.getCountByClassIdFromRegister(classId);
    }

    @Override
    public Page getSinUpStudentList(int pageSize, int pageIndex, long classId, String siName,String unitName, int pay,int dining,int hotel) {
        Page<SignUpStudentVO> newPage = new Page<>();
        List<SignUpStudentVO> newList = new ArrayList<>();

        Page<SignUpStudentDTO> page = iSignUpStudentDao.getSinUpStudentList(pageSize,pageIndex,classId,siName,unitName,pay,dining,hotel);
        ClassInfo classInfo = classInfoService.get(classId);
        PayInfoForDiningAndHotelChangeVO payInfoForDiningAndHotelChangeVO = calculateDiningAndHotelPayInfo(classId,1,0);
        List<SignUpStudentDTO> list = page.getList();
        for (SignUpStudentDTO temp:list
             ) {
            SignUpStudentVO signUpStudentVO = new SignUpStudentVO(
                    temp.getStudent(),
                    classInfo,
                    temp.getDining(),
                    temp.getHotel(),
                    temp.getPay(),
                    temp.getReportTime(),
                    temp.getPlace(),
                    temp.getHotelPlace(),
                    temp.getScaleFeeTotal(),
                    temp.getFoodTotal(),
                    temp.getOtherCharges(),
                    temp.getTrainingExpense(),
                    temp.getNumber()
            );
            newList.add(signUpStudentVO);
        }

        newPage.setList(newList);
        newPage.setTotalRecords(page.getTotalRecords());
        newPage.setPageSize(pageSize);
        newPage.setPageNo(pageIndex);
        return newPage;
    }


    @Override
    public ClassInfo getClassInfoByClassId(long classId) {
        return iSignUpManagerDao.getClassInfoByClassId(classId);
    }
    @Override
    public ClassInfoForRegisterBySelfVO getClassInfoByClassIdForSelfRegister(long classId){
        ClassInfoForRegisterBySelfVO classInfoSimply;
        ClassInfo classInfo = iSignUpManagerDao.getClassInfoByClassId(classId);
        classInfoSimply = new ClassInfoForRegisterBySelfVO(
                classInfo.getId(),
                classInfo.getProjectNumber(),
                classInfo.getClassNumber(),
                classInfo.getClassName(),
                classInfo.getSiUnitId(),
                classInfo.getHostUnit(),
                classInfo.getTeacherName(),
                classInfo.getPhoneNumber(),
                classInfo.getSchedulingId(),
                classInfo.getTypeId(),
                classInfo.getTypeName(),
                classInfo.getTrainingExpense(),
                classInfo.getRegPlace(),
                classInfo.getOtherCharges(),
                classInfo.getHotelPlace(),
                classInfo.getDiningPlace(),
                classInfo.getStartStopTime(),
                classInfo.getIncreaseDay(),
                classInfo.getFileUrl(),
                classInfo.getEvaluate(),
                classInfo.getEvaluateStartTime(),
                classInfo.getEvaluateStopTime()
        );
        return classInfoSimply;
    }


    @Override
    public SignUpManagerListVO getClassInfoList(int pageSize, int pageIndex, String classNumber, String className, String teacherName, String startStopTime, int userId,String regPlace,String entryStartTime,String orderName,String orderBy) {
        List<SignUpManagerVO> signUpManagerVOList = new ArrayList<>();
        Pages pages = iSignUpManagerDao.getClassInfoList(pageSize,pageIndex,classNumber,className,teacherName,startStopTime,userId,regPlace,entryStartTime,orderName,orderBy);
        List classInfoArrayList = pages.getResult();
        long totalResults = pages.getTotalResults();
        long totalPages = pages.getTotalPages();

        for ( Object temp:classInfoArrayList
        ) {
            ClassInfo classInfo = (ClassInfo) temp;


            //通过班级id查询报名学生中哪些人报了这个班
            long signUpCount = getCountByClassIdFromRegister(classInfo.getId());
            //查看计划人数
            long signUpCountPlan = classInfo.getPlannedNumber();

            //实例化SignUpManagerVO对象并添加入List表中
            SignUpManagerVO signUpManagerVO = new SignUpManagerVO(classInfo.getId(),classInfo.getClassNumber(),classInfo.getClassName(),classInfo.getUserId(),classInfo.getTeacherName(),signUpCount,signUpCountPlan,classInfo.getHostUnit(),classInfo.getStartStopTime(),classInfo.getDayNum(),classInfo.getStatus(),classInfo.getRegPlace());
            signUpManagerVOList.add(signUpManagerVO);

        }
        return new SignUpManagerListVO(totalResults,totalPages,signUpManagerVOList);
    }

    @Override
    public boolean judgeUserIsRole(long userId, String roleValue) {
        return iSignUpManagerDao.judgeUserIsRole(userId,roleValue);
    }

    @Override
    public UserRole getUserRole(int userId) {
        return iSignUpManagerDao.getUserRole(userId);
    }

    @Override
    public boolean judgeStudentHadPay(int studnetId) {
        return iSignUpManagerDao.judgeStudentHadPay(studnetId);
    }

    @Override
    public boolean judgeStudentIdAndClassIdInRegister(int studentId, long classId) {
        return iSignUpManagerDao.judgeStudentIdAndClassIdInRegister(studentId,classId);
    }

    @Override
    public boolean studentPayConfirm(int studentId, long classId, int pay) {
        return iSignUpManagerDao.studentPayConfirm(studentId,classId,pay);
    }

    @Override
    public boolean studentPayConfirm(int studentId, long classId, int pay, double trainingExpense, double otherCharges, double hotelFee, double diningFee) {
        return iSignUpManagerDao.studentPayConfirm(studentId,classId,pay,trainingExpense,otherCharges,hotelFee,diningFee);
    }

    @Override
    public List<Scheduling> getClassSchedulingList(String id) {
        return iSchedulingService.findSchedulingList(null,null,id);
    }

    @Override
    public List<UnitVO> getUnitGradList() {
        List<UnitVO> unitVOList = new ArrayList<>();
        List<Unit> fatherUnitList = iSignUpManagerDao.getFatherUnitList();
        for (Unit temp: fatherUnitList
        ) {
            List<UnitVO> childUnitVOList = new ArrayList<>();
            List<Unit> childUnitList = iSignUpManagerDao.getChildUnitList(temp.getAreaId());
            for (Unit temp2:childUnitList
            ) {
                UnitVO childUnitVO = new UnitVO(temp2.getAreaId(),temp2.getAreaName(),null);
                childUnitVOList.add(childUnitVO);
            }

            UnitVO fatherUnitVO = new UnitVO(temp.getAreaId(),temp.getAreaName(),childUnitVOList);
            unitVOList.add(fatherUnitVO);

        }
        return unitVOList;
    }

    @Override
    public boolean studentRegisterBySelf(int siId, long classId) {

        boolean isRegister = judgeStudentIdAndClassIdInRegister(siId,classId);
        if (isRegister){
            //已经存在报名信息
            return true;//通知修改学生信息
        }else {
            //报名信息不在register里
            return false;//通知在register里添加报名信息
        }
    }

    @Override
    public Register getRegisterInfo(long classId) {
        return iSignUpManagerDao.getRegisterInfo(classId);
    }

    @Override
    public Student getStudentInfo(Integer siId) {
        return iSignUpManagerDao.getStudentInfo(siId);
    }

    @Override
    public List<StudentTrainingHistoryVO> getStudentTrainingHistory(Integer siId) {
        List<StudentTrainingHistoryVO> studentTrainingHistoryVOList = new ArrayList<>();
        List<Register> registerList = iSignUpManagerDao.getStudentTrainingHistoryList(siId);
        for (Register r: registerList
        ) {
            ClassInfo classInfo = iSignUpManagerDao.getClassInfoByClassId(r.getClassId());
            if (classInfo !=null){
                StudentTrainingHistoryVO studentTrainingHistoryVO = new StudentTrainingHistoryVO(classInfo.getClassName(),classInfo.getHostUnit(),classInfo.getStartStopTime(),classInfo.getDayNum(),classInfo.getTimeNum());
                studentTrainingHistoryVOList.add(studentTrainingHistoryVO);
            }
        }
        return studentTrainingHistoryVOList;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public void saveRegisterInfo(String siId, String classId) {
        Register newRegister = new Register();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        //获取报名班级报信息
        ClassInfo classInfo = iSignUpManagerDao.getClassInfoByClassId(Long.parseLong(classId));
        //初始化支付信息为就餐和单间
        PayInfoForDiningAndHotelChangeVO pay = calculateDiningAndHotelPayInfo(Long.parseLong(classId),1,0);
        newRegister.setSiId(Integer.parseInt(siId));
        newRegister.setClassId(Integer.parseInt(classId));
//        newRegister.setTrainingExpense(classInfo.getTrainingExpense()==null?(double) 0:classInfo.getTrainingExpense());
        newRegister.setTrainingExpense(0d);
//        newRegister.setOtherCharges(classInfo.getOtherCharges()==null?(double) 0:classInfo.getOtherCharges());
        newRegister.setOtherCharges(0d);
//        newRegister.setScaleFeeTotal(pay.getHotelFee());
        newRegister.setScaleFeeTotal(0d);
//        newRegister.setFoodTotal(pay.getDiningFee());
        newRegister.setFoodTotal(0d);
        newRegister.setDining("1");
        newRegister.setHotel(0);
        newRegister.setPay("2");
        newRegister.setReportTime(df.format(new Date()));
        newRegister.setStatus(SignUpManagerConstantCode.STUDENT_NOT_EVALUATE);
        newRegister.setPlace(classInfo.getHotelPlace());
        //添加报名信息
        registerIBaseService.save(newRegister);
    }

    @Override
    public PayInfoForDiningAndHotelChangeVO calculateDiningAndHotelPayInfo(long classId, int dining, int hotel) {
        ClassInfo classInfo = iSignUpManagerDao.getClassInfoByClassId(classId);
        double hotelFee,diningFee;
        double increaseDay = classInfo.getIncreaseDay()==null?0:classInfo.getIncreaseDay();
        //Integer hotelDay = calculateHotelDay();
        switch (hotel){
            //标间
            case 0:hotelFee = classInfo.getInterScaleFee()*(classInfo.getDayNum()+increaseDay-1);
                break;
            //单间
            case 1:hotelFee = classInfo.getSingleRoomCharge()*(classInfo.getDayNum()+increaseDay-1);
                break;
            //不住宿
            case 2:hotelFee = 0;
                break;
            default:hotelFee = 0;
                break;
        }
        switch (dining){
            //就餐
            /**
             * 检查当数据库为空的情况（未做）
             */
            case 1:diningFee = getClassDiningFee(classId);
                break;
            //不就餐
            case 2:diningFee = 0;
                break;
            default:diningFee = 0;
                break;
        }
        return new PayInfoForDiningAndHotelChangeVO(hotelFee,diningFee);
    }

    @Override
    public double getClassDiningFee(long classId) {
        List<ClassDining> diningList = iSignUpManagerDao.getClassDining(classId);
        ClassInfo classInfo = iSignUpManagerDao.getClassInfoByClassId(classId);
        double breakfast = Double.parseDouble(classInfo.getBreakfast());
        double lunch = Double.parseDouble(classInfo.getLunch());
        double dinner = Double.parseDouble(classInfo.getDinner());
        //总餐费
        double diningFee = 0;

        for (ClassDining temp: diningList
        ) {
            //加上早餐费
            diningFee+=(temp.getBreakfast()==1?breakfast:0);
            //加上午餐费
            diningFee+=(temp.getLunch()==1?lunch:0);
            //加上晚餐费
            diningFee+=(temp.getDinner()==1?dinner:0);
        }
        return diningFee;
    }

    @Transactional
    @Override
    public Unit getUnitInfo(Integer id) {
        return unitIBaseService.get(id);
    }

    @Override
    public SysUser getRoleInfo(Integer userId) {
        return sysUserIBaseService.get(userId);
    }

    @Override
    public List<StudentCard> getStudentCardInfo(String number) {
        return iSignUpManagerDao.getStudentCardInfo(number);
    }

    @Override
    public int updateStudentCard(String number, String studentId, String classId) {
        int status;
        Register register = iSignUpManagerDao.getStudentRegisterInfo(Integer.parseInt(studentId),Long.parseLong(classId));
        if (register == null){//没有报信息
            status = 0;
        }else if(register.getReportTime()==null||("").equals(register.getReportTime())){//报名信息报名时间为空
            status = 1;
        }else if (register.getNumber()!= null&&number.equals(register.getNumber())){//报信息里存在流水号
            status = 7;
        }else {
            List<StudentCard> list = iSignUpManagerDao.getStudentCardInfo(number);
            switch (list.size()){
                case 0://查询流水号表结果集为空
                    status = 2;
                    break;
                case 1://查询流水号表结果集唯一
                    //
                    if (list.get(0).getStudentId() == null&list.get(0).getRegisterTime() == null){//判断占用情况
                        //流水号未占用
                        //获取当前流水号对象
                        StudentCard studentCard = studentCardService.get(list.get(0).getId());
                        //更新流水号对象信息并保存
                        studentCard.setStudentId(Integer.valueOf(studentId));
                        studentCard.setRegisterTime(register.getReportTime());
                        studentCardService.update(studentCard);
                        //注册信息更新并保存
                        register.setNumber(number);
                        registerService.update(register);
                        status = 3;
                        break;
                        // 存储失败,目前无法操作
                    }else if (list.get(0).getStudentId() == Integer.parseInt(studentId)& list.get(0).getRegisterTime().equals(register.getReportTime())){
                        //流水号已经分配给当前角色
                        status = 4;
                        break;
                    }else{
                        //流水号已经被他人占用
                        status = 5;
                        break;
                    }
                default://结果集不唯一,说明流水号表里存在多个相同流水号,建议不使用这种流水号,交给管理员处理删除重复流水号
                    status = 6;
                    break;
            }
        }

        return status;
    }

    @Override
    public Double calculateHotelDay(long classId, int siId) {

        Register register = registerService.getStuRegister(siId,classId);
        ClassInfo classInfo = classInfoService.get(classId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startStopTime = classInfo.getStartStopTime();
        String[] timeArr = startStopTime.split(" 至 ");
        Calendar startTime = null,stopTime = null;
        Double scaleFee = 0d;
        if (timeArr.length == 2){
            try {
                startTime = Calendar.getInstance();
                startTime.setTime(sdf.parse(timeArr[0]));

                stopTime = Calendar.getInstance();
                stopTime.setTime(sdf.parse(timeArr[1]));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            //判断注册信息是否为空，一般根据报到表进行此查询应该可以查到数据，如果查不到说明传参有误
            if (register != null){
                if (register.getScaleFeeTotal()!=0||register.getScaleFeeTotal()!=null){
                    //表中住宿费用不为空
                    scaleFee = register.getScaleFeeTotal();
                }else {
                    //todo 表中住宿费用为空


                }
            }
        }
        return scaleFee;
    }
}
