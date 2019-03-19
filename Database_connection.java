
package stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database_connection {
    Connection conn;
  public static Connection connect() throws SQLException{
      // Class.forName("com.mysql.jdbc.Driver");
      Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/stock","root","");
      return conn;


      
  }  

}
