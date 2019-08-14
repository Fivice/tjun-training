package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.SignUpStudentDTO;
import tjuninfo.training.task.util.Page;

public interface ISignUpStudentDao extends IBaseDao<SignUpStudentDTO> {
    /**
     * 通过班级Id获取报名这个班的学生信息
     * @param pageSize 页码大小
     * @param pageIndex 页码
     * @param classId 班级Id
     * @return 返回分页信息
     */
    public Page getSinUpStudentList(int pageSize, int pageIndex, long classId);

    /**
     * 根据班级id,学员姓名,支付状态查询获取学员信息表
     * @param pageSize
     * @param pageIndex
     * @param classId
     * @param siName
     * @param pay
     * @return
     */
    public Page<SignUpStudentDTO> getSinUpStudentList(int pageSize, int pageIndex, long classId,String siName,String unitName,int pay,int dining,int hotel);
}
