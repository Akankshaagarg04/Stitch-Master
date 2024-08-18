package com.example.projectjune24.workers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import com.example.projectjune24.mysqlConnector;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;


public class workersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> spllist;

    @FXML
    private TextField wadd;

    @FXML
    private TextField wmob;

    @FXML
    private TextField wname;

    @FXML
    private TextField wspl;
PreparedStatement st;
    @FXML
    void wdel(ActionEvent event) {
        try {
            st=con.prepareStatement("delete from workers where wname=?");
            st.setString(1,wname.getText());
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
    void Update(ActionEvent event) {
try{
    st= con.prepareStatement("update workers set wmob=?,wadd=?,wspl=? where wname=?");
    st.setString(4,wname.getText());
    st.setString(1,wmob.getText());
    st.setString(2,wadd.getText());
    st.setString(3,wspl.getText());
    st.execute();
    System.out.println("Record Updated");
clearall();
}
catch(Exception exp){
    exp.printStackTrace();
}
    }


    @FXML
    void wfetch(ActionEvent event) {
 try{
     st=con.prepareStatement("select* from workers where wname=?");
st.setString(1,wname.getText());
ResultSet records= st.executeQuery();
while(records.next()){
    String em=records.getString("wmob");
    String n=records.getString("wadd");
    String add=records.getString("wspl");
    wadd.setText(n);
    wmob.setText(em);
    wspl.setText(add);
}
 }
 catch(Exception e){
     e.printStackTrace();
 }
    }

    @FXML
    void addspl(MouseEvent event) {
if(event.getClickCount()==2){
    String ans=wspl.getText();
   if(ans.equals("")){
       ans+=spllist.getSelectionModel().getSelectedItem();

   }
   else{
       ans+=","+spllist.getSelectionModel().getSelectedItem();
   }
    wspl.setText(ans);
}
    }

    @FXML
    void wsave(ActionEvent event) {
        try{
            st=con.prepareStatement("insert into workers values(?,?,?,?,?)");
            st.setString(1,wname.getText());
            st.setString(2,wmob.getText());
            st.setString(3,wadd.getText());
            st.setString(4,wspl.getText());
            st.setDate(5,java.sql.Date.valueOf(LocalDate.now()));
            st.execute();
            System.out.println("Record Saved");
            clearall();
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }@FXML
    void goback(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/adminn/admin-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    Connection con;
    @FXML
    void initialize() {
        jasos=true;
        con= mysqlConnector.doConnect();
        if(con==null)
            System.out.println("Connection Lost");
        else
            System.out.println("Connection Established Successfully");

        String spls[]={"Trousers","Shirts","Blazers","Ladies Suits","Fancy Dresses","3 piece suits","Kurta Pajama","Sherwani"};
        spllist.getItems().addAll(spls);

    }
    void clearall(){
 wspl.setText("");
        wname.setText("");
        wmob.setText("");
        wadd.setText("");
    }

}
