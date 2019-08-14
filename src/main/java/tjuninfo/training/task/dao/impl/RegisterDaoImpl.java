package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.RegisterDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.Register;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/**
 * 报到表数据层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Repository
public class RegisterDaoImpl extends BaseDaoImpl<Register> implements RegisterDao {

	public RegisterDaoImpl(){
		super(Register.class);
	}


	@Override
	public List<Register> findByClassId(Integer classId) {
		List<Register> list=null;
		StringBuffer hql =new StringBuffer(" from Register r where 1=1 ");
		if(classId!= null && !classId.equals("")){
			hql.append( " and r.classId = "+classId);
		}

		return getSession().createQuery(hql.toString()).list();
	}


	@Override
	public List<ClassDining> findClassDiningList(String classNum) {
		StringBuffer hql =new StringBuffer("from ClassDining c where 1=1 ") ;
		if (classNum!=null && !classNum.equals("")){
			hql.append(" and c.classRoom="+classNum.trim());
		}
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public long studentCountByRegister(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT COUNT(1) from Register r where 1=1") ;
			hql.append(" and r.classId = "+classId);
		long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public long trainingFeeByRegister(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT COUNT(trainingExpense) from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
		hql.append("and r.trainingExpense <>"+0);
		long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public long hotelByRegister(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT COUNT(scaleFeeTotal) from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
		hql.append(" and r.hotel <> "+2);
		long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public Double trainingExpenseByRegister(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT SUM(trainingExpense) from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
//		hql.append(" and r.trainingExpense <> "+0);
		Double count = (Double) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public Double scaleFeeTotalByRegister(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT SUM(scaleFeeTotal) from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
//		hql.append(" and r.trainingExpense <> "+0);
		Double count = (Double) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public long foodTotalByRegister(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT COUNT(foodTotal) from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
		hql.append(" and r.dining = "+1);
		long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public Double foodTotalByRegister2(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT SUM(foodTotal) from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
//		hql.append(" and r.foodTotal <> "+0);
		Double count = (Double) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public Double otherChargesByRegister(Long classId) {
		StringBuffer hql =new StringBuffer("SELECT SUM(otherCharges) from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
//		hql.append(" and r.foodTotal <> "+0);
		Double count = (Double) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public List<Register> findAllByClassId(long classId) {
		StringBuffer hql =new StringBuffer("from Register r where 1=1") ;
		hql.append(" and r.classId = "+classId);
		return getSession().createQuery(hql.toString()).list();
	}
	@Override
	public List<Register> findRegisters(String studentId, String classId) {
		StringBuffer hql =new StringBuffer("select new Register(id) from Register r where 1=1");
		if (classId!=null && !classId.equals("")){
			hql.append(" and r.classId = "+Integer.parseInt(classId.trim()));
		}
		if (studentId!=null && !studentId.equals("")){
			hql.append(" and r.siId = "+Integer.parseInt(studentId.trim()));
		}
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public List<Register> findRegistersBysIdAndcId(String studentId, String classId) {
		StringBuffer hql =new StringBuffer("from Register r where 1=1");
		if (classId!=null && !classId.equals("")){
			hql.append(" and r.classId = "+Integer.parseInt(classId.trim()));
		}
		if (studentId!=null && !studentId.equals("")){
			hql.append(" and r.siId = "+Integer.parseInt(studentId.trim()));
		}
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public Register findRegister(Integer siId, String number, String reportTime) {
		StringBuffer hql =new StringBuffer("from Register r where 1=1") ;
		hql.append(" and r.siId = "+siId);
		hql.append(" and r.number = "+"'"+number+"'");
		hql.append(" and r.reportTime = "+"'"+reportTime+"'");
		Register register = (Register)getSession().createQuery(hql.toString()).uniqueResult();
		return register;
	}

	@Override
	public Register findStuRegister(Integer siId) {
		StringBuffer hql =new StringBuffer("from Register r where 1=1") ;
		hql.append(" and r.siId = "+siId);
		Register register = (Register)getSession().createQuery(hql.toString()).uniqueResult();
		return register;
	}

	@Override
	public Pages findAllByClassId(int pageSize, int pageNumber, long l) {
		StringBuffer hql =new StringBuffer("from Register r where 1=1") ;
		hql.append(" and r.classId = "+l);
		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageNumber, pageSize, query);
		return pages;
	}

	@Override
	public Long evaluationStudentCountByRegister(Long id) {
		StringBuffer hql =new StringBuffer("SELECT COUNT(1) from Register r where 1=1") ;
		hql.append("and r.status =" +1);
		hql.append(" and r.classId = "+id);
		long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
		return count;
	}

	@Override
	public Register getStuRegister(Integer siId, Long classId) {
        StringBuffer hql =new StringBuffer("from Register r where 1=1") ;
        hql.append("and r.siId =").append(siId);
        hql.append(" and r.classId = ").append(classId);
        Query query = getSession().createQuery(hql.toString());

		return (Register) query.uniqueResult();
	}

	@Override
	public List<Register> getTodayDining() {
		String currentTime = DateUtil.getDateTime(new Date()).substring(0,10);//获取当前日期
		StringBuffer sql =new StringBuffer("SELECT r.* from register r " +
				"where r.dining = 1 and r.class_id in (SELECT c.class_room " +
				"from class_dining c where c.`day` like '"+currentTime+"')") ;
		List<Register> list = this.getSession().createSQLQuery(sql.toString()).addEntity(Register.class).getResultList();
		return list;
	}

	@Override
	public BigInteger getClassDiningCount(String diningPlace, String classId) {
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM register r INNER JOIN class_info ci ON r.class_id = ci.id AND r.dining = 1");
		if (StringUtils.isNotBlank(diningPlace)){
			sql.append(" AND ci.dining_place = '").append(diningPlace).append("'");
		}
		if (StringUtils.isNotBlank(classId)){
			sql.append(" AND ci.id = ").append(classId);
		}
		Query query = getSession().createSQLQuery(sql.toString());
		BigInteger count = (BigInteger) query.uniqueResult();
		return count;
	}
}
