package com.example.projectjune24.measurements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.projectjune24.mysqlConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;

public class measurementsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView img;

    @FXML
    private TextField mbill;

    @FXML
    private DatePicker mdod;

    @FXML
    private ComboBox<String> mdress;

    @FXML
    private TextArea mm;

    @FXML
    private TextField mmob;

    @FXML
    private TextField mprice;

    @FXML
    private ComboBox<String> mqty;

    @FXML
    private ComboBox<String> mworker;


    @FXML
    void calBill(KeyEvent event) {
      String p=mprice.getText();
      int pr=Integer.parseInt(p);
      String q=mqty.getValue();
      int qt=Integer.parseInt(q);
      mbill.setText(String.valueOf(pr*qt));
    }
    PreparedStatement st;
    @FXML
    void fillworker(ActionEvent event) {
        mworker.getItems().clear();

      String d=mdress.getSelectionModel().getSelectedItem();
      if(d!=null){
          try{
              st=con.prepareStatement("select wname from workers where wspl like(?)");
 st.setString(1,"%"+d+"%");
              ResultSet record=st.executeQuery();
              while(record.next()){
                  String n=record.getString("wname");
                  mworker.getItems().add(n);
              }
          }
          catch(Exception exo){
              exo.printStackTrace();;
          }
      }

    }

    @FXML
    void doNew(ActionEvent event) {
     mm.setText("");
     mmob.setText("");
     mprice.setText("");
     mbill.setText("");
     mdod.setValue(null);
     mdress.setValue("");
     mqty.getSelectionModel().select(0);
     mworker.getSelectionModel().select("");
     img.setImage(null);
    }

    @FXML
    void doSave(ActionEvent event) {
        try{
            st=con.prepareStatement("insert into measurements values(NULL,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1,mmob.getText());
            st.setString(2,mdress.getValue());
            st.setString(3,filepath);
            LocalDate local=mdod.getValue();
            Date dt= Date.valueOf(local);
            st.setDate(4,dt);
            st.setInt(5,Integer.parseInt(mqty.getSelectionModel().getSelectedItem()));
            st.setInt(6,Integer.parseInt(mbill.getText()));
            st.setString(7,mm.getText());
            st.setString(8,mworker.getSelectionModel().getSelectedItem());
            st.setDate(9, Date.valueOf(LocalDate.now()));
            st.setInt(10,1);
            st.executeUpdate();
            System.out.println("Record Saved");
           ResultSet rs= st.getGeneratedKeys();
           if(rs.next()){
               showMsg(String.valueOf(rs.getInt(1)));
           }
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }
    String filepath="nopic.jpg";
    @FXML
    void findimg(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Select Design Pic:");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("*.*", "*.*")
        );
        File file=chooser.showOpenDialog(null);
        filepath=file.getAbsolutePath();


        try {
            img.setImage(new Image(new FileInputStream(file)));
        }
        catch (FileNotFoundException e) {	e.printStackTrace();}
    }

    @FXML
    void findold(ActionEvent event) {
   try{
       st=con.prepareStatement("select meas from measurements where mob=? and dress=?");
       st.setString(1,mmob.getText());
       st.setString(2,mdress.getSelectionModel().getSelectedItem());
       ResultSet rec= st.executeQuery();
       StringBuilder sb=new StringBuilder();
       while(rec.next()){
           String s=rec.getString("meas");
           sb.append(s);
           sb.append("\n");
       }
       mm.setText(sb.toString());
   }
   catch (Exception e){
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
        mdress.getItems().addAll(spls);
        for(int i=1;i<=10;i++){
            mqty.getItems().add(String.valueOf(i));
        }
        mqty.getSelectionModel().select(0);

    }
    void showMsg(String msgg){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Delivery Status");
        alert.setContentText(msgg);
        alert.showAndWait();
    }

}
