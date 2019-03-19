/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;

public class LoginController implements Initializable {

Connection con;
PreparedStatement stmn;
private Database_connection db;
ResultSet rs;

    @FXML
    private JFXButton signup;
    @FXML
    private JFXTextField citinum;
    @FXML
    private JFXPasswordField paa;
    @FXML
    private JFXButton signin;

    private static String citi;
    private static String paas;


    //when sign up button is pressed
    @FXML
    public void signUp() throws IOException{
        signup.getScene().getWindow().hide();
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Sign Up");
         stage.setResizable(false);
         stage.show();
}

    public void AdminLogin(){
        try {
            Connection conn= Database_connection.connect();
            String sql="SELECT * FROM `admin` WHERE Citizen=? AND Password=?";
            PreparedStatement psd=conn.prepareStatement(sql);
            psd.setString(1,citi);
            psd.setString(2,paas);
            psd.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    
    //when sign in button is pressed
    @FXML
    public void signIn(ActionEvent event) throws IOException {
        
        citi=citinum.getText();
        paas=paa.getText();


    if(citi.equals("admin") && paas.equals("admin")){
           Stage admin=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Admin/Admin.fxml"));

        Scene scene=new Scene(root);
           admin.setTitle("Admin Panel");
           admin.setScene(scene);
           admin.setResizable(false);
           admin.show();

           signin.getScene().getWindow().hide();
}
        
        else{
              
           try{
           String sql="SELECT * FROM `sign up`WHERE Citizen='"+citi+"' && Password='"+paas+"'";
           con=db.connect();
            stmn=con.prepareStatement(sql);
            rs=stmn.executeQuery();
            
             if(rs.next()){
                 ProfileController.getUser(citi);
                 HOMEController.getUser(citi);
                 signin.getScene().getWindow().hide();
                 Stage stage=new Stage();
                 Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                  Scene scene = new Scene(root);
                  stage.setTitle("Sign In");
                  stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
             }
           stmn.close();
           rs.close();
           }catch(Exception e){
          
           System.out.println(e);
           }
    
        }



}



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        citinum.setStyle("-fx-text-inner-color:white;"
                + "-fx-text-inner-color:white;");
        paa.setStyle("-fx-text-inner-color:white;"
                + "-fx-prompt-text-fill:white;");
      db=new Database_connection();
      System.out.println(citi);
}
    

    
}
