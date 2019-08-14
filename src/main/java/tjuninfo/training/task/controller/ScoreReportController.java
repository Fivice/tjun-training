package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 成绩证书表示层控制器
 * @author 
 * @date 2018年5月17日
 */
@Controller
@RequestMapping(value = "/scoreReport")
public class ScoreReportController extends BaseController{
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private IUserRoleService iUserRoleService;
	@Autowired
	private ISysUserService iSysUserService;
	@Autowired
	private ScoreReportService scoreReportService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ISysRoleService sysRoleService;


	/**访问页面**/
	@RequestMapping("/view")
	public String toTeacherInfo(Model model){
        /*List<TrainingSubject> ts =  trainingSubjectService.findTsList();
        model.addAttribute("ts",ts);*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String month = sdf.format(date);
		model.addAttribute("month", month);

	    return "scoreReport/scoreReport_list";
	}
	/**
	 * 加载table列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/findTable")
	@ResponseBody
	public Object list(Model model,SysUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
        String month = request.getParameter("month");
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		int userId = userInfo.getUserId();
		String teacherName = userInfo.getUserName();
		List<UserRole> urList = iUserRoleService.getRoleIdByUserId(userId);
		int ss=0;
		List<Map<String, Object>> mapList = Lists.newArrayList();

		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		for (UserRole userRole:
					urList) {
				String roleName=sysRoleService.get(userRole.getRoleId()).getRoleValue();
				if(roleName!=null && roleName.equals("班主任")){
					ss++;
				}


		}
		if(ss>0){
			Pages pages=classInfoService.getList(pageSize,pageNumber,null,null,month,null,teacherName,null,null,null,null,null,null,null,null);
			map.put("rows",  pages.getResult());
			map.put("total", pages.getTotalResults());
			return map;

		}else{
			Pages pages=classInfoService.getList(pageSize,pageNumber,null,null,month,null,null,null,null,null,null,null,null,null,null);
			map.put("rows",  pages.getResult());
			map.put("total", pages.getTotalResults());
			return map;

		}


/*		List<Map<String, Object>> mapList = Lists.newArrayList();
		if(urList.get(0).getRoleId() == 4){
			List<ScoreReport> scoreReportList1 = scoreReportService.findScoreReportList(month,teacherName);
			List<ScoreReport> scoreReportList = removeDuplicateUser(scoreReportList1);
			for (ScoreReport scoreReport : scoreReportList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("scoreReport", scoreReport);
				mapList.add(map);
			}
		}else{
			List<ScoreReport> scoreReportList1 = scoreReportService.findScoreReportList(month,null);
			List<ScoreReport> scoreReportList = removeDuplicateUser(scoreReportList1);
			for (ScoreReport scoreReport : scoreReportList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("scoreReport", scoreReport);
				mapList.add(map);
			}
		}*/
	}
	//根据list对象属性判断去重
	private static ArrayList<ScoreReport> removeDuplicateUser(List<ScoreReport> users) {
		Set<ScoreReport> set = new TreeSet<ScoreReport>(new Comparator<ScoreReport>() {
			public int compare(ScoreReport o1, ScoreReport o2) {
				//字符串,则按照asicc码升序排列
				return o1.getClassInfo().getId().compareTo(o2.getClassInfo().getId());
			}
		});
		set.addAll(users);
		return new ArrayList<ScoreReport>(set);
	}

	/**访问成绩录入页面**/
	/**访问页面**/
	@RequestMapping("/view2")
	public String toMark(@RequestParam(value="id",required=false)String id,Model model){
        /*List<TrainingSubject> ts =  trainingSubjectService.findTsList();
        model.addAttribute("ts",ts);*/

		/*for (Student student:stuList
			 ) {
			System.out.println(student.getSiId()+"111111111111111");
		}*/
		model.addAttribute("id",id);
		return "scoreReport/scoreReport_write";
	}
	/**
	 * 加载成绩证书列表table
	 *
	 * @return
	 */
	@RequestMapping(value = "/findTable2")
	@ResponseBody
	public Object list2(@RequestParam(value="id",required=false)String id,Model model) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String,Object> map1 = Maps.newHashMap();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		Pages pages = registerService.findAllByClassId(pageSize,pageNumber,Integer.parseInt(id));
		List<Register> regList = pages.getResult();
		for (Register register : regList
			 ) {
			Map<String, Object> map = new HashMap<String, Object>();
			Student stu = studentService.get(register.getSiId());
			ScoreReport scoreReport = scoreReportService.selectScoreReport(String.valueOf(register.getSiId()),String.valueOf(register.getClassId()));
			map.put("register",register);
			map.put("stu",stu);
			map.put("scoreReport",scoreReport);
			mapList.add(map);
		}
		map1.put("rows",  mapList);
		map1.put("total", pages.getTotalResults());
		return map1;


