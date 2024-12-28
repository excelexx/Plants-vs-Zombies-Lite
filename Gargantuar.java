
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gargantuar extends Zombie{
    BufferedImage gargantuarImage;
    GamePanel game;

    public Gargantuar(int rw, GamePanel gme){
        super(rw,1000,gme);
        xOffsetCenter = 20;
        xOffsetFront = -40;
        game = gme;
        row = rw;
        loadImage();
        speed = 1;
    }

    public void loadImage() {
        try {
            gargantuarImage = ImageIO.read(getClass().getResource("/Images/gargantuarImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g){
        if (gargantuarImage != null) {
                g.drawImage(gargantuarImage, positionX, Grid.rowToY(row)-100, null);
           }
        }

}
