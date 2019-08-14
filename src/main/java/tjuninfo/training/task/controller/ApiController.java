package tjuninfo.training.task.controller;


import com.google.common.collect.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.ApiCode;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.vo.DiningRecordVO;
import tjuninfo.training.task.vo.StuDiningRecordVO1;
import tjuninfo.training.task.vo.TeachDiningRecordVO1;
import tjuninfo.training.task.vo.TeachDiningVO;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Api控制器
 * @author
 * @date 2018年11月12日
 */

@Controller
@RequestMapping(value = "/api")
public class ApiController extends BaseController {
    @Resource
    private StudentRecoverService studentRecoverService;
    @Resource
    private RegisterService registerService;
    @Resource
    private ClassDiningService classDiningService;
    @Resource
    private ClassInfoService classInfoService;
    @Resource
    private TeachRecoverService teachRecoverService;
    @Resource
    private TeachDinRecordService teachDinRecordService;
    @Resource
    private TeachDiningService teachDinService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherInfoService teacherInfoService;
    @Resource
    private TeachDinFaceRecordService teachDinFaceRecordService;
    @Resource
    private IOfflineDataService  offlineDataService;
    @Resource
    private IUnitService unitService;
    @Resource
    private StuDinRecordService stuDinRecordService;
    @Resource
    private IBasicParametersService basicParametersService;

    @Resource
    private IFaceDetectionService iFaceDetectionService;
    @RequestMapping(value="/test")
    public void transact1() throws IOException{
        super.writeJSON("数据进来了");
    }

