package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: win7
 * @Date: 2018/10/9 10:57
 * @Description:学员就餐记录表实体类VO
 */
public class StuDiningRecordVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**主键 ID**/
    private Integer id;

    /** 班级ID**/
    private Integer classId;

    /**学员ID**/
    private Integer studentId;

    /**早餐次数**/
    private BigDecimal countB;

    /**中餐次数**/
    private BigDecimal countL;

    /**晚餐次数**/
    private BigDecimal countD;

    /**学员姓名**/
    private String studentName;

    /**学员身份证**/
    private String idCard;

    /**班级编号**/
    private String classNumber;

    /**班级名称**/
    private String className;

    /**就餐地点**/
    private String diningPlace;

    /**就餐率**/
    private BigDecimal rate;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
