package Upasthiti.vistor_vision.in.eAttendance.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class ServicesListData implements Serializable {
    private String Today_Follow_Up="";
    private String TypeOfDisability="";
    private String MobileNo="";
    private String EntryBy="";
    private String EntryDate="";
    private String CaseDescription="";
    private String ServiceDescription="";
    private String ServiceFrom="";
    private String IsCaseClose="";
    private String ServiceRemarks="";
    private String DisabilityRemarks="";
    private String Remarks="";
    private String RefferedTo="";
    private String Block="";
    private String District="";
    private String Address="";
    private String NofVisit="";
    private String Qualification="";
    private String Age="";
    private String BenName="";
    private String BeneficiaryId="";
    private String ServiceId="";

    private static Class<ServicesListData> servicesListDataClass= ServicesListData.class;


    public ServicesListData(SoapObject sobj)
    {

        this.setToday_Follow_Up(sobj.getProperty("FT").toString());
        this.setTypeOfDisability(sobj.getProperty("TypeOfDisability").toString());
        this.setMobileNo(sobj.getProperty("MobileNo").toString());
        this.setEntryBy(sobj.getProperty("EntryBy").toString());
        this.setEntryDate(sobj.getProperty("EntryDate").toString());
        this.setCaseDescription(sobj.getProperty("CaseDescription").toString());
        this.setServiceDescription(sobj.getProperty("ServiceDescription").toString());
        this.setServiceFrom(sobj.getProperty("ServiceFrom").toString());
        this.setIsCaseClose(sobj.getProperty("IsCaseClose").toString());
        this.setServiceRemarks(sobj.getProperty("ServiceRemarks").toString());
        this.setDisabilityRemarks(sobj.getProperty("DisabilityRemarks").toString());
        this.setRemarks(sobj.getProperty("Remarks").toString());
        this.setRefferedTo(sobj.getProperty("RefferedTo").toString());
        this.setDistrict(sobj.getProperty("District").toString());
        this.setBlock(sobj.getProperty("Block").toString());
        this.setAddress(sobj.getProperty("Address").toString());
        this.setNofVisit(sobj.getProperty("NofVisit").toString());
        this.setQualification(sobj.getProperty("Qualification").toString());
        this.setAge(sobj.getProperty("Age").toString());
        this.setBenName(sobj.getProperty("BenName").toString());
        this.setBeneficiaryId(sobj.getProperty("BeneficiaryId").toString());
        this.setServiceId(sobj.getProperty("ServiceId").toString());

    }

    public static Class<ServicesListData> getServicesListDataClass() {
        return servicesListDataClass;
    }

    public static void setServicesListDataClass(Class<ServicesListData> servicesListDataClass1) {
        ServicesListData.servicesListDataClass = servicesListDataClass1;
    }

    public ServicesListData() {
        super();
    }


    public String getToday_Follow_Up() {
        return Today_Follow_Up;
    }

    public void setToday_Follow_Up(String today_Follow_Up) {
        Today_Follow_Up = today_Follow_Up;
    }

    public String getTypeOfDisability() {
        return TypeOfDisability;
    }

    public void setTypeOfDisability(String typeOfDisability) {
        TypeOfDisability = typeOfDisability;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEntryBy() {
        return EntryBy;
    }

    public void setEntryBy(String entryBy) {
        EntryBy = entryBy;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getCaseDescription() {
        return CaseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        CaseDescription = caseDescription;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
    }

    public String getServiceFrom() {
        return ServiceFrom;
    }

    public void setServiceFrom(String serviceFrom) {
        ServiceFrom = serviceFrom;
    }

    public String getIsCaseClose() {
        return IsCaseClose;
    }

    public void setIsCaseClose(String isCaseClose) {
        IsCaseClose = isCaseClose;
    }

    public String getServiceRemarks() {
        return ServiceRemarks;
    }

    public void setServiceRemarks(String serviceRemarks) {
        ServiceRemarks = serviceRemarks;
    }

    public String getDisabilityRemarks() {
        return DisabilityRemarks;
    }

    public void setDisabilityRemarks(String disabilityRemarks) {
        DisabilityRemarks = disabilityRemarks;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getRefferedTo() {
        return RefferedTo;
    }

    public void setRefferedTo(String refferedTo) {
        RefferedTo = refferedTo;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNofVisit() {
        return NofVisit;
    }

    public void setNofVisit(String nofVisit) {
        NofVisit = nofVisit;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBenName() {
        return BenName;
    }

    public void setBenName(String benName) {
        BenName = benName;
    }

    public String getBeneficiaryId() {
        return BeneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        BeneficiaryId = beneficiaryId;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }
}
