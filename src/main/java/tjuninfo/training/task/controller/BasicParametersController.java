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
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.IBasicParametersService;

import java.util.ArrayList;
import java.util.List;

/**
 * 院校基本参数表示层控制器
 * @author 
 */
@Controller
@RequestMapping(value = "/basicParameters")
public class BasicParametersController extends BaseController{
	@Autowired
	private IBasicParametersService basicParametersService;

	@GetMapping(value = "/view")
	public String list(Model model) {
		List<BasicParameters> basicParametersList = basicParametersService.list();
		model.addAttribute("basicParametersList", basicParametersList);
		return "/basicParameters/basicParameters_list";
	}

    /**
     * 编辑院校基本参数页面
     * @param model，areaId
     * @return
     */
    @GetMapping(value = "/update")
    public String update(@RequestParam(value = "id", required = false) String id,Model model) {
        //院校基本参数列表
        List<BasicParameters> basicParameters = basicParametersService.list();
        //获取就餐标准
        String eatStandard = basicParameters.get(0).getEatStandard();
        String[] eatStandards = eatStandard.split("[，,]");
        //获取房间标准
        String houseStandard = basicParameters.get(0).getHouseStandard();
        String[] houseStandards = houseStandard.split("[，,]");
        model.addAttribute("eatStandard0",eatStandards[0]);
        model.addAttribute("eatStandard1",eatStandards[1]);
        model.addAttribute("eatStandard2",eatStandards[2]);
        model.addAttribute("houseStandard0",houseStandards[0]);
        model.addAttribute("houseStandard1",houseStandards[1]);
        model.addAttribute("basicParameters",basicParametersService.get(Integer.valueOf(id)));
        return "/basicParameters/basicParameters_update";

    }


    /**
     * 保存跟新院校基本参数
     *
     * @param basicParameters
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object update(BasicParameters basicParameters) {
        String eatStandard0=request.getParameter("eatStandard0");
        String eatStandard1=request.getParameter("eatStandard1");
        String eatStandard2=request.getParameter("eatStandard2");
        String eatStandard=eatStandard0+","+eatStandard1+","+eatStandard2;
        String houseStandard0=request.getParameter("houseStandard0");
        String houseStandard1=request.getParameter("houseStandard1");
        String houseStandard=houseStandard0+","+houseStandard1;
        basicParameters.setEatStandard(eatStandard);
        basicParameters.setHouseStandard(houseStandard);
        basicParametersService.update(basicParameters);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }
/**
 * 获取食堂、前台具体类型
 * @param sjName
 * @return
 */
	@RequestMapping(value = "/getNextType")
    @ResponseBody
	public List<String> getNextType(@RequestParam(value="sjName",required=false)String sjName){
        List<BasicParameters> basicParameters = basicParametersService.list();

        List<String> list = new ArrayList<String>();
        //获取报名住宿地点
        String address = basicParameters.get(0).getAddress();
        //获取就餐地点
        String eatPlaces = basicParameters.get(0).getEatPlace();
        //判断用户具体类型
        if(sjName.equals("食堂")){
            String []userTypeEat = eatPlaces.split("[，,]");
            for (int i = 0; i < userTypeEat.length; i++) {
                String eatPlace = userTypeEat[i];
                list.add(eatPlace);
            }
        }else if(sjName.equals("前台")){
            String []userTypeAdd = address.split("[，,]");
            for (int i = 0; i < userTypeAdd.length; i++) {
                String address1 = userTypeAdd[i];
                list.add(address1);
            }
        }else{
            list.add("一般");
        }
        return list;
	}

}


