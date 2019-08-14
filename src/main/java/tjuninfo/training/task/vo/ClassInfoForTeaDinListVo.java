package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.util.List;

public class ClassInfoForTeaDinListVo implements Serializable {

    //  总记录条数
    private long totalResults;
    //总页数
    private long totalPages;

    //ClassInfoForTeachProfileVO数据链表
    private List<ClassInfoForTeachProfileVO> teachProfileVOList;

    public ClassInfoForTeaDinListVo(long totalResults, long totalPages, List<ClassInfoForTeachProfileVO> teachProfileVOList) {
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.teachProfileVOList = teachProfileVOList;
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

    public List<ClassInfoForTeachProfileVO> getTeachProfileVOList() {
        return teachProfileVOList;
    }

    public void setTeachProfileVOList(List<ClassInfoForTeachProfileVO> teachProfileVOList) {
        this.teachProfileVOList = teachProfileVOList;
    }
}
