package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class ViewEmpProfile implements Serializable {
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
    private String Password="";
    private String is_Lock="";


    private static Class<ViewEmpProfile> viewEmpProfileClass= ViewEmpProfile.class;


    public ViewEmpProfile(SoapObject sobj)
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
        this.setPassword(sobj.getProperty("Password").toString());
        this.setIs_Lock(sobj.getProperty("IsLock").toString());

    }
    public ViewEmpProfile() {
        super();
    }

    public static Class<ViewEmpProfile> viewEmpProfileClass() {
        return viewEmpProfileClass;
    }

    public static void setViewEmpProfileClass(Class<ViewEmpProfile> viewEmpProfileClass1) {
        viewEmpProfileClass = viewEmpProfileClass1;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getIs_Lock() {
        return is_Lock;
    }

    public void setIs_Lock(String is_Lock) {
        this.is_Lock = is_Lock;
    }
}
