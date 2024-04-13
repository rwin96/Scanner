package org.lexical;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LexicalAnalyzer {
    private static final SymbolTable symbolTable = SymbolTable.getInstance();
    private static final TokenCreator tokenCreator = TokenCreator.getInstance();

    public static void scan(ArrayList<List<Character>> fileReaderOutput) {

        List<Token> tokenList = new ArrayList<>();
        Token token = null;

        Stack<String> scopeStack = new Stack<>();
        scopeStack.push("main");
        int scopeCounter = 1;

        String string = "";
        boolean insideQ = false;

        for (List<Character> line : fileReaderOutput) {

            for (int i = 0; i < line.size(); i++) {

                char ch = line.get(i);

                if (symbolTable.isOneOrTwoCharToken(ch)) {
                    //check in the symbols

                    if (ch == '<') {
                        if (nextChar(line, line.indexOf('<')) == '=') {

                            string = "<=";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());
                            i++;

                        } else if (nextChar(line, line.indexOf('<')) == '<') {
                            string = "<<";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());
                            i++;

                        } else {
                            string = "<";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());

                        }

                    } else if (ch == '=') {

                        if (nextChar(line, line.indexOf('=')) == '=') {

                            string = "==";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());
                            i++;

                        } else {
                            string = "=";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());

                        }

                    } else if (ch == '>') {

                        if (nextChar(line, line.indexOf('>')) == '=') {

                            string = ">=";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());
                            i++;


                        } else if (nextChar(line, line.indexOf('>')) == '>') {

                            string = ">>";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());
                            i++;


                        } else {
                            string = ">";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());

                        }
                    } else if (ch == '+') {
                        if (nextChar(line, line.indexOf('+')) == '+') {
                            string = "++";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());
                            i++;

                        } else {
                            string = "+";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());

                        }
                    } else if (ch == '-') {
                        if (nextChar(line, line.indexOf('-')) == '-') {
                            string = "--";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());
                            i++;

                        } else {
                            string = "-";
                            token = tokenCreator.createToken(string, i, scopeStack.peek());

                        }
                    } else if (ch == '/') {
                        string = "/";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == '*') {
                        string = "*";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == '%') {
                        string = "%";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == '[') {
                        string = "[";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == ']') {
                        string = "]";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == '(') {
                        string = "(";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == ')') {
                        string = ")";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == '{') {
                        string = "{";
                        scopeStack.push("main-loop-" + scopeCounter);
                        scopeCounter++;

                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == '}') {
                        string = "}";
                        scopeStack.pop();

                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == '"') {

                        String literal = "";
                        insideQ = true;
                        StringBuilder stringBuilder = new StringBuilder();

                        int j = i++;

                        while (j < line.size() && line.get(j) != '"') {
                            stringBuilder.append(line.get(j));
                            j++;
                        }
                        literal = stringBuilder.toString();

                        token = tokenCreator.createLiteralToken(literal, i, scopeStack.peek());


                    } else if (ch == '\'') {
                        string = "'";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    } else if (ch == ';') {
                        string = ";";
                        token = tokenCreator.createToken(string, i, scopeStack.peek());

                    }

                } else {

                    //  id or keyword or number
                    StringBuilder stringBuilder = new StringBuilder();

                    if (Character.isDigit(ch)) {

                        stringBuilder.append(ch);

                        while (Character.isDigit(nextChar(line, line.indexOf(ch)))) {

                            stringBuilder.append(nextChar(line, line.indexOf(ch)));
                            i++;
                        }

                        token = tokenCreator.createToken(stringBuilder.toString(), i, scopeStack.peek());

                    } else {

                        stringBuilder.append(ch);
                        char nextChar = nextChar(line, line.indexOf(ch));
                        while (Character.isLetterOrDigit(nextChar) || nextChar == '_' || nextChar == '$') {

                            stringBuilder.append(nextChar);
                            i++;

                        }
                        token = tokenCreator.createToken(stringBuilder.toString(), i, scopeStack.peek());
                    }


                }

                tokenList.add(token);
            }


        }


    }


    private static char nextChar(List<Character> list, int index) {

        char ch = list.get(index + 1);

        return ch;
    }


}
