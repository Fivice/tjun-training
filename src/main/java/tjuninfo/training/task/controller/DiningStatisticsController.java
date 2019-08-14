package tjuninfo.training.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.DiningStatisticsConstantCode;
import tjuninfo.training.task.constant.SignUpManagerConstantCode;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.SysRole;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.entity.UserRole;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.ClassInfoForDiningPrepVO;
import tjuninfo.training.task.vo.ClassInfoForDiningStatisticsVO;
import tjuninfo.training.task.vo.StuDiningDataStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningDataStatisticsVO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/diningDataStatistics")
public class DiningStatisticsController extends BaseController {
    private final ClassInfoService classInfoService;
    private final ClassDiningService classDiningService;
    private final IBasicParametersService iBasicParametersService;
    private final TeachDinStatisticsService teachDinStatisticsService;
    private final IUserRoleService iUserRoleService;
    private final ISysRoleService iSysRoleService;
    private final ISignUpManagerService iSignUpManagerService;

    @Autowired
    public DiningStatisticsController(ClassInfoService classInfoService, ClassDiningService classDiningService, IBasicParametersService iBasicParametersService, TeachDinStatisticsService teachDinStatisticsService, IUserRoleService iUserRoleService, ISysRoleService iSysRoleService, ISignUpManagerService iSignUpManagerService) {
        this.classInfoService = classInfoService;
        this.classDiningService = classDiningService;
        this.iBasicParametersService = iBasicParametersService;
        this.teachDinStatisticsService = teachDinStatisticsService;
        this.iUserRoleService = iUserRoleService;
        this.iSysRoleService = iSysRoleService;
        this.iSignUpManagerService = iSignUpManagerService;
    }

    //跳转就餐统计页面
    @RequestMapping("/view")
    public String view() {
        return "diningDataStatistics/diningDataStatistics";
    }
    @PostMapping("/classInfo")
    @ResponseBody
    public void classInfo() throws IOException {
        BTView<ClassInfoForDiningStatisticsVO> bt = new BTView<>();
        String startStopTime = request.getParameter("startStopTime");
        String diningPlace = request.getParameter("diningPlace");
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        if (DiningStatisticsConstantCode.ALL_PLACE.equals(diningPlace)){
            diningPlace = "";
        }
        //获取当前登录用户
        SysUser sysUser = getUser();
        long roleId = 0;

        UserRole userRole = iSignUpManagerService.getUserRole(sysUser.getUserId());
        roleId = userRole.getRoleId();

        boolean isHeadMaster = iSignUpManagerService.judgeUserIsRole(roleId,SignUpManagerConstantCode.HEAD_MASTER);
        Page<ClassInfoForDiningStatisticsVO> page;
        if (isHeadMaster){
            page = classInfoService.getClassInfo(pageNumber,pageSize,startStopTime,diningPlace,sysUser.getUserId());
        }else {
            page = classInfoService.getClassInfo(pageNumber,pageSize,startStopTime,diningPlace,null);
        }

        bt.setRows(page.getList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(page.getTotalRecords());
        super.writeJSON(bt);
    }
    @GetMapping("/stuDiningDataStatistics")
    @ResponseBody
    public List diningDataStatistics (){
        String classIdArrString = request.getParameter("classIdArrString");
        String[] classIdArr = classIdArrString.substring(1,classIdArrString.length()-1).split(",");
        String startStopTime = request.getParameter("startStopTime");
        String diningPlace = request.getParameter("diningPlace");
        String[] timeArr;
        String startTime = "",endTime = "";
        if (StringUtils.isNotBlank(startStopTime)){
            timeArr = startStopTime.split(" - ");
            if (timeArr.length == 2){
                startTime = timeArr[0];
                endTime = timeArr[1];
            }
        }



        List<StuDiningDataStatisticsVO> list = classDiningService.getStuDiningStatisticsDataList(diningPlace,classIdArr,startTime,endTime);
        return list;
    }
    @GetMapping("/diningPlaceSelect")
    @ResponseBody
    public String[] diningPlaceSelect(){
        List<BasicParameters> basicParametersList = iBasicParametersService.list();
        String roleValue;
        String[] diningPlaceArr = {};
        SysUser sysUser = (SysUser) session.getAttribute("USER_SESSION");
        Integer userId = sysUser.getUserId();
        List<UserRole> userRoleList = iUserRoleService.getRoleIdByUserId(userId);
        UserRole userRole =  userRoleList.size()!=0?userRoleList.get(0):null;
        if (userRole!=null){
            SysRole sysRole = iSysRoleService.get(userRole.getRoleId());
            roleValue = sysRole.getRoleValue();
        }else {
            roleValue = "";
        }
        if ("就餐管理员".equals(roleValue)){
            diningPlaceArr = new String[]{"就餐管理员",sysUser.getUserType()};
        }else{
            BasicParameters basicParameters = basicParametersList.get(0);
            String diningPlaceStr = basicParameters.getEatPlace();
            diningPlaceArr = diningPlaceStr.split(",");

        }

        return diningPlaceArr;
    }
    @GetMapping("/teachDiningDataStatistics")
    @ResponseBody
    public List teachDiningDataStatistics (){
        String classIdArrString = request.getParameter("classIdArrString");
        String[] classIdArr = classIdArrString.substring(1,classIdArrString.length()-1).split(",");
        String startStopTime = request.getParameter("startStopTime");
        String diningPlace = request.getParameter("diningPlace");
        String[] timeArr;
        String startTime = "",endTime = "";
        if (StringUtils.isNotBlank(startStopTime)){
            timeArr = startStopTime.split(" - ");
            if (timeArr.length == 2){
                startTime = timeArr[0];
                endTime = timeArr[1];
            }
        }
        List<TeachDiningDataStatisticsVO> list = teachDinStatisticsService.getTeachDiningStatisticsDataList(diningPlace,classIdArr,startTime,endTime);
        return list;
    }


    //跳转备餐统计页面
    @RequestMapping("/view_prep")
    public String view1() {
        return "diningDataStatistics/diningPrep";
    }

    @PostMapping("/findPrep")
    @ResponseBody
    public void list(Model model) throws IOException {
        BTView<ClassInfoForDiningPrepVO> bt = new BTView<>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String today = request.getParameter("today");
        String diningPlace =request.getParameter("diningPlace");

       // Pages pages=classInfoService.findPrepList(pageSize,pageNumber,today,diningPlace);

        Page<ClassInfoForDiningPrepVO> page;
        page = classInfoService.findPrepList(pageNumber,pageSize,today,diningPlace);

        bt.setRows(page.getList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(page.getTotalRecords());
        super.writeJSON(bt);

    }
}
