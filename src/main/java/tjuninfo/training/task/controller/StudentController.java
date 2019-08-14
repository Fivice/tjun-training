package tjuninfo.training.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.CommonProp;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.entity.Unit;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.ClassVo;
import tjuninfo.training.task.vo.TrainRecordVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 学生信息表示层控制器
 * @author CJ
 * @date 2018年9月19日
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController extends BaseController{

	@Autowired
	private StudentService studentService;
	@Autowired
	private DepartmentService departmentService;
    @Autowired
    private IUnitService unitService;
    @Resource
	private ClassInfoService classInfoService;
	@Autowired
	private SchedulingService schedulingService;

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
		return "/student/studentList";
	}

	/**
	 * 新建或修改表单
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/form")
	public String form(Student student, Model model) {
		String query =request.getParameter("query");
		if(query!=null && !query.equals("")){
			model.addAttribute("query", query);
		}
	 /*   //部门列表
		List<Department> departmentList = departmentService.list();
		//单位列表
        List<Unit> unitList = unitService.list();*/

		String id = request.getParameter("id");
		if(id!=null && !id.equals("")){
			student=studentService.get(Integer.parseInt(id));
			//部门编号
		/*	Integer departmentId= student.getSiDepartmentId();
			if(null!=departmentId){
				Department department=departmentService.get(departmentId);
				model.addAttribute("department", department);
			}*/
			//单位编号
			Integer unitId= student.getSiUnitId();
			if(null!=unitId){
				Unit unit=unitService.get(unitId);
				model.addAttribute("unit", unit);
			}
		}

		model.addAttribute("student", student);
		/*model.addAttribute("departmentList", departmentList);
        model.addAttribute("unitList", unitList);*/
		/*return "student/studentForm";*/
		return "student/studentForm4";
	}

	/**
	 * 列表数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findTable")
	@ResponseBody
	public Object list(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		String unitId = request.getParameter("unitId");
		String unitName = request.getParameter("unitName");
		String siIDNumber = request.getParameter("siIDNumber");
		/*Page page=studentService.queryForPage(pageNumber,pageSize,null);
		map.put("rows",  page.getList());
		map.put("total", page.getTotalRecords());*/
		Pages pages=studentService.getList(pageSize,pageNumber,unitId,unitName,siIDNumber);
		map.put("rows",  pages.getResult());
		map.put("total", pages.getTotalResults());
		return map;
	}

	/**
	 * 添加/修改数据
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
	public Object save(Student student){
		CommonProp commonProp=setCommon();
		//更新时间
		student.setUpdateDate(commonProp.getUpdateDate());
		//更新者
		student.setUpdateBy(commonProp.getUpdateBy());
        if(null==student.getSiId()){
			//创建时间
			student.setCreateDate(commonProp.getCreateDate());
			//创建者
			student.setCreateBy(commonProp.getCreateBy());
			studentService.save(student);

		}else{

			studentService.update(student);
		}

		/*return "/student/studentList";*/
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
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
			studentService.deleteByPK(Integer.valueOf(id));
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);

	}

	/**
	 * 查询编号是否存在
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/findIdCard")
	@ResponseBody
	public Object findIdCard(String siIDNumber,String siId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=siIDNumber && !siIDNumber.equals("")){
			Student student=studentService.getBysiIDNumber(siIDNumber,siId);
			if(student==null){
				map.put("valid",true);
			}else{
				map.put("valid",false);
			}

		}

		return map;
	}

	/**
	 * 查看学员培训记录
	 * @param
	 * @return
	 */
	@GetMapping(value = "/form1")
	public String form(ClassInfo classInfo, Model model,@RequestParam(value = "siId", required = false) String siId,@RequestParam(value = "siIDNumber", required = false) String siIDNumber) {
		/*Student student = studentService.findByNumber(siIDNumber);
		String siName = student.getSiName();
		model.addAttribute("siId",siId);
		model.addAttribute("siName",siName);
		model.addAttribute("siIDNumber",siIDNumber);*/
		model.addAttribute("siId",siId);
		return "/student/studentForm3";
	}

	/*
	* @Description TODO 学员培训情况详情展示
	* @param classInfo
	* @param btView
	* @Return void
	* @Authort hanyt
	* @Date 2019/6/6 上午 10:21
	**/
	@RequestMapping(value = "/formTable")
	@ResponseBody
	public void list1(ClassInfo classInfo, BTView<ClassVo> btView) throws IOException {
        List<TrainRecordVO> list1 = new ArrayList<>();
		String id = request.getParameter("id");
		request.setCharacterEncoding("utf-8");
		Student student = studentService.get(Integer.parseInt(id));
		//因bigint与Long类型不同,建一个ClassVo方便取班级的id(bigint)
		List<ClassVo> list = classInfoService.findList(btView,Integer.parseInt(id) );
		for(ClassVo c:list){
			String lid = c.getId().toString();
			TrainRecordVO vo = new TrainRecordVO();
			//根据班级id到日程管理中取出课程
			List<String> subList = schedulingService.findList(lid);
			if(subList.size()<1){
				String subject = "";
				vo.setSubject(subject);
			}else {//课程数大于1,转成字符串存入vo中
				String subject = String.join(";",subList);
				vo.setSubject(subject);
			}
			vo.setClassName(c.getClassName());
			vo.setDayNum(c.getDayNum());
			vo.setHostUnit(c.getHostUnit());
            vo.setStartStopTime(c.getStartStopTime());
            vo.setTimeNum(c.getTimeNum());
            vo.setIdCard(student.getSiIDNumber());
            vo.setName(student.getSiName());
            list1.add(vo);
        }
		btView.setRows(list);
        BTView<TrainRecordVO> b = new BTView<>();
        b.setRows(list1);
        b.setTotal(btView.getTotal());
		super.writeJSON(b);
	}


}


