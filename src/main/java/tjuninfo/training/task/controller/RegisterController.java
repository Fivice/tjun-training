package tjuninfo.training.task.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.DateUtils;
import tjuninfo.training.task.util.RandomUtils;
import tjuninfo.training.task.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.lang3.ObjectUtils.compare;

/**
 *报名控制器
 * @author
 * @date 2018年10月8日
 */
@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController{

	@Autowired
	private StudentService studentService;
	@Autowired
	private IUnitService unitService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private ClassDiningService classDiningService;
	@Autowired
	private StudentCardService studentCardService;
	@Autowired
	private IBasicParametersService basicParametersService;
	@Autowired
	private IFaceDetectionService iFaceDetectionService;
	@Autowired
	private ClassInfoService iClassInfoService;


	/**报名登记页面**/
	@RequestMapping("/form")
	public String form(Model model){

		//查询当前登录用户
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		//
        if(userInfo.getSupType()!=null && !userInfo.getSupType().equals("")){
			model.addAttribute("userType", userInfo.getUserType());
		}
		//前台用户
		if(userInfo.getSupType()!=null && userInfo.getSupType().equals("前台")){
			//获取报名地点，让班级按报名地点显示
			model.addAttribute("type", userInfo.getUserType());
			//如果是红枫路前台，默认住宿标准是单间
			if(userInfo.getUserType()!=null&&userInfo.getUserType().contains("红枫")){
				model.addAttribute("hotelparam", "hotelparam");
			}
		}
		//院校基本参数列表
		List<BasicParameters> basicParameters = basicParametersService.list();
		//获取报名住宿地点
		String address = basicParameters.get(0).getAddress();
		List<String> list = new ArrayList<String>();
		String[] result = address.split("[，,]");
		for (int i = 0; i < result.length; i++) {
			String addres = result[i];
			list.add(addres);
		}
		model.addAttribute("list", list);


		return "register/registerForm";
	}

	/**获取机器扫描数据**/
	@RequestMapping("/idNumber")
	@ResponseBody
	public Object getIdNumber(Model model){
		JSONObject jsonObject = new JSONObject();
		String registerArea;
		//查询当前登录用户
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		registerArea = userInfo.getUserType();

		String[] propName = {"registerArea"};
		Object[] propValue = {registerArea};
		List list = iFaceDetectionService.queryByProerties(propName,propValue,null,null);
		return list;
	}

	/**
	 * 查询相关信息（刷新）
	 * @param siIDNumber
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/findInfo")
	public String findStudent(String siIDNumber, Model model) {
		//查询学生
		Student student=studentService.findByNumber(siIDNumber);
		//单位列表
		List<Unit> unitList = unitService.list();
		model.addAttribute("unitList", unitList);
		model.addAttribute("student", student);
		return "/register/registerForm";
	}

	/**
	 * 查询相关信息（无刷新）
	 * @param siIDNumber
	 * @return
	 */
	@RequestMapping(value = "/findInformation")
	@ResponseBody
	public Object findInformation(@RequestParam(value="siIDNumber",required=false)String siIDNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(siIDNumber!=null && !siIDNumber.equals("")){
			//查询学生
			Student student = studentService.findByNumber(siIDNumber);
			map.put("student", student);
		}
			//所有父级单位列表
			List<Unit> unitList = unitService.getBySjreaId(0);
			map.put("unitList", unitList);

		return map;
	}

	/**
	 * 根据父级id查询子单位集合
	 * @param sjareaId
	 * @return
	 */
	@RequestMapping(value = "/findUnit")
	@ResponseBody
	public Object findUnit(@RequestParam(value="sjareaId",required=false)String sjareaId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sjareaId!=null && !sjareaId.equals("")) {
			//根据父级id查询子单位列表
			List<Unit> unitList = unitService.getBySjreaId(Integer.parseInt(sjareaId));
			map.put("unitList", unitList);
		}

		return map;
	}

	/**
	 * //根据id查询父级单位列表
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/findUnitList")
	@ResponseBody
	public Unit findUnitList(@RequestParam(value="areaId",required=false)String areaId) {
		/*List<Unit> unitList =null;
		if(areaId!=null && !areaId.equals("")) {
			//根据id查询父级单位列表
			unitList = unitService.findByareaId(Integer.parseInt(areaId));
		}
		return unitList;*/
		Unit unit =null;
		if(areaId!=null && !areaId.equals("")) {
			//根据id查询单位
			unit = unitService.get(Integer.parseInt(areaId));
		}
		return unit;
	}

	/**
	 * //根据班级和学生id查询报到信息
	 * @param studentId,classId
	 * @return
	 */
	@RequestMapping(value = "/findRegister")
	@ResponseBody
	public List<Register> findRegister(@RequestParam(value="studentId",required=false)String studentId,
									   @RequestParam(value="classId",required=false)String classId) {
		/*List<Register> registers =registerService.findRegisters(studentId,classId);*/

		return registerService.findRegisters(studentId,classId);
	}




	/**
	 * //根据班级id查询就餐列表
	 * @param classNum
	 * @return
	 */
	@RequestMapping(value = "/findClassDiningList")
	@ResponseBody
	public Register findClassDiningList(@RequestParam(value="classNum",required=false)String classNum) {
		List<ClassDining> classDinings =null;
		if(classNum!=null && !classNum.equals("")) {
			//根据班级id查询就餐列表
			classDinings = classDiningService.findClassDiningList(classNum);
		}

		//早中晚就餐安排
		int breakfastTotal=0;
		int lunchTotal=0;
		int dinnerTotal=0;

		if(classDinings!=null && classDinings.size()>0){
			//获取就餐安排的时间数组
			String dates[] = new String[classDinings.size()];
			for (int j =0;j<classDinings.size();j++){
				dates[j] = classDinings.get(j).getDay();
			}
			//获取就餐安排时间数组中最小日期的值
			String mindate=dates[0];
			for(int i=0;i<dates.length;i++){
				if(compare(dates[i], mindate)==-1){
					mindate=dates[i];
				}
			}

			//获取当前报到时间
			String str2 = DateUtils.getDateTime();
			//就餐安排最早时间(早于）报到时间
			if (mindate.compareTo(str2)<0) {
				List<String> days = DateUtils.getDays(mindate,str2);
				for (int i =0;i<days.size()-1;i++){
						//去除当天之前的未报到之前的就餐安排次数
					   //根据日期查询就餐安排
						classDinings.remove(classDiningService.findClassDiningList(classNum,days.get(i)).get(0));
				}
			}

			for (ClassDining classDining: classDinings) {

				//当天就餐安排时间
				String param[]=classDining.getDay().split(" ");
				//当天报到时间
				String param2[]=str2.split(" ");
				//当天报到情况(十点之前算早餐,十点到三点之间算中餐,三点之后算晚餐)
				//当天就餐安排
				if(param[0].toString().trim().equals(param2[0].toString().trim())){
					//System.out.println("==============当天就餐安排="+classDining.getDay());
					// 获取当前报到时间（时分秒）

					//当前时间小于早上八点半
					if(param2[1].trim().compareTo("08:30:00") <= 0){
						//System.out.println("============上午十点之前");

						//早，中，晚都就餐
						//早餐就餐
						if (classDining.getBreakfast() != null && classDining.getBreakfast() == 1) {
							breakfastTotal++;
						}
						//午餐就餐
						if (classDining.getLunch() != null && classDining.getLunch() == 1) {
							lunchTotal++;
						}
						//晚餐就餐
						if (classDining.getDinner() != null && classDining.getDinner() == 1) {
							dinnerTotal++;
						}

						//当前时间晚于下午一点
					} else if(param2[1].trim().compareTo("13:00:00") >= 0){
						//System.out.println("============下午三点之后");

						//晚 就餐

						//晚餐就餐
						if (classDining.getDinner() != null && classDining.getDinner() == 1) {
							dinnerTotal++;
						}
					}else{
						//System.out.println("============上午九点和下午一点之间");

						//中 晚 就餐

						//午餐就餐
						if (classDining.getLunch() != null && classDining.getLunch() == 1) {
							lunchTotal++;
						}
						//晚餐就餐
						if (classDining.getDinner() != null && classDining.getDinner() == 1) {
							dinnerTotal++;
						}
					}

					//其余天数就餐安排
				}else {
					//System.out.println("==============其余天数就餐安排="+classDining.getDay());
					//早餐就餐
					if (classDining.getBreakfast() != null && classDining.getBreakfast() == 1) {
						breakfastTotal++;
					}
					//午餐就餐
					if (classDining.getLunch() != null && classDining.getLunch() == 1) {
						lunchTotal++;
					}
					//晚餐就餐
					if (classDining.getDinner() != null && classDining.getDinner() == 1) {
						dinnerTotal++;
					}
				}

			}




		}


		Register register =new  Register();
		register.setBreakfastTotal(breakfastTotal);
		register.setLunchTotal(lunchTotal);
		register.setDinnerTotal(dinnerTotal);

		return register;
	}

	/**
	 * 根据班级id查询报到集合
	 * @param classNum
	 * @return
	 */
	@RequestMapping(value = "/findRegisterList")
	@ResponseBody
	public Object findRegisterList(@RequestParam(value="classNum",required=false)String classNum) {
		List<Register> registerList = null;
		//报到人数
		Integer registerPeoples =0;
		//住宿人数
		Integer hotelPeoples =0;
		//就餐人数
		Integer diningPeoples =0;
		//培训费
		Double training_expense =0.0;
		//食宿费(住宿费+就餐费)
		Double scaleFee_total =0.0;
		//其它费用
		Double other_charges =0.0;
		if (classNum != null && !classNum.equals("")) {
			//根据班级id查询报到列表
			registerList = registerService.findAllByClassId(Long.parseLong(classNum));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(registerList!=null&&registerList.size()>0){
			for (Register register:registerList
				 ) {
				//住宿
                 //标间                        //单间
				if (register.getHotel()==0 || register.getHotel()==1){
					hotelPeoples++;
				}
				//就餐
				if(register.getDining().equals("1")){
					diningPeoples++;
				}
				//费用(已交)
				if(!register.getPay().equals("2")){
					//培训费
					training_expense+=register.getTrainingExpense();
					//食宿费
					scaleFee_total+=register.getScaleFeeTotal()+register.getFoodTotal();
					//其它费用
					other_charges+=register.getOtherCharges();
				}

			}
			registerPeoples = registerList.size();
		}
		map.put("registerPeoples",registerPeoples);
		map.put("hotelPeoples",hotelPeoples);
		map.put("diningPeoples",diningPeoples);
		map.put("training_expense",training_expense);
		map.put("scaleFee_total",scaleFee_total);
		map.put("other_charges",other_charges);

		//根据id查询班级信息
		ClassInfo classInfo = iClassInfoService.findClassInfoByClassId(classNum);
		//标间标准
		map.put("houseStandard0",classInfo.getInterScaleFee());
		//单间标准
		map.put("houseStandard1",classInfo.getSingleRoomCharge());
		//查询当前登录用户
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		//前台用户
		if(userInfo.getSupType()!=null && userInfo.getSupType().equals("前台")){
			//如果是红枫路前台，默认住宿标准是单间
			if(userInfo.getUserType()!=null&&userInfo.getUserType().contains("红枫")){
				map.put("hotelparam", "hotelparam");
			}
		}

		return map;
	}


	//base64字符串转化成图片
	public static String GenerateImage(String imgStr)
	{  //对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) //图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try
		{
			//Base64解码
			//byte[] b = decoder.decodeBuffer(imgStr.substring(imgStr.indexOf(",")+1));
			byte[] b = decoder.decodeBuffer(imgStr);
			for(int i=0;i<b.length;++i)
			{
				if(b[i]<0)
				{//调整异常数据
					b[i]+=256;
				}
			}
			//生成jpeg图片
			String path="/upload/studentInfo/";
			String uploadPath=path;
			File up = new File(uploadPath);
			if (!up.exists()) {
				up.mkdirs();
			}
			//String imgFilePath = "D:\\tupian\\new.jpg";//新生成的图片

			String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(RandomUtils.getRandom(6)).concat(".").concat("jpg");
			String imgFilePath = path+fileName;//新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return imgFilePath;
		}
		catch (Exception e)
		{
			return null;
		}
	}


	/**
	 * 添加/修改数据
	 * @param register
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate")
	@ResponseBody
	public synchronized Object save(Register register){
		/*String number=request.getParameter("number");*/
		/*StudentCard studentCard=new StudentCard();*/
		List<StudentCard> studentCards=studentCardService.findListBy(register.getNumber());
		StudentCard studentCard=studentCards.get(0);
		/*studentCard.setNumber(register.getNumber());*/

		if(register.getReportTime()!=null && !register.getReportTime().equals("")){
			studentCard.setRegisterTime(register.getReportTime());
		}


		//学生相关信息
		String siIDNumber = request.getParameter("siIDNumber");
		if(siIDNumber!=null && !siIDNumber.equals("")){
			/*String studentId = request.getParameter("studentId");*/
			String siName = request.getParameter("siName");
			String phoneNumber = request.getParameter("phoneNumber");
			String ethnicGroup = request.getParameter("ethnicGroup");
			String unitId = request.getParameter("unitId");
			String unitName = request.getParameter("unitName");
			//根据身份证号查询学生
			Student student = studentService.findByNumber(siIDNumber);
			if(null!=student){
				//更新学生信息
				student.setSiName(siName);
				student.setPhoneNumber(phoneNumber);
				student.setEthnicGroup(ethnicGroup);
				if(unitId!=null && !unitId.equals("")){
					student.setSiUnitId(Integer.parseInt(unitId));
				}
				student.setUnitName(unitName);
				//学生照片
				String pStr = GenerateImage(register.getPhotoStr());
				if(StringUtils.isNotBlank(pStr)){
					student.setPhoto(pStr);
				}

				studentService.update(student);

				//学生存在的id
				studentCard.setStudentId(student.getSiId());
				register.setSiId(student.getSiId());

			}else{
				//保存学生信息
				Student student1 = new Student();
				student1.setSiIDNumber(siIDNumber);
				student1.setSiName(siName);
				student1.setPhoneNumber(phoneNumber);
				student1.setEthnicGroup(ethnicGroup);

				if(unitId!=null && !unitId.equals("")) {
					student1.setSiUnitId(Integer.parseInt(unitId));
				}
				student1.setUnitName(unitName);
				student1.setStatus("0");

				//学生照片
				String pStr = GenerateImage(register.getPhotoStr());
				if(StringUtils.isNotBlank(pStr)){
					student1.setPhoto(pStr);
				}

				studentService.save(student1);
				//学生不存在的id
				studentCard.setStudentId(student1.getSiId());
				register.setSiId(student1.getSiId());
			}
		}

		//学生流水号
		/*studentCardService.save(studentCard);*/
		studentCardService.update(studentCard);
		//报到登记
		register.setStatus(0);
		registerService.save(register);

		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}


    /**
     * 添加/修改数据
     * @param register
     * @return
     */
