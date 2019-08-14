package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 回执单表实体类
 * @author Fivice
 * @date 2018年11月9日11:35:10
 */
@Entity
@Table(name = "receipt_form")
public class ReceiptForm extends BaseEntity {
    //private static final long serialVersionUID = 1L;

    /**主键**/
    private long receiptId;
    /**学生id**/
    private Integer siId;
    /**班级id**/
    private long classId;
    /**报到时间**/
    private String reportTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    public long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(long receiptId) {
        this.receiptId = receiptId;
    }

    @Column(name = "si_id")
    public Integer getSiId() {
        return siId;
    }

    public void setSiId(Integer siId) {
        this.siId = siId;
    }

    @Column(name = "class_id")
    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    @Column(name = "report_time")
    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
