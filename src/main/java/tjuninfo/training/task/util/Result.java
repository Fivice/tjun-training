package tjuninfo.training.task.util;

import java.io.Serializable;
/**
 * 返回结果
 * @author shenxianyan
 * @date 2018年5月16日
 */
public class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2267751680865696851L;
	/**是否成功**/
	private Boolean success = false ;
	/**返回消息**/
	private String message;
	/**返回数据**/
	private Object data;
	
	
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
