package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.ScoreReport;

import java.util.List;

/**
 * 成绩证书业务层接口
 * @author
 * @date 2018年5月17日
 */
public interface ScoreReportService extends IBaseService<ScoreReport>{
    //条件查询查询所有列表
    public List<ScoreReport> findScoreReportList(String month,String teacherName);

    //条件查询查询所有列表
    public List<ScoreReport> findScoreReportList2(Long classId);

    //根据id删除数据
    public  void deleteById(Integer id);

    //添加数据
    public  void saveTeacherInfo(ScoreReport scoreReport);

    //根据id查询数据
    public  ScoreReport selectById(Integer id);

    List<ScoreReport> list();

    void deleteByClassId(Long classId);

    ScoreReport selectScoreReport(String id,String classId);


}
