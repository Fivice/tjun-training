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
import tjuninfo.training.task.vo.OverallEvaluationVO;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *总体评价控制器
 */
@Controller
@RequestMapping("/overallEvaluation")
public class OverallEvaluationController extends BaseController{

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
	@Autowired
	private EvaluateScoreService evaluateScoreService;

	/**总体评价数据分析页面**/
	@RequestMapping("/view")
	public String view(){
		return "overallEvaluation/overallEvaluation_list";
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
        String query=request.getParameter("query").trim();
        String year=request.getParameter("year").trim();
        String plan=request.getParameter("plan").trim();
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
				OverallEvaluationVO  overallEvaluationVO = new OverallEvaluationVO();
				String s = "";
				if (i < 10) {
					s = "0" + i;
				} else {
					s = s + i;
				}
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
				if(classInfos.size()>0){
					List<Integer> registerId = new ArrayList<Integer>();
					for (ClassInfo classInfo : classInfos) {
						List<Register> registers = registerService.findByClassId(classInfo.getId().intValue());
						if (registers.size() > 0) {
							for (Register register : registers) {
								registerId.add(register.getId());
							}

						}
					}
					if(registerId.size()>0){
						overallEvaluationVO = evaluateScoreService.getByRegisterId(registerId);
					}else {
						overallEvaluationVO.setQualityResult(0.00);
						overallEvaluationVO.setComprehensiveResult(0.0);
					}
				}else {
					overallEvaluationVO.setQualityResult(0.00);
					overallEvaluationVO.setComprehensiveResult(0.0);
				}

				overallEvaluationVO.setMonth(String.valueOf(i));
				overallEvaluationVO.setClassNum(classInfos.size());
				map.put("overallEvaluationVO", overallEvaluationVO);
				mapList.add(map);
			}
			//按类型统计
		}else if(query!=null && query.equals("type")) {
        	List<TrainingType> trainingTypes =trainingTypeService.findTrainingTypeList();
			for (TrainingType trainingType: trainingTypes) {
				Map<String, Object> map = Maps.newHashMap();
				OverallEvaluationVO  overallEvaluationVO = new OverallEvaluationVO();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDay=null;
				String endDay=null;
				startDay=sdf.format(DateUtils.getCurrYearFirst(Integer.parseInt(year)));
				endDay=sdf.format(DateUtils.getCurrYearLast(Integer.parseInt(year)));
				String dat=startDay+"至"+endDay;
				List<ClassInfo> classInfos = classInfoService.findClassInfosByDate(dat,trainingType.getId().toString(),plan);
				List<Integer> registerId = new ArrayList<>();
				if(classInfos.size()>0) {
					for (ClassInfo classInfo : classInfos) {
						List<Register> registers = registerService.findByClassId(classInfo.getId().intValue());
						if (registers.size() > 0) {
							for (Register register : registers) {
								registerId.add(register.getId());
							}
						}
					}
					if (registerId.size() > 0) {
						overallEvaluationVO = evaluateScoreService.getByRegisterId(registerId);
					} else {
						overallEvaluationVO.setQualityResult(0.00);
						overallEvaluationVO.setComprehensiveResult(0.0);
					}
				}else {
					overallEvaluationVO.setQualityResult(0.00);
					overallEvaluationVO.setComprehensiveResult(0.0);
				}
				overallEvaluationVO.setMonth(trainingType.getType());
				overallEvaluationVO.setClassNum(classInfos.size());
				map.put("overallEvaluationVO", overallEvaluationVO);
				mapList.add(map);
			}
		}
		return mapList;
	}



}
