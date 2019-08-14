package tjuninfo.training.task.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.RegisterDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.Register;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.RegisterService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * 报到信息表业务层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Service
public class RegisterServiceImpl extends BaseServiceImpl<Register> implements RegisterService {
	private RegisterDao registerDao;
	@Autowired
	private ClassInfoService classInfoService;




	@Resource
	public void setSysAuthorityDao(RegisterDao registerDao) {
		this.registerDao = registerDao;
		this.dao = registerDao;
	}


	@Override
	public List<Register> getAll() {
		return registerDao.getAll();
	}


	@Override
	public List<Register> findByClassId(Integer classId) {
		return registerDao.findByClassId(classId);
	}

	@Override
	public Register findRegister(Integer siId, String number, String reportTime) {
		return registerDao.findRegister(siId,number,reportTime);
	}

	@Override
	public Register findStuRegister(Integer siId) {
		return registerDao.findStuRegister(siId);
	}


	@Override
	public List<ClassDining> findClassDiningList(String classNum) {
		return registerDao.findClassDiningList(classNum);
	}


	@Override
	public Long studentCountByRegister(Long classId) {
		return registerDao.studentCountByRegister(classId);
	}

	@Override
	public long trainingFeeByRegister(Long classId) {
		return registerDao.trainingFeeByRegister(classId);
	}

	@Override
	public long hotelByRegister(Long classId) {
		return registerDao.hotelByRegister(classId);
	}

	@Override
	public Double trainingExpenseByRegister(Long classId) {
		return registerDao.trainingExpenseByRegister(classId);
	}

	@Override
	public Double hotelDayCount(Long classId) {
		//获取该班级所有报到学员的信息表
		List<Register> registerList = findAllByClassId(classId);
		ClassInfo classInfo = classInfoService.get(classId);
		double totalHotelDays = 0;
		for (Register r:registerList
			 ) {
			//计算学员的住宿天数，通过住宿总费用除以该学员住宿一天的费用
			double dayFee = 0;
			double hotelDays = 0;
			//标间情况下每天住宿费用
			if (r.getHotel() == 0){
				dayFee = classInfo.getInterScaleFee();
			}
			//单间情况下每天住宿费用
			if (r.getHotel() == 1){
				dayFee = classInfo.getSingleRoomCharge();
			}
			//当该学员住宿时使用总费用除以每日的住宿费，得到住宿天数
			if (r.getHotel()!=2){
				//计算当前学员住宿天数
				hotelDays = r.getScaleFeeTotal()/(dayFee);
				//累加当前学员住宿天数到该班级住宿合计天数上
				totalHotelDays += hotelDays;
			}

		}
		return totalHotelDays;
	}

	@Override
    public Double scaleFeeTotalByRegister(Long classId) {
        return registerDao.scaleFeeTotalByRegister(classId);
    }

    @Override
    public long foodTotalByRegister(Long classId) {
        return registerDao.foodTotalByRegister(classId);
    }

    @Override
    public Double foodTotalByRegister2(Long classId) {
        return registerDao.foodTotalByRegister2(classId);
    }

    @Override
    public Double otherChargesByRegister(Long classId) {
        return registerDao.otherChargesByRegister(classId);
    }

	@Override
	public List<Register> findAllByClassId(long classId) {
		return registerDao.findAllByClassId(classId);
	}

	@Override
	public List<Register> findRegisters(String studentId, String classId) {
		return registerDao.findRegisters(studentId,classId);
	}

	@Override
	public List<Register> findRegistersBysIdAndcId(String studentId, String classId) {
		return registerDao.findRegistersBysIdAndcId(studentId,classId);
	}

	@Override
	public Pages findAllByClassId(int pageSize, int pageNumber, long l) {
		return registerDao.findAllByClassId(pageSize,pageNumber,l);
	}

	@Override
	public Long evaluationStudentCountByRegister(Long id) {
		return registerDao.evaluationStudentCountByRegister(id);
	}

	@Override
	public Register getStuRegister(Integer siId, Long classId) {
		return registerDao.getStuRegister(siId,classId);
	}

	@Override
	public List<Register> getTodayDining() {
		return registerDao.getTodayDining();
	}
	public BigInteger getClassDiningCount(String diningPlace, String classId){
		return registerDao.getClassDiningCount(diningPlace,classId);
	}
}
