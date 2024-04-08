package org.lexical;

public class Token {
    private String category;
    private String lexim;
    private String scope = "main";
    private int line;
    private String value;

    public Token(String category, String lexim, int line) {
        this.category = category;
        this.lexim = lexim;
        this.line = line;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLexim() {
        return lexim;
    }

    public void setLexim(String lexim) {
        this.lexim = lexim;
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
}
