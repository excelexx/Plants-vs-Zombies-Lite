
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sun extends Rectangle {

    int positionX;
    int positionY;
    int row;
    GamePanel game;
    public static final int DIAMETER = 30;
    BufferedImage sunImage;
    public static final int xOffset = 45;
    public static final int yOffset = 25;
    public static final int SPEED = 1;
    boolean isGone = false;

    Sun(int col, int rw, GamePanel gme) {
        super(Grid.colToX(col), Grid.rowToY(rw), DIAMETER, DIAMETER);
        game = gme;
        loadImage();
        positionX = Grid.colToX(col) + xOffset;
        positionY = Grid.rowToY(rw) + yOffset;
        row = rw;
    }

    public void loadImage() {
        try {
            sunImage = ImageIO.read(getClass().getResource("/Images/sunImage.png"));
        } catch (IOException e) {

        }
    }

    public int getPosX() {
        return positionX;
    }

    public int getPosY() {
        return positionY;
    }

    public void getHitbox(){

    }
    public void move() {
        positionX -= SPEED;
        if (positionX > GamePanel.GAME_WIDTH) {
            this.die();
        }
    }

    public void draw(Graphics g) {
        if (sunImage != null && !isGone) {
            g.drawImage(sunImage, positionX, positionY, DIAMETER, DIAMETER, null);
        }
    }

    public void die() {
        
    }

}
