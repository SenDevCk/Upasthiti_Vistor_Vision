package Upasthiti.vistor_vision.in.eAttendance.Activity;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Objects;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.EmpListAdaptor;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.DistrictData;
import Upasthiti.vistor_vision.in.eAttendance.entity.EmployeeListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.PostData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Employee_List extends AppCompatActivity {
    Toolbar toolbar_gd;
    ProgressDialog pd;
    ListView list_emp_Itm;
    LinearLayout ll_post,ll_dist;
    SwipeRefreshLayout swipeRefreshLayout;
    Spinner sp_post,sp_dst;
    ArrayList<DistrictData> DistList = new ArrayList<>();
    ArrayAdapter<String> districtadapter;
    EmpListAdaptor empListAdaptor;
    ArrayList<EmployeeListData> EmpListData;
    ArrayList<PostData> postList = new ArrayList<>();
    String Empid="",UserId = "",UserRole="",PostCode="",PostName="",distId="";
    boolean isPost=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = findViewById(R.id.toolbar_gd_emplst);
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Employee List");
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


        UserRole = (CommonPref.getUserDetails(Employee_List.this).getUserRole());

        list_emp_Itm = findViewById(R.id.list_emp_Itm);
        sp_post = findViewById(R.id.sp_post);
        sp_dst = findViewById(R.id.sp_dst);
        ll_post = findViewById(R.id.ll_post);
        ll_dist = findViewById(R.id.ll_dist);
        loadDistrict();
        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        EmpListData = new ArrayList<>();
        empListAdaptor = new EmpListAdaptor(Employee_List.this, EmpListData);
        list_emp_Itm.setAdapter(empListAdaptor);

        if(UserRole.equals("DSTADM")) {
            distId=CommonPref.getUserDetails(Employee_List.this).getDistrictCode();
            if(Utiilties.isOnline(Employee_List.this))
            {
                new GetEmpList().execute();
            }
            else {
                Toast.makeText(Employee_List.this, "please check your network connection ...", Toast.LENGTH_SHORT).show();
            }
            ll_dist.setVisibility(View.GONE);
            ll_post.setVisibility(View.VISIBLE);
            if (postList.size() > 0) {
                loadPostSPinner();
            } else {
                isPost = false;
                if (Utiilties.isOnline(Employee_List.this)) {
                    new LoadPostdata().execute();
                } else {
                    Toast.makeText(Employee_List.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                    PostCode = "";
                }
            }
        }

        UserId="0";
           list_emp_Itm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Empid = EmpListData.get(position).getEmpNo();
                    if(UserRole.equals("DSTADM"))
                    {
                        Intent i = new Intent(Employee_List.this, Work_Details.class);
                        i.putExtra("empdata", EmpListData.get(position));
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(Employee_List.this, Monthly_Report.class);
                        i.putExtra("empdata", EmpListData.get(position));
                        i.putExtra("postcode", PostCode);
                        i.putExtra("postname", PostName);
                        startActivity(i);
                    }



            }
        });

        list_emp_Itm.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(UserRole.equals("ADM"))
                {
                    Intent i = new Intent(Employee_List.this, Emp_Profile.class);
                    i.putExtra("empdata", EmpListData.get(position));
                    i.putExtra("is_web","Y" );
                    startActivity(i);
                }
                else if(UserRole.equals("DSTADM"))
                {
                    Intent i = new Intent(Employee_List.this, Monthly_Report.class);
                    i.putExtra("empdata", EmpListData.get(position));
                    i.putExtra("postcode", PostCode);
                    i.putExtra("postname", PostName);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(Employee_List.this, Emp_Profile.class);
                    i.putExtra("empdata", EmpListData.get(position));
                    startActivity(i);
                }

                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    if(Utiilties.isOnline(Employee_List.this)) {
                        if(PostCode!=null) {
                            new GetEmpList().execute();
                        }
                        else {
                            Toast.makeText(Employee_List.this, "Please Select the District !", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        Toast.makeText(Employee_List.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
        });



    }


    public class GetEmpList extends AsyncTask<String, Void, ArrayList<EmployeeListData>> {

        private final ProgressDialog dialog = new ProgressDialog(Employee_List.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(Employee_List.this).create();

        public GetEmpList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Employee List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<EmployeeListData> doInBackground(String... params) {
            ArrayList<EmployeeListData> res = WebServiceHelper.empListLoader(PostCode,distId);
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<EmployeeListData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if (result.isEmpty()) {
                    list_emp_Itm.setAdapter(null);
                    Toast.makeText(Employee_List.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                } else if (result != null) {

                    EmpListData = result;
                    empListAdaptor.upDateEntries(EmpListData);
                } else {
                    Toast.makeText(Employee_List.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
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

                    if(postList.size()>0 ) {
                        loadPostSPinner();
                        ll_post.setVisibility(View.VISIBLE);
                        list_emp_Itm.setAdapter(empListAdaptor);
                        if(Utiilties.isOnline(Employee_List.this))
                        {
                            new GetEmpList().execute();
                        }
                        else {
                            Toast.makeText(Employee_List.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        isPost = false;
                        if (Utiilties.isOnline(Employee_List.this)) {
                            new LoadPostdata().execute();
                            new GetEmpList().execute();
                            ll_post.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(Employee_List.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                            PostCode = "";
                            ll_post.setVisibility(View.GONE);
                        }
                    }
                }
                else
                {
                    distId="";
                    ll_post.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class LoadPostdata extends AsyncTask<String, Void, ArrayList<PostData>> {
        ArrayList<PostData> blockDataArrayList=new  ArrayList<PostData>();
        private final ProgressDialog dialog = new ProgressDialog(
                Employee_List.this);

        LoadPostdata() {

        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected ArrayList<PostData> doInBackground(String... param) {

            this.blockDataArrayList= WebServiceHelper.getPost();

            return this.blockDataArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<PostData> result) {
            if (pd.isShowing()) {
                pd.dismiss();

            }

            if(result!=null)
            {
                isPost=true;
                if (result.size()>0) {

                    DataBaseHelper placeData = new DataBaseHelper(Employee_List.this);
                    long i=placeData.savePost(result);
                    if(i>0)
                    {
                        loadPostSPinner();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    public void loadPostSPinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        postList = dataBaseHelper.getPostLocal();
        ArrayList<String> plist = new ArrayList<>();
        if (postList.size() > 0) {
            plist.add("-- Choose Post --");
        }
        for (int i = 0; i < postList.size(); i++) {
            plist.add(postList.get(i).getPostName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, plist);
        sp_post.setAdapter(spinnerAdapter);
        sp_post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {
                    PostData postcode = postList.get(arg2 - 1);
                    PostCode = postcode.getPostCode();
                    PostName=postcode.getPostName();
                    list_emp_Itm.setAdapter(empListAdaptor);
                    if(Utiilties.isOnline(Employee_List.this))
                    {
                        new GetEmpList().execute();
                    }
                    else {
                        Toast.makeText(Employee_List.this, "please check your network connection ...", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    PostCode="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void clearlist()
    {
        EmpListData.clear();
        empListAdaptor.notifyDataSetChanged();
    }

}