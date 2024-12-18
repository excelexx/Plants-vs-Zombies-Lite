//Alexander Zhang and Stanley Zhou
//2024-12-17
//Code for the GamePanel class that controls all the game logic and collisions

//imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

//GamePanel class
public class GamePanel extends JLayeredPane implements Runnable, KeyListener, MouseMotionListener {

    //dimensions of window
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 810;

    //Declares all variables
    public BufferedImage gameBackground;
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public static boolean isRunning = false;
    public static Menu menu = new Menu();
    public static int difficulty;
    public static boolean isGameDone = false;
    public static ArrayList<Plant> plantList = new ArrayList<>();

    //Constructor for gamepanel class
    public GamePanel() {
        loadImage();
        //creates both paddles and the ball
        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this); //starts listening for keyboard input
        //adds mouselistener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(!isRunning){
                    Menu.mouseMoved(e);
                    Menu.mouseReleased(e);
                }
            }
        });
        //adds mouse motion listener
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!isRunning){
                    Menu.mouseMoved(e);
                }
            }
        });

        //sets window size
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        //runs a new thread to allow classes to run simultaneously
        gameThread = new Thread(this);
        gameThread.start();

        //runs a new thread to run a background soundtrack throughout the run of the program
        //done with help of this stack overflow post https://stackoverflow.com/questions/17758411/java-creating-a-new-thread
        Thread soundtrackThread = new Thread(new Runnable() {
            public void run()
            {
                SoundTrack.setUp();
            }});  
            soundtrackThread.start();
        
    }

    //loads all images
    public void loadImage() {
        try {
            gameBackground = ImageIO.read(getClass().getResource("/Images/gameGrid.png"));
        } catch (IOException e) {
            System.out.println("Error loading image. Please check the files were saved correctly.");
        }
    }

    //draws things on screen
    public void paint(Graphics g) {
        //uses double buffering to draw
        image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
        graphics = image.getGraphics();
        draw(graphics);//update the positions of everything on the screen 
        g.drawImage(image, 0, 0, this); //move the image on the screen

    }

    //calls the draw methods in each class to update positions as things move
    public void draw(Graphics g) {
        if (isRunning) {
            if (gameBackground != null) {
                g.drawImage(gameBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
            }
        } else {
            Menu.draw(g);
        }

    }

    //call the move methods in other classes to update positions
    public void move() {
        if (isRunning) {

        }
    }

    //handles all collision detection and responds accordingly
    public void checkCollision() {

    }

    //makes game run constantly, 
    public void run() {
        //ensure that code is run at 60 ticks per second
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long now;

        while (!isGameDone) { //runs game until isGameDone is true
            now = System.nanoTime();
            delta = delta + (now - lastTime) / ns;
            lastTime = now;

            //only move objects around and update screen if enough time has passed
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
        System.exit(0);
    }

    //checks when a key is pressed and sends to other classes for processing
    public void keyPressed(KeyEvent e) {

        if (e.getKeyChar() == ' ' && !isRunning) {
            isRunning = true;
        }
    }

    //checks when a key is released and sends to other classes for processing
    public void keyReleased(KeyEvent e) {

        menu.keyReleased(e);
    }

    //overrides KeyListener interface
    public void keyTyped(KeyEvent e) {

    }

    //overrides MouseMotionListener interface
    public void mouseDragged(MouseEvent e) {

    }

    //overrides MouseMotionListener interface
    public void mouseMoved(MouseEvent e) {

    }
    public void removePlant(Plant p){
        plantList.remove(p);
    }

}
