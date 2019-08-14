package tjuninfo.training.task.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tjuninfo.training.task.entity.ClassInfo;

import java.io.Serializable;

/**
 * @author wubin
 * @date 2018年10月17号
 * @Description 报名班级管理
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpManagerVO implements Serializable {

    private static final long serialVersionUID =1L;

    /**主键 id**/
    private long id;
    /**班级编号**/
    private String classId;
    /**班级名称**/
    private String className;
    /**教师Id**/
    private Integer userId;
    /**教师姓名**/
    private String teacherName;
    /**报名人数**/
    private long signUpCount;
    /**计划报名人数**/
    private long signUpCountPlan;
    /**主办单位**/
    private String hostUnit;
    /**开班起止时间**/
    private String startStopTime;
    /**开班天数**/
    private Integer days;
    /**开班状态**/
    private Integer status;

    private String regPlace;


}
