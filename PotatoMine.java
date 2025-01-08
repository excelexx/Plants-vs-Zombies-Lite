
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PotatoMine extends Plant {

    public final int POTATO_DURABILITY = 100;
    GamePanel game;
    BufferedImage unarmedPotatoMineImage;
    BufferedImage armedPotatoMineImage;
    BufferedImage explodedPotatoMineImage;
    boolean isAlive = true;
    int xOffset = 40;
    int yOffset = 40;
    int spriteCounter = 0;
    boolean isArmed;
    Thread potatoMineThread;
    int spriteToggle = 1;
    boolean showExplosion = false;
    int positionX;

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
        positionX = Grid.colToX(col) + xOffset;
    }

    public void startTimer(){
        potatoMineThread = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Thread.sleep(2500);
                    arm();
                }
                catch(Exception e){
        
                }
            }
        });
        potatoMineThread.start();
    }
    public void loadImage() {
        try {
            unarmedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\unarmedPotatoMineImage.png"));
            armedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\armedPotatoMineImage.png"));
            explodedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\explodedPotatoMineImaget1.png"));
        } catch (IOException e) {

        }
    }

    @Override
    public void draw(Graphics g) {
        if(!isArmed){
            g.drawImage(unarmedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        }
        else if(!showExplosion){
            g.drawImage(armedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        } else {
            g.drawImage(explodedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        }
    }

    @Override
    public void die() {
        isAlive = false;
    }
    @Override
    public String toString(){
        return durability +"";
    }
    public void arm(){
        isArmed = true;
    }

    public boolean isArmed(){
        return isArmed;
    }

    public int getPosX() {
        return positionX;
    }
}
