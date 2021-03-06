package socio.connectors;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectorMySQL {
    private static String driver="com.mysql.jdbc.Driver";
    private static String user="root";
    private static String pass="";
    private static String url="jdbc:mysql://localhost:3306/socios";
    private static Connection conn=null;
    private ConnectorMySQL(){}
    public synchronized static Connection getConnection(){ 
        if(conn==null){
            try {
                Class.forName(driver);
                conn=DriverManager.getConnection(url, user, pass);
            } catch (Exception e) { e.printStackTrace(); }
        }
        return conn;
    }
}