package Upasthiti.vistor_vision.in.eAttendance.entity;

import org.ksoap2.serialization.SoapObject;

public class WorkTypeData {

	private String Code;
	private String WorkType;

	public static Class<WorkTypeData> WORKTYPE_CLASS= WorkTypeData.class;


	public WorkTypeData(SoapObject sobj)
	{

		this.Code=sobj.getProperty(0).toString();
		this.WorkType=sobj.getProperty(1).toString();
	}
	public WorkTypeData(){

	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getWorkType() {
		return WorkType;
	}

	public void setWorkType(String workType) {
		WorkType = workType;
	}
}
