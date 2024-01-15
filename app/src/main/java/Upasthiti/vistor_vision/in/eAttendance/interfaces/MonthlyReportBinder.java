package Upasthiti.vistor_vision.in.eAttendance.interfaces;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.MonthlyReportData;

public interface MonthlyReportBinder {
    void bindMonthlyReport(ArrayList<MonthlyReportData> monthlyReportDataArrayList);
    void cancleMonthlyReportBinding();
}
