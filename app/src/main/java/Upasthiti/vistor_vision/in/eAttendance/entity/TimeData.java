package Upasthiti.vistor_vision.in.eAttendance.entity;

import org.ksoap2.serialization.SoapObject;

public class TimeData {

	private String Code;
	private String WorkTime;

	public static Class<TimeData> TIME_CLASS= TimeData.class;


	public TimeData(SoapObject sobj)
	{

		this.Code=sobj.getProperty(0).toString();
		this.WorkTime=sobj.getProperty(1).toString();
	}
	public TimeData(){

	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getWorkTime() {
		return WorkTime;
	}

	public void setWorkTime(String workTime) {
		WorkTime = workTime;
	}
}
