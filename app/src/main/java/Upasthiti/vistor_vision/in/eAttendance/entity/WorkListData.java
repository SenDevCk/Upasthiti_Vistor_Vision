package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class WorkListData implements Serializable {
    private String WorkId="";
    private String EmpNo="";
    private String LogDate="";
    private String LogType="";
    private String ProjectName="";
    private String WorkType="";
    private String WTime="";
    private String LogDescription="";
    private String remarks="";
    private String status="";

    private static Class<WorkListData> workListDataClass= WorkListData.class;


    public WorkListData(SoapObject sobj)
    {

        this.setWorkId(sobj.getProperty("WorkId").toString());
        this.setEmpNo(sobj.getProperty("EmpNo").toString());
        this.setLogDate(sobj.getProperty("LogDate").toString());
        this.setLogType(sobj.getProperty("LogType").toString());
        this.setProjectName(sobj.getProperty("ProjectName").toString());
        this.setWorkType(sobj.getProperty("WorkType").toString());
        this.setWTime(sobj.getProperty("WTime").toString());
        this.setLogDescription(sobj.getProperty("LogDescription").toString());
        this.setRemarks(sobj.getProperty("remarks").toString());
        this.setStatus(sobj.getProperty("status").toString());

    }

    public static Class<WorkListData> getWorkListDataClass() {
        return workListDataClass;
    }

    public static void setWorkListDataClass(Class<WorkListData> workListDataClass1) {
        WorkListData.workListDataClass = workListDataClass1;
    }

    public WorkListData() {
        super();
    }

    public String getWorkId() {
        return WorkId;
    }

    public void setWorkId(String workId) {
        WorkId = workId;
    }

    public String getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(String empNo) {
        EmpNo = empNo;
    }

    public String getLogDate() {
        return LogDate;
    }

    public void setLogDate(String logDate) {
        LogDate = logDate;
    }

    public String getLogType() {
        return LogType;
    }

    public void setLogType(String logType) {
        LogType = logType;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getWorkType() {
        return WorkType;
    }

    public void setWorkType(String workType) {
        WorkType = workType;
    }

    public String getWTime() {
        return WTime;
    }

    public void setWTime(String WTime) {
        this.WTime = WTime;
    }

    public String getLogDescription() {
        return LogDescription;
    }

    public void setLogDescription(String logDescription) {
        LogDescription = logDescription;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
