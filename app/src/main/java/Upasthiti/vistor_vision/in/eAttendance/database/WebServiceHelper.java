package Upasthiti.vistor_vision.in.eAttendance.database;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Activity.Work_Details;
import Upasthiti.vistor_vision.in.eAttendance.entity.AppliedLeaveData;
import Upasthiti.vistor_vision.in.eAttendance.entity.AttendanceSummeryReportData;
import Upasthiti.vistor_vision.in.eAttendance.entity.BlockData;
import Upasthiti.vistor_vision.in.eAttendance.entity.EmployeeListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.HolidaysData;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveHistoryData;
import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveStatusData;
import Upasthiti.vistor_vision.in.eAttendance.entity.LogtypeData;
import Upasthiti.vistor_vision.in.eAttendance.entity.MarkedAttendanceData;
import Upasthiti.vistor_vision.in.eAttendance.entity.MonthlyReportData;
import Upasthiti.vistor_vision.in.eAttendance.entity.PendingLeaveListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.PostData;
import Upasthiti.vistor_vision.in.eAttendance.entity.ProjectData;
import Upasthiti.vistor_vision.in.eAttendance.entity.ServicesListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.TimeData;
import Upasthiti.vistor_vision.in.eAttendance.entity.UserDetails;
import Upasthiti.vistor_vision.in.eAttendance.entity.ValidateAttendance;
import Upasthiti.vistor_vision.in.eAttendance.entity.Versioninfo;
import Upasthiti.vistor_vision.in.eAttendance.entity.ViewEmpProfile;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkTypeData;


public class WebServiceHelper {

    public static final String SERVICENAMESPACE = "http://www.bedmc.in/";
    public static final String SERVICEURL = "http://www.bedmc.in/VistarWebService.asmx";

    public static final String APPVERSION_METHOD = "getAppLatest";
    public static final String AUTHENTICATE_METHOD = "Authenticate";
    public static final String REGISTER_METHOD = "insertEmpdetails";
    public static final String UPDATE_LOC = "UpdateLocation";
    public static final String LOG_OUT_METHOD = "LogOutUser";
    public static final String APPROVE_REJECT_WORK_METHOD = "ApproveRejectWork";
    public static final String LOG_EFFORT_METHOD = "insertTimeSheetDetail";
    public static final String APPROVE_PENDING_LEAVE_METHOD = "ApprovePendingLeave";
    public static final String APPLY_LEAVE = "LeaveApply";
    public static final String UPDATE_EMP_PROFILE_METHOD = "UpdateEpmProfile";
    public static final String UPDATE_ISLOCK_METHOD = "MarkActiveInactive";
    public static final String CHECKIN_METHOD = "CheckInAttendance";
    public static final String FOLLOW_UP_METHOD = "Followup";
    public static final String VALIDATE_CHECKIN_METHOD = "ValidateCheckIn";
    public static final String CHECKOUT_METHOD = "CheckOUTAttendance";
    public static final String CHANGE_PASS = "ChangePassword";
    public static final String UPLOAD_PHOTO_PATH = "UpdateProfilePhoto";
    public static final String GET_SUBDIV_LIST = "getSubdivision";
    public static final String GET_Post_LIST = "getPost";
    public static final String GET_LOG_TYPE_LIST = "getLogType";
    public static final String GET_TIMEE_LIST = "getLogTime";
    public static final String GET_PROJECT_LIST = "getProjectType";
    public static final String GET_WorkTYPE_LIST = "getWorkType";
    public static final String GET_ATTENDANCE_LIST = "DailyAttendanceDetails";
    public static final String GET_EMPLOYEE_LIST = "GetEmpList";
    public static final String GET_WORK_DETAILS_LIST = "GetWorkDetails";
    public static final String GET_HOLIDAYE_LIST = "GetHolidayList";
    public static final String ATT_SUMMERY_REPORT_METHOD = "GetAttendanceMontholy";
    public static final String GET_MONTH_WISE_STATUS = "GetMonthWiseSataus";
    public static final String GET_LEAVE_HISTORY = "GetLeaveHistory";
    public static final String GET_APPLIED_LEAVES = "GetLeavDashboard";
    public static final String FORGET_PASS_METHOD = "ForgetPwd";
    public static final String LEAVE_STATUS_METHOD = "GetLeaveStatus";
    public static final String GET_PROFILE_VIEW = "GetProfileView";
    public static final String GET_LEAV_FORAPPROVEAL = "GetLeavForApproval";


