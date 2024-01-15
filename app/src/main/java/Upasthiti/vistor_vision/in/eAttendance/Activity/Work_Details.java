package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.EmpListAdaptor;
import Upasthiti.vistor_vision.in.eAttendance.Adaptor.WorkListAdaptor;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.DistrictData;
import Upasthiti.vistor_vision.in.eAttendance.entity.EmployeeListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.PostData;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkListData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Work_Details extends AppCompatActivity {
    Toolbar toolbar_wrklst;
    ProgressDialog pd;
    ListView list_wrk_Itm;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<WorkListData> WrkListData;
    WorkListAdaptor workListAdaptor;
    ImageView lft_arrow,right_arrow;
    String Empid="",distId = "",Month="",CurrentMonth="";
    int mnt;
    TextView tv_mnth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_wrklst = findViewById(R.id.toolbar_wrklst);
            toolbar_wrklst.setTitle(getResources().getString(R.string.app_name));
            toolbar_wrklst.setSubtitle("Work List");
            toolbar_wrklst.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_wrklst.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_wrklst);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar_wrklst.setNavigationOnClickListener(new View.OnClickListener() {
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

        list_wrk_Itm = findViewById(R.id.list_wrk_Itm);
        tv_mnth = findViewById(R.id.tv_mnth);
        lft_arrow = findViewById(R.id.lft_arrow);
        right_arrow = findViewById(R.id.right_arrow);
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        distId= CommonPref.getUserDetails(Work_Details.this).getDistrictCode();
        EmployeeListData data =(EmployeeListData)getIntent().getSerializableExtra("empdata");
        Empid=data.getEmpNo();
        Month= Utiilties.getcurrentMonthName();
        CurrentMonth= Utiilties.getCurrentMonth();
        mnt= Integer.parseInt(CurrentMonth);
        tv_mnth.setText(Month);

        WrkListData = new ArrayList<>();
        workListAdaptor = new WorkListAdaptor(Work_Details.this, WrkListData);
        list_wrk_Itm.setAdapter(workListAdaptor);

        if(Utiilties.isOnline(Work_Details.this)) {
            new GetWorkList().execute();
        }
        else {
            Toast.makeText(Work_Details.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
        }


        list_wrk_Itm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Empid = WrkListData.get(position).getEmpNo();
                Intent i = new Intent(Work_Details.this, Approve_Reject_Work.class);
                i.putExtra("workdata", WrkListData.get(position));
                startActivity(i);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Utiilties.isOnline(Work_Details.this)) {

                    new GetWorkList().execute();

                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    Toast.makeText(Work_Details.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lft_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utiilties.isOnline(Work_Details.this)) {
                    int m=mnt-1;
                    if(m==1)
                    {
                        tv_mnth.setText(getResources().getString(R.string.jan));
                        mnt=1;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==2)
                    {
                        tv_mnth.setText(getResources().getString(R.string.fab));
                        mnt=2;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==3)
                    {
                        tv_mnth.setText(getResources().getString(R.string.mar));
                        mnt=3;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==4)
                    {
                        tv_mnth.setText(getResources().getString(R.string.apr));
                        mnt=4;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==5)
                    {
                        tv_mnth.setText(getResources().getString(R.string.may));
                        mnt=5;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==6)
                    {
                        tv_mnth.setText(getResources().getString(R.string.jun));
                        mnt=6;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==7)
                    {
                        tv_mnth.setText(getResources().getString(R.string.jul));
                        mnt=7;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==8)
                    {
                        tv_mnth.setText(getResources().getString(R.string.aug));
                        mnt=8;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==9)
                    {
                        tv_mnth.setText(getResources().getString(R.string.spt));
                        mnt=9;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==10)
                    {
                        tv_mnth.setText(getResources().getString(R.string.oct));
                        mnt=10;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==11)
                    {
                        tv_mnth.setText(getResources().getString(R.string.nov));
                        mnt=11;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==12)
                    {
                        tv_mnth.setText(getResources().getString(R.string.dec));
                        mnt=12;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                }
                else {
                    Toast.makeText(Work_Details.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utiilties.isOnline(Work_Details.this)) {
                    int m=mnt+1;
                    if(m==1)
                    {
                        tv_mnth.setText(getResources().getString(R.string.jan));
                        mnt=1;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==2)
                    {
                        tv_mnth.setText(getResources().getString(R.string.fab));
                        mnt=2;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==3)
                    {
                        tv_mnth.setText(getResources().getString(R.string.mar));
                        mnt=3;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==4)
                    {
                        tv_mnth.setText(getResources().getString(R.string.apr));
                        mnt=4;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==5)
                    {
                        tv_mnth.setText(getResources().getString(R.string.may));
                        mnt=5;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==6)
                    {
                        tv_mnth.setText(getResources().getString(R.string.jun));
                        mnt=6;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==7)
                    {
                        tv_mnth.setText(getResources().getString(R.string.jul));
                        mnt=7;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==8)
                    {
                        tv_mnth.setText(getResources().getString(R.string.aug));
                        mnt=8;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==9)
                    {
                        tv_mnth.setText(getResources().getString(R.string.spt));
                        mnt=9;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==10)
                    {
                        tv_mnth.setText(getResources().getString(R.string.oct));
                        mnt=10;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==11)
                    {
                        tv_mnth.setText(getResources().getString(R.string.nov));
                        mnt=11;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                    else if(m==12)
                    {
                        tv_mnth.setText(getResources().getString(R.string.dec));
                        mnt=12;
                        list_wrk_Itm.setAdapter(workListAdaptor);
                        new GetWorkList().execute();
                    }
                }
                else {
                    Toast.makeText(Work_Details.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    public class GetWorkList extends AsyncTask<String, Void, ArrayList<WorkListData>> {

        private final ProgressDialog dialog = new ProgressDialog(Work_Details.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(Work_Details.this).create();

        public GetWorkList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Employee List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<WorkListData> doInBackground(String... params) {
            ArrayList<WorkListData> res = WebServiceHelper.workListLoader(Empid,distId,String.valueOf(mnt));
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<WorkListData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if (result.isEmpty()) {
                    list_wrk_Itm.setAdapter(null);
                    Toast.makeText(Work_Details.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                } else if (result != null) {
                    WrkListData = result;
                    workListAdaptor.upDateEntries(WrkListData);
                } else {
                    Toast.makeText(Work_Details.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                }


            }

        }


    }
}