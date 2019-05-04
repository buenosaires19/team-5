package ar.org.centro8.curso.java.connectors;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {
    //private static String driver="com.mysql.jdbc.Driver";
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String vendor="mysql";
    private static String server="localhost";
    private static String port="3306";
    private static String db="colegio";
    private static String user="root";
    private static String pass="";
    
    private static String url="jdbc:"+vendor+"://"+server+":"+port+"/"+db;
    
    private static Connection conn=null;
    
    private Connector(){} //constructor privado
    
    public synchronized static Connection getConnection(){
        if(conn==null){
            try {
                Class.forName(driver);
                conn=DriverManager.getConnection(url, user, pass);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return conn;
    }
    
}
