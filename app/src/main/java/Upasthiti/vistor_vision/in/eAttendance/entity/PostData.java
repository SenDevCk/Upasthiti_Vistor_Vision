package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

public class PostData {
    String PostType="";
    String PostCode="";
    String PostName="";

    public static Class<PostData> PostData_CLASS= PostData.class;


    public PostData(SoapObject sobj)
    {

        this.PostType=sobj.getProperty(0).toString();
        this.PostCode=sobj.getProperty(1).toString();
        this.PostName=sobj.getProperty(2).toString();
    }
    public PostData() {
        super();
    }

    public String getPostType() {
        return PostType;
    }

    public void setPostType(String postType) {
        PostType = postType;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }
}
