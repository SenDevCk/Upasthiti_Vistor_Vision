package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.Approve_Rej;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.SignUpService;
import Upasthiti.vistor_vision.in.eAttendance.entity.EmployeeListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkListData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Approve_Reject_Work extends AppCompatActivity {
    Toolbar toolbar_rej_approv;
    TextView text_EmpNo,text_log_date,text_log_typ,text_proj_typ,text_wrk_typ,text_w_time,text_log_des,text_remark,text_sts,text_wrk_id;
    EditText et_rej_reason;
    Button btn_rej,btn_approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_work);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_rej_approv = findViewById(R.id.toolbar_rej_approv);
            toolbar_rej_approv.setTitle(getResources().getString(R.string.app_name));
            toolbar_rej_approv.setSubtitle("Approve Reject Work");
            toolbar_rej_approv.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_rej_approv.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_rej_approv);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar_rej_approv.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        Inialatoin();
        setData();

        btn_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utiilties.isOnline(Approve_Reject_Work.this))
                {
                    new Approve_Rej(Approve_Reject_Work.this).execute(text_wrk_id.getText().toString().trim(),text_EmpNo.getText().toString().trim(), "R",et_rej_reason.getText().toString().trim());
                }
                else {
                    Toast.makeText(Approve_Reject_Work.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utiilties.isOnline(Approve_Reject_Work.this))
                {
                    new Approve_Rej(Approve_Reject_Work.this).execute(text_wrk_id.getText().toString().trim(),text_EmpNo.getText().toString().trim(), "A",et_rej_reason.getText().toString().trim());
                }
                else {
                    Toast.makeText(Approve_Reject_Work.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void Inialatoin()
    {
        text_EmpNo=findViewById(R.id.text_EmpNo);
        text_log_date=findViewById(R.id.text_log_date);
        text_log_typ=findViewById(R.id.text_log_typ);
        text_proj_typ=findViewById(R.id.text_proj_typ);
        text_wrk_typ=findViewById(R.id.text_wrk_typ);
        text_wrk_id=findViewById(R.id.text_wrk_id);
        text_w_time=findViewById(R.id.text_w_time);
        text_log_des=findViewById(R.id.text_log_des);
        text_remark=findViewById(R.id.text_remark);
        text_sts=findViewById(R.id.text_sts);
        et_rej_reason=findViewById(R.id.et_rej_reason);
        btn_rej=findViewById(R.id.btn_rej);
        btn_approve=findViewById(R.id.btn_approve);
    }


    private void setData()
    {
        WorkListData data =(WorkListData)getIntent().getSerializableExtra("workdata");
        text_EmpNo.setText(data.getEmpNo());
        text_log_date.setText(data.getLogDate());
        text_log_typ.setText(data.getLogType());
        text_proj_typ.setText(data.getProjectName());
        text_wrk_typ.setText(data.getWorkType());
        text_wrk_id.setText(data.getWorkId());
        text_w_time.setText(data.getWTime());
        text_log_des.setText(data.getLogDescription());
        text_remark.setText(data.getRemarks());
        text_sts.setText(data.getStatus());
    }
}