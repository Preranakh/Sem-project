package stock;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HOMEController implements Initializable {


    @FXML
    private AnchorPane home;

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
    private static String Username;
    Database_connection db;
    PreparedStatement psd;
    ResultSet rs;
    private Connection conn;

    private String fn;
    private String ln;
    private String pa;
    private String cont;
    private String dmat;
    private String cits;

    public static void getUser(String citi){
        Username=citi;
        System.out.println(Username);
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db=new Database_connection();

        String sql="SELECT * FROM `sign up` WHERE Citizen=?";
        try {
            conn=db.connect();
            psd=conn.prepareStatement(sql);
            psd.setString(1,Username);
            rs=psd.executeQuery();
            while (rs.next()) {
                fn = rs.getString("FirstName");
                ln = rs.getString("LastName");
                cont = rs.getString("Contact");
                pa = rs.getString("Permanent");
                dmat=rs.getString("DMAT");
                cits=rs.getString("Citizen");

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        fnn.setText(fn);
        lnn.setText(ln);
        pm.setText(pa);
        con.setText(cont);
        cit.setText(cits);
        dma.setText(dmat);
    }


}
