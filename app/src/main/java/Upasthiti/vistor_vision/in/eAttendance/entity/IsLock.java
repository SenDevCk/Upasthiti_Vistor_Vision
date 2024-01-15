package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class IsLock implements Serializable {
    private String _empId="";
    private String isLock="";



    private static Class<IsLock> viewEmpProfileClass= IsLock.class;


    public IsLock(SoapObject sobj)
    {

        this.set_empId(sobj.getProperty("_empId").toString());
        this.setIsLock(sobj.getProperty("isLock").toString());


    }
    public IsLock() {
        super();
    }

    public String get_empId() {
        return _empId;
    }

    public void set_empId(String _empId) {
        this._empId = _empId;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public static Class<IsLock> getViewEmpProfileClass() {
        return viewEmpProfileClass;
    }

    public static void setViewEmpProfileClass(Class<IsLock> viewEmpProfileClass) {
        IsLock.viewEmpProfileClass = viewEmpProfileClass;
    }
}
