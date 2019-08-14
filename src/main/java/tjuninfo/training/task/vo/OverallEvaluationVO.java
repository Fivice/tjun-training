package tjuninfo.training.task.vo;



import java.io.Serializable;

public class OverallEvaluationVO implements Serializable {

    /**主键**/
    private Integer id;

    /**月份**/
    private String month;

    /**班级数**/
    private Integer classNum;

    /**培训质量评价**/
    private Double qualityResult;

    /**综合评价**/
    private Double comprehensiveResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    @Override
    public String toString() {
        return "OverallEvaluationVO{" +
                "id=" + id +
                ", month='" + month + '\'' +
                ", classNum=" + classNum +
                ", qualityResult=" + qualityResult +
                ", comprehensiveResult=" + comprehensiveResult +
                '}';
    }

    public Double getQualityResult() {
        return qualityResult;
    }

    public void setQualityResult(Double qualityResult) {
        this.qualityResult = qualityResult;
    }

    public Double getComprehensiveResult() {
        return comprehensiveResult;
    }

    public void setComprehensiveResult(Double comprehensiveResult) {
        this.comprehensiveResult = comprehensiveResult;
    }
}
