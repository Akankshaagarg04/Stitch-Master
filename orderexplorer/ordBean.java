package com.example.projectjune24.orderexplorer;

public class ordBean {
String worker;
String dress;
String status;
String odid;
String mob;
String bill;

    public ordBean(String worker, String dress, String status, String odid, String mob, String bill) {
        this.worker = worker;
        this.dress = dress;
        this.status = status;
        this.odid = odid;
        this.mob = mob;
        this.bill = bill;
    }

    public ordBean() {
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getOdid() {
        return odid;
    }

    public void setOdid(String odid) {
        this.odid = odid;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
