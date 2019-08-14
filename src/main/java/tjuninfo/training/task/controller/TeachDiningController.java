package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.ClassInfoForTeaDinListVo;
import tjuninfo.training.task.vo.ClassInfoForTeachProfileVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: win7
 * @Date: 2018/10/17 11:39
 * @Description:教师就餐安排控制层
 */
@Controller
@RequestMapping(value = "teachDin")
public class TeachDiningController extends BaseController {

    @Resource
    private TeachDiningService      teachDiningService;
    @Autowired
    private IBasicParametersService basicParametersService;
    @Autowired
    private ClassInfoService        classInfoService;
    @Resource
    private IUserRoleService        userRoleService;
    @Resource
    private TeachRecoverService     teacherCardService;
    @Resource
    private ISysRoleService         sysRoleService;
    @Autowired
    private IUserRoleService iUserRoleService;
    /*
     * 访问教师就餐管理页面
     */
    @RequestMapping("/view_profile")
    public String toTeachDiningProfile(Model model) {

        return "teachDining/teachDinProfile";
    }

    /**
     * 列表数据(第一层)
     * @param model
     * @return
     */
    @RequestMapping(value = "/findTable_profile")
    @ResponseBody
    public void list(Model model) throws IOException {
        ClassInfoForTeaDinListVo classInfoForTeaDinListVo;
        BTView<ClassInfoForTeachProfileVO> bt = new BTView<>();

        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());

        String classNumber = request.getParameter("classNumber");
        String startStopTime = request.getParameter("startStopTime");
        String className = request.getParameter("className");
        String teacherName = request.getParameter("teacherName");
        String orderName = request.getParameter("orderName");
        String orderBy = request.getParameter("orderBy");
        //查询当前登录用户
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        //当前用户角色
        SysRole sysRole = sysRoleService.get(userRoleService.getUserRoleByUserId(userInfo.getUserId()).getRoleId());
        List list = new ArrayList();

            if(sysRole.getRoleValue()!=null && sysRole.getRoleValue().trim().equals("班主任")){
                classInfoForTeaDinListVo =classInfoService.getTeachDinProfile(pageSize,pageNumber,classNumber,startStopTime,className,teacherName,userInfo.getUserId().toString(),orderName,orderBy);
            }else{
                classInfoForTeaDinListVo =classInfoService.getTeachDinProfile(pageSize,pageNumber,classNumber,startStopTime,className,teacherName,null,orderName,orderBy);
            }

        bt.setRows(classInfoForTeaDinListVo.getTeachProfileVOList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(classInfoForTeaDinListVo.getTotalResults());
        super.writeJSON(bt);
    }

    /*
     * 访问页面（详情页面）
     */
    @RequestMapping("/view1")
    public String toTeachDining1(Model model,@RequestParam(value = "id", required = false) Long id) {
        model.addAttribute("classId", id);
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
        return "teachDining/teachDining_profileList";
    }

    /*
     * 访问页面（详情页面）
     */
    @RequestMapping("/view")
    public String toTeachDining(Model model,@RequestParam(value = "id", required = false) Long id) {
        model.addAttribute("classId", id);
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
        return "teachDining/teachDining_list";
    }

    /**
     * 加载table列表(第二层)
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/findTable1")
    public void list(BTView<TeachDining> btView) throws IOException {
        String schoolName = request.getParameter("schoolName");
        String number = request.getParameter("number");
        String month = request.getParameter("month");
        Integer classId = Integer.valueOf(request.getParameter("classId1"));


        //分页查询
        List<TeachDining> list = teachDiningService.findTeachDiningList1(btView, schoolName, number, month, null, classId);
        for (TeachDining td : list) {
            if (td.getClassId() != null) {
                ClassInfo classInfo = classInfoService.get(td.getClassId().longValue());
                String className = classInfo.getClassName();
                td.setClassName(className);
            }
        }
        btView.setRows(list);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);


    }

    /*
          查找数据表
        */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public void upsDay(Model model, BTView<TeachDining> btView) throws IOException {
        String schoolName = null;
        String number = null;
        String month = null;
        String arranger = null;
        Integer roleId = null;
        SysUser user = getUser();
        Integer userId = user.getUserId();
        SysRole sysRole = new SysRole();
        List<UserRole> list1 = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : list1) {
            sysRole = sysRoleService.get(l.getRoleId());
        }
        if (sysRole.getRoleValue().equals("班主任")) {
            arranger = user.getUserId().toString();
        }
//        try {
//            request.setCharacterEncoding("utf-8");
//            schoolName = new String(request.getParameter("schoolName").getBytes("iso8859-1"),"utf-8").trim();
//            number = new String(request.getParameter("number").getBytes("iso8859-1"),"utf-8").trim();
//            month = new String(request.getParameter("month").getBytes("iso8859-1"),"utf-8").trim();

