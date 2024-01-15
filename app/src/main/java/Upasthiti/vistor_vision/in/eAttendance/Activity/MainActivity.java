package Upasthiti.vistor_vision.in.eAttendance.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import Upasthiti.vistor_vision.in.eAttendance.Adaptor.AppledLeaveAdapter;
import Upasthiti.vistor_vision.in.eAttendance.Adaptor.PendingLeaveListAdaptor;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.GlobalVariables;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.CheckInAttendance;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.CheckOutAttendance;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.LogoutService;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.SignUpService;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.AppliedLeaveData;
import Upasthiti.vistor_vision.in.eAttendance.entity.PendingLeaveListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.ValidateAttendance;
import Upasthiti.vistor_vision.in.eAttendance.entity.ViewEmpProfile;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    String latitude = "", longitude = "";
    MenuItem m1, m2, m3,m4,m5,m6;
    CheckBox check_box;
    TextInputLayout ly_remark;
    TextView tv_time, tv_date, tv_name_post, txt_Buniyad, text_header_name, text_header_Role, tv_mark_att, tv_In_Time, tv_out_time, tv_landmark,tv_emp_no;
    Button btn_chkIn, btn_chkout,btn_time_sheet;
    ImageView imgview ,imageViewheader;
    LinearLayout  ll_remark,ll_tk_camera,ll_leave_sts,ll_pending_leave_sts,ll_att_mrk,ll_time_sheet;
    TextInputEditText edt_remark;
    ScrollView parent_scroll;
    String error_msg = "", empId = "", postId = "", Address = "", mobile = "", Checked = "", Remark = "", Inoffice = "";
    String EMPNo = "", AttendanceStatus = "", OutTime = "", INime = "",DistCode="",BlkCode="";
    ArrayList<ValidateAttendance> ValidateAttendance_data;
    TelephonyManager tm;
    private static String imei;
    Bitmap im1;
    byte[] imageData1;
    String keyid = "";
    boolean edit = false;
    int ThumbnailSize = 500;
    private final static int CAMERA_PIC = 99;
    private boolean initse;
    LocationManager mlocManager = null;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    ListView list_report,list_pendingleave_Itm;
    SwipeRefreshLayout swipeRefreshLayout,pullToRefresh_pending_leave;
    AppledLeaveAdapter leavhisreport;
    ArrayList<AppliedLeaveData> leavhisReportData;
    String Name="",District="",Buniyad="",leaveType="",noOfDay="",remarks="",lstatus="",approvedate="",appRemarks="",Profile_Pic="",UserRole="",FromDate="",ToDate="";
    PendingLeaveListAdaptor pendingLeaveListAdaptor;
    ArrayList<PendingLeaveListData> pendingLeaveList;
     String TagLoginId,LeaveId,sts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        UserRole=CommonPref.getUserDetails(MainActivity.this).getUserRole();
        imageViewheader = (ImageView) header.findViewById(R.id.imageViewheader);
        check_box = findViewById(R.id.chk_inoffice);
        ly_remark = findViewById(R.id.ly_remark);
        tv_time = findViewById(R.id.tv_time);
        tv_date = findViewById(R.id.tv_date);
        tv_emp_no = findViewById(R.id.tv_emp_no);
        txt_Buniyad = findViewById(R.id.txt_Buniyad);
        tv_name_post = findViewById(R.id.tv_name_post);
        btn_chkIn = findViewById(R.id.btn_chkIn);
        btn_chkout = findViewById(R.id.btn_chkout);
        btn_time_sheet = findViewById(R.id.btn_time_sheet);
        edt_remark = findViewById(R.id.edt_remark);
        tv_landmark = findViewById(R.id.tv_landmark);
        ll_remark = findViewById(R.id.ll_remark);
        ll_tk_camera = findViewById(R.id.ll_tk_camera);
        ll_leave_sts = findViewById(R.id.ll_leave_sts);
        ll_pending_leave_sts = findViewById(R.id.ll_pending_leave_sts);
        ll_att_mrk = findViewById(R.id.ll_att_mrk);
        ll_time_sheet = findViewById(R.id.ll_time_sheet);
        parent_scroll = findViewById(R.id.parent_scroll_Main);
        imgview = findViewById(R.id.imgview);
        tv_mark_att = findViewById(R.id.tv_mark_att);
        tv_In_Time = findViewById(R.id.tv_In_Time);
        tv_out_time = findViewById(R.id.tv_out_time);
        text_header_name = (TextView) header.findViewById(R.id.tv_user_name);
        text_header_Role = (TextView) header.findViewById(R.id.tv_join_date);

        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        pullToRefresh_pending_leave = findViewById(R.id.pullToRefresh_pending_leave);
        list_report = (ListView) findViewById(R.id.list_leav_report);
        list_pendingleave_Itm = (ListView) findViewById(R.id.list_pendingleave_Itm);

        pendingLeaveList = new ArrayList<>();
        pendingLeaveListAdaptor = new PendingLeaveListAdaptor(MainActivity.this, pendingLeaveList);
        list_pendingleave_Itm.setAdapter(pendingLeaveListAdaptor);

        list_pendingleave_Itm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Intent i = new Intent(MainActivity.this,LeaveApprove.class);
                i.putExtra("leavedata",pendingLeaveList.get(position));
                i.putExtra("leavests","P");
                startActivity(i);
            }
        });

        empId = (CommonPref.getUserDetails(MainActivity.this).getEmpNo());
        EMPNo = CommonPref.getUserDetails(MainActivity.this).getEmpNo();
        postId = (CommonPref.getUserDetails(MainActivity.this).getPostCode());
        mobile = (CommonPref.getUserDetails(MainActivity.this).getMobileNumber());
        DistCode = (CommonPref.getUserDetails(MainActivity.this).getDistrictCode());
        BlkCode = (CommonPref.getUserDetails(MainActivity.this).getSubDivCode());



        tv_date.setText(Utiilties.getcurrentDate());
        Thread myThread = null;
        Runnable runnable = new CountDownRunner();
        myThread = new Thread(runnable);
        myThread.start();
        readPhoneState();

        Menu menu = navigationView.getMenu();
        m1 = menu.findItem(R.id.m1);
        m2 = menu.findItem(R.id.m2);
        m3 = menu.findItem(R.id.m3);
        m4 = menu.findItem(R.id.m4);
        m5 = menu.findItem(R.id.m5);
        m6 = menu.findItem(R.id.m6);

        if(UserRole.equals("DSTADM"))
        {
            tv_name_post.setText(CommonPref.getUserDetails(MainActivity.this).getUserName() + "(" + " " + UserRole + " " + ")");
            txt_Buniyad.setText("District"  + "( " +CommonPref.getUserDetails(MainActivity.this).getDistName() +" )");
            text_header_name.setText("District Admin");
            text_header_Role.setText(CommonPref.getUserDetails(MainActivity.this).getDistName());
            parent_scroll.setVisibility(View.GONE);
            tv_emp_no.setVisibility(View.GONE);
            ll_leave_sts.setVisibility(View.VISIBLE);
            ll_pending_leave_sts.setVisibility(View.GONE);
            m2.setVisible(true);
            m1.setVisible(false);
            m3.setVisible(false);
            m4.setVisible(false);
            m5.setVisible(false);
            m6.setVisible(false);
            Profile_Pic="";
            if(Utiilties.isOnline(MainActivity.this)) {
                new GetLeaveHistoryList().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
            else {
                Toast.makeText(MainActivity.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if(Utiilties.isOnline(MainActivity.this)  ) {
                        new GetLeaveHistoryList().execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

      else if(UserRole.equals("ADM"))
        {
            m4.setVisible(true);
            m3.setVisible(false);
            m1.setVisible(false);
            m2.setVisible(false);
            m5.setVisible(false);
            m6.setVisible(false);
            TagLoginId=CommonPref.getUserDetails(MainActivity.this).getUserId();
            tv_name_post.setText(CommonPref.getUserDetails(MainActivity.this).getUserName() + "(" + " " + UserRole + " " + ")");
            text_header_name.setText(CommonPref.getUserDetails(MainActivity.this).getUserName());
            text_header_Role.setText(CommonPref.getUserDetails(MainActivity.this).getUserRole());
            imageViewheader.setImageResource(R.drawable.man);
            parent_scroll.setVisibility(View.GONE);
            ll_leave_sts.setVisibility(View.GONE);
            ll_pending_leave_sts.setVisibility(View.VISIBLE);
            LeaveId="0";
            sts="0";
            if(Utiilties.isOnline(MainActivity.this)) {
               new GetPendingLeaveList().execute();
                pullToRefresh_pending_leave.setRefreshing(false);
            }
            else {
                Toast.makeText(MainActivity.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }
            pullToRefresh_pending_leave.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if(Utiilties.isOnline(MainActivity.this)) {
                        new GetPendingLeaveList().execute();
                        pullToRefresh_pending_leave.setRefreshing(false);

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        else {
            tv_emp_no.setVisibility(View.VISIBLE);
            ll_att_mrk.setVisibility(View.VISIBLE);
            ll_time_sheet.setVisibility(View.GONE);
            tv_name_post.setText(CommonPref.getUserDetails(MainActivity.this).getUserName() + "(" + " " + CommonPref.getUserDetails(MainActivity.this).getPost_name() + " " + ")");
            txt_Buniyad.setText( "District :"+ " " + CommonPref.getUserDetails(MainActivity.this).getDistName() );
            tv_emp_no.setText("EMP No"+" "+"("+ " " + CommonPref.getUserDetails(MainActivity.this).getEmpNo() + " " + ")");
            text_header_name.setText(CommonPref.getUserDetails(MainActivity.this).getUserName());
            text_header_Role.setText(CommonPref.getUserDetails(MainActivity.this).getPost_name());
            parent_scroll.setVisibility(View.VISIBLE);
            ll_leave_sts.setVisibility(View.GONE);
            ll_pending_leave_sts.setVisibility(View.GONE);
            m2.setVisible(false);
            m3.setVisible(false);
            m4.setVisible(false);
            m5.setVisible(false);
            m6.setVisible(false);
            m1.setVisible(true);
           Profile_Pic = CommonPref.getProfilePhoto(MainActivity.this).getInPhoto();
           if(Profile_Pic.equals(""))
           {
               imageViewheader.setImageResource(R.drawable.man);
           }
           else {
               new GetEmpProfileData().execute();
           }

           if (Utiilties.isOnline(MainActivity.this) ){
               new GetValidateAttendaceData().execute();
               swipeRefreshLayout.setRefreshing(false);
           }
               else{
               Toast.makeText(this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
           }


        }

        imageViewheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserRole.equals("EMP")) {
                    Intent intent = new Intent(MainActivity.this, Emp_Profile.class);
                    startActivity(intent);
                }
                else
                    {

                }
            }
        });

        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    ly_remark.setVisibility(View.GONE);
                    Checked = "Y";
                } else {
                    ly_remark.setVisibility(View.VISIBLE);
                    Checked = "N";
                }
            }
        });

        ll_tk_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utiilties.isGPSEnabled(MainActivity.this)) {
                    Utiilties.displayPromptForEnablingGPS(MainActivity.this);
                } else {
                    Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
                    iCamera.putExtra("KEY_PIC", "1");
                    iCamera.putExtra("frntcam", "Y");
                    startActivityForResult(iCamera, CAMERA_PIC);
                    ll_remark.setVisibility(View.VISIBLE);
                }

            }
        });

        //  btn_chkIn.setEnabled(false);

        btn_chkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (Utiilties.isOnline(MainActivity.this)) {
                        Remark = edt_remark.getText().toString().trim();
                        if (AttendanceStatus.equalsIgnoreCase("IN")) {
                            Toast.makeText(MainActivity.this, "Already checked In !", Toast.LENGTH_SHORT).show();
                        }
                         else if (AttendanceStatus.equalsIgnoreCase("P") || AttendanceStatus.trim().equals("")) {
                            if (im1 != null) {
                                if (Checked.equals("N")) {
                                    if (validate()) {
                                        Inoffice = "1";
                                        if (Address.trim().equals("")) {
                                            Toast.makeText(MainActivity.this, "your address is null ! please take photo again", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            new CheckInAttendance(MainActivity.this).execute(empId, postId, Remark, latitude, longitude, Address, Utiilties.BitArrayToString(imageData1), imei, Inoffice,DistCode,BlkCode);
                                            ll_remark.setVisibility(View.GONE);
                                        }
                                    }

                                } else {
                                    Remark = "In Office";
                                    Inoffice = "0";
                                    if (Address.trim().equals("")) {
                                        Toast.makeText(MainActivity.this, "your address is null ! please take photo again", Toast.LENGTH_SHORT).show();

                                    } else {
                                        new CheckInAttendance(MainActivity.this).execute(empId, postId, Remark, latitude, longitude, Address, Utiilties.BitArrayToString(imageData1), imei, Inoffice,DistCode,BlkCode);
                                        ll_remark.setVisibility(View.GONE);
                                    }
                                }

                            }
                            else {
                                Toast.makeText(MainActivity.this, "Please Click Photo", Toast.LENGTH_SHORT).show();
                            }
                        }

                        else if (AttendanceStatus.equalsIgnoreCase("OUT")) {
                            Toast.makeText(MainActivity.this, "Today your attendance is completed !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "please check internet connection !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Turn on Internet connection  !", Toast.LENGTH_SHORT).show();
                    }
            }

        });
        btn_chkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (Utiilties.isOnline(MainActivity.this)) {
                        Remark = edt_remark.getText().toString().trim();
                        if (AttendanceStatus.equalsIgnoreCase("IN")) {
                            if (im1 != null) {
                                if (Checked.equals("N")) {
                                    if (validate()) {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("Please Confirm !")
                                                .setMessage("Are you sure you want to checkout?")
                                                .setNegativeButton(android.R.string.no, null)
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (Address.trim().equals("")) {
                                                            Toast.makeText(MainActivity.this, "your address is null ! please take photo again", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            new CheckOutAttendance(MainActivity.this).execute(empId, Remark, latitude, longitude, Address, Utiilties.BitArrayToString(imageData1), imei);
                                                            ll_remark.setVisibility(View.GONE);
                                                        }
                                                    }
                                                }).create().show();
                                    }
                                } else {
                                    Remark = "In Office";
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("Please Confirm !")
                                            .setMessage("Are you sure you want to checkout?")
                                            .setNegativeButton(android.R.string.no, null)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (Address.trim().equals("")) {
                                                        Toast.makeText(MainActivity.this, "your address is null ! please take photo again", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else {
                                                        new CheckOutAttendance(MainActivity.this).execute(empId, Remark, latitude, longitude, Address, Utiilties.BitArrayToString(imageData1), imei);
                                                        ll_remark.setVisibility(View.GONE);

                                                    }
                                                }
                                            }).create().show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Please Click Photo", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if (AttendanceStatus.equalsIgnoreCase("OUT")) {
                            Toast.makeText(MainActivity.this, "Already checked OUT !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please  checked In !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }

        });


        leavhisReportData = new ArrayList<>();
        leavhisreport = new AppledLeaveAdapter(MainActivity.this, leavhisReportData);
        list_report.setAdapter(leavhisreport);

        list_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Name = leavhisReportData.get(position).getEmpName();
                District = leavhisReportData.get(position).getDistrict();
                Buniyad = leavhisReportData.get(position).getBuniyadCenter();
                leaveType = leavhisReportData.get(position).getLeaveType();
                FromDate = leavhisReportData.get(position).getDateFrom();
                ToDate = leavhisReportData.get(position).getDateTo();
                noOfDay = leavhisReportData.get(position).getNoOfDay();
                remarks = leavhisReportData.get(position).getRemarks();
                lstatus = leavhisReportData.get(position).getLstatus();
                approvedate = leavhisReportData.get(position).getApprovedate();
                appRemarks = leavhisReportData.get(position).getAppRemarks();
                setUpDialog();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            if(Utiilties.isOnline(MainActivity.this))
            {
                new LogoutService(MainActivity.this).execute(CommonPref.getUserDetails(MainActivity.this).getUserId());
            }
            else {
                Toast.makeText(MainActivity.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tdy_rpt) {
            Intent intent = new Intent(MainActivity.this, DailyReportList.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_mnth_rpt) {
            Intent intent = new Intent(MainActivity.this, Monthly_Report.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_aply_leave) {
            Intent intent = new Intent(MainActivity.this, Apply_Leave.class);
            startActivity(intent);
        }else if (id == R.id.nav_time_sheet) {
            Intent intent = new Intent(MainActivity.this, TimeSheet.class);
            startActivity(intent);
        }else if (id == R.id.nav_time_sheet_hq) {
            Intent intent = new Intent(MainActivity.this, TimeSheet.class);
            startActivity(intent);
        } else if (id == R.id.nav_reg) {
            Intent intent = new Intent(MainActivity.this, registeration.class);
            startActivity(intent);
        } else if (id == R.id.nav_tdy_att) {
            Intent intent = new Intent(MainActivity.this, DailyReportList.class);
            startActivity(intent);
        } else if (id == R.id.nav_emply_list) {
            Intent i =new Intent(MainActivity.this,Employee_List.class);
            startActivity(i);
        }  else if (id == R.id.nav_reg_adm) {
            Intent intent = new Intent(MainActivity.this, registeration.class);
            startActivity(intent);

        } else if (id == R.id.nav_tdy_att_adm) {
            Intent intent = new Intent(MainActivity.this, DailyReportList.class);
            startActivity(intent);
        }else if (id == R.id.nav_update_lat_long) {
            Intent intent = new Intent(MainActivity.this, Update_Lat_Long.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_emp_list) {
            Intent i =new Intent(MainActivity.this,Employee_List.class);
            startActivity(i);
        }  else if (id == R.id.nav_getleave_pm_spm) {
            Intent i =new Intent(MainActivity.this,Employee_List.class);
            startActivity(i);
        }  else if (id == R.id.nav_aprvedleave_pm_spm) {
            Intent i =new Intent(MainActivity.this,Approved_Leave_List.class);
            startActivity(i);
        }
        else if (id == R.id.nav_pass_change) {
            Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_holiday) {
            Intent intent = new Intent(MainActivity.this, Holidays.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {

                    Calendar cal = Calendar.getInstance();
                    int hours = cal.get(Calendar.HOUR);
                    int minutes = cal.get(Calendar.MINUTE);
                    int seconds = cal.get(Calendar.SECOND);
                    String curTime = hours + ":" + minutes + ":" + seconds;
                    tv_time.setText(curTime);
                } catch (Exception e) {
                }
            }
        });
    }


    class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            im1 = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            imgview.setScaleType(ImageView.ScaleType.FIT_XY);
                            imgview.setImageBitmap(Utiilties.GenerateThumbnail(im1, ThumbnailSize, ThumbnailSize));
                            //  viewIMG1.setVisibility(View.VISIBLE);
                            imageData1 = imgData;
                            latitude = data.getStringExtra("Lat");
                            longitude = data.getStringExtra("Lng");
                            getLocationName(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            break;
                    }
                }
        }

    }

    private boolean validate() {
        boolean validated = false;
        if (Remark.equals("")) {
            Toast.makeText(this, "Enter Remark !", Toast.LENGTH_SHORT).show();
            error_msg = "Enter Remark !";
            edt_remark.setError("Enter Remark !");
            validated = false;
        }
        else {
            validated = true;
        }
        return validated;
    }

    @SuppressLint("MissingPermission")
    public void readPhoneState() {

        try {
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) ;
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                imei = tm.getDeviceId();
            } else {
                imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class GetValidateAttendaceData extends AsyncTask<String, Void, ArrayList<ValidateAttendance>> {

        public GetValidateAttendaceData() {
        }


        @Override
        protected ArrayList<ValidateAttendance> doInBackground(String... params) {

            ArrayList<ValidateAttendance> res = WebServiceHelper.ValidateAttendanceDataLoader(EMPNo);
            return res;

        }

        @Override
        protected void onPostExecute(ArrayList<ValidateAttendance> result) {
            if(result==null){
                Toast.makeText(MainActivity.this, "Server Problem", Toast.LENGTH_SHORT).show();
            }

          else if (result.size() > 0) {
                ValidateAttendance_data = result;
                AttendanceStatus = (result.get(0).getStatus());
                OutTime = (result.get(0).getOutTime1());
                INime = (result.get(0).getIntime1());

                if (AttendanceStatus.equalsIgnoreCase("IN")) {
                    tv_mark_att.setText("Successfully Marked Attendance !");
                    tv_In_Time.setText(INime);
                    tv_out_time.setText("Pending Checked Out");
                } else if (AttendanceStatus.equalsIgnoreCase("OUT")) {
                    tv_mark_att.setText("Successfully Marked Attendance !");
                    tv_In_Time.setText(INime);
                    tv_out_time.setText(OutTime);
                    ll_tk_camera.setEnabled(false);
                }
                else {
                    tv_mark_att.setText(getResources().getString(R.string.mark));
                    tv_In_Time.setText("Pending Checked IN");
                    tv_out_time.setText("Pending Checked Out");
                }

            } else if (result.size() == 0) {
                AttendanceStatus = "";
                OutTime = "";
                INime = "";
            } else {
                Toast.makeText(MainActivity.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLocationName(double latitude1, double longitude1) {
        try {

            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<android.location.Address> addresses = geo.getFromLocation(latitude1, longitude1, 1);
            if (addresses.isEmpty()) {
                tv_landmark.setText("Waiting for Location...");
            } else {
                if (addresses.size() > 0) {

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    tv_landmark.setText(new StringBuilder().append("Address : ").append(address + ",").append(city + ",").append(state + ",").append(country + ",").append(postalCode + ",").append(knownName));
                   Address = address + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class GetLeaveHistoryList extends AsyncTask<String, Void, ArrayList<AppliedLeaveData>> {

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        public GetLeaveHistoryList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Report List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<AppliedLeaveData> doInBackground(String... params) {
            ArrayList<AppliedLeaveData> res = WebServiceHelper.getappliedLeaves(DistCode);
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<AppliedLeaveData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if(result != null) {
                    if (result.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                    } else if (result != null) {
                        leavhisReportData = result;
                        leavhisreport.upDateEntries(leavhisReportData);
                    } else {
                        Toast.makeText(MainActivity.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();

                }

            }

        }
    }

    private void setUpDialog() {
        final Dialog setup_dialog = new Dialog(MainActivity.this);
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
        final TextView fromDate = (TextView) setup_dialog.findViewById(R.id.tv_frmdate);
        final TextView toDate = (TextView) setup_dialog.findViewById(R.id.tv_to_date);
        final TextView NoOfDay = (TextView) setup_dialog.findViewById(R.id.tv_intime);
        final TextView Remarks = (TextView) setup_dialog.findViewById(R.id.tv_outtime);
        final TextView Lstatus = (TextView) setup_dialog.findViewById(R.id.tv_inrmk);
        final TextView Approvedate = (TextView) setup_dialog.findViewById(R.id.tv_outrmk);
        final TextView AppRemarks = (TextView) setup_dialog.findViewById(R.id.tv_inadd);
          final LinearLayout todate =(LinearLayout) setup_dialog.findViewById(R.id.ll_to_date);
          final LinearLayout frmdate =(LinearLayout) setup_dialog.findViewById(R.id.ll_frm_date);
          todate.setVisibility(View.VISIBLE);
        frmdate.setVisibility(View.VISIBLE);
        LL_Name.setVisibility(View.VISIBLE);
        LL_Dist.setVisibility(View.VISIBLE);
        LL_Buniyad.setVisibility(View.VISIBLE);
        name.setText("Name :" + " " + Name);
        Dist.setText("District :" + " " + District);
        buniyad.setText("Buniyad Kendra :" + " " + Buniyad );
        LeaveType.setText("Leave Type :" + " " + leaveType);
        fromDate.setText("from Date :" + " " + FromDate);
        toDate.setText("To Date :" + " " + ToDate);
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

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
        LeaveType.startAnimation(animation);
        Remarks.startAnimation(animation);
        Lstatus.startAnimation(animation);
        Approvedate.startAnimation(animation);
        AppRemarks.startAnimation(animation);
    }





    public class GetPendingLeaveList extends AsyncTask<String, Void, ArrayList<PendingLeaveListData>> {

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        public GetPendingLeaveList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Leave List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PendingLeaveListData> doInBackground(String... params) {
            ArrayList<PendingLeaveListData> res = WebServiceHelper.pendingleaveListLoader(TagLoginId, LeaveId,sts);
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<PendingLeaveListData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if (result.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                } else if (result != null) {
                    pendingLeaveList = result;
                    pendingLeaveListAdaptor.upDateEntries(pendingLeaveList);
                } else {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                }


            }

        }


    }


    @Override
    public void onResume() {
        super.onResume();
        if(UserRole.equals("EMP")) {
            if (Utiilties.isOnline(MainActivity.this)) {
                new GetValidateAttendaceData().execute();
                 if(AttendanceStatus.trim().equals(""))
                {
                    tv_mark_att.setText(getResources().getString(R.string.mark));
                    tv_In_Time.setText("Pending Checked IN");
                    tv_out_time.setText("Pending Checked Out");
                }
            } else {
                Toast.makeText(this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }

        }

       /* if(UserRole.equals("ADM"))
        {
            if(Utiilties.isOnline(MainActivity.this)) {
                new GetPendingLeaveList().execute();
                pullToRefresh_pending_leave.setRefreshing(false);
            }
            else {
                Toast.makeText(MainActivity.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }
        }

        else {

        }*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(CommonPref.getUserDetails(MainActivity.this).getUserRole().equals("EMP")) {
            if (Utiilties.isOnline(MainActivity.this)) {
                new GetValidateAttendaceData().execute();
                 if(AttendanceStatus.trim().equals(""))
                {
                    tv_mark_att.setText(getResources().getString(R.string.mark));
                    tv_In_Time.setText("Pending Checked IN");
                    tv_out_time.setText("Pending Checked Out");
                }
            } else {
                Toast.makeText(this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }

        }
        else {

        }
    }


    @SuppressLint("StaticFieldLeak")
    public class GetEmpProfileData extends AsyncTask<String, Void, ArrayList<ViewEmpProfile>> {

        public GetEmpProfileData() {
        }


        @Override
        protected ArrayList<ViewEmpProfile> doInBackground(String... params) {

            ArrayList<ViewEmpProfile> res;
            res = WebServiceHelper.EmpProfileLoder(EMPNo);
            return res;

        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(ArrayList<ViewEmpProfile> result) {
            if (result.size() > 0) {
                Picasso.with(MainActivity.this).load("http://www.bedmc.in/" + Profile_Pic).error(R.drawable.man).into(imageViewheader);

            }

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                }).create().show();

    }
}
