package Upasthiti.vistor_vision.in.eAttendance.interfaces;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.AttendanceSummeryReportData;

public interface AttSummeryReportBinder {
    void bindAttReport(ArrayList<AttendanceSummeryReportData> attendanceSummeryReportDataArrayList);
    void cancleAttReportBinding();
}
