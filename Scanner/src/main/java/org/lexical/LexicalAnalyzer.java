package org.lexical;

import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer {
    SymbolTable symbolTable = new SymbolTable();
    public void scan(ArrayList<List<Character>> fileReaderOutput) {

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
                            // Relational_op
                            //Rwin
                            i++;

                        } else if (nextChar(line, line.indexOf('<')) == '<') {
                            string = "<<";
                            // Special_Op
                            //Rwin
                            i++;

                        } else {
                            string = "<";
                            //comp_OP
                            //Rwin

                        }

                    } else if (ch == '=') {

                        if (nextChar(line, line.indexOf('=')) == '=') {
                            string = "==";
                            //equal
                            i++;

                        } else {
                            string = "=";
                            //assignment
                            //Rwin

                        }

                    } else if (ch == '>') {

                        if (nextChar(line, line.indexOf('>')) == '=') {
                            string = ">=";
                            //relational_OP
                            //Rwin
                            i++;


                        } else if (nextChar(line, line.indexOf('>')) == '>') {
                            string = ">>";
                            // specail_OP
                            //Rwin
                            i++;


                        } else {
                            string = ">";
                            // com_OP
                            //Rwin

                        }
                    } else if (ch == '+') {
                        if (nextChar(line, line.indexOf('+')) == '+') {
                            string = "++";
                            //increment
                            //Rwin
                            i++;

                        } else {
                            string = "+";
                            //plus
                            //Rwin

                        }
                    } else if (ch == '-') {
                        if (nextChar(line, line.indexOf('-')) == '-') {
                            string = "--";
                            //decrement
                            //Rwin
                            i++;

                        } else {
                            string = "-";
                            //minus
                            //Rwin

                        }
                    } else if (ch == '/') {
                        string = "/";
                        //divide
                        //Rwin

                    } else if (ch == '*') {
                        string = "*";

                    } else if (ch == '%') {
                        string = "%";
                        //Rwin

                    } else if (ch == '[') {
                        string = "[";
                        //Open_bracket
                        //Rwin

                    } else if (ch == ']') {
                        string = "]";
                        //Close_bracket
                        //Rwin

                    } else if (ch == '(') {
                        string = "(";
                        //Open_parantess
                        //Rwin

                    } else if (ch == ')') {
                        string = ")";
                        //Close_parantes
                        //Rwin

                    } else if (ch == '{') {
                        string = "{";
                        //Open_curly_brace
                        //Rwin

                    } else if (ch == '}') {
                        string = "}";
                        //Close_curly_brace
                        //Rwin

                    } else if (ch == '"') {
                        String literal = "";
                        insideQ = true;
                        StringBuilder stringBuilder = new StringBuilder();

                        int j = i++;

                        while (j < line.size() && line.get(j) != '"') {
                            stringBuilder.append(line.get(j));
                            j++;
                        }
                        literal = stringBuilder.toString(); // -->Rwin


                    } else if (ch == '\'') {
                        string = "\'";
                        //Rwin

                    } else if (ch == ';') {
                        string = ";";
                        //Rwin

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
                        //Rwin

                    } else {

                        stringBuilder.append(ch);
                        char nextChar = nextChar(line, line.indexOf(ch));
                        while (Character.isLetterOrDigit(nextChar) || nextChar == '_' || nextChar == '$') {

                            stringBuilder.append(nextChar);
                            i++;

                        }
                        //Rwin
                    }


                }


            }


        }


    }


    public char nextChar(List<Character> list, int index) {

        char ch = list.get(index + 1);

        return ch;
    }




}
