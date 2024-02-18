package Server;
import java.sql.*;
import javafx.application.Platform;


public class SQLDatabaseConnection {

    final static String DatabaseName = "LeafyLodge";
    public final static String url = "jdbc:mysql://localhost:3306/";
    public final static String filePath = "C://_saleem/information.sql";

    public static Connection connectToDatabase(String url, String username, String password) throws SQLException{
        Connection connection = null;

        try {
           // DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void exitProgram(Connection connection) {
        System.out.println("Exiting the program");
        SQLDatabaseConnection.closeConnection(connection);
        Platform.exit();   
    }

    public static void createDatabaseSQL(Connection connection) throws SQLException{
        Statement createDatabasStatement = null;

        try{
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS "+ DatabaseName;

            createDatabasStatement = connection.createStatement();
            createDatabasStatement.executeUpdate(createDatabaseSQL);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void useDatabaseSQL(Connection connection) throws SQLException{
        Statement useDatabasStatement = null;
        try{
            String useDatabaseSQL = "USE " + DatabaseName;
            useDatabasStatement = connection.createStatement();
            useDatabasStatement.execute(useDatabaseSQL);
        }catch(SQLException e){
            e.printStackTrace();
        }

      
    }

    // public static void executeSqlScript(Connection connection, String filePath) throws IOException, SQLException {
    //     try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    //         String line;
    //         StringBuilder sb = new StringBuilder();
    //         Statement statement = connection.createStatement();

    //         while ((line = reader.readLine()) != null) {
    //             sb.append(line);
    //             if (line.endsWith(";")) {
    //                 statement.executeUpdate(sb.toString());
    //                 sb.setLength(0);
    //             }
    //         }           

    
    //         statement.close();
            
    //     }
    // }
}