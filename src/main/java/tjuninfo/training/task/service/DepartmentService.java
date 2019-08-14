package tjuninfo.training.task.service;

import java.util.List;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Department;

/**
 * 部门表业务层接口
 * @author 
 * @date 2018年5月17日
 */
public interface DepartmentService extends IBaseService<Department>{
	/**
	 * 获取部门目录列表
	 * @return list
	 */
	List<Department> list();
	
	/**
	 * 查询所有可用部门
	 * @return
	 */
	List<Department> findList();
	
	/**
	 * 根据id获取区域
	 * @param areaId
	 * @return
	 */
	Department getByAreaId(Integer areaId);
	
	/**
	 * 根据sjareaId获取区域名称
	 * @param sjareaId
	 * @return
	 */
	List<Department> getBySjreaId(Integer sjareaId);
	
	/**
	 * 添加更新信息
	 * @param department
	 * @param areaId
	 * @return
	 */
	void updateDepartment(Department department, Integer areaId);


}
