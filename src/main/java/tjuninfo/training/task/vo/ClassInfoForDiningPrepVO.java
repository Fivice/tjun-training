package tjuninfo.training.task.vo;

import java.io.Serializable;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName ClassInfoForDiningStatistics
 * @date 2019/1/29 23:06
 **/
public class ClassInfoForDiningPrepVO implements Serializable {
//    班级id
    private Long classId;
//    班级编号
    private String classNumber;
//    班级名称
    private String className;
//    就餐地点
    private String diningPlace;

    //早餐报到人数
    private Integer registerNumberBf;

    //午餐报到人数
    private Integer registerNumberLu;

    //晚餐报到人数
    private Integer registerNumberDi;

    //早餐计划人数
    private Integer plannedNumberBf;

    //午餐计划人数
    private Integer plannedNumberLu;

    //晚餐计划人数
    private Integer plannedNumberDi;

    //教师早餐
    private Integer teacherBf;

    //教师午餐
    private Integer teacherLu;

    //教师晚餐
    private Integer teacherDi;




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

    public Integer getRegisterNumberBf() {
        return registerNumberBf;
    }

    public void setRegisterNumberBf(Integer registerNumberBf) {
        this.registerNumberBf = registerNumberBf;
    }

    public Integer getRegisterNumberLu() {
        return registerNumberLu;
    }

    public void setRegisterNumberLu(Integer registerNumberLu) {
        this.registerNumberLu = registerNumberLu;
    }

    public Integer getRegisterNumberDi() {
        return registerNumberDi;
    }

    public void setRegisterNumberDi(Integer registerNumberDi) {
        this.registerNumberDi = registerNumberDi;
    }

    public Integer getPlannedNumberBf() {
        return plannedNumberBf;
    }

    public void setPlannedNumberBf(Integer plannedNumberBf) {
        this.plannedNumberBf = plannedNumberBf;
    }

    public Integer getPlannedNumberLu() {
        return plannedNumberLu;
    }

    public void setPlannedNumberLu(Integer plannedNumberLu) {
        this.plannedNumberLu = plannedNumberLu;
    }

    public Integer getPlannedNumberDi() {
        return plannedNumberDi;
    }

    public void setPlannedNumberDi(Integer plannedNumberDi) {
        this.plannedNumberDi = plannedNumberDi;
    }

    public Integer getTeacherBf() {
        return teacherBf;
    }

    public void setTeacherBf(Integer teacherBf) {
        this.teacherBf = teacherBf;
    }

    public Integer getTeacherLu() {
        return teacherLu;
    }

    public void setTeacherLu(Integer teacherLu) {
        this.teacherLu = teacherLu;
    }

    public Integer getTeacherDi() {
        return teacherDi;
    }

    public void setTeacherDi(Integer teacherDi) {
        this.teacherDi = teacherDi;
    }
}
