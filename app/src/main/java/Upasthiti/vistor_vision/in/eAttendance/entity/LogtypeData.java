package Upasthiti.vistor_vision.in.eAttendance.entity;

import org.ksoap2.serialization.SoapObject;

public class LogtypeData {

	private String LogCode;
	private String LogTyp;

	public static Class<LogtypeData> LOGTYPE_CLASS= LogtypeData.class;


	public LogtypeData(SoapObject sobj)
	{

		this.LogCode=sobj.getProperty(0).toString();
		this.LogTyp=sobj.getProperty(1).toString();
	}
	public LogtypeData(){

	}

	public String getLogCode() {
		return LogCode;
	}

	public void setLogCode(String logCode) {
		LogCode = logCode;
	}

	public String getLogTyp() {
		return LogTyp;
	}

	public void setLogTyp(String logTyp) {
		LogTyp = logTyp;
	}
}
