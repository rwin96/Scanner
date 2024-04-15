package org.lexical;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class use for connect to database
 * if you want to use database,
 * just create an instance of this class
 */
public class DataBase {

    /**
     * You can execute your queries with this field`s help
     */
    private Statement statement;

    public DataBase() {
        connection();
    }

    /**
     * This method use for connect to database
     */
    private void connection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:Symbol_Table.db");
            statement = connection.createStatement();
            System.out.println("Connected to database Successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete all saved data from past with this method
     */
    public void deleteAllSavedTokens() {
        if (statement == null) connection();

        try {
            getStatement().execute("DROP TABLE Code_Tokens;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return statement
     */
    public Statement getStatement() {
        return statement;
    }
}