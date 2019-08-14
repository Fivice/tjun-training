package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.util.List;

public class EvaluationResultVO implements Serializable {
//    评价分类
    private String type;
//    评价项目
    private List<EvaluationProjectVO> project;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<EvaluationProjectVO> getProject() {
        return project;
    }

    public void setProject(List<EvaluationProjectVO> project) {
        this.project = project;
    }
}
