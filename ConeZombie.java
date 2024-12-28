
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ConeZombie extends RegularZombie{
    int firstDurability;
    BufferedImage coneZombieImage;

    ConeZombie(int rw, GamePanel gme){
        super(rw,gme);
        offsetY = 55;
        durability = 640;
        xOffsetCenter = 20;
        xOffsetFront = -40;
    }

    public void loadImage() {
        try {
            coneZombieImage = ImageIO.read(getClass().getResource("/Images/coneZombieImage.png"));
            zombieImage = ImageIO.read(getClass().getResource("/Images/regularZombieImage.png"));
        } catch (IOException e) {

        }
    }

    public void draw(Graphics g){
        if (zombieImage != null && coneZombieImage != null) {
            if(durability<270){
                g.drawImage(zombieImage, positionX, Grid.rowToY(row)-offsetY, null);
            }
           else{
                g.drawImage(coneZombieImage, positionX, Grid.rowToY(row)-offsetY, null);
           }
        }

    }

}
