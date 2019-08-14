package tjuninfo.training.task.dao.impl;

import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IFaceDetectionDao;
import tjuninfo.training.task.entity.FaceDetection;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName FaceDetectionDaoImpl
 * @date 2019/1/15 23:39
 **/
@Repository
public class FaceDetectionDaoImpl extends BaseDaoImpl<FaceDetection> implements IFaceDetectionDao {
    public FaceDetectionDaoImpl() {
        super(FaceDetection.class);
    }
}
