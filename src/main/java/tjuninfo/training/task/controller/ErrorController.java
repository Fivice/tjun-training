package tjuninfo.training.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tjuninfo.training.support.controller.BaseController;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
    /**首页**/
    @RequestMapping("/404")
    public String toIndex(){
        return "error";
    }

}
