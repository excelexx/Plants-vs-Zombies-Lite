//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for the potatomine class

//imports
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//potato mine class
public class PotatoMine extends Plant {
    //variables
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
    int state =0;
    boolean showExplosion = false;
    int positionX;

    //constructor 
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

    //starts timer 
    public void startTimer(){
        potatoMineThread = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Thread.sleep(game.getMineTime());
                    arm();
                }
                catch(Exception e){
                    System.out.println("Error loading image files. Please check all files are saved properly.");
                }
            }
        });
        potatoMineThread.start();
    }

    //loads the images
    public void loadImage() {
        try {
            unarmedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\unarmedPotatoMineImage.png"));
            armedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\armedPotatoMineImage.png"));
            explodedPotatoMineImage = ImageIO.read(getClass().getResource("Images\\explodedPotatoMineImaget1.png"));
        } catch (IOException e) {

        }
    }

    //draws potatomine
    @Override
    public void draw(Graphics g) {
        if(showExplosion) {
            g.drawImage(explodedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        } else if(isArmed){
            g.drawImage(armedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        } 
        else {
            g.drawImage(unarmedPotatoMineImage, Grid.colToX(column), Grid.rowToY(row), null);
        }

    }

    //kills the potatomine
    @Override
    public void die() {
        isAlive = false;
    }

    //arms potatomine
    public void arm(){
        isArmed = true;
    }

    //returns if the mine is armed
    public boolean isArmed(){
        return isArmed;
    }

    //returns hitbox
    public int getPosX() {
        return positionX;
    }

    public int getState(){
        return state;
    }
    public void incrementState(){
        state++;
    }

    public void showExplosion(){
        showExplosion = true;
    }
}
