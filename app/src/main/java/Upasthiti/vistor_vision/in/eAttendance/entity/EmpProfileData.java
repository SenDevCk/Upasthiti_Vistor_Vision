package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class EmpProfileData implements Serializable {
    private String EmpNo="";
    private String OfficeEmpId="";
    private String Post_name="";
    private String UserName="";
    private String mobile="";
    private String HomeDistrict="";
    private String Gender="";
    private String DOJ="";
    private String DOB="";
    private String Address="";
    private String inPhoto="";
    private String AccountNo="";
    private String IFSCCode="";
    private String AadhaarNo="";
    private String PanNo="";


    private static Class<EmpProfileData> profileDataClass= EmpProfileData.class;


    public EmpProfileData(SoapObject sobj)
    {

        this.setEmpNo(sobj.getProperty("EmpNo").toString());
        this.setOfficeEmpId(sobj.getProperty("OfficeEmpId").toString());
        this.setPost_name(sobj.getProperty("Post_name").toString());
        this.setUserName(sobj.getProperty("UserName").toString());
        this.setMobile(sobj.getProperty("mobile").toString());
        this.setHomeDistrict(sobj.getProperty("HomeDistrict").toString());
        this.setGender(sobj.getProperty("Gender").toString());
        this.setDOJ(sobj.getProperty("DOJ").toString());
        this.setDOB(sobj.getProperty("DOB").toString());
        this.setAddress(sobj.getProperty("Address").toString());
        this.setInPhoto(sobj.getProperty("inPhoto").toString());
        this.setAccountNo(sobj.getProperty("AccountNo").toString());
        this.setIFSCCode(sobj.getProperty("IFSCCode").toString());
        this.setAadhaarNo(sobj.getProperty("AadhaarNo").toString());
        this.setPanNo(sobj.getProperty("PanNo").toString());

    }
    public EmpProfileData() {
        super();
    }

    public static Class<EmpProfileData> getProfileDataClass() {
        return profileDataClass;
    }

    public static void setProfileDataClass(Class<EmpProfileData> profileDataClass) {
        EmpProfileData.profileDataClass = profileDataClass;
    }

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
    }

    public String getOfficeEmpId() {
        return OfficeEmpId;
    }

    public void setOfficeEmpId(String officeEmpId) {
        OfficeEmpId = officeEmpId;
    }

    public String getPost_name() {
        return Post_name;
    }

    public void setPost_name(String post_name) {
        Post_name = post_name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHomeDistrict() {
        return HomeDistrict;
    }

    public void setHomeDistrict(String homeDistrict) {
        HomeDistrict = homeDistrict;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String DOJ) {
        this.DOJ = DOJ;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getInPhoto() {
        return inPhoto;
    }

    public void setInPhoto(String inPhoto) {
        this.inPhoto = inPhoto;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }

    public String getAadhaarNo() {
        return AadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        AadhaarNo = aadhaarNo;
    }

    public String getPanNo() {
        return PanNo;
    }

    public void setPanNo(String panNo) {
        PanNo = panNo;
    }





}
