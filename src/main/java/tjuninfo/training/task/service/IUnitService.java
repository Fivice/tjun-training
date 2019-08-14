package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.entity.Unit;

import java.util.List;


/**
 * 单位表业务层接口
 * @author 
 * @date 2018年5月17日
 */
public interface IUnitService extends IBaseService<Unit>{
	/**
	 * 获取单位目录列表
	 * @return list
	 */
	List<Unit> list();
	
	/**
	 * 查询所有单位
	 * @return
	 */
	List<Unit> findList();

    /**
     * 根据sjareaId获取单位
     * @param sjareaId
     * @return
     */
    List<Unit> getBySjreaId(Integer sjareaId);

	//查找所有状态不为删除单位集合
	public List<Unit> findAll();
	//根据id查询所有父级单位集合
	public List<Unit> findByareaId(Integer areaId);
	

}
