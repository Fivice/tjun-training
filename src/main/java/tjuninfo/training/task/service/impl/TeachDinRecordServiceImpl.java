package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ClassInfoDao;
import tjuninfo.training.task.dao.IBasicParametersDao;
import tjuninfo.training.task.dao.TeachDinRecordDao;
import tjuninfo.training.task.dto.TeachDiningRecordForDiningStatisticsDTO;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.TeachDinRecordService;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;
import tjuninfo.training.task.vo.TeachDiningRecordVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/12 11:20
 * @Description:
 */
@Service
public class TeachDinRecordServiceImpl extends BaseServiceImpl<TeachDiningRecord> implements TeachDinRecordService {

    private TeachDinRecordDao teachDinRecordDao;
    private IBasicParametersDao basicParametersDao;
    @Resource
    public void setTeachDinRecordDao (TeachDinRecordDao teachDinRecordDao) {
        this.teachDinRecordDao = teachDinRecordDao;
        this.dao = teachDinRecordDao;
    }
    @Resource
    public void setDao(IBasicParametersDao basicParametersDao) {
        this.basicParametersDao = basicParametersDao;
//        this.dao = teachDinRecordDao;
    }


    @Override
    public List<TeachDiningRecordVO> findSum(String area, Integer classRoom,String className,Integer userId,Integer roleId) {
        List<TeachDiningRecordVO> list = teachDinRecordDao.findSum(area,classRoom,className,userId,roleId);
        return list;
    }

    @Override
    public List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> bt, String area, Integer classRoom, String className,String month) {
        return teachDinRecordDao.findSum(bt,area,classRoom,className,month);
    }

    @Override
    public List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> bt, String area, Integer classRoom, String className, Integer userId,String month) {
        return teachDinRecordDao.findSum(bt,area,classRoom,className,userId,month);
    }

    @Override
    public TeachDiningRecord findByTime(String time,String day, String number, String teacherName) {
        return teachDinRecordDao.findByTime(time,day, number, teacherName);
    }

    @Override
    public List<TeachDiningRecord> findCount(String classId, String type) {
        return teachDinRecordDao.findCount(classId,type);
    }

    @Override
    public List<TeachDiningFaceRecord> findCount2(String classId, String type) {
        return teachDinRecordDao.findCount2(classId,type);
    }

    @Override
    public List<TeachDiningRecord> findList(String num) {
        List<TeachDiningRecord> list = teachDinRecordDao.findAll(num);
        return list;
    }

    @Override
    public List<TeachDiningRecord> findList(BTView<TeachDiningRecord> bt, String num,Integer classId) {
        return teachDinRecordDao.findList(bt,num,classId);
    }

    @Override
    public Integer sumByClassId(Long classId) {
        List<TeachDiningRecord> teachDiningRecordList = teachDinRecordDao.findAllByClassId(classId);
        List<BasicParameters> basicParametersList = basicParametersDao.getAll();
        String eatStandard = basicParametersList.get(0).getEatStandard();
        String[] eatStandards = eatStandard.split("[，,]");
        Integer teacherDinding = 0 ;//教师就餐费用
        //获取就餐标准
        Integer breakfastStandard = Integer.valueOf(eatStandards[0].trim());//早餐标准
        Integer lunchStandard = Integer.valueOf(eatStandards[1].trim());//中餐标准
        Integer dinnerStandard = Integer.valueOf(eatStandards[2].trim());//晚餐标准
        Integer breakfast = 0 ;
        Integer lunch = 0 ;
        Integer dinner = 0 ;
        if(teachDiningRecordList.size()>0){
            for(TeachDiningRecord teachDiningRecord : teachDiningRecordList){
                    if(teachDiningRecord.getBreakfast() == 1){
                        breakfast += teachDiningRecord.getBreakfast();
                    }
                    if(teachDiningRecord.getLunch() == 1){
                        lunch += teachDiningRecord.getLunch();
                    }
                    if(teachDiningRecord.getDinner() == 1){
                        dinner += teachDiningRecord.getDinner();
                }
            }
        }

        teacherDinding = breakfast*breakfastStandard+lunch*lunchStandard+dinner*dinnerStandard;
        return teacherDinding;

    }

    @Override
    public Integer faceByClassId(Long id) {
        List<TeachDiningFaceRecord> teachDiningRecordList = teachDinRecordDao.faceByClassId(id);
        List<BasicParameters> basicParametersList = basicParametersDao.getAll();
        String eatStandard = basicParametersList.get(0).getEatStandard();
        String[] eatStandards = eatStandard.split("[，,]");
        Integer teacherDinding = 0 ;//教师就餐费用
        //获取就餐标准
        Integer breakfastStandard = Integer.valueOf(eatStandards[0].trim());//早餐标准
        Integer lunchStandard = Integer.valueOf(eatStandards[1].trim());//中餐标准
        Integer dinnerStandard = Integer.valueOf(eatStandards[2].trim());//晚餐标准
        Integer breakfast = 0 ;
        Integer lunch = 0 ;
        Integer dinner = 0 ;
        if(teachDiningRecordList.size()>0){
            for(TeachDiningFaceRecord teachDiningRecord : teachDiningRecordList){
                if(teachDiningRecord.getBreakfast() == 1){
                    breakfast += teachDiningRecord.getBreakfast();
                }
                if(teachDiningRecord.getLunch() == 1){
                    lunch += teachDiningRecord.getLunch();
                }
                if(teachDiningRecord.getDinner() == 1){
                    dinner += teachDiningRecord.getDinner();
                }
            }
        }

        teacherDinding = breakfast*breakfastStandard+lunch*lunchStandard+dinner*dinnerStandard;
        return teacherDinding;

    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        return teachDinRecordDao.getTeachDiningRecordStatistics(diningPlace,classId,startTime,endTime);
    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachFirstDayDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        return teachDinRecordDao.getTeachFirstDayDiningRecordStatistics(diningPlace,classId,startTime,endTime);
    }
}
