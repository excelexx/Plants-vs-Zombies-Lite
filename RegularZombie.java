
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RegularZombie extends Zombie {

    GamePanel game;
    BufferedImage zombieImage;
    int state = 1;
    int offsetY = 30;
    RegularZombie(int rw, GamePanel gme) {
        super(rw, 100, gme);
        game = gme;
        row = rw;
        loadImage();
        durability = 100;
        xOffsetCenter = 20;
        xOffsetFront = -40;
        speed = 1;
    }

    public void loadImage() {
        try {
            zombieImage = ImageIO.read(getClass().getResource("/Images/regularZombieImage.png"));
        } catch (IOException e) {

        }
    }


    public void draw(Graphics g) {
        if (zombieImage != null) {
            g.drawImage(zombieImage, positionX, Grid.rowToY(row)-offsetY, null);
        }
    }
}
