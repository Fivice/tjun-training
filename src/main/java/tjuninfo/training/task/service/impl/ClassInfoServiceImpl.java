package tjuninfo.training.task.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ClassInfoDao;
import tjuninfo.training.task.dto.ClassInfoForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.service.ClassDiningService;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.RegisterService;
import tjuninfo.training.task.service.TeachDiningService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 班级信息表业务层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Service
public class ClassInfoServiceImpl extends BaseServiceImpl<ClassInfo> implements ClassInfoService {
	private ClassInfoDao classInfoDao;
	@Autowired
	private RegisterService registerService;
	@Resource
	private ClassDiningService classDiningService;
	@Resource
	private TeachDiningService teachDiningService;
	@Resource
	public void setSysAuthorityDao(ClassInfoDao classInfoDao) {
		this.classInfoDao = classInfoDao;
		this.dao = classInfoDao;
	}


	@Override
	public Pages getList(int pageSize, int pageIndex,String plan,String classNumber,String startStopTime,String className,String teacherName,String userId,String time,String regPlace,String order,String entryStartTime,String evaluateStopTime,String sort, String sortOrder) {
		return classInfoDao.getList(pageSize,pageIndex,plan,classNumber,startStopTime,className,teacherName,userId,time,regPlace, order,entryStartTime,evaluateStopTime,sort,sortOrder);
	}

