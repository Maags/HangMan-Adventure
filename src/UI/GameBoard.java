package UI;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GameBoard extends JFrame {


    //Width of the GameBoard.
    private final int WIDTH;

    //Height of the GameBoard.
    private final int HEIGHT;

    //The maximum number of guesses before game over.
    private final int MAX_INCORRECT;

    //The maximum amount of characters in the hidden word
    private final int MAX_HIDDENWORD_LENGTH;

    //Directory of the images for the hangman (lives).
    private final String HANGMAN_IMAGE_DIRECTORY;

    //Type of the images for the hangman.
    private final String HANGMAN_IMAGE_TYPE;

    //The base name of the images of the hangman (e.g. "hangman" for "hang_0.JPG, hangman_1.JPG, ...")
    private final String HANGMAN_IMAGE_BASENAME;

    //Directory of the images for the letters.
    private final String LETTER_IMAGE_DIRECTORY;

    //Type of the images for the letters.
    private final String LETTER_IMAGE_TYPE;

    //The letter rack containing the letters to be guessed.
    private LetterRack gameRack;

    //The hangman image placeholder.
    private HangManLoarder gameHangman;

    //The number of incorrect guesses.
    private int numIncorrect;

    //Display the hidden word as "_ _ _ _", revealing each letter as it is guessed.
    private JLabel correct;

    //Display the number of incorrect guesses.
    private JLabel incorrect;

    //The hidden word to be guessed by the player.
    private String word;

    //Stringbuilder used to hide the word, revealing letters as they are guessed by the player.
    private StringBuilder hiddenWord;

    //The default constructer.
    public GameBoard() {

        WIDTH = 1000;
        HEIGHT = 600;
        MAX_INCORRECT = 6;
        MAX_HIDDENWORD_LENGTH = 15;

        /*The default directory for the sample images is src/Resources/
        and the default image type is .JPG ensure the directory is created
        in the project folder if the sample images are used.
         */
        HANGMAN_IMAGE_DIRECTORY = "src/Resources/";
        HANGMAN_IMAGE_TYPE = ".JPG";
        HANGMAN_IMAGE_BASENAME = "hangman";
        LETTER_IMAGE_DIRECTORY = "src/Resources/";
        LETTER_IMAGE_TYPE = ".JPG";

        setTitle("HANGMAN ADVENTURES");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        addCloseWindowListener();

        initialize();
    }

    /* Initialize all elements of the GameBoard that must be refreshed
    upon the start of new game.
     */
    private void initialize() {

        numIncorrect = 0;

        correct = new JLabel("WORD: ");
        correct.setFont(new Font("Arial", Font.PLAIN, 28));
        incorrect = new JLabel("Incorrect: " + numIncorrect);
        word = new String();
        hiddenWord = new StringBuilder();

        getHiddenWord();
        addTextPanel();
        addLetterRack();
        addHangman();

        /*Set the board lightly above middle of the screen to prevent
        dialogs windows from blocking the images
         */
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2 - 200);
        setVisible(true);
    }

    //Ask the user to confirm before quitting out of the window.
    private void addCloseWindowListener() {

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {

                int promt = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);

                if (promt == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
    }

    //Adds the correct and incorrect labels to the top of the GameBoard
    private void addTextPanel() {

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1, 2));
        textPanel.add(correct);
        textPanel.add(incorrect);
        add(textPanel, BorderLayout.NORTH);
    }

    /* Adds the LetterRack to the bottom of the GameBoard and attaches
    the LetterTile TileListeners to the LetterTiles
     */
    private void addLetterRack() {

        gameRack = new LetterRack(word, LETTER_IMAGE_DIRECTORY, LETTER_IMAGE_TYPE);
        gameRack.attachListeners(new TileListener());
        add(gameRack, BorderLayout.SOUTH);

    }

    //Adds a panel that contains the hangman images to the middle of the GameBoard.
    private void addHangman(){

        JPanel hangmanPanel = new JPanel();
        gameHangman = new HangManLoarder(HANGMAN_IMAGE_BASENAME, HANGMAN_IMAGE_DIRECTORY, HANGMAN_IMAGE_TYPE);
        hangmanPanel.add(gameHangman);
        add(hangmanPanel, BorderLayout.CENTER);
    }

    /*Ask the player to enter a hidden word.
    Constrained by the length and content of the hidden word.
     */
    private void getHiddenWord() {

        String[] options = {"Let's play", "Quit"};
        JPanel hiddenWordPanel = new JPanel();
        JLabel hiddenWordLabel = new JLabel("Enter hidden word to be guessed: ");
        JTextField hiddenWordText = new JTextField(MAX_HIDDENWORD_LENGTH);
        hiddenWordPanel.add(hiddenWordLabel);
        hiddenWordPanel.add(hiddenWordText);
        int confirm = -1;

        while (word.isEmpty()) {

            confirm = -1;
            confirm = JOptionPane.showOptionDialog(null, hiddenWordPanel, "Enter hidden word", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (confirm == 0) {
                word = hiddenWordText.getText();

                /* Makes sure the hidden word is only made up of letters and is less than
                the maximum hidden word length
                 */
                if (!word.matches("[a-zA-Z]+") || word.length() > MAX_HIDDENWORD_LENGTH) {

                    JOptionPane.showMessageDialog(null, "Word is invalid");
                    word = ""; //if hidden word is empty an error occurs.
                }
            } else if (confirm == 1)
                System.exit(0);
        }

        hiddenWord.append(word.replaceAll(".", "-"));
        correct.setText(correct.getText() + hiddenWord.toString());

    }

    //Ask the player for a new game, when the game ends. Restarts GameBoard.
    private void newGameDialog() {

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "The password was: " + word + "\nWould you like to start a new game?", "Play Again?", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION)
            initialize();
        else System.exit(0);
    }

    /* A custom MouseListener for the LetterTiles that detects when the player
    "guesses" (clicks on) a LetterTile and updates the game accordingly.
     */
    private class TileListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            Object source = e.getSource();

            if (source instanceof LetterTileLoader) {
                char c = ' ';
                int index = 0;
                boolean updated = false;

                //Cast the source of the click to a LetterTile object.
                LetterTileLoader tilePressed = (LetterTileLoader) source;
                c = tilePressed.guess();

                //reveal each instance of the character if it appears in the hidden word.
                while ((index = word.toLowerCase().indexOf(c, index)) != -1) {

                    hiddenWord.setCharAt(index, word.charAt(index));
                    index++;
                    updated = true;
                }

                //if the guess was correct, update the GameBoard and check for a win
                if (updated) {

                    correct.setText("Word: " + hiddenWord.toString());

                    if (hiddenWord.toString().equals(word)) {

                        gameRack.removeListeners();
                        gameHangman.winImage();
                        newGameDialog();
                    }
                //else, add an incorrect guess and check for a loss
                } else {
                    incorrect.setText("Incorrect: " + ++numIncorrect);

                    if (numIncorrect >= MAX_INCORRECT) {

                        gameHangman.loseImage();
                        gameRack.removeListeners();
                        newGameDialog();
                    } else
                        gameHangman.nextImage(numIncorrect);
                }
            }
        }

        /*These methods contains no code. But it must they mus be implemented in every
        MouseListener for application to run..
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }


}





