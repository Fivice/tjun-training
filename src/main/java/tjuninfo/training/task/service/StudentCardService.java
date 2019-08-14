package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.StudentCard;

import java.util.List;


/**
 * 学生信息表业务层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface StudentCardService extends IBaseService<StudentCard>{
    /**
     * 获取学生证目录列表
     * @return list
     */
    List<StudentCard> list();

    //根据流水号查询集合
    List<StudentCard> findListBy(String number);

    public List<StudentCard> findByStudentIdAndRegisterTime(String siId, String registerTime);

}


