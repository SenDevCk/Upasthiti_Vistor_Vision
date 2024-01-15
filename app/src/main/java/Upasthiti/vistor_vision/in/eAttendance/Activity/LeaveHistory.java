package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.LeaveHistoryAdapter;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveHistoryData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class LeaveHistory extends AppCompatActivity {
    Toolbar toolbar_gd;
    LinearLayout ll_apply_leave,ll_leav_his;
    TextView tv_aply_leav,tv_leave_his;
    View view_apply,view_his;
    ListView list_report;
    SwipeRefreshLayout swipeRefreshLayout;
    LeaveHistoryAdapter leavhisreport;
    ArrayList<LeaveHistoryData> leavhisReportData;
    String EMPNo="",Name="",District="",Buniyad="",leaveType="",noOfDay="",remarks="",lstatus="",approvedate="",appRemarks="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_history);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            //toolbar_gd.setNavigationIcon(getResources().getDrawable(R.drawable.leftarrow));
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Leave History");
            toolbar_gd.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_gd.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_gd);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar_gd.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        ll_apply_leave=findViewById(R.id.ll_apply_leave);
        ll_leav_his=findViewById(R.id.ll_leav_his);
        tv_aply_leav=findViewById(R.id.tv_aply_leav);
        tv_leave_his=findViewById(R.id.tv_leave_his);
        view_apply=findViewById(R.id.view_apply);
        view_his=findViewById(R.id.view_his);
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        list_report = (ListView) findViewById(R.id.list_report);
        tv_aply_leav.setTextColor(getResources().getColor(R.color.white));
        tv_leave_his.setTextColor(getResources().getColor(R.color.orange));
        view_apply.setBackgroundColor(getResources().getColor(R.color.white));
        view_his.setBackgroundColor(getResources().getColor(R.color.orange));
         EMPNo= CommonPref.getUserDetails(LeaveHistory.this).getEmpNo();
        Name= CommonPref.getUserDetails(LeaveHistory.this).getUserName();
         District= CommonPref.getUserDetails(LeaveHistory.this).getDistName();
        leavhisReportData = new ArrayList<>();
        leavhisreport = new LeaveHistoryAdapter(LeaveHistory.this, leavhisReportData);
        list_report.setAdapter(leavhisreport);

            if(Utiilties.isOnline(LeaveHistory.this)) {
                new GetLeaveHistoryList().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
            else {
                Toast.makeText(LeaveHistory.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    if(Utiilties.isOnline(LeaveHistory.this)) {
                              new GetLeaveHistoryList().execute();
                            swipeRefreshLayout.setRefreshing(false);
                      }
                    else {
                        Toast.makeText(LeaveHistory.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
        });

        ll_apply_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LeaveHistory.this,Apply_Leave.class);
                startActivity(i);
            }
        });

        ll_leav_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LeaveHistory.this,LeaveHistory.class);
                startActivity(i);
            }
        });

        list_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                /*Name = leavhisReportData.get(position).getEmpName();
                District = leavhisReportData.get(position).getDistrict();
                Buniyad = leavhisReportData.get(position).getBuniyadCenter();*/
                leaveType = leavhisReportData.get(position).getLeaveType();
                noOfDay = leavhisReportData.get(position).getNoOfDay();
                remarks = leavhisReportData.get(position).getRemarks();
                lstatus = leavhisReportData.get(position).getLstatus();
                approvedate = leavhisReportData.get(position).getApprovedate();
                appRemarks = leavhisReportData.get(position).getAppRemarks();
                setUpDialog();
            }
        });

    }

    public class GetLeaveHistoryList extends AsyncTask<String, Void, ArrayList<LeaveHistoryData>> {

        private final ProgressDialog dialog = new ProgressDialog(LeaveHistory.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(LeaveHistory.this).create();
        public GetLeaveHistoryList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Report List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<LeaveHistoryData> doInBackground(String... params) {
            ArrayList<LeaveHistoryData> res = null;
            try {
                if (Utiilties.isOnline(LeaveHistory.this) && Utiilties.isConnected()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        res = WebServiceHelper.getLeaveHistory(EMPNo);
                    }else{
                        alertDialog.setMessage("Your device must have atleast Kitkat or Above Version");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }else{
                    Log.d("log", "No Internet Connection !");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<LeaveHistoryData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if(result != null) {
                    if (result.isEmpty()) {
                        Toast.makeText(LeaveHistory.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                    } else if (result != null) {
                        leavhisReportData = result;
                        leavhisreport.upDateEntries(leavhisReportData);
                    } else {
                        Toast.makeText(LeaveHistory.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(LeaveHistory.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();

                }

            }

        }
    }

    private void setUpDialog() {
        final Dialog setup_dialog = new Dialog(LeaveHistory.this);
        setup_dialog.setContentView(R.layout.attendance_rpt_details_dialog);
        setup_dialog.setCancelable(false);
        // set values for custom dialog components - text, image and button
        ImageView close_setup = (ImageView) setup_dialog.findViewById(R.id.close_setup);
        final TextView name = (TextView) setup_dialog.findViewById(R.id.tv_emp_name);
        final TextView Dist = (TextView) setup_dialog.findViewById(R.id.tv_emp_dist);
        final TextView buniyad = (TextView) setup_dialog.findViewById(R.id.tv_emp_buniyad);
        final LinearLayout LL_Name = (LinearLayout) setup_dialog.findViewById(R.id.ll_empname);
        final LinearLayout LL_Dist = (LinearLayout) setup_dialog.findViewById(R.id.ll_empdist);
        final LinearLayout LL_Buniyad = (LinearLayout) setup_dialog.findViewById(R.id.ll_emp_buniyad);
        final TextView LeaveType = (TextView) setup_dialog.findViewById(R.id.tv_name);
        final TextView NoOfDay = (TextView) setup_dialog.findViewById(R.id.tv_intime);
        final TextView Remarks = (TextView) setup_dialog.findViewById(R.id.tv_outtime);
        final TextView Lstatus = (TextView) setup_dialog.findViewById(R.id.tv_inrmk);
        final TextView Approvedate = (TextView) setup_dialog.findViewById(R.id.tv_outrmk);
        final TextView AppRemarks = (TextView) setup_dialog.findViewById(R.id.tv_inadd);
        //  final ImageView attimg =(ImageView) setup_dialog.findViewById(R.id.img1);

        LL_Name.setVisibility(View.VISIBLE);
        LL_Dist.setVisibility(View.VISIBLE);
        LL_Buniyad.setVisibility(View.VISIBLE);
        name.setText("Name :" + " " + Name);
        Dist.setText("District :" + " " + District);
        buniyad.setText("Buniyad Kendra :" + " " + Buniyad );
        LeaveType.setText("Leave Type :" + " " + leaveType);
        NoOfDay.setText("No Of Day :" + " " + noOfDay);
        Remarks.setText("Remarks :" + " " + remarks);
        Lstatus.setText("Leave Status :" + " " + lstatus);
        Approvedate.setText("Approve Date :" + " " + approvedate);
        AppRemarks.setText("Approval Remarks :" + " " + appRemarks);
        //  Picasso.with(DailyReportList.this).load("https://sams.sspmis.in" + AttImg).into(attimg);
        setAnimation(LeaveType,NoOfDay,Remarks,Lstatus,Approvedate,AppRemarks);

        close_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup_dialog.dismiss();
            }
        });

        setup_dialog.show();
    }
    private void setAnimation(View viewToAnimate, TextView LeaveType, TextView Remarks, TextView Lstatus, TextView Approvedate, TextView AppRemarks)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        Animation animation = AnimationUtils.loadAnimation(LeaveHistory.this, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
        LeaveType.startAnimation(animation);
        Remarks.startAnimation(animation);
        Lstatus.startAnimation(animation);
        Approvedate.startAnimation(animation);
        AppRemarks.startAnimation(animation);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}