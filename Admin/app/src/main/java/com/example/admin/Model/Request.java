package com.example.admin.Model;

public class Request {

private String prn;
private String issuedbookid;


    public Request() {
    }

    public Request(String prn, String issuedbookid) {
        this.prn = prn;
        this.issuedbookid = issuedbookid;

    }

    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public String getIssuedbookid() {
        return issuedbookid;
    }

    public void setIssuedbookid(String issuedbookid) {
        this.issuedbookid = issuedbookid;
    }




}
