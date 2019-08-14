package tjuninfo.training.task.vo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Auther: win7
 * @Date: 2018/12/6 10:43
 * @Description:教师人脸识别实际就餐记录VO
 */
public class TeachDiningFaceRecordVO implements Serializable {

    /**
     * 主键id
     **/
    private Integer id;

    /**
     * 教师ID
     **/
    private Integer teacherId;

    /**
     * 班级id
     **/
    private BigInteger classId;

    /**
     * 就餐地点
     **/
    private String diningPlace;

    /**
     * 就餐日期
     **/
    private String day;

    /**
     * 早餐
     **/
    private Integer breakfast;

    /**
     * 中餐
     **/
    private Integer lunch;

    /**
     * 晚餐
     **/
    private Integer dinner;

    /** 班级编号**/
    private String classNumber;

    /** 班级名称**/
    private String className;

    /**教师姓名**/
    private String teacherName;

    /**早餐次数**/
    private BigDecimal countB;

    /**中餐次数**/
    private BigDecimal countL;

    /**晚餐次数**/
    private BigDecimal countD;

    /**就餐率**/
    private BigDecimal rate;

    /**授权者编号**/
    private Integer authorizerId;

    public Integer getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(Integer authorizerId) {
        this.authorizerId = authorizerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public BigInteger getClassId() {
        return classId;
    }

    public void setClassId(BigInteger classId) {
        this.classId = classId;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getLunch() {
        return lunch;
    }

    public void setLunch(Integer lunch) {
        this.lunch = lunch;
    }

    public Integer getDinner() {
        return dinner;
    }

    public void setDinner(Integer dinner) {
        this.dinner = dinner;
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
}
