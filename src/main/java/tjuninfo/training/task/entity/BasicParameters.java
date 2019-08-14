package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 院校基本参数实体类
* @author 
 */
@Entity
@Table(name = "colleges_basic_parameters")
public class BasicParameters extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键 **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**院校名称**/
    @Column(name = "name")
    private String name;
    
    /**系统外网网址**/
    @Column(name = "url")
	private String url;

	/**报名（住宿）地点**/
	@Column(name = "address")
	private String address;

	/**标间/单间标准**/
	@Column(name = "house_standard")
	private String houseStandard;

	/**就餐地点**/
	@Column(name = "eat_place")
	private String eatPlace;

	/**早/中/晚就餐标准**/
	@Column(name = "eat_standard")
	private String eatStandard;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHouseStandard() {
		return houseStandard;
	}

	public void setHouseStandard(String houseStandard) {
		this.houseStandard = houseStandard;
	}

	public String getEatPlace() {
		return eatPlace;
	}

	public void setEatPlace(String eatPlace) {
		this.eatPlace = eatPlace;
	}

	public String getEatStandard() {
		return eatStandard;
	}

	public void setEatStandard(String eatStandard) {
		this.eatStandard = eatStandard;
	}
}
