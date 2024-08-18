package com.example.projectjune24.workerinfo;
import java.io.*;
import java.net.URL;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.projectjune24.mysqlConnector;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;

public class workerinfoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> spcl;

    @FXML
    private TableView<prodBean> tablee;
    PreparedStatement st;
    @FXML
    void fillsa(ActionEvent event) {
        spcl.getItems().clear();
        tablee.getColumns().clear();
        TableColumn<prodBean, String> name=new TableColumn<prodBean, String>("Name");//kuch bhi
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(100);
        TableColumn<prodBean, String> mob=new TableColumn<prodBean, String>("Mobile");//kuch bhi
        mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mob.setMinWidth(100);
        TableColumn<prodBean, String> add=new TableColumn<prodBean, String>("Address");//kuch bhi
        add.setCellValueFactory(new PropertyValueFactory<>("address"));
        add.setMinWidth(100);
        TableColumn<prodBean, String> spl=new TableColumn<prodBean, String>("Special");//kuch bhi
        spl.setCellValueFactory(new PropertyValueFactory<>("Special"));
        spl.setMinWidth(150);
        TableColumn<prodBean, String> doe=new TableColumn<prodBean, String>("DOE");//kuch bhi
        doe.setCellValueFactory(new PropertyValueFactory<>("date"));
        doe.setMinWidth(100);
        tablee.getColumns().addAll(name,mob,add,spl,doe);
        tablee.setItems(getrecords());
    }

    ObservableList<prodBean> getrecords(){
        ObservableList<prodBean> ary= FXCollections.observableArrayList();

        String d=spcl.getSelectionModel().getSelectedItem();
        if(d!=null) {
            try {
                st = con.prepareStatement("select * from workers where wspl LIKE ?");
                st.setString(1, "%" + d + "%");
                ResultSet records = st.executeQuery();
                while (records.next()) {
                    String n = records.getString("wname");//col name
                    String m = records.getString("wmob");//col name
                    String a = records.getString("wadd");//col name
                    String s = records.getString("wspl");//col name
                    String e = records.getString("wdoe");//col name
                    ary.add(new prodBean(n, m, a, s, e));
                }


            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        else{
            try {
                st = con.prepareStatement("select * from workers");
                ResultSet records= st.executeQuery();
                while(records.next())
                {
                    String n=records.getString("wname");//col name
                    String m=records.getString("wmob");//col name
                    String a=records.getString("wadd");//col name
                    String s=records.getString("wspl");//col name
                    String e=records.getString("wdoe");//col name
                    ary.add(new prodBean(n,m,a,s,e) );
                }

            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        }
        return ary;
    }

    @FXML
    void fillw(ActionEvent event) {
        tablee.getColumns().clear();
        TableColumn<prodBean, String> name=new TableColumn<prodBean, String>("Name");//kuch bhi
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(100);
        TableColumn<prodBean, String> mob=new TableColumn<prodBean, String>("Mobile");//kuch bhi
        mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mob.setMinWidth(100);
        TableColumn<prodBean, String> add=new TableColumn<prodBean, String>("Address");//kuch bhi
        add.setCellValueFactory(new PropertyValueFactory<>("address"));
        add.setMinWidth(100);
        TableColumn<prodBean, String> spl=new TableColumn<prodBean, String>("Special");//kuch bhi
        spl.setCellValueFactory(new PropertyValueFactory<>("Special"));
        spl.setMinWidth(150);
        TableColumn<prodBean, String> doe=new TableColumn<prodBean, String>("DOE");//kuch bhi
        doe.setCellValueFactory(new PropertyValueFactory<>("date"));
        doe.setMinWidth(100);
        tablee.getColumns().addAll(name,mob,add,spl,doe);
        tablee.setItems(getrecords());
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
            File file = new File("Users.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Name,Mobile,Address,Special,DOE\n";
            writer.write(text);
            for (prodBean p : getrecords())
            {
                text = p.getName()+ "," + p.getMobile()+ "," + p.getAddress()+ "," + p.getSpecial()+ "," + p.getDate()+"\n";
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

        getrecords();
        String spls[]={"Trousers","Shirts","Blazers","Ladies Suits","Fancy Dresses","3 piece suits","Kurta Pajama","Sherwani"};
        spcl.getItems().addAll(spls);
    }

}