/*    @RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
    public Object save(Register register){
        *//*String number=request.getParameter("number");*//*
        *//*StudentCard studentCard=new StudentCard();*//*
        List<StudentCard> studentCards=studentCardService.findListBy(register.getNumber());
        StudentCard studentCard=studentCards.get(0);
        *//*studentCard.setNumber(register.getNumber());*//*
        studentCard.setStudentId(register.getSiId());
        if(register.getReportTime()!=null && !register.getReportTime().equals("")){
            studentCard.setRegisterTime(register.getReportTime());
        }
        //学生流水号
        *//*studentCardService.save(studentCard);*//*
        studentCardService.update(studentCard);
        //报到登记
        register.setStatus(0);
        registerService.save(register);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }*/

	/**
	 * 查询流水号是否存在
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findStudentCardNumber")
	@ResponseBody
	public Object findClassNumber(String number,Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=number && !number.equals("")){
			List<StudentCard> studentCards=studentCardService.findListBy(number);
			if(studentCards==null || studentCards.size()==0){
				map.put("valid",false);
			}else{
				map.put("valid",true);
			}

		}

		return map;
	}


	/**访问学员报名登记页面**/
	@RequestMapping("/stuRegister")
	public String form1(){
		return "register/student_register";
	}

	/**访问学员报名登记页面**/
	@RequestMapping("/stuRegister2")
	public String form2(Model model){
		String siUnitId = request.getParameter("siUnitId");

        List<Unit> unitList = unitService.findByareaId(Integer.parseInt(siUnitId));
        for (Unit u: unitList) {
            String unitName = u.getAreaName();
            model.addAttribute("unitName", unitName);
        }

		return "register/student_register_form";
	}

	/**
	 * 根据身份证号码查询学生信息
	 * @param siIDNumber
	 * @return
	 */
	@RequestMapping(value = "/findStudentByNumber")
	@ResponseBody
	public Object findStudentByNumber(String siIDNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=siIDNumber && !siIDNumber.equals("")){
			Student student=studentService.getBysiIDNumber(siIDNumber,null);

				map.put("student",student);

		}

		return map;
	}


}
