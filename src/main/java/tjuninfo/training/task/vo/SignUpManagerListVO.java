package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.util.List;

public class SignUpManagerListVO implements Serializable {

    //  总记录条数
    private long totalResults;
    //总页数
    private long totalPages;

    //SignUpManagerVO数据链表
    private List<SignUpManagerVO> signUpManagerVOList;

    public SignUpManagerListVO(long totalResults, long totalPages, List<SignUpManagerVO> signUpManagerVOList) {
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.signUpManagerVOList = signUpManagerVOList;
    }

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

    public List<SignUpManagerVO> getSignUpManagerVOList() {
        return signUpManagerVOList;
    }

    public void setSignUpManagerVOList(List<SignUpManagerVO> signUpManagerVOList) {
        this.signUpManagerVOList = signUpManagerVOList;
    }
}
