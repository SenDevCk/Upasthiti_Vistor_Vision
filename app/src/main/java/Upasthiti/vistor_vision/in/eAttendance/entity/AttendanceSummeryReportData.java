package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

public class AttendanceSummeryReportData {
    private String _Date="";
    private String _Status="";
    private String _InTime="";
    private String _OutTime="";

    private static Class<AttendanceSummeryReportData> Attendance_Summery_Class= AttendanceSummeryReportData.class;


    public AttendanceSummeryReportData(SoapObject sobj)
    {

        this.set_Date(sobj.getProperty("_PhotoIn").toString());
        this.set_Status(sobj.getProperty("Status").toString());
        this.set_InTime(sobj.getProperty("Intime1").toString());
        this.set_OutTime(sobj.getProperty("OutTime1").toString());

    }
    public AttendanceSummeryReportData() {
        super();
    }

    public static Class<AttendanceSummeryReportData> getAttendance_Summery_Class() {
        return Attendance_Summery_Class;
    }

    public static void setBlockData_CLASS(Class<AttendanceSummeryReportData> attendance_Summery_Class) {
        Attendance_Summery_Class = attendance_Summery_Class;
    }

    public String get_Date() {
        return _Date;
    }

    public void set_Date(String _Date) {
        this._Date = _Date;
    }

    public String get_Status() {
        return _Status;
    }

    public void set_Status(String _Status) {
        this._Status = _Status;
    }

    public String get_InTime() {
        return _InTime;
    }

    public void set_InTime(String _InTime) {
        this._InTime = _InTime;
    }

    public String get_OutTime() {
        return _OutTime;
    }

    public void set_OutTime(String _OutTime) {
        this._OutTime = _OutTime;
    }
}
