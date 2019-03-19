package Admin;

import Admin.CompanyData;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import stock.Database_connection;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField id;

    @FXML
    private TextField tsa;

    @FXML
    private TextField sr;

    @FXML
    private TextField dt;

    @FXML
    private JFXButton logout;

    @FXML
    private TableView<Admin.CompanyData> companytableView;

    @FXML
    private TableColumn<Admin.CompanyData, String> idcolumn;

    @FXML
    private TableColumn<Admin.CompanyData, String> totalsharecolumn;

    @FXML
    private TableColumn<Admin.CompanyData, String> shareratecolumn;

    @FXML
    private TableColumn<Admin.CompanyData, String> announceddatecolumn;

    private Connection conn;
    private ObservableList<Admin.CompanyData> data;


    private Database_connection dc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dc = new Database_connection();

        try {
            Refresh();
            RefreshProfile();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        mouseclicked();
        profileMouseClicked();
    }
//refresh table

    public void Refresh() throws SQLException {

        Connection conn = dc.connect();
        data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `Company`");
        while (rs.next()) {
            data.add(new Admin.CompanyData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        idcolumn.setCellValueFactory(new PropertyValueFactory<Admin.CompanyData, String>("iD"));
        totalsharecolumn.setCellValueFactory(new PropertyValueFactory<Admin.CompanyData, String>("shareamount"));
        shareratecolumn.setCellValueFactory(new PropertyValueFactory<Admin.CompanyData, String>("sharerate"));
        announceddatecolumn.setCellValueFactory(new PropertyValueFactory<Admin.CompanyData, String>("dob"));

        companytableView.setItems(data);

    }

    //to load company data
    public void loadCompanyData(ActionEvent event) throws SQLException {
        conn = dc.connect();
        data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `Company`");
        while (rs.next()) {
            data.add(new Admin.CompanyData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        idcolumn.setCellValueFactory(new PropertyValueFactory<Admin.CompanyData, String>("iD"));
        totalsharecolumn.setCellValueFactory(new PropertyValueFactory<Admin.CompanyData, String>("shareamount"));
        shareratecolumn.setCellValueFactory(new PropertyValueFactory<Admin.CompanyData, String>("sharerate"));
        announceddatecolumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("dob"));

        companytableView.setItems(data);


    }

    //when admin adds the data
    public void addEntry(ActionEvent event) throws SQLException {

        String InsertSql = "INSERT INTO company (TotalsharesAnnouced,ShareRate,AnnouncedDate) VALUES(?,?,?)";
        try {
            Connection conn = Database_connection.connect();
            PreparedStatement psd = conn.prepareStatement(InsertSql);
            psd.setString(1, tsa.getText());
            psd.setString(2, sr.getText());
            psd.setString(3, dt.getText());
            psd.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Refresh();


    }

    //when clear is pressed

    public void Clear(ActionEvent event) {
        id.setText("");
        tsa.setText("");
        sr.setText("");
        dt.setText("");

    }

    //when delete button is pressed
    public void delete(ActionEvent event) throws SQLException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("");
        confirm.setContentText("Are you Sure you want to delete the data??");
        Optional<ButtonType> action = confirm.showAndWait();
        if (action.get() == ButtonType.OK) {
            String deleteQ = "DELETE FROM `company` WHERE id=?";
            try {
                Connection conn = Database_connection.connect();
                PreparedStatement psd = conn.prepareStatement(deleteQ);
                psd.setString(1, id.getText());
                psd.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }

        Refresh();
    }


//when update button is pressed

    public void Update(ActionEvent event) {
        String UpdateQ = "UPDATE company SET Id=?,TotalsharesAnnouced=?,ShareRate=?, AnnouncedDate=? WHERE Id='" + id.getText() + "'";
        Connection conn = null;
        try {
            conn = Database_connection.connect();
            PreparedStatement psd = conn.prepareStatement(UpdateQ);
            psd.setString(1, id.getText());
            psd.setString(2, tsa.getText());
            psd.setString(3, sr.getText());
            psd.setString(4, dt.getText());

            Alert update = new Alert(Alert.AlertType.INFORMATION);
            update.setContentText("Data has been Updated");
            update.showAndWait();
            psd.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //when Logout Button is clicked
    public void Logout(ActionEvent event) throws IOException {
        logout.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/stock/Login.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
    }


    //when tableview row is clicked

    public void mouseclicked() {
        companytableView.setOnMouseClicked(e -> {
            try {
                CompanyData com = (CompanyData) companytableView.getSelectionModel().getSelectedItem();
                Connection con = Database_connection.connect();
                String sql = "SELECT * FROM `company` WHERE Id=?";
                PreparedStatement psd = con.prepareStatement(sql);
                psd.setString(1, com.getiD());

                ResultSet rs = psd.executeQuery();

                while (rs.next()) {
                    id.setText(rs.getString(1));
                    tsa.setText(rs.getString(2));
                    sr.setText(rs.getString(3));
                    dt.setText(rs.getString(4));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        });
    }


    //for controlling the profile data from here because for single fxml we cannot create more than one controller

    @FXML
    private TableView<ProfileData> profietableview;

    @FXML
    private TableColumn<ProfileData, String> fnamecolum;

    @FXML
    private TableColumn<ProfileData, String> lnamecolumn;

    @FXML
    private TableColumn<ProfileData, String> dmatcolumn;

    @FXML
    private TableColumn<ProfileData, String> permanentcolumn;

    @FXML
    private TableColumn<ProfileData, String> contactcolumn;

    @FXML
    private TableColumn<ProfileData, String> citizencolumn;

    @FXML
    private TableColumn<ProfileData, String> passwordcolumn;

    @FXML
    private TextField fnametexfield;

    @FXML
    private TextField lnametextfield;

    @FXML
    private TextField dmattextfield;

    @FXML
    private TextField permanenttextfield;

    @FXML
    private TextField contacttextfield;

    @FXML
    private TextField citizentextfield;

    @FXML
    private TextField passwordtextfield;

    private ObservableList<ProfileData> data1 ;


    private void RefreshProfile() {
        try {
            Connection con = Database_connection.connect();
            data1=FXCollections.observableArrayList();
            PreparedStatement psd = con.prepareStatement("SELECT * FROM `sign up`");
            ResultSet rs = psd.executeQuery();

            while (rs.next()) {
                data1.add(new ProfileData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

            }

            fnamecolum.setCellValueFactory(new PropertyValueFactory<ProfileData, String>("fname"));
            lnamecolumn.setCellValueFactory(new PropertyValueFactory<ProfileData, String>("lname"));
            dmatcolumn.setCellValueFactory(new PropertyValueFactory<ProfileData, String>("dmat"));
            permanentcolumn.setCellValueFactory(new PropertyValueFactory<ProfileData, String>("permanentadd"));
            contactcolumn.setCellValueFactory(new PropertyValueFactory<ProfileData, String>("contact"));
            citizencolumn.setCellValueFactory(new PropertyValueFactory<ProfileData, String>("citizen"));
            passwordcolumn.setCellValueFactory(new PropertyValueFactory<ProfileData, String>("password"));

            profietableview.setItems(data1);
        } catch (SQLException e) {
            e.printStackTrace();
        }



}


    //when the mouse is clicked in profile data

    private void profileMouseClicked(){
        profietableview.setOnMouseClicked(e->{
            try {
                ProfileData profileObject=(ProfileData)profietableview.getSelectionModel().getSelectedItem();
                Connection conn=Database_connection.connect();
                PreparedStatement psd=conn.prepareStatement("SELECT * FROM `sign up`  WHERE citizen=?");
                psd.setString(1,profileObject.getCitizen());
                ResultSet rs=psd.executeQuery();
                while (rs.next()){
                    fnametexfield.setText(rs.getString(1));
                    lnametextfield.setText(rs.getString(2));
                    dmattextfield.setText(rs.getString(3));
                    permanenttextfield.setText(rs.getString(4));
                    contacttextfield.setText(rs.getString(5));
                    citizentextfield.setText(rs.getString(6));
                    passwordtextfield.setText(rs.getString(7));

                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        });
    }


    //when clear button is clicked
@FXML
    private void clearprofile(ActionEvent event){
        fnametexfield.clear();
        lnametextfield.clear();
        dmattextfield.clear();
        contacttextfield.clear();
        permanenttextfield.clear();
        citizentextfield.clear();
        passwordtextfield.clear();
    }

    private void clearprofile(){
        fnametexfield.clear();
        lnametextfield.clear();
        dmattextfield.clear();
        contacttextfield.clear();
        permanenttextfield.clear();
        citizentextfield.clear();
        passwordtextfield.clear();
    }

    //when add entry is button for profile is clicked
    @FXML
    private void addEntryProfile(ActionEvent event){
        try {
            Connection con=Database_connection.connect();
            String sql="INSERT INTO `sign up`(FirstName,LastName,DMAT,Permanent,Contact,Citizen,Password)VALUES(?,?,?,?,?,?,?)";
            PreparedStatement psd=con.prepareStatement(sql);
            psd.setString(1,fnametexfield.getText());
            psd.setString(2,lnametextfield.getText());
            psd.setString(3,dmattextfield.getText());
            psd.setString(4,permanenttextfield.getText());
            psd.setString(5,contacttextfield.getText());
            psd.setString(6,citizentextfield.getText());
            psd.setString(7,citizentextfield.getText());
            psd.executeUpdate();

            RefreshProfile();
            clearprofile();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //when delete button is clicked
    @FXML

    private void DeleteProfile(ActionEvent event) {
        try {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("delete");
            confirm.setContentText("Are you Sure you want to delete?");
            Optional<ButtonType> action = confirm.showAndWait();
            if (action.get() == ButtonType.OK) {
                Connection con = Database_connection.connect();
                String sql = "DELETE FROM `sign up` WHERE Citizen=?";
                PreparedStatement psd = con.prepareStatement(sql);
                psd.setString(1, citizentextfield.getText());
                psd.executeUpdate();

            }
            } catch(SQLException e){
                e.printStackTrace();
            }
            RefreshProfile();
            clearprofile();
        }

        //when update button is clicked
        @FXML
        private void UpdateProfile(ActionEvent event){
            try {
                Connection conn= Database_connection.connect();
                String sql="UPDATE `sign up` SET FirstName=?,LastName=?,DMAT=?,Permanent=?,Contact=?,Password=? WHERE Citizen='"+citizentextfield.getText()+"'";
                PreparedStatement psd=conn.prepareStatement(sql);
                psd.setString(1,fnametexfield.getText());
                psd.setString(2,lnametextfield.getText());
                psd.setString(3,dmattextfield.getText());
                psd.setString(4,permanenttextfield.getText());
                psd.setString(5,contacttextfield.getText());

                psd.setString(6,passwordtextfield.getText());

                Alert update=new Alert(Alert.AlertType.INFORMATION);
                update.setHeaderText("Update");
                update.setContentText("Data update sucessfully");
                update.showAndWait();
                psd.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            RefreshProfile();
            clearprofile();
        }


}
