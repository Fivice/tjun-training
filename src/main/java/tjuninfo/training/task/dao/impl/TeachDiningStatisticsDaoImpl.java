package tjuninfo.training.task.dao.impl;



import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IStuDiningStatisticsDao;
import tjuninfo.training.task.dao.ITeachDiningStatisticsDao;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachFirstDiningStatisticsVO;

import java.util.List;


/**
 * 教师就餐数据层接口实现类
 */
@Repository
public class TeachDiningStatisticsDaoImpl extends BaseDaoImpl<TeachDiningStatisticsVO> implements ITeachDiningStatisticsDao {

	public TeachDiningStatisticsDaoImpl() {
		super(TeachDiningStatisticsVO.class);
	}

	@Override
	public List<TeachDiningStatisticsVO> count(String startDay, String endDay) {
		StringBuffer sql = null ;
		if (startDay!=null && !startDay.equals("")) {
			sql = new StringBuffer("SELECT aa.area AS diningPlace, SUM(aa.abreakfast = 1) AS dBreakfast, SUM(aa.alunch= 1) AS dLunch,\n" +
					" SUM(aa.adinner= 1) AS dDinner, SUM(aa.breakfast = 1) AS rBreakfast, SUM(aa.lunch = 1) AS rLunch,SUM(aa.dinner = 1) AS rDinner\n" +
					"FROM ( SELECT a.area, a. DAY, a.breakfast AS abreakfast, a.lunch AS aLunch, a.dinner AS adinner,\n" +
					" tdr.breakfast, tdr.lunch, tdr.dinner FROM teach_dining a LEFT JOIN teach_dining_record tdr ON a.number = tdr.num\n" +
					"AND a.`day` = tdr.t_day) aa where aa.`DAY` IS NOT NULL AND aa.`DAY`>='"+startDay+"' and aa.`DAY`<='"+endDay+"' \n" +
					"GROUP BY aa.area ");
		}else {
			sql = new StringBuffer("SELECT aa.area AS diningPlace, SUM(aa.abreakfast = 1) AS dBreakfast, SUM(aa.alunch= 1) AS dLunch,\n" +
					" SUM(aa.adinner= 1) AS dDinner, SUM(aa.breakfast = 1) AS rBreakfast,SUM(aa.lunch = 1) AS rLunch,SUM(aa.dinner = 1) AS rDinner \n" +
					" FROM ( SELECT a.area, a. DAY, a.breakfast AS abreakfast, a.lunch AS alunch, a.dinner AS adinner, \n" +
					" tdr.breakfast, tdr.lunch, tdr.dinner FROM teach_dining a LEFT JOIN teach_dining_record tdr ON a.number = tdr.num \n" +
					" AND a.`day` = tdr.t_day) aa where aa.`DAY` IS NOT NULL \n" +
					" GROUP BY aa.area  ");
		}
        List<TeachDiningStatisticsVO> list = super.getSession().createSQLQuery(sql.toString())
                .setResultTransformer(Transformers.aliasToBean(TeachDiningStatisticsVO.class)).list();
        return list;
	}

	@Override
	public List<TeachDiningStatisticsVO> count2(String startDay, String endDay) {
		StringBuffer sql = null ;
		if(startDay != null && !startDay.equals("")){
			sql = new StringBuffer("SELECT  aa.dp as diningPlace, SUM(aa.abreakfast=1) as dBreakfast, SUM(aa.adinner=1) as dDinner, SUM(aa.alunch=1) as dLunch,\n" +
					"SUM(aa.breakfast=1) as rBreakfast, SUM(aa.lunch=1) as rLunch, SUM(aa.dinner=1) as rDinner\n" +
					" from ( SELECT  tdfs.dining_date AS DAY, tdfs.dining_palce as dp, tdfs.breakfast AS abreakfast, tdfs.dinner AS adinner,\n" +
					" tdfs.lunch AS alunch, tdr.class_id, tdr.teacher_id, tdfr.breakfast, tdfr.lunch, tdfr.dinner\n" +
					" FROM teacher_dining_for_scene tdfs LEFT JOIN teacher_dining_register tdr ON tdfs.tdr_id = tdr.id\n" +
					" LEFT JOIN teach_dining_face_record tdfr ON tdfr.class_id = tdr.class_id\n" +
					" AND tdfs.dining_date = tdfr.`day` )aa where aa.`DAY` >= '"+startDay+"' and aa.`DAY` <= '"+endDay+"'  GROUP BY aa.dp");
		}else {
			sql = new StringBuffer("SELECT  aa.dp as diningPlace, SUM(aa.abreakfast=1) as dBreakfast, SUM(aa.adinner=1) as dDinner, SUM(aa.alunch=1) as dLunch, \n" +
					" SUM(aa.breakfast=1) as rBreakfast, SUM(aa.lunch=1) as rLunch, SUM(aa.dinner=1) as rDinner \n" +
					" from ( SELECT  tdfs.dining_date AS DAY, tdfs.dining_palce as dp, tdfs.breakfast AS abreakfast, tdfs.dinner AS adinner, \n" +
					" tdfs.lunch AS alunch, tdr.class_id, tdr.teacher_id, tdfr.breakfast, tdfr.lunch, tdfr.dinner \n" +
					" FROM teacher_dining_for_scene tdfs LEFT JOIN teacher_dining_register tdr ON tdfs.tdr_id = tdr.id \n" +
					" LEFT JOIN teach_dining_face_record tdfr ON tdfr.class_id = tdr.class_id  \n" +
					" AND tdfs.dining_date = tdfr.`day` )aa   GROUP BY aa.dp");
		}
		List<TeachDiningStatisticsVO> list = super.getSession().createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(TeachDiningStatisticsVO.class)).list();
		return list;
	}
}