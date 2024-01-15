package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class MarkedAttendanceData implements Serializable {
    private String EmpNo="";
    private String Post_name="";
    private String UserName="";
    private String inRemark="";
    private String outRemark="";
    private String inPhoto="";
    private String outPhoto="";
    private String IsInOffice="";
    private String outaddress="";
    private String inAddress="";
    private String outTime="";
    private String inTime="";
    private String InMode="";

    private static Class<MarkedAttendanceData> attendanceDataClass= MarkedAttendanceData.class;


    public MarkedAttendanceData(SoapObject sobj)
    {

        this.setEmpNo(sobj.getProperty("EmpNo").toString());
        this.setPost_name(sobj.getProperty("Post_name").toString());
        this.setUserName(sobj.getProperty("UserName").toString());
        this.setInRemark(sobj.getProperty("inRemark").toString());
        this.setOutRemark(sobj.getProperty("outRemark").toString());
        this.setInPhoto(sobj.getProperty("inPhoto").toString());
        this.setOutPhoto(sobj.getProperty("outPhoto").toString());
        this.setIsInOffice(sobj.getProperty("IsInOffice").toString());
        this.setOutaddress(sobj.getProperty("outaddress").toString());
        this.setInAddress(sobj.getProperty("inAddress").toString());
        this.setOutTime(sobj.getProperty("outTime").toString());
        this.setInTime(sobj.getProperty("inTime").toString());
        this.setInMode(sobj.getProperty("InMode").toString());
    }
    public MarkedAttendanceData() {
        super();
    }

    public static Class<MarkedAttendanceData> AttendanceDataClass() {
        return attendanceDataClass;
    }

    public static void setAttendanceDataClass(Class<MarkedAttendanceData> attendanceDataClass1) {
        attendanceDataClass = attendanceDataClass1;
    }

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
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

    public String getInRemark() {
        return inRemark;
    }

    public void setInRemark(String inRemark) {
        this.inRemark = inRemark;
    }

    public String getOutRemark() {
        return outRemark;
    }

    public void setOutRemark(String outRemark) {
        this.outRemark = outRemark;
    }

    public String getInPhoto() {
        return inPhoto;
    }

    public void setInPhoto(String inPhoto) {
        this.inPhoto = inPhoto;
    }

    public String getOutPhoto() {
        return outPhoto;
    }

    public void setOutPhoto(String outPhoto) {
        this.outPhoto = outPhoto;
    }

    public String getIsInOffice() {
        return IsInOffice;
    }

    public void setIsInOffice(String isInOffice) {
        IsInOffice = isInOffice;
    }

    public String getOutaddress() {
        return outaddress;
    }

    public void setOutaddress(String outaddress) {
        this.outaddress = outaddress;
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getInMode() {
        return InMode;
    }

    public void setInMode(String inMode) {
        InMode = inMode;
    }
}
