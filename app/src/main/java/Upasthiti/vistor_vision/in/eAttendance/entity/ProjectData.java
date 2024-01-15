package Upasthiti.vistor_vision.in.eAttendance.entity;

import org.ksoap2.serialization.SoapObject;

public class ProjectData {

	private String ProjectCode;
	private String ProjectName;

	public static Class<ProjectData> PROJECT_CLASS= ProjectData.class;


	public ProjectData(SoapObject sobj)
	{

		this.ProjectCode=sobj.getProperty(0).toString();
		this.ProjectName=sobj.getProperty(1).toString();
	}
	public ProjectData(){

	}

	public String getProjectCode() {
		return ProjectCode;
	}

	public void setProjectCode(String projectCode) {
		ProjectCode = projectCode;
	}

	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
}
