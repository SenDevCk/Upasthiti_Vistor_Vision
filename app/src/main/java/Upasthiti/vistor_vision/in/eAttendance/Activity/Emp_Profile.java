package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.UpdateIsLock;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.EmployeeListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.ViewEmpProfile;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Emp_Profile extends AppCompatActivity {
   TextView tv_name,tv_post_name,tv_mob_no,tv_edt_pro,text_emp_id,text_off_emp_id,tv_hom_dist,tv_per_add,tv_dob,tv_doj,tv_ac,tv_ifsc,tv_pan,tv_uid,tv_pass;
   ImageView edit,profile_pic;
   String EMPNo="",pro_pic="",is_Lock="",Is_web="";
   LinearLayout ll_pass,ll_switch;
   Switch swch_islock;
    ArrayList<ViewEmpProfile> viewEmpProfiles;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_profile);

        final Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout c = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        AppBarLayout appbar = (AppBarLayout)findViewById(R.id.app_bar);
        tool.setTitle("");
        tool.setSubtitle("");
        setSupportActionBar(tool);
        c.setTitleEnabled(false);
        if(Utiilties.isOnline(Emp_Profile.this)) {
            EMPNo = CommonPref.getUserDetails(Emp_Profile.this).getEmpNo();
            new GetEmpProfileData().execute();
        }
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isVisible = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    tool.setTitle(getResources().getString(R.string.app_name));
                    tool.setSubtitle("Profile");
                    isVisible = true;
                } else if(isVisible) {
                    tool.setTitle("");
                    tool.setSubtitle("");
                    isVisible = false;
                }
            }
        });
        tv_name=findViewById(R.id.tv_name);
        tv_edt_pro=findViewById(R.id.tv_edt_pro);
        tv_mob_no=findViewById(R.id.tv_mob_no);
        tv_post_name=findViewById(R.id.tv_post_name);
        text_emp_id=findViewById(R.id.text_emp_id);
        edit=findViewById(R.id.edit);
        text_off_emp_id=findViewById(R.id.text_off_emp_id);
        tv_hom_dist=findViewById(R.id.tv_hom_dist);
        tv_per_add=findViewById(R.id.tv_per_add);
        tv_dob=findViewById(R.id.tv_dob);
        tv_doj=findViewById(R.id.tv_doj);
        tv_ac=findViewById(R.id.tv_ac);
        tv_ifsc=findViewById(R.id.tv_ifsc);
        tv_pan=findViewById(R.id.tv_pan);
        tv_uid=findViewById(R.id.tv_uid);
        tv_pass=findViewById(R.id.tv_pass);
      //  tv_islock=findViewById(R.id.tv_islock);
        ll_pass=findViewById(R.id.ll_pass);
        ll_switch=findViewById(R.id.ll_switch);
        swch_islock=findViewById(R.id.swch_islock);
        profile_pic=findViewById(R.id.profile_pic);
        Is_web="N";

        if(CommonPref.getUserDetails(Emp_Profile.this).getUserRole().equals("ADM"))
        {
            EmployeeListData data =(EmployeeListData)getIntent().getSerializableExtra("empdata");
            EMPNo=data.getEmpNo();
            profile_pic.setEnabled(false);
            ll_pass.setVisibility(View.VISIBLE);
            ll_switch.setVisibility(View.VISIBLE);
            if(Utiilties.isOnline(Emp_Profile.this))
            {
                new GetEmpProfileData().execute();

                    swch_islock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String isOn_Off;

                            if (isChecked) {
                                if(Is_web.equals("Y"))
                                {
                                    swch_islock.setText(": Active");
                                }
                               else {
                                    if (Utiilties.isOnline(Emp_Profile.this)) {
                                        swch_islock.setText(": Inactive");
                                        isOn_Off = "Y";
                                        new UpdateIsLock(Emp_Profile.this).execute(EMPNo, isOn_Off);
                                    } else {
                                        Toast.makeText(Emp_Profile.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            else {
                                if(Utiilties.isOnline(Emp_Profile.this)) {
                                    swch_islock.setText(": Active");
                                    isOn_Off = "N";
                                    new UpdateIsLock(Emp_Profile.this).execute(EMPNo, isOn_Off);
                                }
                                else {
                                    Toast.makeText(Emp_Profile.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }

            }

        else
        {
            ll_pass.setVisibility(View.GONE);
            ll_switch.setVisibility(View.GONE);
            pro_pic=CommonPref.getProfilePhoto(Emp_Profile.this).getInPhoto();
            tv_name.setText(CommonPref.getUserDetails(Emp_Profile.this).getUserName());
            tv_post_name.setText(CommonPref.getUserDetails(Emp_Profile.this).getPost_name());
            tv_mob_no.setText(CommonPref.getUserDetails(Emp_Profile.this).getMobileNumber());
            text_emp_id.setText(":"+" "+CommonPref.getUserDetails(Emp_Profile.this).getEmpNo());
            text_off_emp_id.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getOfficeEmpId()));
            tv_hom_dist.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getHomeDistrict()));
            tv_per_add.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getAddress()));
            tv_dob.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getDOB()));
            tv_doj.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getDOJ()));
            tv_ac.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getAccountNo()));
            tv_ifsc.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getIFSCCode()));
            tv_pan.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getPanNo()));
            tv_uid.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getAadhaarNo()));
            tv_pass.setText(":"+" "+(CommonPref.getProfilePhoto(Emp_Profile.this).getPassword()));
            if(pro_pic.equals(""))
            {
                profile_pic.setImageResource(R.drawable.man);
            }
            else {
                Picasso.with(Emp_Profile.this).load("http://www.bedmc.in/" + pro_pic).error(R.drawable.man).into(profile_pic);
            }
        }


        tv_edt_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
