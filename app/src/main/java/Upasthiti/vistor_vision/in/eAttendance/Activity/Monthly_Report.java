package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.MonthlyReportAdapter;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.EmployeeListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.MonthlyReportData;
import Upasthiti.vistor_vision.in.eAttendance.entity.UserDetails;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Monthly_Report extends AppCompatActivity {
    Toolbar toolbar_report;
    ListView list_report;
    TextView txt_uname,text_report_not_found,txt_subdiv;
    Spinner spn_year;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> years = new ArrayList<String>();
    int thisYear = Calendar.getInstance().get(Calendar.YEAR);
    MonthlyReportAdapter adaptermreport;
    ArrayList<MonthlyReportData> monthlyReportData;
    UserDetails userDetails;
    String Year="",EMPNO="",EMPName="",EMP_Post="",Dist_Name="",Post_Code="",Blk_Name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);

        toolbar_report = (Toolbar) findViewById(R.id.toolbar_report);
        toolbar_report.setTitle(getResources().getString(R.string.app_name));
        toolbar_report.setSubtitle("Monthly Performance Report");
        setSupportActionBar(toolbar_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userDetails = CommonPref.getUserDetails(Monthly_Report.this);
        text_report_not_found =  findViewById(R.id.text_report_not_found);
        txt_uname =  findViewById(R.id.txt_uname);
        txt_subdiv =  findViewById(R.id.txt_subdiv);
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);

        list_report = (ListView) findViewById(R.id.list_report);

        Year=Utiilties.getCurrentYear();
        if( userDetails.getUserRole().equals("ADM")|| userDetails.getUserRole().equals("DSTADM"))
        {
            Intent intent = getIntent();
             Post_Code=intent.getStringExtra("postcode");
            EMP_Post=intent.getStringExtra("postname");
            EmployeeListData data =(EmployeeListData)getIntent().getSerializableExtra("empdata");
            Dist_Name=data.getDistrictName();
            Blk_Name=data.getBlockName();

            txt_uname.setText(data.getUserName()+" ("+ EMP_Post +" )");
            txt_subdiv.setText("District :"+" "+ Dist_Name+", "+" "+"Block :"+" "+Blk_Name);

            EMPNO=data.getEmpNo();
            EMPName=data.getUserName();
           // EMP_Post=data.getPost_name();

        }
        else {
            txt_uname.setText(userDetails.getUserName()+" (" + userDetails.getPost_name()+" )");
            txt_subdiv.setText("District :"+" "+ userDetails.getDistName()+", "+" "+"Block :"+" "+userDetails.getSubdivisionName());
            EMPNO=userDetails.getEmpNo().trim();
        }

        spn_year=findViewById(R.id.spn_year);
        loadYearSpinnerData();

        monthlyReportData = new ArrayList<>();
        adaptermreport = new MonthlyReportAdapter(Monthly_Report.this, monthlyReportData);
        list_report.setAdapter(adaptermreport);
        if(!Year.equals("")) {
            new GetMonthlyRptList().execute();
        }
        else {
            Toast.makeText(Monthly_Report.this, "Please select the year", Toast.LENGTH_SHORT).show();
        }

        list_report.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Intent i = new Intent(Monthly_Report.this,Report_Attendance_Summary.class);
                i.putExtra("EmpYear",Year);
                i.putExtra("UserId",EMPNO);
                i.putExtra("empName",EMPName);
                i.putExtra("empPost",EMP_Post);
                i.putExtra("distname",Dist_Name);
                i.putExtra("blkname",Blk_Name);
                i.putExtra("monthlyreportdata",monthlyReportData.get(position));
                startActivity(i);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    if(Utiilties.isOnline(Monthly_Report.this)) {
                        if(!Year.equals("")) {
                            new GetMonthlyRptList().execute();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            Toast.makeText(Monthly_Report.this, "Please select the year", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(Monthly_Report.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
        });

    }

    private void loadYearSpinnerData() {
        years.add("-- Select Year --");
        for (int i = 2015; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, years);
        spn_year.setAdapter(adapter);
        spn_year.setSelection(adapter.getPosition(Year));
        spn_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    Year= spn_year.getSelectedItem().toString().trim();
                    new GetMonthlyRptList().execute();
                    }
                else
                {
                    Year="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public class GetMonthlyRptList extends AsyncTask<String, Void, ArrayList<MonthlyReportData>> {

        private final ProgressDialog dialog = new ProgressDialog(Monthly_Report.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(Monthly_Report.this).create();
        public GetMonthlyRptList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Attendance List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<MonthlyReportData> doInBackground(String... params) {
            ArrayList<MonthlyReportData> res = WebServiceHelper.getMonthlyReportData(Year,EMPNO);
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<MonthlyReportData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if(result != null) {
                    if (result.isEmpty()) {
                        Toast.makeText(Monthly_Report.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                    } else if (result != null) {
                        monthlyReportData = result;
                        adaptermreport.upDateEntries(monthlyReportData);
                    } else {
                        Toast.makeText(Monthly_Report.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(Monthly_Report.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();

                }



            }

        }


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}