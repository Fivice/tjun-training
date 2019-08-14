package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.StudentCard;

import java.util.List;


/**
 * 学生信息表数据层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface StudentCardDao extends IBaseDao<StudentCard> {
    List<StudentCard> list();
    //根据流水号查询集合
    List<StudentCard> findListBy(String number);
    public List<StudentCard> findByStudentIdAndRegisterTime(String siId,String registerTime);
}

	
	
