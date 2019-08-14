package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.util.List;

public class UnitVO implements Serializable {
    Integer unitId;//单位Id
    String unitName;//单位名称
    List<UnitVO> unitVOList;//子集单位

    public UnitVO(Integer unitId, String unitName, List<UnitVO> unitVOList) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.unitVOList = unitVOList;
    }


    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<UnitVO> getUnitVOList() {
        return unitVOList;
    }

    public void setUnitVOList(List<UnitVO> unitVOList) {
        this.unitVOList = unitVOList;
    }

    @Override
    public String toString() {
        return "{\"data\": [{\"unitId\": \""+unitId+"\",\"unitName\":\""+unitName+"\",\"sub\":\""+unitVOList+"\"}]}";
    }
}
