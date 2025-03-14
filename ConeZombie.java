//Alexander Zhang and Stanley Zhou
//2025-01-17
//Code for the gameframe class that defines information about the window
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ConeZombie extends RegularZombie{
    //declaring all variables
    BufferedImage coneZombieWalkingSprite1;
    BufferedImage coneZombieWalkingSprite2;
    BufferedImage coneZombieEatingSprite1;
    BufferedImage coneZombieEatingSprite2;
    BufferedImage coneZombieDamagedWalkingSprite1;
    BufferedImage coneZombieDamagedWalkingSprite2;
    BufferedImage coneZombieDamagedEatingSprite1;
    BufferedImage coneZombieDamagedEatingSprite2;
    BufferedImage zombieWalkingSprite1;
    BufferedImage zombieWalkingSprite2;
    BufferedImage zombieEatingSprite1;
    BufferedImage zombieEatingSprite2;
    int spriteCounter = 0;
    int spriteToggle = 1;

    //Constructor method
    ConeZombie(int rw, GamePanel gme){
        super(rw,gme);
        offsetY = 40;
        loadImage();
        durability = 640;
        xOffsetCenter = 20;
        xOffsetFront = -40;
        speed = 1;
    }

    //Loading the image files
    public void loadImage() {
        try {
            coneZombieWalkingSprite1 = ImageIO.read(getClass().getResource("Images\\coneZombieWalkingt1.png"));
            coneZombieWalkingSprite2 = ImageIO.read(getClass().getResource("Images\\coneZombieWalkingt2.png"));
            coneZombieEatingSprite1 = ImageIO.read(getClass().getResource("Images\\coneZombieEatingt1.png"));
            coneZombieEatingSprite2 = ImageIO.read(getClass().getResource("Images\\coneZombieEatingt2.png"));
            coneZombieDamagedWalkingSprite1 = ImageIO.read(getClass().getResource("Images\\coneZombieDamagedWalkingt1.png"));
            coneZombieDamagedWalkingSprite2 = ImageIO.read(getClass().getResource("Images\\coneZombieDamagedWalkingt2.png"));
            coneZombieDamagedEatingSprite1 = ImageIO.read(getClass().getResource("Images\\coneZombieDamagedEatingt1.png"));
            coneZombieDamagedEatingSprite2 = ImageIO.read(getClass().getResource("Images\\coneZombieDamagedEatingt2.png"));
            zombieWalkingSprite1 = ImageIO.read(getClass().getResource("Images\\regularZombieWalkingt1.1.png"));
            zombieWalkingSprite2 = ImageIO.read(getClass().getResource("Images\\regularZombieWalkingt2.1.png"));
            zombieEatingSprite1 = ImageIO.read(getClass().getResource("Images\\regularZombieEatingt1.1.png"));
            zombieEatingSprite2 = ImageIO.read(getClass().getResource("Images\\regularZombieEatingt2.1.png"));
        } catch (IOException e) {
            System.out.println("Image files not loaded. Please check they were saved correctly.");
        }
    }

    //drawing the zombies
    public void draw(Graphics g){
        if (zombieWalkingSprite1 != null) {
            spriteCounter++;
            if(isStopped) {
                //display eating
                
                if(spriteCounter > 0 && spriteCounter <= 50) {
                    spriteToggle = 3;
                } else if(spriteCounter > 50 && spriteCounter <= 100) {
                    spriteToggle = 4;
                } else if(spriteCounter > 100) {
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

            if(durability >= 480) {
                //new cone do nothing
            } else if(durability >= 290) {
                //damaged cone
                spriteToggle += 4;
            } else if(durability > 0) {
                //completely gone cone
                spriteToggle += 8;
            } else {
                //dead zombie
            }

            //drawing the animations for the zombie
            if(spriteToggle == 1) {
                g.drawImage(coneZombieWalkingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 2) {
                g.drawImage(coneZombieWalkingSprite2, positionX, positionY, null);
            }
            if(spriteToggle == 3) {
                g.drawImage(coneZombieEatingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 4) {
                g.drawImage(coneZombieEatingSprite2, positionX, positionY, null);
            }
            if(spriteToggle == 5) {
                g.drawImage(coneZombieDamagedWalkingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 6) {
                g.drawImage(coneZombieDamagedWalkingSprite2, positionX, positionY, null);
            }
            if(spriteToggle == 7) {
                g.drawImage(coneZombieDamagedEatingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 8) {
                g.drawImage(coneZombieDamagedEatingSprite2, positionX, positionY, null);
            }
            if(spriteToggle == 9) {
                g.drawImage(zombieWalkingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 10) {
                g.drawImage(zombieWalkingSprite2, positionX, positionY, null);
            }
            if(spriteToggle == 11) {
                g.drawImage(zombieEatingSprite1, positionX, positionY, null);
            }
            if(spriteToggle == 12) {
                g.drawImage(zombieEatingSprite2, positionX, positionY, null);
            }
        }
    }

}