	@Override
	public List<ClassInfo> getClassInfoList(int pageSize, int pageIndex, String plan, String classNumber, String startStopTime, String className, String teacherName, String userId,String time, String regPlace) {
        List<ClassInfo> list = classInfoDao.getClassInfoList(plan,classNumber,startStopTime,className,teacherName,userId,null,regPlace);
        List<ClassInfo> newList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date timeDate = null;
        try {
            timeDate = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Object temp:list
             ) {
            ClassInfo classInfo = (ClassInfo) temp;
            String duringTime = classInfo.getStartStopTime();

            int dateLength = 10;
            //取出开始时间
            String startTime = duringTime.substring(0,dateLength);
            Date startTimeDate = null;
            try {
                startTimeDate = sdf.parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //取出结束时间
            String endTime = duringTime.substring(dateLength+3);
            Date endTimeDate = null;
            try {
                endTimeDate = sdf.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //判断time是否在开始和结束时间之间
            int v1 =timeDate.compareTo(startTimeDate);//返回0||1则满足
            int v2 =timeDate.compareTo(endTimeDate);//返回0||-1则满足
            if (v1!=-1&&v2!=1){
                //在时间段内则往newList中添加这个对象
                newList.add(classInfo);
            }
        }
        list.clear();
        list.addAll(newList);
		return list;
	}

    @Override
    public Page<ClassInfo> transListToPage(int pageSize, int pageIndex, List list) {
	    Page<ClassInfo> page = new Page();
	    page.setPageSize(pageSize);
	    page.setPageNo(pageIndex);
	    page.setTotalRecords(list.size());
	    if (list.size()<=pageSize){
	        page.setList(list);
        }else if(pageIndex*pageSize<=list.size()-1){
	        page.setList(list.subList((pageIndex-1)*pageSize,(pageIndex-1)*pageSize+(pageSize)));
        }else{
	        page.setList(list.subList((pageIndex-1)*pageSize,list.size()));
        }
        return page;
    }

    @Override
	public List<ClassInfo> list() {
		return classInfoDao.list();
	}

	@Override
	public List<ClassInfo> getByUserId(Integer userId) {
		return classInfoDao.getByUserId(userId);
	}


	@Override
	public List<ClassInfo> findClassInfosByDate(String date,String type,String plan) {
		return classInfoDao.findClassInfosByDate(date,type,plan);
	}

	@Override
	public List<ClassInfo> findList(Integer siId) {
		return classInfoDao.findList(siId);
	}

	@Override
	public List<ClassVo> findList(BTView<ClassVo> bt, Integer siId) {
		return classInfoDao.findList(bt,siId);
	}

	@Override
	public List<ClassInfo> findAll() {
		return classInfoDao.getAll();
	}

	@Override
	public Pages findAllByStartStopTime(int pageSize, int pageIndex,String startDay, String endDay, String classNumber, String className, String trainingType, String plan,Integer useId,String place) {
		return classInfoDao.findAllByStartStopTime(pageSize,pageIndex,startDay,endDay,classNumber,className,trainingType,plan,useId,place);
	}

	@Override
	public Pages classList(int pageSize, int pageIndex, String startStopTime, String classNumber, String className, String trainingType, String plan, Integer useId, String place) {
		return classInfoDao.classList(pageSize,pageIndex,startStopTime,classNumber,className,trainingType,plan,useId,place);
	}

	@Override
	public ClassInfo getClassIfo(Integer siId, String time) {
		return classInfoDao.getClassIfo(siId,time);
	}

	@Override
	public List<ClassInfo> byDiningPlace(String eatPlace) {
		return classInfoDao.byDiningPlace(eatPlace);
	}

	//根据班级编号查询班级信息
	public ClassInfo getByclassNumber(String classNumber,String classInfoId){

		return classInfoDao.getByclassNumber(classNumber,classInfoId);
	}

	@Override
	public ClassInfo getByclassNumber(String classNumber) {
		return classInfoDao.getByclassNumber(classNumber);	}

//    public static void main(String[] args) {
//        String duringTime = "2018-09-01 至 2018-09-17";
//        DateStyle dateStyle = DateUtil.getDateStyle(duringTime);
//        String startTime = duringTime.substring(0,dateStyle.getValue().length());
//        String endTime = duringTime.substring(dateStyle.getValue().length()+3);
//        System.out.println(startTime+"/"+endTime);
//    }

	@Override
	public Page<ClassInfoForDiningStatisticsVO> getClassInfo(int pageIndex, int pageSize, String startStopTime, String diningPlace,Integer userId) {
		Page<ClassInfoForDiningStatisticsDTO> classInfoPage = classInfoDao.getClassInfo(pageIndex,pageSize,startStopTime,diningPlace,userId);
		Page<ClassInfoForDiningStatisticsVO> newPage = new Page<>();
		List<ClassInfoForDiningStatisticsDTO> classInfoList = classInfoPage.getList();
		List<ClassInfoForDiningStatisticsVO> list = new ArrayList<>();
		for (ClassInfoForDiningStatisticsDTO classInfo: classInfoList
			 ) {
			Long RegisterCount = registerService.studentCountByRegister(Long.parseLong(classInfo.getClassId().toString()));
			ClassInfoForDiningStatisticsVO classInfoForDiningStatisticsVO = new ClassInfoForDiningStatisticsVO(
					Long.parseLong(classInfo.getClassId().toString()),
					classInfo.getClassNumber(),
					classInfo.getClassName(),
					classInfo.getDiningPlace(),
					RegisterCount,
					classInfo.getPlannedNumber()
			);
			list.add(classInfoForDiningStatisticsVO);
		}
		newPage.setList(list);
		newPage.setTotalRecords(classInfoPage.getTotalRecords());
		newPage.setPageNo(classInfoPage.getPageNo());
		newPage.setPageSize(classInfoPage.getPageSize());
		return newPage;
	}

	@Override
	public Page<ClassInfoForDiningPrepVO>  findPrepList(int pageSize, int pageIndex, String today, String diningPlace) {
		Page<ClassInfoForDiningStatisticsDTO> classInfoPage = classInfoDao.findPrepList(pageIndex,pageSize,today,diningPlace);
		Page<ClassInfoForDiningPrepVO> newPage = new Page<>();

		List<ClassInfoForDiningStatisticsDTO> classInfoList = classInfoPage.getList();

		List<ClassInfoForDiningPrepVO> list = new ArrayList<>();


		for (ClassInfoForDiningStatisticsDTO classInfo: classInfoList
		) {
			//通过班级id和日期获取就餐情况
			List<ClassDining> cdList = classDiningService.findClassDiningList(String.valueOf(classInfo.getClassId()), today);
			if (cdList.size() > 0) {


				int breakfast = cdList.get(0).getBreakfast();//获取早餐
				int lunch = cdList.get(0).getLunch();//午餐
				int dinner = cdList.get(0).getDinner();//晚餐

				Long RegisterCount = registerService.studentCountByRegister(Long.parseLong(classInfo.getClassId().toString()));
				ClassInfoForDiningPrepVO classDiningPrepVO = new ClassInfoForDiningPrepVO();
				if (breakfast == 2) {//判断早餐计划是否就餐

					classDiningPrepVO.setClassId(Long.parseLong(classInfo.getClassId().toString()));
					classDiningPrepVO.setClassName(classInfo.getClassName());
					classDiningPrepVO.setClassNumber(classInfo.getClassNumber());
					classDiningPrepVO.setDiningPlace(classInfo.getDiningPlace());
					classDiningPrepVO.setPlannedNumberBf(0);

					classDiningPrepVO.setRegisterNumberBf(0);

					if (lunch == 2) {//判断午餐是否就餐
						classDiningPrepVO.setPlannedNumberLu(0);
						classDiningPrepVO.setRegisterNumberLu(0);
						if (dinner == 2) {
							classDiningPrepVO.setPlannedNumberDi(0);
							classDiningPrepVO.setRegisterNumberDi(0);
						} else {
							classDiningPrepVO.setPlannedNumberDi(classInfo.getPlannedNumber());
							classDiningPrepVO.setRegisterNumberDi(RegisterCount.intValue());
						}
					} else {
						classDiningPrepVO.setPlannedNumberLu(classInfo.getPlannedNumber());
						classDiningPrepVO.setRegisterNumberLu(RegisterCount.intValue());
						if (dinner == 2) {
							classDiningPrepVO.setPlannedNumberDi(0);
							classDiningPrepVO.setRegisterNumberDi(0);
						} else {
							classDiningPrepVO.setPlannedNumberDi(classInfo.getPlannedNumber());
							classDiningPrepVO.setRegisterNumberDi(RegisterCount.intValue());
						}
					}

				} else {
					classDiningPrepVO.setClassId(Long.parseLong(classInfo.getClassId().toString()));
					classDiningPrepVO.setClassName(classInfo.getClassName());
					classDiningPrepVO.setClassNumber(classInfo.getClassNumber());
					classDiningPrepVO.setDiningPlace(classInfo.getDiningPlace());

					classDiningPrepVO.setPlannedNumberBf(classInfo.getPlannedNumber());
					classDiningPrepVO.setRegisterNumberBf(RegisterCount.intValue());

					if (lunch == 2) {
						classDiningPrepVO.setPlannedNumberLu(0);
						classDiningPrepVO.setRegisterNumberLu(0);
						if (dinner == 2) {
							classDiningPrepVO.setPlannedNumberDi(0);
							classDiningPrepVO.setRegisterNumberDi(0);
						} else {
							classDiningPrepVO.setPlannedNumberDi(classInfo.getPlannedNumber());
							classDiningPrepVO.setRegisterNumberDi(RegisterCount.intValue());
						}
					} else {
						classDiningPrepVO.setPlannedNumberLu(classInfo.getPlannedNumber());
						classDiningPrepVO.setRegisterNumberLu(RegisterCount.intValue());
						if (dinner == 2) {
							classDiningPrepVO.setPlannedNumberDi(0);
							classDiningPrepVO.setRegisterNumberDi(0);
						} else {
							classDiningPrepVO.setPlannedNumberDi(classInfo.getPlannedNumber());
							classDiningPrepVO.setRegisterNumberDi(RegisterCount.intValue());
						}
					}
				}


				//通过班级id和日期分别获取教师就餐情况

				List<TeachDining> teList1 = teachDiningService.TeachDiningPrepList(String.valueOf(classInfo.getClassId()), today,1,null,null);
				List<TeachDining> teList2 = teachDiningService.TeachDiningPrepList(String.valueOf(classInfo.getClassId()), today,null,1,null);
				List<TeachDining> teList3 = teachDiningService.TeachDiningPrepList(String.valueOf(classInfo.getClassId()), today,null,null,1);
				classDiningPrepVO.setTeacherBf(teList1.size());
				classDiningPrepVO.setTeacherLu(teList2.size());
				classDiningPrepVO.setTeacherDi(teList3.size());
				list.add(classDiningPrepVO);
			}
		}
		newPage.setList(list);
		newPage.setTotalRecords(classInfoPage.getTotalRecords());
		newPage.setPageNo(classInfoPage.getPageNo());
		newPage.setPageSize(classInfoPage.getPageSize());
		return newPage;
	}

	@Override
	public String findMaxClassNumber() {
		return classInfoDao.findMaxClassNumber();
	}

	@Override
	public ClassInfo findClassInfoByClassId(String classId) {
		return classInfoDao.findClassInfoByClassId(classId);
	}

	@Override
	public ClassInfoForTeaDinListVo getTeachDinProfile(int pageSize, int pageIndex, String classNumber,
													   String startStopTime, String className, String teacherName,
													   String userId, String orderName, String orderBy){

		List<ClassInfoForTeachProfileVO> teachProfileList = new ArrayList<>();
		Pages pages = classInfoDao.getTeachDinProfile(pageSize,pageIndex,classNumber,startStopTime,className,teacherName,userId,orderName,orderBy);
		List teachDinProfileArrayList = pages.getResult();
		long totalResults = pages.getTotalResults();
		long totalPages = pages.getTotalPages();

		for ( Object temp:teachDinProfileArrayList
		) {

			ClassInfo classInfo = (ClassInfo) temp;
			//查询教师就餐人数（班级id,月份）
			long teacherCount = teachDiningService.getCountByClassIdFromTeachDining(classInfo.getId(),startStopTime);

			//实例化ClassInfoForTeachProfileVO对象并添加入List表中
			ClassInfoForTeachProfileVO cftProfile = new ClassInfoForTeachProfileVO();
			cftProfile.setClassId(classInfo.getId());
			cftProfile.setClassName(classInfo.getClassName());
			cftProfile.setClassNumber(classInfo.getClassNumber());
			cftProfile.setTeacherName(classInfo.getTeacherName());
			cftProfile.setTeacherDinCount(teacherCount);
			teachProfileList.add(cftProfile);

		}
		return new ClassInfoForTeaDinListVo(totalResults,totalPages,teachProfileList);
	}

	@Override
	public Boolean ifExest(Long id) {
		boolean b =false;
		ClassInfo c = classInfoDao.get(id);
		if(c!=null&&!c.equals("")){
			b=true;
		}
		return b;
	}
}
