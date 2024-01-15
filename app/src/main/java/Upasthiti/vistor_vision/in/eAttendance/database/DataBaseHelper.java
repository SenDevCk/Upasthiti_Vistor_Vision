package Upasthiti.vistor_vision.in.eAttendance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import Upasthiti.vistor_vision.in.eAttendance.entity.BlockData;
import Upasthiti.vistor_vision.in.eAttendance.entity.DistrictData;
import Upasthiti.vistor_vision.in.eAttendance.entity.GenderData;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveData;
import Upasthiti.vistor_vision.in.eAttendance.entity.LogtypeData;
import Upasthiti.vistor_vision.in.eAttendance.entity.PostData;
import Upasthiti.vistor_vision.in.eAttendance.entity.ProjectData;
import Upasthiti.vistor_vision.in.eAttendance.entity.Show_Edit;
import Upasthiti.vistor_vision.in.eAttendance.entity.TimeData;
import Upasthiti.vistor_vision.in.eAttendance.entity.UserDetails;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkTypeData;


public class DataBaseHelper extends SQLiteOpenHelper {
    // The Android's default system path of your application database.
    private static String DB_PATH = "";// "/data/data/com.bih.nic.app.biharmunicipalcorporation/databases/";
    //private static String DB_NAME = "chatrawasInsp.db";
    // private static String DB_NAME = "AlertMSGDB";

