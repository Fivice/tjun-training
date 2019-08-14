package tjuninfo.training.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.DateUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/offlineData")
public class OfflineDataController extends BaseController{
	@Resource
	private IOfflineDataService  offlineDataService;
	@Resource
	private IBasicParametersService basicParametersService;
	@Resource
	private StudentService studentService;
	@Resource
	private IUnitService unitService;
	@Resource
	private ClassInfoService classInfoService;
	@Resource
	private TeacherInfoService teacherInfoService;
	@Resource
	private TeachDinFaceRecordService teachDinFaceRecordService;
	@Resource
	private StuDinRecordService stuDinRecordService;


	/**
	 * 访问页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/view")
	public String toList(Model model){
		String month = DateUtil.getDateTime(new Date()).substring(0,7);
		model.addAttribute("month",month);

		List<BasicParameters> basicParameters = basicParametersService.list();//院校基本参数列表
		String eatPlaces = basicParameters.get(0).getEatPlace();//获取就餐地点
		List<String> diningPlaceList = new ArrayList<String>();
		String[] res = eatPlaces.split("[，,]");
		for (int i = 0; i < res.length; i++) {
			String eatPlace = res[i];
			diningPlaceList.add(eatPlace);
		}
		model.addAttribute("list", diningPlaceList);
		return "offlineData/list";
	}

    /**
     * 访问人脸脱机数据页面
     * @param model
     * @return
     */
    @RequestMapping("/view1")
    public String toList1(Model model){
        String month = DateUtil.getDateTime(new Date()).substring(0,7);
        model.addAttribute("month",month);

        List<BasicParameters> basicParameters = basicParametersService.list();//院校基本参数列表
        String eatPlaces = basicParameters.get(0).getEatPlace();//获取就餐地点
        List<String> diningPlaceList = new ArrayList<String>();
        String[] res = eatPlaces.split("[，,]");
        for (int i = 0; i < res.length; i++) {
            String eatPlace = res[i];
            diningPlaceList.add(eatPlace);
        }
        model.addAttribute("list", diningPlaceList);
        return "offlineData/list_face";
    }

	/**
	 * 获取列表
	 * @param o
	 * @param btView
	 * @throws IOException
	 */
	@RequestMapping(value = "/findList", method = { RequestMethod.POST, RequestMethod.GET })
	public void findUser(OfflineData o, BTView<OfflineData> btView) throws IOException{
		String area = request.getParameter("area").trim();
		String month = request.getParameter("month").trim();
		String time = request.getParameter("time").trim();
		String status = request.getParameter("status").trim();
		List<OfflineData> list = offlineDataService.getinfo(btView,area,month,time,status);
		btView.setRows(list);
		super.writeJSON(btView);
	}

    /**
     * 获取列表
     * @param o
     * @param btView
     * @throws IOException
     */
    @RequestMapping(value = "/findListFace", method = { RequestMethod.POST, RequestMethod.GET })
    public void findListFace(OfflineData o, BTView<OfflineData> btView) throws IOException{
        String area = request.getParameter("area").trim();
        String month = request.getParameter("month").trim();
        String time = request.getParameter("time").trim();
        String status = request.getParameter("status").trim();
        List<OfflineData> list = offlineDataService.getFaceInfo(btView,area,month,time,status);
        btView.setRows(list);
        super.writeJSON(btView);
    }

