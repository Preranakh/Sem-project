/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SellItemsController implements Initializable {

    Connection cons;
    Statement stmn;

    @FXML
    private AnchorPane sellAnchor;

    @FXML
    private TableView<CompanyData> companyTable;
    @FXML
    private TableColumn<CompanyData, String> idcolumn;

    @FXML
    private TableColumn<CompanyData, String> totalshares;

    @FXML
    private TableColumn<CompanyData, String> sharerate;

    @FXML
    private TableColumn<CompanyData, String> announceDate;

    String Tsa;
    String sr;
    String da;
    public ObservableList<CompanyData> data;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database_connection db = new Database_connection();
        try {
            cons = db.connect();
            stmn = cons.createStatement();
            String sql = "SELECT * FROM `company`";
            data = FXCollections.observableArrayList();
            ResultSet rs = stmn.executeQuery(sql);
            while (rs.next()) {
                data.add(new CompanyData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        idcolumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("id"));
        totalshares.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("ts"));
        sharerate.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("srr"));
        announceDate.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("sdd"));

        this.companyTable.setItems(data);

        companyTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage graph = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Graph.fxml"));
                    Scene scene = new Scene(root);
                    graph.setScene(scene);
                    graph.setTitle("Company Graph");
                    graph.setResizable(false);
                    graph.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }


    @FXML
    private void Back(ActionEvent event) {

    }

    @FXML
    public void LoadGraph(ActionEvent event) {

        Stage graph = new Stage();
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1000, 600);
        graph.setScene(scene);
        graph.setTitle("Company history and Details");
        graph.show();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart chart = new BarChart(xAxis, yAxis, getChartData());
        chart.setTitle("Speculations");
        Timeline t1 = new Timeline();
        t1.getKeyFrames().add(new KeyFrame(Duration.millis(500),
                (ActionEvent e) -> {
                    chart.getData().stream().forEach((series) -> {

                    });
                }));
        root.getChildren().add(chart);


    }


    private ObservableList<XYChart.Series<String, Double>> getChartData() {
        double sbi = 17000;
        double nibl = 16500;
        double prabu = 8000;

        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();

        XYChart.Series<String, Double> sbi1 = new XYChart.Series<>();
        XYChart.Series<String, Double> c = new XYChart.Series<>();
        XYChart.Series<String, Double> cpp = new XYChart.Series<>();

        sbi1.setName("SBI");
        c.setName("NIBL");
        cpp.setName("Prabhu");

        for (int i = 2011; i < 2021; i++) {
            sbi1.getData().add(new XYChart.Data(Integer.toString(i), sbi));
            sbi = sbi + 1 * Math.random() - 0.2;

            c.getData().add(new XYChart.Data(Integer.toString(i), nibl));
            nibl = nibl + 2 * Math.random() - 2;

            cpp.getData().add(new XYChart.Data(Integer.toString(i), prabu));
            prabu = prabu + 3 * Math.random() - 0.2;
        }


        data.addAll(sbi1, c, cpp);

        return data;
    }

}



