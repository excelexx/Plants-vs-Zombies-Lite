//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for walnut class

//import
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Walnut extends Plant {
    //declares variables
    public final int WALNUT_DURABILITY = 1500;
    GamePanel game;
    BufferedImage walnutSprite1;
    BufferedImage walnutSprite2;
    BufferedImage walnutSprite3;
    boolean isAlive = true;
    int xOffset = 40;
    int yOffset = 40;
    int spriteCounter = 0;
    int spriteToggle = 1;

    //constructor code
    Walnut(int col, int rw, GamePanel gme) {
        super(col, rw, 1500, gme);
        game = gme;
        column = col;
        row = rw;
        durability = WALNUT_DURABILITY;
        xOffsetEat = 20;
        loadImage();
    }

    //loads images
    public void loadImage() {
        try {
            walnutSprite1 = ImageIO.read(getClass().getResource("Images\\walnutt1.png"));
            walnutSprite2 = ImageIO.read(getClass().getResource("Images\\walnutDamagedt1.png"));
            walnutSprite3 = ImageIO.read(getClass().getResource("Images\\walnutDamagedt2.png"));
        } catch (IOException e) {
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }

    //animates walnut
    public void draw(Graphics g) {
        if(durability >= 1000) {
            //not damaged
            g.drawImage(walnutSprite1, Grid.colToX(column), Grid.rowToY(row), null);
        } else if(durability >= 500) {
            //slightly damaged
            g.drawImage(walnutSprite2, Grid.colToX(column), Grid.rowToY(row), null);
        } else {
            //very damaged
            g.drawImage(walnutSprite3, Grid.colToX(column), Grid.rowToY(row), null);
        }
    }

    //kills walnut
    @Override
    public void die() {
        isAlive = false;
    }
}
