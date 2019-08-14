package tjuninfo.training.task.controller;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.ClassDiningService;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: win7
 * @Date: 2018/9/29 10:01
 * @Description:
 */

@Controller
@RequestMapping(value = "/classDining")
public class ClassDiningController extends BaseController{

    @Resource
    private ClassDiningService classDiningService;
    @Resource
    private ClassInfoService classInfoService;
    /*
     * 访问页面
     */
    @RequestMapping("/view")
    public String toClassDining(@RequestParam(value="id",required=false)String id,Model model) {
        model.addAttribute("classInfo",classInfoService.get( Long.parseLong(id)));
        return "classDining/classDining_list";
    }
    /*
     * 访问页面
     */
    @RequestMapping("/view2")
    public String toClassDining2(@RequestParam(value="id",required=false)String id,Model model) {
        model.addAttribute("classInfo",classInfoService.get( Long.parseLong(id)));
        return "classDining/classDining_list2";
    }
    /*
          查找数据表
        */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public Object upsDay(@RequestParam(value="id",required=false)String id) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        Map<String, Object> map = Maps.newHashMap();
        if(StringUtils.isNotBlank(id)){
            ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
            //根据班级ID进行查询分页
            Pages pages = classDiningService.findClassDiningList(pageSize,pageNumber,String.valueOf(classInfo.getId()));

            map.put("rows",  pages.getResult());
            map.put("total", pages.getTotalResults());
        }
        return map;
    }

    /*
     * GET 添加页面
     */
    @RequestMapping(value= "/create")
    public String getInsertPage(Model model,@RequestParam(value="id",required=false)String id) throws ParseException {
        ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
        model.addAttribute("classInfo",classInfo);
        String startTime = classInfo.getStartStopTime().substring(0,10);
        List<ClassDining> ClassDiningList = classDiningService.findClassDiningList(String.valueOf(classInfo.getId()));
        if(ClassDiningList.size()>0){
            startTime= ClassDiningList.get(ClassDiningList.size()-1).getDay();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date utilDate = simpleDateFormat.parse(startTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(utilDate);//设置起时间
            cal.add(Calendar.DATE, 1);//增加一天  
            startTime = simpleDateFormat.format(cal.getTime());
        }else {
           startTime=startTime;
        }
        model.addAttribute("startTime",startTime);
        return "classDining/classDining_add";
    }

    /*
     * POST 保存信息
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object insert(ClassDining classDining) {
        classDiningService.saveOrUpdate(classDining);
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
            ClassDining classDining = classDiningService.get(id);
            classDining.setBreakfast(2);
            classDining.setLunch(2);
            classDining.setDinner(2);
            classDiningService.update(classDining);
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /*
     * GET 更新页面
     */
    @GetMapping(value = "/update")
    public String getUpdatePage(Model model,@RequestParam(value = "id", required = false) String id) {
        ClassDining classDining = classDiningService.get(Integer.valueOf(id));
        model.addAttribute("classDining",classDining);
        return "classDining/classDining_update";
    }
    /*
     * PUT 根据Id来更新对象
     */
    @RequestMapping(value = "/saveClassDining")
    @ResponseBody
    public Object update(ClassDining classDining) {
        classDiningService.update(classDining);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

    /*
     * 初始化
     */
    @RequestMapping(value = "/init")
    @ResponseBody
    public Object init(@RequestParam(value="id",required=false)String id) throws ParseException {
        ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
        classDiningService.deleteByClassNumber(String.valueOf(classInfo.getId()));
        String startTime = classInfo.getStartStopTime().substring(0,10);
        String endTime = classInfo.getStartStopTime().substring(13,23);
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        //开始时间必须小于结束时间
        Date beginDate = dateFormat1.parse(startTime);
        Date endDate = dateFormat1.parse(endTime);
        Date date = beginDate;

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = dateFormat1.parse(sdf.format(new Date()));
        ///开始时间小于现在
            if(endDate.getTime()>nowDate.getTime()&&nowDate.getTime()>beginDate.getTime()){
                date = nowDate;}
        if(nowDate.equals(endDate)||endDate.getTime()<nowDate.getTime()){
            date = nowDate;}

        String time = null;
        if (date.getTime()<=endDate.getTime()) {
        while (!date.equals(endDate) ) {
            time = dateFormat1.format(date);
            ClassDining classDining = new ClassDining();
            if(time.equals(startTime)){
                classDining.setClassRoom(String.valueOf(classInfo.getId()));
                classDining.setDay(time);
                classDining.setBreakfast(2);
                classDining.setLunch(2);
                classDining.setDinner(1);
                classDiningService.save(classDining);
            }else {
                classDining.setClassRoom(String.valueOf(classInfo.getId()));
                classDining.setDay(time);
                classDining.setBreakfast(1);
                classDining.setLunch(1);
                classDining.setDinner(1);
                classDiningService.save(classDining);
            }
            c.setTime(date);
            c.add(Calendar.DATE, 1); // 日期加1天
            date = c.getTime();
        }
        ClassDining classDining1 = new ClassDining();
        classDining1.setClassRoom(String.valueOf(classInfo.getId()));
        classDining1.setDay(endTime);
        classDining1.setBreakfast(1);
        classDining1.setLunch(1);
        classDining1.setDinner(2);
        classDiningService.save(classDining1);
            return new CmsResult(CommonReturnCode.SUCCESS, 1);
        }else {
            return new CmsResult(CommonReturnCode.FAILED, 0);
        }

    }

    /*
     * 清空
     */
    @RequestMapping(value = "/empty")
    @ResponseBody
    public Object empty(@RequestParam(value="id",required=false)String id)  {
        ClassInfo classInfo = classInfoService.get(Long.parseLong(id));
        classDiningService.deleteByClassNumber(String.valueOf(classInfo.getId()));
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }



}
