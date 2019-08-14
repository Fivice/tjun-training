package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.CommonProp;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学生证流水号表示层控制器
 * @date 2018年9月19日
 */
@Controller
@RequestMapping(value = "/studentCard")
public class StudentCardController extends BaseController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private IUnitService unitService;
	@Autowired
	private StudentCardService studentCardService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private ClassInfoService classInfoService;
    @Autowired
    private EvaluateProjectService evaProService;
    @Autowired
    private EvaluateScoreService evaScoService;
    @Autowired
	private SchedulingService schedulingService;
	@Autowired
	private SubjEvaluateService subjEvaService;

	@Autowired
	private SubjTeachEvaluateService subjTeachService;



	/**
	 * 列表页面
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/view")
	public String view(Model model) {

		List<StudentCard> stuCardList = studentCardService.list();
		model.addAttribute("stuCardList", stuCardList);
		return "/student_card/student_card";
	}

	/**
	 * 进入选择学生证页面
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/form")
	@ResponseBody
	public Object form(@RequestParam(value = "id", required = false) String id, Model model) {
		StudentCard studentCard1 = studentCardService.get(Integer.parseInt(id));
		HttpSession sessionName = request.getSession();
		sessionName.setAttribute("id", id);//从session中获取学生流水号id
		Register reg = registerService.findRegister(studentCard1.getStudentId(), studentCard1.getNumber(), studentCard1.getRegisterTime());
		if (reg == null) {
			return new CmsResult(CommonReturnCode.FAILED, 0);
		} else {
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
		}


	}

	/**
	 * 进入班级相关信息
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/form2")
	//@ResponseBody
	public String form2(@RequestParam(value = "id", required = false) String id, Model model) {
		StudentCard studentCard1 = studentCardService.get(Integer.parseInt(id));
		Register reg = registerService.findRegister(studentCard1.getStudentId(), studentCard1.getNumber(), studentCard1.getRegisterTime());
		model.addAttribute("reg", reg);
		ClassInfo classInfo = classInfoService.get(Integer.valueOf(reg.getClassId()).longValue());
		System.out.println("联系号码：" + classInfo.getPhoneNumber() + "住宿地点：" + classInfo.getHotelPlace());
		model.addAttribute("classInfo", classInfo);
		Student student = studentService.get(reg.getSiId());
		model.addAttribute("student", student);
		model.addAttribute("id", id);
		return "/student_card/student_card_form";
	}

    /**
     * 根据流水号id跳转页面
     * @param model
     * @return
     */
	@GetMapping("/classAndStudentInfo")
	public String classAndStudentInfo(Model model){
		//获取流水号Id
		String studentCardId = request.getParameter("id");

		HttpSession sessionName = request.getSession();
		sessionName.setAttribute("id", studentCardId);

		//流水号id验证
        if (studentCardId==null||("").equals(studentCardId)){//流水号id为空
        	model.addAttribute("message","流水号失效");
            return "/common/noBindingInfo";
        }
        //查询流水号表获取信息
        StudentCard studentCard = studentCardService.get(Integer.parseInt(studentCardId));
        if (studentCard == null){//流水号id没有查到信息
        	model.addAttribute("message","流水号失效");
            return "/common/noBindingInfo";
        }
		if (studentCard.getStudentId()!=null&&studentCard.getRegisterTime()!=null){//流水号绑定了学生信息
            Register register = registerService.findRegister(studentCard.getStudentId(),studentCard.getNumber(),studentCard.getRegisterTime());
            ClassInfo classInfo = classInfoService.get(register.getClassId().longValue());
            Student student = studentService.get(register.getSiId());
			model.addAttribute("reg", register);
            model.addAttribute("classInfo", classInfo);
            model.addAttribute("student", student);
            model.addAttribute("id", studentCardId);
			return "/student_card/student_card_form";
		}else{//流水号没有绑定信息
			model.addAttribute("message","流水号没有绑定学员信息");
            return "/common/noBindingInfo";
		}

	}

	/**
	 * 添加/修改数据
	 *
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate")
	@ResponseBody
	public Object save(Student student) {

		CommonProp commonProp = setCommon();
		//更新时间
		student.setUpdateDate(commonProp.getUpdateDate());
		//更新者
		student.setUpdateBy(commonProp.getUpdateBy());
		if (null == student.getSiId()) {
			//创建时间
			student.setCreateDate(commonProp.getCreateDate());
			//创建者
			student.setCreateBy(commonProp.getCreateBy());
			studentService.save(student);

		} else {

			studentService.update(student);
		}

		/*return "/student/studentList";*/
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * 批量删除
	 *
	 * @param iNumbers
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value = "iNumbers", required = false) String iNumbers) {
		String[] result = iNumbers.split(",");

		for (int i = 0; i < result.length; i++) {
			String id = result[i];
			studentService.deleteByPK(Integer.valueOf(id));
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);

	}

	/**
	 * 查询编号是否存在
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/findIdCard")
	@ResponseBody
	public Object findIdCard(String siIDNumber, String siId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != siIDNumber && !siIDNumber.equals("")) {
			Student student = studentService.getBysiIDNumber(siIDNumber, siId);
			if (student == null) {
				map.put("valid", true);
			} else {
				map.put("valid", false);
			}

		}

		return map;
	}

	public   static   List  removeDuplicate(List list)  {
		for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
			for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
				if  (list.get(j).equals(list.get(i)))  {
					list.remove(j);
				}
			}
		}
		return list;
	}

	/**
	 * 学员进入评价页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/judgeView")
	public String view2(Model model) {


		//StudentCard studentCard1 = studentCardService.get(Integer.parseInt(id));
		//Register reg = registerService.findRegister(studentCard1.getStudentId(), studentCard1.getNumber(), studentCard1.getRegisterTime());
		String classId = request.getParameter("classId");
		String studentId = request.getParameter("siId");


        List<Register> regList= registerService.findRegisters(studentId,classId);
        System.out.println(regList.get(0).getId());
        model.addAttribute("regId", regList.get(0).getId());//将登记id传到页面

        ClassInfo classInfo = classInfoService.get(Integer.valueOf(classId).longValue());
		model.addAttribute("classInfo", classInfo);//将班级信息传到页面

		//Student student = studentService.get(reg.getSiId());
		//model.addAttribute("student", student);

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
		removeDuplicate(typeList);//去除list中重复的元素
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

		/*List <EvaluateProject> projectList1 = evaProService.findEvaluateProjectList(1);
		List projectList = new ArrayList();
		for (EvaluateProject eva:projectList1
				) {
			projectList.add(eva.getProject());
		}
		removeDuplicate(projectList);
		model.addAttribute("projectList",projectList);
		model.addAttribute("projectList1",projectList1);*/

		//通过班级id获取课程list
        List <Scheduling> evaSubjList = schedulingService.findEvaSchList(classId,1);
        model.addAttribute("evaSubjList",evaSubjList);
		return "/student_card/judgeForm";
	}


    /**
     * 保存评价数据
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Object saveJudge() {

        Integer regId = Integer.parseInt(request.getParameter("id"));
        System.out.println("id========="+regId);
        Register register = registerService.get(regId);
        register.setStatus(1);
		registerService.save(register);
        List<EvaluateProject> evaList = evaProService.findAll();
        for(int i = 0;i<evaList.size();i++){
            EvaluateScore evaluateScore = new EvaluateScore();
            Integer project = Integer.parseInt(request.getParameter("project"+(i+1)));
            System.out.println("project========"+project);
            String result = request.getParameter("result"+i);
            System.out.println("result========="+result);
            evaluateScore.setProjectId(project);
            evaluateScore.setResult(result);
            evaluateScore.setRegisterId(regId);
            evaScoService.saveJudge(evaluateScore);
        }

		//通过班级id获取课程list
		String classId = request.getParameter("classId");
		System.out.println("classId============="+classId);
		List <Scheduling> evaSubjList = schedulingService.findEvaSchList(classId,1);
		for(int i = 0;i<evaSubjList.size();i++){
			SubjEvaluate subjEvaluate = new SubjEvaluate();
			SubjTeachEvaluate subjTeachEvaluate = new SubjTeachEvaluate();

			Integer project = Integer.parseInt(request.getParameter("pro"+i));
			System.out.println("教师课程project========"+project);

			String result1 = request.getParameter("res"+i);
			System.out.println("课程result========="+result1);

			String result2 = request.getParameter("resu"+i);
			System.out.println("教师result========="+result2);

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