	/**
	 *新增页面
	 * @return
	 */
	@GetMapping(value= "/add/view")
	public String add() {
		return "offlineData/add";
	}
	/**
	 * 逐条新增
	 * @param o
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(OfflineData o) throws ParseException {
		CmsResult cmsResult  = offlineDataService.offlineSave(o.getNumber(),o.getDay());
		String remarks =cmsResult.getMessage();
		Integer status = 1;//上传成功
		if(cmsResult.getCode()==1||cmsResult.getCode()==30){//1刷卡成功，30每人每餐只能刷一次
			Object obj = cmsResult.getData();
			if(!o.getNumber().substring(0,1).equals("9")){//流水号不以9开头，为学员证
				StuDiningRecord sNew = (StuDiningRecord)obj;
				Student stu = studentService.get(sNew.getStudent());
				o.setName(stu.getSiName());
				o.setIdCard(stu.getSiIDNumber());
				o.setTel(stu.getPhoneNumber());
				if(stu.getSiUnitId()!=null&&!stu.getSiUnitId().equals("")){
					o.setUnit(unitService.get(stu.getSiUnitId()).getAreaName());
				}else {
					o.setUnit(stu.getUnitName());
				}
				o.setArea(sNew.getArea());
			}else {//教师卡
				TeachDiningRecord tNew =(TeachDiningRecord)obj;
				o.setArea(tNew.getArea());
				o.setName(tNew.getTeacherName());
			}
		}else if(cmsResult.getCode()==20){//没有就餐安排
			Object obj = cmsResult.getData();
			if(!o.getNumber().substring(0,1).equals("9")){//流水号不以9开头，为学员证
				Register r = (Register)obj;
				Student stu = studentService.get(r.getSiId());
				o.setName(stu.getSiName());
				o.setIdCard(stu.getSiIDNumber());
				o.setTel(stu.getPhoneNumber());
				if(stu.getSiUnitId()!=null&&!stu.getSiUnitId().equals("")){
					o.setUnit(unitService.get(stu.getSiUnitId()).getAreaName());
				}else {
					o.setUnit(stu.getUnitName());
				}
			}else {//教师卡
				TeacherCard t =(TeacherCard)obj;
				o.setName(t.getTeacherName());
			}
		}
			if(cmsResult.getCode()!=1){
				remarks = "刷卡失败： "+remarks;
				status = 2;//上传失败
			}
		//判断餐点
		String time = o.getDay();
		String timePD12 = time.substring(11,13);
		time = time.substring(11);
		String zhtimePD =" 15:00:00";//中餐
		String ztimePD = " 10:00:00";//早餐
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		if((dateFormat.parse(time).getTime())>=(dateFormat.parse(zhtimePD).getTime())){//晚餐
			o.setTime(3);//（1早餐；2中餐；3晚餐）
		}else if((dateFormat.parse(time).getTime())>=(dateFormat.parse(ztimePD).getTime())||timePD12.equals("12")){//中餐
			o.setTime(2);
		}else {
			o.setTime(1);
		}
		o.setStatus(status);
		o.setRemarks(remarks);
		o.setUploader(getUser().getUserName());
		offlineDataService.save(o);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * 脱机数据上传到就餐记录(人脸)
	 * @param ids
	 */
	@RequestMapping(value="/faceDataAccept",method= RequestMethod.POST)
	@ResponseBody
	public Object faceDataAccept(String ids) throws ParseException {
		CmsResult cmsResult = null;ids.trim();
		String [] ID = ids.split(",");
		for(String id : ID){
			OfflineData o = offlineDataService.get(Integer.valueOf(id));
			//判断餐点
			Integer dinner= offlineDataService.dinner(o.getDay());//1早2中3晚
			String time2  = o.getDay().substring(0,10);
			if(o.getCategory()==2){//类别（1教师、2学员、3其他）
				//查询当天有就餐安排的学生，并判断idCard是否在其中
				boolean stuIf = studentService.ifExist(time2,o.getIdCard(),dinner);
				Student s = studentService.getBysiIDNumber(o.getIdCard(),null);
				ClassInfo c =classInfoService.getClassIfo(s.getSiId(),time2);
				if(stuIf){
					StuDiningRecord sdr =stuDinRecordService.findByTime(time2,s.getSiId(),c.getId().intValue());  //  查询就餐记录表 判断当天是否有就餐记录
					cmsResult = stuDinRecordService.saveData(o.getDay(),s,sdr,c);//按时间点存储人脸就餐数据
					if(cmsResult.getCode()==1){//刷卡成功
						o.setRemarks("上传成功："+cmsResult.getMessage());
						o.setStatus(1);//0.未接收、1成功,2失败
					}else {//每人每餐只能刷卡一次 30
						o.setRemarks("上传失败："+cmsResult.getMessage());
						o.setStatus(2);//0.未接收、1成功,2失败
					}
					o.setArea(c.getDiningPlace());
				}else {
					o.setRemarks("上传失败：该学员此时间无就餐安排");
					o.setStatus(2);//0.未接收、1成功,2失败
				}
			}else if(o.getCategory()==1){//类别（1教师、2学员、3其他）
				//查询当天有就餐安排的教师，并判断idCard是否在其中
				boolean teaIf = teacherInfoService.ifExist(time2,o.getIdCard(),dinner);
				if(teaIf){
					TeacherInfo t = teacherInfoService.getBysiIDNumber(o.getIdCard(),null);//根据身份证查找教师
					TeachDiningFaceRecord tdfr = teachDinFaceRecordService.findByTime(time2,t.getTiId());//在就餐记录中查询当天的就餐情况
					TeacherDiningRegister tdr = teachDinFaceRecordService.findClass(time2,t.getTiId());//根据教师id和就餐时间查询班级id
					ClassInfo c = classInfoService.get(tdr.getClassId().longValue());
					cmsResult=teachDinFaceRecordService.saveData(o.getDay(),t,tdfr,tdr,c);//教师人脸就餐记录保存
					if(cmsResult.getCode()==1){//刷卡成功
						o.setRemarks("上传成功："+cmsResult.getMessage());
						o.setStatus(1);//0.未接收、1成功,2失败
					}else {//每人每餐只能刷卡一次 30
						o.setRemarks("上传失败："+cmsResult.getMessage());
						o.setStatus(2);//0.未接收、1成功,2失败
					}
					o.setArea(c.getDiningPlace());
				}else {
					o.setRemarks("就餐失败：该教师此时间无就餐安排");
					o.setStatus(2);//0.未接收、1成功,2失败
				}
			}else {
				o.setRemarks("就餐失败：无就餐安排");
				o.setStatus(2);//0.未接收、1成功,2失败
			}
			o.setUploader(getUser().getUserName());
			offlineDataService.update(o);
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
}