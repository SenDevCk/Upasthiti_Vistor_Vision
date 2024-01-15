package Upasthiti.vistor_vision.in.eAttendance.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class UserDetails implements KvmSerializable {
	public static Class<UserDetails> USER_CLASS = UserDetails.class;
	private String UserPassword="";
	private String EmpNo="";
	private String Post_name="";
	private String PostCode="";
	private String Lat="";
	private String Long="";
	private boolean _isAuthenticated = false;
	private String UserId = "";
	private String UserName = "";
	private String UserNameHN = "";
	private String MobileNumber = "";
	private String UserRole="";
	private String DistrictCode="";
	private String DistName="";
	private String SubDivCode="";
	private String SubdivisionName="";
	private String IMEI="";
	private String isLocked="";
	private String isLogin="";


	public UserDetails(SoapObject obj) {


		
		this.set_isAuthenticated(Boolean.parseBoolean(obj.getProperty(
				"isAuthenticated").toString()));
		if(_isAuthenticated) {
		//	this.setUserPassword(obj.getProperty("Password").toString());
			this.setEmpNo(obj.getProperty("EmpNo").toString());
			this.setPost_name(obj.getProperty("Post_name").toString());
			this.setPostCode(obj.getProperty("PostCode").toString());
			this.setLat(obj.getProperty("Lat").toString());
			this.setLong(obj.getProperty("Long").toString());
			this.setUserId(obj.getProperty("UserId").toString());
			this.setUserName(obj.getProperty("UserName").toString());
			this.setUserNameHN(obj.getProperty("UserNameHN").toString());
			this.setMobileNumber(obj.getProperty("MobileNumber").toString());
			this.setUserRole(obj.getProperty("UserRole").toString());
			this.setDistrictCode(obj.getProperty("DistrictCode").toString());
			this.setDistName(obj.getProperty("DistName").toString());
			this.setSubDivCode(obj.getProperty("SubDivCode").toString());
			this.setSubdivisionName(obj.getProperty("SubdivisionName").toString());
			this.setIMEI(obj.getProperty("IMEI").toString());
			this.setIsLocked(obj.getProperty("isLocked").toString());
			this.setIsLogin(obj.getProperty("isLogin").toString());

		}

	}



	public UserDetails() {
	}

	public static Class<UserDetails> getUserClass() {
		return USER_CLASS;
	}

	public static void setUserClass(Class<UserDetails> userClass) {
		USER_CLASS = userClass;
	}


	@Override
	public Object getProperty(int i) {
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 0;
	}

	@Override
	public void setProperty(int i, Object o) {

	}

	@Override
	public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

	}

	public String getEmpNo() {
		return EmpNo;
	}

	public void setEmpNo(String empNo) {
		EmpNo = empNo;
	}

	public String getPost_name() {
		return Post_name;
	}

	public void setPost_name(String post_name) {
		Post_name = post_name;
	}

	public String getPostCode() {
		return PostCode;
	}

	public void setPostCode(String postCode) {
		PostCode = postCode;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public boolean is_isAuthenticated() {
		return _isAuthenticated;
	}

	public void set_isAuthenticated(boolean _isAuthenticated) {
		this._isAuthenticated = _isAuthenticated;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserNameHN() {
		return UserNameHN;
	}

	public void setUserNameHN(String userNameHN) {
		UserNameHN = userNameHN;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public String getDistrictCode() {
		return DistrictCode;
	}

	public void setDistrictCode(String districtCode) {
		DistrictCode = districtCode;
	}

	public String getDistName() {
		return DistName;
	}

	public void setDistName(String distName) {
		DistName = distName;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String IMEI) {
		this.IMEI = IMEI;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getLat() {
		return Lat;
	}

	public void setLat(String lat) {
		Lat = lat;
	}

	public String getLong() {
		return Long;
	}

	public void setLong(String aLong) {
		Long = aLong;
	}

	public String getSubDivCode() {
		return SubDivCode;
	}

	public void setSubDivCode(String subDivCode) {
		SubDivCode = subDivCode;
	}

	public String getSubdivisionName() {
		return SubdivisionName;
	}

	public void setSubdivisionName(String subdivisionName) {
		SubdivisionName = subdivisionName;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
}
