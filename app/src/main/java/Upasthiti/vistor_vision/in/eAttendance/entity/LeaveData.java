package Upasthiti.vistor_vision.in.eAttendance.entity;

import org.ksoap2.serialization.SoapObject;

public class LeaveData {

	private String Leavecode;
	private String LeaveName;

	public static Class<LeaveData> LEAVE_CLASS= LeaveData.class;


	public LeaveData(SoapObject sobj)
	{

		this.Leavecode=sobj.getProperty(0).toString();
		this.LeaveName=sobj.getProperty(1).toString();

	}
	public LeaveData(){

	}

	public String getLeavecode() {
		return Leavecode;
	}

	public void setLeavecode(String leavecode) {
		Leavecode = leavecode;
	}

	public String getLeaveName() {
		return LeaveName;
	}

	public void setLeaveName(String leaveName) {
		LeaveName = leaveName;
	}
}
