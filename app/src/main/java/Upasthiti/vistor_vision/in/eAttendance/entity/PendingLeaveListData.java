package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class PendingLeaveListData implements Serializable {
    private String EmpNo="";
    private String LeaveType="";
    private String LeaveId="";
    private String NoOfDay="";
    private String DateFrom="";
    private String DateTo="";
    private String Remarks="";
    private String ApplyDate="";
    private String Lstatus="";
    private String Approvedate="";
    private String AppRemarks="";
    private String District="";
    private String BuniyadCenter="";
    private String EmpName="";
    private String Post_name="";
    private String AppDoc="";
    private static Class<PendingLeaveListData> pendingLeaveListDataClass= PendingLeaveListData.class;


    public PendingLeaveListData(SoapObject sobj)
    {

        this.setEmpNo(sobj.getProperty("EmpNo").toString());
        this.setLeaveType(sobj.getProperty("LeaveType").toString());
        this.setLeaveId(sobj.getProperty("LeaveId").toString());
        this.setNoOfDay(sobj.getProperty("NoOfDay").toString());
        this.setDateFrom(sobj.getProperty("DateFrom").toString());
        this.setDateTo(sobj.getProperty("DateTo").toString());
        this.setRemarks(sobj.getProperty("Remarks").toString());
        this.setApplyDate(sobj.getProperty("ApplyDate").toString());
        this.setLstatus(sobj.getProperty("Lstatus").toString());
        this.setApprovedate(sobj.getProperty("Approvedate").toString());
        this.setAppRemarks(sobj.getProperty("AppRemarks").toString());
        this.setDistrict(sobj.getProperty("District").toString());
        this.setBuniyadCenter(sobj.getProperty("BuniyadCenter").toString());
        this.setEmpName(sobj.getProperty("EmpName").toString());
        this.setPost_name(sobj.getProperty("Post_name").toString());
        this.setAppDoc(sobj.getProperty("AppDoc").toString());

    }

    public static Class<PendingLeaveListData> getPendingLeaveListDataClass() {
        return pendingLeaveListDataClass;
    }

    public static void setPendingLeaveListDataClass(Class<PendingLeaveListData> pendingLeaveListDataClass1) {
        PendingLeaveListData.pendingLeaveListDataClass = pendingLeaveListDataClass1;
    }

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
    }

    public String getLeaveType() {
        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }

    public String getLeaveId() {
        return LeaveId;
    }

    public void setLeaveId(String leaveId) {
        LeaveId = leaveId;
    }

    public String getNoOfDay() {
        return NoOfDay;
    }

    public void setNoOfDay(String noOfDay) {
        NoOfDay = noOfDay;
    }

    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String dateFrom) {
        DateFrom = dateFrom;
    }

    public String getDateTo() {
        return DateTo;
    }

    public void setDateTo(String dateTo) {
        DateTo = dateTo;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String applyDate) {
        ApplyDate = applyDate;
    }

    public String getLstatus() {
        return Lstatus;
    }

    public void setLstatus(String lstatus) {
        Lstatus = lstatus;
    }

    public String getApprovedate() {
        return Approvedate;
    }

    public void setApprovedate(String approvedate) {
        Approvedate = approvedate;
    }

    public String getAppRemarks() {
        return AppRemarks;
    }

    public void setAppRemarks(String appRemarks) {
        AppRemarks = appRemarks;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getBuniyadCenter() {
        return BuniyadCenter;
    }

    public void setBuniyadCenter(String buniyadCenter) {
        BuniyadCenter = buniyadCenter;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getPost_name() {
        return Post_name;
    }

    public void setPost_name(String post_name) {
        Post_name = post_name;
    }

    public String getAppDoc() {
        return AppDoc;
    }

    public void setAppDoc(String appDoc) {
        AppDoc = appDoc;
    }
}
