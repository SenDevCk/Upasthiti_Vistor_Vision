package Upasthiti.vistor_vision.in.eAttendance.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveStatusData;
import Upasthiti.vistor_vision.in.eAttendance.interfaces.LeaveStatusBinder;


public class LeaveStatusService extends AsyncTask<String, Void, ArrayList<LeaveStatusData>> {
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    static LeaveStatusBinder contributionReportBinder;
   String EMPNo="";
    public LeaveStatusService(Activity activity){
       this.activity=activity;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();
        EMPNo= CommonPref.getUserDetails(activity).getEmpNo();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

            dialog1.setMessage("Getting Report...");
            dialog1.setCancelable(false);
            dialog1.show();

    }

    @Override
    protected ArrayList<LeaveStatusData> doInBackground(String... strings) {
        ArrayList<LeaveStatusData> result = null;
        if (Utiilties.isOnline(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result = WebServiceHelper.getLeaveStatusData(EMPNo);
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
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<LeaveStatusData> res) {
        super.onPostExecute(res);
        if (dialog1.isShowing())dialog1.dismiss();
        if (res==null){
            contributionReportBinder.cancleReportBinding();
        }else{
            if (res.size()>0){
                contributionReportBinder.bindReport(res);
            }else{
                Toast.makeText(activity, "No Report Found !", Toast.LENGTH_SHORT).show();
                contributionReportBinder.cancleReportBinding();
            }
        }
    }
   public static void bindGrivanceReport(LeaveStatusBinder userContributionReportBinder){
       contributionReportBinder=userContributionReportBinder;
   }
}
