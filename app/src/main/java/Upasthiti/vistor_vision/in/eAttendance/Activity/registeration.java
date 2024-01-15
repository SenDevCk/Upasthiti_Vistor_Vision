package Upasthiti.vistor_vision.in.eAttendance.Activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.SignUpService;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.BlockData;
import Upasthiti.vistor_vision.in.eAttendance.entity.DistrictData;
import Upasthiti.vistor_vision.in.eAttendance.entity.GenderData;
import Upasthiti.vistor_vision.in.eAttendance.entity.PostData;
import Upasthiti.vistor_vision.in.eAttndance.R;


public class registeration extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar_gd;
    ProgressDialog pd;
    EditText edt_name,edt_emp_id,edt_mob_no;
    Spinner spn_dist,spn_post,spn_gender,spn_blk;
    Button btn_save, btn_cancel;
    private Calendar cal;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    ImageView btncaladob;
    boolean isPost=false,isBlock=false;
    View scroll_view;
    String error_msg="",distId="",subDivCode="",PostId="",genderId="",Post_type="",distName="",userRole="";

    ArrayList<DistrictData> DistList = new ArrayList<>();
    ArrayList<BlockData> blockList = new ArrayList<>();
    ArrayAdapter<String> districtadapter,blockadaptor;
    ArrayList<PostData> postList = new ArrayList<>();
    ArrayList<GenderData> genderList = new ArrayList<>();
    ArrayAdapter<String> genderadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            //toolbar_gd.setNavigationIcon(getResources().getDrawable(R.drawable.leftarrow));
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Employee Registeration");
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

   //     distId= CommonPref.getUserDetails(registeration.this).getDistrictCode();
        distName= CommonPref.getUserDetails(registeration.this).getDistName();
        userRole= CommonPref.getUserDetails(registeration.this).getUserRole();

        spn_dist =  findViewById(R.id.spn_dist);
        spn_blk =  findViewById(R.id.spn_blk);
        spn_post =  findViewById(R.id.spn_post);
        spn_gender =  findViewById(R.id.spn_gender);
        edt_name =  findViewById(R.id.edt_name);
        edt_emp_id = findViewById(R.id.edt_emp_id);
        edt_mob_no = findViewById(R.id.edt_mob_no);


        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(this);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadDistrict();
        loadGenderSpinnerdata();

        if(postList.size()>0 ) {
            loadPostSPinner();
        }
        else {
            isPost = false;
            if (Utiilties.isOnline(registeration.this)) {
                new LoadPostdata().execute("");
            } else {
                Toast.makeText(registeration.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                PostId = "";
                Post_type="";
            }
        }

    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_save){
            if(validate()) {
                if(Utiilties.isOnline(registeration.this))
                {
                new SignUpService(registeration.this).execute(edt_emp_id.getText().toString().trim(),edt_name.getText().toString().trim(), edt_mob_no.getText().toString().trim(), PostId, subDivCode, distId, genderId);
                }
                else {
                    Toast.makeText(registeration.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(registeration.this, ""+error_msg, Toast.LENGTH_SHORT).show();
                ScrollView scrollView=(ScrollView)findViewById(R.id.parent_scroll);
                scrollToView(scrollView,scroll_view);
            }

        }
    }

    private boolean validate() {
        boolean validated = false;
      if (PostId.equals("")){
            Toast.makeText(this, "Select Post !", Toast.LENGTH_SHORT).show();
            error_msg="Select Post !";
            scroll_view=spn_post;
            validated=false;
        }
      else if (edt_emp_id.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter Employe Id !", Toast.LENGTH_SHORT).show();
            error_msg="Enter Employe Id !";
            scroll_view=edt_emp_id;
            edt_emp_id.setError("Enter  Employe Id !");
            validated = false;
        } else if (edt_name.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter Name !", Toast.LENGTH_SHORT).show();
            error_msg="Enter Name !";
            scroll_view=edt_name;
            edt_name.setError("Enter Name !");
            validated = false;
        } else if (edt_mob_no.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Enter Mobile No !", Toast.LENGTH_SHORT).show();
            error_msg="Enter Mobile No !";
            scroll_view=edt_mob_no;
            edt_mob_no.setError("Enter Mobile Number !");
            validated = false;
        }
        else if (genderId.equals("")){
            Toast.makeText(this, "Select Gender !", Toast.LENGTH_SHORT).show();
            error_msg="Select Gender !";
            scroll_view=spn_gender;
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
        spn_dist.setAdapter(districtadapter);
        if(userRole.equals("DSTADM")) {
            int spinnerPosition = districtadapter.getPosition(distName);
            spn_dist.setSelection(spinnerPosition);
            spn_dist.setClickable(false);
            spn_dist.setEnabled(false);
        }
        else {
            spn_dist.setClickable(true);
            spn_dist.setEnabled(true);
        }
        spn_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    DistrictData dist = DistList.get(i - 1);
                    distId = dist.get_Distcode();
                    if (blockList.size() > 0) {
                        loadBlockSPinner(distId);
                    } else {
                        isBlock = false;
                        if (Utiilties.isOnline(registeration.this)) {
                            new LoadBlockdata(distId).execute("");
                        } else {
                            Toast.makeText(registeration.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                            subDivCode = "";
                        }
                    }
                }
                else
                    {
                        distId = "";
                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private class LoadBlockdata extends AsyncTask<String, Void, ArrayList<BlockData>> {
        String distCode="";
        // String blockCode="";
        ArrayList<BlockData> blockDataArrayList=new  ArrayList<BlockData>();
        private final ProgressDialog dialog = new ProgressDialog(
                registeration.this);

        LoadBlockdata (String distCode) {
            this.distCode = distCode;
            //     this.blockCode=blockCode;

        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected ArrayList<BlockData> doInBackground(String... param) {

            this.blockDataArrayList= WebServiceHelper.getSubDivision(distCode);

            return this.blockDataArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<BlockData> result) {
            if (pd.isShowing()) {
                pd.dismiss();

            }

            if(result!=null)
            {
                isBlock=true;
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(registeration.this);
                    long i=placeData.saveBlock(result, distCode);
                    if(i>0)
                    {
                        loadBlockSPinner(distId);
                    }
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();
                }
            }

        }

    }
        public void loadBlockSPinner(String distId) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            blockList = dataBaseHelper.getBlockLocal(distId);
            ArrayList<String> blist = new ArrayList<>();
            if (blockList.size() > 0) {
                blist.add("-- Choose Block --");
            }
            for (int i = 0; i < blockList.size(); i++) {
                blist.add(blockList.get(i).getBlockname());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, blist);
            spn_blk.setAdapter(spinnerAdapter);
            spn_blk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                    if (arg2 > 0) {
                        BlockData subdivion = blockList.get(arg2 - 1);
                        subDivCode = subdivion.getBlockcode();

                    }
                    else {
                        subDivCode="";
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }


    private class LoadPostdata extends AsyncTask<String, Void, ArrayList<PostData>> {
        ArrayList<PostData> blockDataArrayList=new  ArrayList<PostData>();
        private final ProgressDialog dialog = new ProgressDialog(
                registeration.this);

        LoadPostdata() {

        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected ArrayList<PostData> doInBackground(String... param) {

            this.blockDataArrayList= WebServiceHelper.getPost();

            return this.blockDataArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<PostData> result) {
            if (pd.isShowing()) {
                pd.dismiss();

            }

            if(result!=null)
            {
                isPost=true;
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(registeration.this);
                    long i=placeData.savePost(result);
                    if(i>0)
                    {
                        loadPostSPinner();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();
                }
            }

        }

    }
    public void loadPostSPinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        postList = dataBaseHelper.getPostLocal();
        ArrayList<String> plist = new ArrayList<>();
        if (postList.size() > 0) {
            plist.add("-- Choose Post --");
        }
        for (int i = 0; i < postList.size(); i++) {
            plist.add(postList.get(i).getPostName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, plist);
        spn_post.setAdapter(spinnerAdapter);
        spn_post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (arg2 > 0) {
                    PostData postcode = postList.get(arg2 - 1);
                    PostId = postcode.getPostCode();
                    Post_type = postcode.getPostType();

                }
                else {
                    PostId="";
                    Post_type="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void loadGenderSpinnerdata() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        genderList = dataBaseHelper.getgendertdetail();
        String[] divNameArray = new String[genderList.size() + 1];
        divNameArray[0] = "-- Select Gender --";
        int i = 1;
        for (GenderData gen : genderList) {
            divNameArray[i] = gen.getGendername();
            i++;
        }
        genderadapter = new ArrayAdapter<>(this, R.layout.dropdownlist, divNameArray);
        genderadapter.setDropDownViewResource(R.layout.dropdownlist);
        spn_gender.setAdapter(genderadapter);
        spn_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    GenderData gen = genderList.get(arg2 - 1);
                    genderId = gen.getGenderId();
                }
                else
                {
                    genderId="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }



}

