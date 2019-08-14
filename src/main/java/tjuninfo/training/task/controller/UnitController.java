package tjuninfo.training.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.Unit;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.DepartmentService;
import tjuninfo.training.task.service.IUnitService;

import java.util.List;

/**
 * 单位目录表示层控制器
 * @author 
 */
@Controller
@RequestMapping(value = "/unit")
public class UnitController extends BaseController{
	@Autowired
	private IUnitService unitService;
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping(value = "/view")
	public String list(Model model) {
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);
		return "/unit/unit_list";
	}

	/**
	 * GET 创建第一级单位页面
	 * @return
	 */
	@GetMapping(value = "/create")
	public String getInsertPage() {
		return "/unit/unit_add";
	}


	/**
	 * 添加第一级单位信息
	 * @param unit
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(Unit unit){
		unit.setAreaType(1);
		unit.setSjareaId(0);
		unit.setStatus(1);
		unitService.save(unit);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * DELETE 删除单位信息
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value = "areaId", required = false) String areaId) {
		Integer areaId1 = Integer.parseInt(areaId);
		Unit unit = unitService.get(areaId1);
		List<Unit> lowerUnit = unitService.getBySjreaId(areaId1);
		if(lowerUnit.size() == 0) {
			unit.setStatus(2);
			unitService.update(unit);
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
		}else {
			return new CmsResult(CommonReturnCode.FAILED, 0);
		}
	}

	/**
	 * 编辑单位页面
	 * @param model，areaId
	 * @return
	 */
	@GetMapping(value = "/update")
	public String update(@RequestParam(value = "areaId", required = false) String areaId,Model model) {
		model.addAttribute("unit",unitService.get(Integer.valueOf(areaId)));
		return "/unit/unit_update";

	}


	/**
	 * 保存跟新的单位信息
	 *
	 * @param unit
	 * @return
	 */
	@RequestMapping(value = "/saveUnit")
	@ResponseBody
	public Object update(Unit unit) {
		unitService.update(unit);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * GET 添加下级级单位页面
	 * @return
	 */
	@GetMapping(value = "/addUnit")
	public String getInsertLowerPage(@RequestParam(value = "areaId", required = false) String areaId,Model model) {
		model.addAttribute("unit",unitService.get(Integer.parseInt(areaId)));
		return "/unit/unit_addUnit";
	}

    /**
     * 保存下级单位信息
     * @param unit
     * @return
     */
    @RequestMapping(value = "/saveLowerUnit")
    @ResponseBody
    public Object saveUnit(Unit unit){
        unit.setStatus(1);
        unitService.save(unit);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

	/**
	 * GET 添加下级级单位页面(不获取上级单位)
	 * @return
	 */
	@GetMapping(value = "/addUnit1")
	public String getInsertLowerPage(Model model) {
		List <Unit> unitList = unitService.getBySjreaId(0);

		model.addAttribute("unitList",unitList);
		return "/register/unit_addUnit1";
	}
}


