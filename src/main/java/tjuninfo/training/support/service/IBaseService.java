package tjuninfo.training.support.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import tjuninfo.training.support.BTView;

/**
 * 父类service接口
 * @author shenxianyan
 * @date 2018年5月16日
 * @param <E>
 */
public interface IBaseService<E> {
	/**
	 * 分页查询
	 * @param hql
	 * @param bt
	 * @param param
	 * @return
	 */
	public List<E> findByPage(String hql,BTView<E> bt,Object[] param,Map<String, String> orderMap);
	
	/**
	 * 保存对象
	 * @param entity
	 */
	public void save(E entity);
	
	/**
	 * 根据主键删除对象
	 * @param id	主键编号
	 * @return
	 */
	public boolean deleteByPK(Serializable... id);
	
	/**
	 * 根据对象删除对象
	 * @param entity
	 */
	public void delete(E entity);
	
	/**
	 * 更新对象
	 * @param entity
	 */
	public void update(E entity);
	
	/**
	 * 根据属性更新属性
	 * @param conditionName		WHERE子句条件的属性数组名称
	 * @param conditionValue	WHERE子句条件的属性数组值
	 * @param propertyName		UPDATE子句属性数组名称
	 * @param propertyValue		UPDATE子句属性数组值
	 */
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue);
	
	/**
	 * 根据主键编号查询对象
	 * @param id
	 * @return
	 */
	public E get(Serializable id);
	
	/**
	 * 根据id加载对象
	 * @param id
	 * @return
	 */
	public E load(Serializable id);
	
	/**
	 * 根据属性查询一条对象
	 * @param propName			属性名称数组
	 * @param propValue			属性值数组
	 * @param sortedCondition	排序方式
	 * @return
	 */
	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);
	
	/**
	 * 根据属性查询count
	 * @param propName	属性名称数组
	 * @param propValue	属性值数组
	 * @return
	 */
	public Long getCountByProerties(String[] propName, Object[] propValue);
	
	/**
	 * 根据属性、排序条件和要返回的记录数目获取对象实体列表
	 * @param propName			属性数组名称
	 * @param propValue			属性数组值
	 * @param sortedCondition	排序条件
	 * @param top				要返回的记录数目
	 * @return
	 */
	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top);
	
	/**
	 * 查询总条数
	 * @return
	 */
	public Long countAll();
}