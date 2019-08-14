package tjuninfo.training.task.constant;

import tjuninfo.training.task.exception.ReturnCode;

public enum ApiCode implements ReturnCode {

	/** 刷卡成功 */
	SUCCESS(1, "刷卡成功"),
	/** 学生证无效 */
	FAILED_invalid(10, "该学生证无效或已失效"),
	/** 无就餐安排 */
	FAILED_no(20, "对不起，您没有就餐安排"),
	/** 就餐限制 */
	FAILED_limit(30, "每人每餐只能刷卡一次"),
	/** 学生证无效 */
	FAILED_invalid2(40, "该教师证无效或已失效"),
	/** 未知错误 */
	UNKNOWN_ERROR(-1, "未知错误,请联系管理员!"),
	/** 脱机数据上传成功 */
	SUCCESS_off(1, "脱机数据上传成功"),
	/** 无脱机数据 */
	NO_DATA(-2, "无脱机数据!");

	/** 返回状态码 */
	private Integer code;

	/** 返回消息 */
	private String message;

	private ApiCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
