package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.DepartmentDao;
import tjuninfo.training.task.dao.IUnitDao;
import tjuninfo.training.task.dao.impl.UnitDaoImpl;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.entity.Unit;
import tjuninfo.training.task.enums.AreaTypeEnum;
import tjuninfo.training.task.service.DepartmentService;
import tjuninfo.training.task.service.IUnitService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 单位表业务层接口实现类
 * @author 
 */
@Service
public class UnitServiceImpl extends BaseServiceImpl<Unit> implements IUnitService {
	private IUnitDao unitDao;
	@Resource
	public void setSysAuthorityDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
		this.dao = unitDao;
	}

	@Override
	public List<Unit> list() {
		List<Unit> units = new ArrayList<>();
		//  查询一级目录
		List<Unit> firstUnits = unitDao.findByType(AreaTypeEnum.FIRST_AREA.getType());
		// 查询二级目录
		List<Unit> secondUnits = unitDao.findByType(AreaTypeEnum.SECOND_AREA.getType());
		// 查询三级目录
		List<Unit> thirdUnits = unitDao.findByType(AreaTypeEnum.THIRD_AREA.getType());
		//查询四级目录
		List<Unit> fourthUnits = unitDao.findByType(AreaTypeEnum.FOURTH_AREA.getType());
		
		for (Unit firstUnit : firstUnits) {// 遍历一级目录
            units.add(firstUnit);
			for (Unit secondUnit : secondUnits) {// 遍历二级目录
				if (firstUnit.getAreaId().equals(secondUnit.getSjareaId())) {
                    units.add(secondUnit);
					for (Unit thirdUnit : thirdUnits) {// 遍历三级目录
						if (secondUnit.getAreaId().equals(thirdUnit.getSjareaId())) {
                            units.add(thirdUnit);
							for(Unit fourthUnit : fourthUnits) {// 遍历四级目录
								if(thirdUnit.getAreaId().equals(fourthUnit.getSjareaId())) {
                                    units.add(fourthUnit);
								}
							}
						}
					}
				}
			}
		}
		return units;
	}
	@Override
	public List<Unit> findList() {
		return unitDao.getAll();
	}

    @Override
    public List<Unit> getBySjreaId(Integer sjareaId) {
        return unitDao.findBySjareaId(sjareaId);
    }

	@Override
	public List<Unit> findAll() {
		return unitDao.findAll();
	}

	@Override
	public List<Unit> findByareaId(Integer areaId) {
		return unitDao.findByareaId(areaId);
	}


}
