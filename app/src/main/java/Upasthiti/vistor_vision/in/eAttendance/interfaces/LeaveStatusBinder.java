package Upasthiti.vistor_vision.in.eAttendance.interfaces;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveStatusData;

public interface LeaveStatusBinder {
    void bindReport(ArrayList<LeaveStatusData> gravanceReportDataArrayList);
    void cancleReportBinding();
}
