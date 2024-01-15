package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.AttendanceListAdaptor;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.BlockData;
import Upasthiti.vistor_vision.in.eAttendance.entity.DistrictData;
import Upasthiti.vistor_vision.in.eAttendance.entity.MarkedAttendanceData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class DailyReportList extends AppCompatActivity {
    Toolbar toolbar_gd;
    ProgressDialog pd;
    ListView list_Att_Itm;
    SwipeRefreshLayout swipeRefreshLayout;
    Spinner sp_dst,spn_subDiv;
    AttendanceListAdaptor adapter;
    ArrayList<MarkedAttendanceData> AttendanceListData;
    ArrayList<DistrictData> DistList = new ArrayList<>();
    ArrayAdapter<String> districtadapter;
    ArrayList<BlockData> blockList = new ArrayList<>();
    String Empid = "", distId = "", sdmcode = "", UserId = "",UserRole="";
    String empName = "", Intime = "", OutTime = "", Inadd = "", OutAdd = "", InRemrk = "", outremrk = "", AttImg = "",AttOutImg = "";
    RelativeLayout re_filter;
    LinearLayout Rl_subDiv,ll_dist;
    boolean isBlock=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = findViewById(R.id.toolbar_gd);
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Today Attendance Report");
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

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setIndeterminateDrawable(getResources().getDrawable((R.drawable.my_progress)));
        pd.setMessage("Loading...");

        distId = CommonPref.getUserDetails(DailyReportList.this).getDistrictCode();
        UserRole = CommonPref.getUserDetails(DailyReportList.this).getUserRole();
        sdmcode= CommonPref.getUserDetails(DailyReportList.this).getSubDivCode();

        list_Att_Itm = findViewById(R.id.list_Att_Itm);
        re_filter = findViewById(R.id.re_filter);
        Rl_subDiv = findViewById(R.id.Rl_subDiv);
        ll_dist = findViewById(R.id.ll_dist);
        sp_dst = findViewById(R.id.sp_dst);
        spn_subDiv = findViewById(R.id.spn_subDiv);
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        AttendanceListData = new ArrayList<>();
        adapter = new AttendanceListAdaptor(DailyReportList.this, AttendanceListData);
        list_Att_Itm.setAdapter(adapter);

        if(UserRole.equals("ADM")){
            re_filter.setVisibility(View.VISIBLE);
            ll_dist.setVisibility(View.VISIBLE);
            Rl_subDiv.setVisibility(View.VISIBLE);
            loadDistrict();
            UserId="0";
        }
       else if(UserRole.equals("DSTADM")) {
            UserId = "0";
            Rl_subDiv.setVisibility(View.VISIBLE);
            re_filter.setVisibility(View.VISIBLE);
            ll_dist.setVisibility(View.GONE);
             distId = (CommonPref.getUserDetails(DailyReportList.this).getDistrictCode());
            try {
                if(Utiilties.isOnline(DailyReportList.this)) {
                    new GetAttendanceList().execute();
                }
                else {
                    Toast.makeText(DailyReportList.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }  catch (Exception e) {
                e.printStackTrace();
            }
            if (blockList.size() > 0) {
                loadBlockSPinner(distId);
            } else {
                isBlock = false;
                if (Utiilties.isOnline(DailyReportList.this)) {
                    new LoadBlockdata(distId).execute("");
                } else {
                    Toast.makeText(DailyReportList.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                    sdmcode = "";
                }
            }
        }
        else {
            re_filter.setVisibility(View.GONE);
            ll_dist.setVisibility(View.GONE);
            Rl_subDiv.setVisibility(View.GONE);
            UserId = (CommonPref.getUserDetails(DailyReportList.this).getEmpNo());
                if(Utiilties.isOnline(DailyReportList.this)) {
                    new GetAttendanceList().execute();
                }
                else {
                    Toast.makeText(this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }

        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    if(Utiilties.isOnline(DailyReportList.this)  ) {
                        new GetAttendanceList().execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        Toast.makeText(DailyReportList.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        list_Att_Itm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Empid = AttendanceListData.get(position).getEmpNo();
                empName = AttendanceListData.get(position).getUserName();
                Intime = AttendanceListData.get(position).getInTime();
                OutTime = AttendanceListData.get(position).getOutTime();
                Inadd = AttendanceListData.get(position).getInAddress();
                OutAdd = AttendanceListData.get(position).getOutaddress();
                InRemrk = AttendanceListData.get(position).getInRemark();
                outremrk = AttendanceListData.get(position).getOutRemark();
                AttImg = AttendanceListData.get(position).getInPhoto();
                AttOutImg = AttendanceListData.get(position).getOutPhoto();
                setUpDialog();
            }
        });


    }


    public class GetAttendanceList extends AsyncTask<String, Void, ArrayList<MarkedAttendanceData>> {

        private final ProgressDialog dialog = new ProgressDialog(DailyReportList.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(DailyReportList.this).create();

        public GetAttendanceList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Marked Attendance List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<MarkedAttendanceData> doInBackground(String... params) {
            ArrayList<MarkedAttendanceData> res = null;
            try {
                if (Utiilties.isOnline(DailyReportList.this)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        res = WebServiceHelper.markedAttendanceLoader(distId,sdmcode,UserId);
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
                    Toast.makeText(DailyReportList.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<MarkedAttendanceData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if (result.isEmpty()) {
                    list_Att_Itm.setAdapter(null);
                    Toast.makeText(DailyReportList.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                } else if (result != null) {
                    AttendanceListData = result;
                    adapter.upDateEntries(AttendanceListData);
                } else {
                    Toast.makeText(DailyReportList.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                }


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
        sp_dst.setAdapter(districtadapter);
        sp_dst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    DistrictData dist = DistList.get(i - 1);
                    distId = dist.get_Distcode();
                    try {
                        if(Utiilties.isOnline(DailyReportList.this)) {
                            new GetAttendanceList().execute();
                        }
                        else {
                            Toast.makeText(DailyReportList.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                        }
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (blockList.size() > 0)
                        loadBlockSPinner(distId);
                    else {
                        isBlock = false;
                        if (Utiilties.isOnline(DailyReportList.this)) {
                            new LoadBlockdata(distId).execute("");
                        } else {
                            Toast.makeText(DailyReportList.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                            sdmcode = "";
                        }
                    }
                    sdmcode = "";
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

    private class LoadBlockdata extends AsyncTask<String, Void, ArrayList<BlockData>> {
        String distCode="";
        // String blockCode="";
        ArrayList<BlockData> blockDataArrayList=new  ArrayList<BlockData>();
        private final ProgressDialog dialog = new ProgressDialog(
                DailyReportList.this);

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

            this.blockDataArrayList= WebServiceHelper.getSubDivision(distId);

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

                    DataBaseHelper placeData = new DataBaseHelper(DailyReportList.this);
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
            blist.add("-- Select Block --");
        }
        for (int i = 0; i < blockList.size(); i++) {
            blist.add(blockList.get(i).getBlockname());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, blist);
        spn_subDiv.setAdapter(spinnerAdapter);
        spn_subDiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (arg2 > 0) {
                    clearlist();
                    BlockData subdivion = blockList.get(arg2 - 1);
                    sdmcode = subdivion.getBlockcode();
                    try {
                        if(Utiilties.isOnline(DailyReportList.this)) {
                            new GetAttendanceList().execute();
                        }
                        else {
                            Toast.makeText(DailyReportList.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                        }
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    sdmcode="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void setUpDialog() {
        final Dialog setup_dialog = new Dialog(DailyReportList.this);
        setup_dialog.setContentView(R.layout.attendance_rpt_details_dialog);
        setup_dialog.setCancelable(false);
        // set values for custom dialog components - text, image and button
        ImageView close_setup = (ImageView) setup_dialog.findViewById(R.id.close_setup);
        final TextView tv_Gid = (TextView) setup_dialog.findViewById(R.id.tv_name);
        final TextView tv_intime = (TextView) setup_dialog.findViewById(R.id.tv_intime);
        final TextView tv_outtime = (TextView) setup_dialog.findViewById(R.id.tv_outtime);
        final TextView tv_inrmk = (TextView) setup_dialog.findViewById(R.id.tv_inrmk);
        final TextView tv_outrmk = (TextView) setup_dialog.findViewById(R.id.tv_outrmk);
        final TextView tv_inadd = (TextView) setup_dialog.findViewById(R.id.tv_inadd);
        final TextView tv_uotadd = (TextView) setup_dialog.findViewById(R.id.tv_uotadd);
        final LinearLayout ll_inphoto = (LinearLayout) setup_dialog.findViewById(R.id.ll_inphoto);
        final LinearLayout ll_outphoto = (LinearLayout) setup_dialog.findViewById(R.id.ll_outphoto);
        final ImageView attimg =(ImageView) setup_dialog.findViewById(R.id.img1);
        final ImageView attimgout =(ImageView) setup_dialog.findViewById(R.id.img2);
        ll_inphoto.setVisibility(View.VISIBLE);
        ll_outphoto.setVisibility(View.VISIBLE);
        tv_Gid.setText("Employee Name :" + " " + empName);
        tv_intime.setText("In Time :" + " " + Intime);
        tv_outtime.setText("Out Time :" + " " + OutTime);
        tv_inadd.setText("In Remarks :" + " " + InRemrk);
        tv_inrmk.setText("In Address :" + " " + Inadd);
        tv_uotadd.setText("Out Remarks :" + " " + outremrk);
        tv_outrmk.setText("Out Address :" + " " + OutAdd);
        Picasso.with(DailyReportList.this).load("http://www.bedmc.in/" + AttImg).error(R.drawable.noimage).into(attimg);
        Picasso.with(DailyReportList.this).load("http://www.bedmc.in/" + AttOutImg).error(R.drawable.noimage).into(attimgout);
        setAnimation(tv_Gid,tv_intime,tv_outtime,tv_inadd,tv_uotadd,tv_inrmk,tv_outrmk);

        close_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup_dialog.dismiss();
            }
        });

        setup_dialog.show();
    }
    private void setAnimation(View viewToAnimate, TextView tv_intime, TextView tv_outtime, TextView tv_inadd, TextView tv_uotadd, TextView tv_inrmk, TextView tv_outrmk)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

            Animation animation = AnimationUtils.loadAnimation(DailyReportList.this, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            tv_intime.startAnimation(animation);
        tv_outtime.startAnimation(animation);
        tv_inadd.startAnimation(animation);
        tv_uotadd.startAnimation(animation);
        tv_inrmk.startAnimation(animation);
        tv_outrmk.startAnimation(animation);

    }
private void clearlist()
{
    AttendanceListData.clear();
    adapter.notifyDataSetChanged();
}
}