    /**
     * 刷卡
     * @param time
     * @param number
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value="/dining",method= RequestMethod.POST)
    public void dining(String time , String number ) throws IOException, ParseException {
        time = time.trim(); number = number.trim();CmsResult cmsResult = null;
        StringBuffer path = new StringBuffer("http://"+request.getRemoteHost()+":"+request.getServerPort());
        if(time!=null&&!time.equals("")&&!number.equals("")&&number!=null){
            if(!number.substring(0,1).equals("9")){//流水号不以9开头  为学生证
                //1、判断流水号是否无效
                //根据流水号获取学员证（流水号要有唯一性）
                try {
                    StudentCard sc =  studentRecoverService.findCardBynumb(number);
                    //判断流水号是否失效或无效(流水号存在且流水号有绑定学生)
                    if(sc!=null&&!sc.equals("")&&sc.getStudentId()!=null&&!sc.getStudentId().equals("")){
                        Register r = registerService.findRegister(sc.getStudentId(),sc.getNumber(),sc.getRegisterTime());
                        //判断是否有就餐安排
                        if(r.getDining().equals("2")){//1:就餐； 2：不就餐
                            JSONObject object =new JSONObject();
                            object.put("stuName",studentService.get(r.getSiId()).getSiName());
                            super.writeJSON(new CmsResult(ApiCode.FAILED_no,object));
                        }else {
                            //判断餐点
                            Integer dinner= offlineDataService.dinner(time);//1早2中3晚
                            //查找所在班级今天这一餐是否有就餐安排
                           boolean ifDing =  classDiningService.ifDing(String.valueOf(r.getClassId()),time,dinner);
                           if(ifDing){//该餐有安排
                               //判断学员证是否过期；是否有就餐安排ss
                                       //3、每个时间段只能刷一次（早餐：10点之前；中餐：下午3点之前；晚餐：3点之后）
                                       String timePD = time.substring(0,10);
                                       //  查询就餐记录表 判断当天晚餐是否刷过
                                       StuDiningRecord sdr = stuDinRecordService.findByTime(timePD,r.getSiId(),r.getClassId());
                                   if(dinner==3){//晚餐
                                           if(sdr!=null){//该天 此流水号有就餐记录
                                               JSONObject object =new JSONObject();
                                               object.put("stuName",studentService.get(sdr.getStudent()).getSiName());
                                               object.put("className",classInfoService.get(Long.valueOf(r.getClassId())).getClassName());
                                               path = path.append(studentService.get(sdr.getStudent()).getPhoto());
                                               object.put("photo",path.toString());
                                               if(sdr.getDinner()==2){//该时间段 未就餐
                                                   sdr.setDinner(1);
                                                   sdr.setTimeDinner(time);
                                                   stuDinRecordService.update(sdr);//按时间段存储就餐信息
                                                   cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                               }else {
                                                   object.put("time",sdr.getTimeDinner());
                                                   cmsResult = new CmsResult(ApiCode.FAILED_limit,object);//每人每餐只能刷一次
                                               }
                                           }else {//该天 此流水号没有就餐记录
                                               StuDiningRecord sNew = new StuDiningRecord();
                                               sNew.setDinner(1);
                                               sNew.setArea(classInfoService.get(Long.valueOf(r.getClassId())).getDiningPlace());
                                               sNew.setBreakfast(2);
                                               sNew.setLunch(2);
                                               sNew.setClassRoom(r.getClassId());
                                               sNew.setDay(timePD);
                                               sNew.setStudent(r.getSiId());
                                               sNew.setTimeDinner(time);
                                               stuDinRecordService.save(sNew);   //按时间段存储就餐信息
                                               JSONObject object =new JSONObject();
                                               object.put("stuName",studentService.get(r.getSiId()).getSiName());
                                               object.put("className",classInfoService.get(Long.valueOf(r.getClassId())).getClassName());
                                               path = path.append(studentService.get(r.getSiId()).getPhoto());
                                               object.put("photo",path.toString());
                                               cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                           }
                                   }else if(dinner==2){//中餐
                                           //  查询就餐记录表 判断当天中餐是否刷过
                                           if(sdr!=null){//该天 此流水号有就餐记录
                                               JSONObject object =new JSONObject();
                                               object.put("stuName",studentService.get(sdr.getStudent()).getSiName());
                                               object.put("className",classInfoService.get(Long.valueOf(r.getClassId())).getClassName());
                                               path = path.append(studentService.get(sdr.getStudent()).getPhoto());
                                               object.put("photo",path.toString());
                                               if(sdr.getLunch()==2){//该时间段 未就餐
                                                   sdr.setLunch(1);
                                                   sdr.setTimeDinner(time);
                                                   stuDinRecordService.update(sdr);//按时间段存储就餐信息
                                                   cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                               }else {
                                                   object.put("time",sdr.getTimeDinner());
                                                   cmsResult = new CmsResult(ApiCode.FAILED_limit,object);//每人每餐只能刷一次
                                               }
                                           }else {//该天 此流水号没有就餐记录
                                               StuDiningRecord sNew = new StuDiningRecord();
                                               sNew.setDinner(2);
                                               sNew.setArea(classInfoService.get(Long.valueOf(r.getClassId())).getDiningPlace());
                                               sNew.setBreakfast(2);
                                               sNew.setLunch(1);
                                               sNew.setClassRoom(r.getClassId());
                                               sNew.setDay(timePD);
                                               sNew.setStudent(r.getSiId());
                                               sNew.setTimeDinner(time);
                                               stuDinRecordService.save(sNew);   //按时间段存储就餐信息
                                               JSONObject object =new JSONObject();
                                               object.put("stuName",studentService.get(r.getSiId()).getSiName());
                                               object.put("className",classInfoService.get(Long.valueOf(r.getClassId())).getClassName());
                                               path = path.append(studentService.get(r.getSiId()).getPhoto());
                                               object.put("photo",path.toString());
                                               cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                           }
                                   }else if(dinner==1){//早餐
                                           //  查询就餐记录表 判断当天晚餐是否刷过
                                           if(sdr!=null){//该天 此流水号有就餐记录
                                               JSONObject object =new JSONObject();
                                               object.put("stuName",studentService.get(sdr.getStudent()).getSiName());
                                               object.put("className",classInfoService.get(Long.valueOf(r.getClassId())).getClassName());
                                               path = path.append(studentService.get(sdr.getStudent()).getPhoto());
                                               object.put("photo",path.toString());
                                               if(sdr.getBreakfast()==2){//该时间段 未就餐
                                                   sdr.setBreakfast(1);
                                                   sdr.setTimeDinner(time);
                                                   stuDinRecordService.update(sdr);//按时间段存储就餐信息
                                                   cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                               }else {
                                                   object.put("time",sdr.getTimeDinner());
                                                   cmsResult = new CmsResult(ApiCode.FAILED_limit,object);//每人每餐只能刷一次
                                               }
                                           }else {//该天 此流水号没有就餐记录
                                               StuDiningRecord sNew = new StuDiningRecord();
                                               sNew.setDinner(2);
                                               sNew.setArea(classInfoService.get(Long.valueOf(r.getClassId())).getDiningPlace());
                                               sNew.setBreakfast(1);
                                               sNew.setLunch(2);
                                               sNew.setClassRoom(r.getClassId());
                                               sNew.setDay(timePD);
                                               sNew.setStudent(r.getSiId());
                                               sNew.setTimeDinner(time);
                                               stuDinRecordService.save(sNew);   //按时间段存储就餐信息
                                               JSONObject object =new JSONObject();
                                               object.put("stuName",studentService.get(r.getSiId()).getSiName());
                                               object.put("className",classInfoService.get(Long.valueOf(r.getClassId())).getClassName());
                                               path = path.append(studentService.get(r.getSiId()).getPhoto());
                                               object.put("photo",path.toString());
                                               cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                           }
                                       }else {
                                           JSONObject object =new JSONObject();
                                           object.put("stuName",studentService.get(r.getSiId()).getSiName());
                                           cmsResult = new CmsResult(ApiCode.FAILED_no,object);
                                       }
                           }else {
                               JSONObject object =new JSONObject();
                               object.put("stuName",studentService.get(r.getSiId()).getSiName());
                               cmsResult = new CmsResult(ApiCode.FAILED_no,object);//无就餐安排 ifDing为false
                           }
                            super.writeJSON(cmsResult);
                        }
                    }else {
                        super.writeJSON(new CmsResult(ApiCode.FAILED_invalid));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else { //教师刷卡
                //1、判断流水号是否无效
                //根据流水号获取教师证（流水号要有唯一性）
                TeacherCard tc =  teachRecoverService.findBynumber(number);
                //2、判断流水号是否失效或无效(流水号存在且流水号有绑定)
                if(tc!=null&&!tc.equals("")&&tc.getTeacherName()!=null&&!tc.getTeacherName().equals("")&&tc.getTime()!=null&&!tc.getTime().equals("")){//有绑定教师
                    //判断餐点
                    Integer dinner= offlineDataService.dinner(time);//1早2中3晚
                    //查找所在班级今天这一餐是否有就餐安排
                    boolean ifDing =  teachDinService.ifDing(tc.getNumber(),tc.getTeacherName(),tc.getTime(),time,dinner);
                    //查找该班级id是否存在
                    List<TeachDining> list =  teachDinService.findTeachDiningList(tc.getNumber(),tc.getTime());//根据教师流水号及流水号绑定时间查
                    boolean ifExest= classInfoService.ifExest(Long.valueOf(list.get(0).getClassId()));
                    if(ifDing && ifExest){//该餐有就餐安排
                        String timePD = time.substring(0,10);
                        //4、就餐安排是否过期
                        TeachDining td = teachRecoverService.findMax(tc.getNumber(),tc.getTeacherName(),tc.getTime());//查找最后就餐安排日期
                        //  查询就餐记录表 判断当天晚餐是否刷过
                        TeachDiningRecord tdr = teachDinRecordService.findByTime(tc.getTime(),timePD,tc.getNumber(),tc.getTeacherName());
                        if(dinner==3){//晚餐
                            if(tdr!=null){//该天 此流水号有就餐记录
                                JSONObject object =new JSONObject();
                                object.put("tName",tc.getTeacherName());
                                object.put("className",classInfoService.get(Long.valueOf(tdr.getClassRoom())).getClassName());
                                if(tdr.getDinner()==2){//该时间段 未就餐
                                    tdr.setDinner(1);
                                    tdr.setTimeDinner(time);
                                    teachDinRecordService.update(tdr);//按时间段存储就餐信息
                                    cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                }else {
                                    object.put("time",tdr.getTimeDinner());
                                    cmsResult = new CmsResult(ApiCode.FAILED_limit,object);//每人每餐只能刷一次
                                }
                            }else {//该天 此流水号没有就餐记录
                                TeachDiningRecord tNew = new TeachDiningRecord();
                                tNew.setDinner(1);
                                tNew.setArea(classInfoService.get(Long.valueOf(td.getClassId())).getDiningPlace());
                                tNew.setBreakfast(2);
                                tNew.setLunch(2);
                                tNew.setClassRoom(td.getClassId());
                                tNew.setTeacherDay(timePD);
                                tNew.setTeacherName(td.getTeacherName());
                                tNew.setNum(td.getNumber());
//                              tNew.setAuthorizerId(td.getArranger());
                                tNew.setTime(td.getTime());
                                tNew.setTimeDinner(time);
                                tNew.setAuthorizerId(classInfoService.get(Long.valueOf(td.getClassId())).getUserId());
                                teachDinRecordService.save(tNew);   //按时间段存储就餐信息
                                JSONObject object =new JSONObject();
                                object.put("tName",tc.getTeacherName());
                                object.put("className",classInfoService.get(Long.valueOf(td.getClassId())).getClassName());
                                cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                            }
                        }else if(dinner==2){//中餐
                            if(tdr!=null){//该天 此流水号有就餐记录
                                JSONObject object =new JSONObject();
                                object.put("tName",tc.getTeacherName());
                                object.put("className",classInfoService.get(Long.valueOf(tdr.getClassRoom())).getClassName());
                                if(tdr.getLunch()==2){//该时间段 未就餐
                                    tdr.setLunch(1);
                                    tdr.setTimeDinner(time);
                                    teachDinRecordService.update(tdr);//按时间段存储就餐信息
                                    cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                }else {
                                    object.put("time",tdr.getTimeDinner());
                                    cmsResult = new CmsResult(ApiCode.FAILED_limit,object);//每人每餐只能刷一次
                                }
                            }else {//该天 此流水号没有就餐记录
                                TeachDiningRecord tNew = new TeachDiningRecord();
                                tNew.setDinner(2);
                                tNew.setArea(classInfoService.get(Long.valueOf(td.getClassId())).getDiningPlace());
                                tNew.setBreakfast(2);
                                tNew.setLunch(1);
                                tNew.setClassRoom(td.getClassId());
                                tNew.setTeacherDay(timePD);
                                tNew.setTeacherName(td.getTeacherName());
                                tNew.setNum(td.getNumber());
//                              tNew.setAuthorizerId(td.getArranger());
                                tNew.setTime(td.getTime());
                                tNew.setTimeDinner(time);
                                tNew.setAuthorizerId(classInfoService.get(Long.valueOf(td.getClassId())).getUserId());
                                    teachDinRecordService.save(tNew);   //按时间段存储就餐信息
                                JSONObject object =new JSONObject();
                                object.put("tName",tc.getTeacherName());
                                object.put("className",classInfoService.get(Long.valueOf(td.getClassId())).getClassName());
                                cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                            }
                        }else if(dinner==1){//早餐
                            if(tdr!=null){//该天 此流水号有就餐记录
                                JSONObject object =new JSONObject();
                                object.put("tName",tc.getTeacherName());
                                object.put("className",classInfoService.get(Long.valueOf(tdr.getClassRoom())).getClassName());
                                if(tdr.getBreakfast()==2){//该时间段 未就餐
                                    tdr.setBreakfast(1);
                                    tdr.setTimeDinner(time);
                                    teachDinRecordService.update(tdr);//按时间段存储就餐信息
                                    cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                                }else {
                                    object.put("time",tdr.getTimeDinner());
                                    cmsResult = new CmsResult(ApiCode.FAILED_limit,object);//每人每餐只能刷一次
                                }
                            }else {//该天 此流水号没有就餐记录
                                TeachDiningRecord tNew = new TeachDiningRecord();
                                tNew.setDinner(2);
                                tNew.setArea(classInfoService.get(Long.valueOf(td.getClassId())).getDiningPlace());
                                tNew.setBreakfast(1);
                                tNew.setLunch(2);
                                tNew.setClassRoom(td.getClassId());
                                tNew.setTeacherDay(timePD);
                                tNew.setTeacherName(td.getTeacherName());
                                tNew.setNum(td.getNumber());
//                              tNew.setAuthorizerId(td.getArranger());
                                tNew.setTime(td.getTime());
                                tNew.setTimeDinner(time);
                                tNew.setAuthorizerId(classInfoService.get(Long.valueOf(td.getClassId())).getUserId());
                                teachDinRecordService.save(tNew);   //按时间段存储就餐信息
                                JSONObject object =new JSONObject();
                                object.put("tName",tc.getTeacherName());
                                object.put("className",classInfoService.get(Long.valueOf(td.getClassId())).getClassName());
                                cmsResult = new CmsResult(ApiCode.SUCCESS,object);
                            }
                        }
                    }else {
                        JSONObject object =new JSONObject();
                        object.put("tName",tc.getTeacherName());
                        cmsResult = new CmsResult(ApiCode.FAILED_no,object);//无就餐安排  ifDing返回值为false
                    }
                }else {
                    cmsResult=new CmsResult(ApiCode.FAILED_invalid2);
                }
                super.writeJSON(cmsResult);
            }
        }else {
            super.writeJSON(new CmsResult(ApiCode.UNKNOWN_ERROR));
        }
    }

    /**
     * 报名采集人信息(学员)
     * @param deviceSn  设备sn好
     * @param data 对比相关信息
     * @param capPic 对比用户照片(scenePicture )
     * @param facepic 身份证头部照片(IDHeadPicture )
     */
    @RequestMapping(value="/collect",method= RequestMethod.POST)
    public void collect(String deviceSn, String data , MultipartFile capPic, MultipartFile facepic ) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CmsResult cmsResult = null;
        try {
            deviceSn = deviceSn .trim();data =data.trim();
            //存储图片
            String IDHeadfName= null;
            String scenefName=null; //人脸照片上传注释
            if(facepic!=null&&!facepic.equals("")){
                IDHeadfName = studentService.uploadFiles(facepic,"/upload/studentInfo/");//身份证头部照片
            }
            if(capPic!=null&&!capPic.equals("")){
                scenefName = studentService.uploadFiles(capPic,"/upload/studentFace/");//对比用户照片
            }  //人脸照片上传注释
            JSONObject jsonData = JSONObject.fromObject(data);

            //查询学员信息
            if(jsonData.get("idCard")!=null&&!jsonData.get("idCard").equals("")){
                try {
                    Student student = studentService.findByNumber(String.valueOf(jsonData.get("idCard")));
                    if(student==null){//数据库无该学员信息
                        Student stu = new Student();
                        stu.setCreateDate(DateUtil.getDateTime(new Date()));
                        stu.setUpdateDate(DateUtil.getDateTime(new Date()));
                        stu.setEthnicGroup(jsonData.getString("nation"));
                        stu.setPhoneNumber(jsonData.getString("userNumber"));
                        if(jsonData.getString("sex").equals("男")){
                            stu.setSex("0");
                        }else  if(jsonData.getString("sex").equals("女")){
                            stu.setSex("1");
                        }
//                        stu.setSex(jsonData.getString("sex"));//（0：男 1：女）
                        stu.setSiAddress(jsonData.getString("address"));
                        stu.setSiIDNumber(jsonData.getString("idCard"));
                        stu.setSiName(jsonData.getString("name"));
                        stu.setStatus("0");
                        stu.setPhoto(IDHeadfName);
                        stu.setScenePicture(scenefName);//人脸照片上传注释
                        stu.setUpdateBy(deviceSn );
                        stu.setCreateBy(deviceSn );
                        studentService.save(stu);
                    }else{//数据库里已有该学员信息
                        student.setUpdateDate(DateUtil.getDateTime(new Date()));
                        student.setEthnicGroup(jsonData.getString("nation"));
                        student.setPhoneNumber(jsonData.getString("userNumber"));
                        if(jsonData.getString("sex").equals("男")){
                            student.setSex("0");
                        }else  if(jsonData.getString("sex").equals("女")){
                            student.setSex("1");
                        }
                        student.setSiAddress(jsonData.getString("address"));
                        student.setSiName(jsonData.getString("name"));
                        student.setStatus("0");
                        student.setPhoto(IDHeadfName);
                        student.setScenePicture(scenefName);//人脸照片上传注释
                        student.setUpdateBy(deviceSn );
                        studentService.update(student);
                    }
                    //更新FaceDetection对应机器码下的信息
                    String idNumber = (String) jsonData.get("idCard");
                    String[] propName = {"deviceSn"};
                    Object[] propValue = {deviceSn};
                    List list = iFaceDetectionService.queryByProerties(propName,propValue,null,null);
                    if (list.size()>0){
                        FaceDetection faceDetection = (FaceDetection) list.get(0);
                        faceDetection.setIdNumber(idNumber);
                        faceDetection.setUpdateTime(sdf.format(new Date()));
                        iFaceDetectionService.update(faceDetection);
                    }

                    cmsResult = new CmsResult(CommonReturnCode.SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    cmsResult = new CmsResult(CommonReturnCode.FAILED);
                }
            }else {
                cmsResult = new CmsResult(CommonReturnCode.FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cmsResult = new CmsResult(CommonReturnCode.UNKNOWN_ERROR);
        }
        super.writeJSON(cmsResult);

    }
    /**
     * 报名采集人信息(教职工)
     * @param deviceSn  设备sn好
     * @param data 对比相关信息
     * @param capPic 对比用户照片(scenePicture )
     * @param facepic 身份证头部照片(IDHeadPicture )
     */
    @RequestMapping(value="/collectTea",method= RequestMethod.POST)
    public void collectTea(String deviceSn   , String data , MultipartFile capPic, MultipartFile facepic ) throws IOException {
        CmsResult cmsResult = null;
        try {
            deviceSn = deviceSn .trim();
            data=data.trim();
            //存储图片
            String IDHeadfName= null;
            String scenefName=null;  //人脸照片上传注释
            if(facepic!=null&&!facepic.equals("")){
                IDHeadfName = studentService.uploadFiles(facepic,"/upload/teacherInfo/");//身份证头部照片
            }
            if(capPic!=null&&!capPic.equals("")){
                scenefName = studentService.uploadFiles(capPic,"/upload/teacherFace/");//对比用户照片
            } //人脸照片上传注释
            JSONObject jsonData = JSONObject.fromObject(data);
            //查询学员信息
            if(jsonData.get("idCard")!=null&&!jsonData.get("idCard").equals("")){
                try {
                     TeacherInfo tea = teacherInfoService.getBysiIDNumber(String.valueOf(jsonData.get("idCard")),null);
                    if(tea==null){//数据库无该学员信息
                        TeacherInfo t = new TeacherInfo();
                        t.setCreateDate(DateUtil.getDateTime(new Date()));
                        t.setUpdateDate(DateUtil.getDateTime(new Date()));
                        t.setEthnicGroup(jsonData.getString("nation"));
                        t.setPhoneNumber(jsonData.getString("userNumber"));
                        if(jsonData.getString("sex").equals("男")){
                            t.setSex("0");
                        }else  if(jsonData.getString("sex").equals("女")){
                            t.setSex("1");
                        }
//                        t.setSex(jsonData.getString("sex"));//（0：男 1：女）
    //                    t.setSiAddress(jsonData.getString("address"));
                        t.setSiIDNumber(jsonData.getString("idCard"));
                        t.setTiName(jsonData.getString("name"));
    //                    t.setStatus("0");
                        t.setPhoto(IDHeadfName);
                        t.setScenePicture(scenefName);//人脸照片上传注释
                        t.setUpdateBy(deviceSn );
                        t.setCreateBy(deviceSn );
                        teacherInfoService.save(t);
                    }else{//数据库里已有该学员信息
                        tea.setUpdateDate(DateUtil.getDateTime(new Date()));
                        tea.setEthnicGroup(jsonData.getString("nation"));
                        tea.setPhoneNumber(jsonData.getString("userNumber"));
                        if(jsonData.getString("sex").equals("男")){
                            tea.setSex("0");
                        }else  if(jsonData.getString("sex").equals("女")){
                            tea.setSex("1");
                        }
//                        tea.setSex(jsonData.getString("sex"));
                        tea.setTiName(jsonData.getString("name"));
                        tea.setPhoto(IDHeadfName);
                        tea.setScenePicture(scenefName);//人脸照片上传注释
                        tea.setUpdateBy(deviceSn );
                        teacherInfoService.update(tea);
                    }
                    cmsResult = new CmsResult(CommonReturnCode.SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    cmsResult = new CmsResult(CommonReturnCode.FAILED);
                }
            }else {
                cmsResult = new CmsResult(CommonReturnCode.FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cmsResult = new CmsResult(CommonReturnCode.UNKNOWN_ERROR);
        }
        super.writeJSON(cmsResult);

    }
    /**
     * 上传数据（教师和学员信息）到face500
     * @return
     */
    @RequestMapping(value="/download",method= RequestMethod.POST)
    public void download(String deviceSn) throws IOException {
        String data = null;String sex = "";
        JSONArray array = new JSONArray();
        //查询今天有就餐安排的所有学员
        try {
            List<Register> slist = registerService.getTodayDining();
            for(Register r : slist){
                Student stu = studentService.get(r.getSiId());
                JSONObject object =new JSONObject();
                object.put("name",stu.getSiName());
                object.put("idCard",stu.getSiIDNumber());
                object.put("nation",stu.getEthnicGroup());
                if(stu.getScenePicture()!=null&&!stu.getScenePicture().equals("")){
                    StringBuffer path = new StringBuffer("http://"+request.getRemoteHost()+":"+request.getServerPort());
                    path = path.append(stu.getScenePicture());
                    object.put("capPic",path.toString());
                }else {
                    object.put("capPic","");
                }
                if(stu.getSex().equals("1")){//0：男 1：女
                    sex = "女";
                }else if(stu.getSex().equals("0")){//0：男 1：女
                    sex = "男";
                }
                object.put("sex",sex);
                array.add(object);
            }
            //查询今天有就餐安排的所有教职工
            List<TeacherInfo> tlist =  teacherInfoService.getTodayDining();
            for(TeacherInfo t : tlist){
                JSONObject object =new JSONObject();
                object.put("name",t.getTiName());
                object.put("idCard",t.getSiIDNumber());
                object.put("nation",t.getEthnicGroup());
//                StringBuffer path = new StringBuffer("http://"+request.getRemoteHost()+":"+request.getServerPort());
//                path = path.append(t.getScenePicture());
//                object.put("capPic",path.toString());
                if(t.getScenePicture()!=null&&!t.getScenePicture().equals("")){
                    StringBuffer path = new StringBuffer("http://"+request.getRemoteHost()+":"+request.getServerPort());
                    path = path.append(t.getScenePicture());
                    object.put("capPic",path.toString());
                }else {
                    object.put("capPic","");
                }
                if(t.getSex().equals("1")){//0：男 1：女
                    sex = "女";
                }else if(t.getSex().equals("0")){//0：男 1：女
                    sex = "男";
                }
                object.put("sex",sex);
                array.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = array.toString();
    super.writeJSON(data);
    }

    /**
     * 人脸就餐接口
     * @param deviceSn
     * @param time
     * @param idCard
     */
    @RequestMapping(value="/faceDinning",method= RequestMethod.POST)
    public void faceDinning(String deviceSn, String time , String idCard) throws IOException, ParseException {
        deviceSn = deviceSn.trim();idCard = idCard.trim(); time = time.trim();
        CmsResult cmsResult = null;
        String time2  = time.substring(0,10);
        //判断餐点
        Integer dinner= offlineDataService.dinner(time);//1早2中3晚
        //查询当天有就餐安排的学生，并判断idCard是否在其中
        boolean stuIf = studentService.ifExist(time2,idCard,dinner);
        //查询当天有就餐安排的教师，并判断idCard是否在其中
        boolean teaIf = teacherInfoService.ifExist(time2,idCard,dinner);
        if(stuIf){//学生（正常情况下，这一步就能确定该人当天是有就餐安排的）
            try {
                Student s = studentService.getBysiIDNumber(idCard,null);
                ClassInfo c =classInfoService.getClassIfo(s.getSiId(),time2);
                ; //根据时间和学员id拿到学员所在班级id
                //判断该人此餐是否已经吃过
            /*Register r = registerService.findRegisters(String.valueOf(s.getSiId()),String.valueOf(c.getId())).get(0);//查找报名记录
            if(r.getDining().equals("1")){//1:就餐，2：不就餐
            }else {
                cmsResult = new CmsResult(ApiCode.FAILED_no);//无就餐安排
            }*/
                StuDiningRecord sdr = null;  //  查询就餐记录表 判断当天是否刷过
                sdr = stuDinRecordService.findByTime(time2,s.getSiId(),c.getId().intValue());
                cmsResult = stuDinRecordService.saveData(time,s,sdr,c);//按时间点存储人脸就餐数据
            } catch (ParseException e) {
                e.printStackTrace();
                cmsResult = new CmsResult(ApiCode.UNKNOWN_ERROR);
            }
        }else if(teaIf){//教职工（正常情况下，这一步就能确定该人当天是有就餐安排的）
          TeacherInfo t = null;
          TeachDiningFaceRecord tdfr = null;
            TeacherDiningRegister tdr = null;
            try {
                t = teacherInfoService.getBysiIDNumber(idCard,null);//根据身份证查找教师
                tdfr = teachDinFaceRecordService.findByTime(time2,t.getTiId());//在就餐记录中查询当天的就餐情况
                tdr = teachDinFaceRecordService.findClass(time2,t.getTiId());//根据教师id和就餐时间查询班级id
               ClassInfo c = classInfoService.get(tdr.getClassId().longValue());
                cmsResult=teachDinFaceRecordService.saveData(time,t,tdfr,tdr,c);//教师人脸就餐记录保存
            } catch (Exception e) {
                e.printStackTrace();
                cmsResult = new CmsResult(ApiCode.UNKNOWN_ERROR);
            }
        }else {
            cmsResult = new CmsResult(ApiCode.FAILED_no);//无就餐安排
        }
        super.writeJSON(cmsResult);
    }

    /**
     * 接收人脸脱机数据
     * @param deviceSn
     * @param data
     */
    @RequestMapping(value="/offlineData",method= RequestMethod.POST)
    public void offlineData(String deviceSn, String data ) throws ParseException, IOException {
        CmsResult cmsResult = null; deviceSn = deviceSn .trim();data = data.trim();
        JSONArray array = JSONArray.fromObject(data);
        if (array.size()>0){
          try {
            for(Object ob : array){
                JSONObject jo = JSONObject.fromObject(ob);
                String time = jo.getString("time");
                String idCard = jo.getString("idCard");
                String time2  = time.substring(0,10);
                /*//查询当天有就餐安排的学生，并判断idCard是否在其中
                boolean stuIf = studentService.ifExist(time2,idCard);
                //查询当天有就餐安排的教师，并判断idCard是否在其中
                boolean teaIf = teacherInfoService.ifExist(time2,idCard);*/
                //查询学生，并判断idCard是否在其中
                boolean stuIf = studentService.ifExist2(idCard);
                //查询教师，并判断idCard是否在其中
                boolean teaIf = teacherInfoService.ifExist2(idCard);
                //判断餐点
                Integer dinner= offlineDataService.dinner(time);
                if(stuIf){//学生
                    Student s = studentService.getBysiIDNumber(idCard,null);
//                    ClassInfo c =classInfoService.getClassIfo(s.getSiId(),time2);
                    OfflineData o = new OfflineData();
                    o.setName(s.getSiName());
                    o.setStatus(0);//数据存储情况（2.失败、1成功、0未接收）
//                    o.setUnit(unitService.get(s.getSiUnitId()).getAreaName());
                    if(s.getSiUnitId()!=null&&!s.getSiUnitId().equals("")){
                        o.setUnit(unitService.get(s.getSiUnitId()).getAreaName());
                    }else {
                        o.setUnit(s.getUnitName());
                    }
                    o.setTel(s.getPhoneNumber());
                    o.setIdCard(jo.getString("idCard"));
                    o.setTime(dinner);
//                    o.setArea(c.getDiningPlace());
                    o.setDay(time);
                    o.setCategory(2);//类别（1教师、2学员）
                    o.setDeviceSn(deviceSn);
                    offlineDataService.save(o);
                }else if(teaIf){//教师
                    TeacherInfo t = teacherInfoService.getBysiIDNumber(idCard,null);//根据身份证查找教师
                    TeachDiningFaceRecord tdfr = teachDinFaceRecordService.findByTime(time2,t.getTiId());//在就餐记录中查询当天的就餐情况
                    TeacherDiningRegister tdr = teachDinFaceRecordService.findClass(time2,t.getTiId());//根据教师id和就餐时间查询班级id
//                    ClassInfo c = classInfoService.get(tdr.getClassId().longValue());
                    OfflineData o = new OfflineData();
                    o.setName(t.getTiName());
                    o.setStatus(0);//数据存储情况（2.失败、1成功、0未接收）
                    o.setUnit(t.getTiDepartment());
                    o.setTel(t.getPhoneNumber());
                    o.setIdCard(jo.getString("idCard"));
                    o.setTime(dinner);
//                    o.setArea(c.getDiningPlace());
                    o.setDay(time);
                    o.setCategory(1);//类别（1教师、2学员）
                    o.setDeviceSn(deviceSn);
                    offlineDataService.save(o);
                }else {
                    OfflineData o = new OfflineData();
                    o.setStatus(0);//数据存储情况（2.失败、1成功、0未接收）
                    o.setIdCard(jo.getString("idCard"));
                    o.setTime(dinner);
                    o.setDeviceSn(deviceSn);
                    o.setDay(time);
                    o.setCategory(3);//类别（1教师、2学员、3其他）
                    offlineDataService.save(o);
                }
                cmsResult = new CmsResult(ApiCode.SUCCESS_off);
            }
          } catch (Exception e) {
              e.printStackTrace();
              cmsResult = new CmsResult(ApiCode.UNKNOWN_ERROR);
          }
        }else {
            cmsResult = new CmsResult(ApiCode.NO_DATA);//无脱机数据
        }
        super.writeJSON(cmsResult);
    }
    /**
     * 接收(刷卡)脱机数据
     * @param data
     */
    @RequestMapping(value="/offlineCard",method= RequestMethod.POST)
    public void offlineCard(String data ) throws ParseException, IOException {
        CmsResult cmsResult = null;
        try {
            data = data.trim();
            JSONArray array = JSONArray.fromObject(data);
            if (array.size()>0){
                for(Object ob : array) {
                    JSONObject jo = JSONObject.fromObject(ob);
                    String time = jo.getString("time").trim();
                    String number = jo.getString("number").trim();
                    OfflineData o = new OfflineData();
                    o.setDay(time);
                    o.setNumber(number);
                    cmsResult = offlineDataService.offlineSave(number, time);
                    String remarks = cmsResult.getMessage();
                    Integer status = 1;//上传成功
                    if (cmsResult.getCode() == 1 || cmsResult.getCode() == 30) {//1刷卡成功，30每人每餐只能刷一次
                        Object obj = cmsResult.getData();
                        if (!o.getNumber().substring(0, 1).equals("9")) {//流水号不以9开头，为学员证
                            StuDiningRecord sNew = (StuDiningRecord) obj;
                            Student stu = studentService.get(sNew.getStudent());
                            o.setName(stu.getSiName());
                            o.setIdCard(stu.getSiIDNumber());
                            o.setTel(stu.getPhoneNumber());
                            o.setArea(sNew.getArea());
                            if(stu.getSiUnitId()!=null&&!stu.getSiUnitId().equals("")){
                                o.setUnit(unitService.get(stu.getSiUnitId()).getAreaName());
                            }else {
                                o.setUnit(stu.getUnitName());
                            }
                        } else {//教师卡
                            TeachDiningRecord tNew = (TeachDiningRecord) obj;
                            o.setArea(tNew.getArea());
                            o.setName(tNew.getTeacherName());
                        }
                    } else if (cmsResult.getCode() == 20) {//没有就餐安排
                        Object obj = cmsResult.getData();
                        if (!o.getNumber().substring(0, 1).equals("9")) {//流水号不以9开头，为学员证
                            Register r = (Register) obj;
                            Student stu = studentService.get(r.getSiId());
                            o.setName(stu.getSiName());
                            o.setIdCard(stu.getSiIDNumber());
                            o.setTel(stu.getPhoneNumber());
                            if(stu.getSiUnitId()!=null&&!stu.getSiUnitId().equals("")){
                                o.setUnit(unitService.get(stu.getSiUnitId()).getAreaName());
                            }else {
                                o.setUnit(stu.getUnitName());
                            }
                        } else {//教师卡
                            TeacherCard t = (TeacherCard) obj;
                            o.setName(t.getTeacherName());
                        }
                    }
                    if (cmsResult.getCode() != 1) {
                        remarks = "刷卡失败： " + remarks;
                        status = 2;//上传失败
                    }
                    //判断餐点
                    String timePD12 = time.substring(11, 13);
                    time = time.substring(11);
                    String zhtimePD = " 15:00:00";//中餐
                    String ztimePD = " 10:00:00";//早餐
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    if ((dateFormat.parse(time).getTime()) >= (dateFormat.parse(zhtimePD).getTime())) {//晚餐
                        o.setTime(3);//（1早餐；2中餐；3晚餐）
                    } else if ((dateFormat.parse(time).getTime()) >= (dateFormat.parse(ztimePD).getTime()) || timePD12.equals("12")) {//中餐
                        o.setTime(2);
                    } else {
                        o.setTime(1);
                    }
                    o.setStatus(status);
                    o.setRemarks(remarks);
                    o.setUploader("自动上传");
                    offlineDataService.save(o);
                }
                cmsResult  = new CmsResult(ApiCode.SUCCESS_off);
            }else {
                cmsResult = new CmsResult(ApiCode.NO_DATA);//无脱机数据
            }
        } catch (Exception e) {
            e.printStackTrace();
            cmsResult = new CmsResult(ApiCode.UNKNOWN_ERROR);//未知错误
        }
        super.writeJSON(cmsResult);
    }

    /**
     * 获取所有食堂接口
     */
    @RequestMapping(value="/eatPlace",method= RequestMethod.POST)
    public void eatPlace() throws IOException {
        //院校基本参数列表
        List<BasicParameters> basicParameters = basicParametersService.list();
        //获取就餐地点
        String eatPlace = basicParameters.get(0).getEatPlace();
        String[] eatPlaces = eatPlace.split("[，,]");
        super.writeJSON(eatPlaces);
    }

    /**
     * 学员就餐记录接口
     */
    @RequestMapping(value="/studentDiningRecord",method= RequestMethod.POST)
    public void studentDiningRecord(String eatPlace) throws ParseException, IOException {
        eatPlace = eatPlace.trim();
        ClassDining classDining = new ClassDining();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());//获取当前时间（年月日时分秒）
        String nowDate = date.substring(0,10);//获取当前（年月日）
        String nowTime = date.substring(11);//获取当前时间（时分秒）
        String zhtimePD = " 15:00:00";//中餐
        String ztimePD = " 10:00:00";//早餐
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        if ((dateFormat.parse(nowTime).getTime()) >= (dateFormat.parse(zhtimePD).getTime())) {//（1早餐；2中餐；3晚餐）
            classDining.setDinner(1);//晚餐
        } else if ((dateFormat.parse(nowTime).getTime()) >= (dateFormat.parse(ztimePD).getTime()) && (dateFormat.parse(nowTime).getTime()) < (dateFormat.parse(zhtimePD).getTime())) {//中餐
            classDining.setLunch(1);//午餐
      } else {
            classDining.setBreakfast(1);//早餐
        }
        List<StuDiningRecordVO1> stuDiningRecordVO1List = Lists.newArrayList();
        List<TeachDiningRecordVO1> teachDiningRecordVO1List = Lists.newArrayList();
        //根据食堂名称到班级信息表中查找所有班级的信息
        List<ClassInfo> classInfoList = classInfoService.byDiningPlace(eatPlace);
        if(classInfoList.size()>0) {
            List<ClassDining> classDiningList = classDiningService.byTime(nowDate, classDining, classInfoList);
            if (classDiningList.size() > 0) {
                for (ClassDining classDining1 : classDiningList) {
                    StuDiningRecordVO1 stuDiningRecordVO1 = new StuDiningRecordVO1();
                    ClassInfo classInfo = classInfoService.get(Long.parseLong(classDining1.getClassRoom()));
                    stuDiningRecordVO1.setClassName(classInfo.getClassName());//班级名称
                    Long reached = registerService.foodTotalByRegister(classInfo.getId());
                    stuDiningRecordVO1.setReached(reached.intValue());//应到人数
                    //根据班级ID和当前日期到学生就餐记录表中去查实到人数
                    Long arrived = studentRecoverService.byTimeAndClassId(classInfo.getId().intValue(), nowDate, classDining);
                    stuDiningRecordVO1.setArrived(arrived.intValue());//实到人数
                    stuDiningRecordVO1List.add(stuDiningRecordVO1);
                }
            }
            //根据当前系统时间和查询出来的班级id去教师证就餐安排表中查询所有班级的id和就餐次数
            List<TeachDining> teachDiningList = teachDinService.byTime(nowDate, classDining, classInfoList);
            //根据当前系统时间和查询出来的班级id去教师证就餐安排表中查询所有班级的id和就餐次数(并分组)
            List<TeachDiningVO> teachDiningList1 = teachDinService.byTime2(nowDate, classDining, classInfoList);
            if (teachDiningList1.size() > 0) {
                for (TeachDiningVO teachDining : teachDiningList1) {
                    TeachDiningRecordVO1 teachDiningRecordVO1 = new TeachDiningRecordVO1();
                    ClassInfo classInfo1 = classInfoService.get(teachDining.getClassId().longValue());
                    teachDiningRecordVO1.setClassName(classInfo1.getClassName());//班级名称
                    teachDiningRecordVO1.setReached(teachDining.getCountB().intValue());//应到人数
                    //根据班级ID和当前日期到教师证教师就餐记录表中去查实到人数
                    Long arrived = teachRecoverService.byTimeAndClassId(nowDate, classDining, teachDining.getClassId());
                    teachDiningRecordVO1.setArrived(arrived.intValue());//实到人数
                    teachDiningRecordVO1List.add(teachDiningRecordVO1);
                }
            }
        }
        ArrayList<DiningRecordVO> objects = new ArrayList<>();
        DiningRecordVO diningRecordVO = new DiningRecordVO();
        DiningRecordVO diningRecordVO2 = new DiningRecordVO();

        diningRecordVO.setStuDiningRecordVO1(stuDiningRecordVO1List);
        diningRecordVO.setName("学生");
        diningRecordVO2.setStuDiningRecordVO1(teachDiningRecordVO1List);
        diningRecordVO2.setName("老师");
        objects.add(diningRecordVO);
        objects.add(diningRecordVO2);
        super.writeJSON(objects);
    }

    /**
     * 食堂刷卡更包
     * @param number 当前版本号
     */
    public void updateApp (String number){
        CmsResult cmsResult = null;
        File file = new File("/upload/userOperate/tjun-training/");
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null){
            for(File a:files){
                String fName = a.getName();
                //判断是否又高于当前版本的包
              /*  if(fName.substring(0,6).equals(str)){
                    day=fName;
                }*/
            }
        }
    }
}
