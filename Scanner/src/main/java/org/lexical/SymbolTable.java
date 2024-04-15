package org.lexical;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A singleton class for symbol table that
 * helping for read and write data into database
 */
public class SymbolTable {
    private static SymbolTable instance;
    private final HashMap<String, String> table;
    private final DataBase dataBase;
    private List<Character> oneOrTwoCharTokensList;

    private SymbolTable() {
        table = new HashMap<>();
        dataBase = new DataBase();
        dataBase.deleteAllSavedTokens();
        generateTables();
    }

    /**
     * Use for get SymbolTable instance
     *
     * @return the only SymbolTable instance
     */
    public static SymbolTable getInstance() {
        if (instance == null)
            instance = new SymbolTable();
        return instance;
    }

    /**
     * Read symbols and put all of them into a hashmap called table
     */
    private void generateTables() {
        ResultSet resultSet;
        try {
            resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Symbols;");

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                String value = resultSet.getString("value");
                table.put(value, category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // create oneOrTwoCharTokensList
        oneOrTwoCharTokensList = new ArrayList<Character>();

        for (String tmp : table.keySet()) {
            if (!Character.isLetterOrDigit(tmp.charAt(0)))
                oneOrTwoCharTokensList.add(tmp.charAt(0));
        }

        // create Code_Tokens table
        try {
            dataBase.getStatement().execute("CREATE TABLE Code_Tokens (id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, lexeme TEXT, value TEXT, line INTEGER, scope TEXT)");
            System.out.println("Code_Tokens table created.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param target is a char that we want to know about it
     * @return true if target is one or two character token
     */
    public boolean isOneOrTwoCharToken(char target) {
        return oneOrTwoCharTokensList.contains(target);
    }

    /**
     * @return a hasMap which have all symbols
     */
    public HashMap<String, String> getTable() {
        return table;
    }
}
