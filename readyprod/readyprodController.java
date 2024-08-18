package com.example.projectjune24.readyprod;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.projectjune24.mysqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;

public class readyprodController {

    Connection con;
    PreparedStatement st;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> dodl;

    @FXML
    private ListView<String> drs;

    @FXML
    private ListView<String> ordid;

    @FXML
    private ComboBox<String> wknm;

    @FXML
    void allr(ActionEvent event) {
       for(int i=ordid.getItems().size()-1;i>=0;i--){
   String id=ordid.getItems().get(i);
           try{
               st=con.prepareStatement("update measurements set status=2 where orderid=?");
               st.setInt(1,Integer.parseInt(id));
               st.executeUpdate();
               ordid.getItems().remove(i);
               drs.getItems().remove(i);
               dodl.getItems().remove(i);
           }
           catch (Exception e){
               e.printStackTrace();
           }
       }
    }
    @FXML
    void chk(MouseEvent event) {
        if(!ordid.getItems().isEmpty() && event.getClickCount()==2){
            String or=ordid.getSelectionModel().getSelectedItem();
            int ind=ordid.getSelectionModel().getSelectedIndex();
            String dr=drs.getItems().get(ind);
            String dl=dodl.getItems().get(ind);
           boolean ans= showMsg(or+" "+dr+" "+dl);
           // SI.getItems().remove(item);
            //SP.getItems().remove(ind);
            if(ans==true){
                try{
                    st=con.prepareStatement("update measurements set status=2 where orderid=?");
                    st.setInt(1,Integer.parseInt(or));
                    st.executeUpdate();
                    ordid.getItems().remove(or);
                    drs.getItems().remove(ind);
                    dodl.getItems().remove(ind);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    void fillord(ActionEvent event) {
        try{
            st=con.prepareStatement("select * from measurements where status=1 and worker=?");
            st.setString(1,wknm.getValue());
            st.execute();
            ResultSet rs= st.getResultSet();
            while(rs.next()){
                ordid.getItems().add(rs.getString("orderid"));
                drs.getItems().add(rs.getString("dress"));
                dodl.getItems().add(rs.getString("dod"));
            }
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
    @FXML
    void initialize() {
        jasos=true;
        con= mysqlConnector.doConnect();
        if(con==null)
            System.out.println("Connection Lost");
        else
            System.out.println("Connection Established Successfully");

        try{
           st=con.prepareStatement("select distinct worker from measurements where status=1");
           st.execute();
            ResultSet rs= st.getResultSet();
            while(rs.next()){
                wknm.getItems().add(rs.getString("worker"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    boolean showMsg(String msgg){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Are You Sure?");
        alert.setContentText(msgg);
        ButtonType yesButton = new ButtonType("Yes");
        alert.getButtonTypes().setAll(yesButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            return true;
        }
        return false;
    }

}
