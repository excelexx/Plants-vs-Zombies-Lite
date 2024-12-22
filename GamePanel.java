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
public class GamePanel extends JLayeredPane implements Runnable, KeyListener, MouseMotionListener, MouseListener {

    //dimensions of window
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 810;

    //Declares all variables
    Iterator<Pea> peaIterator;
    public BufferedImage gameBackground;
    public static final int PEA_DAMAGE = 20;
    Iterator<Zombie> zombieIterator;
    Iterator<Peashooter> peashooterIterator;
    public Thread gameThread;
    public Image image;
    Thread soundtrackThread;
    public Graphics graphics;
    Pea tempPea;

    public static boolean isRunning = false;
    public static Menu menu = new Menu();
    public Inventory inventory;
    public static int difficulty;
    public static boolean isGameDone = false;
    public static final int REGULAR_ZOMBIE_DAMAGE = 2;
    public static ArrayList<ArrayList<ArrayList<Plant>>> plantList = new ArrayList<>();
    public static ArrayList<ArrayList<Peashooter>> peashooterList = new ArrayList<>();
    public static ArrayList<ArrayList<Pea>> peaList = new ArrayList<>();
    public static ArrayList<ArrayList<Zombie>> zombieList = new ArrayList<>();
    public static ArrayList<Sun> sunList = new ArrayList<>();
    public static int[] furthestZombies = new int[5];
    public static int sunCount = 50;
    Zombie secondTempZombie;
    int minimum;
    int tempInt;
    Peashooter tempPeashooter;
    public static boolean soundPlayed = false;

    public static int mouseX;
    public static int mouseY;
    Zombie tempZombie;

