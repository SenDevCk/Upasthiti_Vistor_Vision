package Upasthiti.vistor_vision.in.eAttendance.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.AttendanceSummeryReportData;
import Upasthiti.vistor_vision.in.eAttendance.interfaces.AttSummeryReportBinder;

public class AttSummeryReportService extends AsyncTask<String,Void, ArrayList<AttendanceSummeryReportData>> {
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    static AttSummeryReportBinder attSummeryReportBinder;
    boolean flag;
    public AttSummeryReportService(Activity activity, boolean flag){
       this.activity=activity;
       this.flag=flag;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (flag) {
            dialog1.setMessage("Getting Report...");
            dialog1.setCancelable(false);
            dialog1.show();
        }
    }

    @Override
    protected ArrayList<AttendanceSummeryReportData> doInBackground(String... strings) {
        ArrayList<AttendanceSummeryReportData> result = null;
        try {
            if (Utiilties.isOnline(activity)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        result = WebServiceHelper.getAttSummeryReportData(strings);
                }else{
                    alertDialog.setMessage("Your device must have atleast Kitkat or Above Version");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }else{
                Log.d("log", "No Internet Connection !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<AttendanceSummeryReportData> res) {
        super.onPostExecute(res);
        if (res != null) {
            if (dialog1.isShowing() && flag) dialog1.dismiss();
            if (res == null) {
                attSummeryReportBinder.cancleAttReportBinding();
            } else {
                if (res.size() > 0) {
                    attSummeryReportBinder.bindAttReport(res);
                } else {
                    Toast.makeText(activity, "No Report Found !", Toast.LENGTH_SHORT).show();
                    attSummeryReportBinder.cancleAttReportBinding();
                }
            }
        }
        else {
            Toast.makeText(activity, "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_SHORT).show();
        }
    }
   public static void bindAttSummeryReport(AttSummeryReportBinder attSummeryReportBinder1){
       attSummeryReportBinder=attSummeryReportBinder1;
   }
}