    public static Versioninfo CheckVersion(String version) {
        Versioninfo versioninfo;
        SoapObject res1;
        try {

            //res1=getServerData(APPVERSION_METHOD,Versioninfo.Versioninfo_CLASS,"IMEI","Ver",imei,version);
            SoapObject request = new SoapObject(SERVICENAMESPACE, APPVERSION_METHOD);
            // request.addProperty("_DeptId", "19");
            // request.addProperty("IMEI", imei);
            request.addProperty("Ver", version);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, Versioninfo.Versioninfo_CLASS.getSimpleName(), Versioninfo.Versioninfo_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + APPVERSION_METHOD, envelope);
            res1 = (SoapObject) envelope.getResponse();
            SoapObject final_object = (SoapObject) res1.getProperty(0);

            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {

            return null;
        }
        return versioninfo;

    }


    public static SoapObject getServerData(String methodName, Class bindClass, String param, String value) {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, methodName);
            request.addProperty(param, value);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, bindClass.getSimpleName(), bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }


    public static UserDetails Login(String User_ID, String Pwd) {
        try {
            SoapObject res1;
            //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            SoapObject request = new SoapObject(SERVICENAMESPACE, AUTHENTICATE_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
            request.addProperty("UserID", User_ID);
            request.addProperty("Password", Pwd);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, UserDetails.USER_CLASS.getSimpleName(), UserDetails.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + AUTHENTICATE_METHOD, envelope);
            res1 = (SoapObject) envelope.getResponse();
            if (res1 != null) {
                return new UserDetails(res1);
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String Registerempolys(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, REGISTER_METHOD);
            request.addProperty("_empId", strings[0].trim());
            request.addProperty("_Name", strings[1].trim());
            request.addProperty("_Mobile", strings[2].trim());
            request.addProperty("_Post_code", strings[3].trim());
            request.addProperty("_SDMCode", strings[4].trim());
            request.addProperty("_Posted_District", strings[5].trim());
            request.addProperty("_Gender", strings[6].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + REGISTER_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String UpdateLatLong(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATE_LOC);
            request.addProperty("Lat", strings[0].trim());
            request.addProperty("Long", strings[1].trim());
            request.addProperty("_Address", strings[2].trim());
            request.addProperty("dstcode", strings[3].trim());
            request.addProperty("blkcode", strings[4].trim());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + UPDATE_LOC, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String logoutempolys(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, LOG_OUT_METHOD);
            request.addProperty("Userid", strings[0].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + LOG_OUT_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String ApproveRejWork(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, APPROVE_REJECT_WORK_METHOD);
            request.addProperty("Workid", strings[0].trim());
            request.addProperty("_EmpId", strings[1].trim());
            request.addProperty("ApproveRejectStatus", strings[2].trim());
            request.addProperty("Remarks", strings[3].trim());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + APPROVE_REJECT_WORK_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String InsertTimeSheetDetail(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, LOG_EFFORT_METHOD);
            request.addProperty("_LogDate", strings[0].trim());
            request.addProperty("_EmpId", strings[1].trim());
            request.addProperty("_LogType", strings[2].trim());
            request.addProperty("_LogTime", strings[3].trim());
            request.addProperty("_Project", strings[4].trim());
            request.addProperty("_WorkType", strings[5].trim());
            request.addProperty("_LogDescription", strings[6].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + LOG_EFFORT_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String ApproveLeave(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, APPROVE_PENDING_LEAVE_METHOD);
            request.addProperty("EmpNo", strings[0].trim());
            request.addProperty("Leaveid", strings[1].trim());
            request.addProperty("ApproveSts", strings[2].trim());
            request.addProperty("AppRemarks", strings[3].trim());
            request.addProperty("ApproveBy", strings[4].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + APPROVE_PENDING_LEAVE_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String ApplyLeave(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, APPLY_LEAVE);
            request.addProperty("_empId", strings[0].trim());
            request.addProperty("_Post_code", strings[1].trim());
            request.addProperty("LeaveType", strings[2].trim());
            request.addProperty("DateFrom", strings[3].trim());
            request.addProperty("DateTo", strings[4].trim());
            request.addProperty("NoOfDay", strings[5].trim());
            request.addProperty("Remarks", strings[6].trim());
            request.addProperty("AppDoc", strings[7].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + APPLY_LEAVE, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String UpdateProfile(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATE_EMP_PROFILE_METHOD);
            request.addProperty("_empId", strings[0].trim());
            request.addProperty("HomeDist", strings[1].trim());
            request.addProperty("PerAddress", strings[2].trim());
            request.addProperty("DOB", strings[3].trim());
            request.addProperty("DOJ", strings[4].trim());
            request.addProperty("AccountNo", strings[5].trim());
            request.addProperty("IfscCode", strings[6].trim());
            request.addProperty("PANNo", strings[7].trim());
            request.addProperty("AadhaarNo", strings[8].trim());
            request.addProperty("MobileNo", strings[9].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + UPDATE_EMP_PROFILE_METHOD, envelope);
                response = envelope.getResponse().toString();
            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String UpdateIsLock(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATE_ISLOCK_METHOD);
            request.addProperty("_empId", strings[0].trim());
            request.addProperty("isLock", strings[1].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + UPDATE_ISLOCK_METHOD, envelope);
                response = envelope.getResponse().toString();
            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }
    public static String MarkAttendance(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, CHECKIN_METHOD);
            request.addProperty("_empId", strings[0].trim());
            request.addProperty("_postId", strings[1].trim());
            request.addProperty("_inRemark", strings[2].trim());
            request.addProperty("Lat", strings[3].trim());
            request.addProperty("Long", strings[4].trim());
            request.addProperty("_Address", strings[5].trim());
            request.addProperty("image", strings[6].trim());
            request.addProperty("DeviceId", strings[7].trim());
            request.addProperty("IsInOffice", strings[8].trim());
            request.addProperty("dstcode", strings[9].trim());
            request.addProperty("blkcode", strings[10].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
  /*          SSLConnection sslConnection = new SSLConnection();
            sslConnection.allowAllSSL();*/
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + CHECKIN_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
            return null;
        }
        return response;

    }

    public static String OutAttendance(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, CHECKOUT_METHOD);
            request.addProperty("_empId", strings[0].trim());
            request.addProperty("outRemark", strings[1].trim());
            request.addProperty("outlatitude", strings[2].trim());
            request.addProperty("outlongitude", strings[3].trim());
            request.addProperty("outaddress", strings[4].trim());
            request.addProperty("outPhoto", strings[5].trim());
            request.addProperty("DeviceIdOut", strings[6].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
           /* SSLConnection sslConnection = new SSLConnection();
            sslConnection.allowAllSSL();*/
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + CHECKOUT_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }


    public static ArrayList<ValidateAttendance> ValidateAttendanceDataLoader(String empno) {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, VALIDATE_CHECKIN_METHOD);
            request.addProperty("_EMPNo", empno);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, ValidateAttendance.attendanceDataClass().getSimpleName(), ValidateAttendance.attendanceDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            try {
                androidHttpTransport.call(SERVICENAMESPACE + VALIDATE_CHECKIN_METHOD, envelope);
                res1 = (SoapObject) envelope.getResponse();
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<ValidateAttendance> validateAttendanceArrayList = new ArrayList<ValidateAttendance>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ValidateAttendance validateAttendanceData = new ValidateAttendance(final_object);
                    validateAttendanceArrayList.add(validateAttendanceData);
                }
            } else
                return validateAttendanceArrayList;
        }

        return validateAttendanceArrayList;
    }

    public static ArrayList<ViewEmpProfile> EmpProfileLoder(String empno) {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_PROFILE_VIEW);
            request.addProperty("_empId", empno);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, ViewEmpProfile.viewEmpProfileClass().getSimpleName(), ViewEmpProfile.viewEmpProfileClass());
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            try {
                androidHttpTransport.call(SERVICENAMESPACE + GET_PROFILE_VIEW, envelope);
                res1 = (SoapObject) envelope.getResponse();
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<ViewEmpProfile> empProfileDataArrayList = new ArrayList<ViewEmpProfile>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ViewEmpProfile view_empProfileData = new ViewEmpProfile(final_object);
                    empProfileDataArrayList.add(view_empProfileData);
                }
            } else
                return empProfileDataArrayList;
        }


        return empProfileDataArrayList;
    }

    public static String UploadPhotoPath(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, UPLOAD_PHOTO_PATH);
            request.addProperty("_empId", strings[0].trim());
            request.addProperty("Photo", strings[1].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + UPLOAD_PHOTO_PATH, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String follow_up(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, FOLLOW_UP_METHOD);
            request.addProperty("_CenterId", strings[0].trim());
            request.addProperty("_BeneficiaryId", strings[1].trim());
            request.addProperty("_ServiceId", strings[2].trim());
            request.addProperty("_EntryBy", strings[3].trim());
            request.addProperty("_FollowedBy", strings[4].trim());
            request.addProperty("_EntryFrom", strings[5].trim());
            request.addProperty("_Remarks", strings[6].trim());
            request.addProperty("Lat", strings[7].trim());
            request.addProperty("Long", strings[8].trim());
            request.addProperty("_Address", strings[9].trim());
            request.addProperty("image", strings[10].trim());
            request.addProperty("_isFollowupClose", strings[11].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
  /*          SSLConnection sslConnection = new SSLConnection();
            sslConnection.allowAllSSL();*/
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + FOLLOW_UP_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
            return null;
        }
        return response;

    }



    public static String ChangePassword(String[] strings) {
        String response = null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, CHANGE_PASS);
            request.addProperty("UserId", strings[0].trim());
            request.addProperty("OldPassword", strings[1].trim());
            request.addProperty("NewPassword", Integer.parseInt(strings[2].trim()));
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + CHANGE_PASS, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<BlockData> getSubDivision(String DistId) {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_SUBDIV_LIST);
            request.addProperty("DistCode", DistId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, BlockData.BlockData_CLASS.getSimpleName(), BlockData.BlockData_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            try {
                androidHttpTransport.call(SERVICENAMESPACE + GET_SUBDIV_LIST, envelope);
                res1 = (SoapObject) envelope.getResponse();
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();

        ArrayList<BlockData> blockDataArrayList = new ArrayList<BlockData>();
        //healthFacilityList = null;

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    BlockData blkList = new BlockData(final_object);
                    blockDataArrayList.add(blkList);
                }
            } else
                return blockDataArrayList;
        }


        return blockDataArrayList;


    }

    public static ArrayList<LogtypeData> getLogtype() {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_LOG_TYPE_LIST);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, LogtypeData.LOGTYPE_CLASS.getSimpleName(), LogtypeData.LOGTYPE_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_LOG_TYPE_LIST, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();

        ArrayList<LogtypeData> logtypeData = new ArrayList<LogtypeData>();
        //healthFacilityList = null;

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    LogtypeData logtypeData1 = new LogtypeData(final_object);
                    logtypeData.add(logtypeData1);
                }
            } else
                return logtypeData;
        }
        return logtypeData;
    }
    public static ArrayList<ProjectData> getProject() {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_PROJECT_LIST);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, ProjectData.PROJECT_CLASS.getSimpleName(), ProjectData.PROJECT_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_PROJECT_LIST, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<ProjectData> projectDataArrayList = new ArrayList<ProjectData>();
        //healthFacilityList = null;

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ProjectData projectData = new ProjectData(final_object);
                    projectDataArrayList.add(projectData);
                }
            } else
                return projectDataArrayList;
        }
        return projectDataArrayList;
    }

    public static ArrayList<WorkTypeData> getWorkdata() {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_WorkTYPE_LIST);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, WorkTypeData.WORKTYPE_CLASS.getSimpleName(), WorkTypeData.WORKTYPE_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_WorkTYPE_LIST, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();
        ArrayList<WorkTypeData> workTypeDataArrayList = new ArrayList<WorkTypeData>();
        //healthFacilityList = null;

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    WorkTypeData workTypeData = new WorkTypeData(final_object);
                    workTypeDataArrayList.add(workTypeData);
                }
            } else
                return workTypeDataArrayList;
        }
        return workTypeDataArrayList;
    }

    public static ArrayList<TimeData> getTime() {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_TIMEE_LIST);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, TimeData.TIME_CLASS.getSimpleName(), TimeData.TIME_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_TIMEE_LIST, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();

        ArrayList<TimeData> timeData = new ArrayList<TimeData>();
        //healthFacilityList = null;

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    TimeData timeData1 = new TimeData(final_object);
                    timeData.add(timeData1);
                }
            } else
                return timeData;
        }


        return timeData;


    }

    public static ArrayList<PostData> getPost() {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_Post_LIST);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, PostData.PostData_CLASS.getSimpleName(), PostData.PostData_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_Post_LIST, envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();

        ArrayList<PostData> postDataArrayList = new ArrayList<PostData>();
        //healthFacilityList = null;

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PostData postList = new PostData(final_object);
                    postDataArrayList.add(postList);
                }
            } else
                return postDataArrayList;
        }


        return postDataArrayList;


    }

    public static ArrayList<MarkedAttendanceData> markedAttendanceLoader(String distcode,String sdmcode, String UserId) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_ATTENDANCE_LIST);

        // request.addProperty("comCode", "");
        request.addProperty("Dstcode", distcode);
        request.addProperty("sdmcode", sdmcode);
        request.addProperty("UserId", UserId);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, MarkedAttendanceData.AttendanceDataClass().getSimpleName(), MarkedAttendanceData.AttendanceDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_ATTENDANCE_LIST, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<MarkedAttendanceData> markedAttendanceDataArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    MarkedAttendanceData markedAttendanceData = new MarkedAttendanceData(final_object);
                    markedAttendanceDataArrayList.add(markedAttendanceData);
                }
            } else
                return markedAttendanceDataArrayList;
        }


        return markedAttendanceDataArrayList;
    }

    public static ArrayList<EmployeeListData> empListLoader(String postcode,String dstcode) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_EMPLOYEE_LIST);

        request.addProperty("Postcode", postcode);
        request.addProperty("dstcode", dstcode);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, EmployeeListData.getEmployeeListDataClass().getSimpleName(), EmployeeListData.getEmployeeListDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_EMPLOYEE_LIST, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<EmployeeListData> employeeListDataArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    EmployeeListData employeeListData = new EmployeeListData(final_object);
                    employeeListDataArrayList.add(employeeListData);
                }
            } else
                return employeeListDataArrayList;
        }


        return employeeListDataArrayList;
    }

    public static ArrayList<WorkListData> workListLoader(String empno,String dstcode,String month) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_WORK_DETAILS_LIST);

        request.addProperty("EmpNo", empno);
        request.addProperty("DstCode", dstcode);
        request.addProperty("MonthId", month);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, WorkListData.getWorkListDataClass().getSimpleName(), WorkListData.getWorkListDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_WORK_DETAILS_LIST, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<WorkListData> workListDataArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    WorkListData workListData = new WorkListData(final_object);
                    workListDataArrayList.add(workListData);
                }
            } else
                return workListDataArrayList;
        }


        return workListDataArrayList;
    }

    public static ArrayList<PendingLeaveListData> pendingleaveListLoader(String tagLoginId, String leaveId, String sts) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_LEAV_FORAPPROVEAL);

        request.addProperty("TagLoginId", tagLoginId);
        request.addProperty("LeaveId", leaveId);
        request.addProperty("sts", sts);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, PendingLeaveListData.getPendingLeaveListDataClass().getSimpleName(), PendingLeaveListData.getPendingLeaveListDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            try {
                androidHttpTransport.call(SERVICENAMESPACE + GET_LEAV_FORAPPROVEAL, envelope);
                res1 = (SoapObject) envelope.getResponse();
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<PendingLeaveListData> pendingLeaveListData = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PendingLeaveListData pendingLeaveListData1 = new PendingLeaveListData(final_object);
                    pendingLeaveListData.add(pendingLeaveListData1);
                }
            } else
                return pendingLeaveListData;
        }


        return pendingLeaveListData;
    }

    public static ArrayList<HolidaysData> HolidayLoader(int month) {

        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_HOLIDAYE_LIST);

            request.addProperty("Month", month);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, HolidaysData.getHolidaysDataClass().getSimpleName(), HolidaysData.getHolidaysDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_HOLIDAYE_LIST, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<HolidaysData> markedAttendanceDataArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    HolidaysData markedAttendanceData = new HolidaysData(final_object);
                    markedAttendanceDataArrayList.add(markedAttendanceData);
                }
            } else
                return markedAttendanceDataArrayList;
        }


        return markedAttendanceDataArrayList;
    }




    public static ArrayList<AttendanceSummeryReportData> getAttSummeryReportData(String[] strings) {

        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, ATT_SUMMERY_REPORT_METHOD);
            request.addProperty("Year", strings[0].trim());
            request.addProperty("Month", strings[1].trim());
            request.addProperty("EmpNo",  strings[2].trim());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, AttendanceSummeryReportData.getAttendance_Summery_Class().getSimpleName(),  AttendanceSummeryReportData.getAttendance_Summery_Class());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + ATT_SUMMERY_REPORT_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<AttendanceSummeryReportData> userByLocData = new ArrayList<AttendanceSummeryReportData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    AttendanceSummeryReportData usergrivancedata = new AttendanceSummeryReportData(final_object);
                    userByLocData.add(usergrivancedata);
                }
            } else
                return userByLocData;
        }


        return userByLocData;
    }


    public static ArrayList<MonthlyReportData> getMonthlyReportData(String year, String empid) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_MONTH_WISE_STATUS);

        // request.addProperty("comCode", "");
        request.addProperty("Year", year);
        request.addProperty("EMPNo", empid);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, MonthlyReportData.monthlyReportDataClass().getSimpleName(), MonthlyReportData.monthlyReportDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_MONTH_WISE_STATUS, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<MonthlyReportData> monthlyReportDataArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    MonthlyReportData monthlyReportData1 = new MonthlyReportData(final_object);
                    monthlyReportDataArrayList.add(monthlyReportData1);
                }
            } else
                return monthlyReportDataArrayList;
        }


        return monthlyReportDataArrayList;
    }

    public static ArrayList<LeaveHistoryData> getLeaveHistory(String empno) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_LEAVE_HISTORY);

       request.addProperty("EMPNo", empno);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, LeaveHistoryData.getLeaveHistoryDataClass().getSimpleName(), LeaveHistoryData.getLeaveHistoryDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_LEAVE_HISTORY, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<LeaveHistoryData> leaveHistoryDataArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    LeaveHistoryData leaveHistoryData = new LeaveHistoryData(final_object);
                    leaveHistoryDataArrayList.add(leaveHistoryData);
                }
            } else
                return leaveHistoryDataArrayList;
        }


        return leaveHistoryDataArrayList;
    }

    public static ArrayList<AppliedLeaveData> getappliedLeaves(String distcode) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_APPLIED_LEAVES);

        request.addProperty("Dstcode", distcode);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, AppliedLeaveData.getAppliedLeaveDataClass().getSimpleName(), AppliedLeaveData.getAppliedLeaveDataClass());
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            try{
            androidHttpTransport.call(SERVICENAMESPACE + GET_APPLIED_LEAVES, envelope);
            res1 = (SoapObject) envelope.getResponse();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<AppliedLeaveData> leaveHistoryDataArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    AppliedLeaveData leaveHistoryData = new AppliedLeaveData(final_object);
                    leaveHistoryDataArrayList.add(leaveHistoryData);
                }
            } else
                return leaveHistoryDataArrayList;
        }


        return leaveHistoryDataArrayList;
    }

    public static String forgetPassword(String MB) {
        String response=null;
        try {

            //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            SoapObject request = new SoapObject(SERVICENAMESPACE, FORGET_PASS_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
            request.addProperty("MobileNo", MB.trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + FORGET_PASS_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<LeaveStatusData> getLeaveStatusData( String empno) {
        SoapObject request = new SoapObject(SERVICENAMESPACE, LEAVE_STATUS_METHOD);

        SoapObject res1;
        try {
            request.addProperty("EMPNo", empno);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, LeaveStatusData.getLeaveStatusDataClass().getSimpleName(),  LeaveStatusData.getLeaveStatusDataClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + LEAVE_STATUS_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<LeaveStatusData> leaveStatusData1 = new ArrayList<LeaveStatusData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    LeaveStatusData leaveStatusData = new LeaveStatusData(final_object);
                    leaveStatusData1.add(leaveStatusData);
                }
            } else
                return leaveStatusData1;
        }


        return leaveStatusData1;
    }

}
