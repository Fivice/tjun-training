package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IFaceDetectionDao;
import tjuninfo.training.task.entity.FaceDetection;
import tjuninfo.training.task.service.IFaceDetectionService;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName FaceDetectionServiceImpl
 * @date 2019/1/15 23:43
 **/
@Service
public class FaceDetectionServiceImpl extends BaseServiceImpl<FaceDetection> implements IFaceDetectionService {

    private final IFaceDetectionDao iFaceDetectionDao;

    @Autowired
    public FaceDetectionServiceImpl(IFaceDetectionDao iFaceDetectionDao) {
        this.iFaceDetectionDao = iFaceDetectionDao;
        this.dao = iFaceDetectionDao;
    }

    @Override
    public FaceDetection getById(int id) {
        return get(id);
    }
}
