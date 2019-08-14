package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.entity.Classroom;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.ICampusService;
import tjuninfo.training.task.service.IClassroomService;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域及教室表表示层控制器
 */
@Controller
@RequestMapping(value = "/classroom")
public class ClassroomController extends BaseController{
    @Autowired
    private IClassroomService classroomService;
    @Autowired
    private ICampusService campusService;


    @GetMapping(value = "/view")
    public String list( Model model){
        model.addAttribute("campusList",campusService.list());
        return "/classroom/classroom_list";
    }

    @RequestMapping(value = "/findTable")
    @ResponseBody
    public Object findTable() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> map1 = Maps.newHashMap();
        String schoolName =request.getParameter("schoolName").trim();
        String classType = request.getParameter("classType");
        String className = request.getParameter("className").trim();
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        Pages pages = classroomService.list(pageSize,pageNumber,schoolName,classType,className);
        List<Classroom> classroomList = pages.getResult();
        for(Classroom classroom : classroomList){
            Map<String, Object> map = Maps.newHashMap();
            Campus campus = campusService.get(classroom.getSchoolName());
            map.put("campus",campus);
            map.put("classroom",classroom);
            mapList.add(map);
        }
        
        map1.put("rows",  mapList);
        map1.put("total", pages.getTotalResults());
        return map1;

    }

    /**
     * 查询教室列表
     * @return
     */
    @GetMapping(value = "/findList")
    @ResponseBody
    public Object findList() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Classroom> classroomList = classroomService.list();
        for (Classroom classroom : classroomList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("classroom", classroom);
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * GET 添加教室页面
     * @return
     */
    @GetMapping(value = "/create")
    public String getInsertPage(Model model) {
        model.addAttribute("campusList",campusService.list());
        return "/classroom/classroom_add";
    }

    /**
     * 判断教室名称是否已存在
     */
    @RequestMapping(value = "/getClassName")
    @ResponseBody
    public Object getClassName(@RequestParam String schoolName,@RequestParam String className,@RequestParam String id) {
        Map<String, Object> map = Maps.newHashMap();

        if(null !=className && !className.equals("")){
           Classroom classroom = classroomService.getByProp(schoolName,className,id);
            if(classroom==null){
                map.put("valid",true);
            }else{
                map.put("valid",false);
            }

        }

        return map;
    }



    /**
     * 保存教室
     * @param classroom
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(Classroom classroom){
//        classroom.setSjId(0);
//        classroom.setType(1);
        classroom.setaState(1);
        classroomService.save(classroom);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /**
     * DELETE 删除校区或教室
     * @param iNumbers
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "iNumbers", required = false) String iNumbers) {
        String[] result = iNumbers.split(",");

        for (int i = 0; i < result.length; i++) {
            String id = result[i];
            Classroom classroom = classroomService.get(Integer.valueOf(id));
            classroom.setaState(2);
            classroomService.update(classroom);
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /**
     * 编辑教室页面
     * @param model，id
     * @return
     */
    @GetMapping(value = "/update")
    public String update(@RequestParam(value = "id", required = false) String id,Model model) {
        model.addAttribute("campusList",campusService.list());
        model.addAttribute("classroom",classroomService.get(Integer.valueOf(id)));
        return "/classroom/classroom_update";

    }


    /**
     * 保存新的教室信息
     *
     * @param classroom
     * @return
     */
    @RequestMapping(value = "/saveClassroom")
    @ResponseBody
    public Object update(Classroom classroom) {
        classroomService.update(classroom);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /**
     * GET 添加校区页面
     * @return
     */
    @GetMapping(value = "/addCampus")
    public String getInsertPageCampus(Model model) {
        return "/classroom/campus_add";
    }

    /**
     * 判断用户账号是否已存在
     * @throws IOException
     */
    @RequestMapping(value = "/getSchoolName")
    @ResponseBody
    public Object getUserNameCount(@RequestParam String schoolName) {
        Map<String, Object> map = Maps.newHashMap();
        if(null !=schoolName && !schoolName.equals("")){
            Long count = campusService.getCountByProerties(new String[]{"schoolName"}, new String[]{schoolName});
            if(count==0){
                map.put("valid",true);
            }else{
                map.put("valid",false);
            }

        }

        return map;
    }

    /**
     * 保存校区
     * @param campus
     * @return
     */
    @RequestMapping(value = "/saveCampus")
    @ResponseBody
    public Object saveCampus(Campus campus){
        campusService.save(campus);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /*校区列表访问页面*/
    @RequestMapping(value = "/campusList")
    public String campusList(){
//        model.addAttribute("campusList",campusService.list());
        return "/classroom/campusList";
    }

    /*校区列表数据加载*/
    @RequestMapping(value = "/campusListTable")
    @ResponseBody
    public Object campusListTable() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Campus> campusList = campusService.list();
        for(Campus campus: campusList){
            Map<String, Object> map = Maps.newHashMap();
            map.put("campus",campus);
            mapList.add(map);
        }
        return mapList;

    }

    /*
     * 校区表中行内编辑
     */
    @RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(@RequestParam(value="id",required=false)String id,@RequestParam(value="schoolName",required=false)String schoolName)  {
        List<Campus> campuses=campusService.getByIdAndSchoolName(id,schoolName);
        if(campuses.size()>0){
            return new CmsResult(CommonReturnCode.FAILED, 0);
        }else {
            Campus campus = new Campus();
            campus.setId(Integer.valueOf(id));
            campus.setSchoolName(schoolName);
            campusService.update(campus);
            return new CmsResult(CommonReturnCode.SUCCESS, 1);
        }
    }

    /**
     * DELETE 删除校区或教室
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteCampus")
    @ResponseBody
    public Object deleteCampus(@RequestParam(value = "id", required = false) String id) {
        List<Classroom> classroomList = classroomService.getClassroomBySchool(Integer.valueOf(id));
//        Campus campus = campusService.get(Integer.valueOf(id));
        if(classroomList.size()>0){
            return new CmsResult(CommonReturnCode.FAILED, 0);
        }else {
            campusService.deleteByPK(Integer.valueOf(id));
            return new CmsResult(CommonReturnCode.SUCCESS, 1);
        }

    }


}
