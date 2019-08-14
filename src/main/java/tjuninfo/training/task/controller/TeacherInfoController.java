package tjuninfo.training.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.CommonProp;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.TeacherInfoService;
import tjuninfo.training.task.service.TrainingSubjectService;
import tjuninfo.training.task.util.Pages;

import java.util.HashMap;
import java.util.Map;

/**
 * 教师信息表示层控制器
 * @author 
 * @date 2018年5月17日
 */
@Controller
@RequestMapping(value = "/teacherInfo")
public class TeacherInfoController extends BaseController{
	@Autowired
	private TeacherInfoService teacherInfoService;
	@Autowired
	private TrainingSubjectService trainingSubjectService;

	/**访问页面**/
	@RequestMapping("/view")
	public String toTeacherInfo(Model model){
        /*List<TrainingSubject> ts =  trainingSubjectService.findTsList();
        model.addAttribute("ts",ts);*/

	    return "teacherInfo/teacher_list";
	}
	/**
	 * 加载table列表
	 *
	 * @return
	 */
	@GetMapping(value = "/findTable")
	@ResponseBody
	public Object list(Model model) {
        String subject = request.getParameter("subject").trim();
        String tiName = request.getParameter("tiName").trim();
        System.out.println(tiName);
        String tiDepartment = request.getParameter("tiDepartment").trim();
		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		Pages pages=teacherInfoService.findTeacherInfoList(pageSize,pageNumber,subject,tiName,tiDepartment);
		map.put("rows",  pages.getResult());
		map.put("total", pages.getTotalResults());
		return map;
	}

	/**
	 * 新建或修改表单
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/form")
	public String form(TeacherInfo teacherInfo, Model model) {
		String query =request.getParameter("query");
		if(query!=null && !query.equals("")){
			model.addAttribute("query", query);
		}
		String id = request.getParameter("id");
		if(id!=null && !id.equals("")){
			teacherInfo=teacherInfoService.get(Integer.parseInt(id));

		}

		model.addAttribute("teacherInfo", teacherInfo);

		return "teacherInfo/teacherInfoForm";
	}

	/**
	 * 查询身份证号是否存在
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/findIdCard")
	@ResponseBody
	public Object findIdCard(String siIDNumber,String tiId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=siIDNumber && !siIDNumber.equals("")){
			TeacherInfo teacherInfo=teacherInfoService.getBysiIDNumber(siIDNumber,tiId);
			if(teacherInfo==null){
				map.put("valid",true);
			}else{
				map.put("valid",false);
			}

		}

		return map;
	}

    /**
     * 添加/修改数据
     * @param teacherInfo
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
    public Object save(TeacherInfo teacherInfo){
        System.out.println("=========进来了");
        CommonProp commonProp=setCommon();
        //更新时间
        teacherInfo.setUpdateDate(commonProp.getUpdateDate());
        //更新者
        teacherInfo.setUpdateBy(commonProp.getUpdateBy());
        if(null==teacherInfo.getTiId()){
            //创建时间
            teacherInfo.setCreateDate(commonProp.getCreateDate());
            //创建者
            teacherInfo.setCreateBy(commonProp.getCreateBy());
            teacherInfoService.save(teacherInfo);

        }else{

            teacherInfoService.update(teacherInfo);
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
			teacherInfoService.deleteById(Integer.valueOf(id));
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);

	}

}


