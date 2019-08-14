package tjuninfo.training.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.ExcelReaderUtil;
import tjuninfo.training.task.util.Pages;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日程安排表示层控制器
 * @author
 * @date 2018年5月17日
 */
@Controller
@RequestMapping(value = "/scheduling")
public class SchedulingController extends BaseController{
	@Autowired
	private SchedulingService schedulingService;
	@Autowired
	private IClassroomService classroomService;
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private ICampusService campusService;
	@Autowired
	private TeacherInfoService teacherInfoService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserService sysUserService;

	/**访问页面**/
	@RequestMapping("/view")
	public String toScheduling(@RequestParam(value="id",required=false)String id,Model model){
		List<Classroom> classroom =  classroomService.list();
		model.addAttribute("classroom",classroom);
		ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
		model.addAttribute("classInfo",classInfo);
		/*String startTime = classInfo.getStartStopTime().substring(0,10);
		String stopTime = classInfo.getStartStopTime().substring(13);
		System.out.print(stopTime);
		model.addAttribute("startTime",startTime);
		model.addAttribute("stopTime",stopTime);*/
		return "scheduling/scheduling_list";
	}
	/**
	 * 加载table1列表
	 *
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "/findTable")
	public Object list(@RequestParam(value="id",required=false)String id,Model model) {

		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		String className = request.getParameter("className");
		String day =request.getParameter("day");
		Pages pages=schedulingService.findSchList(pageSize,pageNumber,day,className,id);
		map.put("rows",  pages.getResult());
		map.put("total", pages.getTotalResults());
		return map;
	}

	/**
	 * 添加页面
	 *
	 */
	@RequestMapping(value = "/add")
	public String form(Model model,@RequestParam(value="id",required=false)String id) {

		List<ClassInfo> classInfo1 =  classInfoService.list();
		model.addAttribute("classInfo1",classInfo1);

		ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
		/*
		Campus  campus = campusService.get(Integer.valueOf(cr.getSchoolName()));
		List<Classroom>  classroomList = classroomService.getClassroomBySchool(campus.getId());
		model.addAttribute("classroomList",classroomList);
		model.addAttribute("campus",campus);*/
		model.addAttribute("classInfo",classInfo);

		/*String startTime = classInfo.getStartStopTime().substring(0,10);
		String stopTime = classInfo.getStartStopTime().substring(13);
		model.addAttribute("startTime",startTime);
		model.addAttribute("stopTime",stopTime);*/

		model.addAttribute("campusList",campusService.list());

		List<TeacherInfo> teacherInfoList = teacherInfoService.list();
		model.addAttribute("teacherInfoList",teacherInfoList);

		/*//用户列表
		List<SysUser> sysUserList = sysUserService.findUserList();*/
		List<SysRole> sysRoles=sysRoleService.getUsers("班主任");
		List<SysUserDto> sysUserList=sysUserService.findByRoleId(sysRoles.get(0).getRoleId().intValue());
		model.addAttribute("sysUserList", sysUserList);
		return "/scheduling/scheduling_add";
	}

	/**
	 * 获取校区获取教室名
	 * @param newSname
	 * @return
	 */
	@RequestMapping(value = "/getClassName")
	@ResponseBody
	public List<Classroom> getClassName(@RequestParam(value="newSname",required=false)String newSname){
		List<Campus> camList = campusService.findBySchoolName1(newSname);
		Classroom classroom = new Classroom();
		List<Classroom>  list = classroomService.getClassroomBySchool(camList.get(0).getId());
		return list;
	}
	/**
	 * 保存数据
	 * @param scheduling
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(Scheduling scheduling ){
	    //拼接教室
		String cs1 = "";
		String cs[] = request.getParameterValues("classroom");
		cs1 = StringUtils.join(cs, ",");

		scheduling.setClassroom(cs1);
		//拼接教师
		String tc1 ="";
		String tc []= request.getParameterValues("teacher");
		tc1 = StringUtils.join(tc, ",");
        scheduling.setTeacher(tc1);
        scheduling.setEvaluate(0);
		schedulingService.saveScheduling(scheduling);
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
			schedulingService.deleteById(Long.parseLong(id));
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);

	}
	/**
	 * 创建编辑页面
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/addUpdate")
	public String addUpdate(@RequestParam(value = "schId", required = false) Long schId,@RequestParam(value="id",required=false)String id,Model model) {
		Scheduling scheduling = schedulingService.selectById(schId);
		model.addAttribute("scheduling",scheduling);

		/*TrainingSubject t = teacherInfo.getTrainingSubject();
		model.addAttribute("t",t);*/

		/*List<TrainingSubject> ts =  trainingSubjectService.findTsList();
		model.addAttribute("ts",ts);*/
		List<Classroom> classroom =  classroomService.list();
		model.addAttribute("classroom",classroom);
		List<ClassInfo> classInfo1 =  classInfoService.list();
		model.addAttribute("classInfo1",classInfo1);

		ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
		model.addAttribute("classInfo",classInfo);

