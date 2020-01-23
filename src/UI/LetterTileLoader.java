package UI;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class LetterTileLoader extends JLabel {

    private final char IMAGE_LETTER;

    private int preferredWidth;

    private int preferredHeight;

    private final String IMAGE_DIRECTORY;

    private final String IMAGE_TYPE;

    private String path;

    private BufferedImage image;

    private MouseListener tileListener;

    public LetterTileLoader() {
        this('a', "Resources/",".jpeg");
    }



    public LetterTileLoader(char imageLetter, String imageDirectory, String imageType) {

        preferredHeight = 50;
        preferredWidth = 65;

        IMAGE_LETTER = imageLetter;
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        path = IMAGE_DIRECTORY + IMAGE_LETTER + IMAGE_TYPE;
        image = loadImage(path);
    }

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

    public char guess(){

        loadNewImage("guessed");
        removeTileListener();
        return IMAGE_LETTER;
    }

    private void loadNewImage(String suffix){

        path = IMAGE_DIRECTORY + suffix + IMAGE_TYPE;
        image = loadImage(path);
        repaint();
    }

    public void addTileListener(MouseListener l){

        tileListener = l;
        addMouseListener(tileListener);

    }

    public void removeTileListener(){

        removeMouseListener(tileListener);
    }

    @Override
    protected void paintComponent (Graphics g){

        super.paintComponent(g);
        g.drawImage(image, 0, 0, preferredHeight, preferredWidth, null);
    }


}
