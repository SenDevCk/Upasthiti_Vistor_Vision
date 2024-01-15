package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class HolidaysData implements Serializable {
    private String Day="";
    private String Date="";
    private String Remarks="";


    private static Class<HolidaysData> holidaysDataClass= HolidaysData.class;


    public HolidaysData(SoapObject sobj)
    {

        this.setDay(sobj.getProperty("Day").toString());
        this.setDate(sobj.getProperty("Date").toString());
        this.setRemarks(sobj.getProperty("Remarks").toString());

    }
    public HolidaysData() {
        super();
    }

    public static Class<HolidaysData> getHolidaysDataClass() {
        return holidaysDataClass;
    }

    public static void setAttendanceDataClass(Class<HolidaysData> holidaysDataClass1) {
        holidaysDataClass = holidaysDataClass1;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
