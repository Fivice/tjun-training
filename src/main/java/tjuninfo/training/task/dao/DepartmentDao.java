package tjuninfo.training.task.dao;

import java.util.List;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Department;
/**
 * 部门表数据层接口
 * @author 
 * @date 2018年5月16日
 */
public interface DepartmentDao extends IBaseDao<Department> {
	
	/**
	 * 根据权限类型查找菜单
	 * @param areaType
	 * @return
	 */
	public List<Department> findByType(Integer areaType);
	
	/**
	 * 根据上级类型查找学校
	 * @param sjareaId
	 * @return
	 */
	public List<Department> findBySjareaId(Integer sjareaId);

	/**
	 * 查询所有可用的部门
	 * @return
	 */
	public List<Department> findList();
}

	
	
