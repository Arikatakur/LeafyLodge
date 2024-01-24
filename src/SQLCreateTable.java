import java.sql.*;

public class SQLCreateTable{
    
    public static void createTable(Connection connection) throws SQLException{
        Statement statement = null;
        try{
            statement = connection.createStatement();

            String createTableSQL = "CREATE TABLE IF NOT EXISTS Information" +
                    " (LogID INT, " +
                    "LineID VARCHAR(512), " +
                    "LogTime VARCHAR(512), " +
                    "LoggedValue DOUBLE, " +
                    "CmdType INT, " +
                    "Description VARCHAR(512), " +
                    "UnitType VARCHAR(512))";

            // Execute the SQL query to create the table
            statement.executeUpdate(createTableSQL);
        }catch(SQLException e){
            e.getStackTrace();
        }

        finally{
            if (statement != null) {
                statement.close();
            }
        }
    }
    
}
