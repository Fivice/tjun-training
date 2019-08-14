package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.StuDiningDataStatisticsVO;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningDataStatisticsVO;
import tjuninfo.training.task.vo.TeachFirstDiningStatisticsVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: win7
 * @Description: 就餐统计*/
@Controller
@RequestMapping(value = "diningFirstCount")
public class DinFirstStatisticsController extends BaseController {

    @Resource
    private StuFirstDinStatisticsService stuFirstDinStatisticsService;
    @Resource
    private TeachFirstDinStatisticsService teachFirstDinStatisticsService;
    @Autowired
    private IBasicParametersService basicParametersService;

    /*
     * 学生首日就餐统计访问页面
     */
    @RequestMapping("/stuView")
    public String toDining(Model model) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String endDay =df.format(new Date());// new Date()为获取当前系统时间
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - 6);
        Date d = c.getTime();
        String startDay = df.format(d);
        String startStopTime = startDay + " 至 " +  endDay ;
        model.addAttribute("startStopTime",startStopTime);
    return "dinFirstStatistics/stuDinFirstStatistics_list";
    }

        /*
           查找学员首日就餐数据统计
         */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public Object upsDay(){
        //院校基本参数列表
        List<BasicParameters> basicParameters = basicParametersService.list();
        //获取就餐标准
        String eatStandard = basicParameters.get(0).getEatStandard();
        String[] eatStandards = eatStandard.split("[，,]");
        BigDecimal eatStandard0 = new BigDecimal(eatStandards[0].trim());
        BigDecimal eatStandard1 = new BigDecimal(eatStandards[1].trim());
        BigDecimal eatStandard2 = new BigDecimal(eatStandards[2].trim());
        String startDay = null;
        String endDay = null;
        String startStopTime = request.getParameter("startStopTime");
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
        if (startStopTime!=null && !startStopTime.equals("")){
            startDay = startStopTime.substring(0,10);
            endDay = startStopTime.substring(13,23);
        }
        Map<String, Object> map = Maps.newHashMap();
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Page page = stuFirstDinStatisticsService.count(pageSize,pageNumber,startDay,endDay);
        List<StuFirstDiningStatisticsVO> StuList = page.getList();
        for (StuFirstDiningStatisticsVO sdrlVO : StuList) {
            Map<String, Object> mapvo = Maps.newHashMap();
            mapvo.put("sdrlVO", sdrlVO);
            mapvo.put("eatStandard0", eatStandard0);
            mapvo.put("eatStandard1", eatStandard1);
            mapvo.put("eatStandard2", eatStandard2);
            mapList.add(mapvo);
        }
        map.put("rows",  mapList);
        map.put("total", page.getTotalRecords());
        return map;
    }

    /*
     * 教师首日就餐统计访问页面
     */
    @RequestMapping("/teaView")
    public String toTeachDining(Model model) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String endDay =df.format(new Date());// new Date()为获取当前系统时间
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - 6);
        Date d = c.getTime();
        String startDay = df.format(d);
        String startStopTime = startDay + " 至 " +  endDay ;
        model.addAttribute("startStopTime",startStopTime);
        return "dinFirstStatistics/teachFirstDinStatistics_list";
    }

    /*
     查找教师首日就餐数据统计
   */
    @RequestMapping(value = "/findTeachTable")
    @ResponseBody
    public Object teachDing() {
        //院校基本参数列表
        List<BasicParameters> basicParameters = basicParametersService.list();
        //获取就餐标准
        String eatStandard = basicParameters.get(0).getEatStandard();
        String[] eatStandards = eatStandard.split("[，,]");
        BigDecimal eatStandard0 = new BigDecimal(eatStandards[0].trim());
        BigDecimal eatStandard1 = new BigDecimal(eatStandards[1].trim());
        BigDecimal eatStandard2 = new BigDecimal(eatStandards[2].trim());
        String startDay = null;
        String endDay = null;
        String startStopTime = request.getParameter("startStopTime");
        if (startStopTime!=null && !startStopTime.equals("")){
            startDay = startStopTime.substring(0,10);
            endDay = startStopTime.substring(13,23);
        }
        List<Map<String, Object>> mapList = Lists.newArrayList();
        //流水号教师首日就餐数据
        List<TeachFirstDiningStatisticsVO> teachList = teachFirstDinStatisticsService.count(startDay,endDay);
        //人脸数据教师首日就擦数据
        List<TeachFirstDiningStatisticsVO> teachFaceList = teachFirstDinStatisticsService.count2(startDay,endDay);
        if(teachList.size() > 0 && teachFaceList.size()>0){
            for (TeachFirstDiningStatisticsVO sdrlVO : teachList) {
                TeachFirstDiningStatisticsVO list = new TeachFirstDiningStatisticsVO();
                if(null == sdrlVO.getrBreakfast() ){
                    sdrlVO.setrBreakfast(new BigDecimal("0"));
                }
                if(null == sdrlVO.getrDinner() ){
                    sdrlVO.setrDinner(new BigDecimal("0"));
                }
                if(null == sdrlVO.getrLunch() ){
                    sdrlVO.setrLunch(new BigDecimal("0"));
                }
                for(TeachFirstDiningStatisticsVO sdrlFaceVO : teachFaceList){
                    if(null == sdrlFaceVO.getrBreakfast() ){
                        sdrlFaceVO.setrBreakfast(new BigDecimal("0"));
                    }
                    if(null == sdrlFaceVO.getrDinner() ){
                        sdrlFaceVO.setrDinner(new BigDecimal("0"));
                    }
                    if(null == sdrlFaceVO.getrLunch() ){
                        sdrlFaceVO.setrLunch(new BigDecimal("0"));
                    }
                    if(sdrlVO.getDiningPlace().equals(sdrlFaceVO.getDiningPlace())){
                        list.setDiningPlace(sdrlVO.getDiningPlace());
                        list.setrBreakfast(sdrlVO.getrBreakfast().add(sdrlFaceVO.getrBreakfast()));
                        list.setdBreakfast(sdrlVO.getdBreakfast().add(sdrlFaceVO.getdBreakfast()));
                        list.setdLunch(sdrlVO.getdLunch().add(sdrlFaceVO.getdLunch()));
                        list.setrLunch(sdrlVO.getrLunch().add(sdrlFaceVO.getrLunch()));
                        list.setdDinner(sdrlVO.getdDinner().add(sdrlFaceVO.getdDinner()));
                        list.setrDinner(sdrlVO.getrDinner().add(sdrlFaceVO.getrDinner()));
                    }else{
                        if(null != sdrlVO.getDiningPlace() && !sdrlVO.getDiningPlace().equals("")){
                            list.setDiningPlace(sdrlVO.getDiningPlace());
                            list.setrBreakfast(sdrlVO.getrBreakfast());
                            list.setdBreakfast(sdrlVO.getdBreakfast());
                            list.setdLunch(sdrlVO.getdLunch());
                            list.setrLunch(sdrlVO.getrLunch());
                            list.setdDinner(sdrlVO.getdDinner());
                            list.setrDinner(sdrlVO.getrDinner());
                        }else {
                            list.setDiningPlace(sdrlFaceVO.getDiningPlace());
                            list.setrBreakfast(sdrlFaceVO.getrBreakfast());
                            list.setdBreakfast(sdrlFaceVO.getdBreakfast());
                            list.setdLunch(sdrlFaceVO.getdLunch());
                            list.setrLunch(sdrlFaceVO.getrLunch());
                            list.setdDinner(sdrlFaceVO.getdDinner());
                            list.setrDinner(sdrlFaceVO.getrDinner());
                        }
                    }
                }
                Map<String, Object> mapvo = Maps.newHashMap();
                mapvo.put("list", list);
                mapvo.put("eatStandard0", eatStandard0);
                mapvo.put("eatStandard1", eatStandard1);
                mapvo.put("eatStandard2", eatStandard2);
                mapList.add(mapvo);
            }
        }else if(teachList.size() > 0 ){
            for (TeachFirstDiningStatisticsVO sdrlVO : teachList) {
                TeachFirstDiningStatisticsVO list = new TeachFirstDiningStatisticsVO();
                if(null == sdrlVO.getrBreakfast() ){
                    sdrlVO.setrBreakfast(new BigDecimal("0"));
                }
                if(null == sdrlVO.getrDinner() ){
                    sdrlVO.setrDinner(new BigDecimal("0"));
                }
                if(null == sdrlVO.getrLunch() ){
                    sdrlVO.setrLunch(new BigDecimal("0"));
                }
                list.setDiningPlace(sdrlVO.getDiningPlace());
                list.setrBreakfast(sdrlVO.getrBreakfast());
                list.setdBreakfast(sdrlVO.getdBreakfast());
                list.setdLunch(sdrlVO.getdLunch());
                list.setrLunch(sdrlVO.getrLunch());
                list.setdDinner(sdrlVO.getdDinner());
                list.setrDinner(sdrlVO.getrDinner());
                Map<String, Object> mapvo = Maps.newHashMap();
                mapvo.put("list", list);
                mapvo.put("eatStandard0", eatStandard0);
                mapvo.put("eatStandard1", eatStandard1);
                mapvo.put("eatStandard2", eatStandard2);
                mapList.add(mapvo);
            }
        }else {
            for (TeachFirstDiningStatisticsVO sdrlFaceVO : teachFaceList) {
                TeachFirstDiningStatisticsVO list = new TeachFirstDiningStatisticsVO();
                if (null == sdrlFaceVO.getrBreakfast()) {
                    sdrlFaceVO.setrBreakfast(new BigDecimal("0"));
                }
                if (null == sdrlFaceVO.getrDinner()) {
                    sdrlFaceVO.setrDinner(new BigDecimal("0"));
                }
                if (null == sdrlFaceVO.getrLunch()) {
                    sdrlFaceVO.setrLunch(new BigDecimal("0"));
                }
                list.setDiningPlace(sdrlFaceVO.getDiningPlace());
                list.setrBreakfast(sdrlFaceVO.getrBreakfast());
                list.setdBreakfast(sdrlFaceVO.getdBreakfast());
                list.setdLunch(sdrlFaceVO.getdLunch());
                list.setrLunch(sdrlFaceVO.getrLunch());
                list.setdDinner(sdrlFaceVO.getdDinner());
                list.setrDinner(sdrlFaceVO.getrDinner());
                Map<String, Object> mapvo = Maps.newHashMap();
                mapvo.put("list", list);
                mapvo.put("eatStandard0", eatStandard0);
                mapvo.put("eatStandard1", eatStandard1);
                mapvo.put("eatStandard2", eatStandard2);
                mapList.add(mapvo);
            }
        }
        return mapList;
    }

    @GetMapping("/stuFirstDayDiningStatistics")
    @ResponseBody
    public List stuFirstDayDiningStatistics (){
        String classIdArrString = request.getParameter("classIdArrString");
        String[] classIdArr = classIdArrString.substring(1,classIdArrString.length()-1).split(",");
        String diningPlace = request.getParameter("diningPlace");
        List<StuDiningDataStatisticsVO> list = stuFirstDinStatisticsService.getStuFirstDayDiningStatisticsList(diningPlace,classIdArr);
        return list;
    }
    @GetMapping("/teachFirstDayDiningStatistics")
    @ResponseBody
    public List teachFirstDayDiningStatistics (){
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
        List<TeachDiningDataStatisticsVO> list = teachFirstDinStatisticsService.getTeachDiningFirstDayStatisticsList(diningPlace,classIdArr,startTime,endTime);
        return list;
    }
}
