//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for the Inventory class that controls the sun cost, planting, seedslots, and progress bar

//imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//code for inventory class
public class Inventory {

    //declares all variables
    private BufferedImage inventoryImage;
    private BufferedImage zombieHead;
    private BufferedImage shovelImage;
    private BufferedImage shovelOnlyImage;
    private BufferedImage peaUpgradeImage;
    private BufferedImage mineUpgradeImage;
    private BufferedImage peaSpeedImage;
    private BufferedImage sunflowerImage;
    private BufferedImage peashooterImage;
    private BufferedImage mineImage;
    private BufferedImage walnutImage;
    private int state;
    private int selectedState;
    private int mouseX;
    private int mouseY;
    private Thread colorThread;
    private final GamePanel game;
    private Message sunAmount = new Message(34 + 40, 87 + 10, 50, 50, "50", 20);
    private int progress;
    private int progressBarWidth = 200;
    private int totalProgress;
    int[] mineUpgradeCosts = {50, 100, 200, 400, 600, 1000, 2000};
    int[] mineUpgradeTimes = {20000, 15000, 9000, 5000, 3000, 2000, 1000};
    int mineUpgradeState = 0;
    int[] peaUpgradeCosts = {50, 100, 300, 600, 1000, 1500, 2000};
    int[] peaUpgradeDamage = {20, 35, 50, 65, 80, 100, 125};
    int peaUpgradeState = 0;
    int[] speedUpgradeCosts = {50, 100, 300, 600, 1000, 1500, 2000};
    double[] speedUpgradeTimes = {1.0, 0.75, 0.5, 0.45, 0.4, 0.35, 0.25};
    int speedUpgradeState = 0;
    Message peaUpgradeMessage = new Message(605, 92, 50, 50, peaUpgradeCosts[0] + "", 17);
    Message mineUpgradeMessage = new Message(715, 92, 50, 50, mineUpgradeCosts[0] + "", 17);
    Message peaSpeedMessage = new Message(820, 92, 50, 50, speedUpgradeCosts[0] + "", 17);
    Message dmgLevel = new Message(600, 73, 50, 50, peaUpgradeDamage[0] + "dmg", 14);
    Message timeLevel = new Message(720, 73, 50, 50, mineUpgradeTimes[0] / 1000 + "s", 14);
    Message speedLevel = new Message(835, 73, 50, 50, (Math.round((100.0 / speedUpgradeTimes[speedUpgradeState])) / 100.0) + "x", 14);
    int mouseMovedX;
    int mouseMovedY;

    //code for constructor
    Inventory(GamePanel gme) {
        game = gme;
        loadImage();
        totalProgress = game.zombieSpawnList[game.getDifficulty()][0].length - 1;
    }

