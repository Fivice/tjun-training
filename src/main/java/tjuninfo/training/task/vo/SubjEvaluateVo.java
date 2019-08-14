package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 总体评价表vo
 * @author win7
 *
 */
public class SubjEvaluateVo implements Serializable{
	private static final long serialVersionUID = 1L;
    /**
     * 报名id
     **/
    private Integer registerId;

    /**
     * 课程id
     **/
    private Integer projectId;

    /**
     * 课程名称
     */
    private String schedulingContent;
    /**
     * 教师名
     */
    private String teacher;
    /**
     * 结果
     * 1非常满意130
     **/
    private BigDecimal result1;

    /**
     * 结果
     *  2 满意115
     **/
    private BigDecimal result2;

    /**
     * 结果
     * 3基本满意90
     **/
    private BigDecimal result3;

    /**
     * 结果
     *4一般75
     **/
    private BigDecimal result4;

    /**
     * 结果
     * 5不满意60
     **/
    private BigDecimal result5;

    /**
     * 平均结果
     **/
    private BigDecimal result;
    /**
     * 结果次数
     **/
    private BigInteger resultSum;

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSchedulingContent() {
        return schedulingContent;
    }

    public void setSchedulingContent(String schedulingContent) {
        this.schedulingContent = schedulingContent;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public BigDecimal getResult1() {
        return result1;
    }

    public void setResult1(BigDecimal result1) {
        this.result1 = result1;
    }

    public BigDecimal getResult2() {
        return result2;
    }

    public void setResult2(BigDecimal result2) {
        this.result2 = result2;
    }

    public BigDecimal getResult3() {
        return result3;
    }

    public void setResult3(BigDecimal result3) {
        this.result3 = result3;
    }

    public BigDecimal getResult4() {
        return result4;
    }

    public void setResult4(BigDecimal result4) {
        this.result4 = result4;
    }

    public BigDecimal getResult5() {
        return result5;
    }

    public void setResult5(BigDecimal result5) {
        this.result5 = result5;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public BigInteger getResultSum() {
        return resultSum;
    }

    public void setResultSum(BigInteger resultSum) {
        this.resultSum = resultSum;
    }
}
