package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tjuninfo.training.support.CommonProp;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.DateUtils;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.excel.ImportExcel;
import tjuninfo.training.task.util.excel.beanvalidator.BeanValidators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 学生信息表示层控制器
 * @author CJ
 * @date 2018年9月19日
 */
@Controller
@RequestMapping(value = "/classInfo")
public class ClassInfoController extends BaseController{

	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private TrainingTypeService trainingTypeService;
	/*@Autowired
	private TeacherInfoService teacherInfoService;*/
	@Autowired
	private IBasicParametersService basicParametersService;
	@Autowired
	private ICampusService campusService;
	/*@Autowired
	private StudentService studentService;*/
	@Autowired
	private DepartmentService departmentService;
    @Autowired
    private IUnitService unitService;
	@Autowired
	private IClassroomService classroomService;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private EvaluateSubjService evaluateSubjService;
    @Autowired
    private IUserRoleService userRoleService;
	@Autowired
	private ClassDiningService classDiningService;
    @Autowired
    private TeachDiningService teachDiningService;

	/**
	 * 列表页面
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/view")
	public String view(Model model) {
		//单位列表
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);
		String startStopTime = DateUtils.formatDateTime3(new Date());
		model.addAttribute("startStopTime", startStopTime);

		//院校基本参数列表
		List<BasicParameters> basicParameters = basicParametersService.list();
		//获取报名地点
		String address = basicParameters.get(0).getAddress();
		List<String> placeList = new ArrayList<String>();

		String[] result = address.split("[，,]");
		for (int i = 0; i < result.length; i++) {
			String addres = result[i];
			placeList.add(addres);
		}
		model.addAttribute("placeList",placeList);
        //查询当前登录用户
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		//当前用户角色
		SysRole sysRole = sysRoleService.get(userRoleService.getUserRoleByUserId(userInfo.getUserId()).getRoleId());
		if(sysRole.getRoleValue()!=null && sysRole.getRoleValue().trim().equals("班主任")){
			model.addAttribute("buserId",userInfo.getUserId());
		}
		/*model.addAttribute("mess", "");*/
		return "/classInfo/classInfoList";
	}

	/**
	 * 班级管理列表页面
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/viewManage")
	public String viewManage(Model model) {
		//单位列表
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);
        String startStopTime = DateUtils.formatDateTime3(new Date());
        model.addAttribute("startStopTime", startStopTime);
		//院校基本参数列表
		List<BasicParameters> basicParameters = basicParametersService.list();
		//获取报名地点
		String address = basicParameters.get(0).getAddress();
		List<String> placeList = new ArrayList<String>();

		String[] result = address.split("[，,]");
		for (int i = 0; i < result.length; i++) {
			String addres = result[i];
			placeList.add(addres);
		}
		model.addAttribute("placeList",placeList);
		return "/classInfoManger/classInfoList";
	}

	/**
	 * 新建或修改表单
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/form")
	public String form(ClassInfo classInfo, Model model) {

		String query =request.getParameter("query");
		if(query!=null && !query.equals("")){
			model.addAttribute("query", query);
		}

		//培训类型列表
		List<TrainingType> trainingTypeList = trainingTypeService.findTrainingTypeList();
		//教师列表
		/*List<TeacherInfo> teacherInfoList = teacherInfoService.findTeacherInfoList(null,null,null);*/
		/*//用户列表
		List<SysUser> sysUserList = sysUserService.findUserList();*/
		List<SysRole> sysRoles=sysRoleService.getUsers("班主任");
        List<SysUserDto> sysUserList=sysUserService.findByRoleId(sysRoles.get(0).getRoleId().intValue());
	    //部门列表
		List<Department> departmentList = departmentService.list();
		//单位列表
        List<Unit> unitList = unitService.list();
		//校区列表
		List<Campus> campusList = campusService.list();
		//院校基本参数列表
		List<BasicParameters> basicParameters = basicParametersService.list();
		//获取报名住宿地点
		String address = basicParameters.get(0).getAddress();
		//获取就餐地点
		String eatPlaces = basicParameters.get(0).getEatPlace();
        //获取就餐标准
        String eatStandard = basicParameters.get(0).getEatStandard();
        //获取房间标准
        String houseStandard = basicParameters.get(0).getHouseStandard();
		/*//班级编号
		String m=IdGen.nextCode("dq");*/
		//查询当前登录用户
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		//当前用户角色
		SysRole sysRole = sysRoleService.get(userRoleService.getUserRoleByUserId(userInfo.getUserId()).getRoleId());
		if(sysRole.getRoleValue()!=null && sysRole.getRoleValue().trim().equals("班主任")){
			model.addAttribute("buserId",userInfo.getUserId());
		}
		List<String> list = new ArrayList<String>();
		List<String> diningPlaceList = new ArrayList<String>();

		String[] result = address.split("[，,]");
		for (int i = 0; i < result.length; i++) {
			String addres = result[i];
			list.add(addres);
		}
		String[] res = eatPlaces.split("[，,]");
		for (int i = 0; i < res.length; i++) {
			String eatPlace = res[i];
			diningPlaceList.add(eatPlace);
		}
        String[] eatStandards = eatStandard.split("[，,]");
        String[] houseStandards = houseStandard.split("[，,]");
		String id = request.getParameter("id");
		//编辑页面
		if(id!=null && !id.equals("")){
			classInfo=classInfoService.get(Long.parseLong(id));
			//if(classInfo.getClassroom()!=null){
				/*//获取校区名称
				model.addAttribute("schoolName",campusService.get(classroomService.get(classInfo.getClassroom()).getSchoolName()).getSchoolName() );
               //获取校区id
				model.addAttribute("cAmId",campusService.get(classroomService.get(classInfo.getClassroom()).getSchoolName()).getId());

				//获取教室id
				model.addAttribute("cRoomId",classInfo.getClassroom());*/

                //获取校区id
               /* model.addAttribute("campusId",classInfo.getClassroom());*/
			//}
			if(classInfo.getClassroomName()!=null && !classInfo.getClassroomName().equals("")){
				model.addAttribute("classroomName", classInfo.getClassroomName());
			}

			String entryStartTime = classInfo.getEntryStartTime().trim();
			if(null!=entryStartTime && !entryStartTime.equals("")){
				String entryStart = entryStartTime.substring( 0, entryStartTime.indexOf( ' ' ) ).trim();
				String entryStart1 = entryStartTime.substring( entryStartTime.indexOf( ' ' ), entryStartTime.length() ).trim();
				model.addAttribute("entryStart", entryStart.trim());
				model.addAttribute("entryStart1", entryStart1.trim());
			}
        //添加页面
		}else{
			String classNumber = String.valueOf(Long.parseLong(classInfoService.findMaxClassNumber())+1);
			//System.out.println("==========ces="+classNumber);
			classInfo.setClassNumber(classNumber);
		}
		model.addAttribute("classInfo", classInfo);
		/*model.addAttribute("teacherInfoList", teacherInfoList);*/
		model.addAttribute("sysUserList", sysUserList);
		model.addAttribute("trainingTypeList", trainingTypeList);
		model.addAttribute("departmentList", departmentList);
        model.addAttribute("unitList", unitList);
		model.addAttribute("campusList",campusList);
		model.addAttribute("list", list);
		model.addAttribute("diningPlaceList", diningPlaceList);
		/*model.addAttribute("m", m);*/
        model.addAttribute("eatStandard0", eatStandards[0].trim());
        model.addAttribute("eatStandard1", eatStandards[1].trim());
        model.addAttribute("eatStandard2", eatStandards[2].trim());
        model.addAttribute("houseStandard0", houseStandards[0].trim());
        model.addAttribute("houseStandard1", houseStandards[1].trim());
		/*return "/classInfo/classInfoForm";*/
		return "/classInfo/classInfoForm2";
	}

	/**
	 * 列表数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findTable")
	@ResponseBody
	public Object list(Model model){

		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		String plan = request.getParameter("plan");
		String classNumber = request.getParameter("classNumber");
		String startStopTime = request.getParameter("startStopTime");
		String className = request.getParameter("className");
		String teacherName = request.getParameter("teacherName");
		String time = request.getParameter("time");
		String regPlace = request.getParameter("regPlace");
		String searchStatus = request.getParameter("searchStatus");
		String order = request.getParameter("order");
		String entryStartTime = request.getParameter("entryStartTime");

        String sort = request.getParameter("sort");
        String sortOrder = request.getParameter("sortOrder");

		//查询当前登录用户
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		//当前用户角色
        SysRole sysRole = sysRoleService.get(userRoleService.getUserRoleByUserId(userInfo.getUserId()).getRoleId());
        Pages pages;
        List list = new ArrayList();
        if (("1").equals(searchStatus)){//查询正在开班的班级;
            try {
					list = classInfoService.getClassInfoList(
							pageSize,
							pageNumber,
							null, null, null, null, null, null,
							time,
							regPlace);
            }catch (Exception e){
                e.printStackTrace();
            }
            Page page = classInfoService.transListToPage(
                    pageSize,
                    pageNumber,
                    list
            );
            map.put("rows",  page.getList());
            map.put("total", page.getTotalRecords());
        }else if(("2").equals(searchStatus)){//查询正在报到的班级
            pages = classInfoService.getList(pageSize, pageNumber, plan, classNumber, startStopTime, className, teacherName, null, time, regPlace, order, entryStartTime, null,sort,sortOrder);
            map.put("rows",  pages.getResult());
            map.put("total", pages.getTotalResults());
        }else {//其他情况当做"2"处理
            if(sysRole.getRoleValue()!=null && sysRole.getRoleValue().trim().equals("班主任")){

                pages=classInfoService.getList(pageSize,pageNumber,plan,classNumber,startStopTime,className,teacherName,userInfo.getUserId().toString(),time,regPlace,order,entryStartTime,null,sort,sortOrder);

            }else{

				//pages = classInfoService.getList(pageSize, pageNumber, plan, classNumber, startStopTime, className, teacherName, null, time, regPlace, order, entryStartTime, null);

				//超级管理员
				if(sysRole.getRoleValue()!=null && sysRole.getRoleValue().trim().equals("超级管理员")){

					List<ClassInfo> classInfoList = new ArrayList<>();
					pages = classInfoService.getList(pageSize, pageNumber, plan, classNumber, startStopTime, className, teacherName, null, time, regPlace, order, entryStartTime, null,sort,sortOrder);
					List result  = pages.getResult();
					for (int i = 0; i <result.size() ; i++) {
						classInfoList.add((ClassInfo) result.get(i));
					}
					for (ClassInfo classInfo:classInfoList) {
						classInfo.setAdministratorOrNot(true);
					}


				}else {
					pages = classInfoService.getList(pageSize, pageNumber, plan, classNumber, startStopTime, className, teacherName, null, time, regPlace, order, entryStartTime, null,sort,sortOrder);

				}
            }

			map.put("rows",  pages.getResult());
			map.put("total", pages.getTotalResults());


        }
		return map;
	}

	/**
	 * 添加/修改数据
	 * @param classInfo
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
	public Object save(ClassInfo classInfo){


        CommonProp commonProp=setCommon();
	    //更新时间
		classInfo.setUpdateDate(commonProp.getUpdateDate());
        //更新者
		classInfo.setUpdateBy(commonProp.getUpdateBy());
		//班主任
		if(null != classInfo.getUserId()){
			//查询当前登录用户
			/*SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
			classInfo.setTeacherName(userInfo.getLoginAccount());*/
			SysUser userInfo = sysUserService.get(classInfo.getUserId());
			classInfo.setTeacherName(userInfo.getUserName());
		}
		/*if(null != classInfo.getUserId()){
			classInfo.setTeacherName(teacherInfoService.get(classInfo.getUserId()).getName());
		}*/
		//培训类型
		if(null != classInfo.getTypeId()){
			classInfo.setTypeName(trainingTypeService.get(classInfo.getTypeId()).getType());
		}
        //培训费
        if(null == classInfo.getTrainingExpense()){
            classInfo.setTrainingExpense(0.0);
        }
        //其它费用
        if(null == classInfo.getOtherCharges()){
            classInfo.setOtherCharges(0.0);
        }
        if(null == classInfo.getBreakfast() || classInfo.getBreakfast().equals("")) {
			//院校基本参数列表
			List<BasicParameters> basicParameters = basicParametersService.list();
			//获取就餐标准
			String eatStandard = basicParameters.get(0).getEatStandard();
			String[] eatStandards = eatStandard.split("[，,]");
			classInfo.setBreakfast(eatStandards[0]);
		}
		if(null == classInfo.getLunch() || classInfo.getLunch().equals("")) {
			//院校基本参数列表
			List<BasicParameters> basicParameters = basicParametersService.list();
			//获取就餐标准
			String eatStandard = basicParameters.get(0).getEatStandard();
			String[] eatStandards = eatStandard.split("[，,]");
			classInfo.setLunch(eatStandards[1]);
		}
		if(null == classInfo.getDinner() || classInfo.getDinner().equals("")) {
			//院校基本参数列表
			List<BasicParameters> basicParameters = basicParametersService.list();
			//获取就餐标准
			String eatStandard = basicParameters.get(0).getEatStandard();
			String[] eatStandards = eatStandard.split("[，,]");
			classInfo.setDinner(eatStandards[2]);
		}
		if(null == classInfo.getInterScaleFee()) {
			//院校基本参数列表
			List<BasicParameters> basicParameters = basicParametersService.list();
			//获取房间标准
			String houseStandard = basicParameters.get(0).getHouseStandard();
			String[] houseStandards = houseStandard.split("[，,]");
			classInfo.setInterScaleFee(Double.valueOf(houseStandards[0]));
		}
		if(null == classInfo.getSingleRoomCharge()) {
			//院校基本参数列表
			List<BasicParameters> basicParameters = basicParametersService.list();
			//获取房间标准
			String houseStandard = basicParameters.get(0).getHouseStandard();
			String[] houseStandards = houseStandard.split("[，,]");
			classInfo.setSingleRoomCharge(Double.valueOf(houseStandards[1]));
		}

