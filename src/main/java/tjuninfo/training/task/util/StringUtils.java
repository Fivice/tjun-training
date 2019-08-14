package tjuninfo.training.task.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.security.Principal;

/**
 * String工具类
 * @author shenxianyan
 * @date 2018年5月16日
 */
public class StringUtils {
	
	/**
	 * 判断字符不为空
	 * @param str	
	 * @return	为空返回false，不为空返回true
	 */
	public static boolean isNotBlank(String str){
		return str != null && !"".equals(str);
	}
	
	
	/**
	 * 判断字符是否为空
	 * @param str
	 * @return	为空返回true，不为空返回false
	 */
	public static boolean isBlank(String str){
		return str == null || "".equals(str);
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static String checkNull(String str){
		if(str == null || str.equals("")){
			return "";
		}else{
			return str;
		}

	}

	/**
	 * 获取名称后缀
	 * @param name
	 * @return
	 */
	public static String getExt(String name){
		if(name == null || "".equals(name) || !name.contains("."))
			return "";
		return name.substring(name.lastIndexOf(".")+1);
	}

}
