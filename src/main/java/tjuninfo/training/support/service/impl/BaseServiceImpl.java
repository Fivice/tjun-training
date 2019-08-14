package tjuninfo.training.support.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.support.service.IBaseService;

/**
 * 父类service接口实现类
 * @author shenxianyan
 * @date 2018年5月16日
 * @param <E>
 */
@Transactional
public class BaseServiceImpl<E> implements IBaseService<E> {
	
	protected IBaseDao<E> dao;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> findByPage(String hql,BTView<E> bt,Object[] param,Map<String, String> orderMap){
		return dao.findByPage(hql, bt, param, orderMap);
	}

	@Override
	public void save(E entity) {
		// TODO 保存
		dao.save(entity);
	}

	@Override
	public boolean deleteByPK(Serializable... id) {
		// TODO 根据id删除
		return dao.deleteByPK(id);
	}

	@Override
	public void delete(E entity) {
		// TODO 根据对象删除
		dao.delete(entity);
	}

	@Override
	public void update(E entity) {
		// TODO 更新
		dao.update(entity);
	}

	@Override
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName,
			Object[] propertyValue) {
		// TODO 根据属性更新对象
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E get(Serializable id) {
		// TODO 根据id查询对象
		return dao.get(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E load(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		// TODO 根据属性查询对象
		return dao.getByProerties(propName, propValue, sortedCondition);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long getCountByProerties(String[] propName, Object[] propValue) {
		// TODO 根据属性查询条数
		return dao.getCountByProerties(propName, propValue);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition,
			Integer top) {
		// TODO 根据属性查询对象集合
		return dao.queryByProerties(propName, propValue, sortedCondition, top);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Long countAll() {
		// TODO 查询总条数
		return dao.countAll();
	}
}