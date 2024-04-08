package org.lexical;


import java.util.ArrayList;
import java.util.List;

public class TokenCreator {
    private static TokenCreator instance;
    private final DataBase dataBase;
    private final SymbolTable symbolTable;
    private List<Token> savedTokensList;

    private TokenCreator() {
        savedTokensList = new ArrayList<>();
        dataBase = new DataBase();
        symbolTable = SymbolTable.getInstance();
    }

    public static TokenCreator getInstance() {
        if (instance == null)
            instance = new TokenCreator();
        return instance;
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


    public Token createToken(String lexim, int line) {
        Token newToken;
        if (symbolTable.getTable().get(lexim) != null)
            newToken = new Token(symbolTable.getTable().get(lexim), lexim, line);
        else
            newToken = new Token("Identifier", lexim, line);

        if (!isSaved(newToken))
            savedTokensList.add(newToken);

        return newToken;
    }

    public Token createToken(String lexim, int line, String scope) {
        Token newToken;
        if (symbolTable.getTable().get(lexim) != null)
            newToken = new Token(symbolTable.getTable().get(lexim), lexim, line);
        else
            newToken = new Token("Identifier", lexim, line);

        newToken.setScope(scope);
        if (!isSaved(newToken))
            savedTokensList.add(newToken);

        return newToken;
    }

    private boolean isSaved(Token token) {
        for (Token tmp : savedTokensList) {
            if (tmp.getLexim().equals(token.getLexim()) && tmp.getScope().equals(token.getScope()))
                return true;
        }
        return false;
    }

    public void updateSavedTokensDB() {
        //TODO: save to db
    }

}
