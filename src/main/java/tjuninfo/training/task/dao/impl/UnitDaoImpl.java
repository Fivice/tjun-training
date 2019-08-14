package tjuninfo.training.task.dao.impl;



import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IUnitDao;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.entity.Unit;

import java.util.List;


/**
 * 单位表数据层接口实现类
 * @author 
 * @date 2018年5月23日
 */
@Repository
public class UnitDaoImpl extends BaseDaoImpl<Unit> implements IUnitDao {

	public UnitDaoImpl() {
		super(Unit.class);
	}
	@Override
	public List<Unit> findByType(Integer areaType) {
		List<Unit> list= super.getSession().createCriteria(Unit.class, "u").add(Restrictions.eq("u.areaType",areaType)).add(Restrictions.eq("u.status",1)).addOrder(Order.asc("u.sort")).list();
		return list;
	}

    @Override
    public List<Unit> findBySjareaId(Integer sjareaId) {
        List<Unit> lists =super.getSession().createCriteria(Unit.class, "u").add(Restrictions.eq("u.sjareaId",sjareaId)).add(Restrictions.eq("u.status",1)).list();
        return lists;
    }

	@Override
	public List<Unit> findAll() {
		StringBuffer hql =new StringBuffer("from Unit c where 1=1") ;
		hql.append(" and c.status = 1");
		Query query = getSession().createQuery(hql.toString());
		return query.list();
	}

	@Override
	public List<Unit> findByareaId(Integer areaId) {
		StringBuffer sql =new StringBuffer( "select * from unit_information where FIND_IN_SET(area_id,getUnLst("+areaId+"))");
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		query.addEntity(Unit.class);
		List<Unit> list = query.list();
		return list;
	}

}