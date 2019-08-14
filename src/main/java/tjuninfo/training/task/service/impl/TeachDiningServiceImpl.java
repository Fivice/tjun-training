package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.dao.TeachDiningDao;
import tjuninfo.training.task.dto.TeachDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.exception.ReturnCode;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.IOfflineDataService;
import tjuninfo.training.task.service.TeachDiningService;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.TeachDiningVO;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/17 11:54
 * @Description:
 */
@Service
public class TeachDiningServiceImpl extends BaseServiceImpl<TeachDining> implements TeachDiningService {

    private TeachDiningDao teachDiningDao;
    @Resource
    public void setDao(TeachDiningDao teachDiningDao) {
        this.teachDiningDao = teachDiningDao;
        this.dao = teachDiningDao;
    }
    @Autowired
    private ClassInfoService classInfoService;
    @Resource
    private IOfflineDataService offlineDataService;
    @Override
    public List<TeachDining> findTeachDiningList(String schoolName,String number,String month,String arranger) {
        List<TeachDining> list = teachDiningDao.findTeachDiningList( schoolName, number, month, arranger);
        return list ;
    }

    @Override
    public List<TeachDining> findTeachDiningList(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger) {
        return teachDiningDao.findTeachDiningList(bt,schoolName,number,month,arranger);
    }


    @Override
    public List<TeachDining> findTeachDiningList1(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger,Integer classId) {
        return teachDiningDao.findTeachDiningList1(bt,schoolName,number,month,arranger,classId);
    }


    @Override
    public List<TeachDining> findTeachDiningList(String number) {
        List<TeachDining> list = teachDiningDao.findTeachDiningList(number);
        return list;
    }

    @Override
    public List<TeachDining> findDiningList(String number) {
        List<TeachDining> list = teachDiningDao.findDiningList(number);
        return list;
    }
    @Override
    public List<TeachDining> findDiningList(BTView<TeachDining> bt, String number,String time) {
        return teachDiningDao.findDiningList(bt,number,time);
    }

    @Override
    public List<TeachDining> findTeachDiningList(String number, String time) {
        return teachDiningDao.findTeachDiningList(number,time);
    }

    @Override
    public boolean ifDing(String number, String teacherName, String time, String day, Integer dinner) {
        return teachDiningDao.ifDing(number,teacherName,time,day,dinner);
    }

    @Override
    public List<TeachDining> byTime(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList) {
        return teachDiningDao.byTime(nowDate,classDining,classInfoList);
    }

    @Override
    public List<TeachDiningVO> byTime2(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList) {
        return teachDiningDao.byTime2(nowDate,classDining,classInfoList);
    }

    @Override
    public List<TeachDining> findTeachDinList(Integer id) {
        List<TeachDining> list = teachDiningDao.findTeachDinList(id);
        return list;
    }

    @Override
    public void saveOrUpdate(TeachDining teachDining) {
        teachDiningDao.saveOrUpdate(teachDining);
    }

    @Override
    public void updateByNumber(String teacherName,Integer classId,String area,String number,String time) {
        teachDiningDao.updateByNumber(teacherName,classId,area,number,time);
    }

    @Override
    public void deleteByNid(String number,String time) {
        teachDiningDao.deleteByNid(number,time);
    }

    @Override
    public void deleteByNumber(String number,String time) {
        teachDiningDao.deleteByNumber(number,time);
    }

    @Override
    public TeachDiningDataForDiningStatisticsDTO getTeachDining(String diningPlace, String classId, String startTime, String endTime) {
        return teachDiningDao.getTeachDining(diningPlace,classId,startTime,endTime);
    }

    @Override
    public TeachDiningDataForDiningStatisticsDTO getTeachFirstDayDining(String diningPlace, String classId, String startTime, String endTime) {
        return teachDiningDao.getTeachFirstDayDining(diningPlace,classId, startTime, endTime);
    }

