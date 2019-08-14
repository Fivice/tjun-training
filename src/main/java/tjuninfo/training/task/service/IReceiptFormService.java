package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.ReceiptForm;

import java.util.List;

/**
 * @description 回执表单服务层
 * @author Fivice
 * @date 2018年11月9日11:54:34
 */
public interface IReceiptFormService extends IBaseService<ReceiptForm> {
    /**
     * 保存回执表
     * @param entity
     * @return
     */
    public long saveReceiptForm(ReceiptForm entity);

    /**
     * 获取回执表信息
     * @param siUnitId
     * @param classId
     * @return
     */
    public List getStudentInfoListByReceiptForm(String siUnitId, String classId);

    public long getReceiptFormCount(long classId,int studentId);
}
