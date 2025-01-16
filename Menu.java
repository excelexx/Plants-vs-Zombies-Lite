//Alexander Zhang and Stanley Zhou
//2025-01-17
//Code for the Main Menu class that handles rules and instructions

//imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

//code for menu class
public class Menu extends JPanel {

    // declares all variables
    public static BufferedImage homeDefault;
    public static BufferedImage signPlay;
    public static BufferedImage signHelp;
    public static BufferedImage signInfo;
    public static BufferedImage signQuit;
    public static BufferedImage levelsDefault;
    public static BufferedImage levelsEasy;
    public static BufferedImage levelsMedium;
    public static BufferedImage levelsHard;
    public static BufferedImage levelsClose;
    public static BufferedImage helpDefault;
    public static BufferedImage helpClose;
    public static BufferedImage infoDefault;
    public static BufferedImage infoClose;
    public static int level;
    public static int signState = 0;
    public static int mouseX;
    public static int mouseY;
    public static int mouseClickedX;
    public static int mouseClickedY;
    public static int buttonState = 0;
    public static boolean stillHovering = false;
    Thread soundThread;

    // constructor
    public Menu() {
        loadImage();
        Sound.playSingleSound(
            "Sounds\\Hover Click.wav", 0);
    }

    // ends the game
    public static void endGame() {
        buttonState = 0;
        stillHovering = false;
        signState = 0;
        mouseClickedX = -1;
        mouseClickedY = -1;
    }

    // loads all images
    public void loadImage() {
        try {
            homeDefault = ImageIO.read(getClass().getResource("/Images/homeDefaultNew.png"));
            signPlay = ImageIO.read(getClass().getResource("/Images/homePlayNew.png"));
            signHelp = ImageIO.read(getClass().getResource("/Images/homeHelpNew.png"));
            signInfo = ImageIO.read(getClass().getResource("/Images/homeInfoNew.png"));
            signQuit = ImageIO.read(getClass().getResource("/Images/homeQuitNew.png"));
            levelsDefault = ImageIO.read(getClass().getResource("/Images/levelsDefault.png"));
            levelsEasy = ImageIO.read(getClass().getResource("/Images/levelsEasy.png"));
            levelsMedium = ImageIO.read(getClass().getResource("/Images/levelsMedium.png"));
            levelsHard = ImageIO.read(getClass().getResource("/Images/levelsHard.png"));
            levelsClose = ImageIO.read(getClass().getResource("/Images/levelsClose.png"));
            helpDefault = ImageIO.read(getClass().getResource("/Images/helpDefaultNew.png"));
            helpClose = ImageIO.read(getClass().getResource("/Images/helpCloseNew.png"));
            infoDefault = ImageIO.read(getClass().getResource("/Images/infoDefault.png"));
            infoClose = ImageIO.read(getClass().getResource("/Images/infoClose.png"));
        } catch (IOException e) {
            System.out.println("Error loading images. Please check the files were saved correctly.");
        }
    }

    // called from GamePanel when any key is released
    public void keyReleased(KeyEvent e) {
    }

    // checks where the mouse was released and sets the states of the buttons
    // accordingly
    public static void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (signState == 1) {
            buttonState = 1;
            signState = 5;
        }
        if (signState == 2) {

        }
        if (signState == 9) {
            buttonState = 0;
            signState = 0;
        }
        if (signState == 6) {
            GamePanel.isRunning = true;
            GamePanel.difficulty = 1;
        }
        if (signState == 7) {
            GamePanel.isRunning = true;
            GamePanel.difficulty = 2;
        }
        if (signState == 8) {
            GamePanel.isRunning = true;
            GamePanel.difficulty = 3;
        }
        if (signState == 4) {
            System.exit(0);
        }
        if (signState == 2) {
            buttonState = 2;
            signState = 10;
        }
        if (buttonState == 2 && signState == 11) {
            buttonState = 0;
            signState = 0;
        }

        if (signState == 3) {
            buttonState = 3;
            signState = 12;
        }