        /*Classroom cr =classroomService.get(classInfo.getClassroom());
        model.addAttribute("cr",cr);*/
        /*Campus campus = campusService.get(cr.getSchoolName());
        model.addAttribute("campus",campus);*/

		/*String startTime = classInfo.getStartStopTime().substring(0,10);
		String stopTime = classInfo.getStartStopTime().substring(13);
		model.addAttribute("startTime",startTime);
		model.addAttribute("stopTime",stopTime);*/
        model.addAttribute("campusList",campusService.list());

        //获取选中教师selected选项
        List<TeacherInfo>  tiList = teacherInfoService.list();
        String selTeacher1 = scheduling.getTeacher();
        if(selTeacher1.length()>0){
            String[] selTeacher = selTeacher1.split(",");
            model.addAttribute("selTeacher",selTeacher);
            for(int i=0;i<selTeacher.length;i++) {
                for (int j = 0; j < tiList.size(); j++) {
                    TeacherInfo ti = (TeacherInfo) tiList.get(j);
                    if (ti.getTiName().contains(selTeacher[i])) {
                    tiList.remove(ti);
                    }
                }
            }
            model.addAttribute("tiList",tiList);
        }else{
            model.addAttribute("tiList",tiList);
        }


        //获取选中教室selected选项
//		Campus campus= campusService.get(cr.getSchoolName());
       /* List<Classroom>  classroomList = classroomService.getClassroomBySchool(cr.getSchoolName());
        String selClassroom1 = scheduling.getClassroom();
        if(selClassroom1 != null){
            String []selClassroom = selClassroom1.split(",");
            model.addAttribute("selClassroom",selClassroom);
            for(int i=0;i<selClassroom.length;i++){
                for (int j = 0; j < classroomList.size(); j++) {
                    Classroom cr1 = (Classroom) classroomList.get(j);
                    if (cr1.getClassName().contains(selClassroom[i])) {
                        classroomList.remove(cr1);
                    }
                }
            }
            model.addAttribute("classroomList",classroomList);
        }else{
            model.addAttribute("classroomList",classroomList);
        }*/

/*//用户列表
		List<SysUser> sysUserList = sysUserService.findUserList();*/
		List<SysRole> sysRoles=sysRoleService.getUsers("班主任");
		List<SysUserDto> sysUserList=sysUserService.findByRoleId(sysRoles.get(0).getRoleId().intValue());
		model.addAttribute("sysUserList", sysUserList);
		return "/scheduling/scheduling_update";

	}

	/**
	 * 更新数据
	 *
	 * @param scheduling
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(Scheduling scheduling) {

       /* //拼接教师
        Classroom classroom = new Classroom();
        String cs1 = "";
        String cs[] = request.getParameterValues("classroom");
        cs1 = StringUtils.join(cs, ",");

        scheduling.setClassroom(cs1);*/
        //拼接教师
        String tc1 ="";
        String tc []= request.getParameterValues("teacher");
        tc1 = StringUtils.join(tc, ",");
        scheduling.setTeacher(tc1);
		schedulingService.update(scheduling);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);

	}

	/**
	 * 修改参评状态
	 *
	 * @return
	 */
	@GetMapping(value = "/changeEva1")
	@ResponseBody
	public Object changeEva1() {

		String iNumber = request.getParameter("iNumber");
		// 通过日程id查询
		Scheduling scheduling = schedulingService.selectById(Long.parseLong(iNumber));
		//更改参评状态
		scheduling.setEvaluate(1);
		schedulingService.update(scheduling);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheduling",  scheduling);
		return map;

	}

	/**
	 * 改变盘点状态
	 *
	 * @return
	 */
	@GetMapping(value = "/changeEva2")
	@ResponseBody
	public Object changeInventory2() {

		String iNumber = request.getParameter("iNumber");
		// 通过日程id查询
		Scheduling scheduling = schedulingService.selectById(Long.parseLong(iNumber));
		//更改参评状态
		scheduling.setEvaluate(0);
		schedulingService.update(scheduling);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scheduling",  scheduling);
		return map;

	}

	/**
	 * 下载导入数据模板
	 *
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/template", method = RequestMethod.GET)//method = RequestMethod.GET将数据传递给前端
	public void downloadExcel(HttpServletResponse response,HttpServletRequest request) {
		try {
			//获取文件的路径
			String excelPath = request.getSession().getServletContext().getRealPath("/WEB-INF/template/"+"课程安排模板.xlsx");
			String fileName = "课程安排模板.xlsx".toString(); // 文件的默认保存名
			// 读到流中
			InputStream inStream = new FileInputStream(excelPath);//文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("bin");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode("课程安排模板.xlsx", "UTF-8"));
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
    @PostMapping(value = "/import")
	@ResponseBody
    public Object importFile(@RequestParam(value="file",required=false)MultipartFile file,Model model,@RequestParam(value="id",required=false)String id) throws IOException {
		String pageNo = request.getParameter("pageNo");
		String classId = request.getParameter("id");
		JSONObject jsonObject = new JSONObject();
		ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));

		model.addAttribute("classInfo",classInfo);
		int nullLine=0;
		String errorStr = "";
		try {
			ExcelReaderUtil readExcel = new ExcelReaderUtil(file.getInputStream(),file.getOriginalFilename());
			readExcel.setSheetNum(0); // 设置读取索引为0的工作表
			Map<String,String> checkRepeatMap=new HashMap<String,String>();//检验数据是否正常的集合
			List<String> list=new ArrayList<String>();//最终得到的tableid集合
			List<List<String>> mapList = Lists.newArrayList();
			/*这里为循环校验数据*/
			for (int i = 2; i < readExcel.getRowCount()+1; i++) {
				List<String> schList=new ArrayList<String>();
				String error="";
				//库名,先判断是否在合并单元格内，若有则取合并单元格的值
				String moduleName0=readExcel.getCellValue(i, 0).trim();//库
				String moduleName1=readExcel.getCellValue(i, 1).trim();//一级模块
				String moduleName2=readExcel.getCellValue(i, 2).trim();//二级模块
				String moduleName3=readExcel.getCellValue(i, 3).trim();//三级模块
				String moduleName4=readExcel.getCellValue(i, 4).trim();//四级模块
				String moduleName5=readExcel.getCellValue(i, 5).trim();//五级模块
				String moduleName6=readExcel.getCellValue(i, 6).trim();//六级模块
				String moduleName7=readExcel.getCellValue(i, 7).trim();//七级模块
				//String tableChiName=rows[4]==null ? "" :rows[4].trim();//表中文名  *暂时用不上
				//String tableEnName=readExcel.getCellValue(i, 5).trim().toUpperCase();//表物理名
				//空行校验
				if(StringUtils.isBlank(moduleName0) && StringUtils.isBlank(moduleName1) && StringUtils.isBlank(moduleName2)
						&& StringUtils.isBlank(moduleName3) && StringUtils.isBlank(moduleName4) && StringUtils.isBlank(moduleName5)
						&& StringUtils.isBlank(moduleName6)&& StringUtils.isBlank(moduleName6))
				{
					nullLine++;
					continue;
				}


				schList.add(moduleName0);
				schList.add(moduleName1);
				schList.add(moduleName2);
				schList.add(moduleName3);
				schList.add(moduleName4);
				schList.add(moduleName5);
				/*//判断教室是否存在
				List<Classroom> classroomList=null;
				if(moduleName6 != null && !moduleName6.trim().equals("")) {
					classroomList = classroomService.findByClassName(moduleName6.trim());
					if(classroomList.size()>0){
						schList.add(moduleName6);
					}else{
						mapList.clear();
						jsonObject.put("msg","第"+(i-1)+"行,教室不存在,请校验后上传");
						break;
					}
				}*/
				schList.add(moduleName6);
				schList.add(moduleName7);
				mapList.add(schList);
				String mapValue=moduleName0+moduleName1+moduleName2+moduleName3+moduleName4+moduleName5+moduleName6+moduleName7;

				if(checkRepeatMap.containsValue(mapValue)){
					error ="\n第"+(i+1)+"行：该行数据为重复数据，请删除该行后重新提交。\n";
					errorStr+=error;
					jsonObject.put("msg","errorStr");
					continue;
				}else{
					checkRepeatMap.put((i+1)+"",mapValue);
				}
			}

			if (StringUtils.isNotBlank(errorStr)) {
				int errorCount=readExcel.getRowCount()-1-list.size()-nullLine;
				System.out.println("导入失败！有"+errorCount+"条数据存在问题，请修正后再次导入：\n"+errorStr);
				jsonObject.put("msg","导入失败！有"+errorCount+"条数据存在问题，请修正后再次导入：\n"+errorStr);

			} else {
				/*保存操作*/
				System.out.println("======开始保存");
				for (int i = 0; i < mapList.size(); i++) {


						Scheduling scheduling = new Scheduling();
						scheduling.setClassInfo(classInfo);
						scheduling.setDay(mapList.get(i).get(0).trim());
						scheduling.setTime(mapList.get(i).get(1).trim());
						scheduling.setSchContent(mapList.get(i).get(2).trim());
						scheduling.setTeachingForm(mapList.get(i).get(3).trim());
						scheduling.setTeacher(mapList.get(i).get(4).trim());
						scheduling.setTeacherDep(mapList.get(i).get(5).trim());
						scheduling.setClassroom(mapList.get(i).get(6).trim());
						if (mapList.get(i).get(7) != null && mapList.get(i).get(7).trim().equals("是")) {
							scheduling.setEvaluate(1);
						} else {
							scheduling.setEvaluate(0);
						}
						schedulingService.saveScheduling(scheduling);
						jsonObject.put("msg","导入成功");

				}

			}
		} catch (Exception e) {
			jsonObject.put("msg","导入失败");
			//logger.error("批量导入出错："+e.getMessage().toString());
			// OutPutUtil.ajaxOut(response, "批量导入出错："+e.getMessage().toString());
			e.printStackTrace();
		}

		return jsonObject;
    }


}


