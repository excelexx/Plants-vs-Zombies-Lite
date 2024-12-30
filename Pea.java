
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pea extends Rectangle {

    int positionX;
    int positionY;
    int row;
    GamePanel game;
    public static final int DIAMETER = 30;
    BufferedImage projectileImage;
    BufferedImage projectileSplatImage;
    public static final int SPEED = 6;
    public static final int xOffset = 45;
    public static final int yOffset = 25;
    public boolean showPeaSplat = false;
    Peashooter peashooter;

    Pea(int col, int rw, GamePanel gme, Peashooter p) {
        super(Grid.colToX(col), Grid.rowToY(rw), DIAMETER, DIAMETER);
        game = gme;
        loadImage();
        positionX = Grid.colToX(col) + xOffset;
        positionY = Grid.rowToY(rw) + yOffset;
        peashooter = p;
        row = rw;
    }

    public void loadImage() {
        try {
            projectileImage = ImageIO.read(getClass().getResource("Images\\peat1.png"));
            projectileSplatImage = ImageIO.read(getClass().getResource("Images\\peat2.png"));
        } catch (IOException e) {

        }
    }

    public int getPosX() {
        return positionX+DIAMETER;
    }

    public int getRow() {
        return row;
    }

    public int getPosY() {
        return positionY;
    }

    public void move() {
        positionX += SPEED;
        if (positionX > GamePanel.GAME_WIDTH) {
            this.die();
        }
    }

    public void checkHit(){

    }

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

    public void die() {
        game.removePea(this);
    }
    public int getPeashooterX(){
        return peashooter.getXEat();
    }
}
