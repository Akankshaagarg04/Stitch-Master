package com.example.projectjune24.customerenroll;
import com.example.projectjune24.flagss;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.projectjune24.mysqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;


public class customerenrollController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField cadd;

    @FXML
    private TextField ccity;

    @FXML
    private ComboBox<String> cgen;

    @FXML
    private TextField cmob;

    @FXML
    private TextField cname;

    @FXML
    private DatePicker dob;
    @FXML
    private TextField cmail;


    PreparedStatement st;
    @FXML
    void doDelete(ActionEvent event) {

        try {
            st=con.prepareStatement("delete from csmenl where cmobile=?");
            st.setString(1,cmob.getText());
            int count=st.executeUpdate();
            if(count==1)
                System.out.println("Record Deleted ");
            else
                System.out.println("Invalid email");

            clearall();
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doSave(ActionEvent event) {
        try{
            st=con.prepareStatement("insert into csmenl values(?,?,?,?,?,?,?,?)");
            st.setString(1,cname.getText());
            st.setString(2,cmob.getText());
            st.setString(3,cmail.getText());
            mailController.SendMail(cmail.getText(),null,"Registered","Welcome aboard!!!");
            st.setString(4,cadd.getText());
            st.setString(5,ccity.getText());
            st.setString(6,cgen.getSelectionModel().getSelectedItem());
            LocalDate local=dob.getValue();
            Date dt=java.sql.Date.valueOf(local);
            st.setDate(7,dt);
            st.setDate(8,java.sql.Date.valueOf(LocalDate.now()));


            st.execute();
            System.out.println("Record Saved");
            clearall();
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }

    @FXML
    void doSearch(ActionEvent event) {
        try{
            st=con.prepareStatement("select * from csmenl where cmobile=?");
            st.setString(1,cmob.getText());
            ResultSet records=st.executeQuery();
            while(records.next()){
                String em=records.getString("cmail");
                String n=records.getString("cname");
                String add=records.getString("address");
                String c=records.getString("city");
                String gen=records.getString("gender");
                Date dt=records.getDate("dob");
                cgen.setValue(gen);
                cname.setText(n);
                cadd.setText(add);
                cmail.setText(em);
                ccity.setText(c);
                dob.setValue(dt.toLocalDate());

            }
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }
    @FXML
    void goback(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/adminn/admin-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void doUpdate(ActionEvent event) {
        try{
            st=con.prepareStatement("update csmenl set cname=?,cmail=?,address=?,city=?,gender=?,dob=? where cmobile=?");
            st.setString(1,cname.getText());
            st.setString(7,cmob.getText());
            st.setString(2,cmail.getText());
            st.setString(3,cadd.getText());
            st.setString(4,ccity.getText());
            st.setString(5,cgen.getSelectionModel().getSelectedItem());
            LocalDate local=dob.getValue();
            Date dt=java.sql.Date.valueOf(local);
            st.setDate(6,dt);



            st.execute();
            System.out.println("Record Updated");
            clearall();
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }
Connection con;

    @FXML
    void initialize() throws ParseException {
        jasos=true;
        con= mysqlConnector.doConnect();
        if(con==null)
            System.out.println("Connection Lost");
        else
            System.out.println("Connection Established Successfully");

        String gen[]= {"Male","Female","Transgender","Prefer not to say"};
        cgen.getItems().addAll(gen);
    }
    void clearall(){
        cmob.setText("");
        cadd.setText("");
        ccity.setText("");
        cmail.setText("");
        cname.setText("");
        cgen.setValue("");
        dob.setValue(null);
    }
    }


