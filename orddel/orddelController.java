package com.example.projectjune24.orddel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.projectjune24.mysqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;

public class orddelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> bill;

    @FXML
    private ListView<String> dress;

    @FXML
    private ListView<String> id;

    @FXML
    private TextField omob;

    @FXML
    private ListView<String> status;

    @FXML
    private TextField totbill;

    @FXML
    void delall(ActionEvent event) {
try{
st=con.prepareStatement("update measurements set status=3 where status=2 and mob=?");
    st.setString(1,omob.getText());
   st.executeUpdate();
    fillall(null);
    showMsg("Delivered");
}
catch(Exception e){
    e.printStackTrace();
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
PreparedStatement st;
    @FXML
    void fillall(ActionEvent event) {
        status.getItems().clear();
        id.getItems().clear();
        dress.getItems().clear();
        bill.getItems().clear();
try{
    st=con.prepareStatement("select * from measurements where status in (1,2) and mob=? order by status desc");
    st.setString(1,omob.getText());
    ResultSet records=st.executeQuery();
    int sum=0;
    while(records.next()){
        status.getItems().add(records.getString("status"));
        id.getItems().add(records.getString("orderid"));
        dress.getItems().add(records.getString("dress"));
        bill.getItems().add(records.getString("bill"));
        if(Integer.parseInt(records.getString("status"))==2){
            sum+=Integer.parseInt(records.getString("bill"));
        }
    }
   totbill.setText(String.valueOf(sum));
}
catch (Exception e){
    e.printStackTrace();
}
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

    }
    void showMsg(String msgg){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Order ID");
        alert.setContentText(msgg);
        alert.showAndWait();
    }
}
