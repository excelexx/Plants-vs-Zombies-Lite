//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for the GamePanel class that controls all the game logic and collisions`

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
    public BufferedImage endScreenWinDefault;
    public BufferedImage endScreenLoseDefault;
    public BufferedImage endScreenLoseHover;
    boolean isLost = false;
    public BufferedImage endScreenWinHover;
    public static final int PEA_DAMAGE = 20;
    Iterator<Zombie> zombieIterator;
    Iterator<Peashooter> peashooterIterator;
    Iterator<Sun> sunIterator;
    public Thread gameThread;
    public Image image;
    public boolean gameEnded = false;
    public static final int GARGANTUAR_DAMAGE = 1000;
    public Graphics graphics;
    int mouseClickedY;
    int mouseClickedX;
    Pea tempPea;
    public static boolean isRunning = false;
    boolean exists;
    public static Menu menu = new Menu();
    public Inventory inventory;
    public static int difficulty = 1;
    public static boolean isGameDone = false;
    public static final int REGULAR_ZOMBIE_DAMAGE = 1;
    public static final int POTATO_MINE_DAMAGE = 800;
    public static ArrayList<ArrayList<ArrayList<Plant>>> plantList = new ArrayList<>();
    public static ArrayList<ArrayList<Peashooter>> peashooterList = new ArrayList<>();
    public static ArrayList<ArrayList<Sunflower>> sunflowerList = new ArrayList<>();
    public static ArrayList<ArrayList<Pea>> peaList = new ArrayList<>();
    public static ArrayList<ArrayList<Zombie>> zombieList = new ArrayList<>();
    public static ArrayList<Sun> sunList = new ArrayList<>();
    public static ArrayList<ArrayList<Peashooter>> peashooterListRemove = new ArrayList<>();
    public static ArrayList<ArrayList<Sunflower>> sunflowerListRemove = new ArrayList<>();
    public static ArrayList<ArrayList<Pea>> peaListRemove = new ArrayList<>();
    public static ArrayList<ArrayList<Zombie>> zombieListRemove = new ArrayList<>();
    public static ArrayList<Sun> sunListRemove = new ArrayList<>();
    public static ArrayList<ArrayList<Peashooter>> peashooterListAdd = new ArrayList<>();
    public static ArrayList<ArrayList<Sunflower>> sunflowerListAdd = new ArrayList<>();
    public static ArrayList<ArrayList<Pea>> peaListAdd = new ArrayList<>();
    public static ArrayList<ArrayList<Zombie>> zombieListAdd = new ArrayList<>();
    public static ArrayList<Sun> sunListAdd = new ArrayList<>();
    public static ArrayList<ArrayList<Walnut>> walnutList = new ArrayList<>();
    public static ArrayList<ArrayList<Walnut>> walnutListAdd = new ArrayList<>();
    public static ArrayList<ArrayList<Walnut>> walnutListRemove = new ArrayList<>();
    public static ArrayList<ArrayList<PotatoMine>> potatoMineList = new ArrayList<>();
    public static ArrayList<ArrayList<PotatoMine>> potatoMineListAdd = new ArrayList<>();
    public static ArrayList<ArrayList<PotatoMine>> potatoMineListRemove = new ArrayList<>();
    public static int[] furthestZombies = new int[5];
    Iterator<Sunflower> sunflowerIterator;
    public static int sunCount = 50000;
    public boolean zombiesAllDead = true;
    Zombie secondTempZombie;
    public static Thread zombieSpawnThread;
    public static Thread sunThread;
    int minimum;
    int tempInt;
    public static boolean hasStartedSun = false;
    public int levelProgressState = 0;
    Peashooter tempPeashooter;
    public static boolean soundPlayed = false;
    Iterator<Walnut> walnutIterator;
    Iterator<PotatoMine> potatoMineIterator;
    PotatoMine tempPotatoMine;
    Walnut tempWalnut;
    public static int mouseX;
    public static int mouseY;
    Zombie tempZombie;
    Sunflower tempSunflower;
    public int[][][] zombieSpawnList = {{{5, 5, 3, 4, 5, 6, 1,0}, {5, 0, 1, 1, 1, 2, 0,0}, {2, 0, 0, 1, 1, 1, 0,0}}, {{1, 1, 1, 1, 1, 2, 3,0}, {0, 0, 1, 2, 2, 2, 3,0}, {1, 1, 1, 1, 1, 1, 1,0}}, {{1, 2, 3, 4, 5, 6, 7,0}, {0, 0, 1, 2, 3, 3, 5,0}, {1, 1, 1, 1, 1, 1, 1,0}}};
    int[][] zombieTimes = {{2000, 2000, 2000, 2000, 2000, 2000, 2000, 100000}, {10000, 2000, 3000, 6000, 7000, 8000, 10000, 100000}, {10000, 2000, 3000, 6000, 7000, 8000, 10000, 100000}};

    //Constructor for gamepanel class 
    public GamePanel() {
        //loading all images
        loadImage();
        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this); //starts listening for keyboard input
        this.addMouseListener(this);
        inventory = new Inventory(this);
        //adds mouselistener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isRunning) {
                    Menu.mouseReleased(e);
                } else {
                    inventory.mouseReleased(e);
                }
            }

            //overriding mousereleased method
            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
        //adds mouse motion listener
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!isRunning) {
                    Menu.mouseMoved(e);
                } else {
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
        //initializes soundtrack
        SoundTrack.setUp();
        //initializes all arraylists
        for (int i = 0; i < 5; i++) {
            peaList.add(new ArrayList<>());
            zombieList.add(new ArrayList<>());
            peashooterList.add(new ArrayList<>());
            sunflowerList.add(new ArrayList<>());
            walnutList.add(new ArrayList<>());
            potatoMineList.add(new ArrayList<>());
            peaListRemove.add(new ArrayList<>());
            zombieListRemove.add(new ArrayList<>());
            peashooterListRemove.add(new ArrayList<>());
            sunflowerListRemove.add(new ArrayList<>());
            walnutListRemove.add(new ArrayList<>());
            potatoMineListRemove.add(new ArrayList<>());
            peaListAdd.add(new ArrayList<>());
            zombieListAdd.add(new ArrayList<>());
            peashooterListAdd.add(new ArrayList<>());
            sunflowerListAdd.add(new ArrayList<>());
            walnutListAdd.add(new ArrayList<>());
            potatoMineListAdd.add(new ArrayList<>());
        }
    }

    //loads all images
    public void loadImage() {
        try {
            gameBackground = ImageIO.read(getClass().getResource("/Images/gameGrid.png"));
            endScreenWinDefault = ImageIO.read(getClass().getResource("/Images/endScreenWinDefault.png"));
            endScreenWinHover = ImageIO.read(getClass().getResource("/Images/endScreenWinHover.png"));
            endScreenLoseDefault = ImageIO.read(getClass().getResource("/Images/endScreenLoseDefault.png"));
            endScreenLoseHover = ImageIO.read(getClass().getResource("/Images/endScreenLoseHover.png"));
        } catch (IOException e) {
            System.out.println("Error loading images. Please check the files were saved correctly.");
        }
    }

    //draws things on screen
    @Override
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
            //draws stuff if game is running
            if (!gameEnded) {
                if (gameBackground != null) {
                    g.drawImage(gameBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
                }
                //draws plants and zombies
                for (int i = 0; i < 5; i++) {
                    for (Pea pea : new ArrayList<>(peaList.get(i))) {
                        pea.draw(g);
                    }
                    for (Zombie zombie : new ArrayList<>(zombieList.get(i))) {
                        zombie.draw(g);
                    }
                    for (Peashooter peashooter : peashooterList.get(i)) {
                        peashooter.draw(g);
                    }
                    for (Sunflower sunflower : sunflowerList.get(i)) {
                        sunflower.draw(g);
                    }
                    for (Walnut walnut : walnutList.get(i)) {
                        walnut.draw(g);
                    }
                    for (PotatoMine potatoMine : potatoMineList.get(i)) {
                        potatoMine.draw(g);
                    }
                }
                //draws inventory
                inventory.draw(g);
                //draws sun
                for (Sun sun : new ArrayList<>(sunList)) {
                    sun.draw(g);
                }
            }
            //draws endscreens depending on if the player lost or won
            if (gameEnded && !isLost) {
                if (350 <= mouseX && mouseX <= 350 + 167 && 631 <= mouseY && mouseY <= 631 + 73) {
                    g.drawImage(gameBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
                    g.drawImage(endScreenWinHover, 0, 0, null);
                } else {
                    g.drawImage(gameBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
                    g.drawImage(endScreenWinDefault, 0, 0, null);
                }
                if (350 <= mouseClickedX && mouseClickedX <= 350 + 167 && 631 <= mouseClickedY && mouseClickedY <= 631 + 73) {
                    endGame();
                }
            } else if (gameEnded && isLost) {
                if (350 <= mouseX && mouseX <= 350 + 167 && 631 <= mouseY && mouseY <= 631 + 73) {
                    g.drawImage(gameBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
                    g.drawImage(endScreenLoseHover, 0, 0, null);
                } else {
                    g.drawImage(gameBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
                    g.drawImage(endScreenLoseDefault, 0, 0, null);
                }
                if (350 <= mouseClickedX && mouseClickedX <= 350 + 167 && 631 <= mouseClickedY && mouseClickedY <= 631 + 73) {
                    endGame();
                }

            }
        } else {
            //draws the main menu
            Menu.draw(g);
        }
    }

    //call the move methods in other classes to update positions
    public void move() {
        if (isRunning && !gameEnded) {
            if (zombiesAllDead) {
                //spawns zombies once all zombies have died
                spawnZombie();
            }
            //makes peas and zombies move
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
                for (Sunflower sunflower : sunflowerList.get(i)) {
                    sunflower.move();
                }
                for (Walnut walnut : walnutList.get(i)) {
                    walnut.move();
                }
                for (PotatoMine potatoMine : potatoMineList.get(i)) {
                    potatoMine.move();
                }
            }
            //makes sun move
            for (Sun sun : sunList) {
                sun.move();
            }
            //makes inventory move
            inventory.move();

        }
        if (!hasStartedSun && !gameEnded) {
            //spawns sun
            spawnSun();
        }

    }

    //spawns sun
    public void spawnSun() {
        if (isRunning && (sunThread == null || !sunThread.isAlive())) {
            sunThread = new Thread(new Runnable() {
                @Override
                @SuppressWarnings("UseSpecificCatch")
                public void run() {
                    try {
                        //spawns sun at random intervals using a thread
                        Thread.sleep((int) (Math.random() * /* 10000 + 6000*/ 5000));
                        addSun(new Sun((int) (Math.random() * (GAME_WIDTH - 100) + 50), (int) (Math.random() * (GAME_HEIGHT - 300) + 50), GamePanel.this));
                        sunThread.interrupt();
                    } catch (Exception e) {

                    }
                }
            });
            sunThread.start();
        }
    }

    //spawns zombies
    public void spawnZombie() {
        if (levelProgressState < zombieSpawnList[difficulty - 1][0].length-1 && isRunning
                && (zombieSpawnThread == null || !zombieSpawnThread.isAlive())) {

            // Initialize zombieListAdd only if it's empty
            if (zombieListAdd.isEmpty()) {
                for (int j = 0; j < 5; j++) {
                    zombieListAdd.add(new ArrayList<Zombie>());
                }
            }

            zombieSpawnThread = new Thread(() -> {
                try {
                    while (isRunning && levelProgressState < zombieSpawnList[difficulty - 1][0].length-1) { // Exit condition for the loop
                        Thread.sleep(100);

                        // Wait until all zombies are dead
                        while (!zombiesAllDead && isRunning) {
                            Thread.sleep(100);
                        }

                        // Spawn zombies if the game is running
                        if (!isRunning) {
                            break;
                        }

                        
                        Thread.sleep((int) ((Math.random() * 0.25 + 1) * zombieTimes[difficulty - 1][levelProgressState]));

                        // Spawn Regular Zombies
                        for (int i = 0; i < zombieSpawnList[difficulty - 1][0][levelProgressState]; i++) {
                            tempInt = (int) (Math.random() * 5) + 1;
                            synchronized (zombieListAdd) {
                                RegularZombie zombie = new RegularZombie(tempInt, GamePanel.this);
                                zombieListAdd.get(tempInt - 1).add(zombie);
                            }
                            Thread.sleep((int) (Math.random() * 5000 + 1000));
                        }

                        // Spawn Cone Zombies
                        for (int i = 0; i < zombieSpawnList[difficulty - 1][1][levelProgressState]; i++) {
                            tempInt = (int) (Math.random() * 5) + 1;
                            synchronized (zombieListAdd) {
                                ConeZombie zombie = new ConeZombie(tempInt, GamePanel.this);
                                zombieListAdd.get(tempInt - 1).add(zombie);
                            }
                            Thread.sleep((int) (Math.random() * 5000 + 1000));
                        }

                        // Spawn Gargantuars
                        for (int i = 0; i < zombieSpawnList[difficulty - 1][2][levelProgressState]; i++) {
                            tempInt = (int) (Math.random() * 5) + 1;
                            synchronized (zombieListAdd) {
                                Gargantuar zombie = new Gargantuar(tempInt, GamePanel.this);
                                zombieListAdd.get(tempInt - 1).add(zombie);
                            }
                            Thread.sleep((int) (Math.random() * 5000 + 1000));
                        }

                        // Advance level progress
                        levelProgressState++;
                        if (levelProgressState > zombieSpawnList[difficulty - 1][0].length-2) {
                            while (!zombiesAllDead) {
                                try {
                                    Thread.sleep(300);
                                } catch (Exception e) {

                                }
                            }
                            gameEnded = true;
                            isLost = false;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Log the exception for debugging
                }
            });
            //starts zombie thread
            zombieSpawnThread.start();
        }
    }

    //handles all collision detection and responds accordingly
    public void checkCollision() {
        //finds the furthest zombie
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

        //if the furthest zombie is at house, ends game
        for (int i = 0; i < 5; i++) {
            if (furthestZombies[i] != -1) {
                if (zombieList.get(i).get(furthestZombies[i]).getXEat() <= 0) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {

                    }
                    isLost = true;
                    gameEnded = true;

                }

                //handles pea collisions with zombies
                tempZombie = zombieList.get(i).get(furthestZombies[i]);
                peaIterator = peaList.get(i).iterator();
                while (peaIterator.hasNext()) {
                    tempPea = peaIterator.next();
                    for (Zombie z : zombieList.get(i)) {
                        if (z.getXEat() >= tempPea.getPosX() - 20 && z.getXEat() <= tempPea.getPosX() + 10) {
                            
                            peaListRemove.get(i).add(tempPea);
                            z.peaDamage();
                            Sound.playSingleSound("Sounds\\pea shooter sound effects 1# - Made with Clipchamp.wav", 30);
                            break;
                        }
                    }
                }

                //checks if potato mine has been stepped on
                potatoMineIterator = potatoMineList.get(i).iterator();
                while (potatoMineIterator.hasNext()) {
                    tempPotatoMine = potatoMineIterator.next();
                    if (tempPotatoMine.isArmed()) {
                        for (Zombie z : zombieList.get(i)) {
                            //if(!tempPotatoMine.showExplosion){
                                if (z.getXEat() - tempPotatoMine.getPosX() <= 51) {
                                    //show explosion
                                    tempPotatoMine.showExplosion = true;
                                }
                                if (z.getXEat() - tempPotatoMine.getPosX() <= 50) {
                                    //play explosion sound
                                    Sound.playSingleSound("Sounds\\Potato Mine Explosion - Plants vs Zombies Sound Effect - Made with Clipchamp.wav", 0);
                                }
                                if (z.getXEat() >= tempPotatoMine.getXEat() + 10 && z.getXEat() <= tempPotatoMine.getXEat() + 70) {
                                    z.potatoMineDamage();
                                    potatoMineListRemove.get(i).add(tempPotatoMine);
                                    tempPotatoMine.showExplosion = true;
                                }
                            //}                            
                        }
                    }
                }

                //if a zombie has no durability, it kills the zombie
                if (tempZombie.getDurability() <= 0) {
                    zombieIterator = zombieList.get(i).iterator();
                    while (zombieIterator.hasNext()) {
                        secondTempZombie = zombieIterator.next();
                        if (secondTempZombie == tempZombie) {
                            zombieListRemove.get(i).add(secondTempZombie);
                            break;
                        }
                    }
                    if (zombieList.get(i).isEmpty()) {
                        furthestZombies[i] = -1;
                    } else {
                        furthestZombies[i] = 0;
                    }
                }

            }
            //handles collisions between peashooters and zombies
            peashooterIterator = peashooterList.get(i).iterator();
            while (peashooterIterator.hasNext()) {
                tempPeashooter = peashooterIterator.next();
                if (furthestZombies[i] == -1) {
                    tempPeashooter.noZombie();
                    continue;
                }
                if (tempPeashooter.getDurability() <= 0) {//removes peashooters if they are dead
                    peashooterListRemove.get(i).add(tempPeashooter);
                    tempPeashooter.die();
                } else {
                    zombieIterator = zombieList.get(i).iterator();
                    while (zombieIterator.hasNext()) {
                        tempZombie = zombieIterator.next();
                        if (furthestZombies[i] == -1) {
                            tempPeashooter.noZombie();
                        } else if (tempZombie.getXPosition() >= tempPeashooter.getXEat()) {
                            tempPeashooter.yesZombie();
                        }
                    }
                }
            }

            //iterates through all plants to determine if a zombie is eating them
            zombieIterator = zombieList.get(i).iterator();
            while (zombieIterator.hasNext()) {
                exists = false;
                tempZombie = zombieIterator.next();
                if (peashooterList.get(i).isEmpty() && sunflowerList.get(i).isEmpty() && walnutList.get(i).isEmpty() && potatoMineList.get(i).isEmpty()) {
                    tempZombie.go();
                    continue;
                } else {
                    peashooterIterator = peashooterList.get(i).iterator();
                    while (peashooterIterator.hasNext()) {
                        tempPeashooter = peashooterIterator.next();
                        if (tempZombie.getXPosition() <= Grid.colToX(tempPeashooter.getCol()) + 50 && tempZombie.getXPosition() >= Grid.colToX(tempPeashooter.getCol())) {
                            if (tempZombie instanceof RegularZombie || tempZombie instanceof ConeZombie) {
                                tempPeashooter.regularEatPlant();
                            } else if (tempZombie instanceof Gargantuar) {
                                tempPeashooter.gargantuarEatPlant();
                            }
                            tempZombie.stop();
                            exists = true;

                            //eating
                            if (!soundPlayed) {
                                Sound.playEatingSounds();
                                soundPlayed = true; // Set the flag to true after the sound is played
                            }
                        }
                    }
                    //iterates through sunflowers to see if they are being eaten
                    sunflowerIterator = sunflowerList.get(i).iterator();
                    while (sunflowerIterator.hasNext()) {
                        tempSunflower = sunflowerIterator.next();
                        if (tempZombie.getXPosition() <= Grid.colToX(tempSunflower.getCol()) + 50 && tempZombie.getXPosition() >= Grid.colToX(tempSunflower.getCol())) {
                            if (tempZombie instanceof RegularZombie || tempZombie instanceof ConeZombie) {
                                tempSunflower.regularEatPlant();

                            } else if (tempZombie instanceof Gargantuar) {
                                tempSunflower.gargantuarEatPlant();
                            }
                            tempZombie.stop();
                            exists = true;

                            //eating
                            if (!soundPlayed) {
                                Sound.playEatingSounds();
                                soundPlayed = true; // Set the flag to true after the sound is played
                            }
                        }
                    }
                    //iterates through walnuts to see if they are being eaten
                    walnutIterator = walnutList.get(i).iterator();
                    while (walnutIterator.hasNext()) {
                        tempWalnut = walnutIterator.next();
                        if (tempZombie.getXPosition() <= Grid.colToX(tempWalnut.getCol()) + 50 && tempZombie.getXPosition() >= Grid.colToX(tempWalnut.getCol())) {
                            if (tempZombie instanceof RegularZombie || tempZombie instanceof ConeZombie) {
                                tempWalnut.regularEatPlant();

                            } else if (tempZombie instanceof Gargantuar) {
                                tempWalnut.gargantuarEatPlant();
                            }
                            tempZombie.stop();
                            exists = true;

                            //eating
                            if (!soundPlayed) {
                                Sound.playEatingSounds();
                                soundPlayed = true; // Set the flag to true after the sound is played
                            }
                        }
                    }
                    //iterates through potato mines to see if they are being eaten
                    potatoMineIterator = potatoMineList.get(i).iterator();
                    while (potatoMineIterator.hasNext()) {
                        tempPotatoMine = potatoMineIterator.next();
                        if (tempZombie.getXPosition() <= Grid.colToX(tempPotatoMine.getCol()) + 50 && tempZombie.getXPosition() >= Grid.colToX(tempPotatoMine.getCol())) {
                            if (tempZombie instanceof RegularZombie || tempZombie instanceof ConeZombie) {
                                tempPotatoMine.regularEatPlant();
                            } else if (tempZombie instanceof Gargantuar) {
                                tempPotatoMine.gargantuarEatPlant();
                            }
                            tempZombie.stop();
                            exists = true;

                            //eating
                            if (!soundPlayed) {
                                Sound.playEatingSounds();
                                soundPlayed = true; // Set the flag to true after the sound is played
                            }
                        }
                    }
                    if (!exists) {
                        //if there is no plant in front of it, the zombie moves forward
                        tempZombie.go();
                    }
                }
            }

            //removes sunflowers once they die
            sunflowerIterator = sunflowerList.get(i).iterator();
            while (sunflowerIterator.hasNext()) {
                tempSunflower = sunflowerIterator.next();
                if (tempSunflower.getDurability() <= 0) {
                    sunflowerListRemove.get(i).add(tempSunflower);
                    tempSunflower.die();
                }
            }
            
            //removes walnuts once they die
            walnutIterator = walnutList.get(i).iterator();
            while (walnutIterator.hasNext()) {
                tempWalnut = walnutIterator.next();
                if (tempWalnut.getDurability() <= 0) {
                    walnutListRemove.get(i).add(tempWalnut);
                    tempWalnut.die();
                }
            }
            //removes potatomines  once they die
            potatoMineIterator = potatoMineList.get(i).iterator();
            while (potatoMineIterator.hasNext()) {
                tempPotatoMine = potatoMineIterator.next();
                if (tempPotatoMine.getDurability() <= 0) {
                    potatoMineListRemove.get(i).add(tempPotatoMine);
                    tempPotatoMine.die();
                }
            }
        }

        //removes sun once they are clicked
        sunIterator = sunList.iterator();
        while (sunIterator.hasNext()) {
            Sun tempSun = sunIterator.next();
            if (tempSun.getIsGone()) {
                sunListRemove.add(tempSun);
                tempSun.die();
            }
        }

        //checks if all the zombies have died
        zombiesAllDead = true;
        for (int i = 0; i < 5; i++) {
            if (!zombieList.get(i).isEmpty()) {
                zombiesAllDead = false;
                break;
            }
        }
    }

    //everything is added into a seperate array and then added and removed later
    public void addAndRemove() {
        //iterates through all lanes
        for (int i = 0; i < 5; i++) {
            //adds everything to be added into the necessary arraylists
            for (Walnut walnut : walnutListAdd.get(i)) {
                walnutList.get(i).add(walnut);
            }
            for (PotatoMine potatoMine : potatoMineListAdd.get(i)) {
                potatoMineList.get(i).add(potatoMine);
            }
            for (Peashooter peashooter : peashooterListAdd.get(i)) {
                peashooterList.get(i).add(peashooter);
            }
            for (Sunflower sunflower : sunflowerListAdd.get(i)) {
                sunflowerList.get(i).add(sunflower);
            }
            for (Zombie zombie : (zombieListAdd.get(i))) {
                zombieList.get(i).add(zombie);
            }
            for (Pea pea : peaListAdd.get(i)) {
                peaList.get(i).add(pea);
            }
            //removes everything to be removed
            for (Pea pea : peaListRemove.get(i)) {
                peaList.get(i).remove(pea);
            }
            for (Zombie zombie : zombieListRemove.get(i)) {
                zombieList.get(i).remove(zombie);
            }
            for (Peashooter peashooter : peashooterListRemove.get(i)) {
                peashooterList.get(i).remove(peashooter);
            }
            for (Sunflower sunflower : sunflowerListRemove.get(i)) {
                sunflowerList.get(i).remove(sunflower);
            }
            for (Walnut walnut : walnutListRemove.get(i)) {
                walnutList.get(i).remove(walnut);
            }
            for (PotatoMine potatoMine : potatoMineListRemove.get(i)) {
                potatoMineList.get(i).remove(potatoMine);
            }
        }
        //adds and removes sun that must be removed
        for (Sun sun : sunListAdd) {
            sunList.add(sun);
        }
        for (Sun sun : sunListRemove) {
            sunList.remove(sun);
        }
        //clears all the lists
        peashooterListRemove.clear();
        sunflowerListRemove.clear();
        peaListRemove.clear();
        zombieListRemove.clear();
        peashooterListAdd.clear();
        sunflowerListAdd.clear();
        peaListAdd.clear();
        zombieListAdd.clear();
        walnutListAdd.clear();
        walnutListRemove.clear();
        potatoMineListAdd.clear();
        potatoMineListRemove.clear();
        //iterates through the array of each lane, and reinitializes them
        for (int i = 0; i < 5; i++) {
            peaListAdd.add(new ArrayList<>());
            zombieListAdd.add(new ArrayList<>());
            sunflowerListAdd.add(new ArrayList<>());
            peashooterListAdd.add(new ArrayList<>());
            walnutListAdd.add(new ArrayList<>());
            potatoMineListAdd.add(new ArrayList<>());
            zombieListRemove.add(new ArrayList<>());
            peaListRemove.add(new ArrayList<>());
            sunflowerListRemove.add(new ArrayList<>());
            peashooterListRemove.add(new ArrayList<>());
            walnutListRemove.add(new ArrayList<>());
            potatoMineListRemove.add(new ArrayList<>());
        }
        //reinitializes the sunlist
        sunListRemove.clear();
        sunListAdd.clear();
    }

    //ensures that the game keeps running
    @Override
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
                try {
                    //tries to check for collisions
                    checkCollision();
                } catch (Exception e) {
                }
                while(true){
                    try{
                        //keeps trying to add and remove stuff until successful
                        addAndRemove();
                        break;
                    }
                    catch(Exception e){

                    }
                }
                
                repaint();
                delta--;
            }
        }
        //ends the game
        System.exit(0);
    }

    //checks when a key is pressed and sends to other classes for processing
    @Override
    public void keyPressed(KeyEvent e) {

    }

    //checks when a key is released and sends to other classes for processing
    @Override
    public void keyReleased(KeyEvent e) {
        menu.keyReleased(e);

    }

    //overrides KeyListener interface
    @Override
    public void keyTyped(KeyEvent e) {
    }

    //overrides MouseMotionListener interface
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    //overrides MouseMotionListener interface
    @Override
    public void mouseMoved(MouseEvent e) {

        mouseX = e.getX();
        mouseY = e.getY();
    }

    //overrides mousereleased interface, and checks if any sun has been clicked on.
    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            for (Sun sun : sunList) {
                sun.mouseReleased(e);
            }
        } catch (Exception h) {
        }
        mouseClickedX = e.getX();
        mouseClickedY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //plants a peashooter
    public void plantPeashooter(int x, int y) {
        peashooterList.get(Grid.yToRow(y) - 1).add(new Peashooter(Grid.xToCol(x), Grid.yToRow(y), this));
    }

    //plants a potatomine
    public void plantPotatoMine(int x, int y) {
        potatoMineList.get(Grid.yToRow(y) - 1).add(new PotatoMine(Grid.xToCol(x), Grid.yToRow(y), this));
    }

    //adds a pea in a row
    public void addPea(Pea p, int rw) {
        peaListAdd.get(rw - 1).add(p);
    }

    //plants a walnut
    public void plantWalnut(int x, int y) {
        walnutList.get(Grid.yToRow(y) - 1).add(new Walnut(Grid.xToCol(x), Grid.yToRow(y), this));
    }

    //increments the sun count
    public void changeSun(int s) {
        if (s > 0) {
            Sound.playSingleSound("Sounds\\Sun Being Collected (sound effect).wav", 0);
        }
        sunCount += s;
    }

    //plants a sunflower
    public void plantSunflower(int x, int y) {
        if (sunCount >= 50) {
            sunflowerList.get(Grid.yToRow(y) - 1).add(new Sunflower(Grid.xToCol(x), Grid.yToRow(y), this));
        }
    }

    //returns suncount
    public int getSun() {
        return sunCount;
    }

    //overrides mousepressed interface
    @Override
    public void mousePressed(MouseEvent e) {
        //plays click sound effect
        Sound.playSingleSound("Sounds\\Select Click V.2 - Sound Effect (HD).wav - Made with Clipchamp.wav", -60);
    }

    //overrides mouseentered interface
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    //overrides mouseexited interface
    @Override
    public void mouseExited(MouseEvent e) {
    }

    //checks if there is a plant in a row/column
    public boolean isPlant(int row, int col) {
        while (true) {
            try {
                for (Peashooter p : peashooterList.get(row - 1)) {
                    if (p.getCol() == col) {
                        return true;
                    }
                }
                for (Sunflower s : sunflowerList.get(row - 1)) {
                    if (s.getCol() == col) {
                        return true;
                    }
                }
                for (PotatoMine p : potatoMineList.get(row - 1)) {
                    if (p.getCol() == col) {
                        return true;
                    }
                }
                for (Walnut w : walnutList.get(row - 1)) {
                    if (w.getCol() == col) {
                        return true;
                    }
                }
                break;
            } catch (Exception e) {
            }
        }
        return false;
    }

    //adds sun to gamepanel
    public void addSun(Sun s) {
        sunListAdd.add(s);
    }

    //returns difficulty
    public int getDifficulty() {
        return difficulty;
    }

    //ends game
    public void endGame() {
        //resets all booleans and values
        gameEnded = false;
        isRunning = false;
        isGameDone = false;
        isLost = false;
        difficulty = 1;
        sunCount = 50;
        levelProgressState = 0;
        hasStartedSun = false;
        soundPlayed = false;
        //reinitializes and clears all arraylists
        peaList.clear();
        zombieList.clear();
        peashooterList.clear();
        sunflowerList.clear();
        walnutList.clear();
        potatoMineList.clear();
        sunList.clear();
        peaListRemove.clear();
        zombieListRemove.clear();
        peashooterListRemove.clear();
        sunflowerListRemove.clear();
        walnutListRemove.clear();
        potatoMineListRemove.clear();
        sunListRemove.clear();
        peaListAdd.clear();
        zombieListAdd.clear();
        peashooterListAdd.clear();
        sunflowerListAdd.clear();
        walnutListAdd.clear();
        potatoMineListAdd.clear();
        sunListAdd.clear();
        //reinitializes the lists
        for (int i = 0; i < 5; i++) {
            peaList.add(new ArrayList<>());
            zombieList.add(new ArrayList<>());
            peashooterList.add(new ArrayList<>());
            sunflowerList.add(new ArrayList<>());
            walnutList.add(new ArrayList<>());
            potatoMineList.add(new ArrayList<>());
            peaListRemove.add(new ArrayList<>());
            zombieListRemove.add(new ArrayList<>());
            peashooterListRemove.add(new ArrayList<>());
            sunflowerListRemove.add(new ArrayList<>());
            walnutListRemove.add(new ArrayList<>());
            potatoMineListRemove.add(new ArrayList<>());
            peaListAdd.add(new ArrayList<>());
            zombieListAdd.add(new ArrayList<>());
            peashooterListAdd.add(new ArrayList<>());
            sunflowerListAdd.add(new ArrayList<>());
            walnutListAdd.add(new ArrayList<>());
            potatoMineListAdd.add(new ArrayList<>());
        }
        //ends the game for menu
        Menu.endGame();
        //resets the inventory
        inventory = new Inventory(this);

        //restarts the game
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            while (true) {
                try {
                    gameThread = new Thread(this);
                    gameThread.start();
                    break;
                } catch (Exception e) {
                }
            }
        }
    }

    //removes a plant in a row/column by adding to to the remove list
    public void removePlant(int row, int col) {
        //keeps trying until successful
        while (true) {
            try {
                //removes peashooter
                for (Peashooter p : peashooterList.get(row - 1)) {
                    if (p.getCol() == col) {
                        peashooterListRemove.get(row - 1).add(p);
                        break;
                    }
                }
                //removes sunflowers
                for (Sunflower s : sunflowerList.get(row - 1)) {
                    if (s.getCol() == col) {
                        sunflowerListRemove.get(row - 1).add(s);
                        break;
                    }
                }
                //removes potatomines
                for (PotatoMine p : potatoMineList.get(row - 1)) {
                    if (p.getCol() == col) {
                        potatoMineListRemove.get(row - 1).add(p);
                        break;
                    }
                }
                //removes walnuts
                for (Walnut w : walnutList.get(row - 1)) {
                    if (w.getCol() == col) {
                        walnutListRemove.get(row - 1).add(w);
                        break;
                    }
                }
                break;
            } catch (Exception e) {
            }
        }
    }
    public void removePea(Pea p){
        peaListRemove.get(p.getRow()-1).add(p);
    }
}
