//Alexander Zhang and Stanley Zhou
//2025-01-17
//Code for the Pea class that controls and draws the peas
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//code for pea class
public class Pea extends Rectangle {
    //declares all variables
    int positionX;
    int positionY;
    int row;
    GamePanel game;
    public static final int DIAMETER = 30;
    BufferedImage projectileImage;
    BufferedImage projectileSplatImage;
    public static final int SPEED = 6;
    public static final int X_OFFSET = 45;
    public static final int Y_OFFSET = 25;
    public boolean showPeaSplat = false;
    Peashooter peashooter;

    //constructor
    Pea(int col, int rw, GamePanel gme, Peashooter p) {
        super(Grid.colToX(col), Grid.rowToY(rw), DIAMETER, DIAMETER);
        game = gme;
        loadImage();
        positionX = Grid.colToX(col) + X_OFFSET;
        positionY = Grid.rowToY(rw) + Y_OFFSET;
        peashooter = p;
        row = rw;
    }

    //loads images
    public void loadImage() {
        try {
            projectileImage = ImageIO.read(getClass().getResource("Images\\peat1.png"));
            projectileSplatImage = ImageIO.read(getClass().getResource("Images\\peat2.png"));
        } catch (IOException e) {
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }

    //returns x coordinate of pea
    public int getPosX() {
        return positionX+DIAMETER;
    }

    //returns row of pea
    public int getRow() {
        return row;
    }

    //returns y coordinate of pea
    public int getPosY() {
        return positionY;
    }

    //makes pea move
    public void move() {
        positionX += SPEED;
        if (positionX > GamePanel.GAME_WIDTH) {
            this.die();
        }
    }

    //draws pea
    public void draw(Graphics g) {
        if (projectileImage != null) {
            if(showPeaSplat) {
                //about to hit zombie, show pea splat
                g.drawImage(projectileSplatImage, positionX, positionY, DIAMETER, DIAMETER, null);
            } else {
                //still flying, show normal projectile
                g.drawImage(projectileImage, positionX, positionY, DIAMETER, DIAMETER, null);
            }
            
        }
    }

    //kills the pea
    public void die() {
        game.removePea(this);
    }
    //returns peashooter hitbox
    public int getPeashooterX(){
        return peashooter.getXEat();
    }
}
