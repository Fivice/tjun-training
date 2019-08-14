package tjuninfo.training.task.dao.impl;



import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IStuDiningStatisticsDao;
import tjuninfo.training.task.dao.IUnitDao;
import tjuninfo.training.task.entity.Unit;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuDiningRecordVO;
import tjuninfo.training.task.vo.StuDiningStatisticsVO;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;

import java.util.List;


/**
 * 单位表数据层接口实现类
 * @author 
 * @date 2018年5月23日
 */
@Repository
public class StuDiningStatisticsDaoImpl extends BaseDaoImpl<StuDiningStatisticsVO> implements IStuDiningStatisticsDao {

	public StuDiningStatisticsDaoImpl() {
		super(StuDiningStatisticsVO.class);
	}

	@Override
	public Page count(int pageSize, int pageNumber, String startDay, String endDay) {
		StringBuffer sql = null;
		if (startDay!=null && !startDay.equals("")){
			sql = new StringBuffer("SELECT a.dining_place AS diningPlace, SUM((a.a2*a.cb)) AS dBreakfast, SUM((a.a2*a.cl)) AS dLunch,\n" +
					" SUM((a.a2*a.cd)) AS dDinner, SUM(aa.sb) AS rBreakfast, SUM(aa.sl) AS rLunch, SUM(aa.sd) AS rDinner FROM (SELECT a1.class_room,\n" +
					"a1.`day`,SUM(a1.breakfast = 1) AS cb,SUM(a1.lunch = 1) AS cl,SUM(a1.dinner = 1) AS cd,a1.planned_number, (SELECT SUM(dining=1) FROM register where class_id =a1.class_room)as a2,a1.dining_place from (\n" +
					" SELECT  cd.class_room, cd.`day`, cd.breakfast  , cd.lunch  , cd.dinner  , ci.planned_number, ci.dining_place FROM\n" +
					" class_dining cd LEFT JOIN class_info ci ON cd.class_room = ci.id )a1 WHERE  a1.`day`>='"+startDay+"' and a1.`day`<='"+endDay+"' \n" +
					" GROUP BY a1.class_room ) a LEFT JOIN (SELECT aa1.class_room, aa1.`day`, aa1.area, SUM(aa1.breakfast = 1) AS sb, SUM(aa1.lunch = 1) AS sl,\n" +
					" SUM(aa1.dinner = 1) AS sd  from ( SELECT sdr.`day`, sdr.class_room, sdr.area, sdr.breakfast, sdr.lunch , sdr.dinner \n" +
					" FROM stud_dining_record sdr ) aa1 where aa1.`day`>='"+startDay+"' and aa1.`day`<='"+endDay+"' GROUP BY\n" +
					" aa1.class_room)aa on a.class_room = aa.class_room GROUP BY a.dining_place");
		}else {
			sql = new StringBuffer("SELECT a.dining_place AS diningPlace, SUM((a.a2*a.cb)) AS dBreakfast, SUM((a.a2*a.cl)) AS dLunch,\n" +
					" SUM((a.a2*a.cd)) AS dDinner, SUM(aa.sb) AS rBreakfast, SUM(aa.sl) AS rLunch, SUM(aa.sd) AS rDinner FROM (SELECT \n" +
					" cd.class_room,\n" +
					" SUM(cd.breakfast=1) as cb,\n" +
					" SUM(cd.lunch=1) as cl,\n" +
					" SUM(cd.dinner=1) as cd,\n" +
					" ci.planned_number,(SELECT SUM(dining=1) FROM register where class_id =ci.id)as a2,ci.dining_place\n" +
					" FROM\n" +
					"class_dining cd\n" +
					"LEFT JOIN class_info ci ON cd.class_room = ci.id\n" +
					"GROUP BY cd.class_room ) a LEFT JOIN (\n" +
					"SELECT sdr.class_room,sdr.area,SUM(sdr.breakfast=1) as sb,\n" +
					"SUM(sdr.lunch=1) as sl,SUM(sdr.dinner=1) as sd\n" +
					" from stud_dining_record sdr GROUP BY sdr.class_room\n" +
					")aa on a.class_room = aa.class_room GROUP BY a.dining_place");
		}
		Page<StuDiningStatisticsVO> page = new Page<>();
		//這裡需要注意的一點是返回的VO對象，如果有構造函數，則無參構造函數一定要是public
		Query query = getSession().createSQLQuery(sql.toString());
		int count = query.setResultTransformer(Transformers.aliasToBean(StuDiningStatisticsVO.class)).list().size();
		query.setFirstResult((pageNumber-1)*pageSize);
		query.setMaxResults(pageSize);
		List<StuDiningStatisticsVO> list = query.setResultTransformer(Transformers.aliasToBean(StuDiningStatisticsVO.class)).list();
		page.setPageSize(pageSize);
		page.setList(list);
		page.setPageNo(pageNumber);
		page.setTotalRecords(count);
		return page;
	}

}