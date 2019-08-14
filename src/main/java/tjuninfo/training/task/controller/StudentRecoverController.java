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
import tjuninfo.training.task.vo.TrainRecordVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @Auther: win7
 * @Date: 2018/10/25 14:44
 * @Description:学员流水号回收
 */
@Controller
@RequestMapping(value ="stuRecover")
public class StudentRecoverController extends BaseController {
    @Resource
    private StudentRecoverService   studentRecoverService;
    @Autowired
    private ISysRoleService         sysRoleService;
    @Autowired
    private IUserRoleService userRoleService;

    /*
     * 访问页面
     */
    @RequestMapping("/view")
    public String toStudentRecover(Model model) {
        return "stuRecover/stuRecover_list";
    }

    /*
     * 访问Table页面
     */
    @RequestMapping("/view1")
    public String toStudentRecoverTable(Model model) {
        return "stuRecover/stuRecover_table";
    }

    /*
          查找班级数据表
        */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public void upsDay(Model model,BTView<StudentCardVO> btView)throws IOException {
        List<StudentCardVO> StudentCardList= null;
        //查询当前登录用户
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        //当前用户角色
        SysRole sysRole = sysRoleService.get(userRoleService.getUserRoleByUserId(userInfo.getUserId()).getRoleId());
        if(sysRole.getRoleValue()!=null && sysRole.getRoleValue().trim().equals("班主任")){//用户为班主任
            StudentCardList = studentRecoverService.findList(btView,userInfo.getUserId());
        }else {
            StudentCardList = studentRecoverService.findList(btView,null);
        }
//         StudentCardList = studentRecoverService.findList(btView);
 /*       for (StudentCardVO scl : StudentCardList) {
            String startTime = scl.getStartStopTime().substring(0, 10);
            String recoverTime = scl.getStartStopTime().substring(13, 23);
            scl.setStartTime(startTime);
            scl.setRecoverTime(recoverTime);
        }*/
        btView.setRows(StudentCardList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }

    /**
     * 根据流水号查找对应的学生信息
     * */
    @RequestMapping(value="findCard")
    @ResponseBody
    public Object findCard(Model model,String number){
        Integer studentId = null;
        StudentCard studentCard  = studentRecoverService.findCardBynumb(number);
        if(studentCard.getId() == null){
            studentId = 0;//无对应流水号 赋0
        }else {
            try{
                if(null==studentCard.getStudentId() || studentCard.getStudentId().equals("")){
                    studentId = 1;//有对应的流水号，但学员ID为空 赋1
                }else {
                    studentId = studentCard.getStudentId().intValue();//有对应的流水号，且学员ID不为空 赋学员ID
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId",studentId);
        return map;
    }

    /**
     * 根据学员流水号回收
     */
    @RequestMapping(value = "/recover")
    @ResponseBody
    public Object toRecoverStudent(StudentCard studentCard, @RequestParam(value = "number", required = false) String number) {
        studentRecoverService.recoverStuList(number.trim());
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }


    /**
     * 整班流水号回收
     */
    @RequestMapping(value = "/recoverClass")
    @ResponseBody
    public Object toRecoverClass(StudentCard studentCard, @RequestParam(value = "classId", required = false) BigInteger classId) {
        studentRecoverService.recoverClassList(classId);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }




}
