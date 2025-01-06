
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gargantuar extends Zombie{
    BufferedImage gargantuarSprite1;
    BufferedImage gargantuarSprite2;
    int spriteCounter = 0;
    int spriteToggle = 1;
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
            gargantuarSprite1 = ImageIO.read(getClass().getResource("Images\\gargantuarWalkingt1.png"));
            gargantuarSprite2 = ImageIO.read(getClass().getResource("Images\\gargantuarWalkingt2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g){
        if (gargantuarSprite1 != null) {

            spriteCounter++;
            if(spriteCounter <= 40) {
                spriteToggle = 1;
            } else if (spriteCounter <= 80) {
                spriteToggle = 2;
            } else if (spriteCounter > 80) {
                spriteCounter = 0;
            }

            if(spriteToggle == 1) {
                g.drawImage(gargantuarSprite1, positionX, Grid.rowToY(row)-100, null);
            } else {
                g.drawImage(gargantuarSprite2, positionX, Grid.rowToY(row)-100, null);
            }
        }
    }

}
