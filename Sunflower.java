//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for sunflower class

//imports
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sunflower extends Plant {
    //declares variables
    public static final int SUNFLOWER_DURABILITY = 100;
    GamePanel game;
    BufferedImage sunflowerSprite1;
    BufferedImage sunflowerSprite2;
    BufferedImage sunflowerSprite3;
    BufferedImage sunflowerSprite4;
    Thread sunThread;
    boolean isAlive = true;
    int xOffset = 40;
    int yOffset = 40;
    int spriteCounter = 0;
    int spriteToggle = 1;

    //constructor
    Sunflower(int col, int rw, GamePanel gme) {
        super(col, rw, SUNFLOWER_DURABILITY, gme);
        game = gme;
        column = col;
        row = rw;
        xOffsetEat = 20;
        loadImage();
    }

    //loads images
    public void loadImage() {
        try {
            sunflowerSprite1 = ImageIO.read(getClass().getResource("Images\\sunflowert1.png"));
            sunflowerSprite2 = ImageIO.read(getClass().getResource("Images\\sunflowert2.png"));
            sunflowerSprite3 = ImageIO.read(getClass().getResource("Images\\sunflowert1.png"));
            sunflowerSprite4 = ImageIO.read(getClass().getResource("Images\\sunflowert3.png"));
        } catch (IOException e) {
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }

    //moves the sunflower
    @Override
    public void move() {
        if (isAlive && (sunThread == null || !sunThread.isAlive())) {
            sunThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(15000);
                        if(game.isRunning){
                            game.addSun(new Sun(positionX+xOffset, positionY+yOffset, game, false));
                        }
                        sunThread.interrupt();
                    } catch (Exception e) {

                    }
                }
            });
            sunThread.start();
        }

    }

    //animates the sunflower
    @Override
    public void draw(Graphics g) {
        if (sunflowerSprite1 != null) {

            spriteCounter++;

            if(spriteCounter > 0 && spriteCounter <= 30) {
                spriteToggle = 1;
            } else if(spriteCounter > 30 && spriteCounter < 60) {
                spriteToggle = 2;
            } else if(spriteCounter > 60 && spriteCounter < 90) {
                spriteToggle = 3;
            } else if(spriteCounter > 90 && spriteCounter < 120) {
                spriteToggle = 4;
            } else if(spriteCounter > 120) {
                spriteCounter = 0;
            }

            if(spriteToggle == 1) {
                g.drawImage(sunflowerSprite1, Grid.colToX(column), Grid.rowToY(row), null);
            }
            if(spriteToggle == 2) {
                g.drawImage(sunflowerSprite2, Grid.colToX(column), Grid.rowToY(row), null);
            }
            if(spriteToggle == 3) {
                g.drawImage(sunflowerSprite3, Grid.colToX(column), Grid.rowToY(row), null);
            }
            if(spriteToggle == 4) {
                g.drawImage(sunflowerSprite4, Grid.colToX(column), Grid.rowToY(row), null);
            }
        }
    }

    //kills the sunflower
    @Override
    public void die() {
        isAlive = false;
    }
}
