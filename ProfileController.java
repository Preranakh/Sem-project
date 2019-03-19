/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import java.io.*;
import java.sql.PreparedStatement;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ProfileController implements Initializable {

    static Connection conn;
    static PreparedStatement stmn;
    @FXML
    private TextField fnn;
    @FXML
    private TextField lnn;
    @FXML
    private TextField pm;
    @FXML
    private TextField con;
    @FXML
    private TextField cit;
    @FXML
    private TextField dma;
    @FXML
    private PasswordField passwordfield;

    @FXML
    private ImageView imageview;

    @FXML
    private AnchorPane holderPane;

    AnchorPane sellAnchor;
    AnchorPane BuyShare;
    AnchorPane home;
    @FXML
    private JFXButton sellbtn;

    @FXML
    private JFXButton buybtn;

    @FXML
    private JFXButton logout;



    private static String userName;
    private String fn;
    private String ln;
    private String pa;
    private String cont;
    private String dmat;
    private String cits;
    private String pass;

    public static void getUser(String s) {
        userName = s;
        // System.out.println("Caught "+userName+" and in getUser");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Database_connection dbcon = new Database_connection();
        try {
            conn = dbcon.connect();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }


        try {
            String sql = "SELECT * FROM `sign up` WHERE Citizen=?";
            stmn = conn.prepareStatement(sql);
            stmn.setString(1, userName);
            ResultSet rs = stmn.executeQuery();
            while (rs.next()) {
                fn = rs.getString("FirstName");
                ln = rs.getString("LastName");
                cont = rs.getString("Contact");
                pa = rs.getString("Permanent");
                dmat = rs.getString("DMAT");
                cits = rs.getString("Citizen");
                pass=rs.getString("Password");


                InputStream is = rs.getBinaryStream("Image");

                OutputStream os = new FileOutputStream(new File("login.png"));

                byte[] content = new byte[1024];

                int size = 0;

                while ((size = is.read(content)) != -1) {

                    os.write(content, 0, size);

                }

                Image image = new Image("file:login.png", 100, 150, true, true);
                imageview.setImage(image);

            }
            System.out.println(fn);
            fnn.setText(fn);
            lnn.setText(ln);
            pm.setText(pa);
            con.setText(cont);
            cit.setText(cits);
            dma.setText(dmat);
            passwordfield.setText(pass);



        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//when sell share button is clicked

    public void sellShare(javafx.event.ActionEvent actionEvent) throws IOException {
        sellAnchor=FXMLLoader.load(getClass().getResource("SellItems.fxml"));
         holderPane.getChildren().clear();
         holderPane.getChildren().add(sellAnchor);
         }
//when profile button is clicked
    public void profilebtns(javafx.event.ActionEvent actionEvent) throws IOException {
       home=FXMLLoader.load(getClass().getResource("HOME.fxml"));
       holderPane.getChildren().clear();
       holderPane.getChildren().add(home);

    }
//when Buy Button is Pressed
    public void buyShare(javafx.event.ActionEvent actionEvent) throws IOException {
        BuyShare=FXMLLoader.load(getClass().getResource("BuyShares.fxml"));
        holderPane.getChildren().clear();
        holderPane.getChildren().add(BuyShare);


    }

    //when Logout Button is pressed


    public void LogOut(javafx.event.ActionEvent actionEvent) throws IOException {
        logout.getScene().getWindow().hide();
        Stage login=new Stage();
        Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene=new Scene(root);
        login.setScene(scene);
        login.show();
    }

    //when update button is clicked
@FXML
    public void update(ActionEvent event){
        try {
            Connection conn=Database_connection.connect();
            String sql="UPDATE `sign up` SET FirstName=?,LastName=?,DMAT=?,Permanent=?,Contact=?,Citizen=?,Password=? WHERE Citizen='"+userName+"'";
            PreparedStatement psd=conn.prepareStatement(sql);
            psd.setString(1,fnn.getText());
            psd.setString(2,lnn.getText());
            psd.setString(3,dma.getText());
            psd.setString(4,pm.getText());
            psd.setString(5,con.getText());
            psd.setString(6,cit.getText());
            psd.setString(7,passwordfield.getText());

            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setHeaderText("updated");
            al.setContentText("Profile Updated successfully");
            al.showAndWait();
            psd.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
