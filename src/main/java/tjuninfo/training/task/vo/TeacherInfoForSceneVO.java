package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class TeacherInfoForSceneVO implements Serializable {
    //教师id
    private int teacherId;
    //教师姓名
    private String teacherName;
    //身份证号码
    private String idNumber;
    //班级id
    private BigInteger classId;
    //班级名
    private String className;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public BigInteger getClassId() {
        return classId;
    }

    public void setClassId(BigInteger classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
