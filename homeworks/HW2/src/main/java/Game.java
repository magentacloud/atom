import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    final Logger logger = (Logger) LogManager.getLogger();
    private int numberOfAttempts = 10;
    private String[] dictionary;
    boolean isEnd = false;

    public Game() {
        this.dictionary = readDictionary();
    }

    public void start() {
        System.out.println("Welcome to Bulls and Cows game!");
        String word = getWordFromDictionary();
        logger.info("Game started");
        System.out.print("I offered a " + word.length() + "-letter word, your guess?\n");
        Reader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        while (!isEnd) {
            String answer = "";
            while (answer.length() != word.length()) {
                answer = getWordFromUser(bufferedReader);
            }
            check(word, answer);
            if (numberOfAttempts == 0) {
                isEnd = true;
            }
            numberOfAttempts--;
        }
        System.out.println("Wanna play again? Y/N");
        logger.info("Game finished");
        Character ch = null;
        try {
            ch = bufferedReader.readLine().charAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ch.equals("Y")) {
            this.start();
        }
    }

    private String getWordFromUser(BufferedReader bufferedReader) {
        String answer = "";
        try {
            answer = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

    int countCows(String hiddenWord, String typedWord) {
        int cowsCount = 0;
        LinkedHashSet<Character> cows = new LinkedHashSet();
        for (int i = 0; i < hiddenWord.length(); i++) {
            if (!cows.contains(hiddenWord.charAt(i)))
                cows.add(hiddenWord.charAt(i));
        }
        for (int i = 0; i < typedWord.length(); i++) {
            if (cows.contains(typedWord.charAt(i)))
                cowsCount++;
        }
        return cowsCount;
    }

    private void check(String hiddenWord, String typedWord) {
        if (hiddenWord.equals(typedWord)) {
            System.out.print("You won!");
            isEnd = true;
        } else {
            int bulls = countBulls(hiddenWord, typedWord);
            int cows = countCows(hiddenWord, typedWord) - bulls;
            System.out.print("Cows: " + cows + "\n");
            System.out.print("Bulls: " + bulls + "\n");
        }
    }

    private String[] readDictionary() {
        String[] dictionary = new String[0];
        try {
            System.out.println(System.getProperty("user.dir"));
            String input = new String(Files.readAllBytes(Paths.get("dictionary.txt")));
            dictionary = input.split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    String getWordFromDictionary() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, dictionary.length + 1);
        return this.dictionary[randomNum];
    }

    private int countBulls(String hiddenWord, String typedWord) {
        int bulls = 0;
        for (int i = 0; i < hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == typedWord.charAt(i))
                bulls++;
        }
        return bulls;
    }

}
