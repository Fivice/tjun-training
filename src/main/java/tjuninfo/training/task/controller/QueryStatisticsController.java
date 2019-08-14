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
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.IdGen;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.RegisterVo;
import tjuninfo.training.task.vo.StuDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningStatisticsVO;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:31
 * @Description: 查询统计*/
@Controller
@RequestMapping(value = "queryStatistics")
public class QueryStatisticsController extends BaseController {

    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private TrainingTypeService trainingTypeService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private TeachDinRecordService teachDinRecordService;
    @Autowired
    private StudentRecoverService studentRecoverService;
    @Autowired
    private RecordChangeService recordChangeService;
    @Autowired
    private StudentService studentService;
    @Resource
    private IUserRoleService        userRoleService;
    @Resource
    private ISysRoleService sysRoleService;

    /*
     * 查询统计访问页面
     */
    @RequestMapping("/view")
    public String toDining(Model model) {
        String startDay = null;
        String endDay = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar   cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        startDay = df.format(cal_1.getTime());
        cal_1.set(Calendar.DAY_OF_MONTH,cal_1.getActualMaximum(Calendar.DAY_OF_MONTH));//设置为1号,当前日期既为本月第一天
        endDay = df.format(cal_1.getTime());
        String startStopTime = startDay + " 至 " +  endDay ;
        //查找所有培训类型
        List<TrainingType> trainingType = trainingTypeService.findTrainingTypeList();
        model.addAttribute("trainingType",trainingType);
        model.addAttribute("startStopTime",startStopTime);
        return "queryStatistics/queryStatistics_list";
    }

