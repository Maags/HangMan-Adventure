import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class HangmanLogic {
    String hiddenWord;
    StringBuilder currentWord;
    ArrayList<Character> previousGuesses = new ArrayList<>();


    int maxTries = 6;
    int currentTry = 0;
//
    ArrayList<String> dictionary = new ArrayList<>();
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;

    public HangmanLogic() throws IOException {
        connectLibrary();
        hiddenWord = pickWord();
        currentWord = initializeHiddenWord();
    }

    //checks is dictionary library is available
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
    public StringBuilder initializeHiddenWord() {
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
    public String getHiddenWord() {
        return "Hidden word: " + currentWord.toString();
    }

    //returns the results, if the player won or lost
    public boolean gameOver() {
        if (didWeWin()) {
            System.out.println("CONGRATS! You have won the game");
        }
        else if (didWeLose()) {
            System.out.println("YOU GOT HANGED..\n" +
                                " The hidden word was: " + hiddenWord + ".");
        }
        return didWeWin() || didWeLose();
    }

    public boolean didWeWin() {
        String guess = getCondensedCurrentGuess();
        return guess.equals(hiddenWord);
    }

    public boolean didWeLose() {
        return currentTry >= maxTries;
    }

    public String getCondensedCurrentGuess() {
        String guess = currentWord.toString();
        return guess.replace(" ", "");
    }

    public boolean isGuessedAlready(char guess) {
        return previousGuesses.contains(guess);
    }

    public boolean playGuess(char guess) {
        boolean isItAGoodGuess = false;
        previousGuesses.add(guess);
        for (int i = 0; i< hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == guess) {
                currentWord.setCharAt(i*2, guess);
                isItAGoodGuess = true;
            }
        }
        if (!isItAGoodGuess) {
            currentTry++;
        }
        return isItAGoodGuess;
    }

    //drawing the hangman in the console
    public String drawHangman() {
        switch(currentTry) {
            case 0: return noPerson();
            case 1: return addHead();
            case 2: return addBody();
            case 3: return addOneArm();
            case 4: return addSecondArm();
            case 5: return addFirstLeg();
            default: return fullPerson();
        }

    }

    private String noPerson() {
        return " - - - - -\n"+
                "|        |\n"+
                "|        \n" +
                "|       \n"+
                "|        \n" +
                "|       \n" +
                "|\n" +
                "|\n";
    }

    private String addHead() {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|       \n"+
                "|        \n" +
                "|       \n" +
                "|\n" +
                "|\n";
    }

    private String addBody() {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|        | \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    private String addOneArm() {
        return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / |  \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    private String addSecondArm() {
        return  " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    private String addFirstLeg() {
        return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|       / \n" +
                "|\n" +
                "|\n";
    }

    private String fullPerson() {
        return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|       / \\ \n" +
                "|\n" +
                "|\n";
    }
}