        if (buttonState == 3 && signState == 13) {
            buttonState = 0;
            signState = 0;
        }
    }

    // code to get coordinates of the last mouse click
    public static void mousePressed(MouseEvent e) {
        mouseClickedX = e.getX();
        mouseClickedY = e.getY();
    }

    // checks where the mouse is and sets the state of the signs/buttons accordingly
    public static void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // changes image according to where the signs/buttons are
        switch (buttonState) {
            // code for if nothing has been pushed yet
            case 0:
                if (782 <= mouseX && mouseX <= 932 && 238 <= mouseY && mouseY <= 327) {
                    signState = 1;
                    tryPlayHoverMusic(stillHovering);
                } else if (771 <= mouseX && mouseX <= 771 + 185 && 339 <= mouseY && mouseY <= 339 + 73) {
                    signState = 2;
                    tryPlayHoverMusic(stillHovering);
                } else if (759 <= mouseX && mouseX <= 759 + 222 && 427 <= mouseY && mouseY <= 427 + 66) {
                    signState = 3;
                    tryPlayHoverMusic(stillHovering);
                } else if (786 <= mouseX && mouseX <= 786 + 169 && 512 <= mouseY && mouseY <= 512 + 67) {
                    signState = 4;
                    tryPlayHoverMusic(stillHovering);
                } else {
                    signState = 0;
                    stillHovering = false;
                }
                break;
            // code for if it is on the play page
            case 1:
                if (268 <= mouseX && mouseX <= 268 + 216 && 294 <= mouseY && mouseY <= 294 + 92) {
                    signState = 6;
                    tryPlayHoverMusic(stillHovering);
                } else if (267 <= mouseX && mouseX <= 267 + 219 && 429 <= mouseY && mouseY <= 429 + 88) {
                    signState = 7;
                    tryPlayHoverMusic(stillHovering);
                } else if (272 <= mouseX && mouseX <= 272 + 213 && 559 <= mouseY && mouseY <= 559 + 78) {
                    signState = 8;
                    tryPlayHoverMusic(stillHovering);
                } else if (954 <= mouseX && mouseX <= 954 + 54 && 684 <= mouseY && mouseY <= 684 + 54) {
                    signState = 9;
                    tryPlayHoverMusic(stillHovering);
                } else {
                    signState = 5;
                    stillHovering = false;
                }
                break;
            // code for if it is on the help page
            case 2:
                if (954 <= mouseX && mouseX <= 954 + 54 && 684 <= mouseY && mouseY <= 684 + 54) {
                    signState = 11;
                    tryPlayHoverMusic(stillHovering);
                } else {
                    signState = 10;
                    stillHovering = false;
                }
                break;
            // code for if it is on the options page
            case 3:
                if (954 <= mouseX && mouseX <= 954 + 55 && 683 <= mouseY && mouseY <= 683 + 54) {
                    signState = 13;
                    tryPlayHoverMusic(stillHovering);
                } else {
                    signState = 12;
                    stillHovering = false;
                }
                break;
        }
    }

    // plays the sound when mouse is hovering
    public static void tryPlayHoverMusic(boolean isHovering) {
        if (!stillHovering) {
            stillHovering = true;
            Sound.playSingleSound(
                    "Sounds\\hover.wav",
                    0);
        }
    }

    // draws the background depending on what the current state it
    public static void draw(Graphics g) {

        if (homeDefault != null && signPlay != null && signHelp != null && signInfo != null && signQuit != null) {
            switch (buttonState) {
                // code for if it is on the default page
                case 0:
                    switch (signState) {
                        case 0:
                            g.drawImage(homeDefault, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 1:
                            g.drawImage(signPlay, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 2:
                            g.drawImage(signHelp, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 3:
                            g.drawImage(signInfo, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 4:
                            g.drawImage(signQuit, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                    }
                    break;
                // code for if it is on the play page
                case 1:
                    switch (signState) {
                        case 5:
                            g.drawImage(levelsDefault, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 6:
                            g.drawImage(levelsEasy, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 7:
                            g.drawImage(levelsMedium, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 8:
                            g.drawImage(levelsHard, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 9:
                            g.drawImage(levelsClose, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                    }
                    break;
                // code for if it is on the help page
                case 2:
                    switch (signState) {
                        case 10:
                            g.drawImage(helpDefault, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 11:
                            g.drawImage(helpClose, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                    }
                    break;
                // code for if it is on the options page
                case 3:
                    switch (signState) {
                        case 12:
                            g.drawImage(infoDefault, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                        case 13:
                            g.drawImage(infoClose, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
                            break;
                    }
                    break;
            }
        }

    }

}
