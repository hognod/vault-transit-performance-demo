import java.sql.*;
import java.util.ArrayList;

public class MySQL {
    private static String mysqlURL;
    private static String mysqlUser;
    private static String mysqlPassword;
    
    public MySQL(String mysql_host, String mysql_port, String mysql_db_name, String mysql_username, String mysql_userpw) {
        this.mysqlURL = String.format("jdbc:mysql://%s:%s/%s", mysql_host, mysql_port, mysql_db_name);
        this.mysqlUser = mysql_username;
        this.mysqlPassword = mysql_userpw;
    }
    
    public void insertData(String data) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(mysqlURL, mysqlUser, mysqlPassword);
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO vault_data (data) VALUES (?)");
            pstmt.setString(1, data);
            
            pstmt.executeUpdate();
            
            pstmt.close();
            connection.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<String> getData() {
        ArrayList<String> data;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(mysqlURL, mysqlUser, mysqlPassword);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vault_data");
            data = new ArrayList<>();
            
            while(rs.next()) {
                data.add(rs.getString(2));
            }
            
            rs.close();
            stmt.close();
            connection.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        
        return data;
    }
}
