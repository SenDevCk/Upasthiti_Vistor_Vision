package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

public class LeaveStatusData {

    private String LeaveType="";
    private String TotalLeave="";
    private String UsedLeave="";
    private String AvilabelLeave="";


    private static Class<LeaveStatusData> leaveStatusDataClass= LeaveStatusData.class;


    public LeaveStatusData(SoapObject sobj)
    {

        this.setLeaveType(sobj.getProperty("LeaveType").toString());
        this.setTotalLeave(sobj.getProperty("TotalLeave").toString());
        this.setUsedLeave(sobj.getProperty("UsedLeave").toString());
        this.setAvilabelLeave(sobj.getProperty("AvailLeave").toString());
    }
    public LeaveStatusData() {
        super();
    }

    public String getLeaveType() {
        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }

    public String getTotalLeave() {
        return TotalLeave;
    }

    public void setTotalLeave(String totalLeave) {
        TotalLeave = totalLeave;
    }

    public String getUsedLeave() {
        return UsedLeave;
    }

    public void setUsedLeave(String usedLeave) {
        UsedLeave = usedLeave;
    }

    public String getAvilabelLeave() {
        return AvilabelLeave;
    }

    public void setAvilabelLeave(String avilabelLeave) {
        AvilabelLeave = avilabelLeave;
    }

    public static Class<LeaveStatusData> getLeaveStatusDataClass() {
        return leaveStatusDataClass;
    }

    public static void setLeaveStatusDataClass(Class<LeaveStatusData> leaveStatusDataClass) {
        LeaveStatusData.leaveStatusDataClass = leaveStatusDataClass;
    }
}
