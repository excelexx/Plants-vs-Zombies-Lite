import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class Inventory{
    int positionX;
    BufferedImage inventoryImage;
    BufferedImage sunflowerSeedSlot;
    BufferedImage peashooterSeedSlot;
    BufferedImage potatoMineSeedSlot;
    BufferedImage walnutSeedSlot;
    int positionY;
    int state;
    int selectedState;
    int mouseX;
    int mouseY;
    boolean isHolding = false;
    GamePanel game;
    Message sunAmount = new Message(27+56,  87+23, 50, 50, "50", 30);

    Inventory(GamePanel gme){
        game = gme;
        loadImage();
    }

    public void loadImage(){
        try{
            inventoryImage = ImageIO.read(getClass().getResource("/Images/inventoryImage.png"));
            sunflowerSeedSlot = ImageIO.read(getClass().getResource("/Images/sunflowerSeedSlot.png"));
            peashooterSeedSlot = ImageIO.read(getClass().getResource("/Images/peashooterSeedSlot.png"));
            potatoMineSeedSlot = ImageIO.read(getClass().getResource("/Images/potatoMineSeedSlot.png"));
            walnutSeedSlot = ImageIO.read(getClass().getResource("/Images/walnutSeedSlot.png"));
        }
        catch(IOException e){
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }
    public void move(){
        sunAmount.setMessage(game.getSun()+"");
    }

    public void mouseMoved(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
        if(selectedState == 0){
            if(56+107<mouseX && mouseX< 56+107+71 && 8<mouseY && mouseY<108){
                state = 1;
            }
            else if(56+181<mouseX && mouseX<56+181+71&& 8<mouseY && mouseY<108){
                state = 2;
            }
            else if(56+253<mouseX && mouseX<56+253+71 && 8<mouseY && mouseY<108){
                state = 3;
            }
            else if(56+325 < mouseX && mouseX<56+325+71 && 8<mouseY && mouseY<108){
                state = 4;
            }
            else{
                state = 0;
            }
        }
    }

    public void mouseReleased(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
        switch(selectedState){
            case 0:
                switch(state){
                    case 0:
                        selectedState = 0;
                        break;
                    case 1:
                        isHolding = true;
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
                }
                break;
            case 1:
            selectedState = 0;
                break;
            case 2:
                if(Grid.isInGame(mouseX, mouseY)){
                    game.plantPeashooter(mouseX,mouseY);
                    game.changeSun(-100);
                }
                selectedState = 0;
                break;
            case 3:
                selectedState = 0;
                break;
            case 4:
                selectedState = 0;
                break;

        }      
    }

    public void draw(Graphics g){
        if(inventoryImage != null){
            g.drawImage(inventoryImage,56,0, null);
            g.drawImage(sunflowerSeedSlot,56+108,8, null);
            g.drawImage(peashooterSeedSlot,56+181,8, null);
            g.drawImage(potatoMineSeedSlot,56+253,8, null);
            g.drawImage(walnutSeedSlot,56+325,8, null);
            sunAmount.draw(g);
        }
    }
}
