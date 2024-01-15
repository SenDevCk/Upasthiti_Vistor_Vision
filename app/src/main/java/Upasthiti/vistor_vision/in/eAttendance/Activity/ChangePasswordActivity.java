package Upasthiti.vistor_vision.in.eAttendance.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.ChangePassService;
import Upasthiti.vistor_vision.in.eAttndance.R;


public class ChangePasswordActivity extends AppCompatActivity {
Toolbar toolbar_cp;
TextInputEditText etben_old_pass,etben_new_pass,et_con_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar_cp = (Toolbar) findViewById(R.id.toolbar_cp);
        toolbar_cp.setTitle(getResources().getString(R.string.app_name));
        toolbar_cp.setSubtitle("Change Password");
        setSupportActionBar(toolbar_cp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

            init();
    }

    private void init() {
        etben_old_pass=(TextInputEditText)findViewById(R.id.etben_old_pass);
        etben_new_pass=(TextInputEditText)findViewById(R.id.etben_new_pass);
        et_con_pass=(TextInputEditText)findViewById(R.id.et_con_pass);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void ChangePassword(View view) {
        if (etben_old_pass.getText().toString().trim().equals("")){
            etben_old_pass.setError("Enter Old Pass");
        }
        else if (etben_new_pass.getText().toString().trim().equals("")){
            etben_new_pass.setError("Enter New Pass");
        }
        else if (etben_new_pass.getText().toString().length() < 4){
            etben_new_pass.setError("4 char minimum!");
        }
        else if (et_con_pass.getText().toString().trim().equals("")){
            et_con_pass.setError("Confirm new Pass");
        }
        else if (et_con_pass.getText().toString().length() < 4){
            et_con_pass.setError("4 char minimum!");
        }
        else if (!et_con_pass.getText().toString().trim().equals(etben_new_pass.getText().toString().trim())){
            et_con_pass.setError("Invalid");
            etben_new_pass.setError("Invalid");
        }
        else{
            if(Utiilties.isOnline(ChangePasswordActivity.this)){
            new ChangePassService(ChangePasswordActivity.this).execute(CommonPref.getUserDetails(ChangePasswordActivity.this).getUserId(),
                    etben_old_pass.getText().toString().trim(),etben_new_pass.getText().toString().trim());
        }
             else {
                Toast.makeText(this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
