package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.util.List;

public class EvaluationProjectVO implements Serializable {
//    评价项目
    private String project;
//    评价建议
    private List<String> suggests;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<String> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<String> suggests) {
        this.suggests = suggests;
    }
}
