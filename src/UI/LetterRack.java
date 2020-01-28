package UI;

import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class LetterRack extends JPanel {

    //The number of columns present in the letter tile rack.
    private int RACK_COLS;

    //The number of rows present in the letter tile rack.
    private int RACK_ROWS;

    //The layout of the letter rack.
    private GridLayout LETTER_RACK_LAYOUT;

    //The capacity of the letter rack.
    private int CAPACITY;

    //Directory containing the letter images.
    private String IMAGE_DIRECTORY;

    //The type of image (JPG, PNG..)
    private String IMAGE_TYPE;

    //The hidden word chosen to be guessed.
    private String hiddenWord;

    //An array of letters to be displayed on the GameBoard.
    private ArrayList<LetterTileLoader> rack;

    // The default constructor.
    public LetterRack(){
        this ("password", "src/Resources/", ".JPG");
    }

    /* Creates a new LetterRack given the password to be guessed
    letter image, directory of the image and the image type.
     */
    public LetterRack (String inPassword, String imageDirectory, String imageType) {

        RACK_COLS = 13;
        RACK_ROWS = 2;
        LETTER_RACK_LAYOUT = new GridLayout(RACK_ROWS, RACK_COLS);
        LETTER_RACK_LAYOUT.setVgap(10);
        CAPACITY = RACK_ROWS * RACK_COLS;

        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        rack = new ArrayList<>();
        hiddenWord = inPassword;

        //added a little pedding to make sure the letter rack is centered.
        setBorder(BorderFactory.createEmptyBorder(10, 17, 10, 10));
        BorderFactory.createEmptyBorder();
        setLayout(LETTER_RACK_LAYOUT);
        loadRack();
    }


    //Builds and loads the letter rack with letter tiles
    private void loadRack() {
        buildRack();
        for (LetterTileLoader tile : rack)
            add(tile);
    }

    //Builds a letter rack from a blend of the password and random letters.
    private void buildRack() {
        StringBuilder passwordBuilder = new StringBuilder(hiddenWord.toLowerCase());
        ArrayList<Character> tiles = new ArrayList<>();
        Random rand = new Random();
        int i = 0, j = 0;

        //add the password letters to the rack
        while (passwordBuilder.length() > 0) {

            //ensure that no letters are repeated in the tile rack.
            if (!tiles.contains(passwordBuilder.charAt(0))) {
                tiles.add(passwordBuilder.charAt(0));
                i++;
            }
            passwordBuilder.deleteCharAt(0);
        }
        //add random values to fill the remainder of the rack.
        for (; i < CAPACITY; i++) {
            Character c = 'a'; // 'a' is just default value
            do {
                c = (char) (rand.nextInt(26) + 'a');
            }
            while (tiles.contains(c));
            tiles.add(c);
        }

        //grab random tiles from the ArrayList to display randomly on the Gameboard.
        for (i = 0; i < CAPACITY; i++) {

            j = rand.nextInt(tiles.size());
            rack.add(new LetterTileLoader(tiles.get(j), IMAGE_DIRECTORY, IMAGE_TYPE));
            tiles.remove(j);
        }
    }

    //Add a TileListener to each LetterTile in the LetterRack
    public void attachListeners(MouseListener l) {

        for (LetterTileLoader tile : rack)
            tile.addTileListener(l);
    }

    //Remove alle TileListeners from all LetterTiles.
    public void removeListeners() {
        for (LetterTileLoader tile : rack)
            tile.removeTileListener();
    }


}
