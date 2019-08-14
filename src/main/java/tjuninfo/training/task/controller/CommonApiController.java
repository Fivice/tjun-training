package tjuninfo.training.task.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.Menu;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.IBasicParametersService;
import tjuninfo.training.task.service.IMenuService;
import tjuninfo.training.task.service.ISignUpManagerService;
import tjuninfo.training.task.vo.SignUpManagerListVO;
import tjuninfo.training.task.vo.SignUpManagerVO;

import java.io.IOException;
import java.util.List;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName CommonApiController
 * @date 2018/12/9 18:54
 **/
@Controller
@RequestMapping(value = "/commonApi")
public class CommonApiController extends BaseController {
    private final ClassInfoService classInfoService;
    private final IMenuService menuService;
    private final IBasicParametersService iBasicParametersService;
    private final ISignUpManagerService iSignUpManagerService;

    @Autowired
    public CommonApiController(ClassInfoService classInfoService, IMenuService menuService, IBasicParametersService iBasicParametersService, ISignUpManagerService iSignUpManagerService) {
        this.classInfoService = classInfoService;
        this.menuService = menuService;
        this.iBasicParametersService = iBasicParametersService;
        this.iSignUpManagerService = iSignUpManagerService;
    }

    @GetMapping(value = "/getClassInfoByClassId")
    @ResponseBody
    public Object getClassInfoByClassId(){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("classId");
        if (id != null){
            long classId = Long.parseLong(id);
            ClassInfo classInfo = classInfoService.get(classId);
            jsonObject.put("classInfo",classInfo);
        }else{
            jsonObject.put("classInfo",null);
        }
        return jsonObject;
    }
    @GetMapping(value = "/getMenu")
    @ResponseBody
    public Object getMenu(){
        JSONObject jsonObject = new JSONObject();
        List<Menu> menus = menuService.list();
        jsonObject.put("menu",menus);
        return jsonObject;
    }
    @GetMapping(value = "/regAddressList")
    @ResponseBody
    public Object regAddressList(){
        List<BasicParameters> basicParametersList = iBasicParametersService.list();
        String[] regPlaceArr = {};
        if (basicParametersList.size()>0){
            BasicParameters basicParameters = basicParametersList.get(0);
            String regPlace = basicParameters.getAddress();
            regPlaceArr = regPlace.split("[,，]");
        }

        return regPlaceArr;
    }
    @GetMapping(value = "/sysUserType")
    @ResponseBody
    public Object sysUserType(){
        return getUser().getUserType();
    }

    @GetMapping(value = "/classInfoList")
    @ResponseBody
    public void classInfoList() throws IOException {
        SignUpManagerListVO signUpManagerListVO;
        BTView<SignUpManagerVO> bt = new BTView<>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String classNumber = request.getParameter("classNumber").trim();
        String className = request.getParameter("className").trim();
        String startTime = request.getParameter("startTime").trim();
        String orderName = request.getParameter("orderName");
        String orderBy = request.getParameter("orderBy");
        signUpManagerListVO = iSignUpManagerService.getClassInfoList(
                pageSize,pageNumber,
                classNumber,className,
                null,startTime,0,null,null,
                orderName,orderBy
        );
        bt.setRows(signUpManagerListVO.getSignUpManagerVOList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(signUpManagerListVO.getTotalResults());
        super.writeJSON(bt);
    }
}
