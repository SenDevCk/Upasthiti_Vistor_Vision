package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.TimeSheetService;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.LogtypeData;
import Upasthiti.vistor_vision.in.eAttendance.entity.ProjectData;
import Upasthiti.vistor_vision.in.eAttendance.entity.TimeData;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkTypeData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class TimeSheet extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar_gd;
    ProgressDialog pd;
    TextView tv_date;
    EditText edt_log_des;
    Spinner spn_logtype,spn_poroject,spn_work_type,spn_time;
    ImageView btncal_date;
    Button btn_save,btn_cancel;
    DatePickerDialog datedialog;
    String error_msg="",Empid="",Logid="",Logtpe="",TimeCode="",Time="",ProjectCode="",ProjectName="",WorkCode="",Worktype="";
    private Calendar cal;
    private int mYear, mMonth,tomDay;
    ArrayList<LogtypeData> LogtypeList = new ArrayList<>();
    ArrayList<ProjectData> ProjectList = new ArrayList<>();
    ArrayList<WorkTypeData> WorkTypeList = new ArrayList<>();
    ArrayList<TimeData> TimeList = new ArrayList<>();
    boolean isLogtype=false,isTime=false,isProject=false,iswork=false;
    View scroll_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            //toolbar_gd.setNavigationIcon(getResources().getDrawable(R.drawable.leftarrow));
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Fill Full TimeSheet");
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

        tv_date=findViewById(R.id.tv_date);
        btncal_date=findViewById(R.id.btncal_date);
        spn_logtype=findViewById(R.id.spn_logtype);
        spn_poroject=findViewById(R.id.spn_poroject);
        spn_work_type=findViewById(R.id.spn_work_type);
        spn_time=findViewById(R.id.spn_time);
        btn_save=findViewById(R.id.btn_save);
        btn_cancel=findViewById(R.id.btn_cancel);
        edt_log_des=findViewById(R.id.edt_log_des);
        Empid= CommonPref.getUserDetails(TimeSheet.this).getEmpNo();
        cal = Calendar.getInstance();
        btncal_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setIndeterminateDrawable(getResources().getDrawable((R.drawable.my_progress)));
        pd.setMessage("Loading...");

        if (Utiilties.isOnline(TimeSheet.this)) {
            new LoadProjectData().execute("");
        } else {
            Toast.makeText(TimeSheet.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
            ProjectCode = "";
            ProjectName = "";
        }

        if(LogtypeList.size()>0 ) {
         //   loadLogTypeSPinner();
        }
        else {
            isLogtype = false;
            if (Utiilties.isOnline(TimeSheet.this)) {
         //   new LoadLogTypeData().execute("");
            } else {
                Toast.makeText(TimeSheet.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                Logid = "";
                Logtpe="";
            }
        }
        btn_save.setOnClickListener(this);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class LoadLogTypeData extends AsyncTask<String, Void, ArrayList<LogtypeData>> {
        ArrayList<LogtypeData> logtype =new  ArrayList<LogtypeData>();
        private final ProgressDialog dialog = new ProgressDialog(
                TimeSheet.this);

        LoadLogTypeData() {

        }
        @Override
        protected void onPreExecute() {
            pd.show();
        }
        @Override
        protected ArrayList<LogtypeData> doInBackground(String... param) {

            this.logtype= WebServiceHelper.getLogtype();

            return this.logtype;
        }

        @Override
        protected void onPostExecute(ArrayList<LogtypeData> result) {
            if (pd.isShowing()) {
                pd.dismiss();

            }

            if(result!=null)
            {
                isLogtype=true;
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(TimeSheet.this);
                    long i=placeData.saveLogType(result);
                    if(i>0)
                    {
                    //   loadLogTypeSPinner();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    public void loadLogTypeSPinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        LogtypeList = dataBaseHelper.getlogtype();
        ArrayList<String> plist = new ArrayList<>();
        if (LogtypeList.size() > 0) {
            plist.add("-- Choose Log Type --");
        }
        for (int i = 0; i < LogtypeList.size(); i++) {
            plist.add(LogtypeList.get(i).getLogTyp());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, plist);
        spn_logtype.setAdapter(spinnerAdapter);
        if (ProjectList.size()>0 ){
        //    loadProjectSPinner();
        }
        else {
            isProject = false;
            if (Utiilties.isOnline(TimeSheet.this)) {
                new LoadProjectData().execute("");
            } else {
                Toast.makeText(TimeSheet.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                ProjectCode = "";
                ProjectName = "";
            }
        }
        spn_logtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (arg2 > 0) {
                    LogtypeData logtypedata = LogtypeList.get(arg2 - 1);
                    Logid = logtypedata.getLogCode();
                    Logtpe = logtypedata.getLogTyp();

                }
                else {
                    Logid="";
                    Logtpe="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadProjectData extends AsyncTask<String, Void, ArrayList<ProjectData>> {
        ArrayList<ProjectData> projectData =new  ArrayList<ProjectData>();
        private final ProgressDialog dialog = new ProgressDialog(
                TimeSheet.this);
        LoadProjectData() {
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected ArrayList<ProjectData> doInBackground(String... param) {

            this.projectData= WebServiceHelper.getProject();

            return this.projectData;
        }

        @Override
        protected void onPostExecute(ArrayList<ProjectData> result) {
            if (pd.isShowing()) {
                pd.dismiss();

            }

            if(result!=null)
            {
                isProject=true;
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(TimeSheet.this);
                    long i=placeData.saveProject(result);
                    if(i>0)
                    {
                        loadProjectSPinner();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    public void loadProjectSPinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        ProjectList = dataBaseHelper.getproject();
        ArrayList<String> projectlist = new ArrayList<>();
        if (ProjectList.size() > 0) {
            projectlist.add("-- Choose Project --");
        }
        for (int i = 0; i < ProjectList.size(); i++) {
            projectlist.add(ProjectList.get(i).getProjectName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, projectlist);
        spn_poroject.setAdapter(spinnerAdapter);
        if (TimeList.size()>0 ){
            loadTimeSPinner();
        }
        else {
            isTime = false;
            if (Utiilties.isOnline(TimeSheet.this)) {
                new LoadTimeData().execute("");
            } else {
                Toast.makeText(TimeSheet.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                TimeCode = "";
                Time = "";
            }
        }
        spn_poroject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (arg2 > 0) {
                    ProjectData projectData = ProjectList.get(arg2 - 1);
                    ProjectCode = projectData.getProjectCode();
                    ProjectName = projectData.getProjectName();


                }
                else {
                    ProjectCode="";
                    ProjectName="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private class LoadWorkTypeData extends AsyncTask<String, Void, ArrayList<WorkTypeData>> {
        ArrayList<WorkTypeData> workTypeData =new  ArrayList<WorkTypeData>();
        private final ProgressDialog dialog = new ProgressDialog(
                TimeSheet.this);
        LoadWorkTypeData() {
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected ArrayList<WorkTypeData> doInBackground(String... param) {

            this.workTypeData= WebServiceHelper.getWorkdata();

            return this.workTypeData;
        }

        @Override
        protected void onPostExecute(ArrayList<WorkTypeData> result) {
            if (pd.isShowing()) {
                pd.dismiss();

            }

            if(result!=null)
            {
                iswork=true;
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(TimeSheet.this);
                    long i=placeData.saveWorkType(result);
                    if(i>0)
                    {
                   //     loadWorkTypeSPinner();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    public void loadWorkTypeSPinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        WorkTypeList = dataBaseHelper.getWorkdata();
        ArrayList<String> worklist = new ArrayList<>();
        if (WorkTypeList.size() > 0) {
            worklist.add("-- Choose Work --");
        }
        for (int i = 0; i < WorkTypeList.size(); i++) {
            worklist.add(WorkTypeList.get(i).getWorkType());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, worklist);
        spn_work_type.setAdapter(spinnerAdapter);
        if (TimeList.size()>0 ){
            loadTimeSPinner();
        }
        else {
            isTime = false;
            if (Utiilties.isOnline(TimeSheet.this)) {
                new LoadTimeData().execute("");
            } else {
                Toast.makeText(TimeSheet.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                TimeCode = "";
                Time = "";
            }
        }
        spn_work_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (arg2 > 0) {
                    WorkTypeData workTypeData = WorkTypeList.get(arg2 - 1);
                    WorkCode = workTypeData.getCode();
                    Worktype = workTypeData.getWorkType();

                }
                else {
                    WorkCode="";
                    Worktype="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }



    private class LoadTimeData extends AsyncTask<String, Void, ArrayList<TimeData>> {
        ArrayList<TimeData> timedata =new  ArrayList<TimeData>();
        private final ProgressDialog dialog = new ProgressDialog(
                TimeSheet.this);
        LoadTimeData() {
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected ArrayList<TimeData> doInBackground(String... param) {

            this.timedata= WebServiceHelper.getTime();

            return this.timedata;
        }

        @Override
        protected void onPostExecute(ArrayList<TimeData> result) {
            if (pd.isShowing()) {
                pd.dismiss();

            }

            if(result!=null)
            {
                isTime=true;
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(TimeSheet.this);
                    long i=placeData.saveTime(result);
                    if(i>0)
                    {
                        loadTimeSPinner();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    public void loadTimeSPinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        TimeList = dataBaseHelper.gettime();
        ArrayList<String> timelist = new ArrayList<>();
        if (TimeList.size() > 0) {
            timelist.add("-- Choose Time --");
        }
        for (int i = 0; i < TimeList.size(); i++) {
            timelist.add(TimeList.get(i).getWorkTime());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, timelist);
        spn_time.setAdapter(spinnerAdapter);
        spn_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (arg2 > 0) {
                    TimeData timedata = TimeList.get(arg2 - 1);
                    TimeCode = timedata.getCode();
                    Time = timedata.getWorkTime();

                }
                else {
                    TimeCode="";
                    Time="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    public void ShowDialog() {

        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        tomDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(TimeSheet.this,
                mDateSetListener, mYear, mMonth, tomDay);
       // datedialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            tomDay = selectedDay;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());
                String  newString = currentTimeString.replace("A.M.", "");
                mMonth= mMonth + 1;
                if(tomDay<10) {
                    tomDay = Integer.parseInt("0" + tomDay);
                    if(mMonth<10 && tomDay<10) {
                        mMonth = Integer.parseInt("0" + mMonth);
                        tv_date.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append("0"+tomDay));
                    }
                    else {
                        tv_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append("0" +tomDay));}

                }
                else if(mMonth<10) {
                    mMonth = Integer.parseInt("0" + mMonth);
                    tv_date.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append(tomDay));

                }
                else {
                    tv_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append(tomDay));

                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    };


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_save){
            if(validate()) {
                if(Utiilties.isOnline(TimeSheet.this))
                {
                    new TimeSheetService(TimeSheet.this).execute(tv_date.getText().toString().trim(),Empid, "0", TimeCode, ProjectCode, "0", edt_log_des.getText().toString().trim());
                }
                else {
                    Toast.makeText(TimeSheet.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(TimeSheet.this, ""+error_msg, Toast.LENGTH_SHORT).show();
                ScrollView scrollView=(ScrollView)findViewById(R.id.parent_scroll);
                scrollToView(scrollView,scroll_view);
            }

        }
    }

    private boolean validate() {
        boolean validated = false;
        if (tv_date.getText().toString().trim().equals("")){
            Toast.makeText(this, "Select Date !", Toast.LENGTH_SHORT).show();
            error_msg="Select Date !";
            scroll_view=tv_date;
            validated=false;
        }
       /* else if (Logid.equals("")){
            Toast.makeText(this, "Select Log Type !", Toast.LENGTH_SHORT).show();
            error_msg="Select Log Type !";
            scroll_view=spn_logtype;
            validated=false;
        }*/
        else if (ProjectCode.equals("")){
            Toast.makeText(this, "Select Project !", Toast.LENGTH_SHORT).show();
            error_msg="Select Project !";
            scroll_view=spn_poroject;
            validated=false;
        }
    /*    else if (WorkCode.equals("")){
            Toast.makeText(this, "Select Work Type !", Toast.LENGTH_SHORT).show();
            error_msg="Select Work Type !";
            scroll_view=spn_work_type;
            validated=false;
        }*/
        else if (TimeCode.equals("")){
            Toast.makeText(this, "Select Time !", Toast.LENGTH_SHORT).show();
            error_msg="Select Time !";
            scroll_view=spn_time;
            validated=false;
        }else if (edt_log_des.getText().toString().trim().equals("")){
            Toast.makeText(this, "Enter Log Description !", Toast.LENGTH_SHORT).show();
            error_msg="Enter Log Description !";
            scroll_view=edt_log_des;
            edt_log_des.setError("Enter Log Description !");
            validated=false;
        }

        else {
            validated = true;
        }
        return validated;
    }

    private void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }


}