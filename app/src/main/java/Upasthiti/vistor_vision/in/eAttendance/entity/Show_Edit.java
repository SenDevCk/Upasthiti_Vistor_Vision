package Upasthiti.vistor_vision.in.eAttendance.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Nicsi on 5/18/2018.
 */

public class Show_Edit implements KvmSerializable
{
    public static Class<Show_Edit> show_editClass = Show_Edit.class;

    int id;
    private String BenName;
    private String BenFName;
    private String Date;
    private String AadharNo;



    @Override
    public Object getProperty(int index) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int index, Object value) {

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {

    }

    public static Class<Show_Edit> getShow_editClass() {
        return show_editClass;
    }

    public static void setShow_editClass(Class<Show_Edit> show_editClass) {
        Show_Edit.show_editClass = show_editClass;
    }

    public String getBenName() {
        return BenName;
    }

    public void setBenName(String benName) {
        BenName = benName;
    }

    public String getBenFName() {
        return BenFName;
    }

    public void setBenFName(String benFName) {
        BenFName = benFName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAadharNo() {
        return AadharNo;
    }

    public void setAadharNo(String aadharNo) {
        AadharNo = aadharNo;
    }


}
