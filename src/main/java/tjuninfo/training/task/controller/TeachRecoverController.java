package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.IdGen;
import tjuninfo.training.task.vo.StudentCardVO;
import tjuninfo.training.task.vo.TeachCardVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: win7
 * @Date: 2018/10/25 10:34
 * @Description:教师流水号
 */
@Controller
@RequestMapping(value = "teachRecover")
public class TeachRecoverController extends BaseController{
    @Resource
    private TeachRecoverService     teachRecoverService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private ISysRoleService sysRoleService;


    /*
     * 访问页面
     */
    @RequestMapping("/view")
    public String toStudentRecover(Model model) {
        return "teachRecover/teachRecover_list";
    }

    /*
     * 访问Table页面
     */
    @RequestMapping("/view1")
    public String toStudentRecoverTable(Model model) {
        return "teachRecover/teachRecover_table";
    }

    /*
          查找数据表
        */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public void upsDay(Model model,BTView<TeachCardVO> btView)throws IOException {
        List<TeachCardVO> TeachCardList = null;
        //查询当前登录用户
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        //当前用户角色
        SysRole sysRole = sysRoleService.get(userRoleService.getUserRoleByUserId(userInfo.getUserId()).getRoleId());
        if(sysRole.getRoleValue()!=null && sysRole.getRoleValue().trim().equals("班主任")){//用户为班主任
           TeachCardList = teachRecoverService.findList(btView,userInfo.getUserId());
        }else {
             TeachCardList = teachRecoverService.findList(btView,null);
        }
      /*  for (TeachCardVO tcl : TeachCardList) {
            String startTime = tcl.getStartStopTime().substring(0,10);
            String recoverTime = tcl.getStartStopTime().substring(13,23);
            tcl.setStartTime(startTime);
            tcl.setRecoverTime(recoverTime);
        }*/
        btView.setRows(TeachCardList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }

    /**
     * 根据流水号查找对应的学生信息
     * */
    @RequestMapping(value="findCard")
    @ResponseBody
    public Object findCard(Model model,String number){
        String teacherName = null;
        TeacherCard teacherCard  = teachRecoverService.findByNum(number);
        if(teacherCard == null ){
            teacherName = "0";//无对应流水号 赋0
        }else {
            try {
                if(null==teacherCard.getTeacherName() || teacherCard.getTeacherName().equals("")){
                    teacherName = "1";//有对应的流水号，但教师姓名为空 赋1
                }else {
                    teacherName = teacherCard.getTeacherName();//有对应的流水号，且教师姓名不为空 赋教师姓名
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("teacherName",teacherName);
        return map;
    }

    /**根据教师流水号回收*/
    @RequestMapping(value = "/recover")
    @ResponseBody
    public Object toRecoverStudent(TeacherCard TeacherCard, @RequestParam(value="number",required=false)String number){
        teachRecoverService.recoverTeachList(number.trim());
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }


    /**整班流水号回收*/
    @RequestMapping(value = "/recoverClass")
    @ResponseBody
    public Object toRecoverClass(TeacherCard TeacherCard,@RequestParam(value="classId",required=false)BigInteger classId){
        teachRecoverService.recoverClassList(classId);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }


}
