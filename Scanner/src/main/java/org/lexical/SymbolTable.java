package org.lexical;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * A singleton class for symbol table that
 * helping for read and write data into database
 */
public class SymbolTable {
    private static SymbolTable instance;
    private final HashMap<String, String> table;

    private SymbolTable() {
        table = new HashMap<>();
        DataBase dataBase = new DataBase();
        generateTable(dataBase);
        dataBase.deleteAllSavedTokens();
    }

    /**
     * Use for get SymbolTable instance
     * @return the only SymbolTable instance
     */
    public static SymbolTable getInstance() {
        if (instance == null)
            instance = new SymbolTable();
        return instance;
    }

    /**
     * @param dataBase
     * Read symbols and put all of them into a hashmap called table
     */
    private void generateTable(DataBase dataBase) {
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
    }

    /**
     *
     * @return a hasMap which have all symbols
     */
    public HashMap<String, String> getTable() {
        return table;
    }
}
