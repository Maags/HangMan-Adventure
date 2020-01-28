package UI;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

//Displays a selected series of hang images to the GameBoard.
public class HangManLoarder extends JLabel {

    //Preferred width of the images.
    private int preferredWidth;

    //The preferred height of the images
    private int preferredHeight;

    //The base name of the images of the hangman (e.g. "hangman" for "hang_0.JPG, hangman_1.JPG, ...")
    private final String IMAGE_BASE_NAME;

    //Directory of the images for the hangman (lives).
    private final String IMAGE_DIRECTORY;

    //Type of the images for the hangman.
    private final String IMAGE_TYPE;

    //The current path of the current image.
    private String path;

    //The current image being displayed.
    private BufferedImage image;

    //The default constructor.
    public HangManLoarder () {this("hangman", "src/Resources/", ".JPG");}


    /* Creates a new hangman given the image series:
    "base name, the directory of the hangman images, and the type of image".
     */
    public HangManLoarder(String imageBaseName, String imageDirectory, String imageType) {

        preferredHeight = 300;
        preferredWidth = 300;

        IMAGE_BASE_NAME = imageBaseName;
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        //all images is suffix with _(image number).
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        path = IMAGE_DIRECTORY + IMAGE_BASE_NAME + "_0" + IMAGE_TYPE;
        image = loadImage(path);
    }

    /* Loads an image from a file.
    Returns a BufferedImage object in success, exits on failure.
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

    //Load the next hangman image in the series
    public void nextImage (int imageNumber){

        loadNewImage(String.valueOf(imageNumber));
    }

    //Display the losing image.
    public void loseImage(){
        loadNewImage("lose");
    }

    //Display the winning image.
    public void winImage(){
        loadNewImage("win");
    }

    //Loads a new image in the hangman image series.
    private void loadNewImage(String suffix){

        path = IMAGE_DIRECTORY + IMAGE_BASE_NAME + "_" + suffix + IMAGE_TYPE;
        image = loadImage(path);
        repaint();
    }


    @Override
    protected void paintComponent (Graphics g){

        super.paintComponent(g);
        g.drawImage(image, 0, 0, preferredHeight, preferredWidth, null);
    }

}