/*        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Emp_Profile.this,Update_Profile.class);
                if(CommonPref.getUserDetails(Emp_Profile.this).getUserRole().equals("ADM"))
                {
                    i.putExtra("EmpProfile",viewEmpProfiles.get(0));
                }
                startActivity(i);
                finish();
            }
        });*/
       /* profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Emp_Profile.this, Upload_Profile_Photo.class);
                startActivity(i);
            }
        });*/
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
                viewEmpProfiles = result;
                tv_name.setText((result.get(0).getUserName()));
                tv_post_name.setText((result.get(0).getPost_name()));
                tv_mob_no.setText((result.get(0).getMobile()));
                text_emp_id.setText(":"+" "+(result.get(0).getEmpNo()));
                text_off_emp_id.setText(":"+" "+(result.get(0).getOfficeEmpId()));
                tv_hom_dist.setText(":"+" "+(result.get(0).getHomeDistrict()));
                tv_per_add.setText(":"+" "+(result.get(0).getAddress()));
                tv_dob.setText(":"+" "+(result.get(0).getDOB()));
                tv_doj.setText(":"+" "+(result.get(0).getDOJ()));
                tv_ac.setText(":"+" "+(result.get(0).getAccountNo()));
                tv_ifsc.setText(":"+" "+(result.get(0).getIFSCCode()));
                tv_pan.setText(":"+" "+(result.get(0).getPanNo()));
                tv_uid.setText(":"+" "+(result.get(0).getAadhaarNo()));
                tv_pass.setText(":"+" "+(result.get(0).getPassword()));
                is_Lock=(result.get(0).getIs_Lock());
                Picasso.with(Emp_Profile.this).load("http://www.bedmc.in/" + result.get(0).getInPhoto()).error(R.drawable.man).into(profile_pic);
                Is_web="Y";
                if (is_Lock.equals("Y"))
                {
                    swch_islock.setChecked(true);
                    swch_islock.setText(": Inactive");
                }
                else if(is_Lock.equals("N"))
                {
                    swch_islock.setChecked(false);
                    swch_islock.setText(": Active");
                    Is_web="N";
                }

            }

        }
    }



   @Override
    public void onResume() {
        super.onResume();


    }

}