		/*List<ScoreReport> scoList = scoreReportService.findScoreReportList2(Long.parseLong(id));

		List<StudentScoreVo> stuScoList = new ArrayList<>();


		for(int i = 0;i < scoList.size();i++){
			StudentScoreVo studentScoreVo1 = new StudentScoreVo();
			studentScoreVo1.setSiId(scoList.get(i).getRegister().getId());
            studentScoreVo1.setStuId(studentService.get(scoList.get(i).getRegister().getSiId()).getSiId());
			studentScoreVo1.setSiName(studentService.get(scoList.get(i).getRegister().getSiId()).getSiName());
			studentScoreVo1.setSiIdNumber(studentService.get(scoList.get(i).getRegister().getSiId()).getSiIDNumber());
			studentScoreVo1.setMark(scoList.get(i).getMark());
			studentScoreVo1.setReportName(scoList.get(i).getReportName());
			studentScoreVo1.setReportNumber(scoList.get(i).getReportNumber());
            studentScoreVo1.setRemark(scoList.get(i).getRemark());
			stuScoList.add(studentScoreVo1);
			System.out.println(studentScoreVo1.getSiId()+"ssssssssssssssssss");
		}

		List<Map<String, Object>> mapList = Lists.newArrayList();

		for (StudentScoreVo studentScoreVo : stuScoList) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("studentScoreVo", studentScoreVo);
			mapList.add(map);
		}

		return mapList;*/
	}


	//公共方法
	public static class UtilMethod {


		/*
		 * 把10002首位的1去掉的实现方法：
		 * @param str
		 * @param start
		 * @return
		 */
		public static String subStr(String str, int start) {
			if (str == null || str.equals("") || str.length() == 0)
				return "";
			if (start < str.length()) {
				return str.substring(start);
			} else {
				return "";
			}

		}

	}
	/*
	 * 初始化
	 */
	@RequestMapping(value = "/findByClassId")
	@ResponseBody
	public Object findByClassId(@RequestParam(value="id",required=false)String id) throws ParseException {
		 ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
		//删除对应班级id的信息
		scoreReportService.deleteByClassId(Long.parseLong(id));
		List<Register> regList = registerService.findByClassId(Integer.parseInt(id));

		String initNum = "0001"; // 截取字符串最后四位，结果:0001

		for(int i = 0;i < regList.size();i++){
			int endNum = Integer.parseInt(initNum); // 把String类型的0001转化为int类型的1
			int tmpNum = 10000 + endNum + i;
			ScoreReport scoreReport = new ScoreReport();
			scoreReport.setRegister(regList.get(i));
			scoreReport.setClassInfo(classInfo);
			scoreReport.setMark("合格");
			scoreReport.setReportName("合格证");
			scoreReport.setReportNumber(classInfo.getClassNumber() + UtilMethod.subStr("" + tmpNum, 1));
            scoreReport.setRemark("无");
			scoreReportService.save(scoreReport);
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/*
	 * 初始化
	 */
	@RequestMapping(value = "/saveOrUpdate")
	@ResponseBody
	public Object saveOrUpdate(@RequestParam(value="pk",required=false)String pk,@RequestParam(value="reId",required=false)String reId,@RequestParam(value="id",required=false)String id,@RequestParam(value="classId",required=false)String classId,@RequestParam(value="mark",required=false)String mark) throws ParseException {
			ScoreReport scoreReport = scoreReportService.selectScoreReport(id, classId);
			/*scoreReport.setRegister(regList.get(i));
			scoreReport.setClassInfo(classInfo);*/
			if(scoreReport != null){


			if(pk!=null && pk.equals("0")){
				scoreReport.setMark(mark);
			}
		if(pk!=null && pk.equals("1")){
			scoreReport.setReportName(mark);
		}
		if(pk!=null && pk.equals("2")){
			scoreReport.setReportNumber(mark);
		}
        if(pk!=null && pk.equals("3")){
            scoreReport.setRemark(mark);
        }

			scoreReportService.save(scoreReport);
			}else {
				ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));
				Register register = registerService.get(Integer.valueOf(reId));
				ScoreReport scoreReport1 = new ScoreReport();
				scoreReport1.setClassInfo(classInfo);
				scoreReport1.setRegister(register);
				if(pk!=null && pk.equals("0")){
					scoreReport1.setMark(mark);
				}
				if(pk!=null && pk.equals("1")){
					scoreReport1.setReportName(mark);
				}
				if(pk!=null && pk.equals("2")){
					scoreReport1.setReportNumber(mark);
				}
				if(pk!=null && pk.equals("3")){
					scoreReport1.setRemark(mark);
				}

				scoreReportService.save(scoreReport1);
			}

		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}




}


