package Upasthiti.vistor_vision.in.eAttendance.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.AttSummeryReportAdapter;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.AttSummeryReportService;
import Upasthiti.vistor_vision.in.eAttendance.entity.AttendanceSummeryReportData;
import Upasthiti.vistor_vision.in.eAttendance.entity.MonthlyReportData;
import Upasthiti.vistor_vision.in.eAttendance.entity.UserDetails;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkListData;
import Upasthiti.vistor_vision.in.eAttendance.interfaces.AttSummeryReportBinder;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Report_Attendance_Summary extends AppCompatActivity {
    Toolbar toolbar_report;
    ListView list_report;
    TextView text_report_not_found, txt_uname, txt_post,txt_mnth;
    LinearLayout ll_tag;
    ArrayList<AttendanceSummeryReportData> attendanceSummeryReportData;
    UserDetails userDetails;
    String Year = "", Month = "",UserId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_attendance_summary);

        toolbar_report = (Toolbar) findViewById(R.id.toolbar_report);
        toolbar_report.setTitle(getResources().getString(R.string.app_name));
        toolbar_report.setSubtitle("Performance Report");
        setSupportActionBar(toolbar_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userDetails = CommonPref.getUserDetails(Report_Attendance_Summary.this);
        MonthlyReportData data =(MonthlyReportData)getIntent().getSerializableExtra("monthlyreportdata");
        ll_tag = (LinearLayout) findViewById(R.id.ll_tag);
        text_report_not_found = (TextView) findViewById(R.id.text_report_not_found);
        txt_uname = (TextView) findViewById(R.id.txt_uname);
        txt_post = (TextView) findViewById(R.id.txt_post);
        txt_mnth = (TextView) findViewById(R.id.txt_mnth);

        list_report = (ListView) findViewById(R.id.list_report);

        if(userDetails.getUserRole().equals("DSTADM") || userDetails.getUserRole().equals("ADM"))
        {

            Intent intent = getIntent();
            UserId=intent.getStringExtra("UserId");
            String  EMPNAME=intent.getStringExtra("empName");
            String  EMP_POST=intent.getStringExtra("empPost");
            String  Dist_Name=intent.getStringExtra("distname");
            String  BLk_Name=intent.getStringExtra("blkname");
            txt_uname.setText(EMPNAME+"( "+EMP_POST+" )");
            txt_post.setText("District :"+" "+ Dist_Name +", "+" "+"Block :"+ " "+BLk_Name);
        }
        else{
            txt_uname.setText(userDetails.getUserName()+"( "+userDetails.getPost_name()+" )");
            txt_post.setText("District :"+" "+ userDetails.getDistName()+","+" "+"Block :"+" "+userDetails.getSubdivisionName());
            UserId=userDetails.getEmpNo().trim();
        }
        txt_mnth.setText("Month :"+" "+data.getMonth());
        Month=data.getMonthid();
        Intent intent = getIntent();
        Year=intent.getStringExtra("EmpYear");

    }

    @Override
    protected void onResume() {
        super.onResume();
            if (Utiilties.isOnline(Report_Attendance_Summary.this) && attendanceSummeryReportData == null) {
                AttSummeryReportService.bindAttSummeryReport(new AttSummeryReportBinder() {
                    @Override
                    public void bindAttReport(ArrayList<AttendanceSummeryReportData> attendanceSummeryReportDataArrayList) {
                        attendanceSummeryReportData = attendanceSummeryReportDataArrayList;
                        list_report.setVisibility(View.VISIBLE);
                        text_report_not_found.setVisibility(View.GONE);
                        list_report.setAdapter(new AttSummeryReportAdapter(Report_Attendance_Summary.this, attendanceSummeryReportData));
                    }

                    @Override
                    public void cancleAttReportBinding() {
                        attendanceSummeryReportData.clear();
                        list_report.invalidate();
                        list_report.setVisibility(View.GONE);
                        text_report_not_found.setVisibility(View.VISIBLE);
                    }

                });
                new AttSummeryReportService(Report_Attendance_Summary.this, true).execute(Year, Month, UserId);
            }
            else {
                Toast.makeText(Report_Attendance_Summary.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