    //Constructor for gamepanel class
    public GamePanel() {

        loadImage();
        //creates both paddles and the ball
        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this); //starts listening for keyboard input
        this.addMouseListener(this);
        inventory = new Inventory(this);
        //adds mouselistener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!isRunning) {
                    Menu.mouseReleased(e);
                    inventory.mouseReleased(e);
                }
            }

            public void mouseReleased(MouseEvent e) {

            }
        });
        //adds mouse motion listener
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (!isRunning) {
                    Menu.mouseMoved(e);
                    inventory.mouseMoved(e);
                }
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        //sets window size
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        //runs a new thread to allow classes to run simultaneously
        gameThread = new Thread(this);
        gameThread.start();

        //runs a new thread to run a background soundtrack throughout the run of the program
        //done with help of this stack overflow post https://stackoverflow.com/questions/17758411/java-creating-a-new-thread
        soundtrackThread = new Thread(new Runnable() {
            public void run() {
                SoundTrack.setUp();
            }
        });
        soundtrackThread.start();

        for (int i = 0; i < 5; i++) {
            peaList.add(new ArrayList<Pea>());
            zombieList.add(new ArrayList<Zombie>());
            peashooterList.add(new ArrayList<Peashooter>());
        }
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
            for (int i = 0; i < 5; i++) {
                for (Pea pea : peaList.get(i)) {
                    pea.draw(g);
                }
                for (Zombie zombie : zombieList.get(i)) {
                    zombie.draw(g);
                }
                for (Peashooter peashooter : peashooterList.get(i)) {
                    peashooter.draw(g);
                }
            }
            inventory.draw(g);
        } else {
            Menu.draw(g);
        }
    }

    //call the move methods in other classes to update positions
    public void move() {
        if (isRunning) {
            for (int i = 0; i < 5; i++) {
                for (Pea pea : peaList.get(i)) {
                    pea.move();
                }
                for (Zombie zombie : zombieList.get(i)) {
                    zombie.move();
                }
                for (Peashooter peashooter : peashooterList.get(i)) {
                    peashooter.move();
                }
            }
        }
    }

    //handles all collision detection and responds accordingly
    public void checkCollision() {

        for (int i = 0; i < 5; i++) {
            if (zombieList.get(i).isEmpty()) {
                furthestZombies[i] = -1;
                continue;
            }
            minimum = 0;
            for (int j = 0; j < zombieList.get(i).size(); j++) {
                if (zombieList.get(i).get(j).getXEat() < zombieList.get(i).get(minimum).getXEat()) {
                    minimum = j;
                }
            }
            furthestZombies[i] = minimum;
        }

        for (int i = 0; i < 5; i++) {
            if (furthestZombies[i] != -1) {
                if (zombieList.get(i).get(furthestZombies[i]).getXEat() <= 0) {
                    System.out.println("You lost the game");
                }

                tempZombie = zombieList.get(i).get(furthestZombies[i]);
                peaIterator = peaList.get(i).iterator();
                while (peaIterator.hasNext()) {
                    tempPea = peaIterator.next();
                    if (tempPea.getPosX() >= tempZombie.getXEat()) {
                        peaIterator.remove();
                        tempZombie.peaDamage();
                        Sound.playSingleSound("Sounds\\pea shooter sound effects 1# - Made with Clipchamp.wav", 0);
                    }
                }

                if (tempZombie.getDurability() <= 0) {
                    zombieIterator = zombieList.get(i).iterator();
                    while (zombieIterator.hasNext()) {
                        secondTempZombie = zombieIterator.next();
                        if (secondTempZombie == tempZombie) {
                            zombieIterator.remove();
                            break;
                        }
                    }

                    if (zombieList.get(i).isEmpty()) {
                        furthestZombies[i] = -1;
                    } else {
                        furthestZombies[i] = 0;
                    }
                }

                peashooterIterator = peashooterList.get(i).iterator();
                while (peashooterIterator.hasNext()) {
                    tempPeashooter = peashooterIterator.next();
                    if (tempPeashooter.getDurability() <= 0) {
                        peashooterIterator.remove();
                        tempPeashooter.die();
                    } else {
                        zombieIterator = zombieList.get(i).iterator();
                        while (zombieIterator.hasNext()) {
                            tempZombie = zombieIterator.next();
                            if (tempZombie.getXEating() <= tempPeashooter.getXEat() && tempZombie.getXEating() >= tempPeashooter.getXEat() - 30) {
                                tempPeashooter.regularEatPlant();
                                tempZombie.stop();
                                
                                //eating
                                if (!soundPlayed) {
                                    Sound.playEatingSounds();
                                    soundPlayed = true; // Set the flag to true after the sound is played
                                }
                                
                            } else {
                                tempZombie.go();

                                //stop eating
                            }
                        }
                    }
                }
                zombieIterator = zombieList.get(i).iterator();
                while (zombieIterator.hasNext()) {
                    tempZombie = zombieIterator.next();
                    if (peashooterList.get(i).isEmpty()) {
                        tempZombie.go();
                        continue;
                    }
                    while (peashooterIterator.hasNext()) {
                        tempPeashooter = peashooterIterator.next();
                        if (!(tempZombie.getXEating() <= tempPeashooter.getXEat() && tempZombie.getXEating() >= tempPeashooter.getXEat() - 30)) {
                            tempZombie.go();
                        }
                    }
                }
            }

            peashooterIterator = peashooterList.get(i).iterator();
            while (peashooterIterator.hasNext()) {
                Peashooter peashooter = peashooterIterator.next();
                if (furthestZombies[i] == -1) {
                    peashooter.noZombie();
                } else {
                    peashooter.yesZombie();
                }
            }

        }
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
                Sound.playZombieGroans();
                repaint();
                delta--;
            }
        }
        System.exit(0);
    }

    //checks when a key is pressed and sends to other classes for processing
    public void keyPressed(KeyEvent e) {

        if (e.getKeyChar() == ' ' && !isRunning) {

        }
        if (e.getKeyChar() == ' ') {
            zombieList.get(Grid.yToRow(mouseY) - 1).add(new RegularZombie(Grid.yToRow(mouseY), this));
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

        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        if (isRunning) {
            mouseX = e.getX();
            mouseY = e.getY();
            peashooterList.get(Grid.yToRow(mouseY) - 1).add(new Peashooter(Grid.xToCol(mouseX), Grid.yToRow(mouseY), this));
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void removePlant(Plant p) {
        plantList.remove(p);
    }

    public void removePea(Pea p) {
    }

    public void plantPeashooter(int x, int y){
        peashooterList.get(Grid.yToRow(y) - 1).add(new Peashooter(Grid.xToCol(x), Grid.yToRow(y), this));
    }
    public void addPea(Pea p, int rw) {
        peaList.get(rw - 1).add(p);
    }

    public void removeZombie(Zombie z) {
        zombieList.remove(z);
    }

    public void mousePressed(MouseEvent e) {
        Sound.playSingleSound("Sounds\\Select Click V.2 - Sound Effect (HD).wav - Made with Clipchamp.wav", 0);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
