package tjuninfo.training.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.DepartmentService;

/**
 * 部门目录表示层控制器      
 * @author 
 * @date 2018年5月17日
 */
@Controller
@RequestMapping(value = "/department")
public class DepartmentController extends BaseController{
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping(value = "/{areaId}/view")
	public String list(Model model) {
		List<Department> departments = departmentService.list();
		model.addAttribute("departments", departments);
		
		for(Department department : departments) {
			List<Department> sjDepartments = departmentService.getBySjreaId(department.getAreaId());
			for(Department sjDepartment: sjDepartments) {
				
				model.addAttribute("sjDepartment",sjDepartment);
			}
		}
		return "/department/department_list";

	}
	
	/**
	 * GET 创建学校页面
	 * @return
	 */
	@GetMapping(value = "/{areaId}/create")
	public String getInsertPage(Model model, @PathVariable("areaId")Integer areaId) {
		Department department = departmentService.getByAreaId(areaId);
		model.addAttribute("department",department);
		return "/department/department_create";
	}
	
	/**
	 * POST 创建
	 * @return 
	 */
	@PostMapping(value = "")
	@ResponseBody
	public Object insert(Department department) {
		department.setState(1);
		departmentService.save(department);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	
	/**
	 * DELETE 删除菜单
	 * @param areaId
	 * @return
	 */
	@DeleteMapping(value = "/{areaId}/delete")
	@ResponseBody
	public Object delete(@PathVariable("areaId") Integer areaId) {
		List<Department> xjDepartment = departmentService.getBySjreaId(areaId);
		if(xjDepartment.size()==0) {
			Department department = departmentService.get(areaId);
			department.setState(2);
			departmentService.updateDepartment(department,areaId);
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
		}else {
			return new CmsResult(CommonReturnCode.FAILED, 0);
		}
	}

	/**
	 * GET 更新菜单页面
	 * @return
	 */
	@GetMapping(value = "/{areaId}/edit")
	public String getUpdatePage(Model model, @PathVariable("areaId") Integer areaId) {
		Department department = departmentService.getByAreaId(areaId);
		model.addAttribute("department", department);

		Department sjDeparetments = departmentService.getByAreaId(department.getSjareaId());
		model.addAttribute("sjDepartment", sjDeparetments);

		return "/department/department_update";
	}
	/**
	 * PUT 更新菜单信息(根据url菜单ID来指定更新对象,并根据传过来的菜单信息来更新菜单信息)
	 * @return
	 */
	@PutMapping(value = "/{areaId}")
	@ResponseBody
	public Object update(Department department,@PathVariable("areaId") Integer areaId) {
		
//			更新用户及菜单记录
			departmentService.updateDepartment(department,areaId);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * 获取下级区域list 
	 * @param sjId
	 * @return
	 */
	@RequestMapping(value = "/getAreaIdBySjId")
	@ResponseBody
	public List<Department> getAreaIdBySjId(@RequestParam(value="sjId",required=false)Integer sjId){
		Department department = new Department();
		department.setAreaName("--请选择--");
		List<Department>  list = departmentService.getBySjreaId(sjId);
		list.add(0,department);
		return list;
	}
}


