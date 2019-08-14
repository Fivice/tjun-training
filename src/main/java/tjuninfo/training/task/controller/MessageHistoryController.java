package tjuninfo.training.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.entity.MessageHistory;
import tjuninfo.training.task.service.IMessageHistoryService;
import tjuninfo.training.task.vo.MessageHistoryVO;

import java.util.List;

@Controller
@RequestMapping(value = "/messageHistory")
public class MessageHistoryController extends BaseController {

    private final IMessageHistoryService iMessageHistoryService;

    @Autowired
    public MessageHistoryController(IMessageHistoryService iMessageHistoryService) {
        this.iMessageHistoryService = iMessageHistoryService;
    }

    @GetMapping(value = "classMsgHistory")
    @ResponseBody
    public Object classMsgHistory(@RequestParam(value = "pageSize")String pageSize,@RequestParam(value = "pageNumber") String pageNumber,@RequestParam(value = "classId") String classId){
        List<MessageHistoryVO> messageHistoryList = iMessageHistoryService.getMessageHistoryList(Long.parseLong(classId),null,null,null);
        return messageHistoryList;
    }
}
