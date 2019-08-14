package tjuninfo.training.task.vo;

import tjuninfo.training.task.entity.Student;

import java.io.Serializable;

public class StudentInfoForRegisterBySelfVO implements Serializable {
    /**学生信息对象 */
    private Student student;
    /**报名状态*/
    private String regStatus;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }
}
