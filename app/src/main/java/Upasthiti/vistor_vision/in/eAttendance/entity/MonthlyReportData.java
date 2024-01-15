package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class MonthlyReportData implements Serializable {

    private String Monthid="";
    private String Month="";
    private String LastDay="";
    private String Present="";
    private String Absent="";
    private String Holidays="";

    private static Class<MonthlyReportData> monthlyReportDataClass= MonthlyReportData.class;


    public MonthlyReportData(SoapObject sobj)
    {

        this.setMonthid(sobj.getProperty("Monthid").toString());
        this.setMonth(sobj.getProperty("Month").toString());
        this.setLastDay(sobj.getProperty("LastDay").toString());
        this.setPresent(sobj.getProperty("Present").toString());
        this.setAbsent(sobj.getProperty("Absent").toString());
        this.setHolidays(sobj.getProperty("Holidays").toString());

    }
    public MonthlyReportData() {
        super();
    }

    public static Class<MonthlyReportData> monthlyReportDataClass() {
        return monthlyReportDataClass;
    }

    public static void setBlockData_CLASS(Class<MonthlyReportData> monthlyReportDataClass1) {
        monthlyReportDataClass = monthlyReportDataClass1;
    }

    public String getMonthid() {
        return Monthid;
    }

    public void setMonthid(String monthid) {
        Monthid = monthid;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getLastDay() {
        return LastDay;
    }

    public void setLastDay(String lastDay) {
        LastDay = lastDay;
    }

    public String getPresent() {
        return Present;
    }

    public void setPresent(String present) {
        Present = present;
    }

    public String getAbsent() {
        return Absent;
    }

    public void setAbsent(String absent) {
        Absent = absent;
    }

    public String getHolidays() {
        return Holidays;
    }

    public void setHolidays(String holidays) {
        Holidays = holidays;
    }
}
