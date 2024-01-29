package Server;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLQueries {

    public static void truncateTable(Connection connection) {
        try {
            String truncateQuery = "TRUNCATE TABLE Information";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(truncateQuery);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String MaxLoggedValueRecord(Connection connection) {

        try {
            String query = "SELECT LogID, LineID, LogTime, LoggedValue " +
                           "FROM Information " +
                           "WHERE LoggedValue = (SELECT MAX(LoggedValue) FROM Information)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int logId = resultSet.getInt("LogID");
                        String lineId = resultSet.getString("LineID");
                        String logTime = resultSet.getString("LogTime");
                        double loggedValue = resultSet.getDouble("LoggedValue");

                        return "LogID: " + logId + ", LineID: " + lineId + ", LogTime: " + logTime + ", LoggedValue: " + loggedValue;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No data was found";
    }

    
    public static List<String> TotalLoggedValueForLogID(Connection connection, int startLogId, int endLogId) {
        List<String> resultList = new ArrayList<>();
        // retrieve total LoggedValue for LogID in the range 1-6
        
        try {
            String query = "SELECT LogID, SUM(LoggedValue) AS TotalLoggedValue " +
                           "FROM Information "+
                           "WHERE LogID BETWEEN ? AND ? " +
                           "GROUP BY LogID";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, startLogId);
                preparedStatement.setInt(2, endLogId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int retrievedLogId = resultSet.getInt("LogID");
                        double totalLoggedValue = resultSet.getDouble("TotalLoggedValue");

                        String resultString = "LogID: " + retrievedLogId + ", TotalLoggedValue: " + totalLoggedValue;
                        resultList.add(resultString);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }    

    public static String LindIDwhereLoggedValueMin(Connection connection){
        // Select LineID where LoggedValue is min except the value = 0!
        try{
            String query = "SELECT LineID, LoggedValue " +
                        "FROM Information " +
                        "WHERE LoggedValue = (SELECT MIN(NULLIF(LoggedValue, 0)) FROM Information)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        String lineId = resultSet.getString("LineID");
                        double loggedValue = resultSet.getDouble("LoggedValue");

                        return "LineID: " + lineId + ", LoggedValue: " + loggedValue;
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return "No data was found!";
    }

    public static String MaxProductionLineID(Connection connection, String startDate, String endDate) {
        try {
            String query = "SELECT LineID " +
                           "FROM Information " +
                           "WHERE LogTime BETWEEN ? AND ? " +
                           "GROUP BY LineID " +
                           "ORDER BY SUM(LoggedValue) DESC " +
                           "LIMIT 1";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, startDate);
                preparedStatement.setString(2, endDate);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String maxProductionLineID = resultSet.getString("LineID");

                        return "LineID with maximum production: " + maxProductionLineID;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No data found within the specified date range.";

    }

    public static List<String> LoggedValueEqualsZero(Connection connection){
            List<String> resultList = new ArrayList<>();
        try{
            String query = "SELECT LogID, LineID, LogTime, LoggedValue "+
                            "FROM Information " + 
                            "WHERE LoggedValue = 0";

            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()) {
                        int logId = resultSet.getInt("LogID");
                        String lineId = resultSet.getString("LineID");
                        String logTime = resultSet.getString("LogTime");
                        double loggedValue = resultSet.getDouble("LoggedValue");

                        String resultString = "LogID: " + logId + ", LineID: " + lineId + ", LogTime: " + logTime + ", LoggedValue: " + loggedValue;
                        resultList.add(resultString);                     
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return resultList;
    }
}
