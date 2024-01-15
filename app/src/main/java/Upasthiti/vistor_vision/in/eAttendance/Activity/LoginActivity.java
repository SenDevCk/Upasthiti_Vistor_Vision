package Upasthiti.vistor_vision.in.eAttendance.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.GlobalVariables;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.ForgetPasswordService;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.LogoutService;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.UserDetails;
import Upasthiti.vistor_vision.in.eAttendance.entity.ViewEmpProfile;
import Upasthiti.vistor_vision.in.eAttendance.interfaces.ForgetPasswordBinder;
import Upasthiti.vistor_vision.in.eAttndance.R;


public class LoginActivity extends AppCompatActivity {
    ConnectivityManager cm;
    String version;
    TelephonyManager tm;
    private static String imei;
    DataBaseHelper localDBHelper;
    EditText userPass,userName;
    Button btn_login;
    TextView txtVersion,tv_fort_pass;
    Context context;
    String UserId="";
    boolean isProfile;
    ArrayList<ViewEmpProfile> viewEmpProfiles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        localDBHelper = new DataBaseHelper(LoginActivity.this);

        userName = (EditText) findViewById(R.id.et_User_Name);
        userPass = (EditText) findViewById(R.id.et_pass);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        tv_fort_pass = (TextView) findViewById(R.id.tv_fort_pass);
        btn_login = (Button) findViewById(R.id.btn_login);

        try {
            localDBHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try {

            localDBHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        tv_fort_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                final EditText input = new EditText(LoginActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                input.setHint("Enter Registered Mobile Number");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setMessage("Please Enter Your Mobile Number bellow")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                                if (input.getText().toString().trim().equals("") || input.getText().toString().trim().length() < 10) {
                                    Toast.makeText(LoginActivity.this, "Enter valid Mobile ", Toast.LENGTH_SHORT).show();
                                } else if (!Utiilties.isOnline(LoginActivity.this)) {
                                    Toast.makeText(LoginActivity.this, "Please go online !", Toast.LENGTH_SHORT).show();
                                } else {
                                    ForgetPasswordService.bindForgetPassword(new ForgetPasswordBinder() {
                                        @Override
                                        public void bindForgetPASS(String res) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                            dialog.dismiss();
                                            alertDialog.setMessage("" + res);
                                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            alertDialog.show();

                                        }
                                    });
                                    new ForgetPasswordService(LoginActivity.this).execute(input.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Forget Password");
                alert.show();
            }
        });

            }


    @SuppressLint("MissingPermission")
    public void readPhoneState() {

        try {
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) ;
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
        try {
            version = LoginActivity.this.getPackageManager().getPackageInfo(LoginActivity.this.getPackageName(), 0).versionName;
            Log.e("App Version : ", "" + version + " ( " + imei + " )");
            txtVersion.setText("App Version" + version + " ( " + imei + " )");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        readPhoneState();

    }

    public void Login(View view) {

        initLogin();
    }

    public void initLogin() {

        String[] param = new String[2];
        param[0] = userName.getText().toString();
        param[1] = userPass.getText().toString();

        UserId=userName.getText().toString().trim();

        if (param[0].trim().length() == 0) {
            userName.setError("This field is required");
        } else if (param[1].trim().length() == 0) {
            userPass.setError("This field is required");
        } else {
            if(Utiilties.isOnline(LoginActivity.this)) {
                new LoginTask().execute(param);
            }
            else {
                Toast.makeText(LoginActivity.this, "Please Turn On Intrnet", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private class LoginTask extends AsyncTask<String, Void, UserDetails> {

        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Authenticating...");
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param) {

            if (!Utiilties.isOnline(LoginActivity.this)){
                UserDetails userDetails= CommonPref.getUserDetails(LoginActivity.this);
                if (userDetails.getUserId().equals(userName.getText().toString().trim())  && userDetails.getUserPassword().equals(userPass.getText().toString().trim())){
                    userDetails.set_isAuthenticated(true);
                    return userDetails;
                }else {
                    return new UserDetails();
                }
            }
            else
                return WebServiceHelper.Login(param[0], param[1]);

            //return WebServiceHelper.Authenticate( param[0], param[1]);

        }

        @Override
        protected void onPostExecute(final UserDetails result) {

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (result==null){
                alertDialog.setTitle("Failed");
                alertDialog.setMessage("Either Server Problem or Something went Wrong and Check Internet !");
                alertDialog.show();
            }
            else if (result != null && result.is_isAuthenticated() == false) {

                alertDialog.setTitle("Failed");
                alertDialog.setMessage("User Id & Password not Matched");
                alertDialog.show();

            }
            else if (result != null && result.is_isAuthenticated() == true && result.getIsLogin().equals("Y")) {
                LogOut();
               /* alertDialog.setTitle("Already Logged");
                alertDialog.setMessage("You Already Logged In Another Device \n Please Log Out");
                alertDialog.show();*/

            }
            else {

                try {
                    GlobalVariables.LoggedUser = result;
                //    GlobalVariables.UserId = result.getUserId();
                    insertinShared(result);
                    new insertEmpProfileData().execute();
                    CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);
                        GlobalVariables.isLogged = true;
                        Intent iUserHome = new Intent(getApplicationContext(), MainActivity.class);
                       // iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NEW_TASK);
                    iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(iUserHome);

                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    public void insertinShared(UserDetails result){

        String Userid=result.getUserId().trim();
        //String Userrole=result.getUserRole();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", Userid ).commit();
        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ROLE", Userrole ).commit();

    }

    public class insertEmpProfileData extends AsyncTask<String, Void, ArrayList<ViewEmpProfile>> {

        public insertEmpProfileData() {
        }


        @Override
        protected ArrayList<ViewEmpProfile> doInBackground(String... params) {

            ArrayList<ViewEmpProfile> res = WebServiceHelper.EmpProfileLoder(CommonPref.getUserDetails(LoginActivity.this).getEmpNo());
            return res;

        }

        @Override
        protected void onPostExecute(ArrayList<ViewEmpProfile> result) {
            if (result.size() > 0) {
                viewEmpProfiles = result;
                CommonPref.setProfilePhoto(getApplicationContext(), result.get(0));
            }

        }
    }
    protected void LogOut()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        /*final EditText input = new EditText(LoginActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        input.setHint("You Already Logged In Another Device Please Log Out");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);*/

        builder.setMessage("Do you want to Log Out")
                .setCancelable(false)
                .setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        if (UserId.equals("")) {
                            Toast.makeText(LoginActivity.this, "Enter valid User Id ", Toast.LENGTH_SHORT).show();
                        } else if (!Utiilties.isOnline(LoginActivity.this)) {
                            Toast.makeText(LoginActivity.this, "Please go online !", Toast.LENGTH_SHORT).show();
                        } else {
                            ForgetPasswordService.bindForgetPassword(new ForgetPasswordBinder() {
                                @Override
                                public void bindForgetPASS(String res) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                    dialog.dismiss();
                                    alertDialog.setMessage("" + res);
                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alertDialog.show();

                                }
                            });
                            new LogoutService(LoginActivity.this).execute(UserId);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Already Logged IN /n Please Log Out");
        alert.show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                    }
                }).create().show();

    }



}
