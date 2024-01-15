package Upasthiti.vistor_vision.in.eAttendance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.CommonPref;
import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.SignUpService;
import Upasthiti.vistor_vision.in.eAttendance.asyncTasks.UpdateLatLongService;
import Upasthiti.vistor_vision.in.eAttendance.database.DataBaseHelper;
import Upasthiti.vistor_vision.in.eAttendance.database.WebServiceHelper;
import Upasthiti.vistor_vision.in.eAttendance.entity.BlockData;
import Upasthiti.vistor_vision.in.eAttendance.entity.DistrictData;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class Update_Lat_Long extends AppCompatActivity {
    Toolbar toolbar_gd;
    ProgressDialog pd;
    EditText edt_add;
    Spinner spn_blk;
    ImageView imgview;
    Bitmap im1;
    byte[] imageData1;
    int ThumbnailSize = 500;
    private final static int CAMERA_PIC = 99;
    Button btn_save, btn_cancel;
    boolean isBlock=false;

    String error_msg="",distId="",Blkcode="",lat="",Long="",userRole="",Address="";


    ArrayList<BlockData> blockList = new ArrayList<>();
    ArrayAdapter<String> Blkadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lat_long);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            toolbar_gd.setTitle(getResources().getString(R.string.app_name));
            toolbar_gd.setSubtitle("Update LatLong");
            toolbar_gd.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_gd.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_gd);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar_gd.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            pd = new ProgressDialog(this);
            pd.setCanceledOnTouchOutside(false);
            pd.setIndeterminateDrawable(getResources().getDrawable((R.drawable.my_progress)));
            pd.setMessage("Loading...");

            distId=CommonPref.getUserDetails(Update_Lat_Long.this).getDistrictCode();

            if (Utiilties.isOnline(Update_Lat_Long.this)) {
                new LoadBlockdata(distId).execute();
            } else {
                Toast.makeText(Update_Lat_Long.this, "Please Check the Internet", Toast.LENGTH_SHORT).show();
                Blkcode = "";
            }
        }

        edt_add=findViewById(R.id.edt_add);
        imgview = findViewById(R.id.imgview);
        spn_blk = findViewById(R.id.spn_blk);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
     //   loadBlockSPinner();
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utiilties.isGPSEnabled(Update_Lat_Long.this)) {
                    Utiilties.displayPromptForEnablingGPS(Update_Lat_Long.this);
                } else {
                    Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
                    iCamera.putExtra("KEY_PIC", "1");
                    iCamera.putExtra("frntcam", "Y");
                    startActivityForResult(iCamera, CAMERA_PIC);
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    if(Utiilties.isOnline(Update_Lat_Long.this))
                    {
                        new UpdateLatLongService(Update_Lat_Long.this).execute(lat,Long,edt_add.getText().toString().trim(),distId, Blkcode);
                    }
                    else {
                        Toast.makeText(Update_Lat_Long.this, "Please Check Internet connection !", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Update_Lat_Long.this, ""+error_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class LoadBlockdata extends AsyncTask<String, Void, ArrayList<BlockData>> {
        String distCode="";
        // String blockCode="";
        ArrayList<BlockData> blockDataArrayList=new  ArrayList<BlockData>();
        private final ProgressDialog dialog = new ProgressDialog(
                Update_Lat_Long.this);

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

            this.blockDataArrayList= WebServiceHelper.getSubDivision(distCode);

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

                    DataBaseHelper placeData = new DataBaseHelper(Update_Lat_Long.this);
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
            blist.add("-- Choose Block --");
        }
        for (int i = 0; i < blockList.size(); i++) {
            blist.add(blockList.get(i).getBlockname());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, blist);
        spn_blk.setAdapter(spinnerAdapter);
        spn_blk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (arg2 > 0) {
                    BlockData subdivion = blockList.get(arg2 - 1);
                    Blkcode = subdivion.getBlockcode();

                }
                else {
                    Blkcode="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            im1 = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            imgview.setScaleType(ImageView.ScaleType.FIT_XY);
                            imgview.setImageBitmap(Utiilties.GenerateThumbnail(im1, ThumbnailSize, ThumbnailSize));
                            //  viewIMG1.setVisibility(View.VISIBLE);
                            imageData1 = imgData;
                            lat = data.getStringExtra("Lat");
                            Long = data.getStringExtra("Lng");
                            getLocationName(Double.parseDouble(lat), Double.parseDouble(Long));
                            break;
                    }
                }
        }

    }

    private void getLocationName(double latitude1, double longitude1) {
        try {

            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<android.location.Address> addresses = geo.getFromLocation(latitude1, longitude1, 1);
            if (addresses.isEmpty()) {
                edt_add.setText("Waiting for Location...");
            } else {
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    edt_add.setText(new StringBuilder().append("Address : ").append(address + ",").append(city + ",").append(state + ",").append(country + ",").append(postalCode + ",").append(knownName));
                    Address = address + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        boolean validated = false;
          if (Blkcode.equals("")){
            Toast.makeText(this, "Select Block !", Toast.LENGTH_SHORT).show();
            error_msg="Select Block !";
            validated=false;
        }
        else if (edt_add.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Take photo  !", Toast.LENGTH_SHORT).show();
            error_msg="Take Photo !";
            validated = false;
        }
        else {
            validated = true;
        }
        return validated;
    }
}