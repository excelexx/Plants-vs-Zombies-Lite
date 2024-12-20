
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Peashooter extends Plant {

    public final int PEA_DURABILITY = 100;
    GamePanel game;
    BufferedImage peaImage;
    public final int SHOOTER_DIAMETER = 90;
    Thread peaThread;
    boolean isZombie = true;
    boolean isAlive = true;

    Peashooter(int col, int rw, GamePanel gme) {
        super(col, rw, 100, gme);
        game = gme;
        column = col;
        row = rw;
        loadImage();
    }

    public void loadImage() {
        try {
            peaImage = ImageIO.read(getClass().getResource("/Images/peaImage.png"));
        } catch (IOException e) {

        }
    }

    public void move() {
        
        if(peaThread == null || !peaThread.isAlive()){
            peaThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        while(isZombie){
                            if(isAlive){
                                Thread.sleep(1200);
                                game.addPea(new Pea(column, row, game, Peashooter.this), row);
                            }
                        }
                    } catch (Exception e) {
    
                    }
                }
            });
            peaThread.start();
        }
        
    }

    public void draw(Graphics g) {
        if (peaImage != null) {
            g.drawImage(peaImage, Grid.colToX(column), Grid.rowToY(row), SHOOTER_DIAMETER, SHOOTER_DIAMETER, null);
        }
    }
    public void yesZombie(){
        isZombie = true;
    }
    public void noZombie(){
        isZombie = false;
    }
    
    public void die(){
        isAlive = false;
    }
}
