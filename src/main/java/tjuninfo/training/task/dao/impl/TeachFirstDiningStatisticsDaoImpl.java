package tjuninfo.training.task.dao.impl;



import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ITeachDiningStatisticsDao;
import tjuninfo.training.task.dao.ITeachFirstDiningStatisticsDao;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachFirstDiningStatisticsVO;

import java.util.List;


/**
 * 教师首日就餐数据层接口实现类
 * @author 
 * @date 2018年5月23日
 */
@Repository
public class TeachFirstDiningStatisticsDaoImpl extends BaseDaoImpl<TeachFirstDiningStatisticsVO> implements ITeachFirstDiningStatisticsDao {

	public TeachFirstDiningStatisticsDaoImpl() {
		super(TeachFirstDiningStatisticsVO.class);
	}

	@Override
	public List<TeachFirstDiningStatisticsVO> count(String startDay, String endDay) {
		StringBuffer sql = null ;
		if (startDay!=null && !startDay.equals("")) {
			sql = new StringBuffer("SELECT sss.area as diningPlace, SUM(sss.tb=1) as dBreakfast,SUM(sss.tl=1) as dLunch," +
					"SUM(sss.td=1) as dDinner,SUM(sss.rb=1) as rBreakfast,SUM(sss.rl=1) as rLunch ,SUM(sss.rd=1)as rDinner FROM(\n" +
					"SELECT ss.* FROM ( SELECT s.* FROM ( SELECT t.number, t.area, t.`day`,\n" +
					" t.breakfast AS tb, t.lunch AS tl, t.dinner AS td, tdr.breakfast AS rb, tdr.lunch AS rl,\n" +
					" tdr.dinner AS rd FROM teach_dining t LEFT JOIN teach_dining_record tdr ON t.number = tdr.num\n" +
					" AND t. DAY = tdr.t_day ) s where s.`day` is not null ORDER BY\n" +
					" s. DAY ) ss GROUP BY ss.number HAVING ss.`day` >='" + startDay + "' and ss.`day` <='" + endDay + "') sss GROUP BY sss.area");
		}else {
			sql =new StringBuffer( "SELECT sss.area as diningPlace, SUM(sss.tb=1) as dBreakfast,SUM(sss.tl=1) as dLunch," +
					"SUM(sss.td=1) as dDinner,SUM(sss.rb=1) as rBreakfast,SUM(sss.rl=1) as rLunch ,SUM(sss.rd=1)as rDinner FROM(\n" +
					"SELECT ss.* FROM ( SELECT s.* FROM ( SELECT t.number, t.area, t.`day`, t.breakfast AS tb,\n" +
					" t.lunch AS tl, t.dinner AS td, tdr.breakfast AS rb, tdr.lunch AS rl, tdr.dinner AS rd\n" +
					" FROM teach_dining t LEFT JOIN teach_dining_record tdr ON t.number = tdr.num AND t. DAY = tdr.t_day\n" +
					" ) s where s.`day` is not null ORDER BY s. DAY ) ss GROUP BY ss.number) sss GROUP BY sss.area");
		}
		List<TeachFirstDiningStatisticsVO> list = super.getSession().createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(TeachFirstDiningStatisticsVO.class)).list();
		return list;
	}

	@Override
	public List<TeachFirstDiningStatisticsVO> count2(String startDay, String endDay) {
		StringBuffer sql = null ;
		if (startDay!=null && !startDay.equals("")) {
			sql= new StringBuffer("SELECT aa.dp as diningPlace, SUM(aa.abreakfast=1) as dBreakfast, SUM(aa.adinner=1) as dDinner, SUM(aa.alunch=1) as dLunch,SUM(aa.breakfast=1) as rBreakfast ,\n" +
					" SUM(aa.lunch =1) as rLunch,SUM(aa.dinner =1) as rDinner from (SELECT a.* FROM ( SELECT s.* FROM (SELECT DISTINCT\n" +
					"tdfs.dining_date AS DAY,tdfs.dining_palce as dp,tdfs.breakfast AS abreakfast,tdfs.dinner AS adinner,\n" +
					"tdfs.lunch AS alunch,tdr.class_id,tdr.teacher_id,tdfr.breakfast,tdfr.lunch,tdfr.dinner FROM\n" +
					"teacher_dining_for_scene tdfs LEFT JOIN teacher_dining_register tdr ON tdfs.tdr_id = tdr.id\n" +
					"LEFT JOIN teach_dining_face_record tdfr ON tdfr.class_id = tdr.class_id\n" +
					"AND tdfs.dining_date = tdfr.`day`) s ORDER BY s.`day`) a\n" +
					"GROUP BY a.class_id HAVING a. DAY >= '" + startDay + "' and a.Day <= '"+ endDay+"'\n" +
					")aa GROUP BY aa.dp");
		}else {
			sql= new StringBuffer("SELECT aa.dp as diningPlace, SUM(aa.abreakfast=1) as dBreakfast, SUM(aa.adinner=1) as dDinner, SUM(aa.alunch=1) as dLunch,SUM(aa.breakfast=1) as rBreakfast ,\n" +
					" SUM(aa.lunch =1) as rLunch,SUM(aa.dinner =1) as rDinner  from (SELECT a.* FROM ( SELECT s.* FROM (SELECT DISTINCT\n" +
					"tdfs.dining_date AS DAY,tdfs.dining_palce as dp,tdfs.breakfast AS abreakfast,tdfs.dinner AS adinner,\n" +
					"tdfs.lunch AS alunch,tdr.class_id,tdr.teacher_id,tdfr.breakfast,tdfr.lunch,tdfr.dinner FROM\n" +
					"teacher_dining_for_scene tdfs LEFT JOIN teacher_dining_register tdr ON tdfs.tdr_id = tdr.id\n" +
					"LEFT JOIN teach_dining_face_record tdfr ON tdfr.class_id = tdr.class_id\n" +
					"AND tdfs.dining_date = tdfr.`day`) s ORDER BY s.`day`) a\n" +
					"GROUP BY a.class_id \n" +
					")aa GROUP BY aa.dp");
		}
		List<TeachFirstDiningStatisticsVO> list = super.getSession().createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(TeachFirstDiningStatisticsVO.class)).list();
		return list;
	}
}