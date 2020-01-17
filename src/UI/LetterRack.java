package UI;

import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class LetterRack extends JPanel {

    private final int RACK_COLS;

    private final int RACK_ROWS;

    private final GridLayout LETTER_RACK_LAYOUT;

    private final int CAPACITY;

    private final String IMAGE_DIRECTORY;

    private final String IMAGE_TYPE;

    private  final String pickWord;

    private final ArrayList<LetterTileLoader> rack;

    public LetterRack(){
        this ("pickWord", "Resources", ".jpeg");
    }

    public LetterRack (String inpickWord, String IMAGE_DIRECTORY, String IMAGE_TYPE){


        RACK_COLS = 13;
        RACK_ROWS = 2;
        LETTER_RACK_LAYOUT = new GridLayout(RACK_ROWS, RACK_COLS);
        LETTER_RACK_LAYOUT.setVgap(10);
        CAPACITY = RACK_ROWS * RACK_COLS;

        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        rack = new ArrayList<>();
        pickWord = inpickWord;

        setBorder(BorderFactory.createEmptyBorder(10,17, 10, 10));
        BorderFactory.createEmptyBorder();
        setLayout(LETTER_RACK_LAYOUT);
        loadrack();
        

    }




}
