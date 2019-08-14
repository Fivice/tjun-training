package tjuninfo.training.task.vo;

import tjuninfo.training.task.entity.Student;

import java.io.Serializable;

public class SignUpByHeadMasterVO implements Serializable {
    //学员信息
    Student student;
    //学员班级id
    long classId;
    /**
     * 学员在该班级的报名状态
     * {status=0:学员信息不存在;status=1:学员信息存在但是没在该班级报名;status=2:该学员已经在该班级报过名}
     */
    int status;

    /**
     * 学员报名时间，没报名则null
     */
    String reportTime;



    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
