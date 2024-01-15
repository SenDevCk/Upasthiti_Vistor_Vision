package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.UpdateProfile;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;

import Upasthiti.vistor_vision.in.eAttendance.entity.DistrictData;

import Upasthiti.vistor_vision.in.eAttendance.entity.ViewEmpProfile;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Update_Profile extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_PHOTO_PICKER = 1;
    Toolbar toolbar_gd;
    ProgressDialog pd;
    EditText edt_emp_id,edt_preAdd,edt_ac_no,edt_ifsc,edt_pan,edt_aadhar,edt_mob_no;
   TextView tvdob,tvdoj;
    Spinner spn_dist;
    Button btn_save, btn_cancel;
    DatePickerDialog datedialog;
    private boolean validAadhaar;
    ImageView btncaladob,btncaladoj;

    String distName="",distId="",empId="",UserRole="";
    private Calendar cal;
    private int mYear, mMonth, mDay;

    ArrayList<DistrictData> DistList = new ArrayList<>();
    ArrayAdapter<String> districtadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            //toolbar_gd.setNavigationIcon(getResources().getDrawable(R.drawable.leftarrow));
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Update Profile");
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

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setIndeterminateDrawable(getResources().getDrawable((R.drawable.my_progress)));
        pd.setMessage("Loading...");

        spn_dist =  findViewById(R.id.spn_dist);
        tvdob =  findViewById(R.id.tvdob);
        tvdoj = findViewById(R.id.tvdoj);
        edt_emp_id = findViewById(R.id.edt_emp_id);
        edt_preAdd = findViewById(R.id.edt_preAdd);
        edt_ac_no = findViewById(R.id.edt_ac_no);
        edt_ifsc = findViewById(R.id.edt_ifsc);
        edt_pan = findViewById(R.id.edt_pan);
        edt_aadhar = findViewById(R.id.edt_aadhar);
        edt_mob_no = findViewById(R.id.edt_mob_no);
        btncaladob =(ImageView) findViewById(R.id.btncaladob);
        btncaladoj =(ImageView) findViewById(R.id.btncaladoj);
        UserRole= CommonPref.getUserDetails(Update_Profile.this).getUserRole();
        cal = Calendar.getInstance();
        btncaladob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        btncaladoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog1();
            }
        });

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


         if(CommonPref.getUserDetails(Update_Profile.this).getUserRole().equals("ADM"))
        {

            ViewEmpProfile viewEmpProfile= (ViewEmpProfile)getIntent().getSerializableExtra("EmpProfile");
            empId=viewEmpProfile.getEmpNo();
            tvdob.setText(viewEmpProfile.getDOB());
            tvdoj.setText(viewEmpProfile.getDOJ());
            edt_preAdd.setText(viewEmpProfile.getAddress());
            edt_ac_no.setText(viewEmpProfile.getAccountNo());
            edt_ifsc.setText(viewEmpProfile.getIFSCCode());
            edt_pan.setText(viewEmpProfile.getPanNo());
            edt_aadhar.setText(viewEmpProfile.getAadhaarNo());
            edt_mob_no.setText(viewEmpProfile.getMobile());
            distName=viewEmpProfile.getHomeDistrict();
            loadDistrict();

        }
        else {
             empId= CommonPref.getUserDetails(Update_Profile.this).getEmpNo();
             tvdob.setText(CommonPref.getProfilePhoto(Update_Profile.this).getDOB());
             tvdoj.setText(CommonPref.getProfilePhoto(Update_Profile.this).getDOJ());
             edt_preAdd.setText(CommonPref.getProfilePhoto(Update_Profile.this).getAddress());
             edt_ac_no.setText(CommonPref.getProfilePhoto(Update_Profile.this).getAccountNo());
             edt_ifsc.setText(CommonPref.getProfilePhoto(Update_Profile.this).getIFSCCode());
             edt_pan.setText(CommonPref.getProfilePhoto(Update_Profile.this).getPanNo());
             edt_aadhar.setText(CommonPref.getProfilePhoto(Update_Profile.this).getAadhaarNo());
             edt_mob_no.setText(CommonPref.getProfilePhoto(Update_Profile.this).getMobile());
             distName= CommonPref.getProfilePhoto(Update_Profile.this).getHomeDistrict();
             loadDistrict();
         }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_save){
            if(Utiilties.isOnline(Update_Profile.this))
            {
                String DOB,DOj;
                DOB=tvdob.getText().toString().trim();
                DOj=tvdoj.getText().toString().trim();
                if (DOB.equals("NA"))
                {
                    DOB="";
                }
                else  if (DOj.equals("NA"))
                {
                    DOj="";
                }

            new UpdateProfile(Update_Profile.this).execute(empId,distId,edt_preAdd.getText().toString().trim(),DOB,DOj, edt_ac_no.getText().toString().trim(), edt_ifsc.getText().toString().trim(),edt_pan.getText().toString().trim(),edt_aadhar.getText().toString().trim(),edt_mob_no.getText().toString().trim());
            }
            else {
                Toast.makeText(Update_Profile.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void loadDistrict() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        DistList = dataBaseHelper.getdistrict();
        String[] divNameArray = new String[DistList.size() + 1];
        divNameArray[0] = "-- Select District --";
        int i = 1;
        for (DistrictData districtData : DistList) {
            divNameArray[i] = districtData.get_DistNameEn();
            i++;
        }
        districtadapter = new ArrayAdapter<>(this, R.layout.dropdownlist, divNameArray);
        districtadapter.setDropDownViewResource(R.layout.dropdownlist);
        if(distName.equals("") || distName.equals("NA"))
        {
            spn_dist.setAdapter(districtadapter);
        }
        else {
            spn_dist.setAdapter(districtadapter);
            spn_dist.setAdapter(districtadapter);
            int spinnerPosition = districtadapter.getPosition(distName);
            spn_dist.setSelection(spinnerPosition);
        }

        spn_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    DistrictData dist = DistList.get(i - 1);
                    distId = dist.get_Distcode();
                }
                else
                {
                    distId="";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void ShowDialog() {

        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(Update_Profile.this,
        mDateSetListener, mYear, mMonth, mDay);
        datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());
                String  newString = currentTimeString.replace("A.M.", "");
                mMonth=mMonth + 1;
                if(mDay<10) {
                    mDay = Integer.parseInt("0" + mDay);
                    if(mMonth<10) {
                        mMonth = Integer.parseInt("0" + mMonth);
                        tvdob.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append("0"+mDay));
                    }
                   else {
                    tvdob.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append("0" +mDay));}

                }
               else if(mMonth<10) {
                    mMonth = Integer.parseInt("0" + mMonth);

                    tvdob.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append(mDay));

                }
                else {
                    tvdob.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append(mDay));

                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    };
    public void ShowDialog1() {

        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(Update_Profile.this,
                mDateSetListener1, mYear, mMonth, mDay);
        datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();

    }

    DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());
                String  newString = currentTimeString.replace("A.M.", "");
                mMonth=mMonth + 1;
                if(mDay<10) {
                    mDay = Integer.parseInt("0" + mDay);
                    if(mMonth<10) {
                        mMonth = Integer.parseInt("0" + mMonth);

                        tvdoj.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append("0"+mDay));
                    }
                    else {
                        tvdoj.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append("0" +mDay));}

                }
                else if(mMonth<10) {
                    mMonth = Integer.parseInt("0" + mMonth);

                    tvdoj.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append(mDay));

                }else {
                    tvdoj.setText(new StringBuilder().append(mDay).append("-").append(mMonth).append("-").append(  mYear));

                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}