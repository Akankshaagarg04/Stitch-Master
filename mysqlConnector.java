package com.example.projectjune24;

import java.sql.Connection;
import java.sql.DriverManager;

public class mysqlConnector {
    public static Connection doConnect(){
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/projectjune24","root","adhm@TIET22");
        }
        catch (Exception exp){
            exp.printStackTrace();
        } return con;
    }
    public  static  void main(String args[]){
        if(doConnect()==null){
            System.out.println("Connection Lost");
        }
        else{
            System.out.println("Connection Established Successfully");
        }
    }
}
