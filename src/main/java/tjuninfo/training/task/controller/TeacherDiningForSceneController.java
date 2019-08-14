package tjuninfo.training.task.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.dto.ClassInfoForSceneDTO;
import tjuninfo.training.task.dto.TeacherInfoForSceneDTO;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.entity.TeacherDiningForScene;
import tjuninfo.training.task.entity.TeacherDiningRegister;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.ITeacherDiningForSceneService;
import tjuninfo.training.task.service.ITeacherDiningRegisterService;
import tjuninfo.training.task.service.TeachDinFaceRecordService;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.ClassInfoForSceneVO;
import tjuninfo.training.task.vo.TeacherInfoForSceneVO;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/TeacherDiningForScene")
public class TeacherDiningForSceneController extends BaseController {
    @Autowired
    private ITeacherDiningForSceneService iTeacherDiningForSceneService;
    @Autowired
    private ITeacherDiningRegisterService iTeacherDiningRegisterService;
    @Autowired
    private TeachDinFaceRecordService teachDinFaceRecordService;
    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 查询教师绑定的就餐班级信息
     * @throws IOException
     */
    @PostMapping(value = "/teacherInfoBindClass")
    @ResponseBody
    public void teacherInfoBindClass() throws IOException {
        BTView<TeacherInfoForSceneVO> bt = new BTView<>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String teacherName = request.getParameter("teacherName");
        Page page = iTeacherDiningForSceneService.getTeacherInfoForScene(pageSize,pageNumber,teacherName);

        bt.setRows(page.getList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(page.getTotalRecords());
        super.writeJSON(bt);
    }

    /**
     * 查询班级名下绑定的就餐教师
     * @throws IOException
     */
    @PostMapping(value = "/classInfoBindClass")
    @ResponseBody
    public void classInfoBindClass() throws IOException {
        BTView<ClassInfoForSceneVO> bt = new BTView<>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        String className = request.getParameter("className");
        Page page = iTeacherDiningForSceneService.getClassInfoForScene(pageSize,pageNumber,className);

        bt.setRows(page.getList());
        bt.setPageSize(pageSize);
        bt.setPageNumber(pageNumber);
        bt.setTotal(page.getTotalRecords());
        super.writeJSON(bt);
    }

    /**
     * 将教师绑定到正在开班班级的绑定操作
     * @return
     */
    @PostMapping(value = "/bindOperate")
    @ResponseBody
    public Object bindOperate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        JSONObject jsonObject = new JSONObject();
        String teacherId = request.getParameter("teacherId");
        String classId = request.getParameter("classId");
        int tid = Integer.parseInt(teacherId);

        TeacherDiningRegister teacherDiningRegister = null;
        try {
            teacherDiningRegister = iTeacherDiningRegisterService.getTeacherBindClassInfo(tid);
        }catch (Exception e ){
            e.printStackTrace();
        }

        if (teacherId.isEmpty()){
            jsonObject.put("message", CommonReturnCode.FAILED);
            return jsonObject;
        }else if (classId.isEmpty()){
            jsonObject.put("message", CommonReturnCode.FAILED);
            return jsonObject;
        }else if(classInfoService.get(Long.parseLong(classId)).getDiningPlace().isEmpty()){
            jsonObject.put("message", CommonReturnCode.FAILED);
            return jsonObject;
        }else {
            if (teacherDiningRegister == null){
                TeacherDiningRegister tdr = new TeacherDiningRegister();
                tdr.setTeacherId(Integer.parseInt(teacherId));
                tdr.setClassId(BigInteger.valueOf(Long.parseLong(classId)));
                tdr.setRegTime(sdf.format(new Date()));
                iTeacherDiningRegisterService.save(tdr);
                jsonObject.put("message", CommonReturnCode.SUCCESS);
            }
        }
        return jsonObject;
    }

    /**
     * 查询教师的就餐安排信息
     * @throws IOException
     */
    @PostMapping(value = "/teacherDiningInfo")
    @ResponseBody
    public void teacherDiningInfo() throws IOException {
        BTView<TeacherDiningForScene> bt = new BTView<>();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        Integer teacherId = Integer.valueOf(request.getParameter("teacherId"));
        long classId = Long.parseLong(request.getParameter("classId"));
        Page page = iTeacherDiningForSceneService.getTeacherDiningForScene(pageSize,pageNumber,teacherId,classId);

        bt.setRows(page.getList());
        bt.setTotal(page.getTotalRecords());
        bt.setPageNumber(pageNumber);
        bt.setPageSize(pageSize);
        super.writeJSON(bt);
    }

