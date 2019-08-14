package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 培训科目的实体类
 */
@Entity
@Table(name = "training_subject")
public class TrainingSubject extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ts_id")
	private Integer tsId;
	/*
	 * 培训科目
	 */
	@Column(name = "ts_name")
	private String tsName;

	public Integer getTsId() {
		return tsId;
	}

	public void setTsId(Integer tsId) {
		this.tsId = tsId;
	}

	public String getTsName() {
		return tsName;
	}

	public void setTsName(String tsName) {
		this.tsName = tsName;
	}
}
