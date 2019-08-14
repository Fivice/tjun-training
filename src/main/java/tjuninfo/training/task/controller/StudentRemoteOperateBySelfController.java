package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.constant.SignUpManagerConstantCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.service.impl.StudentRemoteOperateBySelfServiceImpl;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.StudentEvaluationStatusVO;
import tjuninfo.training.task.vo.StudentInfoForRegisterBySelfVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学生自主报名页控制
 * @author Fivice
 * @date 2018年11月16日14:59:30
 */
@Controller
@RequestMapping(value = "/remoteOperate")
public class StudentRemoteOperateBySelfController extends BaseController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ISignUpManagerService iSignUpManagerService;
    @Autowired
    private IBaseService<Student> studentIBaseService;
    @Autowired
    private IReceiptFormService iReceiptFormService;
    @Autowired
    private IStudentRemoteOperateBySelfService iStudentRemoteOperateBySelfService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private ClassDiningService classDiningService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private SchedulingService schedulingService;
    @Autowired
    private EvaluateProjectService evaProService;
    @Autowired
    private SubjTeachEvaluateService subjTeachService;
    @Autowired
    private EvaluateScoreService evaScoService;
    @Autowired
    private SubjEvaluateService subjEvaService;

    //学生自己提交报名信息前查询
    @RequestMapping(value = "/registerBySelf")
    @ResponseBody
    public Object registerBySelf(){
        JSONObject jsonObject = new JSONObject();
        StudentInfoForRegisterBySelfVO studentInfoForRegisterBySelfVO = new StudentInfoForRegisterBySelfVO();
        String classId = request.getParameter("classId");
        String idNumber = request.getParameter("idNumber");
        //根据身份证号查找学生信息
        Student student = studentService.findByNumber(idNumber);
        boolean isreg = false;//报到状态
        boolean isSign = false;//报名状态
        if (student != null) {
            long count = iReceiptFormService.getReceiptFormCount(Long.parseLong(classId),student.getSiId());
            isSign = count>0;
            isreg = iSignUpManagerService.judgeStudentIdAndClassIdInRegister(student.getSiId(), Long.parseLong(classId));
            studentInfoForRegisterBySelfVO.setStudent(student);
            if (isreg){
                studentInfoForRegisterBySelfVO.setRegStatus(SignUpManagerConstantCode.IS_REGISTER);
            }else {
                if (isSign){
                    studentInfoForRegisterBySelfVO.setRegStatus(SignUpManagerConstantCode.IS_SIGN);
                }else {
                    studentInfoForRegisterBySelfVO.setRegStatus(SignUpManagerConstantCode.IS_NOT_SIGN);
                }
            }
//            studentInfoForRegisterBySelfVO.setRegStatus(isSign? SignUpManagerConstantCode.IS_REGISTER:SignUpManagerConstantCode.IS_NOT_REGISTER);
            jsonObject.put("studentInfo",studentInfoForRegisterBySelfVO);
        }else{
            jsonObject.put("studentInfo",null);
        }
        return jsonObject;
    }

    @PostMapping(value = "/updateBySelf")
    @ResponseBody
    public Object updateBySelf(){
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        String classId = request.getParameter("classId");
        String siId = request.getParameter("siId");
        String siName = request.getParameter("siName");
        String siPhone = request.getParameter("siPhone");
        String siIdNumber = request.getParameter("siIdNumber");
        String stuNumber = request.getParameter("stuNumber");
        String departmentName = request.getParameter("departmentName");
        String email = request.getParameter("email");
        String regStatus =request.getParameter("regStatus");
        String nation = request.getParameter("nation");
        String firstUnitSelect = request.getParameter("firstUnitSelect");
        String nextUnitSelect = request.getParameter("nextUnitSelect");
        Integer unitId = iStudentRemoteOperateBySelfService.getUnitFromSelect(firstUnitSelect,nextUnitSelect);
        int nationId = Integer.parseInt(nation);
        //学生id与身份证从数据库获取的学生Id一致性判断
        if (studentService.findByNumber(siIdNumber).getSiId() != Integer.parseInt(siId)){
            jsonObject.put("message","身份信息与数据库匹配失败");
            return jsonObject;
        }else if (("未报名").equals(regStatus)){
            jsonObject.put("message","未报名不得修改信息,或联系管理员修改");
            return jsonObject;
        }else {
            Student student = iSignUpManagerService.getStudentInfo(Integer.parseInt(siId));
            student = iStudentRemoteOperateBySelfService.studentInfoToPackaging(student,siName,siPhone,siIdNumber,email,siName,unitId,stuNumber,departmentName,nationId);

            //更新学生信息
            studentIBaseService.update(student);
            jsonObject.put("message","修改信息成功");
        }

        return  jsonObject;
    }
    /**
     * 学生自主报名保存或更新注册信息
     * @return
     */
    @PostMapping(value = "/registerInfo")
    @ResponseBody
    public Object registerInfo(){
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        String classId = request.getParameter("classId");
        String siName = request.getParameter("siName");
        String siPhone = request.getParameter("siPhone");
        String siIdNumber = request.getParameter("siIdNumber");
        String stuNumber = request.getParameter("stuNumber");
        String email = request.getParameter("email");
        String regStatus = request.getParameter("regStatus");
        String departmentName = request.getParameter("departmentName");
        String nation = request.getParameter("nation");
        String firstUnitSelect = request.getParameter("firstUnitSelect");
        String nextUnitSelect = request.getParameter("nextUnitSelect");
        Integer unitId = iStudentRemoteOperateBySelfService.getUnitFromSelect(firstUnitSelect,nextUnitSelect);
        Integer siId = null;
        boolean f = StringUtils.isNotBlank(nation);
        nation = (!f)?"1":nation;
        int nationId =Integer.parseInt(nation);


        //如果学生信息表里有这个身份证号则查询对应的学生Id
        if(studentService.findByNumber(siIdNumber)!=null){
            siId = studentService.findByNumber(siIdNumber).getSiId();
        }
        //如果从前台传过来的报名状态不是未报名则提示用户使用修改功能键
        if (!("未报名").equals(regStatus)){
            jsonObject.put("message","已经报过名,修改信息请使用修改功能");
            return jsonObject;
        }
        if (("").equals(classId)||classId == null){
            jsonObject.put("message","班级信息失效请重新扫描二维码");
            return jsonObject;
        }

        if (siId==null){
            //学生表里没这个人，注册学员信息和报名
            Student newStudent = new Student();
            newStudent = iStudentRemoteOperateBySelfService.studentInfoToPackaging(newStudent,siName,siPhone,siIdNumber,email,siName,unitId,stuNumber,departmentName,nationId);

            //更新学生表
            studentIBaseService.save(newStudent);
            //拿到学生id
            Integer newId = studentService.findByNumber(siIdNumber).getSiId();
            //register报名
//            iSignUpManagerService.saveRegisterInfo(String.valueOf(newId),classId);
            // 回执单表信息添加

            ReceiptForm receiptForm = new ReceiptForm();
            receiptForm.setSiId(newId);
            Integer v = Integer.parseInt(classId);
            receiptForm.setClassId(v);
            receiptForm.setReportTime(df.format(new Date()));
            try {
                //这里会莫名其妙结束，跳不出来，但是功能却是正常实现了。所以出此下策

                iReceiptFormService.saveReceiptForm(receiptForm);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                jsonObject.put("message","注册报名成功");
            }
            return jsonObject;
        }else{
            //报名
//            iSignUpManagerService.saveRegisterInfo(String.valueOf(siId),classId);
            //生成回执单信息

            ReceiptForm receiptForm = new ReceiptForm();
            receiptForm.setSiId(siId);
            receiptForm.setClassId(Integer.parseInt(classId));
            receiptForm.setReportTime(df.format(new Date()));
            try {
            //这里会莫名其妙结束，跳不出来，但是功能却是正常实现了。所以出此下策

                iReceiptFormService.saveReceiptForm(receiptForm);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                jsonObject.put("message","报名成功");
            }
            return jsonObject;
        }


    }

    /**
     * 学生自主更新信息
     * @return
     */
    @PostMapping(value = "/studentInfoSave")
    @ResponseBody
    public Object studentInfoSave(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("classId");
        String siName = request.getParameter("siName");
        String siPhone = request.getParameter("siPhone");
        String siId = request.getParameter("siId");
        String stuNumber = request.getParameter("stuNumber");
        String email = request.getParameter("email");
        String nationId =request.getParameter("nation");
        String firstUnitSelect = request.getParameter("firstUnitSelect");
        String nextUnitSelect = request.getParameter("nextUnitSelect");

        String nationValue = iStudentRemoteOperateBySelfService.getNation(Integer.parseInt(nationId));
        //获取单位id
        Integer unitId = iStudentRemoteOperateBySelfService.getUnitFromSelect(firstUnitSelect,nextUnitSelect);

        Student student = iSignUpManagerService.getStudentInfo(Integer.parseInt(siId));
        student.setSiName(siName);
        student.setPhoneNumber(siPhone);
//        student.setSiIDNumber(siIdNumber);
        student.setSiNumber(stuNumber);
        student.setEthnicGroup(nationValue);
        student.setEmail(email);
        student.setUpdateBy(siName);
        student.setSiUnitId(unitId);
        student.setUpdateDate(df.format(new Date()));

        //更新学生信息
        studentIBaseService.update(student);

        jsonObject.put("message","success");
        return jsonObject;
    }
    //单位分级信息返回
    @PostMapping(value = "/unitGradList")
    @ResponseBody
    public List unitGradList(){
        return iSignUpManagerService.getUnitGradList();
    }

    //学员培训记录
    @PostMapping(value = "/studentTrainingHistory")
    @ResponseBody
    public List studentTrainingHistory(){
        String siIdNumber = request.getParameter("siIdNumber");
        Student student = studentService.findByNumber(siIdNumber);
        if (student!=null){
            return iSignUpManagerService.getStudentTrainingHistory(student.getSiId());
        }

        return null;
    }

    /**
     * 响应就餐记录查询
     * @param model
     * @return
     */
    @GetMapping(value = "/studentDiningInfo")
    @ResponseBody
    public Object studentDiningInfo(Model model) {
        String classId = request.getParameter("id");
        String pageSize = request.getParameter("pageSize");
        String pageNumber = request.getParameter("pageNumber");
        ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));
        //根据班级ID进行查询分页
        Pages pages = classDiningService.findClassDiningList(Integer.parseInt(pageSize),Integer.parseInt(pageNumber),classId);
        List<ClassDining> ClassDiningList = pages.getResult();
        Map<String, Object> map = Maps.newHashMap();
        map.put("rows",  pages.getResult());
        map.put("total", pages.getTotalResults());
        return map;
    }

    @GetMapping(value = "/classScheduling")
    @ResponseBody
    public List classScheduling(Model model){
        String classId = request.getParameter("id");
        List schedulingList = iSignUpManagerService.getClassSchedulingList(classId);
        return schedulingList;
    }

    /**
     * 返回报名信息
     * @return
     */
    @GetMapping(value = "/getStudentEvaluateStatus")
    @ResponseBody
    public Object getStudentEvaluateStatus(){
        JSONObject jsonObject = new JSONObject();
        String siId = request.getParameter("siId");
        String classId = request.getParameter("classId");

        Register register = registerService.getStuRegister(Integer.parseInt(siId),Long.parseLong(classId));
        jsonObject.put("message",register.getStatus());
        return jsonObject;
    }

    /**
     * 获取民族列表json
     * @return
     */

    @GetMapping(value = "/formNationList")
    @ResponseBody
    public List formNationList(){
        List list = new ArrayList();
        list = iStudentRemoteOperateBySelfService.formNationList();
        return list;
    }

    /**
     * 判断学员是否有评价资格，并返回信息
     * @return
     */
    @GetMapping(value = "/judgeStudentEvaluationQualification")
    @ResponseBody
    public Object judgeStudentEvaluationQualification(){
        String idNumber = request.getParameter("idNumber");
        String classId = request.getParameter("classId");
        Student student = studentService.findByNumber(idNumber);
        Register register = null;
        StudentEvaluationStatusVO studentEvaluationStatusVO = new StudentEvaluationStatusVO();
        if (student !=null){
            int siId = student.getSiId();
            String[] propName = {"siId","classId"};
            Object[] propValue = {siId,Integer.parseInt(classId)};
            List registers =  registerService.queryByProerties(propName,propValue,null,null);
            if (registers.size() == 0){
                return studentEvaluationStatusVO;
            }
            ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));
            register = (Register) registers.get(0);
            studentEvaluationStatusVO.setClassInfo(classInfo);
            studentEvaluationStatusVO.setRegister(register);

            return studentEvaluationStatusVO;
        }else{
            //没有学员信息
            return studentEvaluationStatusVO;
        }
    }

    @GetMapping(value = "/judgeView")
    public String judgeView(Model model){

        String classId = request.getParameter("classId");
        String studentId = request.getParameter("siId");


        List<Register> regList= registerService.findRegisters(studentId,classId);
        System.out.println(regList.get(0).getId());
        model.addAttribute("regId", regList.get(0).getId());//将登记id传到页面

        ClassInfo classInfo = classInfoService.get(Integer.valueOf(classId).longValue());
        model.addAttribute("classInfo", classInfo);//将班级信息传到页面

        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<EvaluateProject> evaList = evaProService.findAll();
        List typeList = new ArrayList();

        //遍历evalist，获取typeList
        for (EvaluateProject evaProject:evaList) {
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("evaProject",evaProject);
            mapList.add(map1);
            if(evaProject.getLargeClass() == 1){
                typeList.add(evaProject.getType());
            }
            if(evaProject.getLargeClass() == 2){
                typeList.add(evaProject.getType());
            }
            if(evaProject.getLargeClass() == 3){
                typeList.add(evaProject.getType());
            }
            if(evaProject.getLargeClass() == 4){
                typeList.add(evaProject.getType());
            }
            if(evaProject.getLargeClass() == 5){
                typeList.add(evaProject.getType());
            }
            if(evaProject.getLargeClass() == 6){
                typeList.add(evaProject.getType());
            }
            if(evaProject.getLargeClass() == 7){
                typeList.add(evaProject.getType());
            }
            if(evaProject.getLargeClass() == 8){
                typeList.add(evaProject.getType());
            }
        }
        iStudentRemoteOperateBySelfService.removeDuplicate(typeList);//去除list中重复的元素
        model.addAttribute("type1",typeList.get(0));
        model.addAttribute("type2",typeList.get(1));
        model.addAttribute("type3",typeList.get(2));
        model.addAttribute("type4",typeList.get(3));
        model.addAttribute("type5",typeList.get(4));
        model.addAttribute("type6",typeList.get(5));
        model.addAttribute("type7",typeList.get(6));
        model.addAttribute("type8",typeList.get(7));

        model.addAttribute("mapList",mapList);
        model.addAttribute("typeList",typeList);


        //通过班级id获取课程list
        List <Scheduling> evaSubjList = schedulingService.findEvaSchList(classId,1);
        model.addAttribute("evaSubjList",evaSubjList);
        return "/signUpManager/judgeForm";
    }

    @PostMapping(value = "/saveEvaluation")
    @ResponseBody
    public Object saveEvaluation(){
        Integer regId = Integer.parseInt(request.getParameter("id"));
        Register register = registerService.get(regId);
        register.setStatus(1);
        registerService.save(register);
        List<EvaluateProject> evaList = evaProService.findAll();
        for(int i = 0;i<evaList.size();i++){
            EvaluateScore evaluateScore = new EvaluateScore();
            Integer project = Integer.parseInt(request.getParameter("project"+(i+1)));
            String result = request.getParameter("result"+i);
            evaluateScore.setProjectId(project);
            evaluateScore.setResult(result);
            evaluateScore.setRegisterId(regId);

            evaScoService.saveJudge(evaluateScore);
        }

        //通过班级id获取课程list
        String classId = request.getParameter("classId");
        List <Scheduling> evaSubjList = schedulingService.findEvaSchList(classId,1);
        for(int i = 0;i<evaSubjList.size();i++){
            SubjEvaluate subjEvaluate = new SubjEvaluate();
            SubjTeachEvaluate subjTeachEvaluate = new SubjTeachEvaluate();

            Integer project = Integer.parseInt(request.getParameter("pro"+i));

            String result1 = request.getParameter("res"+i);

            String result2 = request.getParameter("resu"+i);

            //保存课程评价
            subjEvaluate.setProjectId(project);
            subjEvaluate.setRegisterId(regId);
            subjEvaluate.setResult(result1);
            subjEvaService.saveSubj(subjEvaluate);

            //保存教师评价
            subjTeachEvaluate.setProjectId(project);
            subjTeachEvaluate.setRegisterId(regId);
            subjTeachEvaluate.setResult(result2);
            subjTeachService.saveTea(subjTeachEvaluate);

        }

        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }
}
