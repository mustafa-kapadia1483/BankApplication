package bankapp;

import java.sql.*;

public class SQLImport {
    final String URL = "jdbc:mysql://localhost:3306/";
    final String USERNAME = "root";
    final String PASSWORD = "1483";
    String query = "SELECT * FROM custInformation";
    String accountNumber;
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    SQLImport(String accountNumber) throws SQLException, ClassNotFoundException{
        this.accountNumber = accountNumber;
        setUpConnection();
    }

    private void setUpConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        statement = connection.createStatement();
        statement.executeQuery("USE bankapplication");
    }

    private boolean checkPassword(String password) throws SQLException {
        query += " AND password = '" + password + "'";
        resultSet = statement.executeQuery(query);
        return resultSet.next();
    }

    public boolean authenticate(String password) throws SQLException{
        query = "SELECT * FROM custInformation WHERE account_number = '" + accountNumber + "'";
        resultSet = statement.executeQuery(query);
        return resultSet.next() && checkPassword(password);
    }

    public void setBalance(double newBalance) throws SQLException {
        query = "UPDATE custInformation SET balance = " + newBalance
                + " WHERE account_number = '" + accountNumber + "'";
        statement.executeUpdate(query);
    }

    public double getBalance() throws SQLException{
        query = "SELECT balance FROM custInformation " +
                "WHERE account_number = '" + accountNumber + "'";
        resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getDouble(1);
    }

    public String getCustomerName() throws SQLException {
        query = "SELECT first_name,last_name FROM custInformation " +
                "WHERE account_number = '" + accountNumber + "'";
        resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString(1) + " " + resultSet.getString(2);
    }
}
