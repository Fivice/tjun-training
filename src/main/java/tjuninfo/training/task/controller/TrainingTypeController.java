package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.entity.TeacherCard;
import tjuninfo.training.task.entity.TrainingType;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.TrainingTypeService;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/training")
public class TrainingTypeController extends BaseController{
	@Resource
	private TrainingTypeService trainingTypeService;
	/*
	 * 访问页面
	 */
	@RequestMapping("/view")
	public String toTrainingType() {
		return "trainingType/trainingType_list";
	}

    /*
       查找数据表
     */
	@RequestMapping(value = "/findTable")
	@ResponseBody
	public void upsDay(Model model, BTView<TrainingType> btView)throws IOException {
		List<TrainingType> TrainingTypeList = trainingTypeService.findTrainingTypeList(btView);
		btView.setRows(TrainingTypeList);
		btView.setTotal(btView.getTotal());
		super.writeJSON(btView);
	}

	/**
	 * 查找对应的培训类型信息
	 * */
	@RequestMapping(value="checkType")
	@ResponseBody
	public Object findCard(Model model,String type){
		String typeId = null;
		List<TrainingType> TrainingTypeList = trainingTypeService.checkType(type.trim());
		if(type.equals("") || type == null){
		    typeId = "2";
        }else {
		    try {
                if (TrainingTypeList.size() == 0) {
                    typeId = "0";//无对应培训类型 赋0
                } else {
                    if (TrainingTypeList.get(0).getType().equals(type)) {
                        typeId = "1";//有对应培训类型 赋1
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId",typeId);
		return map;
	}

	/*
	 * GET 添加页面
	 */
	@RequestMapping(value= "/add/view")
	public String getInsertPage() {
		return "trainingType/trainingType_add";
	}

	/*
	 * POST 保存信息
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object insert(TrainingType trainingType) {
		trainingTypeService.saveOrUpdate(trainingType);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/*
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value="ids",required=false)String ids) {

		String [] result = ids.split(",");
		for(int i =0;i<result.length;i++) {
			int id =Integer.parseInt(result[i]);
			trainingTypeService.deleteByNid(id);
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/*
	 * GET 更新页面
	 */
	@GetMapping(value = "/{id}/edit")
	public String getUpdatePage(Model model,@PathVariable("id")int id) {
		TrainingType trainingType = trainingTypeService.get(id);
		model.addAttribute("trainingType",trainingType);
		return "trainingType/trainingType_update";
	}
	/*
	 * PUT 根据Id来更新对象
	 */
	@PutMapping(value = "/{id}")
	@ResponseBody
	public Object update(TrainingType trainingType, @PathVariable("id") int id) {
		trainingTypeService.update(trainingType);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}




}
