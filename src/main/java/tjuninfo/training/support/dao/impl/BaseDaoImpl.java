package tjuninfo.training.support.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.util.StringUtils;

import static tjuninfo.training.support.controller.BaseController.USER_SESSION;

/**
 * 公共dao接口实现类
 * @author shenxianyan
 * @date 2018年5月16日
 * @param <E>
 */
//@Repository
public class BaseDaoImpl<E> implements IBaseDao<E>{
	@Autowired
	private SessionFactory sessionFactory;
	protected Class<E> entityClass;
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	public void getSessionclear(){
		getSession().clear();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	public BaseDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<E> findByPage(String hql, BTView<E> bt, Object[] param,Map<String, String> orderMap) {
		// TODO hql分页
		try {
			Session session = getSession();
			Query query = session.createQuery(hql);
			if(param!=null){
				for (int i = 0; i < param.length; i++) {
					query.setParameter(i, param[i]);  
				}
			}
			bt.setTotal(countByParamByHql(hql, param)); 
			 
			if (bt.getTotal() > 0L) {
				if (orderMap != null && orderMap.size() > 0) {
					for (Iterator<String> it = orderMap.keySet().iterator(); it.hasNext();) {
						String pm = (String) it.next();
						if(pm ==null || pm.equals("")){
							continue;
						}
						if ("DESC".equalsIgnoreCase((String) orderMap.get(pm))) {
							hql += " desc "+ pm ;
						} else if ("ASC".equalsIgnoreCase((String) orderMap.get(pm))) {
							hql += " asc "+ pm ;
						}
					}
				}
				query.setMaxResults(bt.getPageSize());
				query.setFirstResult((bt.getPageNumber() - 1) * bt.getPageSize());
				return query.getResultList();
			} else { 
				return new ArrayList<E>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询条数
	 * @param hql
	 * @param arrParam
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countByParamByHql(String hql,Object[] arrParam) {
		// TODO hql查询总条数保存对象
		String countHql = "select count(*) from " + hql.substring(hql.indexOf("from")+4);
		Query query = getSession().createQuery(countHql);
		if(arrParam!=null){
			for (int i = 0; i < arrParam.length; i++) {
				query.setParameter(i, arrParam[i]);
			}
		}
		return (Long) query.uniqueResult();
	}

	@Override
	public void save(E entity) {
		// TODO 保存对象
		getSession().save(entity);
	}


	@Override
	public void saveOrUpdate(E entity) {
		// TODO 保存对象
		getSession().saveOrUpdate(entity);
	}

	@Override
	public boolean deleteByPK(Serializable... id) {
		// TODO 根据id删除
		boolean result = false;
		if ((id != null) && (id.length > 0)) {
			for (int i = 0; i < id.length; i++) {
				E entity = get(id[i]);
				if (entity != null) {
					getSession().delete(entity);
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public void delete(E entity) {
		// TODO 根据对象删除
		getSession().delete(entity);
	}

	@Override
	public void update(E entity) {
		// TODO 更新对象
		getSession().update(entity);
	}
	
	public E get(Serializable id) {
		// TODO 根据id查询对象
		return (E) getSession().get(entityClass, id);
	}
	
	public E load(Serializable id) {
		// TODO 根据id加载对象
		return (E)getSession().load(this.entityClass, id);
	}
	
	
	@SuppressWarnings("rawtypes")
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) {
		// TODO 根据属性更新属性
		if ((propertyName != null) && (propertyName.length > 0) && (propertyValue != null) && (propertyValue.length > 0) && (propertyName.length == propertyValue.length) && (conditionValue != null)
				&& (conditionValue.length > 0)) {
			StringBuffer sb = new StringBuffer();
			sb.append("update " + this.entityClass.getName() + " o set ");
			for (int i = 0; i < propertyName.length; i++) {
				sb.append(propertyName[i] + " = :p_" + propertyName[i] + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" where 1=1 ");
			appendQL(sb, conditionName, conditionValue);
			Query query = getSession().createQuery(sb.toString());
			for (int i = 0; i < propertyName.length; i++) {
				query.setParameter("p_" + propertyName[i], propertyValue[i]);
			}
			setParameter(query, conditionName, conditionValue);
			query.executeUpdate();
		} else {
			throw new IllegalArgumentException("Method updateByProperties in BaseDao argument is illegal!");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		// TODO 根据属性查询一条对象信息
		if ((propName != null) && (propName.length > 0) && (propValue != null) && (propValue.length > 0) && (propValue.length == propName.length)) {
			StringBuffer sb = new StringBuffer("select o from " + this.entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
				sb.append(" order by ");
				for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
					if(StringUtils.isNotBlank((String) e.getKey()))
						sb.append((String) e.getKey() + " " + (String) e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			List<E> list = query.getResultList();
			if ((list != null) && (((List) list).size() > 0)) {
				return list.get(0);
			}
		}
		return null;
	}
	
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long getCountByProerties(String[] propName, Object[] propValue) {
		// TODO 根据属性查询条数
		if ((propName != null) && (propName.length > 0) && (propValue != null) && (propValue.length > 0) && (propValue.length == propName.length)) {
			StringBuffer sb = new StringBuffer("select count(*) from " + this.entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			return (Long) query.uniqueResult();
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		// TODO 根据属性查询对象集合
		if ((propName != null) && (propValue != null) && (propValue.length == propName.length)) {
			StringBuffer sb = new StringBuffer("select o from " + this.entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if ((sortedCondition != null) && (sortedCondition.size() > 0)) {
				sb.append(" order by ");
				for (Map.Entry<String, String> e : sortedCondition.entrySet()) {
					if(StringUtils.isNotBlank((String) e.getKey()))
						sb.append((String) e.getKey() + " " + (String) e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			if (top != null) {
				query.setFirstResult(0);
				query.setMaxResults(top.intValue());
			}
			return query.getResultList();
		}
		return null;
	}
	
	
	@SuppressWarnings("deprecation")
	public Long countAll() {
		// TODO 查询总条数
		return (Long) getSession().createQuery("select count(*) from " + this.entityClass.getName()).uniqueResult();
	}
	
	
	private void appendQL(StringBuffer sb, String[] propName, Object[] propValue) {
		// TODO 拼接查询字符
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (((value instanceof Object[])) || ((value instanceof Collection))) {
				Object[] arraySerializable = (Object[]) value;
				if ((arraySerializable != null) && (arraySerializable.length > 0)) {
					sb.append(" and o." + name + " in (:" + name.replace(".", "") + ")");
				}
			} else if (value == null) {
				sb.append(" and o." + name + " is null ");
			} else {
				sb.append(" and o." + name + "=:" + name.replace(".", ""));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void setParameter(Query query, String[] propName, Object[] propValue) {
		// TODO 设置查询参数
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (value != null) {
				if ((value instanceof Object[])) {
					query.setParameterList(name.replace(".", ""), (Object[]) value);
				} else if ((value instanceof Collection)) {
					query.setParameterList(name.replace(".", ""), (Collection) value);
				} else {
					query.setParameter(name.replace(".", ""), value);
				}
			}
		}
	}

	@Override
	public List<E> getAll() {
		// TODO Auto-generated method stub
		return getSession().createCriteria(entityClass).list();
	}


	
}
