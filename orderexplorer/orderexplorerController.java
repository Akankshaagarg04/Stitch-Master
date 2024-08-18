package com.example.projectjune24.orderexplorer;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.projectjune24.mysqlConnector;
import com.example.projectjune24.workerinfo.prodBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;

public class orderexplorerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField mno;

    @FXML
    private ComboBox<String> osts;

    @FXML
    private TableView<ordBean> tables;

    @FXML
    private ComboBox<String> wch;

    ObservableList<ordBean> records(){
        ObservableList<ordBean> ary= FXCollections.observableArrayList();
        int d=osts.getSelectionModel().getSelectedIndex();
        String wr=wch.getSelectionModel().getSelectedItem();
        String my=mno.getText();
        System.out.println(my);
        if(!my.isEmpty()){
            try{
                st=con.prepareStatement("select * from measurements where mob=?");
                st.setString(1,my);
                ResultSet rec=st.executeQuery();
                while(rec.next()){
                    String w=rec.getString("worker");
                    String dr=rec.getString("dress");
                    String s=rec.getString("status");
                    String i=rec.getString("orderid");
                    String m=rec.getString("mob");
                    String b=rec.getString("bill");
                    ary.add(new ordBean(w,dr,s,i,m,b));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(d>=0 && wr!=null){
            try{
                st=con.prepareStatement("select * from measurements where status =? and worker =?");
                st.setString(1,String.valueOf(d+1));
                st.setString(2,wr);
                ResultSet rec=st.executeQuery();
                while(rec.next()){
                    String w=rec.getString("worker");
                    String dr=rec.getString("dress");
                    String s=rec.getString("status");
                    String i=rec.getString("orderid");
                    String m=rec.getString("mob");
                    String b=rec.getString("bill");
                    ary.add(new ordBean(w,dr,s,i,m,b));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(d>=0){
            try{
                st=con.prepareStatement("select * from measurements where status =?");
                st.setString(1,String.valueOf(d+1));
                ResultSet rec=st.executeQuery();
                while(rec.next()){
                    String w=rec.getString("worker");
                    String dr=rec.getString("dress");
                    String s=rec.getString("status");
                    String i=rec.getString("orderid");
                    String m=rec.getString("mob");
                    String b=rec.getString("bill");
                    ary.add(new ordBean(w,dr,s,i,m,b));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        return ary;
    }
    @FXML
    void exportit(ActionEvent event) {
        try {
            writeExcel();
            System.out.println("Exported");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
            File file = new File("order.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="OrderID,Worker,Dress,Status,Bill,Mobile\n";
            writer.write(text);
            for (ordBean p : records())
            {
                text = p.getOdid()+ "," + p.getBill()+ "," + p.getWorker()+ "," + p.getDress()+ "," + p.getStatus()+ "," + p.getMob()+"\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
            writer.close();
        }
    }

    @FXML
    void mnoc(ActionEvent event) {
        System.out.println("hello");
        cols();

    }
    ObservableList<ordBean> allrecords() {
        ObservableList<ordBean> ary = FXCollections.observableArrayList();
            try {
                st = con.prepareStatement("select * from measurements ");
                ResultSet rec = st.executeQuery();
                while (rec.next()) {
                    String w = rec.getString("worker");
                    String dr = rec.getString("dress");
                    String s = rec.getString("status");
                    String i = rec.getString("orderid");
                    String m = rec.getString("mob");
                    String b = rec.getString("bill");
                    ary.add(new ordBean(w, dr, s, i, m, b));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ary;
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
    void showit(ActionEvent event) {
            tables.getColumns().clear();
            TableColumn<ordBean, String> Status=new TableColumn<ordBean, String>("Status");//kuch bhi
            Status.setCellValueFactory(new PropertyValueFactory<>("status"));
            Status.setMinWidth(100);
            TableColumn<ordBean, String> OrderID=new TableColumn<ordBean, String>("OrderID");//kuch bhi
            OrderID.setCellValueFactory(new PropertyValueFactory<>("odid"));
            OrderID.setMinWidth(100);
            TableColumn<ordBean, String> worker=new TableColumn<ordBean, String>("Worker");//kuch bhi
            worker.setCellValueFactory(new PropertyValueFactory<>("worker"));
            worker.setMinWidth(100);
            TableColumn<ordBean, String> dress=new TableColumn<ordBean, String>("Dresses");//kuch bhi
            dress.setCellValueFactory(new PropertyValueFactory<>("dress"));
            dress.setMinWidth(100);
            TableColumn<ordBean, String> mobi=new TableColumn<ordBean, String>("Mobile");//kuch bhi
            mobi.setCellValueFactory(new PropertyValueFactory<>("mob"));
            mobi.setMinWidth(100);
            TableColumn<ordBean, String> billi=new TableColumn<ordBean, String>("Bill");//kuch bhi
            billi.setCellValueFactory(new PropertyValueFactory<>("bill"));
            billi.setMinWidth(100);

            tables.getColumns().addAll(Status,OrderID,worker,dress,mobi,billi);
            tables.setItems(allrecords());

    }

    @FXML
    void stsc(ActionEvent event) {
        System.out.println("hii");
        cols();

    }

    @FXML
    void wchcc(ActionEvent event) {
        cols();

    }
    void cols(){
        tables.getColumns().clear();
        TableColumn<ordBean, String> Status=new TableColumn<ordBean, String>("Status");//kuch bhi
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        Status.setMinWidth(100);
        TableColumn<ordBean, String> OrderID=new TableColumn<ordBean, String>("OrderID");//kuch bhi
        OrderID.setCellValueFactory(new PropertyValueFactory<>("odid"));
        OrderID.setMinWidth(100);
        TableColumn<ordBean, String> worker=new TableColumn<ordBean, String>("Worker");//kuch bhi
        worker.setCellValueFactory(new PropertyValueFactory<>("worker"));
        worker.setMinWidth(100);
        TableColumn<ordBean, String> dress=new TableColumn<ordBean, String>("Dresses");//kuch bhi
        dress.setCellValueFactory(new PropertyValueFactory<>("dress"));
        dress.setMinWidth(100);
        TableColumn<ordBean, String> mobi=new TableColumn<ordBean, String>("Mobile");//kuch bhi
        mobi.setCellValueFactory(new PropertyValueFactory<>("mob"));
        mobi.setMinWidth(100);
        TableColumn<ordBean, String> billi=new TableColumn<ordBean, String>("Bill");//kuch bhi
        billi.setCellValueFactory(new PropertyValueFactory<>("bill"));
        billi.setMinWidth(100);

        tables.getColumns().addAll(Status,OrderID,worker,dress,mobi,billi);
        tables.setItems(records());

    }

    PreparedStatement st;
    void fillwork(){
        try{
            st=con.prepareStatement("select wname from workers");
            ResultSet rec=st.executeQuery();
            while(rec.next()){
                wch.getItems().add(rec.getString("wname"));
            }

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

        String types[]={"In Progress","Recieved","Delivered"};
        osts.getItems().addAll(types);
        fillwork();

    }

}
