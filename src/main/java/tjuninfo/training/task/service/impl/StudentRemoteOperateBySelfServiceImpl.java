package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.constant.NationCode;
import tjuninfo.training.task.constant.SignUpManagerConstantCode;
import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.entity.Unit;
import tjuninfo.training.task.service.IStudentRemoteOperateBySelfService;
import tjuninfo.training.task.vo.NationVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentRemoteOperateBySelfServiceImpl implements IStudentRemoteOperateBySelfService {
    @Autowired
    private IBaseService<Unit> unitIBaseService;
    @Override
    public Integer getUnitFromSelect(String firstUnitSelect, String nextUnitSelect) {
        int unitId = 0;
        //选中第一个，第二个没选中
        if (!firstUnitSelect.equals("-1") && nextUnitSelect.equals("-1")){
            unitId = Integer.parseInt(firstUnitSelect);
        }
        //选中第一个，并且第二个也选了
        if (!firstUnitSelect.equals("-1") && !nextUnitSelect.equals("-1")){
            unitId = Integer.parseInt(nextUnitSelect);
        }
        return unitId;
    }

    @Override
    public Student studentInfoToPackaging(Student student,String siName, String siPhone, String siIdNumber, String email, String updateOrCreateBy, Integer unitId,String stuNumber,String departmentName,Integer nationId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (student.getSiId()==null){
            student.setCreateBy(updateOrCreateBy);
            student.setCreateDate(sdf.format(new Date()));
        }

        student.setUpdateBy(updateOrCreateBy);
        student.setUpdateDate(sdf.format(new Date()));

        student.setSiName(siName);
        student.setPhoneNumber(siPhone);
        student.setSiIDNumber(siIdNumber);
        student.setSex(transIdNumberToSex(siIdNumber));
        student.setEmail(email);
        student.setSiNumber(stuNumber);
        student.setDeparentmentName(departmentName);
        student.setSiUnitId(unitId<=0?null:unitId);
        student.setStatus(SignUpManagerConstantCode.STUDENT_STATUS_VALID);
        student.setUnitName(transUnitIdToUnitName(unitId));
        student.setSex(transIdNumberToSex(siIdNumber));
        student.setEthnicGroup(getNation(nationId));
        return student;
    }

    @Override
    public String transIdNumberToSex(String siIdNumber) {
        if (Integer.parseInt(siIdNumber.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            return "1";
        } else {
            return "0";
        }
    }

    @Override
    public String transUnitIdToUnitName(Integer unitId) {
        if (unitId<=0){
            return null;
        }else{
            return unitIBaseService.get(unitId).getAreaName();
        }
    }

    @Override
    public String getNation(int nationId) {
        String nation ="";
        for (NationCode n: NationCode.values()
             ) {
            if (n.getValue() == nationId){
                nation = n.getNation();
            }
        }
        return nation;
    }

    @Override
    public List<NationVO> formNationList() {

        List<NationVO> list = new ArrayList<>();
        for (NationCode n: NationCode.values()
        ) {
            NationVO nation = new NationVO(n.getValue(),n.getNation());
            list.add(nation);
        }
        return list;
    }

    @Override
    public List  removeDuplicate(List list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
