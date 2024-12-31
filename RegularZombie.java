
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RegularZombie extends Zombie {

    GamePanel game;
    BufferedImage zombieWalkingSprite1;
    BufferedImage zombieWalkingSprite2;
    BufferedImage zombieEatingSprite1;
    BufferedImage zombieEatingSprite2;
    BufferedImage zombieDeadSprite;
    int spriteCounter = 0;
    int spriteToggle = 1;
    int state = 1;
    int offsetY = 30;
    RegularZombie(int rw, GamePanel gme) {
        super(rw, 100, gme);
        //System.out.println("spawned");
        game = gme;
        row = rw;
        loadImage();
        durability = 100;
        xOffsetCenter = 20;
        xOffsetFront = -40;
        speed = 1;
    }

    public void loadImage() {
        try {
            zombieWalkingSprite1 = ImageIO.read(getClass().getResource("Images\\regularZombieWalkingt1.1.png"));
            zombieWalkingSprite2 = ImageIO.read(getClass().getResource("Images\\regularZombieWalkingt2.1.png"));
            zombieEatingSprite1 = ImageIO.read(getClass().getResource("Images\\regularZombieEatingt1.1.png"));
            zombieEatingSprite2 = ImageIO.read(getClass().getResource("Images\\regularZombieEatingt2.1.png"));
            zombieDeadSprite = ImageIO.read(getClass().getResource("Images\\regularZombieDeadt1.1.png"));
        } catch (IOException e) {

        }
    }

    public void draw(Graphics g) {
        if (zombieWalkingSprite1 != null) {

            spriteCounter++;
            if(durability <= 0) {
                //dead
                spriteToggle = 5;
            } else {
                if(isStopped) {
                    //display eating
                    if(spriteCounter > 0 && spriteCounter <= 50) {
                        spriteToggle = 3;
                    } else if(spriteCounter > 50 && spriteCounter <= 100) {
                        spriteToggle = 4;
                    } else if(spriteCounter > 100){
                        spriteCounter = 0;
                    }
                } else {
                    //display walking
                    
                    if(spriteCounter > 0 && spriteCounter <= 50) {
                        spriteToggle = 1;
                    } else if(spriteCounter > 50 && spriteCounter <= 100) {
                        spriteToggle = 2;
                    } else if(spriteCounter > 100) {
                        spriteCounter = 0;
                    }
                }
            }

            if(spriteToggle == 1) {
                g.drawImage(zombieWalkingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 2) {
                g.drawImage(zombieWalkingSprite2, positionX, positionY, null);
            }
            if(spriteToggle == 3) {
                g.drawImage(zombieEatingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 4) { 
                g.drawImage(zombieEatingSprite2, positionX, positionY, null);
            }
            if(spriteToggle == 5) {
                g.drawImage(zombieDeadSprite, positionX, positionY, null);
            }
        }
    }
}
