package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class ValidateAttendance implements Serializable {
    private String _PhotoIn="";
    private String _PhotoOut="";
    private String Status="";
    private String OutTime1="";
    private String Intime1="";


    private static Class<ValidateAttendance> attendanceDataClass= ValidateAttendance.class;


    public ValidateAttendance(SoapObject sobj)
    {

        this.set_PhotoIn(sobj.getProperty("_PhotoIn").toString());
        this.set_PhotoOut(sobj.getProperty("_PhotoOut").toString());
        this.setStatus(sobj.getProperty("Status").toString());
        this.setOutTime1(sobj.getProperty("OutTime1").toString());
        this.setIntime1(sobj.getProperty("Intime1").toString());

    }
    public ValidateAttendance() {
        super();
    }

    public static Class<ValidateAttendance> attendanceDataClass() {
        return attendanceDataClass;
    }

    public static void setAttendanceDataClass(Class<ValidateAttendance> attendanceDataClass1) {
        attendanceDataClass = attendanceDataClass1;
    }

    public String get_PhotoIn() {
        return _PhotoIn;
    }

    public void set_PhotoIn(String _PhotoIn) {
        this._PhotoIn = _PhotoIn;
    }

    public String get_PhotoOut() {
        return _PhotoOut;
    }

    public void set_PhotoOut(String _PhotoOut) {
        this._PhotoOut = _PhotoOut;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOutTime1() {
        return OutTime1;
    }

    public void setOutTime1(String outTime1) {
        OutTime1 = outTime1;
    }

    public String getIntime1() {
        return Intime1;
    }

    public void setIntime1(String intime1) {
        Intime1 = intime1;
    }


}
