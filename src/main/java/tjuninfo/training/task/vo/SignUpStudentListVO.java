package tjuninfo.training.task.vo;

import tjuninfo.training.task.dto.SignUpStudentDTO;

import java.io.Serializable;
import java.util.List;

public class SignUpStudentListVO implements Serializable {

    //  总记录条数
    private long totalResults;
    //总页数
    private long totalPages;
    //数据表
    private List <SignUpStudentVO> signUpManagerVOList;

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<SignUpStudentVO> getSignUpManagerVOList() {
        return signUpManagerVOList;
    }

    public void setSignUpManagerVOList(List<SignUpStudentVO> signUpManagerVOList) {
        this.signUpManagerVOList = signUpManagerVOList;
    }
}
