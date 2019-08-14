package tjuninfo.training.task.controller;

import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.constant.SignUpManagerConstantCode;
import tjuninfo.training.task.dao.StudentDao;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 报名信息管理控制层
 * @author wubin
 * @date 2018年10月17号
 */
@Controller
@RequestMapping(value = "/signUpManager")
public class SignUpManagerController extends BaseController {

    private final ISignUpManagerService iSignUpManagerService;
    private final StudentDao studentService;
    private final IBaseService<Student> studentIBaseService;
    private final RegisterService registerService;

    private final IBaseService<Unit> unitIBaseService;

    private final IReceiptFormService iReceiptFormService;
    private final StuDinRecordService stuDinRecordService;

    @Autowired
    private ClassInfoService iClassInfoService;

    @Autowired
    private StudentCardService studentCardService;

    @Autowired
    private ClassDiningService classDiningService;


    @Autowired
    public SignUpManagerController(ISignUpManagerService iSignUpManagerService, StudentDao studentService, IBaseService<Student> iBaseService, RegisterService registerService, IBaseService<Unit> unitIBaseService, IReceiptFormService iReceiptFormService, StuDinRecordService stuDinRecordService) {
        this.iSignUpManagerService = iSignUpManagerService;
        this.studentService = studentService;
        this.studentIBaseService = iBaseService;
        this.registerService = registerService;
        this.unitIBaseService = unitIBaseService;
        this.iReceiptFormService = iReceiptFormService;
        this.stuDinRecordService = stuDinRecordService;
    }

    /**
     * 列表页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/view")
    public String view(Model model) {
        return "/signUpManager/signUpInfoList";
    }

    //返回班级列表
    @PostMapping(value = "/findTable")
    @ResponseBody
    public void list(Model model) throws IOException {


        SignUpManagerListVO signUpManagerListVO;
        BTView<SignUpManagerVO> bt = new BTView<>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String classNumber = request.getParameter("classNumber").trim();
        String startStopTime = request.getParameter("startStopTime");
        String className = request.getParameter("className").trim();
        String teacherName = request.getParameter("teacherName").trim();
        String entryStartTime = request.getParameter("entryStartTime").trim();
        String orderName = request.getParameter("orderName");
        String orderBy = request.getParameter("orderBy");

        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        /*
         * 班主任身份判断
         */
        long roleId = 0;

        UserRole userRole = iSignUpManagerService.getUserRole(userInfo.getUserId());
        roleId = userRole.getRoleId();

        boolean isHeadMaster = iSignUpManagerService.judgeUserIsRole(roleId, SignUpManagerConstantCode.HEAD_MASTER);
        if (isHeadMaster) {
            //是班主任
            signUpManagerListVO = iSignUpManagerService.getClassInfoList(
                    pageSize,
                    pageNumber,
                    classNumber,
                    className,
                    teacherName,
                    startStopTime,
                    userInfo.getUserId(),
                    null,
                    entryStartTime,
                    orderName,
                    orderBy
            );
        } else if ("一般".equals(userInfo.getUserType())) {
            //其他
            signUpManagerListVO = iSignUpManagerService.getClassInfoList(
                    pageSize,
                    pageNumber,
                    classNumber,
                    className,
                    teacherName,
                    startStopTime,
                    0,
                    null,
                    entryStartTime,
                    orderName,
                    orderBy
            );
        } else {
            signUpManagerListVO = iSignUpManagerService.getClassInfoList(
                    pageSize,
                    pageNumber,
                    classNumber,
                    className,
                    teacherName,
                    startStopTime,
                    0,
                    userInfo.getUserType(),
                    entryStartTime,
                    orderName,
                    orderBy
            );
        }

        bt.setRows(signUpManagerListVO.getSignUpManagerVOList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(signUpManagerListVO.getTotalResults());
        super.writeJSON(bt);
    }