    //loads all images
    public void loadImage() {
        try {
            inventoryImage = ImageIO.read(getClass().getResource("/Images/inventoryImage.png"));
            zombieHead = ImageIO.read(getClass().getResource("/Images/zombieHeadt1.png"));
            shovelImage = ImageIO.read(getClass().getResource("/Images/shovelFramet1.png"));
            shovelOnlyImage = ImageIO.read(getClass().getResource("/Images/shovelt1.png"));
            sunflowerImage = ImageIO.read(getClass().getResource("/Images/sunflowert1.png"));
            peashooterImage = ImageIO.read(getClass().getResource("/Images/peashootert1.png"));
            mineImage = ImageIO.read(getClass().getResource("/Images/armedPotatoMineImage.png"));
            walnutImage = ImageIO.read(getClass().getResource("/Images/walnutt1.png"));
            peaUpgradeImage = ImageIO.read(getClass().getResource("/Images/peaUpgradeNew.png"));
            mineUpgradeImage = ImageIO.read(getClass().getResource("/Images/potatoMineUpgrade.png"));
            peaSpeedImage = ImageIO.read(getClass().getResource("/Images/peashooterUpgradeSpeed.png"));

        } catch (IOException e) {
            //prints error message
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }

    //handles sun amount and game progress
    public void move() {
        sunAmount.setMessage(game.getSun() + "");
        peaUpgradeMessage.setMessage(peaUpgradeCosts[peaUpgradeState] + "");
        mineUpgradeMessage.setMessage(mineUpgradeCosts[mineUpgradeState] + "");
        peaSpeedMessage.setMessage(speedUpgradeCosts[speedUpgradeState] + "");
        dmgLevel.setMessage(peaUpgradeDamage[peaUpgradeState] + "dmg");
        timeLevel.setMessage(mineUpgradeTimes[mineUpgradeState] / 1000 + "s");
        speedLevel.setMessage((Math.round((100 / speedUpgradeTimes[speedUpgradeState])) / 100.0) + "x");
        if (peaUpgradeState == peaUpgradeCosts.length - 1) {
            peaUpgradeMessage.setMessage("Max");
        }
        if (mineUpgradeState == mineUpgradeCosts.length - 1) {
            mineUpgradeMessage.setMessage("Max");
        }
        if (speedUpgradeState == speedUpgradeCosts.length - 1) {
            peaSpeedMessage.setMessage("Max");
        }
        progress = (int) (1.0 * game.levelProgressState / totalProgress * (progressBarWidth - 30)) + 30;
    }

    //stuff for key released
    public void keyReleased(KeyEvent e) {
        if (selectedState == 0) {
            //checks which key was pressed
            if (e.getKeyChar() == '1') {
                state = 1;
            } else if (e.getKeyChar() == '2') {
                state = 2;
            } else if (e.getKeyChar() == '3') {
                state = 3;
            } else if (e.getKeyChar() == '4') {
                state = 4;
            } else if (e.getKeyChar() == '5') {
                state = 5;
            } else if (e.getKeyChar() == '6') {
                state = 6;
            } else if (e.getKeyChar() == '7') {
                state = 7;
            } else if (e.getKeyChar() == '8') {
                state = 8;
            } else {
                state = 0;
            }
        }
        //depending on where it was clicked, it changes
        if (selectedState == 0) {
            switch (state) {
                case 0:
                    selectedState = 0;
                    break;
                case 1:
                    selectedState = 1;
                    break;
                case 2:
                    selectedState = 2;
                    break;
                case 3:
                    selectedState = 3;
                    break;
                case 4:
                    selectedState = 4;
                    break;
                case 5:
                    selectedState = 5;
                    break;
                //upgrade pea
                case 6:
                    if (peaUpgradeState + 1 < peaUpgradeCosts.length && game.getSun() >= peaUpgradeCosts[peaUpgradeState]) {
                        Sound.playSingleSound("Sounds\\chaching.wav", 0);
                        game.changeSun(-peaUpgradeCosts[peaUpgradeState]);
                        game.setPeaDamage(peaUpgradeDamage[peaUpgradeState]);
                        if (peaUpgradeState < peaUpgradeCosts.length - 1) {
                            peaUpgradeState++;
                        }
                    } else if (!(game.getSun() >= peaUpgradeCosts[peaUpgradeState])) {
                        sunRed();
                    }
                    break;
                case 7:
                    //upgrade mine
                    if (mineUpgradeState + 1 < mineUpgradeCosts.length && game.getSun() >= mineUpgradeCosts[mineUpgradeState]) {
                        Sound.playSingleSound("Sounds\\chaching.wav", 0);
                        game.changeSun(-mineUpgradeCosts[mineUpgradeState]);
                        game.setMineTime(mineUpgradeTimes[mineUpgradeState]);
                        if (mineUpgradeState < mineUpgradeCosts.length - 1) {
                            mineUpgradeState++;
                        }
                    } else if (!(game.getSun() >= mineUpgradeCosts[mineUpgradeState])) {
                        sunRed();
                    }
                    break;
                case 8:
                    //upgrade speed
                    if (speedUpgradeState + 1 < speedUpgradeCosts.length && game.getSun() >= speedUpgradeCosts[speedUpgradeState]) {
                        Sound.playSingleSound("Sounds\\chaching.wav", 0);
                        game.changeSun(-speedUpgradeCosts[speedUpgradeState]);
                        game.setSpeed(speedUpgradeTimes[speedUpgradeState]);
                        if (speedUpgradeState < speedUpgradeCosts.length - 1) {
                            speedUpgradeState++;
                        }
                    } else if (!(game.getSun() >= speedUpgradeCosts[speedUpgradeState])) {
                        sunRed();
                    }
                    break;
            }
        } //makes selected state 0
        else {
            selectedState = 0;
        }
    }

    //checks where the mouse is moved
    public void mouseMoved(MouseEvent e) {
        mouseMovedX = e.getX();
        mouseMovedY = e.getY();
    }

    //checks where the mouse was clicked
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //checks what state the mouse is in and changes it accordingly
        if (selectedState == 0) {
            if (56 + 107 < mouseX && mouseX < 56 + 107 + 71 && 8 < mouseY && mouseY < 108) {
                state = 1;
            } else if (56 + 181 < mouseX && mouseX < 56 + 170 + 71 && 8 < mouseY && mouseY < 108) {
                state = 2;
            } else if (56 + 245 < mouseX && mouseX < 56 + 253 + 71 && 8 < mouseY && mouseY < 108) {
                state = 3;
            } else if (56 + 325 < mouseX && mouseX < 56 + 325 + 71 && 8 < mouseY && mouseY < 108) {
                state = 4;
            } else if (465 < mouseX && mouseX < 465 + 107 && 8 < mouseY && mouseY < 108) {
                state = 5;
            } else if (565 < mouseX && mouseX < 565 + 110 && 0 < mouseY && mouseY < 108) {
                state = 6;
            } else if (675 < mouseX && mouseX < 675 + 110 && 0 < mouseY && mouseY < 108) {
                state = 7;
            } else if (785 < mouseX && mouseX < 785 + 110 && 0 < mouseY && mouseY < 108) {
                state = 8;
            } else {
                state = 0;
            }
        }

        //depending on where it was clicked, it changes
        switch (selectedState) {
            case 0:
                switch (state) {
                    case 0:
                        //makes state 0
                        selectedState = 0;
                        break;
                    case 1:
                        //makes state 1
                        selectedState = 1;
                        break;
                    case 2:
                        //makes state 2
                        selectedState = 2;
                        break;
                    case 3:
                        //makes state 3
                        selectedState = 3;
                        break;
                    case 4:
                        //makes case 4
                        selectedState = 4;
                        break;
                    case 5:
                        //makes case 5
                        selectedState = 5;
                        break;
                    case 6:
                        //upgrades pea
                        if (peaUpgradeState + 1 < peaUpgradeCosts.length && game.getSun() >= peaUpgradeCosts[peaUpgradeState]) {
                            Sound.playSingleSound("Sounds\\chaching.wav", 0);
                            game.changeSun(-peaUpgradeCosts[peaUpgradeState]);
                            game.setPeaDamage(peaUpgradeDamage[peaUpgradeState]);
                            if (peaUpgradeState < peaUpgradeCosts.length) {
                                peaUpgradeState++;
                            }
                        } else if (!(game.getSun() >= peaUpgradeCosts[peaUpgradeState])) {
                            sunRed();
                        }
                        break;
                    case 7:
                        //upgrades mine
                        if (mineUpgradeState + 1 < mineUpgradeCosts.length && game.getSun() >= mineUpgradeCosts[mineUpgradeState]) {
                            Sound.playSingleSound("Sounds\\chaching.wav", 0);
                            game.changeSun(-mineUpgradeCosts[mineUpgradeState]);
                            game.setMineTime(mineUpgradeTimes[mineUpgradeState]);
                            if (mineUpgradeState < mineUpgradeCosts.length) {
                                mineUpgradeState++;
                            }
                        } else if (!(game.getSun() >= mineUpgradeCosts[mineUpgradeState])) {
                            sunRed();
                        }
                        break;
                    case 8:
                        //upgrades pea speed
                        if (speedUpgradeState + 1 < speedUpgradeCosts.length && game.getSun() >= speedUpgradeCosts[speedUpgradeState]) {
                            Sound.playSingleSound("Sounds\\chaching.wav", 0);
                            game.changeSun(-speedUpgradeCosts[speedUpgradeState]);
                            game.setSpeed(speedUpgradeTimes[speedUpgradeState]);
                            if (speedUpgradeState < speedUpgradeCosts.length - 1) {
                                speedUpgradeState++;
                            }
                        } else if (!(game.getSun() >= speedUpgradeCosts[speedUpgradeState])) {
                            sunRed();
                        }
                        break;
                }
                break;
            case 1:
                //if it clicked on sunflower seed slot
                if (Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))) {
                    if (game.getSun() >= 50) {
                        game.plantSunflower(mouseX, mouseY);
                        game.changeSun(-50);
                    } else {
                        sunRed();
                    }

                }
                //makes selected state 0
                selectedState = 0;
                break;
            case 2:
                //if it clicked on peashooter seed slot
                if (Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))) {
                    if (game.getSun() >= 100) {
                        game.plantPeashooter(mouseX, mouseY);
                        game.changeSun(-100);
                    } else {
                        sunRed();
                    }

                }
                //makes selected state 0
                selectedState = 0;
                break;
            case 3:
                //if it clicked on potato mine seed slot
                if (Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))) {
                    if (game.getSun() >= 25) {
                        game.plantPotatoMine(mouseX, mouseY);
                        game.changeSun(-25);
                    } else {
                        sunRed();
                    }

                }
                selectedState = 0;
                break;
            case 4:
                //if it clicked on walnut seed slot
                if (Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))) {
                    if (game.getSun() >= 50) {
                        game.plantWalnut(mouseX, mouseY);
                        game.changeSun(-50);
                    } else {
                        sunRed();
                    }

                }
                selectedState = 0;
                break;
            case 5:
                //if it clicked on shovel slot
                if (Grid.isInGame(mouseX, mouseY)) {
                    game.removePlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX));
                }
                selectedState = 0;
                break;
        }
    }

    //makes sun red
    public void sunRed() {
        colorThread = new Thread(new Runnable() {
            public void run() {
                try {
                    sunAmount.setColorRed(true);
                    //play sound effect here
                    Thread.sleep(200);
                    sunAmount.setColorRed(false);
                    Sound.playSingleSound("Sounds\\x.wav", 20);
                } catch (Exception e) {

                }
            }
        });
        colorThread.start();
    }

    //draws all relevent images
    public void draw(Graphics g) {
        if (inventoryImage != null) {
            g.drawImage(inventoryImage, 56, 0, null);
            g.drawImage(shovelImage, 464, 0, null);
            //draws the shovel only if its not being currently held
            if (selectedState != 5) {
                g.drawImage(shovelOnlyImage, 480, 10, null);
            }
            //draws sun amount
            sunAmount.draw(g);

            //draws progress bar
            g.setColor(new Color(139, 69, 19));
            g.fillRoundRect(845, 750, progressBarWidth + 10, 35, 15, 15);
            g.setColor(Color.BLACK);
            g.fillRect(850, 755, progressBarWidth, 25);
            g.setColor(new Color(75, 190, 68));
            g.fillRect(850, 755, progress - 10, 25);
            g.drawImage(zombieHead, 820 + progress, 745, null);
            g.drawImage(mineUpgradeImage, 675, 0, null);
            g.drawImage(peaUpgradeImage, 565, 0, null);
            g.drawImage(peaSpeedImage, 675 + 110, 0, null);
            //draws message
            peaUpgradeMessage.draw(g);
            mineUpgradeMessage.draw(g);
            peaSpeedMessage.draw(g);
            dmgLevel.draw(g);
            timeLevel.draw(g);
            speedLevel.draw(g);
            //draws plants/shovel if it is being held on mouse
            switch (selectedState) {
                case 1:
                    g.drawImage(sunflowerImage, mouseMovedX - 50, mouseMovedY - 40, null);
                    break;
                case 2:
                    g.drawImage(peashooterImage, mouseMovedX - 50, mouseMovedY - 40, null);
                    break;
                case 3:
                    g.drawImage(mineImage, mouseMovedX - 50, mouseMovedY - 40, null);
                    break;
                case 4:
                    g.drawImage(walnutImage, mouseMovedX - 50, mouseMovedY - 40, null);
                    break;
                case 5:
                    g.drawImage(shovelOnlyImage, mouseMovedX - 20, mouseMovedY - 20, null);
                    break;
            }

        }
    }

    //ends the game by resetting everything
    public void endGame() {
        mineUpgradeState = 0;
        peaUpgradeState = 0;
        speedUpgradeState = 0;
    }
}
