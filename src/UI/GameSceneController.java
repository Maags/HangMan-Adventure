package UI;
/*
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameSceneController {

    @FXML Text hiddenWordField;
    @FXML ImageView hangmanImage;

    //stores the hidden word
    String hiddenWord;
    //which words have the user guessed and how many have they left
    StringBuilder currentGuess;
    ArrayList<Character> previousGuesses = new ArrayList<>();


    int maxTries = 6;
    int currentTry = 0;

    //creating arraylist and enable us to make "changes" words into dashes
    ArrayList<String> dictionary = new ArrayList<>();
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;

    public GameSceneController() throws IOException {
        connectLibrary();
        hiddenWord = pickWord();
        currentGuess = initializeCurrentGuess();
    }

    // Reads lines from dictionary.txt and add them to dictionary arraylist
    // Checks is dictionary library is available
    public void connectLibrary() throws IOException {
        try {
            File inFile = new File("src//dictionary.txt");
            fileReader = new FileReader(inFile);
            bufferedFileReader = new BufferedReader(fileReader);
            String currentLine = bufferedFileReader.readLine();
            while (currentLine != null) {
                dictionary.add(currentLine);
                currentLine = bufferedFileReader.readLine();
            }
            bufferedFileReader.close();
            fileReader.close();
        }
        catch (IOException e) {
            System.out.println("Could not find library");
        }
    }

    //picking a random word from dictionary
    public String pickWord() {
        Random rand = new Random();
        int wordIndex = Math.abs(rand.nextInt())%dictionary.size();
        return dictionary.get(wordIndex);
    }

    //creating the hiddenword with dashes and space (_ _ _ _ _)
    public StringBuilder initializeCurrentGuess() {
        StringBuilder transWord = new StringBuilder();
        for (int i = 0; i< hiddenWord.length()*2; i++) {
            if (i%2 == 0) {
                transWord.append("_");
            }
            else {
                transWord.append(" ");
            }
        }
        return transWord;
    }

    //Shows the hidden word
    public void getHiddenWord(ActionEvent actionEvent) {

        hiddenWordField.setText(currentGuess.toString());

    }
}
*/