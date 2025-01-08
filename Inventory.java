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
    private int state;
    private int selectedState;
    private int mouseX;
    private int mouseY;
    private boolean isHolding = false;
    private Thread colorThread;
    private GamePanel game;
    private Message sunAmount = new Message(34 + 40, 87 + 10, 50, 50, "50", 20);
    private int progress;
    private int progressBarWidth = 200;
    private int totalProgress;

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

        } catch (IOException e) {
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }

    //handles sun amount and game progress
    public void move() {
        sunAmount.setMessage(game.getSun() + "");
        progress = (int) (1.0 * game.levelProgressState / totalProgress * (progressBarWidth - 30)) + 30;
    }

    //checks where the mouse is
    public void mouseMoved(MouseEvent e) {
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
            } else {
                state = 0;
            }
        }
    }

    //checks where the mouse was clicked
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //depending on where it was clicked, it changes
        switch (selectedState) {
            case 0:
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
                        isHolding = true;
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
                        colorThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    sunAmount.setColorRed(true);
                                    //play sound effect here
                                    Thread.sleep(200);
                                    sunAmount.setColorRed(false);
                                    Sound.playSingleSound("Sounds\\Not Enough Sun Sound Effect - Plants VS Zombies (Free To Use).wav", 0);
                                } catch (Exception e) {

                                }
                            }
                        });
                        colorThread.start();
                    }

                }
                selectedState = 0;
                break;
            case 2:
                //if it clicked on peashooter seed slot
                if (Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))) {
                    if (game.getSun() >= 100) {
                        game.plantPeashooter(mouseX, mouseY);
                        game.changeSun(-100);
                    } else {
                        colorThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    sunAmount.setColorRed(true);
                                    //play sound effect here
                                    Thread.sleep(200);
                                    sunAmount.setColorRed(false);
                                    Sound.playSingleSound("Sounds\\Not Enough Sun Sound Effect - Plants VS Zombies (Free To Use).wav", 20);
                                } catch (Exception e) {

                                }
                            }
                        });
                        colorThread.start();
                    }

                }
                selectedState = 0;
                break;
            case 3:
            //if it clicked on potato mine seed slot
                if (Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))) {
                    if (game.getSun() >= 25) {
                        game.plantPotatoMine(mouseX, mouseY);
                        game.changeSun(-25);
                    } else {
                        colorThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    sunAmount.setColorRed(true);
                                    //play sound effect here
                                    Thread.sleep(200);
                                    sunAmount.setColorRed(false);
                                    Sound.playSingleSound("Sounds\\Not Enough Sun Sound Effect - Plants VS Zombies (Free To Use).wav", 20);
                                } catch (Exception e) {

                                }
                            }
                        });
                        colorThread.start();
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
                        colorThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    sunAmount.setColorRed(true);
                                    //play sound effect here
                                    Thread.sleep(200);
                                    sunAmount.setColorRed(false);
                                    Sound.playSingleSound("Sounds\\Not Enough Sun Sound Effect - Plants VS Zombies (Free To Use).wav", 20);
                                } catch (Exception e) {

                                }
                            }
                        });
                        colorThread.start();
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
                isHolding = false;
                break;
        }
    }

    //draws all relevent images
    public void draw(Graphics g) {
        if (inventoryImage != null) {
            g.drawImage(inventoryImage, 56, 0, null);
            g.drawImage(shovelImage, 464, 0, null);
            //draws the shovel only if its not being currently held
            if (!isHolding) {
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
            //draws shovel if it is being held on mouse
            if (isHolding) {
                g.drawImage(shovelOnlyImage, mouseX - 20, mouseY - 20, null);
            }
        }
    }
}
