
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sunflower extends Plant {

    public final int PEA_DURABILITY = 100;
    GamePanel game;
    BufferedImage peaImage;
    Thread sunThread;
    boolean isAlive = true;
    int xOffset = 40;
    int yOffset = 40;

    Sunflower(int col, int rw, GamePanel gme) {
        super(col, rw, 100, gme);
        game = gme;
        column = col;
        row = rw;
        loadImage();
    }

    public void loadImage() {
        try {
            peaImage = ImageIO.read(getClass().getResource("/Images/sunflowerImage.png"));
        } catch (IOException e) {

        }
    }

    public void move() {
        if (isAlive && (sunThread == null || !sunThread.isAlive())) {
            sunThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(15000);
                        game.addSun(new Sun(positionX+xOffset, positionY+yOffset, game, false));
                        sunThread.interrupt();
                    } catch (Exception e) {

                    }
                }
            });
            sunThread.start();
        }

    }

    public void draw(Graphics g) {
        if (peaImage != null) {
            g.drawImage(peaImage, Grid.colToX(column), Grid.rowToY(row), null);
        }
    }

    public void die() {
        isAlive = false;
    }
}
