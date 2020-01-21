package UI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

    private String password;

    private StringBuilder passwordHidden;

    public GameBoard (){

        WIDTH = 1000;
        HEIGHT = 600;
        MAX_INCORRECT = 6;
        MAX_PASSWORD_LENGTH = 10;

        HANGMAN_IMAGE_DIRECTORY = "Resources/";
        HANGMAN_IMAGE_TYPE = ".jpeg";
        HANGMAN_IMAGE_BASENAME = "hangman";
        LETTER_IMAGE_DIRECTORY = "Resources/";
        LETTER_IMAGE_TYPE = "jpeg";

        setTitle("HANGMAN ADVENTURES");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        addCloseWindowListener();

        initialize();
    }

    private void addCloseWindowListener(){

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we){

                int promt = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);

                if (promt == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });

    }




}
