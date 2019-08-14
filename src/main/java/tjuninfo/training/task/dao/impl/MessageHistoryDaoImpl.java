package tjuninfo.training.task.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IMessageHistoryDao;
import tjuninfo.training.task.entity.MessageHistory;

import java.util.List;

@Repository
public class MessageHistoryDaoImpl extends BaseDaoImpl<MessageHistory> implements IMessageHistoryDao {
    public MessageHistoryDaoImpl() {
        super(MessageHistory.class);
    }

    @Override
    public List<MessageHistory> getMessageHistoryList(Long classId, String phone, String sendTime, Integer user) {
        StringBuffer hql = new StringBuffer("from MessageHistory mh where 1=1 ");
        if (classId!=null){
            hql.append(" and mh.classId = ").append(classId);
        }
        if (StringUtils.isNotBlank(phone)){
            hql.append(" and mh.phone like '%").append(phone).append("%'");
        }
        if (StringUtils.isNotBlank(sendTime)){
            hql.append(" and mh.sendTime like '").append(sendTime).append("%'");
        }
        if (user!=null){
            hql.append(" and mh.user = ").append(user);
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }
}
