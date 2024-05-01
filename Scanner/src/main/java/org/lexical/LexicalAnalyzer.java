package org.lexical;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LexicalAnalyzer {
    private static final SymbolTable symbolTable = SymbolTable.getInstance();
    private static final TokenCreator tokenCreator = TokenCreator.getInstance();

    public static List<Token> scan(ArrayList<List<Character>> fileReaderOutput) {

        List<Token> tokenList = new ArrayList<>();
        Token token = null;

        Stack<String> scopeStack = new Stack<>();
        scopeStack.push("main");
        int scopeCounter = 1;

        String string = "";
        boolean insideQ = false;

        int lineNumber = 0;
        for (List<Character> line : fileReaderOutput) {
            lineNumber++;
            for (int i = 0; i < line.size(); i++) {

                char ch = line.get(i);

                if (ch == ' ')
                    continue;

                if (symbolTable.isOneOrTwoCharToken(ch)) {
                    //check in the symbols

                    if (ch == '<') {
                        if (nextChar(line, line.indexOf('<')) == '=') {

                            string = "<=";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                            i++;

                        } else if (nextChar(line, line.indexOf('<')) == '<') {
                            string = "<<";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                            i++;

                        } else {
                            string = "<";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                        }

                    } else if (ch == '=') {

                        if (nextChar(line, line.indexOf('=')) == '=') {

                            string = "==";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                            i++;

                        } else {
                            string = "=";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                        }

                    } else if (ch == '>') {

                        if (nextChar(line, line.indexOf('>')) == '=') {

                            string = ">=";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                            i++;


                        } else if (nextChar(line, line.indexOf('>')) == '>') {

                            string = ">>";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                            i++;


                        } else {
                            string = ">";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                        }
                    } else if (ch == '+') {
                        if (nextChar(line, line.indexOf('+')) == '+') {
                            string = "++";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                            i++;

                        } else {
                            string = "+";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                        }
                    } else if (ch == '-') {
                        if (nextChar(line, line.indexOf('-')) == '-') {
                            string = "--";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                            i++;

                        } else {
                            string = "-";
                            token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                        }
                    } else if (ch == '/') {
                        string = "/";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == '*') {
                        string = "*";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == '%') {
                        string = "%";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == '[') {
                        string = "[";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == ']') {
                        string = "]";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == '(') {
                        string = "(";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == ')') {
                        string = ")";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == '{') {
                        string = "{";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == '}') {
                        string = "}";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());
                        scopeStack.pop();

                    } else if (ch == '"') {
                        token = tokenCreator.createToken("\"", lineNumber, scopeStack.peek());
                        tokenList.add(token);

                        String literal;
                        StringBuilder stringBuilder = new StringBuilder();

                        i++;

                        while (i < line.size()) {
                            char temp = line.get(i);
                            if (temp == '"')
                                break;

                            stringBuilder.append(temp);
                            i++;
                        }
                        literal = stringBuilder.toString();

                        token = tokenCreator.createLiteralToken(literal, lineNumber, scopeStack.peek());


                    } else if (ch == '\'') {
                        string = "'";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    } else if (ch == ';') {
                        string = ";";
                        token = tokenCreator.createToken(string, lineNumber, scopeStack.peek());

                    }

                } else {

                    //  id or keyword or number
                    StringBuilder stringBuilder = new StringBuilder();

                    if (Character.isDigit(ch)) {

                        stringBuilder.append(ch);

                        while (Character.isDigit(nextChar(line, i))) {

                            stringBuilder.append(nextChar(line, i));
                            i++;
                        }

                        token = tokenCreator.createToken(stringBuilder.toString(), lineNumber, scopeStack.peek());

                    } else {
                        stringBuilder.append(ch);
                        char nextChar = nextChar(line, i);
                        while (Character.isLetterOrDigit(nextChar) || nextChar == '_' || nextChar == '$') {

                            stringBuilder.append(nextChar);
                            i++;
                            nextChar = nextChar(line, i);

                        }

                        if (stringBuilder.toString().equalsIgnoreCase("loop"))
                            scopeStack.push("main-loop-" + scopeCounter++);

                        if (stringBuilder.toString().equalsIgnoreCase("if") || stringBuilder.toString().equalsIgnoreCase("so"))
                            scopeStack.push("main-loop-" + (scopeCounter - 1));

                        token = tokenCreator.createToken(stringBuilder.toString(), lineNumber, scopeStack.peek());
                    }

                }

                tokenList.add(token);
            }

        }

        return tokenList;
    }


    private static char nextChar(List<Character> list, int index) {
        char ch;

        try {
            ch = list.get(index + 1);
        } catch (IndexOutOfBoundsException e) {
            ch = '^';
        }

        return ch;
    }


}
