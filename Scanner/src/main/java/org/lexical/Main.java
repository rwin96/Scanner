package org.lexical;

public class Main {
    public static void main(String[] args) {
        String inputFileName = "input.txt";
        TextFileReader fileReader = new TextFileReader(inputFileName);
        LexicalAnalyzer.scan(fileReader.getLinesList());
    }
}