package UI;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

// A tile representing a letter to be guessed during a game.
public class LetterTileLoader extends JLabel {

    //The letter to be displayed on the tile.
    private final char IMAGE_LETTER;

    //The preferred width of the tiles.
    private int preferredWidth;

    //The preferred width of the tiles.
    private int preferredHeight;

    //Directory containing the letter images.
    private final String IMAGE_DIRECTORY;

    //The type of image (JPG, PNG..)
    private final String IMAGE_TYPE;

    //The current path of the current image.
    private String path;

    //The current image being displayed.
    private BufferedImage image;

    //A custom MouseListener from the GameBoard class to change the tiles on a click.
    private MouseListener tileListener;

    //The default constructor.
    public LetterTileLoader() {
        this('a', "src/Resources/",".JPG");
    }




    /* Creates a new LetterTile given the letter to be displayed:
    the directory of the letter image series and the image type
     */
    public LetterTileLoader(char imageLetter, String imageDirectory, String imageType) {

        preferredHeight = 70;
        preferredWidth = 70;

        IMAGE_LETTER = imageLetter;
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        path = IMAGE_DIRECTORY + IMAGE_LETTER + IMAGE_TYPE;
        image = loadImage(path);
    }

    /*Loads an image from a file
   Returns a Bufferedimage object on success, exits on failure.
     */
    private BufferedImage loadImage(String imagePath){

        BufferedImage img = null;

        try{
            img = ImageIO.read(new File(imagePath));
        }

        catch (IOException ex){

            System.err.println("loadimage() : Error: Image at " + imagePath + " could not be found");
            System.exit(1);
        }

        return img;

    }

    /* Changes the tile's appearance and removes the MouseListener to
    prevent the tile from being clicked again.
     */
    public char guess(){

        loadNewImage("guessed");
        removeTileListener();
        return IMAGE_LETTER;
    }

    //Loads a new image in the hangman image series.
    private void loadNewImage(String suffix){

        path = IMAGE_DIRECTORY + suffix + IMAGE_TYPE;
        image = loadImage(path);
        repaint();
    }

    //Add a TileListener to the tile.
    public void addTileListener(MouseListener l){

        tileListener = l;
        addMouseListener(tileListener);

    }

    //remove the tile's TileListener.
    public void removeTileListener(){

        removeMouseListener(tileListener);
    }

    @Override
    protected void paintComponent (Graphics g){

        super.paintComponent(g);
        g.drawImage(image, 0, 0, preferredHeight, preferredWidth, null);
    }


}
