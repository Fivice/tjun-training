package tjuninfo.training.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.vo.TeachDiningRecordVO;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/12 11:14
 * @Description:教师就餐记录
 */

@Controller
@RequestMapping(value = "teachDining")
public class TeachDinRecordController extends BaseController {

    @Resource
    private TeachDinRecordService   teachDinRecordService;
    @Autowired
    private IBasicParametersService basicParametersService;
    @Resource
    private ClassInfoService        classInfoService;
    @Resource
    private IUserRoleService        userRoleService;
    @Resource
    private ISysRoleService sysRoleService;
    @Autowired
    private TeachRecoverService teachRecoverService;


    /**
     * 查看页面
     * */
    @RequestMapping("/view")
    public String toDining(Model model) {
        List<BasicParameters> basicParameters = basicParametersService.list();//院校基本参数列表
        String eatPlaces = basicParameters.get(0).getEatPlace();//获取就餐地点
        List<String> diningPlaceList = new ArrayList<String>();
        String[] res = eatPlaces.split("[，,]");
        for (int i = 0; i < res.length; i++) {
            String eatPlace = res[i];
            diningPlaceList.add(eatPlace);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String month = sdf.format(date);
        model.addAttribute("month", month);
        model.addAttribute("list",diningPlaceList);
        return "teachDinRecord/teachDinRecord_list";
    }

    /*
           查找学员就餐数据统计
         */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public void upsDay(Model model,BTView<TeachDiningRecordVO> btView)throws IOException {
        String schoolName = null;
        Integer classRoom = null;
        String className = null;
        String month = null;
        Integer roleId = null;
        SysUser user = getUser();
        Integer userId = user.getUserId();
        SysRole sysRole= new SysRole();
        try {
            request.setCharacterEncoding("utf-8");
//            schoolName = new String(request.getParameter("schoolName").getBytes("iso8859-1"),"utf-8").trim();
//            className = new String(request.getParameter("className").getBytes("iso8859-1"),"utf-8").trim();
//            month = new String(request.getParameter("month").getBytes("iso8859-1"),"utf-8").trim();
            schoolName = request.getParameter("schoolName").trim();
            className =  request.getParameter("className").trim();
            month =  request.getParameter("month").trim();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String classNum = request.getParameter("classRoom").trim();


        if(classNum!=null&&classNum!=""){
            ClassInfo list = classInfoService.getByclassNumber(classNum);
            if(list!=null){
                classRoom  = list.getId().intValue();
            }else {
                classRoom = 99999999;
            }//根据传进来的班级编号去班级查找对应的班级id;不为空就把id带过来;否则带一个不存在的班级id，防止id为空把所有数据查出
        }else {
            classRoom = null;
        }
        List<UserRole> roleList = userRoleService.getRoleIdByUserId(userId);
        for (UserRole l : roleList){
            sysRole = sysRoleService.get(l.getRoleId());
            roleId = l.getRoleId().intValue();
        }
        List<TeachDiningRecordVO> teachList = null;
        if(!sysRole.getRoleValue().equals("班主任")){
            teachList = teachDinRecordService.findSum(btView,schoolName,classRoom,className,month);
        }else {
            teachList = teachDinRecordService.findSum(btView,schoolName,classRoom,className,userId,month);
        }
        btView.setRows(teachList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }

    /**
     *  查看学员就餐详情
     * @param model
     * @return
     */
    @GetMapping(value = "/form")
    public String form(TeachDiningRecordVO teachDiningRecordVO, Model model, @RequestParam(value="num",required=false)String num,@RequestParam(value="classId",required=false)Integer classId) {
        model.addAttribute("num",num);
        model.addAttribute("classId",classId);
        model.addAttribute("teacherName",teachRecoverService.findByNum(num).getTeacherName());
        model.addAttribute("classInfo",classInfoService.get(Long.valueOf(classId)));
        return "/teachDinRecord/teachDinRecord_Form";
    }

    /*
          学员就餐详情数据表
        */
    @RequestMapping(value = "/find")
    @ResponseBody
    public void ups(Model model,@RequestParam(value="num",required=false)String num,@RequestParam(value="classId",required=false)Integer classId,BTView<TeachDiningRecord> btView)throws IOException {
        List<TeachDiningRecord> list = teachDinRecordService.findList(btView,num,classId);
        btView.setRows(list);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }


}
