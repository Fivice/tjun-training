package tjuninfo.training.task.enums;
/**
 * MenuTypeEnum 系统目录类型枚举表述常量数据字段
 * @author shenxianyan
 * @date 2018年5月18日
 */

public enum AreaTypeEnum {
	FIRST_AREA(1, "一级目录"),
	SECOND_AREA(2, "二级目录"),
	THIRD_AREA(3, "三级目录"),
	FOURTH_AREA(4, "四级目录");

	private Integer type;

	private String typeInfo;
    
	private AreaTypeEnum(Integer type, String typeInfo) {
		this.type = type;
		this.typeInfo = typeInfo;
	}

	public Integer getType() {
		return type;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public static AreaTypeEnum stateOf(int index) {
		for (AreaTypeEnum typeEnum : values()) {
			if (typeEnum.getType() == index) {
				return typeEnum;
			}
		}
		return null;
	}
}