    @Override
    public List<TeachDining> TeachDiningPrepList(String classNum, String time,Integer b,Integer l,Integer d) {
        return teachDiningDao.TeachDiningPrepList(classNum,time,b,l,d);
    }

    @Override
    public long getCountByClassIdFromTeachDining(long classId,String startStopTime) {
        return teachDiningDao.getCountByClassIdFromTeachDining(classId,startStopTime);
    }

    @Override
    public Pages teachDiningList1(int pageSize, int pageIndex, String schoolName, String number, String month, Integer classId) {
        return teachDiningDao.teachDiningList1(pageSize,pageIndex,schoolName,number,month,classId);
    }

    @Override
    public CmsResult init(String id ,String number,String time) throws ParseException {
        CmsResult cmsResult =  new CmsResult(CommonReturnCode.SUCCESS);
        ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
        teachDiningDao.deleteByNumber(number, time);
        String startTime = classInfo.getStartStopTime().substring(0, 10);
        String endTime = classInfo.getStartStopTime().substring(13, 23);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        String now = sdf.format(date1);
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        //开始时间必须小于结束时间
        Date beginDate = dateFormat1.parse(startTime);
        Date endDate = dateFormat1.parse(endTime);
        Date d = dateFormat1.parse(now);
        Date date = beginDate;
        String time1 = null;

        if (d.getTime() <= date.getTime()) {
            while (!date.equals(endDate)) {
                time1 = dateFormat1.format(date);
                List<TeachDining> teachDiningList = findTeachDiningList(number, time);
                TeachDining teachDining = teachDiningList.get(0);
                if (time1.equals(startTime)) {
                    teachDining.setDay(time1);
                    teachDining.setBreakfast(2);
                    teachDining.setLunch(2);
                    teachDining.setDinner(1);
                    teachDiningDao.save(teachDining);
                } else {
                    teachDining.setDay(time1);
                    teachDining.setBreakfast(1);
                    teachDining.setLunch(1);
                    teachDining.setDinner(1);
                    teachDiningDao.save(teachDining);
                }
                c.setTime(date);
                c.add(Calendar.DATE, 1); // 日期加1天
                date = c.getTime();
            }
            List<TeachDining> teachDiningList = teachDiningDao.findTeachDiningList(number, time);
            TeachDining teachDining = teachDiningList.get(0);
            teachDining.setClassId(classInfo.getId().intValue());
            teachDining.setDay(endTime);
            teachDining.setBreakfast(1);
            teachDining.setLunch(1);
            teachDining.setDinner(2);
            teachDiningDao.save(teachDining);
        } else {
            if (d.getTime() <=endDate.getTime()) {
                while (!d.equals(endDate)) {
                    now = dateFormat1.format(d);
                    List<TeachDining> teachDiningList =findTeachDiningList(number, time);
                    TeachDining teachDining = teachDiningList.get(0);
                    teachDining.setDay(now);
                    //按时间判断早中晚
//                    Integer dinner= offlineDataService.dinner(time);//1早2中3晚
                    teachDining.setBreakfast(1);
                    teachDining.setLunch(1);
                    teachDining.setDinner(1);
                    teachDiningDao.save(teachDining);
                    c.setTime(d);
                    c.add(Calendar.DATE, 1); // 日期加1天
                    d = c.getTime();
                }
                List<TeachDining> teachDiningList = findTeachDiningList(number, time);
                TeachDining teachDining = teachDiningList.get(0);
                teachDining.setClassId(classInfo.getId().intValue());
                teachDining.setDay(endTime);
                teachDining.setBreakfast(1);
                teachDining.setLunch(1);
                teachDining.setDinner(2);
                teachDiningDao.save(teachDining);
            } else {
                cmsResult = new CmsResult(CommonReturnCode.FAILED);
                //JOptionPane.showMessageDialog(null, "初始化时间错误"+"","", JOptionPane.PLAIN_MESSAGE);
            }
        }
        return cmsResult;
    }
}
