package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 评价结果实体类
* @author 
 * @date 2018年5月27日
 */
@Entity
@Table(name = "evaluate_score")
public class EvaluateScore extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 报名id
	 **/
	@Column(name = "register_id")
	private Integer registerId;

	/**
	 * 课程id
	 **/
	@Column(name = "project_id")
	private Integer projectId;

	/**
	 * 结果
	 * 1非常满意130； 2 满意115； 3基本满意90； 4一般75； 5不满意60
	 * 有个别课程是建议，没有分值
	 **/
	@Column(name = "result")
	private String result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