        schoolName = request.getParameter("schoolName").trim();
        number = request.getParameter("number").trim();
        month = request.getParameter("month").trim();

      /*  } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        //分页查询
        List<TeachDining> list = teachDiningService.findTeachDiningList(btView, schoolName, number, month, arranger);
        for (TeachDining td : list) {
            if (td.getClassId() != null) {
                ClassInfo classInfo = classInfoService.get(td.getClassId().longValue());
                String className = classInfo.getClassName();
                td.setClassName(className);
            }
        }
        btView.setRows(list);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }
    /*
              查找数据表（第二层）
            */
    /*@RequestMapping(value = "/findTable")
    @ResponseBody
    public void upsDay(Model model, BTView<TeachDining> btView) throws IOException {
        String schoolName = null;
        String number = null;
        String month = null;
        String arranger = null;
        Integer classId = null;
        Integer roleId = null;
        SysUser user = getUser();
        Integer userId = user.getUserId();
        SysRole sysRole = new SysRole();
        List<UserRole> list1 = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : list1) {
            sysRole = sysRoleService.get(l.getRoleId());
        }
        if (sysRole.getRoleValue().equals("班主任")) {
            arranger = user.getUserId().toString();
        }
//        try {
//            request.setCharacterEncoding("utf-8");
//            schoolName = new String(request.getParameter("schoolName").getBytes("iso8859-1"),"utf-8").trim();
//            number = new String(request.getParameter("number").getBytes("iso8859-1"),"utf-8").trim();
//            month = new String(request.getParameter("month").getBytes("iso8859-1"),"utf-8").trim();

            schoolName = request.getParameter("schoolName").trim();
            number = request.getParameter("number").trim();
            month = request.getParameter("month").trim();
            classId = Integer.valueOf(request.getParameter("classId1"));

      *//*  } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*//*

        //分页查询
        List<TeachDining> list = teachDiningService.findTeachDiningList(btView, schoolName, number, month, null,classId);
        for (TeachDining td : list) {
            if (td.getClassId() != null) {
                ClassInfo classInfo = classInfoService.get(td.getClassId().longValue());
                String className = classInfo.getClassName();
                td.setClassName(className);
            }
        }
        btView.setRows(list);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }*/