        /*
           查询统计列表
         */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public Object upsDay() {
        String startDay = null;
        String endDay = null;
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        List<UserRole> list = userRoleService.getRoleIdByUserId(getUser().getUserId());
        Integer useId = null;
        for (UserRole l : list){
            SysRole sysRole = sysRoleService.get(l.getRoleId());
            if(sysRole.getRoleValue().equals("班主任")){
                useId = getUser().getUserId();
            }
        }
            String startStopTime = request.getParameter("startStopTime").trim();
            String className = request.getParameter("className").trim();
            String classNumber = request.getParameter("classNumber").trim();
            String trainingType = request.getParameter("trainingType").trim();
            String plan =  request.getParameter("plan").trim();
            String place =  request.getParameter("place").trim();
//        if (startStopTime!=null && !startStopTime.equals("")){
//        startDay = startStopTime.substring(0,10);
//        endDay = startStopTime.substring(13,23);
//        }
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> map1 = Maps.newHashMap();
        //Pages pages = classInfoService.findAllByStartStopTime(pageSize,pageNumber,startDay,endDay,classNumber,className,trainingType,plan,useId,place);
        Pages pages = classInfoService.classList(pageSize,pageNumber,startStopTime,classNumber,className,trainingType,plan,useId,place);
        List<ClassInfo> classInfoList = pages.getResult();
        for (ClassInfo classInfo : classInfoList) {
            Long classId = classInfo.getId();
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的实际报道学生
            Long studentCount = registerService.studentCountByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的交培训费人数
            Long trainingFee = registerService.trainingFeeByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的住宿人数
            Long hotel = registerService.hotelByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生培训费
            Double trainingExpense = registerService.trainingExpenseByRegister(classInfo.getId());
            //根据班级id,查询出该班级学生总的住宿天数
            Double hotelDayCount = registerService.hotelDayCount(classId);

            BigDecimal trainingExpense1 = new BigDecimal("0");
            if(trainingExpense != null){
                trainingExpense1 = new BigDecimal(trainingExpense);
            }
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生住宿费
            Double scaleFee = registerService.scaleFeeTotalByRegister(classId);
            BigDecimal scaleFee1 = new BigDecimal("0");
            if(scaleFee != null){
                scaleFee1 = new BigDecimal(scaleFee);
            }
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生就餐人数
            Long foodTotal = registerService.foodTotalByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生其他费用
            Double otherCharges = registerService.otherChargesByRegister(classInfo.getId());
            BigDecimal otherCharges1 = new BigDecimal("0");
            if(otherCharges != null){
                otherCharges1 = new BigDecimal(otherCharges);
            }
            BigDecimal teacherDinding1 = new BigDecimal("0");
            //教师流水号实际就餐费用
            Integer teacherDinding = teachDinRecordService.sumByClassId(classInfo.getId());
            //教师人脸实际就餐费用
            Integer teacherFaceDinding = teachDinRecordService.faceByClassId(classInfo.getId());
            if(teacherDinding != null && !teacherDinding.equals("") && teacherFaceDinding != null && !teacherFaceDinding.equals("")){
                teacherDinding1 = new BigDecimal(teacherDinding).add(new BigDecimal(teacherFaceDinding));
            }else if (teacherDinding != null && teacherFaceDinding == null){
                teacherDinding1 = new BigDecimal(teacherDinding);
            }else if(teacherDinding == null && teacherFaceDinding != null){
                teacherDinding1 = new BigDecimal(teacherFaceDinding);
            }
            //学生实际就餐费用
            Integer studentDinding = studentRecoverService.sumByClassId(classInfo.getId());
            BigDecimal studentDinding1 = new BigDecimal("0");
            if(studentDinding != null){
                studentDinding1 = new BigDecimal(studentDinding);
            }
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生就餐费
            Double foodTotal2 = registerService.foodTotalByRegister2(classInfo.getId());
            BigDecimal foodTotal3 = new BigDecimal("0");
            if(foodTotal2 != null){
                foodTotal3 = new BigDecimal(foodTotal2);
            }
            List<RecordChange> recordChangeList = recordChangeService.list(classInfo.getId());
            BigDecimal foodTotal4 = new BigDecimal("0");
            for(RecordChange recordChange : recordChangeList){
                if(null !=recordChange.getDiningFeeCollection()){
                    foodTotal3= new BigDecimal(recordChange.getDiningFeeCollection());
                    foodTotal4=foodTotal3;
                }
            }
            //获取结余（学生就餐费前台-学生实际就餐费-教师实际就餐费）
            BigDecimal balance = foodTotal3.subtract(studentDinding1).subtract(teacherDinding1);
            Integer dayNum = 0;
            if(classInfo.getDayNum() !=null){
                dayNum = classInfo.getDayNum();//获取班级培训天数
            }
            Integer studentCountAndDayNum = Math.toIntExact(studentCount * dayNum);//实到人天数
            Integer hotelAndDayNum = Math.toIntExact(hotel * (dayNum - 1));//培训人天数
            Map<String, Object> map = Maps.newHashMap();
            map.put("trainingFee",trainingFee);
            map.put("studentDinding",studentDinding);
            map.put("foodTotal4",foodTotal4);
            map.put("studentCount",studentCount);
            map.put("hotel",hotel);
            map.put("hotelDayCount",hotelDayCount);
            map.put("classInfo", classInfo);
            map.put("studentCountAndDayNum",studentCountAndDayNum);
            map.put("trainingExpense",trainingExpense);
            map.put("hotelAndDayNum",hotelAndDayNum);
            map.put("scaleFee",scaleFee);
            map.put("foodTotal",foodTotal);
            map.put("foodTotal2",foodTotal2);
            map.put("otherCharges",otherCharges);
            map.put("teacherDinding",teacherDinding1);
            map.put("balance",balance);
            mapList.add(map);
        }
        map1.put("rows",  mapList);
        map1.put("total", pages.getTotalResults());
        return map1;

    }

    /**
     * 实际报道人数访问页面
     * @param model
     * @return
     */
    @GetMapping(value = "/form")
    public String form(Model model,@RequestParam(value="id",required=false)String id) {
        //根据班级ID去查找班级所对应的班级所用信息
        ClassInfo classInfoList = classInfoService.get(Long.parseLong(id));
        //班级的开班时间
        String startTime = classInfoList.getStartStopTime().substring(0,10);
        model.addAttribute("startTime",startTime);
        model.addAttribute("classInfoList",classInfoList);
        return "/queryStatistics/register_list";
    }

    /*
           实际报道人数列表
         */
    @RequestMapping(value = "/findRegisterTable")
    @ResponseBody
    public Object findRegisterTable() {
        String classId = request.getParameter("id");
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        Pages page = registerService .findAllByClassId(pageSize,pageNumber,Long.parseLong(classId));
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> map1 = Maps.newHashMap();
        List<Register> registerList = page.getResult();
        for(Register register :registerList ){
            Student student = studentService.get(register.getSiId());
            Map<String, Object> map = Maps.newHashMap();
            map.put("register",register);
            map.put("student",student);
            mapList.add(map);
        }
        map1.put("rows",  mapList);
        map1.put("total", page.getTotalResults());
        return map1;

    }



}
