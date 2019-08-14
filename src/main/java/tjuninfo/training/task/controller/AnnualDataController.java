package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.dto.AnnualDataDto;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.service.impl.TeachDinRecordServiceImpl;
import tjuninfo.training.task.util.DateUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *报名控制器
 * @author
 * @date 2018年10月8日
 */
@Controller
@RequestMapping("/annualData")
public class AnnualDataController extends BaseController{

	@Autowired
	private RegisterService registerService;
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private TrainingTypeService trainingTypeService;
	@Autowired
	private RecordChangeService recordChangeService;
	@Autowired
	private IBasicParametersService basicParametersService;
	@Autowired
	private StuDinRecordService stuDinRecordService;
	@Autowired
	private TeachDinRecordServiceImpl teachDinRecordService;

	/**年度数据分析页面**/
	@RequestMapping("/view")
	public String view(Model model){
		/*//当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		model.addAttribute("year",sdf.format(date));*/
		return "annualData/annualDataList";
	}

	/**年度数据分析页面**/
	@RequestMapping("/mobileView")
	public String mobileView(Model model){
		return "annualData/annualDataMobileList1";
	}



	/**
	 * 列表数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findTable")
	@ResponseBody
	public Object list(Model model) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		/*int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());*/
        String query=request.getParameter("query").trim();
        String year=request.getParameter("year").trim();
        String plan=request.getParameter("plan").trim();
		//院校基本参数列表
		List<BasicParameters> basicParameters = basicParametersService.list();
		//获取就餐标准
		String eatStandard = basicParameters.get(0).getEatStandard();
		String[] eatStandards = eatStandard.split("[，,]");
        //按月份统计
        if (query!=null && query.equals("month")) {
			Calendar date = Calendar.getInstance();
			//当前年
			String yearToday = String.valueOf(date.get(Calendar.YEAR));
			//当前月
			String month = String.valueOf(date.get(Calendar.MONTH) + 1);
			int monthToday=0;
			if(!yearToday.equals(year)){
				monthToday=12;
			}else{
				monthToday=date.get(Calendar.MONTH) + 1;
			}
			for (int i = 1; i <=monthToday; i++) {
				Map<String, Object> map = Maps.newHashMap();
				AnnualDataDto annualDataDto = new AnnualDataDto();
				String s = "";
				if (i < 10) {
					s = "0" + i;
				} else {
					s = s + i;
				}
				//培训天数
				int dayNum = 0;
				//培训人数
				int studentNum = 0;
				//培训费用
				Double trainingExpense = 0.0;
				//住宿费用
				Double scaleFeeTotal = 0.0;
				//就餐费用
				Double foodTotal = 0.0;
				//其它费用
				Double otherCharges = 0.0;
				//就餐结余
				Double balance = 0.0;
				//就餐
				Double f = 0.0;
				//就餐
				Double fo = 0.0;
				//实际就餐总数
				Double count=0.0;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDay=null;
				String endDay=null;
				try {
					startDay=sdf.format(DateUtils.getFirstDayDateOfMonth(simpleDateFormat.parse(year + "-" + s)));
					endDay=sdf.format(DateUtils.getLastDayOfMonth(simpleDateFormat.parse(year + "-" + s)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String dat=startDay+"至"+endDay;
				List<ClassInfo> classInfos = classInfoService.findClassInfosByDate(dat,null,plan);
				for (ClassInfo classInfo : classInfos) {
					//培训天数
					dayNum += classInfo.getDayNum();
					List<Register> registers = registerService.findByClassId(classInfo.getId().intValue());
					//培训人数
					if(registers.size()>0){
						studentNum += registers.size();
					}else{
						studentNum +=0;
					}

					List<RecordChange> recordChanges=recordChangeService.list(classInfo.getId());

							for (Register register : registers) {
								trainingExpense += register.getTrainingExpense();
								otherCharges += register.getOtherCharges();
								scaleFeeTotal += register.getScaleFeeTotal();
								foodTotal += register.getFoodTotal();
							}

					if(recordChanges.size()>0){
						f += recordChanges.get(0).getDiningFeeCollection();
					}else{
						for (Register register : registers) {
							f += register.getFoodTotal();
						}
					}

					DecimalFormat df = new DecimalFormat("#.00");
					Double brekfarst=0.0;
					Double lunch=0.0;
					Double dinner=0.0;
					if(null!=classInfo.getBreakfast() && !classInfo.getBreakfast().equals("")){
						brekfarst=Double.valueOf(classInfo.getBreakfast().trim());
					}else{
						brekfarst=Double.valueOf(eatStandards[0].trim());
					}
					if(null!=classInfo.getLunch()&& !classInfo.getLunch().equals("")){
						lunch=Double.valueOf(classInfo.getLunch().trim());
					}else{
						lunch=Double.valueOf(eatStandards[1].trim());
					}
					if(null!=classInfo.getDinner()&& !classInfo.getDinner().equals("")){
						dinner=Double.valueOf(classInfo.getDinner().trim());
					}else{
						dinner=Double.valueOf(eatStandards[2].trim());
					}
					//学生早餐实际费用
					Double stuBreakfarst=Double.parseDouble(df.format(stuDinRecordService.findCount(classInfo.getId().toString(),"0").size()*brekfarst));
					//学生午餐实际费用
					Double stuLunch=Double.parseDouble(df.format(stuDinRecordService.findCount(classInfo.getId().toString(),"1").size()*lunch));
					//学生晚餐实际费用
					Double stuDinner=Double.parseDouble(df.format(stuDinRecordService.findCount(classInfo.getId().toString(),"2").size()*dinner));
					//教师早餐实际费用
					Double teaBreakfarst=Double.parseDouble(df.format(teachDinRecordService.findCount(classInfo.getId().toString(),"0").size()*brekfarst));
					//教师午餐实际费用
					Double teaLunch=Double.parseDouble(df.format(teachDinRecordService.findCount(classInfo.getId().toString(),"1").size()*lunch));
					//教师晚实际费用
					Double teaDinner=Double.parseDouble(df.format(teachDinRecordService.findCount(classInfo.getId().toString(),"2").size()*dinner));
					//教师早餐实际费用(人脸)
					Double teaBreakfarst2=Double.parseDouble(df.format(teachDinRecordService.findCount2(classInfo.getId().toString(),"0").size()*brekfarst));
					//教师午餐实际费用(人脸)
					Double teaLunch2=Double.parseDouble(df.format(teachDinRecordService.findCount2(classInfo.getId().toString(),"1").size()*lunch));
					//教师晚实际费用(人脸)
					Double teaDinner2=Double.parseDouble(df.format(teachDinRecordService.findCount2(classInfo.getId().toString(),"2").size()*dinner));

					count+=stuBreakfarst+stuLunch+stuDinner+teaBreakfarst+teaLunch+teaDinner+teaBreakfarst2+teaLunch2+teaDinner2;

				}
				balance= f-(count);
				annualDataDto.setMonth(String.valueOf(i));
				annualDataDto.setClassNum(classInfos.size());
				annualDataDto.setDayNum(dayNum);
				annualDataDto.setStudentNum(studentNum);
				annualDataDto.setTrainingExpense(trainingExpense);
				annualDataDto.setOtherCharges(otherCharges);
				annualDataDto.setScaleFeeTotal(scaleFeeTotal);
				annualDataDto.setFoodTotal(foodTotal);
				/*annualDataDto.setBalance((trainingExpense + otherCharges + scaleFeeTotal) - foodTotal);*/
				annualDataDto.setBalance(balance);
				map.put("annualDataDto", annualDataDto);
				mapList.add(map);
			}
			//按类型统计
		}else if(query!=null && query.equals("type")) {
        	List<TrainingType> trainingTypes =trainingTypeService.findTrainingTypeList();
			for (TrainingType trainingType: trainingTypes) {
				Map<String, Object> map = Maps.newHashMap();
				AnnualDataDto annualDataDto = new AnnualDataDto();
				//培训天数
				int dayNum = 0;
				//培训人数
				int studentNum = 0;
				//培训费用
				Double trainingExpense = 0.0;
				//住宿费用
				Double scaleFeeTotal = 0.0;
				//就餐费用
				Double foodTotal = 0.0;
				//其它费用
				Double otherCharges = 0.0;
				//就餐结余
				Double balance = 0.0;
				//就餐
				Double f = 0.0;
				//就餐
				Double fo = 0.0;
				//实际就餐总数
				Double count=0.0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDay=null;
				String endDay=null;

				startDay=sdf.format(DateUtils.getCurrYearFirst(Integer.parseInt(year)));
				endDay=sdf.format(DateUtils.getCurrYearLast(Integer.parseInt(year)));

				String dat=startDay+"至"+endDay;
				List<ClassInfo> classInfos = classInfoService.findClassInfosByDate(dat,trainingType.getId().toString(),plan);
				for (ClassInfo classInfo : classInfos) {
					//培训天数
					dayNum += classInfo.getDayNum();
					List<Register> registers = registerService.findByClassId(classInfo.getId().intValue());
					//培训人数
					if(registers.size()>0){
						studentNum += registers.size();
					}else{
						studentNum +=0;
					}

					List<RecordChange> recordChanges=recordChangeService.list(classInfo.getId());
					if(recordChanges.size()>0){
						f += recordChanges.get(0).getDiningFeeCollection();
					}else{
						for (Register register : registers) {
							f += register.getFoodTotal();
						}
					}

					for (Register register : registers) {
						trainingExpense += register.getTrainingExpense();
						otherCharges += register.getOtherCharges();
						scaleFeeTotal += register.getScaleFeeTotal();
						foodTotal += register.getFoodTotal();
					}

					DecimalFormat df = new DecimalFormat("#.00");
					Double brekfarst=0.0;
					Double lunch=0.0;
					Double dinner=0.0;
					if(null!=classInfo.getBreakfast() && !classInfo.getBreakfast().equals("")){
						brekfarst=Double.valueOf(classInfo.getBreakfast().trim());
					}else{
						brekfarst=Double.valueOf(eatStandards[0].trim());
					}
					if(null!=classInfo.getLunch()&& !classInfo.getLunch().equals("")){
						lunch=Double.valueOf(classInfo.getLunch().trim());
					}else{
						lunch=Double.valueOf(eatStandards[1].trim());
					}
					if(null!=classInfo.getDinner()&& !classInfo.getDinner().equals("")){
						dinner=Double.valueOf(classInfo.getDinner().trim());
					}else{
						dinner=Double.valueOf(eatStandards[2].trim());
					}
					//学生早餐实际费用
					Double stuBreakfarst=Double.parseDouble(df.format(stuDinRecordService.findCount(classInfo.getId().toString(),"0").size()*brekfarst));
					//学生午餐实际费用
					Double stuLunch=Double.parseDouble(df.format(stuDinRecordService.findCount(classInfo.getId().toString(),"1").size()*lunch));
					//学生晚餐实际费用
					Double stuDinner=Double.parseDouble(df.format(stuDinRecordService.findCount(classInfo.getId().toString(),"2").size()*dinner));
					//教师早餐实际费用
					Double teaBreakfarst=Double.parseDouble(df.format(teachDinRecordService.findCount(classInfo.getId().toString(),"0").size()*brekfarst));
					//教师午餐实际费用
					Double teaLunch=Double.parseDouble(df.format(teachDinRecordService.findCount(classInfo.getId().toString(),"1").size()*lunch));
					//教师晚实际费用
					Double teaDinner=Double.parseDouble(df.format(teachDinRecordService.findCount(classInfo.getId().toString(),"2").size()*dinner));
					//教师早餐实际费用(人脸)
					Double teaBreakfarst2=Double.parseDouble(df.format(teachDinRecordService.findCount2(classInfo.getId().toString(),"0").size()*brekfarst));
					//教师午餐实际费用(人脸)
					Double teaLunch2=Double.parseDouble(df.format(teachDinRecordService.findCount2(classInfo.getId().toString(),"1").size()*lunch));
					//教师晚实际费用(人脸)
					Double teaDinner2=Double.parseDouble(df.format(teachDinRecordService.findCount2(classInfo.getId().toString(),"2").size()*dinner));
					count+=stuBreakfarst+stuLunch+stuDinner+teaBreakfarst+teaLunch+teaDinner+teaBreakfarst2+teaLunch2+teaDinner2;
				}
				balance = f-(count);
				annualDataDto.setMonth(trainingType.getType());
				annualDataDto.setClassNum(classInfos.size());
				annualDataDto.setDayNum(dayNum);
				annualDataDto.setStudentNum(studentNum);
				annualDataDto.setTrainingExpense(trainingExpense);
				annualDataDto.setOtherCharges(otherCharges);
				annualDataDto.setScaleFeeTotal(scaleFeeTotal);
				annualDataDto.setFoodTotal(foodTotal);
				/*annualDataDto.setBalance((trainingExpense + otherCharges + scaleFeeTotal) - foodTotal);*/
				annualDataDto.setBalance(balance);
				map.put("annualDataDto", annualDataDto);
				mapList.add(map);

			}

		}
		return mapList;
	}



}
