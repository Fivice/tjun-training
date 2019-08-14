package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ITeacherDiningForSceneDao;
import tjuninfo.training.task.dto.*;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeacherDiningForScene;
import tjuninfo.training.task.entity.TeacherDiningRegister;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.ITeacherDiningForSceneService;
import tjuninfo.training.task.service.ITeacherDiningRegisterService;
import tjuninfo.training.task.service.TeacherInfoService;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.ClassInfoForSceneVO;
import tjuninfo.training.task.vo.TeacherInfoForSceneVO;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class TeacherDiningForSceneServiceImpl extends BaseServiceImpl<TeacherDiningForScene> implements ITeacherDiningForSceneService {
    private final ITeacherDiningForSceneDao iTeacherDiningForSceneDao;
    private final ClassInfoService classInfoService;
    private final ITeacherDiningRegisterService iTeacherDiningRegisterService;
    private final TeacherInfoService teacherInfoService;


    @Autowired
    public TeacherDiningForSceneServiceImpl(ITeacherDiningForSceneDao iTeacherDiningForSceneDao, ClassInfoService classInfoService, ITeacherDiningRegisterService iTeacherDiningRegisterService, TeacherInfoService teacherInfoService) {
        this.iTeacherDiningForSceneDao = iTeacherDiningForSceneDao;
        this.classInfoService = classInfoService;
        this.iTeacherDiningRegisterService = iTeacherDiningRegisterService;
        this.dao = iTeacherDiningForSceneDao;
        this.teacherInfoService = teacherInfoService;
    }

    @Override
    public Page<TeacherInfoForSceneVO> getTeacherInfoForScene(int pageSize, int pageIndex,String teacherName) {
        Page<TeacherInfoForSceneDTO> teacherInfoForSceneDTOPage = iTeacherDiningForSceneDao.getTeacherInfoForScene(pageSize,pageIndex,teacherName);
        List<TeacherInfoForSceneDTO> dtoList= teacherInfoForSceneDTOPage.getList();

        List<TeacherInfoForSceneVO> voList = new ArrayList<>();
        Page<TeacherInfoForSceneVO> teacherInfoForSceneVOPage = new Page<>();

        for (TeacherInfoForSceneDTO temp:dtoList){
            BigInteger classId = temp.getClassId();
            String className = null;
            if(classId!=null){
                className = classInfoService.get(classId.longValue()).getClassName();
            }
            TeacherInfoForSceneVO teacherInfoForSceneVO = new TeacherInfoForSceneVO();
            teacherInfoForSceneVO.setTeacherId(temp.getTeacherId());
            teacherInfoForSceneVO.setTeacherName(temp.getTeacherName());
            teacherInfoForSceneVO.setIdNumber(temp.getIdNumber());
            teacherInfoForSceneVO.setClassId(temp.getClassId());
            teacherInfoForSceneVO.setClassName(className);

            voList.add(teacherInfoForSceneVO);
        }
        teacherInfoForSceneVOPage.setList(voList);
        teacherInfoForSceneVOPage.setPageSize(pageSize);
        teacherInfoForSceneVOPage.setPageNo(pageIndex);
        teacherInfoForSceneVOPage.setTotalRecords(teacherInfoForSceneDTOPage.getTotalRecords());
        return teacherInfoForSceneVOPage;
    }

    @Override
    public Page<ClassInfoForSceneVO> getClassInfoForScene(int pageSize, int pageIndex,String className) {
        Page<ClassInfoForSceneDTO> classInfoForSceneDTOPage = iTeacherDiningForSceneDao.getClassInfoForScene(pageSize,pageIndex,className);
        List<ClassInfoForSceneDTO> dtoList = classInfoForSceneDTOPage.getList();

        Page<ClassInfoForSceneVO> classInfoForSceneVOPage = new Page<>();
        List<ClassInfoForSceneVO> voList = new ArrayList<>();
        for (ClassInfoForSceneDTO temp:dtoList
             ) {
            ClassInfoForSceneVO classInfoForSceneVO = new ClassInfoForSceneVO();
            String[] teacherIdArr = {};
            if(temp.getTeacherId()!=null){
                teacherIdArr = temp.getTeacherId().split(",");
            }

            StringBuffer teacherName = new StringBuffer();
            for (int i = 0; i < teacherIdArr.length; i++) {
                TeacherInfo teacherInfo = teacherInfoService.get(Integer.parseInt(teacherIdArr[i]));
                teacherName.append(teacherInfo.getTiName());
                if (i != teacherIdArr.length-1){
                    teacherName.append(",");
                }
            }
            classInfoForSceneVO.setClassId(temp.getClassId());
            classInfoForSceneVO.setClassName(temp.getClassName());
            classInfoForSceneVO.setTeacherId(temp.getTeacherId());
            classInfoForSceneVO.setTeacherName(teacherName.toString());
            classInfoForSceneVO.setStartStopTime(temp.getStartStopTime());

            voList.add(classInfoForSceneVO);
        }
        classInfoForSceneVOPage.setList(voList);
        classInfoForSceneVOPage.setPageSize(pageSize);
        classInfoForSceneVOPage.setPageNo(pageIndex);
        classInfoForSceneVOPage.setTotalRecords(classInfoForSceneDTOPage.getTotalRecords());
        return classInfoForSceneVOPage;
    }

    @Override
    public Page<TeacherDiningForScene> getTeacherDiningForScene(int pageSize, int pageIndex, int teacherId, long classId) {
        return iTeacherDiningForSceneDao.getTeacherDiningForScene(pageSize,pageIndex,teacherId,classId);
    }

    @Override
    public void teacherDiningInit(int teacherId, long classId, int userId,String startTime,String stopTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendarStartTime = Calendar.getInstance();
        Calendar calendarStopTime = Calendar.getInstance();

        ClassInfo classInfo = classInfoService.get(classId);
        if (classInfo != null){
            TeacherDiningRegister teacherDiningRegister = iTeacherDiningRegisterService.getTeacherDiningRegister(teacherId,classId);

            BigInteger teacherDiningRegId = teacherDiningRegister.getId();
            String startStopTime = classInfo.getStartStopTime();
            String diningPlace = classInfo.getDiningPlace();

            Date startT = DateUtil.StringToDate(startTime,"yyyy-MM-dd");
            Date stopT = DateUtil.StringToDate(stopTime,"yyyy-MM-dd");
            int dayCount = (int) ((stopT.getTime() - startT.getTime())/(1000*3600*24));

            calendarStartTime.setTime(startT);
            calendarStopTime.setTime(stopT);

            //循环对日期内进行教师就餐初始化
            for (;!calendarStartTime.after(calendarStopTime);calendarStartTime.add(Calendar.DATE,1)){
                TeacherDiningForScene oldTeacherDiningForScene = getTeacherDining(teacherDiningRegId,sdf.format(calendarStartTime.getTime()));
                if (oldTeacherDiningForScene == null){
                    //没有当前时间记录则保存
                    TeacherDiningForScene teacherDiningForScene = new TeacherDiningForScene();
                    teacherDiningForScene.setTeacherDiningRegId(teacherDiningRegId);
                    teacherDiningForScene.setArrangeId(String.valueOf(userId));
                    teacherDiningForScene.setDiningPlace(diningPlace);
                    teacherDiningForScene.setDiningDate(sdf.format(calendarStartTime.getTime()));
//                  如果是首日
                    if (calendarStartTime.getTime().equals(startT)){
                        teacherDiningForScene.setBreakfast(2);
                        teacherDiningForScene.setLunch(1);
                        teacherDiningForScene.setDinner(1);
                    }else
//                  如果是最后一日
                    if (calendarStartTime.getTime().equals(calendarStopTime.getTime())){
                        teacherDiningForScene.setBreakfast(1);
                        teacherDiningForScene.setLunch(1);
                        teacherDiningForScene.setDinner(2);
                    }else{
                        teacherDiningForScene.setBreakfast(1);
                        teacherDiningForScene.setLunch(1);
                        teacherDiningForScene.setDinner(1);
                    }
                    teacherDiningForScene.setRecordTime(sdf.format(new Date()));

                    save(teacherDiningForScene);
                }else{
                    //有当前时间安排记录则修改
                    oldTeacherDiningForScene.setBreakfast(1);
                    oldTeacherDiningForScene.setLunch(1);
                    oldTeacherDiningForScene.setDinner(1);
                    update(oldTeacherDiningForScene);
                }

            }

        }
    }

    @Override
    public TeacherDiningForScene getTeacherDining(BigInteger teacherDiningRegId, String diningDate) {
        return iTeacherDiningForSceneDao.getTeacherDining(teacherDiningRegId,diningDate);
    }

    @Override
    public List<TeacherDiningForScene> getTeacherDining(BigInteger teacherDiningRegId) {
        return iTeacherDiningForSceneDao.getTeacherDining(teacherDiningRegId);
    }

    @Override
    public void deleteTeacherDiningForScene(long teacherDiningRegId) {
        iTeacherDiningForSceneDao.deleteTeacherDiningForScene(teacherDiningRegId);
    }

    @Override
    public TeachSenseDiningDataForDiningStatisticsDTO getTeachDining(String diningPlace, String classId, String startTime, String endTime) {
        return iTeacherDiningForSceneDao.getTeachDining(diningPlace,classId,startTime,endTime);
    }

    @Override
    public TeachSenseDiningDataForDiningStatisticsDTO getTeachFirstDayDining(String diningPlace, String classId, String startTime, String endTime) {
        return iTeacherDiningForSceneDao.getTeachFirstDayDining(diningPlace, classId, startTime, endTime);
    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        return iTeacherDiningForSceneDao.getTeachDiningRecordStatistics(diningPlace,classId,startTime,endTime);
    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachFirstDayDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        return iTeacherDiningForSceneDao.getTeachFirstDayDiningRecordStatistics(diningPlace,classId,startTime,endTime);
    }
}
