package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IReceiptFormDao;
import tjuninfo.training.task.entity.ReceiptForm;
import tjuninfo.training.task.service.IReceiptFormService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @description 回执表单服务层
 * @author Fivice
 * @date 2018年11月9日11:56:33
 */
@Service
public class ReceiptFormServiceImpl extends BaseServiceImpl<ReceiptForm> implements IReceiptFormService {

    private IReceiptFormDao iReceiptFormDao;

    @Autowired
    public ReceiptFormServiceImpl(IReceiptFormDao iReceiptFormDao) {
        this.iReceiptFormDao = iReceiptFormDao;
        this.dao = iReceiptFormDao;
    }

    @Override
    public long saveReceiptForm(ReceiptForm receiptForm) {
        return iReceiptFormDao.saveReceiptForm(receiptForm);
    }

    @Override
    public List getStudentInfoListByReceiptForm(String siUnitId, String classId) {
        return iReceiptFormDao.getStudentInfoListByReceiptForm(siUnitId,classId);
    }

    @Override
    public long getReceiptFormCount(long classId, int studentId) {
        String[] propName = {"siId","classId"};
        Object[] propValue = {studentId,classId};
        long count = iReceiptFormDao.getCountByProerties(propName,propValue);
        return count;
    }
}
