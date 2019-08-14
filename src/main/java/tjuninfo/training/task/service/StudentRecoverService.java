package tjuninfo.training.task.service;

import org.springframework.web.multipart.MultipartFile;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.StudentCard;
import tjuninfo.training.task.vo.StudentCardVO;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/25 14:47
 * @Description:学员流水号业务层
 */
public interface StudentRecoverService extends IBaseService<StudentCard> {

    /**查找学员流水号**/
    public List<StudentCardVO> findList();

    /**根据流水号回收学员对应信息**/
    public void recoverStuList(String number);

    /**根据班级ID回收对应的学员流水号**/
    public void recoverClassList(BigInteger classId);

    /**查找学员流水号**/
    Integer sumByClassId(Long id);

    /**分页**/
    List<StudentCardVO> findList(BTView<StudentCardVO> bt,Integer userId);

    /**根据流水号查找流水号绑定信息**/
    StudentCard findCardBynumb(String number);
    //根据班级ID和当前日期到学生就餐记录表中去查人数
    Long byTimeAndClassId(int i, String nowDate, ClassDining classDining);
}
