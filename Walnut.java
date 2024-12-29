
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Walnut extends Plant {

    public final int WALNUT_DURABILITY = 1500;
    GamePanel game;
    BufferedImage walnutImage;
    boolean isAlive = true;
    int xOffset = 40;
    int yOffset = 40;
    int spriteCounter = 0;
    int spriteToggle = 1;

    Walnut(int col, int rw, GamePanel gme) {
        super(col, rw, 1500, gme);
        game = gme;
        column = col;
        row = rw;
        durability = WALNUT_DURABILITY;
        xOffsetEat = 20;
        loadImage();
    }

    public void loadImage() {
        try {
            walnutImage = ImageIO.read(getClass().getResource("Images\\walnutt2.png"));
        } catch (IOException e) {

        }
    }

    public void draw(Graphics g) {
        g.drawImage(walnutImage, Grid.colToX(column), Grid.rowToY(row), null);
    }

    public void die() {
        isAlive = false;
    }
    public String toString(){
        return durability +"";
    }
}
