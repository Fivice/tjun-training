package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.MessageHistory;
import tjuninfo.training.task.vo.MessageFromClassInfoVO;
import tjuninfo.training.task.vo.MessageHistoryVO;

import java.util.List;

public interface IMessageHistoryService extends IBaseService<MessageHistory> {

    MessageFromClassInfoVO formatterMessage(ClassInfo classInfo);

    public List<MessageHistoryVO> getMessageHistoryList(Long classId, String phone, String sendTime, Integer user);
}
