package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.IdGen;
import tjuninfo.training.task.vo.StuDiningRecordVO;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;
import tjuninfo.training.task.vo.TeachDiningRecordVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:31
 * @Description: 学生就餐统计
 */
@Controller
@RequestMapping(value = "stuDining")
public class StuDinRecordController extends BaseController {

    @Resource
    private StuDinRecordService     stuDinRecordService;
    @Resource
    private RegisterService         registerService;
    @Resource
    private ClassInfoService        ClassInfoService;
    @Autowired
    private IBasicParametersService basicParametersService;
    @Resource
    private IUserRoleService        userRoleService;
    @Resource
    private ISysRoleService         sysRoleService;
    @Autowired
    private IUnitService            unitService;
    @Autowired
    private StudentService          studentService;
    @Resource
    private ClassInfoService        classInfoService;
    @Resource
    private TeachDinRecordService   teachDinRecordService;
    @Resource
    private TeachDinFaceRecordService teachDinFaceRecordService;




    /*
     * 访问页面
     */
    @RequestMapping("/view")
    public String toDining(Model model) {
        List<BasicParameters> basicParameters = basicParametersService.list();//院校基本参数列表
        String eatPlaces = basicParameters.get(0).getEatPlace();//获取就餐地点
        List<String> diningPlaceList = new ArrayList<String>();
        String[] res = eatPlaces.split("[，,]");
        for (int i = 0; i < res.length; i++) {
            String eatPlace = res[i];
            diningPlaceList.add(eatPlace);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String month = sdf.format(date);
        model.addAttribute("month", month);
        model.addAttribute("list", diningPlaceList);
        model.addAttribute("rList", registerService.getAll());
        return "stuDinRecord/stuDinRecord_list";
    }

    /*
       查找学员就餐数据统计
     */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public void upsDay(Model model, BTView<StuDiningRecordVO> btView) throws IOException {
        String schoolName = null;
        Integer classRoom = null;
        String className = null;
        String studentName = null;
        String month = null;
        Integer roleId = null;
        SysUser user = getUser();
        Integer userId = user.getUserId();
        SysRole sysRole = new SysRole();
        try {
            request.setCharacterEncoding("utf-8");
            schoolName = request.getParameter("schoolName").trim();
            className =  request.getParameter("className").trim();
            studentName =  request.getParameter("studentName").trim();
            month =  request.getParameter("month").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String classNum = request.getParameter("classRoom").trim();

        if(classNum==null||classNum==""){
            classRoom = null;
        }else {
            ClassInfo list = ClassInfoService.getByclassNumber(classNum);  //根据传进来的班级编号去班级查找对应的班级id
            if (list != null) {   //不为空就把id带过来
                classRoom = list.getId().intValue();
            }else {
                classRoom = 9999999;  //否则带一个不存在的班级id，防止id为空把所有数据查出
            }
        }

        List<UserRole> roleList = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : roleList) {
            sysRole = sysRoleService.get(l.getRoleId());
            roleId = l.getRoleId().intValue();
        }
        List<StuDiningRecordVO> StuList = null;
        if (!sysRole.getRoleValue().equals("班主任")) {
            StuList = stuDinRecordService.findSum(btView, schoolName, classRoom, className, studentName, month);
        } else {
            StuList = stuDinRecordService.findSum(btView, schoolName, classRoom, className, studentName, userId, month);
        }
        btView.setRows(StuList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }

    /**
     * 查看学员就餐详情
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/form")
    public String form(StuDiningRecordVO stuDiningRecordVO, Model model, @RequestParam(value = "studentId", required = false) Integer studentId, @RequestParam(value = "classId", required = false) Integer classId) {
        model.addAttribute("studentId", studentId);
        model.addAttribute("classId", classId);
        model.addAttribute("studentName",studentService.get(studentId).getSiName());
        model.addAttribute("classInfo", ClassInfoService.get(Long.valueOf(classId)));
        return "/stuDinRecord/stuDinRecord_Form";
    }

    /*
          学员就餐详情数据表
        */
    @RequestMapping(value = "/find")
    @ResponseBody
    public void ups(Model model, @RequestParam(value = "studentId", required = false) Integer studentId, @RequestParam(value = "classId", required = false) Integer classId, BTView<StuDiningRecord> btView) throws IOException {
        int student = studentId;
        List<StuDiningRecord> list = stuDinRecordService.findList(btView, student,classId);
        btView.setRows(list);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }


    /**
     * 查看学员
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/form2")
    public String form(Student student, Model model) {
        String query = request.getParameter("query");
        if (query != null && !query.equals("")) {
            model.addAttribute("query", query);
        }
        String id = request.getParameter("id");
        if (id != null && !id.equals("")) {
            student = studentService.get(Integer.parseInt(id));
            Integer unitId = student.getSiUnitId();
            if (null != unitId) {
                Unit unit = unitService.get(unitId);
                model.addAttribute("unit", unit);
            }
        }
        model.addAttribute("student", student);
        return "student/studentForm";
    }



    /****教师就餐记录（流水号）****/

    /*
           查找学员就餐数据统计
         */
    @RequestMapping(value = "/findTeacherTable")
    @ResponseBody
    public void upsDay1(Model model,BTView<TeachDiningRecordVO> btView)throws IOException {
        String schoolName = null;
        Integer classRoom = null;
        String className = null;
        String month = null;
        Integer roleId = null;
        SysUser user = getUser();
        Integer userId = user.getUserId();
        SysRole sysRole= new SysRole();
        try {
            request.setCharacterEncoding("utf-8");
//            schoolName = new String(request.getParameter("schoolName").getBytes("iso8859-1"),"utf-8").trim();
//            className = new String(request.getParameter("className").getBytes("iso8859-1"),"utf-8").trim();
//            month = new String(request.getParameter("month").getBytes("iso8859-1"),"utf-8").trim();
            schoolName = request.getParameter("schoolName").trim();
            className =  request.getParameter("className").trim();
            month =  request.getParameter("month").trim();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String classNum = request.getParameter("classRoom").trim();


        if(classNum!=null&&classNum!=""){
            ClassInfo list = classInfoService.getByclassNumber(classNum);
            if(list!=null){
                classRoom  = list.getId().intValue();
            }else {
                classRoom = 99999999;
            }//根据传进来的班级编号去班级查找对应的班级id;不为空就把id带过来;否则带一个不存在的班级id，防止id为空把所有数据查出
        }else {
            classRoom = null;
        }
        List<UserRole> roleList = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : roleList){
            sysRole = sysRoleService.get(l.getRoleId());
            roleId = l.getRoleId().intValue();
        }
        List<TeachDiningRecordVO> teachList = null;
        if(!sysRole.getRoleValue().equals("班主任")){
            teachList = teachDinRecordService.findSum(btView,schoolName,classRoom,className,month);
        }else {
            teachList = teachDinRecordService.findSum(btView,schoolName,classRoom,className,userId,month);
        }
        btView.setRows(teachList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }





















    /****教师就餐记录（人脸）****/

    /*
           查找学员就餐数据统计
         */
    @RequestMapping(value = "/findTeacherByFaceTable")
    @ResponseBody
    public void upsDay2(Model model,BTView<TeachDiningFaceRecordVO> btView)throws IOException {
        String schoolName = null;
        Integer classRoom = null;
        String className = null;
        String month = null;
        Integer roleId = null;
        SysUser user = getUser();
        Integer userId = user.getUserId();
        SysRole sysRole= new SysRole();
        try {
            request.setCharacterEncoding("utf-8");
            schoolName = request.getParameter("schoolName").trim();
            className =  request.getParameter("className").trim();
            month =  request.getParameter("month").trim();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String classNum = request.getParameter("classRoom").trim();

        if(classNum!=null&&classNum!=""){
            ClassInfo list = classInfoService.getByclassNumber(classNum);
            if(list!=null){
                classRoom  = list.getId().intValue();
            }else {
                classRoom = 99999999;//根据传进来的班级编号去班级查找对应的班级id;不为空就把id带过来;否则带一个不存在的班级id，防止id为空把所有数据查出
            }
        }else {
            classRoom = null;
        }



        List<UserRole> roleList = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : roleList){
            sysRole = sysRoleService.get(l.getRoleId());
            roleId = l.getRoleId().intValue();
        }
        List<TeachDiningFaceRecordVO> teachList = null;
        if(sysRole.getRoleValue().equals("超级管理员")){
            teachList = teachDinFaceRecordService.findFaceList(btView,schoolName,classRoom,className,month);
        }else {
            teachList = teachDinFaceRecordService.findFaceList(btView,schoolName,classRoom,className,userId,month);
        }
        btView.setRows(teachList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }







}
