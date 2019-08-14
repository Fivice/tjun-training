package tjuninfo.training.task.service;


import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.vo.NationVO;

import java.util.List;

public interface IStudentRemoteOperateBySelfService {

    /**
     * 返回UnitId
     * @param firstUnitSelect
     * @param nextUnitSelect
     * @return
     */
    public Integer getUnitFromSelect(String firstUnitSelect,String nextUnitSelect);

    /**
     * 更新student对象
     * @param student
     * @param siName
     * @param siPhone
     * @param siIdNumber
     * @param email
     * @param updateOrCreateBy
     * @param unitId
     * @return
     */
    public Student studentInfoToPackaging(Student student,String siName,String siPhone,String siIdNumber,String email,String updateOrCreateBy,Integer unitId,String stuNumber,String departmentName,Integer nationId);

    /**
     * 通过身份证号码获取对应的性别:返回值 性别(0:男 1:女)
     * @param IdNumber
     * @return
     */
    public String transIdNumberToSex(String IdNumber);

    /**
     * 通过unitId 返回单位名称
     * @param unitId
     * @return
     */
    public String transUnitIdToUnitName(Integer unitId);

    /**
     * 通过id获取民族
     * @param id
     * @return
     */
    public String getNation(int id);

    public List<NationVO> formNationList();
    public List  removeDuplicate(List list);
}
