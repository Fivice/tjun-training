package tjuninfo.training.task.vo;

import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.Register;

import java.io.Serializable;

public class StudentEvaluationStatusVO implements Serializable {

    private Register register;

    private ClassInfo classInfo;

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
}
