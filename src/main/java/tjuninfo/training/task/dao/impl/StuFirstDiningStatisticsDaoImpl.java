package tjuninfo.training.task.dao.impl;



import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IStuFirstDiningStatisticsDao;
import tjuninfo.training.task.dto.SignUpStudentDTO;
import tjuninfo.training.task.entity.SysRole;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;

import java.util.List;


/**
 * 单位表数据层接口实现类
 * @author 
 * @date 2018年5月23日
 */
@Repository
public class StuFirstDiningStatisticsDaoImpl extends BaseDaoImpl<StuFirstDiningStatisticsVO> implements IStuFirstDiningStatisticsDao {

	public StuFirstDiningStatisticsDaoImpl() {
		super(StuFirstDiningStatisticsVO.class);
	}

	@Override
	public Page count(int pageSize, int pageIndex, String startDay, String endDay) {
		StringBuffer sql = null ;
		if (startDay!=null && !startDay.equals("")) {
			sql =new StringBuffer( "SELECT bb.dining_place as diningPlace,SUM(bb.ab) as dBreakfast,SUM(bb.al) as dLunch,SUM(bb.ad) as dDinner," +
					"SUM(bb.sb) as rBreakfast ,SUM(bb.sl) as rLunch,SUM(bb.sd) as rDinner from (\n" +
					"SELECT * FROM (\n" +
					"SELECT aa.class_room as classRoom, aa.dining_place,aa.day as aday,SUM(aa.breakfast=1)*aa.a2 as ab, \n" +
					"SUM(aa.lunch=1)*aa.a2 as al,SUM(aa.dinner=1)*aa.a2 as ad\n" +
					"from (SELECT a1.* FROM (\n" +
					"SELECT a.*from (\n" +
					"SELECT ci.dining_place,ci.planned_number,(SELECT SUM(dining=1) FROM register where class_id =ci.id)as a2,cd.* from class_dining cd \n" +
					"left JOIN class_info ci ON cd.class_room = ci.id ORDER BY cd.day )a GROUP BY a.class_room )a1 where a1.day >='"+startDay+"' and a1.day <= '"+endDay+"')aa GROUP BY \n" +
					"aa.class_room)aaa \n" +
					"LEFT JOIN (SELECT c1.* FROM( SELECT cc.day,cc.class_room, SUM(cc.breakfast=1) as sb,SUM(cc.lunch=1) as sl,SUM(cc.dinner=1) as sd from (SELECT * from \n" +
					"(SELECT * from stud_dining_record sdr ORDER BY sdr.day)c GROUP BY c.student ORDER BY c.day) cc GROUP BY cc.day)c1 where c1.day >='"+startDay+"' and c1.day <= '"+endDay+"')b \n" +
					" on aaa.classRoom = b.class_room and aaa.aday = b.day)bb GROUP BY bb.dining_place " );
		}else {
			sql = new StringBuffer("SELECT bb.dining_place as diningPlace,SUM(bb.ab) as dBreakfast,SUM(bb.al) as dLunch,SUM(bb.ad) as dDinner," +
					"SUM(bb.sb) as rBreakfast,SUM(bb.sl) as rLunch,SUM(bb.sd) as rDinner from (\n" +
					"SELECT * FROM (\n" +
					"SELECT aa.class_room as classRoom, aa.dining_place,aa.day as aday,SUM(aa.breakfast=1)*aa.a2 as ab, \n" +
					"SUM(aa.lunch=1)*aa.a2 as al,SUM(aa.dinner=1)*aa.a2 as ad\n" +
					"from (\n" +
					"SELECT a.*from (\n" +
					"SELECT ci.dining_place,ci.planned_number,(SELECT SUM(dining=1) FROM register where class_id =ci.id)as a2,cd.* from class_dining cd \n" +
					"left JOIN class_info ci ON cd.class_room = ci.id ORDER BY cd.day )a GROUP BY a.class_room )aa GROUP BY aa.class_room)aaa \n" +
					"LEFT JOIN ( SELECT cc.day,cc.class_room, SUM(cc.breakfast=1) as sb,SUM(cc.lunch=1) as sl,SUM(cc.dinner=1) as sd from (SELECT * from \n" +
					"(SELECT * from stud_dining_record sdr ORDER BY sdr.day)c GROUP BY c.student ) cc GROUP BY cc.day)b \n" +
					" on aaa.classRoom = b.class_room and aaa.aday = b.day)bb GROUP BY bb.dining_place " );
		}

        Page<StuFirstDiningStatisticsVO> page = new Page<>();
        //這裡需要注意的一點是返回的VO對象，如果有構造函數，則無參構造函數一定要是public
        Query query = getSession().createSQLQuery(sql.toString());
		int count = query.setResultTransformer(Transformers.aliasToBean(StuFirstDiningStatisticsVO.class)).list().size();
        query.setFirstResult((pageIndex-1)*pageSize);
        query.setMaxResults(pageSize);
        List<StuFirstDiningStatisticsVO> list = query.setResultTransformer(Transformers.aliasToBean(StuFirstDiningStatisticsVO.class)).list();


        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageIndex);
        page.setTotalRecords(count);
        return page;
	}
}