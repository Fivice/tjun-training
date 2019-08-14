package tjuninfo.training.task.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.DepartmentDao;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.enums.AreaTypeEnum;
import tjuninfo.training.task.service.DepartmentService;

/**
 * 部门表业务层接口实现类
 * @author 
 * @date 2018年5月18日
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {
	private DepartmentDao departmentDao;
	@Resource
	public void setSysAuthorityDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
		this.dao = departmentDao;
	}

	@Override
	public List<Department> list() {
		List<Department> departments = new ArrayList<>();
		//  查询一级目录
		List<Department> parentDepartments = departmentDao.findByType(AreaTypeEnum.FIRST_AREA.getType());
		// 查询二级目录
		List<Department> childDepartments = departmentDao.findByType(AreaTypeEnum.SECOND_AREA.getType());
		// 查询三级目录
		List<Department> thirdDepartments = departmentDao.findByType(AreaTypeEnum.THIRD_AREA.getType());
		//查询四级目录
		List<Department> fourthDepartments = departmentDao.findByType(AreaTypeEnum.FOURTH_AREA.getType());
		
		for (Department parentDepartment : parentDepartments) {// 遍历一级目录
			departments.add(parentDepartment);
			for (Department childDepartment : childDepartments) {// 遍历二级目录
				if (parentDepartment.getAreaId() == childDepartment.getSjareaId()) {
					departments.add(childDepartment);
					for (Department thirdDepartment : thirdDepartments) {// 遍历三级目录
						if (childDepartment.getAreaId() == thirdDepartment.getSjareaId()) {
							departments.add(thirdDepartment);
							for(Department fourthDepartment : fourthDepartments) {// 遍历四级目录
								if(thirdDepartment.getAreaId() ==  fourthDepartment.getSjareaId()) {
									departments.add(fourthDepartment);
								}
							}
						}
					}
				}
			}
		}
		return departments;
	}


	@Override
	public Department getByAreaId(Integer areaId) {
		return departmentDao.get(areaId);
	}

	@Override
	public void updateDepartment(Department updateDepartment, Integer areaId) {
		Department department = departmentDao.get(areaId);
		department.setAreaName(updateDepartment.getAreaName());
		department.setAreaType(updateDepartment.getAreaType());
		department.setSjareaId(updateDepartment.getSjareaId());
		department.setSort(updateDepartment.getSort());
		departmentDao.update(department);
		
	}

	@Override
	public List<Department> getBySjreaId(Integer sjareaId) {
		return departmentDao.findBySjareaId(sjareaId);
	}
	@Override
	public List<Department> findList() {
		return departmentDao.findList();
	}

	
}
