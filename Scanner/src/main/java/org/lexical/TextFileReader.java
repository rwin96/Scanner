package org.lexical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {
    private ArrayList<List<Character>> linesList;

    public TextFileReader(String fileName) {
        File myfile = new File(fileName);
        linesList = readLinesFromFile(myfile);
    }

    private static ArrayList<List<Character>> readLinesFromFile(File file) {
        ArrayList<List<Character>> linesList = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buf.readLine()) != null) {
                char[] characters = line.toCharArray();

                List<Character> characterList = new ArrayList<>();
                for (char c : characters) {
                    characterList.add(c);
                }

                linesList.add(characterList);
            }
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

    public ArrayList<List<Character>> getLinesList() {
        return linesList;
    }
}

