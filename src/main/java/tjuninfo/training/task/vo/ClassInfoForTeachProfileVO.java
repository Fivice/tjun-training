package tjuninfo.training.task.vo;

import java.io.Serializable;


public class ClassInfoForTeachProfileVO implements Serializable {
//    班级id
    private Long classId;
//    班级编号
    private String classNumber;
//    班级名称
    private String className;
//    班主任
    private String teacherName;

    //教师人数
    private Long teacherDinCount;

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getTeacherDinCount() {
        return teacherDinCount;
    }

    public void setTeacherDinCount(Long teacherDinCount) {
        this.teacherDinCount = teacherDinCount;
    }
}
