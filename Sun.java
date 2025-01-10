//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for sun class

//imports
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//sun class
public class Sun extends Rectangle {
    //declares variables
    int positionX;
    int positionY;
    int row;
    int column;
    GamePanel game;
    public static final int DIAMETER = 90;
    BufferedImage sunSprite1;
    BufferedImage sunSprite2;
    public static final int X_OFFSET = 45;
    public static final int Y_OFFSET = 45;
    public static final int SPEED = 1;
    boolean isGone = false;
    boolean isFalling;
    Thread sunThread;
    int state;
    int threshold;
    int mouseX;
    int mouseY;
    int spriteCounter = 0;
    int spriteToggle = 1;

    //constructor for when sun spawned by sunflower
    Sun(int x, int y, GamePanel gme) {
        super(x, y, DIAMETER, DIAMETER);
        game = gme;
        loadImage();
        positionX = x;
        positionY = y;
        isFalling = true;
        if(positionY<=500){
        threshold = (int)(500+300*Math.random());
        }
        else{
            threshold = (int)(Math.random()*150+450);
        }
        startTimer();
    }

    //second constructor for when sun is spawned randomly
    Sun(int x, int y, GamePanel gme, boolean isFalling){
        super(x,y,DIAMETER, DIAMETER);
        game = gme;
        loadImage();
        positionX = x-X_OFFSET;
        positionY = y-Y_OFFSET;
        isFalling = false;
        startTimer();
    }

    //starts the sun timer
    public void startTimer(){
        sunThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    die();
                } catch (Exception e) {
    
                }
            }
        });
        sunThread.start();
    }

    //loads images
    public void loadImage() {
        try {
            sunSprite1 = ImageIO.read(getClass().getResource("Images\\sunt1.png"));
            sunSprite2 = ImageIO.read(getClass().getResource("Images\\sunt2.png"));
        } catch (IOException e) {
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }

    //returns the x coordinate
    public int getPosX() {
        return positionX;
    }

    //returns the y coordinate
    public int getPosY() {
        return positionY;
    }

    //returns if the sun is disappeared
    public boolean getIsGone(){
        return isGone;
    }

    //makes sun fall
    public void move() {
        if(isFalling){
            if(positionY<=threshold){
                state++;
                if(state>=2){
                    positionY+=SPEED;
                    state = 0;
                }
            }
        }
    }

    //draws the sprites
    public void draw(Graphics g) {
        if (sunSprite1 != null && sunSprite2 != null && !isGone) {
            spriteCounter++;

            if(spriteCounter > 50) {
                if(spriteToggle == 1) {
                    spriteToggle = 2;
                } else {
                    spriteToggle = 1;
                }
                spriteCounter = 0;
            }

            if(spriteToggle == 1) {
                g.drawImage(sunSprite1, positionX, positionY, DIAMETER, DIAMETER, null);
            }
            if(spriteToggle == 2) {
                g.drawImage(sunSprite2, positionX, positionY, DIAMETER, DIAMETER, null);
            }
        }
    }

    //kills the sun
    public void die() {
        isGone = true;
    }

    //tracks when sun is collected
    public void mouseReleased(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();

        if(positionX<=mouseX && mouseX<=positionX+DIAMETER && positionY<=mouseY && mouseY<=positionY+DIAMETER){
            die();
            game.changeSun(25);
        }
    }

}
