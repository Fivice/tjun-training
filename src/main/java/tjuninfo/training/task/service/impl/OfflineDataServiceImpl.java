package tjuninfo.training.task.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.ApiCode;
import tjuninfo.training.task.dao.IOfflineDataDao;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 就餐脱机数据层接口实现类
 * @author shenxianyan
 * @date 2018年5月23日
 */
@Service
public class OfflineDataServiceImpl extends BaseServiceImpl<OfflineData> implements IOfflineDataService {
	private IOfflineDataDao offlineDataDao;
	@Resource
	public void setOfflineDataDao(IOfflineDataDao offlineDataDao) {
		this.offlineDataDao = offlineDataDao;
		this.dao = offlineDataDao;
	}
	@Resource
	private StudentRecoverService studentRecoverService;
	@Resource
	private RegisterService registerService;
	@Resource
	private ClassDiningService classDiningService;
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private TeachRecoverService teachRecoverService;
	@Autowired
	private TeachDinRecordService teachDinRecordService;
	@Resource
	private StuDinRecordService stuDinRecordService;
	@Resource
	private TeachDiningService teachDinService;
	@Override
	public List<OfflineData> getinfo(BTView<OfflineData> bt,String area,String month,String time,String status) {
		return offlineDataDao.getinfo(bt,area,month,time,status);
	}

	@Override
	public List<OfflineData> getFaceInfo(BTView<OfflineData> bt,String area,String month,String time,String status) {
		return offlineDataDao.getFaceInfo(bt,area,month,time,status);
	}