    /**
     * 初始化教师的就餐安排信息
     * @param model
     */
    @PostMapping(value = "/initTeacherDining")
    @ResponseBody
    public Object initTeacherDining(Model model){
        JSONObject jsonObject = new JSONObject();
        String teacherId = request.getParameter("teacherId");
        String classId = request.getParameter("classId");
        String startTime = request.getParameter("startTime");
        String stopTime = request.getParameter("stopTime");
        //获取当前用户
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        //初始化就餐安排

        if (!teacherId.isEmpty()&&!classId.isEmpty()&&!startTime.isEmpty()&&!stopTime.isEmpty()){
            TeacherDiningRegister teacherDiningRegister = iTeacherDiningRegisterService.getTeacherDiningRegister(Integer.parseInt(teacherId),Long.parseLong(classId));
            List<TeacherDiningForScene> teacherDiningForSceneList = iTeacherDiningForSceneService.getTeacherDining(teacherDiningRegister.getId());
            if (teacherDiningForSceneList.size()!= 0){
                jsonObject.put("message","教师就餐已经初始化过，请先解绑");
            }else if (DateUtil.StringToDate(startTime,"yyyy-MM-dd").getTime() <= DateUtil.StringToDate(stopTime,"yyyy-MM-dd").getTime()){
                iTeacherDiningForSceneService.teacherDiningInit(Integer.parseInt(teacherId),Long.parseLong(classId),userInfo.getUserId(),startTime,stopTime);
                jsonObject.put("message","初始化成功");
            }else {
                jsonObject.put("message",CommonReturnCode.BAD_REQUEST);
            }
        }else {
            jsonObject.put("message",CommonReturnCode.BAD_REQUEST);
        }

        return jsonObject;
    }

    /**
     * 更新单条教师就餐安排记录
     * @param model
     */
    @PostMapping(value = "/updateTeacherDining")
    @ResponseBody
    public Object updateTeacherDining(Model model){
        JSONObject jsonObject = new JSONObject();

        String dinArr = request.getParameter("dinArr");
        JSONArray JsonArr = JSON.parseArray(dinArr);
                //获取当前用户
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
        // 修改就餐安排
        JSONObject js = new JSONObject();
        for (int i = 0; i < JsonArr.size(); i++) {
            js = JsonArr.getJSONObject(i);
            int id = Integer.parseInt(js.getString("id"));
            String breakfast = js.getString("breakfast");
            String lunch = js.getString("lunch");
            String dinner = js.getString("dinner");
            TeacherDiningForScene teacherDiningForScene = iTeacherDiningForSceneService.get(id);
            teacherDiningForScene.setBreakfast("1".equals(breakfast)?1:2);
            teacherDiningForScene.setLunch("1".equals(lunch)?1:2);
            teacherDiningForScene.setDinner("1".equals(dinner)?1:2);

            iTeacherDiningForSceneService.update(teacherDiningForScene);
        }

        jsonObject.put("message",CommonReturnCode.SUCCESS);

        return jsonObject;
    }

    /**
     * 人脸识别教师就餐绑定班级解绑操作
     * @param model
     * @return
     */
    @PostMapping(value = "/unBindOperate")
    @ResponseBody
    public Object unBindOperate(Model model){
        JSONObject jsonObject = new JSONObject();
        String teacherId = request.getParameter("teacherId");
        String classId = request.getParameter("classId");
        int tid = Integer.parseInt(teacherId);
        int cid = Integer.parseInt(classId);
        List list = teachDinFaceRecordService.getTeachDiningFaceRecord(tid,cid);
        if (list.size()!=0){
            jsonObject.put("message",CommonReturnCode.LOCKED);
        }
        // 删除安排
        //查找教师绑定注册id
        TeacherDiningRegister teacherDiningRegister = iTeacherDiningRegisterService.getTeacherDiningRegister(tid,cid);
        long teacherDiningRegId = teacherDiningRegister.getId().longValue();
        //根据绑定id去安排表中删除所有此id下的数据
        iTeacherDiningForSceneService.deleteTeacherDiningForScene(teacherDiningRegId);
        // 删除绑定
        iTeacherDiningRegisterService.delete(teacherDiningRegister);
        jsonObject.put("message",CommonReturnCode.SUCCESS);

        return jsonObject;
    }
}
