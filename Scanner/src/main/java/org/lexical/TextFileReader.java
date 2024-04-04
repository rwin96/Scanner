package org.lexical;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {
    public static void main(String[] args) {
        File myfile = new File("input.txt");
        List<char[]> linesList = readLinesFromFile(myfile);
        printLines(linesList);
    }

    private static List<char[]> readLinesFromFile(File file) {
        List<char[]> linesList = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(file));
            String line;
            while (( line = buf.readLine() ) != null) {
                char[] characters = line.toCharArray();
                linesList.add(characters);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesList;
    }

    private static void printLines(List<char[]> linesList) {
        for (char[] characters : linesList) {
            for (char c : characters) {
                System.out.print(c + " ");
            }

        }
    }
}