    @GetMapping(value = "/signUpStudent")
    public String signUpStudent(Model model) {
        String id = request.getParameter("id");
        //查询当前登录用户
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        request.getSession();
        session.setAttribute("signUpStudentClassId", id);
        ClassInfoVO classInfo = new ClassInfoVO(iSignUpManagerService.getClassInfoByClassId(Integer.parseInt(id)));
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("classId", classInfo.getId());
        model.addAttribute("reportPlace", classInfo.getRegPlace());
        model.addAttribute("className", classInfo.getClassName());
        return "signUpManager/signUpStudentLists";
    }

    @PostMapping(value = "/signUpStudentList")
    @ResponseBody
    public void signUpStudentList(Model model) throws IOException {
        Page page;
        BTView<SignUpStudentVO> bt = new BTView<>();
        //取出查询班级Id、页数、页码
        String id = (String) request.getSession().getAttribute("signUpStudentClassId");
        String siName = request.getParameter("siName").trim();
        String pay = request.getParameter("pay");
        String hotel = request.getParameter("hotel");
        String dining = request.getParameter("dining");
        String unitName = request.getParameter("unitName").trim();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());

        page = iSignUpManagerService.getSinUpStudentList(pageSize, pageNumber, Integer.parseInt(id), siName, unitName, Integer.parseInt(pay), Integer.parseInt(dining), Integer.parseInt(hotel));


