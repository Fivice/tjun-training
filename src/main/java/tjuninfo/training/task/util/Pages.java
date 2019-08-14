package tjuninfo.training.task.util;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;

public class Pages {
    private List result; // 结果集
    private int pageSize; // 页大小
    private int startPage; // 起始页 从1开始
    private ScrollableResults scrollableResults;
    private int totalResults; // 总记录的条数
    private int totalPages; // 总页数

    public Pages(int startPage, int pageSize, Query query) {
        this.startPage = startPage;
        this.pageSize = pageSize;
        this.result = null;
        try {
            this.scrollableResults = query.scroll();
            this.scrollableResults.last();
            if (scrollableResults.getRowNumber() >= 0) {
                this.totalResults = this.scrollableResults.getRowNumber() + 1;
            } else {
                this.totalResults = 0;
            }
            setTotalPages();
            result = query.setFirstResult(
                    (this.getStartPage() - 1) * this.pageSize).setMaxResults(
                    this.pageSize).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到查询结果
     *
     * @return 查询结果
     */
    public List getResult() {
        return result;
    }

    /**
     * 得到起始页
     *
     * @return
     */
    public int getStartPage() {
        if (startPage < 1) {
            startPage = 1;
        }
        if (startPage > totalPages) {
            startPage = totalPages;
        }
        return startPage;
    }

    /**
     * 得到记录总数
     *
     * @return
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * 得到页大小
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 判断是否是第一页
     *
     * @return
     */
    public boolean isFirstPage() {
        return this.startPage == 1;
    }

    /**
     * 判断是否是有后一页
     *
     * @return
     */
    public boolean hasNextPage() {
        return this.startPage < this.totalPages;
    }

    /**
     * 判断是否是有前一页
     *
     * @return
     */
    public boolean hasPreviousPage() {
        return this.startPage > 1;
    }

    /**
     * 设置总页数
     *
     */
    private void setTotalPages() {
        this.totalPages = this.totalResults / this.pageSize;
        if (totalPages * pageSize < totalResults) {
            totalPages++;
        }
    }

    /**
     * 得到总页数
     *
     * @return
     */
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