	public CmsResult offlineSave(String number, String time) throws ParseException {
		time = time.trim(); number = number.trim();CmsResult cmsResult = null;
		if(time!=null&&!time.equals("")&&!number.equals("")&&number!=null){
			if(!number.substring(0,1).equals("9")){//流水号不以9开头  为学生证
				//1、判断流水号是否无效
				//根据流水号获取学员证（流水号要有唯一性）
				try {
					StudentCard sc =  studentRecoverService.findCardBynumb(number);
					//判断流水号是否失效或无效(流水号存在且流水号有绑定学生)
					if(sc!=null&&!sc.equals("")&&sc.getStudentId()!=null&&!sc.getStudentId().equals("")){
						Register r = registerService.findRegister(sc.getStudentId(),sc.getNumber(),sc.getRegisterTime());
						//判断是否有就餐安排
						if(r.getDining().equals("2")){//1:就餐； 2：不就餐
							return  new CmsResult(ApiCode.FAILED_no,r);
						}else {
							//判断餐点
							Integer dinner= dinner(time);//1早2中3晚
							//查找所在班级今天这一餐是否有就餐安排
							boolean ifDing =  classDiningService.ifDing(String.valueOf(r.getClassId()),time,dinner);
							if(ifDing){//该餐有安排
								//判断学员证是否过期；是否有就餐安排ss
								//3、每个时间段只能刷一次（早餐：10点之前；中餐：下午3点之前；晚餐：3点之后）
								String timePD = time.substring(0,10);
								//  查询就餐记录表 判断当天晚餐是否刷过
								StuDiningRecord sdr = stuDinRecordService.findByTime(timePD,r.getSiId(),r.getClassId());
								if(dinner==3){//晚餐
									if(sdr!=null){//该天 此流水号有就餐记录
										if(sdr.getDinner()==2){//该时间段 未就餐
											sdr.setDinner(1);
											stuDinRecordService.update(sdr);//按时间段存储就餐信息
											cmsResult = new CmsResult(ApiCode.SUCCESS,sdr);
										}else {
											cmsResult = new CmsResult(ApiCode.FAILED_limit,sdr);//每人每餐只能刷一次
										}
									}else {//该天 此流水号没有就餐记录
										StuDiningRecord sNew = new StuDiningRecord();
										sNew.setDinner(1);
										sNew.setArea(classInfoService.get(Long.valueOf(r.getClassId())).getDiningPlace());
										sNew.setBreakfast(2);
										sNew.setLunch(2);
										sNew.setClassRoom(r.getClassId());
										sNew.setDay(timePD);
										sNew.setStudent(r.getSiId());
										stuDinRecordService.save(sNew);   //按时间段存储就餐信息
										cmsResult = new CmsResult(ApiCode.SUCCESS,sNew);
									}
								}else if(dinner==2){//中餐
									//  查询就餐记录表 判断当天中餐是否刷过
									if(sdr!=null){//该天 此流水号有就餐记录
										if(sdr.getLunch()==2){//该时间段 未就餐
											sdr.setLunch(1);
											stuDinRecordService.update(sdr);//按时间段存储就餐信息
											cmsResult = new CmsResult(ApiCode.SUCCESS,sdr);
										}else {
											cmsResult = new CmsResult(ApiCode.FAILED_limit,sdr);//每人每餐只能刷一次
										}
									}else {//该天 此流水号没有就餐记录
										StuDiningRecord sNew = new StuDiningRecord();
										sNew.setDinner(2);
										sNew.setArea(classInfoService.get(Long.valueOf(r.getClassId())).getDiningPlace());
										sNew.setBreakfast(2);
										sNew.setLunch(1);
										sNew.setClassRoom(r.getClassId());
										sNew.setDay(timePD);
										sNew.setStudent(r.getSiId());
										stuDinRecordService.save(sNew);   //按时间段存储就餐信息
										cmsResult = new CmsResult(ApiCode.SUCCESS,sNew);
									}
								}else if(dinner==1){//早餐
									//  查询就餐记录表 判断当天晚餐是否刷过
									if(sdr!=null){//该天 此流水号有就餐记录
										if(sdr.getBreakfast()==2){//该时间段 未就餐
											sdr.setBreakfast(1);
											stuDinRecordService.update(sdr);//按时间段存储就餐信息
											cmsResult = new CmsResult(ApiCode.SUCCESS,sdr);
										}else {
											cmsResult = new CmsResult(ApiCode.FAILED_limit,sdr);//每人每餐只能刷一次
										}
									}else {//该天 此流水号没有就餐记录
										StuDiningRecord sNew = new StuDiningRecord();
										sNew.setDinner(2);
										sNew.setArea(classInfoService.get(Long.valueOf(r.getClassId())).getDiningPlace());
										sNew.setBreakfast(1);
										sNew.setLunch(2);
										sNew.setClassRoom(r.getClassId());
										sNew.setDay(timePD);
										sNew.setStudent(r.getSiId());
										stuDinRecordService.save(sNew);   //按时间段存储就餐信息
										cmsResult = new CmsResult(ApiCode.SUCCESS,sNew);
									}
								}else {
									cmsResult = new CmsResult(ApiCode.FAILED_no,r);
								}
							}else {
								cmsResult = new CmsResult(ApiCode.FAILED_no,r);//无就餐安排 ifDing为false
							}
							return cmsResult;
						}
					}else {
						return new CmsResult(ApiCode.FAILED_invalid);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else { //教师刷卡
				//1、判断流水号是否无效
				//根据流水号获取教师证（流水号要有唯一性）
				TeacherCard tc =  teachRecoverService.findBynumber(number);
				//2、判断流水号是否失效或无效(流水号存在且流水号有绑定)
				if(tc!=null&&!tc.equals("")&&tc.getTeacherName()!=null&&!tc.getTeacherName().equals("")&&tc.getTime()!=null&&!tc.getTime().equals("")){//有绑定教师
					//判断餐点
					Integer dinner= dinner(time);//1早2中3晚
					//查找所在班级今天这一餐是否有就餐安排
					boolean ifDing =  teachDinService.ifDing(tc.getNumber(),tc.getTeacherName(),tc.getTime(),time,dinner);
					if(ifDing){//该餐有就餐安排
						String timePD = time.substring(0,10);
						//4、就餐安排是否过期
						TeachDining td = teachRecoverService.findMax(tc.getNumber(),tc.getTeacherName(),tc.getTime());//查找最后就餐安排日期
						//  查询就餐记录表 判断当天晚餐是否刷过
						TeachDiningRecord tdr = teachDinRecordService.findByTime(tc.getTime(),timePD,tc.getNumber(),tc.getTeacherName());
						if(dinner==3){//晚餐
							if(tdr!=null){//该天 此流水号有就餐记录
								if(tdr.getDinner()==2){//该时间段 未就餐
									tdr.setDinner(1);
									teachDinRecordService.update(tdr);//按时间段存储就餐信息
									cmsResult = new CmsResult(ApiCode.SUCCESS,tdr);
								}else {
									cmsResult = new CmsResult(ApiCode.FAILED_limit,tdr);//每人每餐只能刷一次
								}
							}else {//该天 此流水号没有就餐记录
								TeachDiningRecord tNew = new TeachDiningRecord();
								tNew.setDinner(1);
								tNew.setArea(classInfoService.get(Long.valueOf(td.getClassId())).getDiningPlace());
								tNew.setBreakfast(2);
								tNew.setLunch(2);
								tNew.setClassRoom(td.getClassId());
								tNew.setTeacherDay(timePD);
								tNew.setTeacherName(td.getTeacherName());
								tNew.setNum(td.getNumber());
//                              tNew.setAuthorizerId(td.getArranger());
								tNew.setTime(td.getTime());
								teachDinRecordService.save(tNew);   //按时间段存储就餐信息
								cmsResult = new CmsResult(ApiCode.SUCCESS,tNew);
							}
						}else if(dinner==2){//中餐
							if(tdr!=null){//该天 此流水号有就餐记录
								if(tdr.getLunch()==2){//该时间段 未就餐
									tdr.setLunch(1);
									teachDinRecordService.update(tdr);//按时间段存储就餐信息
									cmsResult = new CmsResult(ApiCode.SUCCESS,tdr);
								}else {
									cmsResult = new CmsResult(ApiCode.FAILED_limit,tdr);//每人每餐只能刷一次
								}
							}else {//该天 此流水号没有就餐记录
								TeachDiningRecord tNew = new TeachDiningRecord();
								tNew.setDinner(2);
								tNew.setArea(classInfoService.get(Long.valueOf(td.getClassId())).getDiningPlace());
								tNew.setBreakfast(2);
								tNew.setLunch(1);
								tNew.setClassRoom(td.getClassId());
								tNew.setTeacherDay(timePD);
								tNew.setTeacherName(td.getTeacherName());
								tNew.setNum(td.getNumber());
//                              tNew.setAuthorizerId(td.getArranger());
								tNew.setTime(td.getTime());
								teachDinRecordService.save(tNew);   //按时间段存储就餐信息
								cmsResult = new CmsResult(ApiCode.SUCCESS,tNew);
							}
						}else if(dinner==1){//早餐
							if(tdr!=null){//该天 此流水号有就餐记录
								if(tdr.getBreakfast()==2){//该时间段 未就餐
									tdr.setBreakfast(1);
									teachDinRecordService.update(tdr);//按时间段存储就餐信息
									cmsResult = new CmsResult(ApiCode.SUCCESS,tdr);
								}else {
									cmsResult = new CmsResult(ApiCode.FAILED_limit,tdr);//每人每餐只能刷一次
								}
							}else {//该天 此流水号没有就餐记录
								TeachDiningRecord tNew = new TeachDiningRecord();
								tNew.setDinner(2);
								tNew.setArea(classInfoService.get(Long.valueOf(td.getClassId())).getDiningPlace());
								tNew.setBreakfast(1);
								tNew.setLunch(2);
								tNew.setClassRoom(td.getClassId());
								tNew.setTeacherDay(timePD);
								tNew.setTeacherName(td.getTeacherName());
								tNew.setNum(td.getNumber());
//                              tNew.setAuthorizerId(td.getArranger());
								tNew.setTime(td.getTime());
								teachDinRecordService.save(tNew);   //按时间段存储就餐信息
								cmsResult = new CmsResult(ApiCode.SUCCESS,tNew);
							}
						}
					}else {
						cmsResult = new CmsResult(ApiCode.FAILED_no,tc);//无就餐安排  ifDing返回值为false
					}
				}else {
					cmsResult=new CmsResult(ApiCode.FAILED_invalid2);
				}
				return cmsResult;
			}
		}else {
			return new CmsResult(ApiCode.UNKNOWN_ERROR);
		}
		return cmsResult;
	}


	@Override
	public Integer dinner(String time) throws ParseException {
		//判断餐点
		Integer dinner = 1;
		String timePD12 = time.substring(11,13);
		time = time.substring(11);
		String zhtimePD =" 15:00:00";//中餐
		String ztimePD = " 10:00:00";//早餐
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		if((dateFormat.parse(time).getTime())>=(dateFormat.parse(zhtimePD).getTime())){//晚餐
			dinner = 3;
		}else if((dateFormat.parse(time).getTime())>=(dateFormat.parse(ztimePD).getTime())||timePD12.equals("12")){//中餐
			dinner = 2;
		}else {//早餐
			dinner = 1;
		}
		return dinner ;
	}
}
