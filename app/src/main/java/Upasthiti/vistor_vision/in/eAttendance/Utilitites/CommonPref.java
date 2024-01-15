package Upasthiti.vistor_vision.in.eAttendance.Utilitites;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import Upasthiti.vistor_vision.in.eAttendance.entity.UserDetails;
import Upasthiti.vistor_vision.in.eAttendance.entity.ViewEmpProfile;


public class CommonPref {

	static Context context;

	CommonPref() {

	}

	CommonPref(Context context) {
		CommonPref.context = context;
	}


	public static void setUserDetails(Context context, UserDetails userInfo) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("Password", userInfo.getUserPassword());
		editor.putString("EmpNo", userInfo.getEmpNo());
		editor.putString("Post_name", userInfo.getPost_name());
		editor.putString("PostCode", userInfo.getPostCode());
		editor.putString("Lat", userInfo.getLat());
		editor.putString("Long", userInfo.getLong());
		editor.putString("UserId", userInfo.getUserId());
		editor.putString("UserName", userInfo.getUserName());
		editor.putString("UserNameHN", userInfo.getUserNameHN());
		editor.putString("MobileNumber", userInfo.getMobileNumber());

		editor.putString("UserRole", userInfo.getUserRole());
		editor.putString("DistrictCode", userInfo.getDistrictCode());
		editor.putString("SubDivCode", userInfo.getSubDivCode());
		editor.putString("SubdivisionName", userInfo.getSubdivisionName());

		editor.putString("DistName", userInfo.getDistName());
		editor.putString("IMEI", userInfo.getIMEI());
		editor.putString("isLocked", userInfo.getIsLocked());

		editor.commit();

	}

	public static UserDetails getUserDetails(Context context) {

		String key = "_USER_DETAILS";
		UserDetails userInfo = new UserDetails();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		userInfo.setUserPassword(prefs.getString("Password", ""));
		userInfo.setEmpNo(prefs.getString("EmpNo", ""));
		userInfo.setPost_name(prefs.getString("Post_name", ""));
		userInfo.setPostCode(prefs.getString("PostCode", ""));
		userInfo.setLat(prefs.getString("Lat", ""));
		userInfo.setLong(prefs.getString("Long", ""));
		userInfo.setUserId(prefs.getString("UserId", ""));
		userInfo.setUserName(prefs.getString("UserName", ""));
		userInfo.setUserNameHN(prefs.getString("UserNameHN", ""));
		userInfo.setMobileNumber(prefs.getString("MobileNumber", ""));
		userInfo.setUserRole(prefs.getString("UserRole", ""));
		userInfo.setDistrictCode(prefs.getString("DistrictCode", ""));
		userInfo.setDistName(prefs.getString("DistName", ""));
		userInfo.setSubDivCode(prefs.getString("SubDivCode", ""));
		userInfo.setSubdivisionName(prefs.getString("SubdivisionName", ""));
		userInfo.setIMEI(prefs.getString("IMEI", ""));
		userInfo.setIsLocked(prefs.getString("isLocked", ""));

		return userInfo;
	}


	public static void setCheckUpdate(Context context, long dateTime) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();


		dateTime = dateTime + 1 * 3600000;
		editor.putLong("LastVisitedDate", dateTime);

		editor.commit();

	}

	public static int getCheckUpdate(Context context) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		long a = prefs.getLong("LastVisitedDate", 0);


		if (System.currentTimeMillis() > a)
			return 1;
		else
			return 0;
	}

	public static void setProfilePhoto(Context context, ViewEmpProfile viewEmpProfile) {

		String key = "_Profile_Photo";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("inPhoto", viewEmpProfile.getInPhoto());

		editor.commit();

	}

	public static ViewEmpProfile getProfilePhoto(Context context) {

		String key = "_Profile_Photo";
		ViewEmpProfile viewEmpProfile = new ViewEmpProfile();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		viewEmpProfile.setInPhoto(prefs.getString("inPhoto", ""));


		return viewEmpProfile;
	}

	public static void setAwcId(Activity activity, String awcid) {
		String key = "_Awcid";
		SharedPreferences prefs = activity.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("code2", awcid);
		editor.commit();
	}

	public static void clearpropic(Context context) {

		String key = "_Profile_Photo";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("inPhoto","");
		editor.commit();

	}


	public static void clearLog(Context context) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("Password", "");
		editor.putString("EmpNo", "");
		editor.putString("Post_name", "");
		editor.putString("PostCode", "");
		editor.putString("UserId", "");
		editor.putString("UserName", "");
		editor.putString("UserNameHN", "");
		editor.putString("MobileNumber", "");
		editor.putString("UserRole", "");
		editor.putString("SubdivisionName", "");
		editor.putString("SubDivCode", "");
		editor.putString("DistrictCode", "");
		editor.putString("DistName", "");

		editor.putString("IMEI","");
		editor.putString("isLocked", "");
		editor.commit();

	}
}
