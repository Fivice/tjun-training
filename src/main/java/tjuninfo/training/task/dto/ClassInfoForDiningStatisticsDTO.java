package tjuninfo.training.task.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName ClassInfoForDiningStatistics
 * @date 2019/1/29 23:06
 **/
public class ClassInfoForDiningStatisticsDTO implements Serializable {
//    班级id
    private BigInteger classId;
//    班级编号
    private String classNumber;
//    班级名称
    private String className;
//    就餐地点
    private String diningPlace;
//    计划人数
    private Integer plannedNumber;


    public BigInteger getClassId() {
        return classId;
    }

    public void setClassId(BigInteger classId) {
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

    public Integer getPlannedNumber() {
        return plannedNumber;
    }

    public void setPlannedNumber(Integer plannedNumber) {
        this.plannedNumber = plannedNumber;
    }
}
