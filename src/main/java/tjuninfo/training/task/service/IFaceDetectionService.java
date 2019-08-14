package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.FaceDetection;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName IFaceDetectionService
 * @date 2019/1/15 23:42
 **/
public interface IFaceDetectionService extends IBaseService<FaceDetection> {
    public FaceDetection getById(int id);
}
