
package com.example.projectjune24.admin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.projectjune24.flagss;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.projectjune24.flagss.jasos;

public class adminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button c;

    @FXML
    private Button eo;
    @FXML
    private PieChart pii;


    @FXML
    private Button m;

    @FXML
    private Button od;

    @FXML
    private TextField pass;

    @FXML
    private Button rp;

    @FXML
    private Button we;

    @FXML
    private Button wi;

    @FXML
    void chkkit(ActionEvent event) {
     String p=pass.getText();
     if(p.equals("Admin123") )
     {
            c.setDisable(false);
            eo.setDisable(false);
            m.setDisable(false);
            od.setDisable(false);
            rp.setDisable(false);
            we.setDisable(false);
            wi.setDisable(false);
        }
        else
        {
            showMsg("Incorrect password");
        }
    }
    @FXML
    void cust(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/customerenrolll/customerenroll-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void meas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/measurementss/measurements-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void orddel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/orddell/orddel-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ordexp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/orderexplorerr/orderexplorer-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void redyp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/readyprodd/readyprod-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void winfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/workerinfoo/workerinfo-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void wnrol(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectjune24/workerss/workers-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    void showMsg(String msgg){
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Delivery Status");
        alert.setContentText(msgg);
        alert.showAndWait();
    }
    @FXML
    void initialize() {
if(jasos){
    c.setDisable(false);
    eo.setDisable(false);
    m.setDisable(false);
    od.setDisable(false);
    rp.setDisable(false);
    we.setDisable(false);
    wi.setDisable(false);
}
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(new PieChart.Data("In process",2),new PieChart.Data("Deliverd",15),new PieChart.Data("Recived",8));
        pii.setData(data);
    }

}