        bt.setRows(page.getList());
        bt.setPageSize(page.getPageSize());
        bt.setPageNumber(page.getPageNo());
        bt.setTotal(page.getTotalRecords());
        super.writeJSON(bt);
    }

    /**
     * 报到登记页面
     **/
    @RequestMapping("/form")
    public String form(Model model) throws IOException {

        //查询当前登录用户
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        //
        if (userInfo.getSupType() != null && !userInfo.getSupType().equals("")) {
            model.addAttribute("userType", userInfo.getUserType());
        }
        //前台用户
        if (userInfo.getSupType() != null && userInfo.getSupType().equals("前台")) {
            model.addAttribute("type", userInfo.getUserType());
        }
        return "signUpManager/signUpStudentLists";
    }

    /**
     * 判断是否报道
     *
     * @param model
     * @param studentId
     * @param classId
     * @return
     */
    @PostMapping("/judeStudentHadReg")
    @ResponseBody
    public Object judeStudentHadReg(Model model, @RequestParam(value = "studentId") String studentId, @RequestParam(value = "classId") String classId) {
        JSONObject jsonObject = new JSONObject();
        boolean isReg = iSignUpManagerService.judgeStudentIdAndClassIdInRegister(Integer.parseInt(studentId), Long.parseLong(classId));
        if (isReg) {
            //班级id和学生Id在报名表里同时满足

            jsonObject.put("message", "reg");
            return jsonObject;
        } else {
            //其他情况则重新报名

            jsonObject.put("message", "unReg");
            return jsonObject;
        }


    }


    /**
     * 支付状态修改
     *
     * @param model
     * @return
     */
    @PostMapping("/payConfirm")
    @ResponseBody
    //@Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public Object payConfirm(Model model) {
        String siId = request.getParameter("studentId");
        String pay = request.getParameter("payStatus");
        String trainingExpense = request.getParameter("trainingExpense");
        String otherCharges = request.getParameter("otherCharges");
        String dining = request.getParameter("dining");
        String hotel = request.getParameter("hotel");
        String diningFee = request.getParameter("diningFee");
        String hotelFee = request.getParameter("hotelFee");
        String serialNumber = request.getParameter("serialNumber");

        JSONObject jsonObject = new JSONObject();
        //int serialNumberEditStatus;

        String classId = (String) request.getSession().getAttribute("signUpStudentClassId");
        if ("1".equals(pay)) {
            jsonObject.put("message", "-2");//不做修改
        } else if ("2".equals(pay)) {
            //写入流水号
            //serialNumberEditStatus = iSignUpManagerService.updateStudentCard(serialNumber, siId, classId);

            //if (serialNumberEditStatus == 3||serialNumberEditStatus == 4) {
            //修改支付状态为1(已交状态)
            Register register = registerService.getStuRegister(Integer.parseInt(siId), Long.parseLong(classId));
            register.setDining(dining);
            register.setHotel(Integer.parseInt(hotel));
            registerService.save(register);
            iSignUpManagerService.studentPayConfirm(Integer.parseInt(siId), Long.parseLong(classId), SignUpManagerConstantCode.IS_PAY, Double.parseDouble(trainingExpense), Double.parseDouble(otherCharges), Double.parseDouble(hotelFee), Double.parseDouble(diningFee));
            // }
            jsonObject.put("message", 3);
        }

        return jsonObject;
    }

    /**
     * 计算住宿费和餐费
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/calculatePayInfo")
    @ResponseBody
    public JSONObject calculatePayInfo(Model model) {
        JSONObject jsonObject = new JSONObject();
        String classId = (String) request.getSession().getAttribute("signUpStudentClassId");
        String hotel = request.getParameter("hotelSelect");
        String dining = request.getParameter("diningSelect");
        String siId = request.getParameter("siId");
        if (hotel.equals("") || dining.equals("")) {
            //异常
            jsonObject.put("payInfo", "");
        } else {
            //正常计算流程
            PayInfoForDiningAndHotelChangeVO pay = iSignUpManagerService.calculateDiningAndHotelPayInfo(Long.parseLong(classId), Integer.parseInt(dining), Integer.parseInt(hotel));
            jsonObject.put("payInfo", pay);
        }

        return jsonObject;
    }

    //学生自己报名页面
    @GetMapping(value = "studentRegisterBySelf")
    public String studentRegisterBySelf(Model model) {

        String classId = request.getParameter("classId");
        ClassInfo classInfo = iClassInfoService.get(Long.parseLong(classId));
        List schedulingList = iSignUpManagerService.getClassSchedulingList(classId);
        model.addAttribute("schedulingList", schedulingList);
        model.addAttribute("classInfo", classInfo);
        return "signUpManager/studentRegisterBySelfForm";
    }


    /**
     * 查询验证报名信息
     *
     * @return
     */
    @GetMapping(value = "/signUpByHeadMaster")
    @ResponseBody
    public JSONObject signUpByHeadMaster() {
        JSONObject jsonObject = new JSONObject();
        SignUpByHeadMasterVO signUpByHeadMasterVO = new SignUpByHeadMasterVO();
        String siIdNumber = request.getParameter("idNumber").trim();
        String classId = request.getParameter("classId");
        //查询学员信息
        Student student = studentService.findByNumber(siIdNumber);
        if (student != null) {//查询学员信息不为空

            //查询报名信息
            boolean isReg = iSignUpManagerService.judgeStudentIdAndClassIdInRegister(student.getSiId(), Long.parseLong(classId));
            if (isReg) {//已经在该班级报名
                signUpByHeadMasterVO.setStudent(student);
                signUpByHeadMasterVO.setClassId(Long.parseLong(classId));
                signUpByHeadMasterVO.setReportTime(iSignUpManagerService.getRegisterInfo(Long.parseLong(classId)).getReportTime());
                signUpByHeadMasterVO.setStatus(1);
                jsonObject.put("signUpInfo", signUpByHeadMasterVO);
            } else {//有学员信息，但是没有报名
                signUpByHeadMasterVO.setStudent(student);
                signUpByHeadMasterVO.setClassId(Long.parseLong(classId));
                signUpByHeadMasterVO.setReportTime("");
                signUpByHeadMasterVO.setStatus(2);
                jsonObject.put("signUpInfo", signUpByHeadMasterVO);
            }
        } else {//没有该学员信息，提示录入
            jsonObject.put("signUpInfo", "NO_STUDENT_INFO");
        }
        return jsonObject;
    }

    /**
     * 提交报名信息
     *
     * @return
     */
    @GetMapping(value = "/submitSignUpByHeadMaster")
    @ResponseBody
    public JSONObject submitSignUpByHeadMaster() {
        JSONObject jsonObject = new JSONObject();
        String siId = request.getParameter("siId");
        String classId = request.getParameter("classId");
        String status = request.getParameter("status");
        if ("1".equals(status)) {
            jsonObject.put("message", SignUpManagerConstantCode.IS_REGISTER);
        } else if ("2".equals(status)) {
            //进行报名操作
            iSignUpManagerService.saveRegisterInfo(siId, classId);
            jsonObject.put("message", SignUpManagerConstantCode.REGISTER_SUCCESS);
        } else {
            //异常状态码
            jsonObject.put("message", "异常状态码" + status);
        }
        return jsonObject;
    }

    /**
     * 报到管理手机页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/mobileView")
    public String view1(Model model) {
        return "/signUpManager/signUpInfoList_mobile";
    }

    /**
     * 首页手机页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/mobileView_index")
    public String view2(Model model) {
        return "/index/index_mobile";
    }

    //返回班级列表
    @PostMapping(value = "/findTable1")
    @ResponseBody
    public void list1(Model model) throws IOException {


        SignUpManagerListVO signUpManagerListVO;
        BTView<SignUpManagerVO> bt = new BTView<>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String classNumber = request.getParameter("classNumber").trim();
        String startStopTime = request.getParameter("startStopTime");
        String className = request.getParameter("className").trim();
        String teacherName = request.getParameter("teacherName").trim();


        signUpManagerListVO = iSignUpManagerService.getClassInfoList(pageSize, pageNumber, classNumber, className, teacherName, startStopTime, 0, null, null, null, null);


        bt.setRows(signUpManagerListVO.getSignUpManagerVOList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(signUpManagerListVO.getTotalResults());
        super.writeJSON(bt);
    }

    /**
     * 补卡
     *
     * @return
     */
    @RequestMapping(value = "/cardReplacement")
    @ResponseBody
    public JSONObject cardReplacement() {
        JSONObject jsonObject = new JSONObject();
        String siId = request.getParameter("siId").trim();
        String classId = request.getParameter("classId").trim();
        String studentCardNumber = request.getParameter("studentCardNumber").trim();
        List<StudentCard> studentCards = null;
        //查询输入的流水号是否可用
        if (!studentCardNumber.equals("")) {
            studentCards = studentCardService.findListBy(studentCardNumber);
        }
        //不存在的流水号
        if (studentCards == null || studentCards.size() == 0) {
            //未成功的状态
            jsonObject.put("status", -1);
            jsonObject.put("message", "该流水号不存在!");
            //流水号存在
        } else {
            //流水号正在使用中
            if (null != studentCards.get(0).getStudentId()) {
                //未成功的状态
                jsonObject.put("status", -1);
                jsonObject.put("message", "此流水号正在使用中");
                //闲置的流水号
            } else {
                //回收原来学生的流水号信息
                //根据学生id和班级id查询报道表信息
                if (!siId.equals("") && !classId.equals("")) {
                    Register register = registerService.getStuRegister(Integer.parseInt(siId), Long.valueOf(classId));
                    //根据学生id和报到时间查询流水号信息
                    List<StudentCard> studentCardList = studentCardService.findByStudentIdAndRegisterTime(siId, register.getReportTime());

                    //回收原来的流水号
                    if (studentCardList != null && studentCardList.size() > 0) {
                        StudentCard studentCard1 = studentCardService.get(studentCardList.get(0).getId());
                        studentCard1.setRegisterTime(null);
                        studentCard1.setStudentId(null);
                        studentCardService.update(studentCard1);
                    }

                    //为学生添加新流水号信息
                    StudentCard studentCard = studentCardService.get(studentCards.get(0).getId());
                    studentCard.setStudentId(Integer.parseInt(siId));
                    studentCard.setRegisterTime(register.getReportTime());
                    studentCardService.update(studentCard);

                    //修改报到登记表的流水号信息
                    register.setNumber(studentCard.getNumber());
                    registerService.update(register);

                }
                //成功的状态
                jsonObject.put("status", 0);
            }

        }

        return jsonObject;
    }

    @PostMapping(value = "/changeClass")
    @ResponseBody
    public Object changeClass(@RequestParam(value = "siId") String siId, @RequestParam(value = "oldClassId") String oldClassId, @RequestParam(value = "newClassId") String newClassId) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(siId) && StringUtils.isNotBlank(oldClassId) && StringUtils.isNotBlank(newClassId)) {
            Register register = registerService.getStuRegister(Integer.parseInt(siId), Long.parseLong(oldClassId));
            if (register != null) {
                register.setClassId(Integer.parseInt(newClassId));
                registerService.update(register);
            }
            String[] propName = {"student", "classRoom"};
            Integer[] propValue = {Integer.parseInt(siId), Integer.parseInt(oldClassId)};
            List<StuDiningRecord> stuDiningRecords = stuDinRecordService.queryByProerties(propName, propValue, null, null);
            for (StuDiningRecord sdr : stuDiningRecords
                    ) {
                sdr.setClassRoom(Integer.parseInt(newClassId));
                stuDinRecordService.update(sdr);
            }
            jsonObject.put("msg", CommonReturnCode.SUCCESS);
        } else {
            jsonObject.put("msg", CommonReturnCode.BAD_REQUEST);
        }
        return jsonObject;
    }

    /**
     * 修改费用页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "modificatio_fee")
    public String modificatio_fee(Model model) {
        //班级id
        String classId = request.getParameter("classId").trim();
        model.addAttribute("classId", classId);
        //学员id
        String siId = request.getParameter("siId").trim();
        model.addAttribute("siId", siId);

        //根据班级ID和学生id查询报到登记信息
        List<Register> registers = registerService.findRegisters(siId, classId);
        //报到登记id
        model.addAttribute("rId", registers.get(0).getId());

        //根据id查询班级信息
        ClassInfo classInfo = iClassInfoService.findClassInfoByClassId(classId);
        //标间标准
        model.addAttribute("houseStandard0", classInfo.getInterScaleFee());
        //单间标准
        model.addAttribute("houseStandard1", classInfo.getSingleRoomCharge());
        //住宿天数
        Integer dayNum =0;
        if(classInfo.getDayNum()!=null && classInfo.getDayNum()>=1){
            dayNum = classInfo.getDayNum()-1;
        }
        //住宿增加天数
        Double increaseDay = 0.0;
        if(classInfo.getIncreaseDay()!=null){
            increaseDay = classInfo.getIncreaseDay();
        }
        //实际住宿天数（培训天数-1+住宿增加天数）
        model.addAttribute("dayNum", dayNum+increaseDay);
        //其它费用
        model.addAttribute("other_fees", classInfo.getOtherCharges());
        //培训费
        model.addAttribute("groom_cost", classInfo.getTrainingExpense());
        //班级名称
        model.addAttribute("className", classInfo.getClassName());

        //如果班级的报名地点是红枫路，默认住宿标准是单间
        if(classInfo.getRegPlace()!=null&&classInfo.getRegPlace().contains("红枫")){
            model.addAttribute("hotelparam", "hotelparam");
        }

        //根据学员id查询学生姓名和身份证号
        model.addAttribute("siName", studentService.findStudentById(siId).getSiName());
        model.addAttribute("siIDNumber", studentService.findStudentById(siId).getSiIDNumber());
        return "signUpManager/modificatio_fee";
    }

    /**
     * 根据班级id查询日程安排
     *
     * @param classId
     * @return
     */
    @RequestMapping(value = "/findClassDiningTable")
    @ResponseBody
    public Object findClassDiningTable(@RequestParam(value = "classId", required = false) String classId) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(classId)) {
            //根据班级ID进行查询就餐安排分页
            Pages pages = classDiningService.findClassDiningListByClassId(pageSize, pageNumber, classId);
            map.put("rows", pages.getResult());
            map.put("total", pages.getTotalResults());
        }
        return map;
    }

    /**
     * 根据班级id和学生id查询报到信息
     *
     * @return
     */
    @RequestMapping(value = "/findRegisterBysIdAndcId")
    @ResponseBody
    public Object findRegisterBysIdAndcId() {
        String classId = request.getParameter("classId");
        String siId = request.getParameter("siId");
        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(classId) && StringUtils.isNotBlank(siId)) {
            //根据班级ID和学生id查询报到登记信息
            List<Register> registers = registerService.findRegistersBysIdAndcId(siId, classId);
            Register register = registers.get(0);
            //map.put("register", register);
            //总费用(其它费用+培训费+住宿费+就餐费)
            map.put("total", register.getOtherCharges() + register.getTrainingExpense() + register.getScaleFeeTotal() + register.getFoodTotal());
        }
        return map;
    }


    /**
     * 修改费用（修改报到登记表的信息）
     * @param rId
     * @return
     */
    @RequestMapping(value = "/feeRevision")
    @ResponseBody
    public Object feeRevision(@RequestParam(value = "rId", required = false) String rId) {
        /*Map<String, Object> map = Maps.newHashMap();*/

        //根据ID查询报到登记信息
        Register register = registerService.get(Integer.parseInt(rId));

        //住宿费
        String scaleFeeTotal = request.getParameter("scaleFeeTotal");
        if(scaleFeeTotal==null || scaleFeeTotal.equals("")){
            scaleFeeTotal="0";
        }
        //其它费用
        String otherCharges = request.getParameter("otherCharges");
        if(otherCharges==null || otherCharges.equals("")){
            otherCharges="0";
        }
        //培训费
        String trainingExpense = request.getParameter("trainingExpense");
        if(trainingExpense==null || trainingExpense.equals("")){
            trainingExpense="0";
        }
        //就餐费
        String foodTotal = request.getParameter("foodTotal");
        if(foodTotal==null || foodTotal.equals("")){
            foodTotal="0";
        }

        //就餐费隐藏域
        String table_money2 = request.getParameter("table_money2");
        if(table_money2==null || table_money2.equals("")){
            table_money2="0";
        }

        //住宿方式
        String hotel = request.getParameter("hotel");

        //缴费方式
        String pay = request.getParameter("pay");

        //（1:已交，2：未交, 3:国网商旅，4：统一转账）
        //未交费置为0
        if(pay!=null && pay.equals("2")){

            //住宿费
            register.setScaleFeeTotal(0.0);
            //其它费用
            register.setOtherCharges(0.0);
            //培训费
            register.setTrainingExpense(0.0);
            //就餐费
            register.setFoodTotal(0.0);

        }else{

            //住宿费
            register.setScaleFeeTotal(Double.parseDouble(scaleFeeTotal));
            //其它费用
            register.setOtherCharges(Double.parseDouble(otherCharges));
            //培训费
            register.setTrainingExpense(Double.parseDouble(trainingExpense));
            //就餐费
            register.setFoodTotal(Double.parseDouble(foodTotal));

        }

        //就餐费用为0则设置不就餐否则设置就餐
        if(table_money2.equals("0") || table_money2.equals("")){
            register.setDining("2");
        }else{
            register.setDining("1");
        }

        //住宿方式(（0:标间，1:单间，2：不住宿）)
        register.setHotel(Integer.parseInt(hotel));

        //缴费方式
        register.setPay(pay);

        registerService.update(register);
       /* return map;*/
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }



}
