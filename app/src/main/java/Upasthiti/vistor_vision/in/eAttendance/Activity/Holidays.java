package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.HolidayListAdaptor;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.HolidaysData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Holidays extends AppCompatActivity {
    Toolbar toolbar_gd;
    ProgressDialog pd;
    ListView list_holiday_Itm;
    SwipeRefreshLayout swipeRefreshLayout;
    HolidayListAdaptor adapter;
    ArrayList<HolidaysData> holidaysData;
    String Month="",CurrentMonth="";
    ImageView lft_arrow,right_arrow;
    TextView tv_mnth;
    int mnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = findViewById(R.id.toolbar_gd);
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("HoliDays List");
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
        list_holiday_Itm = findViewById(R.id.list_holiday_Itm);
        lft_arrow = findViewById(R.id.lft_arrow);
        right_arrow = findViewById(R.id.right_arrow);
        tv_mnth = findViewById(R.id.tv_mnth);
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);

        Month= Utiilties.getcurrentMonthName();
        CurrentMonth= Utiilties.getCurrentMonth();
         mnt= Integer.parseInt(CurrentMonth);
        tv_mnth.setText(Month);

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setIndeterminateDrawable(getResources().getDrawable((R.drawable.my_progress)));
        pd.setMessage("Loading...");

        holidaysData = new ArrayList<>();
        adapter = new HolidayListAdaptor(Holidays.this, holidaysData);
        list_holiday_Itm.setAdapter(adapter);


            if(Utiilties.isOnline(Holidays.this)) {
                new GetHolidayList().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
            else {
                Toast.makeText(Holidays.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
            }


        lft_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(Utiilties.isOnline(Holidays.this)) {
                        int m=mnt-1;
                        if(m==1)
                        {
                            tv_mnth.setText(getResources().getString(R.string.jan));
                            mnt=1;
                            new GetHolidayList().execute();
                        }
                        else if(m==2)
                        {
                            tv_mnth.setText(getResources().getString(R.string.fab));
                            mnt=2;
                            new GetHolidayList().execute();
                        }
                        else if(m==3)
                        {
                            tv_mnth.setText(getResources().getString(R.string.mar));
                            mnt=3;
                            new GetHolidayList().execute();
                        }
                        else if(m==4)
                        {
                            tv_mnth.setText(getResources().getString(R.string.apr));
                            mnt=4;
                            new GetHolidayList().execute();
                        }
                        else if(m==5)
                        {
                            tv_mnth.setText(getResources().getString(R.string.may));
                            mnt=5;
                            new GetHolidayList().execute();
                        }
                        else if(m==6)
                        {
                            tv_mnth.setText(getResources().getString(R.string.jun));
                            mnt=6;
                            new GetHolidayList().execute();
                        }
                        else if(m==7)
                        {
                            tv_mnth.setText(getResources().getString(R.string.jul));
                            mnt=7;
                            new GetHolidayList().execute();
                        }
                        else if(m==8)
                        {
                            tv_mnth.setText(getResources().getString(R.string.aug));
                            mnt=8;
                            new GetHolidayList().execute();
                        }
                        else if(m==9)
                        {
                            tv_mnth.setText(getResources().getString(R.string.spt));
                            mnt=9;
                            new GetHolidayList().execute();
                        }
                        else if(m==10)
                        {
                            tv_mnth.setText(getResources().getString(R.string.oct));
                            mnt=10;
                            new GetHolidayList().execute();
                        }
                        else if(m==11)
                        {
                            tv_mnth.setText(getResources().getString(R.string.nov));
                            mnt=11;
                            new GetHolidayList().execute();
                        }
                        else if(m==12)
                        {
                            tv_mnth.setText(getResources().getString(R.string.dec));
                            mnt=12;
                            new GetHolidayList().execute();
                        }
                    }
                    else {
                        Toast.makeText(Holidays.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(Utiilties.isOnline(Holidays.this)) {
                        int m=mnt+1;
                        if(m==1)
                        {
                            tv_mnth.setText(getResources().getString(R.string.jan));
                            mnt=1;
                            new GetHolidayList().execute();
                        }
                        else if(m==2)
                        {
                            tv_mnth.setText(getResources().getString(R.string.fab));
                            mnt=2;
                            new GetHolidayList().execute();
                        }
                        else if(m==3)
                        {
                            tv_mnth.setText(getResources().getString(R.string.mar));
                            mnt=3;
                            new GetHolidayList().execute();
                        }
                        else if(m==4)
                        {
                            tv_mnth.setText(getResources().getString(R.string.apr));
                            mnt=4;
                            new GetHolidayList().execute();
                        }
                        else if(m==5)
                        {
                            tv_mnth.setText(getResources().getString(R.string.may));
                            mnt=5;
                            new GetHolidayList().execute();
                        }
                        else if(m==6)
                        {
                            tv_mnth.setText(getResources().getString(R.string.jun));
                            mnt=6;
                            new GetHolidayList().execute();
                        }
                        else if(m==7)
                        {
                            tv_mnth.setText(getResources().getString(R.string.jul));
                            mnt=7;
                            new GetHolidayList().execute();
                        }
                        else if(m==8)
                        {
                            tv_mnth.setText(getResources().getString(R.string.aug));
                            mnt=8;
                            new GetHolidayList().execute();
                        }
                        else if(m==9)
                        {
                            tv_mnth.setText(getResources().getString(R.string.spt));
                            mnt=9;
                            new GetHolidayList().execute();
                        }
                        else if(m==10)
                        {
                            tv_mnth.setText(getResources().getString(R.string.oct));
                            mnt=10;
                            new GetHolidayList().execute();
                        }
                        else if(m==11)
                        {
                            tv_mnth.setText(getResources().getString(R.string.nov));
                            mnt=11;
                            new GetHolidayList().execute();
                        }
                        else if(m==12)
                        {
                            tv_mnth.setText(getResources().getString(R.string.dec));
                            mnt=12;
                            new GetHolidayList().execute();
                        }
                    }
                    else {
                        Toast.makeText(Holidays.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    if(Utiilties.isOnline(Holidays.this)) {
                        new GetHolidayList().execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        Toast.makeText(Holidays.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    public class GetHolidayList extends AsyncTask<String, Void, ArrayList<HolidaysData>> {

        private final ProgressDialog dialog = new ProgressDialog(Holidays.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(Holidays.this).create();

        public GetHolidayList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading HoliDays\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<HolidaysData> doInBackground(String... params) {
            ArrayList<HolidaysData> res = WebServiceHelper.HolidayLoader(mnt);
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<HolidaysData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if (result.isEmpty()) {
                    Toast.makeText(Holidays.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                } else if (result != null) {
                    holidaysData = result;
                    adapter.upDateEntries(holidaysData);
                } else {
                    Toast.makeText(Holidays.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                }

            }

        }


    }
}