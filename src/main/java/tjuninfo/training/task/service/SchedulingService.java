package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Scheduling;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 日程安排业务层接口
 * @author
 * @date 2018年5月17日
 */
public interface SchedulingService extends IBaseService<Scheduling>{
    /**
     * 获取单位目录列表
     * @return list
     */
    List<Scheduling> list();
    //查询所有列表
    public List<Scheduling> findSchedulingList(String day, String className,String id);

    //根据id删除数据
    public  void deleteById(Long id);

    //添加数据
    public  void saveScheduling(Scheduling scheduling);

    //根据id查询数据
    public  Scheduling selectById(Long id);

    //分页处理
    public Pages findSchList(int pageSize, int pageIndex, String day, String className, String id);

    //查询是否参评列表
    public List<Scheduling> findEvaSchList(String id,int evaluate);


    /*
    * @Description TODO 根据班级id查找其所包含的课程
    * @param id
    * @Return java.util.List<java.lang.String>
    * @Authort hanyt
    * @Date 2019/6/5 上午 10:47
    **/
    List<String> findList(String id);
}
