package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.entity.Unit;

import java.util.List;

/**
 * 单位表数据层接口
 * @author 
 */
public interface IUnitDao extends IBaseDao<Unit> {
    /**
     * 根据单位类型且状态不为删除的查找单位
     * @param areaType
     * @return
     */
    public List<Unit> findByType(Integer areaType);

    /**
     * 根据上级单位编码查找单位
     * @param sjareaId
     * @return
     */
    public List<Unit> findBySjareaId(Integer sjareaId);
    //查找所有状态不为删除单位集合
    public List<Unit> findAll();
    //根据id查询所有父级单位集合
    public List<Unit> findByareaId(Integer areaId);
	
}

	
	
