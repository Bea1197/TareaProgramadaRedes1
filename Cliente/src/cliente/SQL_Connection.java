/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class SQL_Connection {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL_CONEXION = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=REDES;"
                    + "user=INSTADMIN1;"
                    + "password=bea119797;";
    public boolean Registrar(String usuario, String contraseña) {
        Connection dbConnection = null;
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL_CONEXION);
            
            CallableStatement callableStatement = conn.prepareCall("{call sp_INSERTAR_USUARIO (?,?)}");

            callableStatement.setString(1, usuario);
            callableStatement.setString(2, contraseña);
            callableStatement.execute();;
            conn.close();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean Iniciar_Sesion(String usuario, String contraseña) {
        Connection dbConnection = null;
        String result="F";
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL_CONEXION);
            
            CallableStatement callableStatement = conn.prepareCall("{call sp_LOGIN (?,?,?)}");

            callableStatement.setString(1, usuario);
            callableStatement.setString(2, contraseña);
            callableStatement.registerOutParameter(3, java.sql.Types.CHAR);

            callableStatement.execute();
            result = callableStatement.getString(3);
            if (result.equals("V")) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}