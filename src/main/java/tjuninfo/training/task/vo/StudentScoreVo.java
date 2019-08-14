package tjuninfo.training.task.vo;

import java.io.Serializable;

public class StudentScoreVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主键 **/
    private Integer siId;

	/**主键 **/
	private Integer stuId;

    /**姓名**/
    private String siName;
    
    /**身份证号**/
	private String siIdNumber;
    
    /**
     * 成绩
     */
    private String mark;

	/**证书名称**/
	private String reportName;

	/**证书编号**/
	private String reportNumber;

	/**备注**/
	private String remark;

	public Integer getSiId() {
		return siId;
	}

	public void setSiId(Integer siId) {
		this.siId = siId;
	}

	public String getSiName() {
		return siName;
	}

	public void setSiName(String siName) {
		this.siName = siName;
	}

	public String getSiIdNumber() {
		return siIdNumber;
	}

	public void setSiIdNumber(String siIdNumber) {
		this.siIdNumber = siIdNumber;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
}
