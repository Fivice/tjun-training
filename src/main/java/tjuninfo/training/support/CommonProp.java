package tjuninfo.training.support;

public class CommonProp {
    private String createBy;		// 创建者
    private String createDate;		// 创建时间
    private String updateBy;		// 更新者
    private String updateDate;		// 更新时间
    private String remarks;		// 备注信息

    public CommonProp() {
    }

    public CommonProp(String createBy, String createDate) {
        this.createBy = createBy;
        this.createDate = createDate;
    }

    public CommonProp(String createBy, String createDate, String updateBy) {
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
    }

    public CommonProp(String createBy, String createDate, String updateBy, String updateDate) {
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
