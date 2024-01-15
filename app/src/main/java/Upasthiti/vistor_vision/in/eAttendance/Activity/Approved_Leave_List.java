package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Upasthiti.vistor_vision.in.eAttendance.Adaptor.PendingLeaveListAdaptor;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.PendingLeaveListData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Approved_Leave_List extends AppCompatActivity {
    Toolbar toolbar_gd;
    ProgressDialog pd;
    ListView list_leav_Approv_Itm;
    SwipeRefreshLayout swipeRefreshLayout;
    PendingLeaveListAdaptor pendingLeaveListAdaptor;
    ArrayList<PendingLeaveListData> pendingLeaveList;
    List<PendingLeaveListData> listFiltered = new ArrayList<PendingLeaveListData>();
    String TagLoginId,LeaveId,sts;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_leave_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = findViewById(R.id.toolbar_gd);
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Approved / Reject Leave List");
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

            TagLoginId= CommonPref.getUserDetails(Approved_Leave_List.this).getUserId();
            LeaveId="0";
            sts="1";
            list_leav_Approv_Itm=findViewById(R.id.list_leav_Approv_Itm);
            swipeRefreshLayout=findViewById(R.id.pullToRefresh);

            pendingLeaveList = new ArrayList<>();
            this.listFiltered=pendingLeaveList;
            pendingLeaveListAdaptor = new PendingLeaveListAdaptor(Approved_Leave_List.this, pendingLeaveList);
            list_leav_Approv_Itm.setAdapter(pendingLeaveListAdaptor);
            new GetPendingLeaveList().execute();

            list_leav_Approv_Itm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    Intent i = new Intent(Approved_Leave_List.this,LeaveApprove.class);
                    i.putExtra("leavedata",pendingLeaveList.get(position));
                    i.putExtra("leavests","A");
                    startActivity(i);

                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if(Utiilties.isOnline(Approved_Leave_List.this)) {
                            new GetPendingLeaveList().execute();
                            swipeRefreshLayout.setRefreshing(false);

                    }
                    else {
                        Toast.makeText(Approved_Leave_List.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }




    }

    public class GetPendingLeaveList extends AsyncTask<String, Void, ArrayList<PendingLeaveListData>> {

        private final ProgressDialog dialog = new ProgressDialog(Approved_Leave_List.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(Approved_Leave_List.this).create();

        public GetPendingLeaveList() {
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Leave List\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PendingLeaveListData> doInBackground(String... params) {
            ArrayList<PendingLeaveListData> res = null;
            try {
                if (Utiilties.isOnline(Approved_Leave_List.this) && Utiilties.isConnected()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        res = WebServiceHelper.pendingleaveListLoader(TagLoginId, LeaveId,sts);
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<PendingLeaveListData> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                if (result.isEmpty()) {
                    Toast.makeText(Approved_Leave_List.this, "Sorry! no record found.", Toast.LENGTH_SHORT).show();
                } else if (result != null) {
                    pendingLeaveList = result;
                    pendingLeaveListAdaptor.upDateEntries(pendingLeaveList);
                } else {
                    Toast.makeText(Approved_Leave_List.this.getApplicationContext(), "Error!! \n Either your connection is slow or error in Network Server", Toast.LENGTH_LONG).show();
                }


            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        MenuItem action_logout = menu.findItem(R.id.action_logout);
        action_logout.setVisible(false);
        searchViewItem.setVisible(true);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) { 
         //       pendingLeaveListAdaptor.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}