/*		//查询教室
        List<Classroom> classroomList=null;
        List<Classroom> classrooms = null;
        if (classInfo.getClassroomName() != null && !(classInfo.getClassroomName().trim().equals(""))){
            classroomList=classroomService.findByClassName(classInfo.getClassroomName());
        }

        //判断是否有教室存在，有则存入教室id
        if(classroomList!=null && classroomList.size()>0) {
            classInfo.setClassroom(classroomList.get(0).getId());
        }else if(classInfo.getClassroomName()!=null&&classInfo.getClassroomName().contains("东区")){
            classrooms = classroomService.list(null,null,"东区");
        }else if(classInfo.getClassroomName()!=null&&classInfo.getClassroomName().contains("西区")){
            classrooms = classroomService.list(null,null,"西区");
        }else if(classInfo.getClassroomName()!=null&&classInfo.getClassroomName().contains("红枫")) {
            classrooms = classroomService.list(null, null, "红枫");
        }
            //教室列表
            if(classrooms!=null&&classrooms.size()>0){
                classInfo.setClassroom(classrooms.get(0).getId());
            }else{
                classrooms = classroomService.list(null, null, null);
                if(classrooms!=null&&classrooms.size()>0);
                classInfo.setClassroom(classrooms.get(0).getId());
            }*/

       //教室信息
		if (classInfo.getClassroomName() != null && !(classInfo.getClassroomName().trim().equals(""))) {
			//获取校区信息
			List<Campus> campusList = campusService.list();
			for (Campus campus :
					campusList) {
				if (classInfo.getClassroomName().contains(campus.getSchoolName().substring(0, 2).trim())) {
					classInfo.setClassroom(campus.getId());
				}
			}
		}

        //更新班级信息
        if(null != classInfo.getId()){
			classInfoService.update(classInfo);
			//评价课程
			if(null != classInfo.getSubject() && !classInfo.getSubject().equals("")){
				List<EvaluateSubj> evaluateSubjList=evaluateSubjService.findByClassId(classInfo.getId());
				//删除对应班级id的课程评价集合
				for (EvaluateSubj evaluateSubj: evaluateSubjList) {
					evaluateSubjService.deleteByPK(evaluateSubj.getId());
				}
				String[] subjects = classInfo.getSubject().split("[，,]");
				for (int i = 0; i <subjects.length ; i++) {
					EvaluateSubj evaluateSubj=new EvaluateSubj();
					evaluateSubj.setSubject(subjects[i]);
					evaluateSubj.setClassId(classInfo.getId());
					evaluateSubjService.save(evaluateSubj);
				}
			}
        //新建班级信息
		}else{
            //创建时间
			classInfo.setCreateDate(commonProp.getCreateDate());
            //创建者
			classInfo.setCreateBy(commonProp.getCreateBy());
			classInfoService.save(classInfo);

			//评价课程
			if(null != classInfo.getSubject() && !classInfo.getSubject().equals("")){
				String[] subjects = classInfo.getSubject().split("[，,]");
				for (int i = 0; i <subjects.length ; i++) {
					EvaluateSubj evaluateSubj=new EvaluateSubj();
					evaluateSubj.setSubject(subjects[i]);
					evaluateSubj.setClassId(classInfo.getId());
					evaluateSubjService.save(evaluateSubj);
				}
			}

		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

    /**
     * 查询对应班级的就餐安排（班级就餐安排和教师就餐安排）
     * @param iNumbers
     * @return
     */
    @RequestMapping(value = "/searchDining")
    @ResponseBody
    public Object searchDining(@RequestParam(value = "iNumbers", required = false) String iNumbers) {
        String[] result = iNumbers.split(",");

        String res = "-2";

        for (int i = 0; i < result.length; i++) {
            //查询学生就餐安排 教师就餐安排
            //学生就餐安排集合
            List<ClassDining> classDiningList = classDiningService.findClassDiningList(result[i]);
            //教师就餐安排集合
            List<TeachDining> teachDinList = teachDiningService.findTeachDinList(Integer.parseInt(result[i]));
            if ((classDiningList!=null && classDiningList.size()> 0) || (teachDinList!=null && teachDinList.size()> 0) ) {
                //班级存在学生就餐安排或者班级存在教师就餐安排
                res = "-1";
            }
        }
        return res;
    }


	/**
	 * 批量删除
	 * @param iNumbers
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value = "iNumbers", required = false) String iNumbers) {
		String[] result = iNumbers.split(",");

		for (int i = 0; i < result.length; i++) {
			String id = result[i];
			if (registerService.studentCountByRegister(Long.valueOf(id)) > 0) {
				return new CmsResult(-1,"存在已经有学生报到过的班级");
			} else {
				classInfoService.deleteByPK(Long.parseLong(id));
				//课程评价集合
				List<EvaluateSubj> evaluateSubjList = evaluateSubjService.findByClassId(Long.parseLong(id));
				//学生就餐安排集合
                List<ClassDining> classDiningList = classDiningService.findClassDiningList(result[i]);
                //教师就餐安排集合
                List<TeachDining> teachDinList = teachDiningService.findTeachDinList(Integer.parseInt(result[i]));
                if(evaluateSubjList!=null && evaluateSubjList.size()>0) {
                    //删除对应班级id的课程评价集合
                    for (EvaluateSubj evaluateSubj : evaluateSubjList) {
                        evaluateSubjService.deleteByPK(evaluateSubj.getId());
                    }
                }
                if(classDiningList!=null && classDiningList.size()>0) {
                    //删除对应班级id的学生就餐集合
                    for (ClassDining classDining:classDiningList) {
                        classDiningService.deleteByNid(classDining.getId());
                    }
                }
                if(teachDinList!=null && teachDinList.size()>0) {
                    //删除对应班级id的教师就餐集合
                    for (TeachDining teachDining:teachDinList) {
                        teachDiningService.delete(teachDining);
                    }
                }
			}
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}


	/**
	 * 下载导入数据模板
	 *
	 * @param response
	 * @return
	 */
/*	@RequestMapping(value = "/import/template")
	public String importFileTemplate(HttpServletResponse response) {
		String pageNo = request.getParameter("pageNo");
		try {
			if (pageNo == null) {
				pageNo = "1";
			}
			String fileName = "培训项目（样表）.xlsx";
			List<ClassInfo> list =
					classInfoService.getList(2,1,null,null,null,null,null,null,null,null).getResult();
			if(list.size()>0){
				for (ClassInfo classInfo:list) {
					if(null!=classInfo.getPlan()&&classInfo.getPlan()==0){
						classInfo.setPlanName("计划内");
					}else if(null!=classInfo.getPlan()&&classInfo.getPlan()==1){
						classInfo.setPlanName("计划外");
					}else if(null!=classInfo.getPlan()&&classInfo.getPlan()==2){
						classInfo.setPlanName("非培训班");
					}else{
						classInfo.setPlanName("其它");
					}
					if(null!=classInfo.getEvaluate()&&classInfo.getEvaluate()==0){
						classInfo.setEvaluateName("是");
					}else if(null!=classInfo.getEvaluate()&&classInfo.getEvaluate()==1) {
						classInfo.setEvaluateName("否");
					}
					if(classInfo.getPlannedNumber()==null){
						classInfo.setPlannedNumber(10);
					}
					if(classInfo.getStartStopTime()!=null&&!classInfo.getStartStopTime().trim().equals("")){
						String[] time=classInfo.getStartStopTime().split("至");
						classInfo.setStartTime(time[0].trim());
						classInfo.setStopTime(time[1].trim());
					}
					*//*classInfo.setCount(String.valueOf(registerService.findRegisters(null,classInfo.getId().toString()).size()));*//*
				}
				new ExportExcel("班级数据", ClassInfo.class, 2).setDataList(list).write(response, fileName).dispose();
			}else{
				List<ClassInfo> classInfos = Lists.newArrayList();
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassNumber("0001");
				classInfo.setClassName("测试班级");
				classInfo.setPlanName("计划内");
				classInfo.setTrainingObject("财务");
				classInfo.setTypeName("技术管理培训");
				classInfo.setStartTime("2019-01-01");
				classInfo.setStopTime("2019-01-31");
				classInfo.setPlannedNumber(10);
				classInfo.setHostUnit("安徽立卓");
				classInfo.setOrganizerUnit("电力系");
				classInfo.setTeacherName("测试");
				classInfo.setRegPlace("东区招待所");
				classInfo.setClassroomName("测试班级");
				classInfo.setEvaluateName("是");
				classInfo.setRemarks("测试");
				classInfos.add(classInfo);
				new ExportExcel("班级数据", ClassInfo.class, 2).setDataList(classInfos).write(response, fileName).dispose();
			}


			return null;
		} catch (Exception e) {
			System.out.println("======================"+e.getMessage());
		}
		return "/classInfo/classInfoList?pageNo=" + Integer.valueOf(pageNo);
	}*/

    /**
     * 下载导入数据模板
     *
     * @param response
     * @return
     */
    @RequestMapping(value="/import/template", method = RequestMethod.GET)//method = RequestMethod.GET将数据传递给前端
    public void downloadExcel(HttpServletResponse response,HttpServletRequest request) {
        try {
            //获取文件的路径
            String excelPath = request.getSession().getServletContext().getRealPath("/WEB-INF/template/"+"培训班级模板.xlsx");
            String fileName = "培训班级模板.xlsx".toString(); // 文件的默认保存名
            // 读到流中
            InputStream inStream = new FileInputStream(excelPath);//文件的存放路径
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("培训班级模板.xlsx", "UTF-8"));
            // 循环取出流中的数据
            byte[] b = new byte[200];
            int len;

            while ((len = inStream.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	/**
	 * 导入数据
	 *
	 * @param file
	 */
	@RequestMapping(value = "/import")
	public String importFile(MultipartFile file,RedirectAttributes redirectAttributes,Model model) throws IOException {
		String pageNo = request.getParameter("pageNo");
		String messages = "";
		String contextPath = request.getContextPath();//项目名
		try {
			if (pageNo == null) {
				pageNo = "1";
			}
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ClassInfo> list = ei.getDataList(ClassInfo.class);
			ClassInfo classIF=null;
			for (ClassInfo classInfo : list) {
				if(null !=classInfo.getClassNumber() && !classInfo.getClassNumber().equals("")){
					classIF=classInfoService.getByclassNumber(classInfo.getClassNumber().trim(),null);
				}
				if(classIF==null){

				try {
					//教室信息
					if (classInfo.getClassroomName() != null && !(classInfo.getClassroomName().trim().equals(""))){
						//获取校区信息
						List<Campus> campusList = campusService.list();
						for (Campus campus:
								campusList) {
							if(classInfo.getClassroomName().contains(campus.getSchoolName().substring(0, 2).trim())){
								classInfo.setClassroom(campus.getId());
							}
						}
					}else{
						addMessage(redirectAttributes, "教室为空，无法导入班级数据");
					}
           /*         List<Classroom> classroomList=null;
                    if (classInfo.getClassroomName() != null && !(classInfo.getClassroomName().trim().equals(""))){
                        classroomList=classroomService.findByClassName(classInfo.getClassroomName());
                    }

                    //判断是否有教室存在，有则存入教室id，没有则默认存入一个教室id
                    if(classroomList!=null && classroomList.size()>0) {
                        classInfo.setClassroom(classroomList.get(0).getId());
                        *//*classInfo.setClassroom(classroomList.get(0).getSchoolName());*//*
                    }else if(classInfo.getClassroomName()!=null&&classInfo.getClassroomName().contains("东区")){
                        List<Classroom> classrooms = classroomService.list(null,null,"东区");
						//教室列表
                        if(classrooms.size()>0){
                            *//*classInfo.setClassroom(classrooms.get(0).getId());*//*
                            classInfo.setClassroom(classrooms.get(0).getId());
                        }else{
                            addMessage(redirectAttributes, "教室信息错误，无法导入班级数据");
                        }
						*//*List<Campus> campusList = campusService.list();
                        if(campusList.size()>0){
                            *//**//*classInfo.setClassroom(classrooms.get(0).getId());*//**//*
                            classInfo.setClassroom(campusList.get(0).getId());
                        }else{
                            addMessage(redirectAttributes, "没有校区，无法导入班级数据");
                        }*//*
                    }else if(classInfo.getClassroomName()!=null&&classInfo.getClassroomName().contains("西区")){
                        List<Classroom> classrooms = classroomService.list(null,null,"西区");
                        //教室列表
                        if(classrooms.size()>0){
                            *//*classInfo.setClassroom(classrooms.get(0).getId());*//*
                            classInfo.setClassroom(classrooms.get(0).getId());
                        }else{
                            addMessage(redirectAttributes, "教室信息错误，无法导入班级数据");
                        }
                    }else if(classInfo.getClassroomName()!=null&&classInfo.getClassroomName().contains("红枫")){
                        List<Classroom> classrooms = classroomService.list(null,null,"红枫");
                        //教室列表
                        if(classrooms.size()>0){
                            *//*classInfo.setClassroom(classrooms.get(0).getId());*//*
                            classInfo.setClassroom(classrooms.get(0).getId());
                        }else{
                            addMessage(redirectAttributes, "教室信息错误，无法导入班级数据");
                        }
                    }else{
                        List<Classroom> classrooms = classroomService.list(null,null,null);
                        if(classrooms.size()>0){
                            classInfo.setClassroom(classrooms.get(0).getId());
                        }else {
                            addMessage(redirectAttributes, "不存在教室，无法导入班级数据");
                        }
                    }*/
                    //计划类型
					if (classInfo.getPlanName() != null && classInfo.getPlanName().trim().equals("计划内")) {
						classInfo.setPlan(0);
					}
					if (classInfo.getPlanName() != null && classInfo.getPlanName().trim().equals("计划外")) {
						classInfo.setPlan(1);
					}
					if (classInfo.getPlanName() != null && classInfo.getPlanName().trim().equals("非培训班")) {
						classInfo.setPlan(2);
					}
					//是否评价
					if (classInfo.getEvaluateName() != null && classInfo.getEvaluateName().trim().equals("是")) {
						classInfo.setEvaluate(0);
					} else if (classInfo.getEvaluateName() != null && classInfo.getEvaluateName().trim().equals("否")) {
						classInfo.setEvaluate(1);
					}
					//教师名称和电话号码
					if (classInfo.getTeacherName() != null && !classInfo.getTeacherName().trim().equals("")) {
						List<SysUser> sysUsers = sysUserService.findUserListByName(classInfo.getTeacherName());
						if (sysUsers.size()>0) {
							classInfo.setUserId(sysUsers.get(0).getUserId());
							if (null != sysUsers.get(0).getTelephone() && !sysUsers.get(0).getTelephone().trim().equals("")) {
								classInfo.setPhoneNumber(sysUsers.get(0).getTelephone());
							}
						}
					}
                    //报到地点（住宿地点和就餐地点）
                    if(classInfo.getRegPlace()!=null&&!classInfo.getRegPlace().equals("")){
                        //院校基本参数列表
                        List<BasicParameters> basicParameters = basicParametersService.list();
                        //获取住宿地点
                        String address = basicParameters.get(0).getAddress();
                        //获取就餐地点
                        String eatPlaces = basicParameters.get(0).getEatPlace();
                        //住宿地点集合
                        List<String> hotellist = new ArrayList<String>();
                        //就餐地点集合
                        List<String> diningPlaceList = new ArrayList<String>();

                        String[] result = address.split("[，,]");
                        for (int i = 0; i < result.length; i++) {
                            String addres = result[i];
                            hotellist.add(addres);
                        }
                        String[] res = eatPlaces.split("[，,]");
                        for (int i = 0; i < res.length; i++) {
                            String eatPlace = res[i];
                            diningPlaceList.add(eatPlace);
                        }
                        //设置住宿地点
                        for (int i = 0; i < hotellist.size(); i++) {
                            if(classInfo.getRegPlace().contains(hotellist.get(i).substring(0, 2) )){
                                //System.out.println("住宿========"+hotellist.get(i).substring(0, 2));
                                classInfo.setHotelPlace(hotellist.get(i));
                            }

                        }
                        //设置就餐地点
                        for (int i = 0; i < diningPlaceList.size(); i++) {
                            if(classInfo.getRegPlace().contains(diningPlaceList.get(i).substring(0, 2) )){
                                //System.out.println("就餐========"+diningPlaceList.get(i).substring(0, 2));
                                classInfo.setDiningPlace(diningPlaceList.get(i));
                            }
                        }

                    }

                   //培训类型
					List<TrainingType> trainingTypes = trainingTypeService.findListByTypeName(classInfo.getTypeName().trim());
					if (classInfo.getTypeName() != null && classInfo.getTypeName().trim().equals(trainingTypes.get(0).getType())) {
						classInfo.setTypeId(trainingTypes.get(0).getId());
					}
					//办班开始时间和结束时间
					String startStopTime = "";
					if (classInfo.getStartTime() != null && !(classInfo.getStartTime().trim().equals("")) && classInfo.getStopTime() != null && !(classInfo.getStopTime().trim().equals(""))) {
                        Date startDate = org.apache.poi.ss.usermodel.DateUtil
                                .getJavaDate(Double.valueOf(classInfo.getStartTime().trim()));
                        Date stoptDate = org.apache.poi.ss.usermodel.DateUtil
                                .getJavaDate(Double.valueOf(classInfo.getStopTime().trim()));
					    SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
					    String sTime = sdf.format(startDate).trim();
                        String stopTime = sdf.format(stoptDate).trim();
					    //System.out.println("=================classInfo.getStartTime()="+sdf.format(startDate));
                        //System.out.println("=================classInfo.getStopTime()="+sdf.format(stoptDate));
						if(DateUtils.isValidDate(sTime,"yyyy-MM-dd")&&DateUtils.isValidDate(stopTime,"yyyy-MM-dd")){
							startStopTime = sTime + " 至 " + stopTime;
                            //System.out.println("=================startStopTime="+startStopTime);
							Integer dayNum = (int) Math.ceil(DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(sTime), DateUtils.parseDate(stopTime))) + 1;
							classInfo.setDayNum(dayNum);
							classInfo.setTimeNum((double) (dayNum*8));
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = simpleDateFormat.parse(sTime);
							String entryStopTime = simpleDateFormat.format(DateUtils.getDay(date, "yyyy-MM-dd", 1));
							/*classInfo.setEntryStartTime(classInfo.getStartTime().trim() + " 00:00:00");*/
                            //默认报到时间的下午
                            classInfo.setEntryStartTime(sTime + " 12:00:00");
							classInfo.setEntryStopTime(entryStopTime.trim() + " 23:59:59");
							classInfo.setEvaluateStartTime(stopTime + " 00:00:00");
							classInfo.setEvaluateStopTime(stopTime + " 23:59:59");
						}else if(!(DateUtils.isValidDate(sTime,"yyyy-MM-dd"))){
                            addMessage(redirectAttributes, "办班开始时间格式不正确---yyyy-MM-dd");
                        }else if(!(DateUtils.isValidDate(stopTime,"yyyy-MM-dd"))){
                            addMessage(redirectAttributes, "办班结束时间格式不正确---yyyy-MM-dd");
                        }else{
                            addMessage(redirectAttributes, "时间格式不正确---yyyy-MM-dd");
                        }

					}else{
						  messages="办班开始时间不能为空";
						  addMessage(redirectAttributes, "办班开始时间不能为空");
						/*response.getWriter().write("<script>alert('"+messages+"');location='"+contextPath+"/classInfo/view.action';</script>");*/
						/*return;*/
					}
                        //System.out.println("============startStopTime.trim()="+startStopTime.trim());
					if(!DateUtils.isValidDate(startStopTime.trim(),"yyyy-MM-dd")){
                        addMessage(redirectAttributes, "日期格式有误");
                    }else if (!startStopTime.equals("") && DateUtils.isValidDate(startStopTime.trim(),"yyyy-MM-dd")) {
						classInfo.setStartStopTime(startStopTime);
						classInfo.setFileUrl("");
						//院校基本参数列表
						List<BasicParameters> basicParameters = basicParametersService.list();
						//获取就餐标准
						String eatStandard = basicParameters.get(0).getEatStandard();
						//获取房间标准
						String houseStandard = basicParameters.get(0).getHouseStandard();
						String[] eatStandards = eatStandard.split("[，,]");
						String[] houseStandards = houseStandard.split("[，,]");
						classInfo.setInterScaleFee(Double.valueOf(houseStandards[0]));
						classInfo.setSingleRoomCharge(Double.valueOf(houseStandards[1]));
						classInfo.setTrainingExpense(0.0);
						classInfo.setOtherCharges(0.0);
						classInfo.setBreakfast(eatStandards[0].trim());
						classInfo.setLunch(eatStandards[1].trim());
						classInfo.setDinner(eatStandards[2].trim());
						classInfo.setStatus(1);
						CommonProp commonProp = setCommon();
						//更新时间
						classInfo.setUpdateDate(commonProp.getUpdateDate());
						//更新者
						classInfo.setUpdateBy(commonProp.getUpdateBy());
						//创建时间
						classInfo.setCreateDate(commonProp.getCreateDate());
						//创建者
						classInfo.setCreateBy(commonProp.getCreateBy());
						classInfoService.save(classInfo);
					}else if(classInfo.getPlannedNumber()==null || classInfo.getPlannedNumber().toString().trim().equals("")){
                        addMessage(redirectAttributes, "计划人数不能为空！");
                    }else if(startStopTime.equals("")){
						messages="办班结束时间不能为空";
						addMessage(redirectAttributes, "办班结束时间不能为空");
						/*response.getWriter().write("<script>alert('"+messages+"');location='"+contextPath+"/classInfo/view.action';</script>");*/
						/*return;*/
					}else{
                        addMessage(redirectAttributes, "数据异常！");
                    }
                   /* }else{
						messages="数据异常！";
						addMessage(redirectAttributes, "数据异常！---不存在的教室");
						*//*response.getWriter().write("<script>alert('"+messages+"');location='"+contextPath+"/classInfo/view.action';</script>");*//*
						*//*return;*//*
					}*/
					failureNum++;
                    addMessage(redirectAttributes, "导入成功");
				} catch (javax.validation.ConstraintViolationException ex) {
					failureMsg.append("<br/>班级数据" + classInfo + " 导入失败：");
					addMessage(model, ex.getMessage());
					addMessage(redirectAttributes, "班级数据导入失败："+ex.getMessage());
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
                    addMessage(redirectAttributes, "班级数据导入失败"+ex.getMessage());
					addMessage(model, ex.getMessage());
				}
                    successNum++;
				}else{
					messages="班级编号已存在--重复的班级编号";
					addMessage(redirectAttributes, "班级编号已存在--重复的班级编号");
					/*response.getWriter().write("<script>alert('"+messages+"');location='"+contextPath+"/classInfo/view.action';</script>");*/
					/*return;*/
				}
			}
		} catch (Exception e) {
			e.getMessage();
			messages=e.getMessage();
			addMessage(redirectAttributes, messages);
			/*response.getWriter().write("<script>alert('"+messages+"');location='"+contextPath+"/classInfo/view.action';</script>");*/
			/*return;*/
		}

	/*	//单位列表
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);*/
		/*return "/classInfo/classInfoList";*/
		/*map.put("messages",messages);*/

		/*return "redirect:" +"/classInfo/view.action";*/
	/*	return map;*/
		/*response.getWriter().write("<script>alert('数据导入成功');location='"+contextPath+"/classInfo/view.action';</script>");*/

		return "redirect:" +"/classInfo/view.action";
	}

	/**
	 * 查询编号是否存在
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findClassNumber")
	@ResponseBody
	public Object findClassNumber(String classNumber,String classInfoId,Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=classNumber && !classNumber.equals("")){
			ClassInfo classInfo=classInfoService.getByclassNumber(classNumber,classInfoId);
			if(classInfo==null){
				map.put("valid",true);
			}else{
				map.put("valid",false);
			}

		}

		return map;
	}


    /**
     * 得到单位数据
     * @return
     */
    @GetMapping(value = "findUnit")
    @ResponseBody
    public Object findUnit() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Unit> unitList=unitService.findAll();
        for (Unit unit : unitList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", unit.getAreaId());
            map.put("pid", unit.getSjareaId());
            map.put("name", unit.getAreaName());
            mapList.add(map);
        }

        return mapList;

    }



    /**
     * 得到部门数据
     * @return
     */
    @GetMapping(value = "findDepartment")
    @ResponseBody
    public Object findDepartment() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Department> departmentList=departmentService.findList();
        for (Department department : departmentList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", department.getAreaId());
            map.put("pid", department.getSjareaId());
            map.put("name", department.getAreaName());
            mapList.add(map);
        }

        return mapList;

    }

	@RequestMapping(value = "/findClassroom")
	@ResponseBody
	public List<Classroom> findClassList(@RequestParam(value="school",required=false)String school) {
    	List<Classroom> classrooms =null;
		if(school!=null && !school.equals("")) {
			List<Campus> campusList = campusService.findBySchoolName1(school);
			//根据校区查询教室列表
			classrooms =classroomService.getClassroomBySchool(campusList.get(0).getId());
		}
		return classrooms;
	}

	//班级二维码页面
	@GetMapping(value = "/QRcode")
	public String QRcode(Model model){
		String classId = request.getParameter("classId");
		try {
			ClassInfo classInfo=classInfoService.get(Long.valueOf(classId));
			model.addAttribute("classInfo",classInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/classInfoManger/QRcode";
	}

	@RequestMapping(value = "/classDining")
	@ResponseBody
	public Object classDining(@RequestParam(value="id",required=false)String id) {
    	List<ClassDining> classDiningList = classDiningService.byClassID(Long.parseLong(id));
    	if (classDiningList.size()>0){
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
		}else {
			return new CmsResult(CommonReturnCode.FAILED, 0);
		}

	}



}


