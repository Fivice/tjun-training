package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.EvaluateScoreVo;
import tjuninfo.training.task.vo.SubjEvaluateVo;
import tjuninfo.training.task.vo.SubjTeachEvaluateVo;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 评价管理表示层控制器
 */
@Controller
@RequestMapping(value = "/evaluationManagement")
public class EvaluationManagementController extends BaseController{

	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private EvaluateScoreService evaluateScoreService;
	@Autowired
	private SubjEvaluateService subjEvaluateService;
	@Autowired
	private EvaluateSubjService evaluateSubjService;
	@Autowired
	private SubjTeachEvaluateService subjTeachEvaluateService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private IUserRoleService iUserRoleService;

	/*
	 * 查询评价手机列表访问页面
	 */
	@RequestMapping("/mobileView")
	public String mobileView(Model model) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		Calendar cal_1=Calendar.getInstance();//获取当前日期
		String startDay = df.format(cal_1.getTime());
		String startStopTime = startDay;
		model.addAttribute("startStopTime",startStopTime);
		return "evaluationManagement/evaluationMobile";
	}

	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/view")
	public String view(Model model) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		Calendar   cal_1=Calendar.getInstance();//获取当前日期
		String startDay = df.format(cal_1.getTime());
		String startStopTime = startDay;
		model.addAttribute("startStopTime",startStopTime.substring(0,7));
		model.addAttribute("evaluateStopTime",startStopTime);
		return "/evaluationManagement/evaluationManagementList";
	}

	/**
	 * 列表数据
	 * @return
	 */
	@RequestMapping(value = "/findTable")
	@ResponseBody
	public Object list() {
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		int userId = userInfo.getUserId();
		String teacherName = userInfo.getUserName();
		List<UserRole> urList = iUserRoleService.getRoleIdByUserId(userId);
		int ss=0;
		for (UserRole userRole:
				urList) {
			String roleName=sysRoleService.get(userRole.getRoleId()).getRoleValue();
			if(roleName!=null && roleName.equals("班主任")){
				ss++;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		String plan = request.getParameter("plan");
		String classNumber = request.getParameter("classNumber").trim();
		String startStopTime = request.getParameter("startStopTime");
		String className = request.getParameter("className").trim();
		String teacherName1 = request.getParameter("teacherName").trim();
		String time = request.getParameter("time");
		String regPlace = request.getParameter("regPlace");
		String evaluateStopTime = request.getParameter("evaluateStopTime");
		List<Map<String,Object>> mapList = Lists.newArrayList();

		if(ss>0){
			Pages pages=classInfoService.getList(pageSize,pageNumber,plan,classNumber,startStopTime,className,teacherName,null,time,regPlace,null,null,evaluateStopTime,null,null);
			List<ClassInfo> classInfoList = pages.getResult();
			for (ClassInfo classInfo : classInfoList){
				//根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的实际报道学生
				Long studentCount = registerService.studentCountByRegister(classInfo.getId());
				//根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的已评价的学生人数
				Long evaluationStudentCount = registerService.evaluationStudentCountByRegister(classInfo.getId());
				Map<String,Object> map1 = Maps.newHashMap();
				map1.put("studentCount",studentCount);
				map1.put("classInfo",classInfo);
				map1.put("evaluationStudentCount",evaluationStudentCount);
				mapList.add(map1);
			}
			map.put("rows",  mapList);
			map.put("total", pages.getTotalResults());
			return map;

		}else{
			Pages pages=classInfoService.getList(pageSize,pageNumber,plan,classNumber,startStopTime,className,teacherName1,null,time,regPlace,null,null,evaluateStopTime,null,null);
			List<ClassInfo> classInfoList = pages.getResult();
			for (ClassInfo classInfo : classInfoList){
				//根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的实际报道学生
				Long studentCount = registerService.studentCountByRegister(classInfo.getId());
				//根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的已评价的学生人数
				Long evaluationStudentCount = registerService.evaluationStudentCountByRegister(classInfo.getId());
				Map<String,Object> map1 = Maps.newHashMap();
				map1.put("studentCount",studentCount);
				map1.put("classInfo",classInfo);
				map1.put("evaluationStudentCount",evaluationStudentCount);
				mapList.add(map1);
			}
			map.put("rows",  mapList);
			map.put("total", pages.getTotalResults());
			return map;
		}


	}

    /**
     * 根据班级ID查看学生评价情况
     * @param model
     * @return
     */
    @RequestMapping(value = "/findStudent")
    public Object findStudent(Model model,@RequestParam(value = "id", required = false) String id) {
    		//根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的实际报道学生
			Long studentCount = registerService.studentCountByRegister(Long.parseLong(id));
			//根据遍历出来的班级，获取班级ID，然后用班级ID到报到登记表中去查这个班级对应的已评价的学生人数
			Long evaluationStudentCount = registerService.evaluationStudentCountByRegister(Long.parseLong(id));
			//未评价学生人数=实际报道人数-已评价人数
			Long noEvaluationStudentCount = studentCount - evaluationStudentCount ;
			model.addAttribute("id",id);
			model.addAttribute("studentCount",studentCount);
			model.addAttribute("evaluationStudentCount",evaluationStudentCount);
			model.addAttribute("noEvaluationStudentCount",noEvaluationStudentCount);
        return "/evaluationManagement/studentEvaluation";
    }

	/**
	 * 学生评价情况列表数据
	 * @return
	 */
	@RequestMapping(value = "/findStudentTable")
	@ResponseBody
	public Object findStudentTable() {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		String classId = request.getParameter("classId");
		List<Map<String,Object>> mapList = Lists.newArrayList();
		Pages pages=registerService.findAllByClassId(pageSize,pageNumber,Long.parseLong(classId));
		List<Register> registerList = pages.getResult();
		for (Register register : registerList){
			//根据遍历出来的报道信息获取学生ID，根据学生ID到学生信息表中查找学生信息
			Student student = studentService.get(register.getSiId());
			Map<String,Object> map1 = Maps.newHashMap();
			map1.put("student",student);
			map1.put("register",register);
			mapList.add(map1);
		}
		map.put("rows",  mapList);
		map.put("total", pages.getTotalResults());
		return map;

	}

	/**
	 * 根据班级ID查看课程评价情况
	 */
	@RequestMapping(value = "/findSubjEvaluation")
	public Object findSubjEvaluation(Model model,@RequestParam(value = "id", required = false) String id) {
		model.addAttribute("id",id);
		return "/evaluationManagement/subjEvaluation";
	}

	/**
	 * 课程评价情况列表数据
	 */
	@RequestMapping(value = "/findSubjEvaluationTable")
	@ResponseBody
	public Object findSubjEvaluationTable() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> mapList = Lists.newArrayList();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		String classId = request.getParameter("classId");
		List<Register> registerList=registerService.findByClassId(Integer.parseInt(classId));
		List<Integer> registerId = new ArrayList<Integer>();
		if(registerList.size()>0){
			for (Register register : registerList) {
				registerId.add(register.getId());
			}
			//根据遍历出来的报道信息获取报道表ID，根据报道ID到课程评价表中所有信息
			Page page = subjEvaluateService.findAllByRegisterId(pageSize,pageNumber,registerId);
			List<SubjEvaluateVo> subjEvaluateVos = page.getList();
			for(SubjEvaluateVo subjEvaluateVo :subjEvaluateVos){
				EvaluateSubj evaluateSubj = evaluateSubjService.get(subjEvaluateVo.getProjectId());
				Map<String,Object> map1 = Maps.newHashMap();
				map1.put("subjEvaluateVo",subjEvaluateVo);
				map1.put("evaluateSubj",evaluateSubj);
				mapList.add(map1);

			}
			map.put("rows",  mapList);
			map.put("total", page.getTotalRecords());
		}else {
			map.put("rows",  0);
			map.put("total", 0);
		}

		return map;

	}

    /**
     * 根据班级ID查看教师评价情况
     */
    @RequestMapping(value = "/findTeacherEvaluation")
    public Object findTeacherEvaluation(Model model,@RequestParam(value = "id", required = false) String id) {
        model.addAttribute("id",id);
        return "/evaluationManagement/teacherEvaluation";
    }

    /**
     * 教师评价情况列表数据
     */
    @RequestMapping(value = "/findTeacherEvaluationTable")
    @ResponseBody
    public Object findTeacherEvaluationTable() {
        Map<String, Object> map = new HashMap<String, Object>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String classId = request.getParameter("classId");
        List<Map<String,Object>> mapList = Lists.newArrayList();
		List<Register> registerList=registerService.findByClassId(Integer.parseInt(classId));
		List<Integer> registerId = new ArrayList<Integer>();
		if(registerList.size()>0){
			for (Register register : registerList) {
				registerId.add(register.getId());
			}
			//根据遍历出来的报道信息获取报道表ID，根据报道ID到课程评价表中所有信息
			Page page = subjTeachEvaluateService.findAllByRegisterId(pageSize,pageNumber,registerId);
			List<SubjTeachEvaluateVo> subjTeachEvaluateVos = page.getList();
			for(SubjTeachEvaluateVo subjTeachEvaluateVo :subjTeachEvaluateVos){
				EvaluateSubj evaluateSubj = evaluateSubjService.get(subjTeachEvaluateVo.getProjectId());
				Map<String,Object> map1 = Maps.newHashMap();
				map1.put("subjTeachEvaluateVo",subjTeachEvaluateVo);
				map1.put("evaluateSubj",evaluateSubj);
				mapList.add(map1);
			}
			map.put("rows",  mapList);
			map.put("total", page.getTotalRecords());
		}else {
			map.put("rows",0);
			map.put("total",0);
		}
        return map;

    }


    /**
	 * 根据班级ID查看总体评价结果
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findResult")
	public Object findResult(Model model,@RequestParam(value = "id", required = false) String id) {
		model.addAttribute("id",id);
		return "/evaluationManagement/resultEvaluation";
	}

    /**
     * 总体评价结果列表数据
     * @return
     */
    @RequestMapping(value = "/findResultTable")
    @ResponseBody
    public Object findResultTable() {
        Map<String, Object> map = new HashMap<String, Object>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String classId = request.getParameter("classId");
		List<Register> registerList=registerService.findByClassId(Integer.parseInt(classId));
		List<Integer> registerId = new ArrayList<Integer>();
		if(registerList.size()>0){
			for (Register register : registerList) {
				registerId.add(register.getId());
			}
			Page page = evaluateScoreService.findAllByRegisterId(pageSize, pageNumber, registerId);
			map.put("rows", page.getList());
			map.put("total",  page.getTotalRecords());
		}else {
			map.put("rows", 0);
			map.put("total",  0);
		}
        return map;

    }

    /**
     * 根据班级ID查看总体评价结果的建议
     * @param model
     * @return
     */
    @RequestMapping(value = "/findResult1")
    public Object findResult1(Model model,@RequestParam(value = "id", required = false) String id) {
        model.addAttribute("id",id);
        return "/evaluationManagement/resultEvaluation1";
    }

	/**
	 * 总体评价结果的建议列表数据
	 * @return
	 */
	@RequestMapping(value = "/findResultTable1")
	@ResponseBody
	public Object findResultTable1() {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		String classId = request.getParameter("classId");
		List<Register> registerList=registerService.findByClassId(Integer.parseInt(classId));
		List<Integer> registerId = new ArrayList<Integer>();
		if(registerList.size()>0) {
			for (Register register : registerList) {
				registerId.add(register.getId());
			}
			//根据遍历出来的报道信息获取报道表ID，根据报道ID到评价结果表中所有信息
			Page page = evaluateScoreService.findAllByRegisterId1(pageSize, pageNumber, registerId);
			map.put("rows", page.getList());
			map.put("total", page.getTotalRecords());
		}else {
			map.put("rows", 0);
			map.put("total", 0);
		}
		return map;

	}

	/**
	 * 学生评价情况建议列表数据
	 * @return
	 */
	@RequestMapping(value = "/findResultTableTest")
	@ResponseBody
	public Object findResultTableTest() {
		List<EvaluateScoreVo> list = new ArrayList<>();
		String classId = request.getParameter("classId");
		List<Register> registerList=registerService.findByClassId(Integer.parseInt(classId));
		List<Integer> registerId = new ArrayList<Integer>();
		if(registerList.size()>0) {
			for (Register register : registerList) {
				registerId.add(register.getId());
			}
			//根据遍历出来的报道信息获取报道表ID，根据报道ID到评价结果表中所有信息
			list = evaluateScoreService.findAllByRegisterIdTest(registerId);

		}

		return evaluateScoreService.transformList(list);

	}

}


