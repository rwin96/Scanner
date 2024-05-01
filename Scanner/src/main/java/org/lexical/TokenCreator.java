package org.lexical;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TokenCreator {
    private static final DataBase dataBase = new DataBase();
    private static TokenCreator instance;
    private final SymbolTable symbolTable;
    private static List<Token> savedTokensList;

    private TokenCreator() {
        savedTokensList = new ArrayList<>();
        symbolTable = SymbolTable.getInstance();
    }

    public static TokenCreator getInstance() {
        if (instance == null)
            instance = new TokenCreator();
        return instance;
    }

    public static void updateSavedTokensDB() {
        for (Token tk : savedTokensList) {
            try {
                dataBase.getStatement().executeUpdate("INSERT INTO Code_Tokens(category, lexeme, line, scope) VALUES ('" + tk.getCategory() + "', '" + tk.getLexeme() + "', " + tk.getLine() + ", '" + tk.getScope() + "');");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Token createLiteralToken(String literal, int line) {
        Token newToken = new Token("literal", literal, line);
        savedTokensList.add(newToken);

        return newToken;
    }

    public Token createLiteralToken(String literal, int line, String scope) {
        Token newToken = new Token("literal", literal, line);
        newToken.setScope(scope);
        savedTokensList.add(newToken);

        return newToken;
    }

    public Token createToken(String lexeme, int line) {
        Token newToken;
        if (symbolTable.getTable().get(lexeme) != null)
            newToken = new Token(symbolTable.getTable().get(lexeme), lexeme, line);
        else
            newToken = new Token("Identifier", lexeme, line);

        if (!isSaved(newToken))
            savedTokensList.add(newToken);

        return newToken;
    }

    public Token createToken(String lexeme, int line, String scope) {
        Token newToken;
        if (symbolTable.getTable().get(lexeme) != null)
            newToken = new Token(symbolTable.getTable().get(lexeme), lexeme, line);
        else {
            if (Character.isDigit(lexeme.charAt(0)))
                newToken = new Token("Number", lexeme, line);
            else
                newToken = new Token("Identifier", lexeme, line);
        }
        newToken.setScope(scope);
        if (!isSaved(newToken))
            savedTokensList.add(newToken);

        return newToken;
    }

    private boolean isSaved(Token token) {
        for (Token tmp : savedTokensList) {
            if (tmp.getLexeme().equals(token.getLexeme()) && tmp.getScope().equals(token.getScope()))
                return true;
        }
        return false;
    }

}
