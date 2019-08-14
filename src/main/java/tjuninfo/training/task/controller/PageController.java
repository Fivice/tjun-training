package tjuninfo.training.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tjuninfo.training.support.controller.BaseController;
/**
 * 访问页面
 * @author shenxianyan
 * @date 2018年5月28日
 */
@Controller
@RequestMapping("/page")
public class PageController extends BaseController{
	
	/**访问用户页面**/
	@RequestMapping("/user")
	public String toUser(){
		return "user/user";
	}
	
	/**访问主页面**/
	@RequestMapping("/main")
	public String index(){
		return "main";
	}
}
