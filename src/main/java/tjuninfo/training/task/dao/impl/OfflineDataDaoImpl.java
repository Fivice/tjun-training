package tjuninfo.training.task.dao.impl;


import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IOfflineDataDao;
import tjuninfo.training.task.entity.OfflineData;

import java.util.List;

/**
 * 用户数据层接口实现类
 * @author shenxianyan
 * @date 2018年5月29日
 */
@Repository
public class OfflineDataDaoImpl extends BaseDaoImpl<OfflineData> implements IOfflineDataDao {

	public OfflineDataDaoImpl() {
		super(OfflineData.class);
	}

	@Override
	public List<OfflineData> getinfo(BTView<OfflineData> bt,String area,String month,String time,String status) {
		StringBuffer sql = new StringBuffer("select * from offline_data o where 1=1 ");
		StringBuffer sqlTotal = new StringBuffer("SELECT count(*) FROM offline_data o where 1=1 and number !=''");
		if(area!=null&&!area.equals("")){
			sql.append(" and o.area like '%"+area+"%' ");
			sqlTotal.append(" and o.area like '%"+area+"%' ");
		}
		if(month!=null&&!month.equals("")){
			sql.append(" and o.`day` like '"+month+"%' ");
			sqlTotal.append(" and o.`day` like '"+month+"%' ");
		}
		if(time!=null&&!time.equals("")){
			Integer t = Integer.valueOf(time);
			sql.append(" and o.time = "+t+" ");
			sqlTotal.append(" and o.time = "+t+" ");
		}
		if(status!=null&&!status.equals("")){
			Integer t = Integer.valueOf(status);
			sql.append(" and o.status = "+status+" ");
			sqlTotal.append(" and o.status = "+status+" ");
		}
		sql.append("and o.number !=''");//流水号不为空
		sql.append(" ORDER BY o.`day` desc ");
		sqlTotal.append(" ORDER BY o.`day` desc ");
		bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
		List<OfflineData> list =  this.getSession().createSQLQuery(sql.toString()).addEntity(OfflineData.class)
				.setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
		return list;
	}

	@Override
	public List<OfflineData> getFaceInfo(BTView<OfflineData> bt,String area,String month,String time,String status) {
		StringBuffer sql = new StringBuffer("select * from offline_data o where 1=1 ");
		StringBuffer sqlTotal = new StringBuffer("SELECT count(*) FROM offline_data o where 1=1 and number is null or number =''");
		if(area!=null&&!area.equals("")){
			sql.append(" and o.area like '%"+area+"%' ");
			sqlTotal.append(" and o.area like '%"+area+"%' ");
		}
		if(month!=null&&!month.equals("")){
			sql.append(" and o.`day` like '"+month+"%' ");
			sqlTotal.append(" and o.`day` like '"+month+"%' ");
		}
		if(time!=null&&!time.equals("")){
			Integer t = Integer.valueOf(time);
			sql.append(" and o.time = "+t+" ");
			sqlTotal.append(" and o.time = "+t+" ");
		}
		if(status!=null&&!status.equals("")){
			Integer t = Integer.valueOf(status);
			sql.append(" and o.status = "+status+" ");
			sqlTotal.append(" and o.status = "+status+" ");
		}
		sql.append("and (o.number ='' or o.number is null)");//流水号为空或null
		sql.append(" ORDER BY o.`day` desc ");
		sqlTotal.append(" ORDER BY o.`day` desc ");
		bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
		List<OfflineData> list =  this.getSession().createSQLQuery(sql.toString()).addEntity(OfflineData.class)
				.setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
		return list;
	}
}
