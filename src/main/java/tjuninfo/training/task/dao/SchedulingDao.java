package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Scheduling;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 日程安排表数据层接口
 * @author
 * @date 2018年9月19日
 */
public interface SchedulingDao extends IBaseDao<Scheduling> {
	/**
	 * 分页查询日程安排信息
	 * @return
	 */
	List<Scheduling> findAll(String day, String className,String id);

    Pages findSchList(int pageSize, int pageIndex, String day, String className, String id);

	List<Scheduling> findEvaSchList(String id,int evaluate);

	/*
	* @Description TODO 根据班级id查找其所包含的课程
	* @param id
	* @Return java.util.List<java.lang.String>
	* @Authort hanyt
	* @Date 2019/6/5 上午 10:49
	**/
    List<String> findList(String id);
}

	
	
