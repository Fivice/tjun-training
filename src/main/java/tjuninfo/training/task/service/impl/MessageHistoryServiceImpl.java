package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IMessageHistoryDao;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.MessageHistory;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.service.IMessageHistoryService;
import tjuninfo.training.task.service.ISysUserService;
import tjuninfo.training.task.vo.MessageFromClassInfoVO;
import tjuninfo.training.task.vo.MessageHistoryVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageHistoryServiceImpl extends BaseServiceImpl<MessageHistory> implements IMessageHistoryService {
    private final IMessageHistoryDao iMessageHistoryDao;
    private final ISysUserService iSysUserService;
    @Autowired
    public MessageHistoryServiceImpl(IMessageHistoryDao iMessageHistoryDao, ISysUserService iSysUserService) {
        this.iMessageHistoryDao = iMessageHistoryDao;
        this.iSysUserService = iSysUserService;
        this.dao = iMessageHistoryDao;
    }

    @Override
    public MessageFromClassInfoVO formatterMessage(ClassInfo classInfo) {
        return new MessageFromClassInfoVO(
                classInfo.getClassNumber(),
                classInfo.getClassName(),
                classInfo.getHostUnit(),
                classInfo.getStartStopTime(),
                classInfo.getTeacherName(),
                classInfo.getPhoneNumber(),
                classInfo.getRegPlace(),
                classInfo.getEntryStartTime(),
                classInfo.getHotelPlace(),
                classInfo.getDiningPlace(),
                classInfo.getTrainingExpense(),
                classInfo.getOtherCharges()
        );
    }

    @Override
    public List<MessageHistoryVO> getMessageHistoryList(Long classId, String phone, String sendTime, Integer user) {
        MessageHistoryVO messageHistoryVO = new MessageHistoryVO();
        List<MessageHistoryVO> messageHistoryVOList = new ArrayList<>();
        List<MessageHistory> messageHistoryList = iMessageHistoryDao.getMessageHistoryList(classId,phone,sendTime,user);
        for (MessageHistory messageHistory:messageHistoryList
             ) {
            messageHistoryVO.setMessageHistory(messageHistory);
            SysUser sysUser = iSysUserService.get(messageHistory.getUser());
            messageHistoryVO.setSysUser(sysUser);
            messageHistoryVOList.add(messageHistoryVO);
        }
        return messageHistoryVOList;
    }
}
