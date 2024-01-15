package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.LeaveStatusAdapter;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.ApplyLeaveService;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.LeaveStatusService;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveData;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveStatusData;
import Upasthiti.vistor_vision.in.eAttendance.interfaces.LeaveStatusBinder;
import Upasthiti.vistor_vision.in.eAttndance.R;


public class Apply_Leave extends AppCompatActivity implements View.OnClickListener{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RC_PHOTO_PICKER = 1;
    byte[] imageData1;
    Toolbar toolbar_gd;
    TextView tv_aply_leav,tv_leave_his,tv_frm_date,tv_to_date,text_report_not_found;
    EditText et_leave_reason;
     Spinner spn_leave_typ;
   LinearLayout ll_apply_leave,ll_leav_his;
    ArrayList<LeaveData> LeaveList = new ArrayList<>();
    ArrayAdapter<String> Leaveadapter;
    String LeaveId="",error_msg="",EmpNo="",PostCode="",FromGallery="",AppDoc="";
    int NoOfDay=0;
    View view_apply,view_his;
    ImageView imgview_leave;
    DatePickerDialog datedialog;
    private Calendar cal;
    private int mYear, mMonth, mDay,tomDay;
    Button btn_apply;
    ListView list_report;
    ArrayList<LeaveStatusData> LeaveStatusList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            //toolbar_gd.setNavigationIcon(getResources().getDrawable(R.drawable.leftarrow));
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Apply Leave");
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
        spn_leave_typ=findViewById(R.id.spn_leave_typ);
        ll_apply_leave=findViewById(R.id.ll_apply_leave);
        ll_leav_his=findViewById(R.id.ll_leav_his);
        tv_aply_leav=findViewById(R.id.tv_aply_leav);
        tv_leave_his=findViewById(R.id.tv_leave_his);
        tv_frm_date = findViewById(R.id.tv_frm_date);
        tv_to_date =findViewById(R.id.tv_to_date);
        view_apply =findViewById(R.id.view_apply);
        view_his =findViewById(R.id.view_his);
        btn_apply=findViewById(R.id.btn_apply);
        list_report=findViewById(R.id.list_report);
        imgview_leave=findViewById(R.id.imgview_leave);
        text_report_not_found=findViewById(R.id.text_report_not_found);
        et_leave_reason=findViewById(R.id.et_leave_reason);
        tv_aply_leav.setTextColor(getResources().getColor(R.color.orange));
        view_apply.setBackgroundColor(getResources().getColor(R.color.orange));
        view_his.setBackgroundColor(getResources().getColor(R.color.white));
        tv_leave_his.setTextColor(getResources().getColor(R.color.white));
        btn_apply.setOnClickListener(this);
        EmpNo= CommonPref.getUserDetails(Apply_Leave.this).getEmpNo();
        PostCode= CommonPref.getUserDetails(Apply_Leave.this).getPostCode();
        loadLeave();
        imgview_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  showPictureDialog();
                openPhotoPicker();
            }
        });

        ll_apply_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Apply_Leave.this,Apply_Leave.class);
                startActivity(i);
            }
        });

        ll_leav_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Apply_Leave.this,LeaveHistory.class);
                startActivity(i);
            }
        });

        cal = Calendar.getInstance();
        tv_frm_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog1();
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_apply){

      //      Notification();

            if(validate()) {
                NoOfDay= mDay-tomDay;
                if(NoOfDay==0)
                {
                    NoOfDay=1;
                }
                if(Utiilties.isOnline(Apply_Leave.this))
                {
                new ApplyLeaveService(Apply_Leave.this).execute(EmpNo,PostCode,LeaveId,tv_frm_date.getText().toString().trim(), tv_to_date.getText().toString().trim(),String.valueOf(NoOfDay),et_leave_reason.getText().toString().trim(),AppDoc);
               }
                else 
                {
                    Toast.makeText(Apply_Leave.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(Apply_Leave.this, ""+error_msg, Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void loadLeave() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        LeaveList = dataBaseHelper.getleave();
        String[] divNameArray = new String[LeaveList.size() + 1];
        divNameArray[0] = "-- Select Leave Type --";
        int i = 1;
        for (LeaveData leaveData : LeaveList) {
            divNameArray[i] = leaveData.getLeaveName();
            i++;
        }
        Leaveadapter = new ArrayAdapter<>(this, R.layout.dropdownlist, divNameArray);
        Leaveadapter.setDropDownViewResource(R.layout.dropdownlist);
        spn_leave_typ.setAdapter(Leaveadapter);
        spn_leave_typ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    LeaveData leaveData = LeaveList.get(i - 1);
                    LeaveId = leaveData.getLeavecode();
                }
                else
                {
                    LeaveId="";

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
        tomDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(Apply_Leave.this,
                mDateSetListener, mYear, mMonth, tomDay);
        datedialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
                        tv_frm_date.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append("0"+tomDay));
                    }
                    else {
                        tv_frm_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append("0" +tomDay));}

                }
                else if(mMonth<10) {
                    mMonth = Integer.parseInt("0" + mMonth);
                    tv_frm_date.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append(tomDay));

                }
                else {
                    tv_frm_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append(tomDay));

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

        datedialog = new DatePickerDialog(Apply_Leave.this,mDateSetListener1, mYear, mMonth, mDay);
       // datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
                    if(mMonth<10 && mDay<10) {
                        mMonth = Integer.parseInt("0" + mMonth);

                        tv_to_date.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append("0"+mDay));
                    }
                    else {
                        tv_to_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append("0" +mDay));}

                }
                else if(mMonth<10) {
                    mMonth = Integer.parseInt("0" + mMonth);
                    tv_to_date.setText(new StringBuilder().append(mYear).append("-").append("0").append(mMonth).append("-").append(mDay));

                }else {
                    tv_to_date.setText(new StringBuilder().append(mDay).append("-").append(mMonth).append("-").append(  mYear));

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    private boolean validate() {
        boolean validated = false;
        if (LeaveId.equals("")){
            Toast.makeText(this, "Select Leave Type !", Toast.LENGTH_SHORT).show();
            error_msg="Select Leave Type !";
            validated=false;
        }
        else if (tv_frm_date.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter From Date !", Toast.LENGTH_SHORT).show();
            error_msg="Enter From Date !";
            tv_frm_date.setError("Enter  From Date !");
            validated = false;
        } else if (tv_to_date.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter to Date !", Toast.LENGTH_SHORT).show();
            error_msg="Enter to Date !";
            tv_to_date.setError("Enter to Date !");
            validated = false;
        }
        else if (et_leave_reason.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter Reasons To Leave  !", Toast.LENGTH_SHORT).show();
            error_msg="Enter Reasons To Leave !";
            et_leave_reason.setError("Enter Reasons To Leave !");
            validated = false;
        }

        else {
            validated = true;
        }
        return validated;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utiilties.isOnline(Apply_Leave.this) && LeaveStatusList==null) {
            LeaveStatusService.bindGrivanceReport(new LeaveStatusBinder() {
                @Override
                public void bindReport(ArrayList<LeaveStatusData> userContributionDataArrayList) {
                    LeaveStatusList=userContributionDataArrayList;
                    list_report.setVisibility(View.VISIBLE);
                    text_report_not_found.setVisibility(View.GONE);
                    list_report.setAdapter(new LeaveStatusAdapter(Apply_Leave.this,LeaveStatusList));
                }

                @Override
                public void cancleReportBinding() {
                    LeaveStatusList.clear();
                    list_report.invalidate();
                    list_report.setVisibility(View.GONE);
                    text_report_not_found.setVisibility(View.VISIBLE);
                }
            });
            new LeaveStatusService(Apply_Leave.this).execute();
        }
    }

    private  void Notification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"n")
                .setSmallIcon(R.drawable.luncher)
                .setContentTitle("New leave Applied !")
                .setContentText(CommonPref.getUserDetails(Apply_Leave.this).getUserName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openPhotoPicker();
                                FromGallery="Yes";
                                break;
                            case 1:
                                dispatchTakePictureIntent();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, RC_PHOTO_PICKER);
        }
    }

    private void openPhotoPicker() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, false);
        startActivityForResult(Intent.createChooser(photoPickerIntent,"Complete Action Using"), RC_PHOTO_PICKER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();

            //set the selected image to ImageView
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imgview_leave.setImageURI(pickedImage);
                imageData1 = byteArray;
                AppDoc=Utiilties.BitArrayToString(imageData1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "Please Select the Document", Toast.LENGTH_SHORT).show();
        }

     /*   if (FromGallery.equals("Yes")) {
            if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data != null) {
                Uri pickedImage = data.getData();

                //set the selected image to ImageView
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    imgview_leave.setImageURI(pickedImage);
                    imageData1 = byteArray;
                    AppDoc=Utiilties.BitArrayToString(imageData1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        else {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imageData1 = byteArray;
                AppDoc=Utiilties.BitArrayToString(imageData1);
                imgview_leave.setImageBitmap(imageBitmap);
            }
        }*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}