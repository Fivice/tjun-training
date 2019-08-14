package tjuninfo.training.task.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.IReceiptFormService;

import java.util.List;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName ReceiptFormController
 * @date 2018/11/11 22:35
 **/
@Controller
@RequestMapping(value = "/ReceiptForm")
public class ReceiptFormController extends BaseController {
    @Autowired
    private IReceiptFormService iReceiptFormService;
    @Autowired
    private ClassInfoService classInfoService;


    @GetMapping(value = "/StudentInfoInReceiptForm")
    @ResponseBody
    public List StudentInfoInReceiptForm(){
        String classId = (String) request.getSession().getAttribute("classId");

        return iReceiptFormService.getStudentInfoListByReceiptForm("",classId);
    }
    @GetMapping(value = "/view")
    public String view(Model model){
        String classId = request.getParameter("id");
        request.getSession().setAttribute("classId",classId);
        ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));
        model.addAttribute("classInfo",classInfo);
        return "/ReceiptFormManager/StudentInfoInReceiptForm";
    }
}
