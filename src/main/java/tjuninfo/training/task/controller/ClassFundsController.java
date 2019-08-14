package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Pages;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping(value = "classFunds")
public class ClassFundsController extends BaseController {

    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private TrainingTypeService trainingTypeService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private TeachDinRecordService teachDinRecordService;
    @Autowired
    private RecordChangeService recordChangeService;
    @Resource
    private IUserRoleService userRoleService;
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
        return "classFunds/classFunds_list";
    }
    /*
     * 查询统计访问页面
     */
    @RequestMapping("/mobileView")
    public String mobileView(Model model) {
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
        return "classFunds/classFounds_list_mobile";
    }

        /*
           查询统计列表
         */
    @RequestMapping(value = "/findTable",method = RequestMethod.POST)
    @ResponseBody
    public Object upsDay() {
        String startDay = null;
        String endDay = null;
        Integer useId = null;
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        if(getUser()!=null){
            List<UserRole> list = userRoleService.getRoleIdByUserId(getUser().getUserId());

            for (UserRole l : list){
                SysRole sysRole = sysRoleService.get(l.getRoleId());
                if(sysRole.getRoleValue().equals("班主任")){
                    useId = getUser().getUserId();
                }
            }
        }
        String startStopTime =request.getParameter("startStopTime").trim();
        String className = request.getParameter("className").trim();
        String classNumber = request.getParameter("classNumber").trim();
        String  trainingType = request.getParameter("trainingType").trim();
        String plan = request.getParameter("plan").trim();
//        if (startStopTime!=null && !startStopTime.equals("")){
//        startDay = startStopTime.substring(0,10);
//        endDay = startStopTime.substring(13,23);
//        }
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> map1 = Maps.newHashMap();
        //Pages pages = classInfoService.findAllByStartStopTime(pageSize,pageNumber,startDay,endDay,classNumber,className,trainingType,plan,useId,null);
        Pages pages = classInfoService.classList(pageSize,pageNumber,startStopTime,classNumber,className,trainingType,plan,useId,null);
        List<ClassInfo> classInfoList = pages.getResult();
        for (ClassInfo classInfo : classInfoList) {
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的实际报道学生
            Long studentCount = registerService.studentCountByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的交培训费人数
            Long trainingFee = registerService.trainingFeeByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的住宿人数
            Long hotel = registerService.hotelByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生培训费
            Double trainingExpense = registerService.trainingExpenseByRegister(classInfo.getId());
            BigDecimal trainingExpense1 = new BigDecimal("0");
            if(trainingExpense != null){
                trainingExpense1 = new BigDecimal(trainingExpense);
            }
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生住宿费
            Double scaleFee = registerService.scaleFeeTotalByRegister(classInfo.getId());
            BigDecimal scaleFee1 = new BigDecimal("0");
            if(scaleFee != null){
                scaleFee1 = new BigDecimal(scaleFee);
            }
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生就餐人数
            Long foodTotal = registerService.foodTotalByRegister(classInfo.getId());
            //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生就餐费
            Double foodTotal2 = registerService.foodTotalByRegister2(classInfo.getId());
            BigDecimal foodTotal3 = new BigDecimal("0");
            if(foodTotal2 != null){
                foodTotal3 = new BigDecimal(foodTotal2);
            }
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
            //获取结余（住宿费+培训费+其他费用-就餐费-教师就餐费）
            BigDecimal balance =trainingExpense1.add(scaleFee1).add(otherCharges1).subtract(foodTotal3).subtract(teacherDinding1);
            //根据遍历出来的班级，获取班级ID，然后用班级ID到录改表中去查对应的这个班级的统收信息
            List<RecordChange> recordChangeList = recordChangeService.list(classInfo.getId());
            Map<String, Object> map = Maps.newHashMap();
            for(RecordChange recordChange :recordChangeList){
                map.put("recordChange",recordChange);
            }
            Integer dayNum = 0;
            if(classInfo.getDayNum() != null ){
                dayNum = classInfo.getDayNum();//获取班级培训天数
            }
            Integer studentCountAndDayNum = Math.toIntExact(studentCount * dayNum);//实到人天数
            Integer hotelAndDayNum = Math.toIntExact(hotel * (dayNum - 1));//培训人天数
            if(getUser()!=null){
                List<UserRole> userRoles = userRoleService.getRoleIdByUserId(getUser().getUserId());
                for(UserRole userRole : userRoles){
                    map.put("roleValue",sysRoleService.get(userRole.getRoleId()).getRoleValue());
                }
            }else{
                map.put("roleValue","");
            }
            map.put("trainingFee",trainingFee);
            map.put("studentCount",studentCount);
            map.put("hotel",hotel);
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
     * 录改访问页面(修改)
     * @param model
     * @return
     */
    @GetMapping(value = "/recordChange")
    public String form(Model model,@RequestParam(value="id",required=false)String id,@RequestParam(value="classId",required=false)String classId ) {
        //根据班级ID去查找班级所对应的班级所用信息
        ClassInfo classInfoList = classInfoService.get(Long.parseLong(classId));
        RecordChange recordChange = recordChangeService.get(Integer.parseInt(id));
        model.addAttribute("recordChange",recordChange);
        model.addAttribute("classInfoList",classInfoList);
        return "/classFunds/recordChange";
    }

    /**
     * 录改访问页面(新增)
     * @param model
     * @return
     */
    @GetMapping(value = "/recordChange2")
    public String form2(Model model,@RequestParam(value="classId",required=false)String classId ) {
        //根据班级ID去查找班级所对应的班级所用信息
        ClassInfo classInfoList = classInfoService.get(Long.parseLong(classId));
        //初始加载时的录改表都是0
        RecordChange recordChange = new RecordChange();
        recordChange.setClassId(Long.parseLong(classId));
        recordChange.setDiningFeeCollection(0.00);
        recordChange.setHotelExpenseCollection(0.00);
        recordChange.setTrainingFeeCollection(0.00);
        recordChange.setOtherExpensesCollection(0.00);
        model.addAttribute("classInfoList",classInfoList);
        model.addAttribute("recordChange",recordChange);
        return "/classFunds/recordChange";
    }

    /**
     * 添加/修改数据
     * @param recordChange
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
    public Object save(RecordChange recordChange){
        if(null != recordChange.getId()){
            recordChangeService.update(recordChange);
        }else {
            recordChangeService.save(recordChange);
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /**
     * 根据班级ID查看详情
     * @param model
     * @return
     */
    @GetMapping(value = "/details")
    public String details(Model model,@RequestParam(value="id",required=false)String id) {
        ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
        //根据班级ID去查找班级所对应的班级所用信息
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的实际报道学生
        Long studentCount = registerService.studentCountByRegister(Long.parseLong(id));
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的交培训费人数
        Long trainingFee = registerService.trainingFeeByRegister(Long.parseLong(id));
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的住宿人数
        Long hotel = registerService.hotelByRegister(Long.parseLong(id));
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生培训费
        Double trainingExpense = registerService.trainingExpenseByRegister(Long.parseLong(id));
        BigDecimal trainingExpense1 = new BigDecimal("0");
        if(trainingExpense != null){
            trainingExpense1 = new BigDecimal(trainingExpense);
        }
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生住宿费
        Double scaleFee = registerService.scaleFeeTotalByRegister(Long.parseLong(id));
        BigDecimal scaleFee1 = new BigDecimal("0");
        if(scaleFee != null){
            scaleFee1 = new BigDecimal(scaleFee);
        }
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生就餐人数
        Long foodTotal = registerService.foodTotalByRegister(Long.parseLong(id));
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生就餐费
        Double foodTotal2 = registerService.foodTotalByRegister2(Long.parseLong(id));
        BigDecimal foodTotal3 = new BigDecimal("0");
        if(foodTotal2 != null){
            foodTotal3 = new BigDecimal(foodTotal2);
        }
        //根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的学生其他费用
        Double otherCharges = registerService.otherChargesByRegister(Long.parseLong(id));
        BigDecimal otherCharges1 = new BigDecimal("0");
        if(otherCharges != null){
            otherCharges1 = new BigDecimal(otherCharges);
        }
        //教师实际就餐费用
        Integer teacherDinding = teachDinRecordService.sumByClassId(Long.parseLong(id));
        BigDecimal teacherDinding1 = new BigDecimal("0");
        if(otherCharges != null){
            teacherDinding1 = new BigDecimal(teacherDinding);
        }
        //获取结余（住宿费+培训费+其他费用-就餐费-教师就餐费）
        BigDecimal balance =trainingExpense1.add(scaleFee1).add(otherCharges1).subtract(foodTotal3).subtract(teacherDinding1);
        //根据遍历出来的班级，获取班级ID，然后用班级ID到录改表中去查对应的这个班级的统收信息
        List<RecordChange> recordChangeList = recordChangeService.list(Long.parseLong(id));
        for(RecordChange recordChange :recordChangeList){
            model.addAttribute("recordChange",recordChange);
        }
        Integer dayNum = 0;
        if(classInfo.getDayNum() != null){
            dayNum = classInfo.getDayNum();//获取班级培训天数
        }
        Integer studentCountAndDayNum = Math.toIntExact(studentCount * dayNum);//实到人天数
        Integer hotelAndDayNum = Math.toIntExact(hotel * (dayNum - 1));//培训人天数
        model.addAttribute("trainingFee",trainingFee);
        model.addAttribute("studentCount",studentCount);
        model.addAttribute("hotel",hotel);
        model.addAttribute("studentCountAndDayNum",studentCountAndDayNum);
        model.addAttribute("trainingExpense",trainingExpense);
        model.addAttribute("hotelAndDayNum",hotelAndDayNum);
        model.addAttribute("scaleFee",scaleFee);
        model.addAttribute("foodTotal",foodTotal);
        model.addAttribute("foodTotal2",foodTotal2);
        model.addAttribute("otherCharges",otherCharges);
        model.addAttribute("teacherDinding",teacherDinding);
//        map.put("balance",balance);
        model.addAttribute("classInfo",classInfo);
        return "/classFunds/details";
    }



}
