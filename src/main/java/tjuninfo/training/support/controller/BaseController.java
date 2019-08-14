package tjuninfo.training.support.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tjuninfo.training.support.CommonProp;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.util.Result;

/**
 * 父类控制器
 * @author shenxianyan
 * @date 2018年5月16日
 */
@SuppressWarnings("deprecation")
public class BaseController{
	public static final String USER_SESSION = "USER_SESSION";
	protected static ObjectMapper mapper = new ObjectMapper();
	protected static JsonFactory factory = mapper.getJsonFactory();
	protected static Result result = new Result();
	
	protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }
	
	/**将json字符串输出**/
	protected void writeJSON(String json) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}
	/**将对象转成json输出**/
	protected void writeJSON(Object obj) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JsonGenerator responseJsonGenerator = factory.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		responseJsonGenerator.writeObject(obj);
	}

	/**
	 * 获得session用户对象
	 * @return
	 */
	protected SysUser getUser(){
		Object userObj = session.getAttribute(USER_SESSION);
		if(userObj == null){
			return null;
		}
		return (SysUser)userObj;
	}

	/**
	 * 设置公共属性
	 * @param
	 */
	public CommonProp setCommon(){
		//当前时间
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = tempDate.format(new java.util.Date());
		//查询当前登录用户
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		//创建者,创建时间,更新者，更新时间
		CommonProp commonProp= new CommonProp(userInfo.getUserName(),dateTime,userInfo.getUserName(),dateTime);
		return commonProp;
	}

	/**
	 * 添加Model消息
	 * @param messages
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}

	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
}
