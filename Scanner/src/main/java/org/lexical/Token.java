package org.lexical;

public class Token {
    private String category;
    private String lexeme;
    private String scope = "main";
    private int line;
    private String value;

    public Token(String category, String lexeme, int line) {
        this.category = category;
        this.lexeme = lexeme;
        this.line = line;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<" + category + " , " + lexeme + '>';
    }
}