    private static String DB_NAME = "BEDMC";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 3);
        Log.e("DataBaseHelper", "1");
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {

            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
            //CreateNewTables(db);

        } else {

            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS
                            | SQLiteDatabase.OPEN_READWRITE);


        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist() {


        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {

        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


    public long getUserCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from UserDetail", null);

            x = cur.getCount();

            cur.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return x;
    }


    public long insertUserDetails(UserDetails result) {

        long c = 0;
        try {


            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("UserId", result.getUserId());
            values.put("UserName", result.getUserName());
            values.put("UserNameHN", result.getUserNameHN());
            values.put("MobileNumber", result.getMobileNumber());
            values.put("Role", result.getUserRole());
            values.put("DistrictId", result.getDistrictCode());
            values.put("District_name", result.getDistName());


            String[] whereArgs = new String[]{result.getUserId()};

            c = db.update("UserDetail", values, "UserID=? ", whereArgs);

            if (!(c > 0)) {

                c = db.insert("UserDetail", null, values);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;

    }

    public ArrayList<LogtypeData> getlogtype() {
        ArrayList<LogtypeData> logtype = new ArrayList<LogtypeData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM LogType order by LogType asc", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                LogtypeData logtypeData = new LogtypeData();
                logtypeData.setLogCode(cur.getString(cur.getColumnIndex("LogCode")));
                logtypeData.setLogTyp((cur.getString(cur.getColumnIndex("LogType"))));
                logtype.add(logtypeData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {

        }
        return logtype;

    }

    public ArrayList<TimeData> gettime() {
        ArrayList<TimeData> timeData = new ArrayList<TimeData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM Times", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                TimeData timedata = new TimeData();
                timedata.setCode(cur.getString(cur.getColumnIndex("Code")));
                timedata.setWorkTime((cur.getString(cur.getColumnIndex("WorkTime"))));
                timeData.add(timedata);
            }

            cur.close();
            db.close();
        } catch (Exception e) {

        }
        return timeData;

    }

    public ArrayList<ProjectData> getproject() {
        ArrayList<ProjectData> projectData = new ArrayList<ProjectData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM Project", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                ProjectData projectData1 = new ProjectData();
                projectData1.setProjectCode(cur.getString(cur.getColumnIndex("ProjectCode")));
                projectData1.setProjectName((cur.getString(cur.getColumnIndex("ProjectName"))));
                projectData.add(projectData1);
            }

            cur.close();
            db.close();
        } catch (Exception e) {

        }
        return projectData;

    }

    public ArrayList<WorkTypeData> getWorkdata() {
        ArrayList<WorkTypeData> workTypeDataArrayList = new ArrayList<WorkTypeData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM LogWorkType Order by WorkType asc", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                WorkTypeData workTypeData = new WorkTypeData();
                workTypeData.setCode(cur.getString(cur.getColumnIndex("Code")));
                workTypeData.setWorkType((cur.getString(cur.getColumnIndex("WorkType"))));
                workTypeDataArrayList.add(workTypeData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {

        }
        return workTypeDataArrayList;

    }

    public ArrayList<DistrictData> getdistrict() {
        ArrayList<DistrictData> genderdetail = new ArrayList<DistrictData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM Districts order by DistName asc", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                DistrictData distdata = new DistrictData();
                distdata.set_Distcode(cur.getString(cur.getColumnIndex("DistCode")));
                distdata.set_DistNameEn((cur.getString(cur.getColumnIndex("DistName"))));
                genderdetail.add(distdata);
            }

            cur.close();
            db.close();
        } catch (Exception e) {

        }
        return genderdetail;

    }

    public ArrayList<LeaveData> getleave() {
        ArrayList<LeaveData> leavedetail = new ArrayList<LeaveData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM LeaveType", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                LeaveData leaveData = new LeaveData();
                leaveData.setLeavecode(cur.getString(cur.getColumnIndex("LeaveCode")));
                leaveData.setLeaveName((cur.getString(cur.getColumnIndex("LeaveName"))));
                leavedetail.add(leaveData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {

        }
        return leavedetail;

    }

    public long saveBlock(ArrayList<BlockData> blockList, String distcode) {

        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.delete("SubDivision", null, null);
        ArrayList<BlockData> blockData = blockList;
        ContentValues values = new ContentValues();

        if (blockData != null) {
            try {
                for (int i = 0; i < blockData.size(); i++) {

                    values.put("SubdivisionCode", blockData.get(i).getBlockcode());
                    values.put("SubdivisionName", blockData.get(i).getBlockname());
                    values.put("DistCode", distcode);
                   // values.put("AreaTYpe", areatype);
                    String[] whereArgs = new String[]{blockData.get(i).getBlockcode()};
                    c = db.update("SubDivision", values, "SubdivisionCode=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("SubDivision", null, values);
                    }

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long saveLogType(ArrayList<LogtypeData> logtypeDataArrayList) {

        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.delete("Post", null, null);
        ArrayList<LogtypeData> logtypeData = logtypeDataArrayList;
        ContentValues values = new ContentValues();

        if (logtypeData != null) {
            try {
                for (int i = 0; i < logtypeData.size(); i++) {

                    values.put("LogType", logtypeData.get(i).getLogTyp());
                    values.put("LogCode", logtypeData.get(i).getLogCode());
                    String[] whereArgs = new String[]{logtypeData.get(i).getLogCode()};
                    c = db.update("LogType", values, "LogCode=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("LogType", null, values);
                    }

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long saveTime(ArrayList<TimeData> timeDataArrayList) {

        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.delete("Post", null, null);
        ArrayList<TimeData> timeData = timeDataArrayList;
        ContentValues values = new ContentValues();

        if (timeData != null) {
            try {
                for (int i = 0; i < timeData.size(); i++) {

                    values.put("Code", timeData.get(i).getCode());
                    values.put("WorkTime", timeData.get(i).getWorkTime());
                    String[] whereArgs = new String[]{timeData.get(i).getCode()};
                    c = db.update("Times", values, "Code=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Times", null, values);
                    }
                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long savePost(ArrayList<PostData> postDataArrayList) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.delete("Post", null, null);
        ArrayList<PostData> postData = postDataArrayList;
        ContentValues values = new ContentValues();

        if (postData != null) {
            try {
                for (int i = 0; i < postData.size(); i++) {

                    values.put("PostName", postData.get(i).getPostName());
                    values.put("PostCode", postData.get(i).getPostCode());
                    values.put("PostType", postData.get(i).getPostType());
                    String[] whereArgs = new String[]{postData.get(i).getPostCode()};
                    c = db.update("Post", values, "PostCode=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Post", null, values);
                    }

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long saveProject(ArrayList<ProjectData> projectDataArrayList) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.delete("Project", null, null);
        ArrayList<ProjectData> projectData = projectDataArrayList;
        ContentValues values = new ContentValues();
        if (projectData != null) {
            try {
                for (int i = 0; i < projectData.size(); i++) {
                    values.put("ProjectCode", projectData.get(i).getProjectCode());
                    values.put("ProjectName", projectData.get(i).getProjectName());
                    String[] whereArgs = new String[]{projectData.get(i).getProjectCode()};
                    c = db.update("Project", values, "ProjectCode=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("Project", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }


    public long saveWorkType(ArrayList<WorkTypeData> workTypeDataArrayList) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.delete("LogWorkType", null, null);
        ArrayList<WorkTypeData> workTypeData = workTypeDataArrayList;
        ContentValues values = new ContentValues();
        if (workTypeData != null) {
            try {
                for (int i = 0; i < workTypeData.size(); i++) {
                    values.put("Code", workTypeData.get(i).getCode());
                    values.put("WorkType", workTypeData.get(i).getWorkType());
                    String[] whereArgs = new String[]{workTypeData.get(i).getCode()};
                    c = db.update("LogWorkType", values, "Code=?", whereArgs);
                    if (!(c > 0)) {

                        c = db.insert("LogWorkType", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }



    public ArrayList<BlockData> getBlockLocal( String discode) {
        ArrayList<BlockData> blockdetail = new ArrayList<BlockData>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db.rawQuery("SELECT * FROM SubDivision where DistCode='" + discode + "' order by SubdivisionName ", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                BlockData blkdata = new BlockData();
                blkdata.setBlockcode(cur.getString(cur.getColumnIndex("SubdivisionCode")));
                blkdata.setBlockname((cur.getString(cur.getColumnIndex("SubdivisionName"))));
                blockdetail.add(blkdata);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return blockdetail;
    }

    public ArrayList<PostData> getPostLocal() {
        ArrayList<PostData> postdetail = new ArrayList<PostData>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM Post", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                PostData post = new PostData();
                post.setPostCode(cur.getString(cur.getColumnIndex("PostCode")));
                post.setPostName((cur.getString(cur.getColumnIndex("PostName"))));
                post.setPostType((cur.getString(cur.getColumnIndex("PostType"))));
                postdetail.add(post);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return postdetail;
    }

    public ArrayList<GenderData>
    getgendertdetail() {
        ArrayList<GenderData> genderdetail = new ArrayList<GenderData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM Gender", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                GenderData genderd = new GenderData();
                genderd.setGenderId(cur.getString(cur.getColumnIndex("GenderID")));
                genderd.setGendername((cur.getString(cur.getColumnIndex("GenderName"))));
                genderdetail.add(genderd);
            }

            cur.close();
            db.close();
        } catch (Exception e) {

        }
        return genderdetail;

    }

    public String getCurrentDate() {

        String date;
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String dayString = "";
        String monthString = "";
        if (day < 10) dayString = "0" + day;
        else dayString = day + "";
        if (month < 10) monthString = "0" + month;
        else monthString = month + "";
        int h = cal.get(Calendar.HOUR);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);
        String hour = "";
        String minute = "";
        String second = "";
        if (h < 10) hour = "0" + h;
        else hour = h + "";
        if (m < 10) minute = "0" + m;
        else minute = m + "";
        if (s < 10) second = "0" + s;
        else second = s + "";
        date = dayString + "-" + monthString + "-" + year;
        return date;
    }

    public long getPendingUpload_Count(String userid) {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from NewEntry where latitude IS NOT NULL and entryBy= '" + userid + "' ", null);
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return x;
    }

    public ArrayList<Show_Edit> getEntryListForEdit() {


        ArrayList<Show_Edit> typeList = new ArrayList<Show_Edit>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
//                            "SELECT BenName,Fatherhusb_Name,entryDate from NewEntry", null);
                            "SELECT * from NewEntry", null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Show_Edit gen = new Show_Edit();
                gen.setBenName(cur.getString(cur
                        .getColumnIndex("BenName")));

                gen.setBenFName(cur.getString(cur
                        .getColumnIndex("Fatherhusb_Name")));

                gen.setDate(cur.getString(cur
                        .getColumnIndex("entryDate")));

                gen.setAadharNo(cur.getString(cur.getColumnIndex("AadharNo")));
                gen.setId(cur.getInt(cur.getColumnIndex("Id")));


                typeList.add(gen);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return typeList;
    }

    public long deleteDataUploaded(String trim) {
        long c=-1;
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            c=db.delete("NewEntry","AadharNo=?",new String[]{trim.trim()});
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }

    public long deleteFromDB(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        long c = db.delete("NewEntry", "Id = " + id, null);
        if (c > 0) return c;
        else return 0;
    }

    public String getGenderName(String genderId) {
        SQLiteDatabase db=null;
        Cursor cur=null;
        String genname=null;
        try {
            db = this.getReadableDatabase();
            cur = db.rawQuery("SELECT GenderName FROM Gender where GenderID='" + genderId + "'", null);
            while (cur.moveToNext()) {

                genname=cur.getString(cur.getColumnIndex("GenderName"));

            }

        } catch (Exception e) {
            e.printStackTrace();
            return genname;
        }finally {
            cur.close();
            db.close();
        }
        return genname;
    }

    public String getward(String wardid, String pancode) {
        SQLiteDatabase db=null;
        Cursor cur=null;
        String wardname=null;
        try {
            db = this.getReadableDatabase();
            cur = db.rawQuery("SELECT WardName FROM Ward where WardCode='" + wardid + "' and PanchayatCode='" + pancode + "' ", null);
            while (cur.moveToNext()) {

                wardname=cur.getString(cur.getColumnIndex("WardName"));

            }

        } catch (Exception e) {
            e.printStackTrace();
            return wardname;
        }finally {
            cur.close();
            db.close();
        }
        return wardname;
    }


    public String getCatName(String catId) {
        SQLiteDatabase db=null;
        Cursor cur=null;
        String CatName=null;
        try {
            db = this.getReadableDatabase();
            cur = db.rawQuery("SELECT Cat_Name FROM Category where Cat_Id='" + catId + "'", null);
            while (cur.moveToNext()) {

                CatName=cur.getString(cur.getColumnIndex("Cat_Name"));

            }

        } catch (Exception e) {
            e.printStackTrace();
            return CatName;
        }finally {
            cur.close();
            db.close();
        }
        return CatName;
    }




}