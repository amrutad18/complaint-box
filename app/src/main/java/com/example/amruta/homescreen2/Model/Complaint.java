package com.example.amruta.homescreen2.Model;

/**
 * Created by amruta on 27/10/17.
 */

public class Complaint {
    private String user;
    private String productType;
    private String modelNo;
    private String fileDate;
    private String closeDate;
    private int priority;
    private int status_code;
    private String details;

    public String getUser() {
        return user;
    }
    public void setUser(String u) {
        this.user = u;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String ptype) {
        this.productType = ptype;
    }
    public String getFileDate() {
        return fileDate;
    }
    public void setFileDate(String fdate) {
        this.fileDate = fdate;
    }
    public String getCloseDateDate() {
        return closeDate;
    }
    public void setCloseDate(String cdate) {
        this.closeDate = cdate;
    }
    public int getPriority(){
        return  priority;
    }
    public void setPriority(int p){
        this.priority = p;
    }
    public int getStatus_code(){
        return  status_code;
    }
    public void setStatus_code(int s){
        this.status_code = s;
    }
    public String getModelNo(){
        return  modelNo;
    }
    public void setModelNo(String mnum){
        this.modelNo = mnum;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}