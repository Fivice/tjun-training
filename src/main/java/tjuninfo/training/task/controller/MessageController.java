package tjuninfo.training.task.controller;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.MessageCode;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.MessageHistory;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.IMessageHistoryService;
import tjuninfo.training.task.vo.MsgReturnStatusVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/message")
public class MessageController extends BaseController {

    private final ClassInfoService classInfoService;
    private final IMessageHistoryService iMessageHistoryService;
    @Autowired
    public MessageController(ClassInfoService classInfoService, IMessageHistoryService iMessageHistoryService) {
        this.classInfoService = classInfoService;
        this.iMessageHistoryService = iMessageHistoryService;
    }

    @PostMapping(value = "/sendMessage")
    @ResponseBody
    public Object sendMessage(@RequestParam(value = "classId") String classId,@RequestParam(value = "phoneList") String phoneList) throws IOException {
        long id;
        List<MsgReturnStatusVO> msgReturnStatusVOS = new ArrayList<>();

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(MessageCode.POST_URL);
        postMethod.addRequestHeader("Content-Type",MessageCode.CONTENT_TYPE);

        String[] phoneArr = {};
        if (StringUtils.isNotBlank(classId)&&StringUtils.isNotBlank(phoneList)){
            id = Long.parseLong(classId);
            //将字符串拆分成手机号数组
            phoneArr = phoneList.substring(1,phoneList.length()-1).split(",");
            //获取班级信息
            ClassInfo classInfo = classInfoService.get(id);
            String smsText = iMessageHistoryService.formatterMessage(classInfo).toString();
            for (String phone : phoneArr) {
                //按照每个手机号发送短信
                String smsMob = phone.substring(1,phone.length()-1);
                NameValuePair[] nameValuePairs = {
                        new NameValuePair("Uid", MessageCode.UID),
                        new NameValuePair("Key", MessageCode.KEY),
                        new NameValuePair("smsMob",smsMob),
                        new NameValuePair("smsText",smsText)
                };
                postMethod.setRequestBody(nameValuePairs);
                //根据返回状态处理结果

                httpClient.executeMethod(postMethod);
                Header[] header = postMethod.getResponseHeaders();
                int statusCode = postMethod.getStatusCode();
                String result = new String(postMethod.getResponseBodyAsString().getBytes(MessageCode.RESULT_TYPE));
                MsgReturnStatusVO msgReturnStatusVO = new MsgReturnStatusVO();
                msgReturnStatusVO.setPhone(smsMob);
                msgReturnStatusVO.setReturnCode(result);
                msgReturnStatusVOS.add(msgReturnStatusVO);
                if (Integer.parseInt(result)>0){
                    //发送成功后将该班级发送过的手机号以及相关信息存储起来
                    MessageHistory messageHistory = new MessageHistory();
                    messageHistory.setClassId(Long.parseLong(classId));
                    messageHistory.setPhone(smsMob);
                    messageHistory.setSendTime(new Date());
                    messageHistory.setUser(getUser().getUserId());
                    iMessageHistoryService.save(messageHistory);
                }
            }
        }

        postMethod.releaseConnection();

        return msgReturnStatusVOS;
    }
}
