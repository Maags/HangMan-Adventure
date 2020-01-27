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


    private final int WIDTH;

    private final int HEIGHT;

    private final int MAX_INCORRECT;

    private final int MAX_PASSWORD_LENGTH;

    private final String HANGMAN_IMAGE_DIRECTORY;

    private final String HANGMAN_IMAGE_TYPE;

    private final String HANGMAN_IMAGE_BASENAME;

    private final String LETTER_IMAGE_DIRECTORY;

    private final String LETTER_IMAGE_TYPE;

    private LetterRack gameRack;

    private HangManLoarder gameHangman;

    private int numIncorrect;

    private JLabel correct;

    private JLabel incorrect;

    private String word;

    private StringBuilder hiddenWord;

    public GameBoard() {

        WIDTH = 1000;
        HEIGHT = 600;
        MAX_INCORRECT = 6;
        MAX_PASSWORD_LENGTH = 15;

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

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2 - 200);
        setVisible(true);
    }

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

    private void addTextPanel() {

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1, 2));
        textPanel.add(correct);
        textPanel.add(incorrect);
        add(textPanel, BorderLayout.NORTH);
    }

    private void addLetterRack() {

        gameRack = new LetterRack(word, LETTER_IMAGE_DIRECTORY, LETTER_IMAGE_TYPE);
        gameRack.attachListeners(new TileListener());
        add(gameRack, BorderLayout.SOUTH);

    }

    private void addHangman(){

        JPanel hangmanPanel = new JPanel();
        gameHangman = new HangManLoarder(HANGMAN_IMAGE_BASENAME, HANGMAN_IMAGE_DIRECTORY, HANGMAN_IMAGE_TYPE);
        hangmanPanel.add(gameHangman);
        add(hangmanPanel, BorderLayout.CENTER);
    }

    private void getHiddenWord() {

        String[] options = {"Let's play", "Quit"};
        JPanel hiddenWordPanel = new JPanel();
        JLabel hiddenWordLabel = new JLabel("Enter hidden word to be guessed: ");
        JTextField hiddenWordText = new JTextField(MAX_PASSWORD_LENGTH);
        hiddenWordPanel.add(hiddenWordLabel);
        hiddenWordPanel.add(hiddenWordText);
        int confirm = -1;

        while (word.isEmpty()) {

            confirm = -1;
            confirm = JOptionPane.showOptionDialog(null, hiddenWordPanel, "Enter hidden word", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (confirm == 0) {
                word = hiddenWordText.getText();

                if (!word.matches("[a-zA-Z]+") || word.length() > MAX_PASSWORD_LENGTH) {

                    JOptionPane.showMessageDialog(null, "Word is invalid");
                    word = "";
                }
            } else if (confirm == 1)
                System.exit(0);
        }

        hiddenWord.append(word.replaceAll(".", "-"));
        correct.setText(correct.getText() + hiddenWord.toString());

    }

    private void newGameDialog() {

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "The password was: " + word + "\nWould you like to start a new game?", "Play Again?", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION)
            initialize();
        else System.exit(0);
    }

    private class TileListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            Object source = e.getSource();

            if (source instanceof LetterTileLoader) {
                char c = ' ';
                int index = 0;
                boolean updated = false;

                LetterTileLoader tilePressed = (LetterTileLoader) source;
                c = tilePressed.guess();

                while ((index = word.toLowerCase().indexOf(c, index)) != -1) {

                    hiddenWord.setCharAt(index, word.charAt(index));
                    index++;
                    updated = true;
                }

                if (updated) {

                    correct.setText("Word: " + hiddenWord.toString());

                    if (hiddenWord.toString().equals(word)) {

                        gameRack.removeListeners();
                        gameHangman.winImage();
                        newGameDialog();
                    }
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





