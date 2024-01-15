package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class EmployeeListData implements Serializable {
    private String EmpNo="";
    private String DistrictName="";
    private String BlockName="";
    private String UserName="";
    private String inPhoto="";
    private String DateofJoin="";

    private static Class<EmployeeListData> employeeListDataClass= EmployeeListData.class;


    public EmployeeListData(SoapObject sobj)
    {

        this.setEmpNo(sobj.getProperty("EmpNo").toString());
        this.setDistrictName(sobj.getProperty("DistrictName").toString());
        this.setBlockName(sobj.getProperty("BlockName").toString());
        this.setUserName(sobj.getProperty("UserName").toString());
        this.setInPhoto(sobj.getProperty("inPhoto").toString());

    }

    public static Class<EmployeeListData> getEmployeeListDataClass() {
        return employeeListDataClass;
    }

    public static void setEmployeeListDataClass(Class<EmployeeListData> employeeListDataClass) {
        EmployeeListData.employeeListDataClass = employeeListDataClass;
    }

    public EmployeeListData() {
        super();
    }

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getInPhoto() {
        return inPhoto;
    }

    public void setInPhoto(String inPhoto) {
        this.inPhoto = inPhoto;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getDateofJoin() {
        return DateofJoin;
    }

    public void setDateofJoin(String dateofJoin) {
        DateofJoin = dateofJoin;
    }
}
