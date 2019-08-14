package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class ClassInfoForSceneVO implements Serializable {
    private BigInteger classId;
    private String className;
    private String StartStopTime;
    private String teacherId;
    private String teacherName;

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

    public String getStartStopTime() {
        return StartStopTime;
    }

    public void setStartStopTime(String startStopTime) {
        StartStopTime = startStopTime;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
