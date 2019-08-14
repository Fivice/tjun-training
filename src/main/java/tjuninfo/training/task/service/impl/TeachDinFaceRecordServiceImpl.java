package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.ApiCode;
import tjuninfo.training.task.dao.TeachDinFaceRecordDao;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeachDiningFaceRecord;
import tjuninfo.training.task.entity.TeacherDiningRegister;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.TeachDinFaceRecordService;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/12/7 14:34
 * @Description:
 */
@Service
public class TeachDinFaceRecordServiceImpl extends BaseServiceImpl<TeachDiningFaceRecord> implements TeachDinFaceRecordService {

    private TeachDinFaceRecordDao teachDinFaceRecordDao;
    @Resource
    public void TeachDinFaceRecordDao (TeachDinFaceRecordDao teachDinFaceRecordDao) {
        this.teachDinFaceRecordDao = teachDinFaceRecordDao;
        this.dao = teachDinFaceRecordDao;
    }
    @Resource
    private ClassInfoService classInfoService;

    @Override
    public List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, String month) {
        return teachDinFaceRecordDao.findFaceList(btView,schoolName,classRoom,className,month);
    }

    @Override
    public List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, Integer userId, String month) {
        return teachDinFaceRecordDao.findFaceList(btView,schoolName,classRoom,className,userId,month);
    }

    @Override
    public List<TeachDiningFaceRecordVO> findList(BTView<TeachDiningFaceRecordVO> btView, String teacherId,Integer classId) {
        return teachDinFaceRecordDao.findList(btView,teacherId,classId);
    }

    @Override
    public TeachDiningFaceRecord findByTime(String time, Integer teacher) {
        return teachDinFaceRecordDao.findByTime(time,teacher);
    }

    @Override
    public TeacherDiningRegister findClass(String time, Integer teacher) {
        return teachDinFaceRecordDao.findClass(time ,teacher);
    }

    @Override
    public List<TeachDiningFaceRecord> getTeachDiningFaceRecord(int teacherId, int classId) {
        return teachDinFaceRecordDao.getTeachDiningFaceRecord(teacherId,classId);
    }

    @Override
    public CmsResult saveData(String time, TeacherInfo t, TeachDiningFaceRecord tdfr, TeacherDiningRegister tdr, ClassInfo c) throws ParseException {
        CmsResult cmsResult = null;
        String time2 = time.substring(0, 10);
        //判断餐点
        String timePD12 = time.substring(11, 13);
        time = time.substring(11);
        String zhtimePD = " 15:00:00";//中餐
        String ztimePD = " 10:00:00";//早餐
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        if((dateFormat.parse(time).getTime())>=(dateFormat.parse(zhtimePD).getTime())){//晚餐
            if(tdfr!=null){//该天 此流水号有就餐记录
                if(tdfr.getDinner()==2){//该时间段 未就餐
                    tdfr.setDinner(1);
                    teachDinFaceRecordDao.update(tdfr);//按时间段存储就餐信息
                    cmsResult = new CmsResult(ApiCode.SUCCESS);
                }else {
                    cmsResult = new CmsResult(ApiCode.FAILED_limit);//每人每餐只能刷一次
                }
            }else {//该天 此流水号没有就餐记录
                TeachDiningFaceRecord tdfr2 = new TeachDiningFaceRecord();
                tdfr2.setBreakfast(2);
                tdfr2.setLunch(2);
                tdfr2.setDinner(1);
                tdfr2.setClassId(tdr.getClassId().intValue());
                tdfr2.setDay(time2);
                tdfr2.setDiningPlace(c.getDiningPlace());
                tdfr2.setAuthorizerId(c.getUserId());
                tdfr2.setTeacherId(t.getTiId());
                tdfr2.setAuthorizerId(classInfoService.get(tdr.getClassId().longValue()).getUserId());
                teachDinFaceRecordDao.save(tdfr2);   //按时间段存储就餐信息
                cmsResult = new CmsResult(ApiCode.SUCCESS);
            }
        }else if((dateFormat.parse(time).getTime())>=(dateFormat.parse(ztimePD).getTime())||timePD12.equals("12")){//中餐
            if(tdfr!=null){//该天 此流水号有就餐记录
                if(tdfr.getLunch()==2){//该时间段 未就餐
                    tdfr.setLunch(1);
                    teachDinFaceRecordDao.update(tdfr);//按时间段存储就餐信息
                    cmsResult = new CmsResult(ApiCode.SUCCESS);
                }else {
                    cmsResult = new CmsResult(ApiCode.FAILED_limit);//每人每餐只能刷一次
                }
            }else {//该天 此流水号没有就餐记录
                TeachDiningFaceRecord tdfr2 = new TeachDiningFaceRecord();
                tdfr2.setBreakfast(2);
                tdfr2.setLunch(1);
                tdfr2.setDinner(2);
                tdfr2.setClassId(tdr.getClassId().intValue());
                tdfr2.setDay(time2);
                tdfr2.setDiningPlace(c.getDiningPlace());
                tdfr2.setAuthorizerId(c.getUserId());
                tdfr2.setTeacherId(t.getTiId());
                tdfr2.setAuthorizerId(classInfoService.get(tdr.getClassId().longValue()).getUserId());
                teachDinFaceRecordDao.save(tdfr2);   //按时间段存储就餐信息
                cmsResult = new CmsResult(ApiCode.SUCCESS);
            }
        }else {//早餐
            if(tdfr!=null){//该天 此流水号有就餐记录
                if(tdfr.getBreakfast()==2){//该时间段 未就餐
                    tdfr.setBreakfast(1);
                    teachDinFaceRecordDao.update(tdfr);//按时间段存储就餐信息
                    cmsResult = new CmsResult(ApiCode.SUCCESS);
                }else {
                    cmsResult = new CmsResult(ApiCode.FAILED_limit);//每人每餐只能刷一次
                }
            }else {//该天 此流水号没有就餐记录
                TeachDiningFaceRecord tdfr2 = new TeachDiningFaceRecord();
                tdfr2.setBreakfast(1);
                tdfr2.setLunch(2);
                tdfr2.setDinner(2);
                tdfr2.setClassId(tdr.getClassId().intValue());
                tdfr2.setDay(time2);
                tdfr2.setDiningPlace(c.getDiningPlace());
                tdfr2.setAuthorizerId(c.getUserId());
                tdfr2.setTeacherId(t.getTiId());
                tdfr2.setAuthorizerId(classInfoService.get(tdr.getClassId().longValue()).getUserId());
                teachDinFaceRecordDao.save(tdfr2);   //按时间段存储就餐信息
                cmsResult = new CmsResult(ApiCode.SUCCESS);
            }
        }
        return cmsResult;
    }
}
