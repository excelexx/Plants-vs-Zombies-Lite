
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PotatoMine extends Plant {

    public final int POTATO_DURABILITY = 100;
    GamePanel game;
    BufferedImage unarmedPotatoMineImage;
    BufferedImage armedPotatoMineImage;
    boolean isAlive = true;
    int xOffset = 40;
    int yOffset = 40;
    int spriteCounter = 0;
    boolean isArmed;
    boolean isExploded;
    Thread walnutThread;
    int spriteToggle = 1;

    PotatoMine(int col, int rw, GamePanel gme) {
        super(col, rw, 100, gme);
        game = gme;
        column = col;
        row = rw;
        durability = POTATO_DURABILITY;
        xOffsetEat = 20;
        loadImage();
        isArmed = false;
        startTimer();

    }

    public void startTimer(){
        walnutThread = new Thread(new Runnable(){
            public void run(){
                try{
                    Thread.sleep(1000);
                    arm();
                }
                catch(Exception e){
        
                }
            }
        });
        walnutThread.start();
    }
    public void loadImage() {
        try {
            unarmedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\unarmedPotatoMineImage.png"));
            armedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\armedPotatoMineImage.png"));
        } catch (IOException e) {

        }
    }

    public void draw(Graphics g) {
        if(isArmed){
            g.drawImage(armedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        }
        else{
            g.drawImage(unarmedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        }
    }

    public void die() {
        isAlive = false;
    }
    public String toString(){
        return durability +"";
    }
    public void arm(){
        isArmed = true;
    }
    public void explode(){
        isExploded = true;
        die();
    }
    public boolean isArmed(){
        return isArmed;
    }
}
