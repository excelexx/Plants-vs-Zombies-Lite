//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for the Peashooter class that controls game logic with the peashooter

//imports
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Peashooter extends Plant {

    //declares all variables
    public static final int PEA_DURABILITY = 100;
    GamePanel game;
    BufferedImage peashooterSprite1;
    BufferedImage peashooterSprite2;
    BufferedImage peashooterSprite3;
    BufferedImage peashooterSprite4;
    //public final int SHOOTER_DIAMETER = 90;
    Thread peaThread;
    boolean isZombie = false;
    boolean isAlive = true;
    int xOffset = 40;
    int yOffset = 40;
    int spriteCounter = 0;
    int spriteToggle = 1;

    //peashooter constructor
    Peashooter(int col, int rw, GamePanel gme) {
        super(col, rw, PEA_DURABILITY, gme);
        game = gme;
        column = col;
        row = rw;
        loadImage();
        xOffsetEat = 10;
        durability = PEA_DURABILITY;
    }

    //loads image
    public void loadImage() {
        try {
            peashooterSprite1 = ImageIO.read(getClass().getResource("Images\\peashootert1.png"));
            peashooterSprite2 = ImageIO.read(getClass().getResource("Images\\peashootert2.png"));
            peashooterSprite3 = ImageIO.read(getClass().getResource("Images\\peashootert1.png"));
            peashooterSprite4 = ImageIO.read(getClass().getResource("Images\\peashootert3.png"));
        } catch (IOException e) {

        }
    }

    //makes pea shoot and move
    public void move() {
        if (isZombie && isAlive && (peaThread == null || !peaThread.isAlive())) {
            peaThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        game.addPea(new Pea(column, row, game, Peashooter.this), row);
                        peaThread.interrupt();
                    } catch (Exception e) {

                    }
                }
            });
            if(spriteToggle == 2) {
                //only shoot once in next shooting sprite 
                peaThread.start();
            }
            
        }

    }

    //draws sprites
    @Override
    public void draw(Graphics g) {
        if (peashooterSprite1 != null) {

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
                g.drawImage(peashooterSprite1, Grid.colToX(column), Grid.rowToY(row), null);
            }
            if(spriteToggle == 2) {
                g.drawImage(peashooterSprite2, Grid.colToX(column), Grid.rowToY(row), null);
            }
            if(spriteToggle == 3) {
                g.drawImage(peashooterSprite3, Grid.colToX(column), Grid.rowToY(row), null);
            }
            if(spriteToggle == 4) {
                g.drawImage(peashooterSprite4, Grid.colToX(column), Grid.rowToY(row), null);
            }
        }
    }

    //indicates if there is a zombie in the lane
    public void yesZombie() {
        isZombie = true;
    }

    //indicates if there is no zombie in the lane
    public void noZombie() {
        isZombie = false;
    }

    //kills the peashooter
    @Override
    public void die() {
        isAlive = false;
    }
}
