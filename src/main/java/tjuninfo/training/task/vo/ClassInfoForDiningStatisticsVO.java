package tjuninfo.training.task.vo;

import java.io.Serializable;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName ClassInfoForDiningStatistics
 * @date 2019/1/29 23:06
 **/
public class ClassInfoForDiningStatisticsVO implements Serializable {
//    班级id
    private Long classId;
//    班级编号
    private String classNumber;
//    班级名称
    private String className;
//    就餐地点
    private String diningPlace;
//    报到人数
    private Long registerNumber;
//    计划人数
    private Integer plannedNumber;

    public ClassInfoForDiningStatisticsVO(Long classId, String classNumber, String className, String diningPlace, Long registerNumber, Integer plannedNumber) {
        this.classId = classId;
        this.classNumber = classNumber;
        this.className = className;
        this.diningPlace = diningPlace;
        this.registerNumber = registerNumber;
        this.plannedNumber = plannedNumber;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public Long getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(Long registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Integer getPlannedNumber() {
        return plannedNumber;
    }

    public void setPlannedNumber(Integer plannedNumber) {
        this.plannedNumber = plannedNumber;
    }
}
