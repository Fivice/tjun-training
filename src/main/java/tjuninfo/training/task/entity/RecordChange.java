package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/*
录改表实体
 */
@Entity
@Table(name = "record_change")
public class RecordChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 区域编码
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 班级主键
     */
    @Column(name = "class_id")
    private Long classId;

    /**
     * 统收培训费
     */
    @Column(name = "trainingFee_collection")
    private Double trainingFeeCollection;

    /**
     * 统收住宿费
     */
    @Column(name = "hotelExpense_collection")
    private Double hotelExpenseCollection;

    /**
     * 统收就餐费
     */
    @Column(name = "diningFee_collection")
    private Double diningFeeCollection;

    /**
     * 统收其他费用
     */
    @Column(name = "otherExpenses_collection")
    private Double otherExpensesCollection;

    /**
     * 说明
     */
    @Column(name = "explain_collection")
    private String explainCollection;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Double getTrainingFeeCollection() {
        return trainingFeeCollection;
    }


    public void setTrainingFeeCollection(Double trainingFeeCollection) {
        this.trainingFeeCollection = trainingFeeCollection;
    }

    public Double getHotelExpenseCollection() {
        return hotelExpenseCollection;
    }

    public void setHotelExpenseCollection(Double hotelExpenseCollection) {
        this.hotelExpenseCollection = hotelExpenseCollection;
    }

    public Double getDiningFeeCollection() {
        return diningFeeCollection;
    }

    public void setDiningFeeCollection(Double diningFeeCollection) {
        this.diningFeeCollection = diningFeeCollection;
    }

    public Double getOtherExpensesCollection() {
        return otherExpensesCollection;
    }

    public void setOtherExpensesCollection(Double otherExpensesCollection) {
        this.otherExpensesCollection = otherExpensesCollection;
    }

    public String getExplainCollection() {
        return explainCollection;
    }

    public void setExplainCollection(String explainCollection) {
        this.explainCollection = explainCollection;
    }
}
