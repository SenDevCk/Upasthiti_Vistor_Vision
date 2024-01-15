package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.ApproveLeave;
import Upasthiti.vistor_vision.in.eAttendance.entity.PendingLeaveListData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class LeaveApprove extends AppCompatActivity {
    Toolbar toolbar_gd;
    ProgressDialog pd;
    EditText et_leave_reason;
    TextView text_EmpNo,text_EmpName,text_post_name,text_leav_typ,text_leav_id,text_no_day,text_frm_date,text_to_date,text_aply_date,text_buniyad,text_l_sts,tv_aat_doc,
    text_app_remark,text_app_date,text_remark;
    Button btn_rej,btn_approve;
    TextInputLayout txt_layout_leave;
   // ImageView leav_pic;
    LinearLayout ll_ap_date,ll_sts,ll_app_rmk,leave_doc;
    String error_msg="",EmpNo="",Leaveid="",ApproveSts="",ApproveBy="",Approved_Sts="",Pic_link="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_approve);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = findViewById(R.id.toolbar_gd);
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Approve Leave");
            toolbar_gd.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_gd.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_gd);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar_gd.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        text_EmpNo=findViewById(R.id.text_EmpNo);
        text_EmpName=findViewById(R.id.text_EmpName);
        text_post_name=findViewById(R.id.text_post_name);
        text_leav_typ=findViewById(R.id.text_leav_typ);
        text_leav_id=findViewById(R.id.text_leav_id);
        text_no_day=findViewById(R.id.text_no_day);
        text_frm_date=findViewById(R.id.text_frm_date);
        text_to_date=findViewById(R.id.text_to_date);
        text_aply_date=findViewById(R.id.text_aply_date);
        text_buniyad=findViewById(R.id.text_buniyad);
        text_l_sts=findViewById(R.id.text_l_sts);
        text_app_remark=findViewById(R.id.text_app_remark);
        text_remark=findViewById(R.id.text_remark);
        text_app_date=findViewById(R.id.text_app_date);
        txt_layout_leave=findViewById(R.id.txt_layout_leave);
        et_leave_reason=findViewById(R.id.et_leave_rej_reason);
        ll_ap_date=findViewById(R.id.ll_ap_date);
        ll_sts=findViewById(R.id.ll_sts);
       // leav_pic=findViewById(R.id.leav_pic);
        ll_app_rmk=findViewById(R.id.ll_app_rmk);
        leave_doc=findViewById(R.id.leave_doc);
        tv_aat_doc=findViewById(R.id.tv_aat_doc);
        btn_rej=findViewById(R.id.btn_rej);
        btn_approve=findViewById(R.id.btn_approve);

        final Intent intent = getIntent();
        PendingLeaveListData data =(PendingLeaveListData)getIntent().getSerializableExtra("leavedata");
        Approved_Sts=intent.getStringExtra("leavests");
        if(Approved_Sts.equals("A"))
        {
            btn_rej.setVisibility(View.GONE);
            btn_approve.setVisibility(View.GONE);
            txt_layout_leave.setVisibility(View.GONE);
            ll_ap_date.setVisibility(View.VISIBLE);
            ll_app_rmk.setVisibility(View.VISIBLE);
            ll_sts.setVisibility(View.VISIBLE);
        }
    else if(Approved_Sts.equals("P")) {
            btn_rej.setVisibility(View.VISIBLE);
            btn_approve.setVisibility(View.VISIBLE);
            txt_layout_leave.setVisibility(View.VISIBLE);
            ll_ap_date.setVisibility(View.GONE);
            ll_app_rmk.setVisibility(View.GONE);
            ll_sts.setVisibility(View.VISIBLE);
        }
        text_EmpNo.setText(":"+" "+data.getEmpNo());
        text_EmpName.setText(":"+" "+data.getEmpName());
        text_post_name.setText(":"+" "+data.getPost_name());
        text_leav_typ.setText(":"+" "+data.getLeaveType());
        text_leav_id.setText(":"+" "+data.getLeaveId());
        text_no_day.setText(":"+" "+data.getNoOfDay());
        text_frm_date.setText(":"+" "+data.getDateFrom());
        text_to_date.setText(":"+" "+data.getDateTo());
        text_aply_date.setText(":"+" "+data.getApplyDate());
        text_buniyad.setText(":"+" "+data.getDistrict());
        text_app_date.setText(":"+" "+data.getApprovedate());
        text_app_remark.setText(":"+" "+data.getAppRemarks());
        text_remark.setText(":"+" "+data.getRemarks());
        text_l_sts.setText(":"+" "+data.getLstatus());
        Pic_link=data.getAppDoc();
        if(Pic_link.trim().equals("NA") || Pic_link.trim().equals("")  )
        {
            leave_doc.setEnabled(false);
            leave_doc.setClickable(false);
            tv_aat_doc.setText("Document not available !");
        }
        else {
            leave_doc.setEnabled(true);
            leave_doc.setClickable(true);
            tv_aat_doc.setText(" please find the attachment !");

        }
     //   Picasso.with(LeaveApprove.this).load("http://182.72.68.37/" + Pic_link).error(R.drawable.noimage).into(leav_pic);
       

        EmpNo=data.getEmpNo();
        Leaveid=data.getLeaveId();
        ApproveBy= CommonPref.getUserDetails(LeaveApprove.this).getEmpNo();

        leave_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent i = new Intent(LeaveApprove.this,Touch_View_Image.class);
                i.putExtra("piclnk",Pic_link);
                startActivity(i);*/
                Intent intent1 = new Intent(LeaveApprove.this,Touch_View_Image.class);
                        intent1.putExtra("piclnk",Pic_link);
                        startActivity(intent1);
            }
        });

        btn_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApproveSts="R";
                if(validate()) {
                    if(Utiilties.isOnline(LeaveApprove.this))
                    {
                        new ApproveLeave(LeaveApprove.this).execute(EmpNo,Leaveid,ApproveSts,et_leave_reason.getText().toString().trim(), ApproveBy);
                    }
                    else {
                        Toast.makeText(LeaveApprove.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LeaveApprove.this, ""+error_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApproveSts="A";
                if(Utiilties.isOnline(LeaveApprove.this))
                {
                    new ApproveLeave(LeaveApprove.this).execute(EmpNo,Leaveid,ApproveSts,et_leave_reason.getText().toString().trim(), ApproveBy);
                }
                else {
                    Toast.makeText(LeaveApprove.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean validate() {
        boolean validated = false;
        if (et_leave_reason.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please Enter The Rejection Reason !", Toast.LENGTH_LONG).show();
            error_msg="Please Enter The Rejection Reason !";
            et_leave_reason.setError("Enter The Rejection Reason !");
            validated=false;
        }

        else {
            validated = true;
        }
        return validated;
    }

}