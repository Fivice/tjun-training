package tjuninfo.training.task.enums;
/**
 * MenuTypeEnum 系统目录类型枚举表述常量数据字段
 * @author shenxianyan
 * @date 2018年5月18日
 */

public enum MenuTypeEnum {
	FIRST_MENU(1, "一级目录"),
	SECOND_MENU(2, "二级目录"),
	THIRD_MENU(3, "三级目录"),
	HANDLE_MENU(0, "操作目录");

	private Integer type;

	private String typeInfo;
    
	private MenuTypeEnum(Integer type, String typeInfo) {
		this.type = type;
		this.typeInfo = typeInfo;
	}

	public Integer getType() {
		return type;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public static MenuTypeEnum stateOf(int index) {
		for (MenuTypeEnum typeEnum : values()) {
			if (typeEnum.getType() == index) {
				return typeEnum;
			}
		}
		return null;
	}
}
