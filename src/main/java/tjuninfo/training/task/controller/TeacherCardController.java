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
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.ISysUserService;
import tjuninfo.training.task.service.TeachDiningService;
import tjuninfo.training.task.service.TeachRecoverService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/11/13 17:19
 * @Description:
 */
@Controller
@RequestMapping(value = "/teacherCard")
public class TeacherCardController extends BaseController {

    @Autowired
    private TeachRecoverService teachRecoverService;
    @Resource
    private TeachDiningService  teachDiningService;
    @Autowired
    private ClassInfoService    classInfoService;
    @Resource
    private ISysUserService     userService;



    /**
     * 列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/view")
    public String view(Model model) {
        List<TeacherCard> teacherCardList = teachRecoverService.list();
        model.addAttribute("teacherCardList", teacherCardList);
        return "/teacher_card/teacher_card";
    }

    /**
     * 进入选择教师证页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/form")
    @ResponseBody
    public Object form(@RequestParam(value = "number", required = false) String number, Model model) {
        TeacherCard teacherCard = teachRecoverService.findByNum(number);
        if (teacherCard.getTime() == null || teacherCard.getTime().equals("")) {
            return new CmsResult(CommonReturnCode.FAILED, 0);
        } else {
            return new CmsResult(CommonReturnCode.SUCCESS, 1);
        }
    }

    /**
     * 进入教师相关信息
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/form2")
    public String form2(@RequestParam(value = "number", required = false) String number, Model model) {
        TeacherCard teacherCard = teachRecoverService.findByNum(number);
        String time = teacherCard.getTime();//流水号对应绑定的时间不为空，取出时间
        List<TeachDining> teachDiningList = teachDiningService.findTeachDiningList(number,time);//根据流水号，绑定时间从教师就餐安排表中取出数据
        TeachDining teachDining = teachDiningList.get(0);
        long classId = teachDining.getClassId();
        ClassInfo classInfo = classInfoService.get(classId);
        String className = classInfo.getClassName();
        teachDining.setClassName(className);
        teachDining.setTime(time);
        String arranger1 = userService.findUser(Integer.parseInt(teachDining.getArranger())).getUserName();
        teachDining.setArranger(arranger1);
        model.addAttribute("teachDining",teachDining);
        return "/teacher_card/teacher_card_form";
    }
    /**
     * 根据教师流水号id跳转页面
     * @param model
     * @return
     */
    @GetMapping("/teacherAndDingInfo")
    public String teacherAndDingInfo(Model model){
        //获取流水号Id
        String teacherNumber = request.getParameter("number");

        //流水号id验证
        if (teacherNumber==null||("").equals(teacherNumber)){//流水号id为空
            return "/common/noBindingInfo";
        }
        //查询流水号表获取信息
        TeacherCard teacherCard = teachRecoverService.findByNum(teacherNumber);
        if (teacherCard == null){//流水号id没有查到信息
            return "/common/noBindingInfo";
        }
        if (teacherCard.getTeacherName()!=null&&teacherCard.getTime()!=null){//流水号绑定了教师信息
            List<TeachDining> teachDiningList = teachDiningService.findTeachDiningList(teacherNumber,teacherCard.getTime());//根据流水号，绑定时间从教师就餐安排表中取出数据
            TeachDining teachDining = teachDiningList.get(0);
            SysUser sysUser = userService.get(Integer.parseInt(teachDining.getArranger()));
            long classId = teachDining.getClassId();
            ClassInfo classInfo = classInfoService.get(classId);
            String className = classInfo.getClassName();
            teachDining.setClassName(className);
            teachDining.setTime(teacherCard.getTime());
            model.addAttribute("arranger",sysUser.getUserName());
            model.addAttribute("teachDining",teachDining);
            return "/teacher_card/teacher_card_form";
        }else{//流水号没有绑定信息

            return "/common/noBindingInfo";
        }

    }

    /*
     * 查找数据表
     */
    @GetMapping(value = "/find")
    @ResponseBody
    public void upsDays(Model model,BTView<TeachDining> btView)throws IOException {
        String number = request.getParameter("number");
        String time = request.getParameter("time");
        List<TeachDining> TeachDiningList = teachDiningService.findDiningList(btView,number,time);
        btView.setRows(TeachDiningList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }






}
