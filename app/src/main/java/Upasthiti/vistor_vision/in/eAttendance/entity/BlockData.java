package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

public class BlockData {
    String BlockCode="";
    String BlockName="";



    String DistCode="";
    public static Class<BlockData> BlockData_CLASS=BlockData.class;


    public BlockData(SoapObject sobj)
    {

        this.BlockCode=sobj.getProperty(0).toString();
        this.BlockName=sobj.getProperty(1).toString();
    }
    public BlockData() {
        super();
    }

    public String getBlockcode() {
        return BlockCode;
    }

    public void setBlockcode(String _blockcode) {
        BlockCode = _blockcode;
    }

    public String getBlockname() {
        return BlockName;
    }

    public void setBlockname(String _blockname) {
        BlockName = _blockname;
    }
    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String _distCode) {
        DistCode = _distCode;
    }

}
