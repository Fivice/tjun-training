package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: win7
 * @Date: 2018/10/12 10:40
 * @Description:教师就餐记录表实体类VO
 */
public class TeachDiningRecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**主键 id**/
    private Integer id;

    /** 班级id**/
    private Integer classId;

    /** 班级编号**/
    private String classNumber;

    /** 班级名称**/
    private String className;

    /**教师姓名**/
    private String teacherName;

    /**区域**/
    private String diningPlace;

    /**日期**/
    private String teacherDay;

    /**早餐次数**/
    private BigDecimal countB;

    /**中餐次数**/
    private BigDecimal countL;

    /**晚餐次数**/
    private BigDecimal countD;

    /**就餐率**/
    private BigDecimal rate;

    /**流水号**/
    private String num;

    /**授权者编号**/
    private Integer authorizerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public String getTeacherDay() {
        return teacherDay;
    }

    public void setTeacherDay(String teacherDay) {
        this.teacherDay = teacherDay;
    }

    public BigDecimal getCountB() {
        return countB;
    }

    public void setCountB(BigDecimal countB) {
        this.countB = countB;
    }

    public BigDecimal getCountL() {
        return countL;
    }

    public void setCountL(BigDecimal countL) {
        this.countL = countL;
    }

    public BigDecimal getCountD() {
        return countD;
    }

    public void setCountD(BigDecimal countD) {
        this.countD = countD;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(Integer authorizerId) {
        this.authorizerId = authorizerId;
    }
}
