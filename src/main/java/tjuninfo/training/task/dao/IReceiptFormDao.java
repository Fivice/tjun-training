package tjuninfo.training.task.dao;


import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.ReceiptForm;

import java.util.List;


/**
 * @description 回执单表
 * @author Fivice
 * @date 2018年11月9日11:40:25
 */
public interface IReceiptFormDao extends IBaseDao<ReceiptForm> {

    public long saveReceiptForm(ReceiptForm entity);

    /**
     * 获取符合条件的回执表记录数
     * @param siId
     * @param classId
     * @return
     */
    public long judgeStudentIsInReceiptForm(String siId,String classId);

    public List getStudentInfoListByReceiptForm(String siUnitId,String classId);
}
