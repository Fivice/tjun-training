package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.MessageHistory;

import java.util.List;

public interface IMessageHistoryDao extends IBaseDao<MessageHistory> {

    /**
     * 查询发送历史
     * @param classId
     * @param phone
     * @param sendTime
     * @param user
     * @return
     */
    public List<MessageHistory> getMessageHistoryList(Long classId,String phone,String sendTime,Integer user);
}
