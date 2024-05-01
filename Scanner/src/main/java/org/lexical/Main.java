package org.lexical;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inputFileName = "input.txt";
        TextFileReader fileReader = new TextFileReader(inputFileName);
        List<Token> tokenList = LexicalAnalyzer.scan(fileReader.getLinesList());

        for(Token tk : tokenList) {
            System.out.format("%-50s\t Scope: %s\n", tk.toString(), tk.getScope());
        }
        System.out.println(tokenList.size());

        TokenCreator.updateSavedTokensDB();
    }
}