    /**
     * 根据流水号查找对应的教师信息
     */
    @RequestMapping(value = "findCard")
    @ResponseBody
    public Object findCard(Model model, String number) {
        String teacherName = null;
        List<TeacherCard> teacherList = teacherCardService.findNum(number.trim());
        if (teacherList.size() == 0) {
            teacherName = "0";//无对应流水号 赋0
        } else {
            try {
                if (null == teacherList.get(0).getTeacherName() || teacherList.get(0).getTeacherName().equals("")) {
                    teacherName = "1";//有对应的流水号，但教师姓名为空 赋1
                } else {
                    teacherName = teacherList.get(0).getTeacherName();//有对应的流水号，且教师姓名不为空 赋教师姓名
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("teacherName", teacherName);
        return map;
    }

    /*
     * GET 添加页面
     */
    @RequestMapping(value = "/add/view")
    public String getInsertPage(Model model) {
        SysUser user = getUser();
        String userName = user.getUserName();
        Integer userId = null;
        SysRole sysRole = new SysRole();
        List<ClassInfo> list = null;
        userId = user.getUserId();
        List<UserRole> roleList = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : roleList) {
            sysRole = sysRoleService.get(l.getRoleId());
        }
        if (!sysRole.getRoleValue().equals("班主任")) {
//            list = classInfoService.list();
            list = classInfoService.getByUserId(null);
        } else {
            list = classInfoService.getByUserId(userId);
        }
//        List<BasicParameters> basicParameters = basicParametersService.list();//院校基本参数列表
//        String eatPlaces = basicParameters.get(0).getEatPlace();//获取就餐地点
//        List<String> diningPlaceList = new ArrayList<String>();
//        String[] res = eatPlaces.split("[，,]");
//        for (int i = 0; i < res.length; i++) {
//            String eatPlace = res[i];
//            diningPlaceList.add(eatPlace);
//        }
//        model.addAttribute("list",diningPlaceList);
        model.addAttribute("userName", userName);
        model.addAttribute("classNameList", list);
        return "teachDining/teachDining_add";
    }

    /*
     * POST 保存信息
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object insert(TeachDining teachDining) throws ParseException {
        String teacherName = teachDining.getTeacherName();
        String number = teachDining.getNumber();
        teachDining.setTime(DateUtil.getDateTime(new Date()));
        String time = teachDining.getTime();
        SysUser user = getUser();
        Integer userId = user.getUserId();
        teachDining.setArranger(userId.toString());
        teachDiningService.saveOrUpdate(teachDining);
        teacherCardService.updateByNumber(number, teacherName, time);
        CmsResult c = null;
        try {
            c = teachDiningService.init(String.valueOf(teachDining.getClassId()),number,time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c;
    }

    /*
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids", required = false) String ids, @RequestParam(value = "time", required = false) String time) {
        String[] result = ids.split(",");
        for (int i = 0; i < result.length; i++) {
            String number = result[i];
            teachDiningService.deleteByNid(number, time);
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /*
     *  更新页面
     */
    @RequestMapping(value = "/edit")
    public String getUpdatePage(Model model, @RequestParam(value = "number", required = false) String number, @RequestParam(value = "time", required = false) String time) {
        SysUser user = getUser();
        String userName = user.getUserName();
        Integer userId = user.getUserId();
        Integer roleId = null;
        TeachDining teachDining = null;
        List<ClassInfo> list = null;
        SysRole sysRole = new SysRole();
        List<UserRole> roleList = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : roleList) {
            sysRole = sysRoleService.get(l.getRoleId());
        }
        if (!sysRole.getRoleValue().equals("班主任")) {
            list = classInfoService.list();
        } else {
            list = classInfoService.getByUserId(userId);//用户查看相应的信息
        }
        List<TeachDining> teachDiningList = teachDiningService.findTeachDiningList(number, time);
        teachDining = teachDiningList.get(0);
//        List<BasicParameters> basicParameters = basicParametersService.list();//院校基本参数列表
//        String eatPlaces = basicParameters.get(0).getEatPlace();//获取就餐地点
//        List<String> diningPlaceList = new ArrayList<String>();
//        String[] res = eatPlaces.split("[，,]");
//        for (int i = 0; i < res.length; i++) {
//            String eatPlace = res[i];
//            diningPlaceList.add(eatPlace);
//        }
        model.addAttribute("teachDining", teachDining);
        model.addAttribute("classNameList", list);
//        model.addAttribute("list",diningPlaceList);
        model.addAttribute("userName", userName);
        return "teachDining/teachDining_update";
    }

    /*
     *  根据Id来更新对象
     */
    @RequestMapping(value = "/{number}")
    @ResponseBody
    public Object update(TeachDining teachDining, @RequestParam(value = "number", required = false) String number, @RequestParam(value = "time", required = false) String time) {
        String teacherName = teachDining.getTeacherName();
        Integer classId = teachDining.getClassId();
        String area = teachDining.getArea();
        teachDiningService.updateByNumber(teacherName, classId, area, number, time);
        teacherCardService.updateByNumber(number, teacherName, teachDining.getTime());
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    @RequestMapping(value = "/form")
    public String toForm(Model model, @RequestParam(value = "number", required = false) String number, @RequestParam(value = "time", required = false) String time, @RequestParam(value = "classId", required = false) Integer classId) {
        model.addAttribute("time", time);
        model.addAttribute("number", number);
        model.addAttribute("classInfo", classInfoService.get(classId.longValue()));
        return "teachDining/teachDining_Form";
    }

    /*
              查找数据表
            */
    @RequestMapping(value = "/find")
    @ResponseBody
    public void upsDays(Model model, BTView<TeachDining> btView) throws IOException {
        String number = request.getParameter("number").trim();
        String time = request.getParameter("time").trim();
        List<TeachDining> TeachDiningList = teachDiningService.findDiningList(btView, number, time);
        for (TeachDining td : TeachDiningList) {
            if (td.getClassId() != null) {
                ClassInfo classInfo = classInfoService.get(td.getClassId().longValue());
                String className = classInfo.getClassName();
                td.setClassName(className);
            }
        }
        btView.setRows(TeachDiningList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }

    /*
     * GET 添加页面
     */
    @RequestMapping(value = "/create")
    public String getInsertPage(Model model, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "number", required = false) String number, @RequestParam(value = "time", required = false) String time) throws ParseException {
        ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
        model.addAttribute("classInfo", classInfo);
        model.addAttribute("number", number);
        model.addAttribute("time", time);
        String startTime = classInfo.getStartStopTime().substring(0, 10);
        List<TeachDining> TeachDiningList = teachDiningService.findTeachDiningList(number, time);
        TeachDining teachDining = TeachDiningList.get(0);
        if (TeachDiningList.get(TeachDiningList.size() - 1).getDay() != null) {
            startTime = TeachDiningList.get(TeachDiningList.size() - 1).getDay();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date utilDate = simpleDateFormat.parse(startTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(utilDate);//设置起时间
            cal.add(Calendar.DATE, 1);//增加一天  
            startTime = simpleDateFormat.format(cal.getTime());
        } else {
            startTime = startTime;
        }
        model.addAttribute("teachDining", teachDining);
        model.addAttribute("startTime", startTime);
        return "teachDining/teachDining_form_add";
    }

    /*
     * POST 保存信息
     */
    @RequestMapping(value = "/savee")
    @ResponseBody
    public Object insert1(TeachDining teachDining) {
        String time = request.getParameter("time");
        teachDining.setTime(time);
        teachDiningService.saveOrUpdate(teachDining);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /*
     * 初始化
     */
    @RequestMapping(value = "/init")
    @ResponseBody
    public Object init(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "number", required = false) String number, @RequestParam(value = "time", required = false) String time) throws ParseException {
       /* ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
        teachDiningService.deleteByNumber(number, time);
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
                List<TeachDining> teachDiningList = teachDiningService.findTeachDiningList(number, time);
                TeachDining teachDining = teachDiningList.get(0);
                if (time1.equals(startTime)) {
                    teachDining.setDay(time1);
                    teachDining.setBreakfast(2);
                    teachDining.setLunch(1);
                    teachDining.setDinner(1);
                    teachDiningService.save(teachDining);
                } else {
                    teachDining.setDay(time1);
                    teachDining.setBreakfast(1);
                    teachDining.setLunch(1);
                    teachDining.setDinner(1);
                    teachDiningService.save(teachDining);
                }
                c.setTime(date);
                c.add(Calendar.DATE, 1); // 日期加1天
                date = c.getTime();
            }
            List<TeachDining> teachDiningList = teachDiningService.findTeachDiningList(number, time);
            TeachDining teachDining = teachDiningList.get(0);
            teachDining.setClassId(classInfo.getId().intValue());
            teachDining.setDay(endTime);
            teachDining.setBreakfast(1);
            teachDining.setLunch(1);
            teachDining.setDinner(2);
            teachDiningService.save(teachDining);
        } else {
            if (d.getTime() <=endDate.getTime()) {
                while (!d.equals(endDate)) {
                    now = dateFormat1.format(d);
                    List<TeachDining> teachDiningList = teachDiningService.findTeachDiningList(number, time);
                    TeachDining teachDining = teachDiningList.get(0);
                    teachDining.setDay(now);
                    teachDining.setBreakfast(1);
                    teachDining.setLunch(1);
                    teachDining.setDinner(1);
                    teachDiningService.save(teachDining);
                    c.setTime(d);
                    c.add(Calendar.DATE, 1); // 日期加1天
                    d = c.getTime();
                }
                List<TeachDining> teachDiningList = teachDiningService.findTeachDiningList(number, time);
                TeachDining teachDining = teachDiningList.get(0);
                teachDining.setClassId(classInfo.getId().intValue());
                teachDining.setDay(endTime);
                teachDining.setBreakfast(1);
                teachDining.setLunch(1);
                teachDining.setDinner(2);
                teachDiningService.save(teachDining);
            } else {
                return new CmsResult(CommonReturnCode.FAILED, 0);
                //JOptionPane.showMessageDialog(null, "初始化时间错误"+"","", JOptionPane.PLAIN_MESSAGE);
            }
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);*/
        return teachDiningService.init(id,number,time);
    }

    /*
     * 清空
     */
    @RequestMapping(value = "/empty")
    @ResponseBody
    public Object empty(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "number", required = false) String number, @RequestParam(value = "time", required = false) String time) {
        teachDiningService.deleteByNumber(number, time);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /*
     * 删除
     */
    @RequestMapping(value = "/delete1")
    @ResponseBody
    public Object delete1(@RequestParam(value = "ids", required = false) String ids) {
        String[] result = ids.split(",");
        for (int i = 0; i < result.length; i++) {
            int id = Integer.parseInt(result[i]);
            teachDiningService.deleteByPK(id);
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /*
     * GET 更新页面
     */
    @GetMapping(value = "/update1")
    public String getUpdatePage1(Model model, @RequestParam(value = "id", required = false) String id) {
        int teachId = Integer.parseInt(id);
        TeachDining teachDining = teachDiningService.get(teachId);
        model.addAttribute("teachDining", teachDining);
        return "teachDining/teachDining_form_update";
    }

    /*
     * PUT 根据Id来更新对象
     */
    @RequestMapping(value = "/saveTeachDining")
    @ResponseBody
    public Object update(TeachDining teachDining) {
        String time = request.getParameter("time");
        teachDining.setTime(time);
        teachDiningService.update(teachDining);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }


}
