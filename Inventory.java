import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Inventory{
    int positionX;
    BufferedImage inventoryImage;
    BufferedImage zombieHead;
    BufferedImage shovelImage;
    BufferedImage shovelOnlyImage;
    int positionY;
    int state;
    int selectedState;
    int mouseX;
    int mouseY;
    boolean isHolding = false;
    Thread colorThread;
    GamePanel game;
    Message sunAmount = new Message(34+40,  87+10, 50, 50, "50", 20);
    int progress;
    int progressBarWidth=200;
    int totalProgress;

    Inventory(GamePanel gme){
        game = gme;
        loadImage();
        totalProgress = game.zombieSpawnList[game.getDifficulty()][0].length;
    }

    public void loadImage(){
        try{
            inventoryImage = ImageIO.read(getClass().getResource("/Images/inventoryImage.png"));
            zombieHead = ImageIO.read(getClass().getResource("/Images/zombieHeadt1.png"));
            shovelImage = ImageIO.read(getClass().getResource("/Images/shovelFramet1.png"));
            shovelOnlyImage = ImageIO.read(getClass().getResource("/Images/shovelt1.png"));

        }
        catch(IOException e){
            System.out.println("Error loading image files. Please check all files are saved properly.");
        }
    }
    public void move(){
        sunAmount.setMessage(game.getSun()+"");
        progress = (int)(1.0*game.levelProgressState/totalProgress*(progressBarWidth-30))+30;
    }

    public void mouseMoved(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
        if(selectedState == 0){
            if(56+107<mouseX && mouseX< 56+107+71 && 8<mouseY && mouseY<108){
                state = 1;
            }
            else if(56+181<mouseX && mouseX<56+170+71&& 8<mouseY && mouseY<108){
                state = 2;
            }
            else if(56+245<mouseX && mouseX<56+253+71 && 8<mouseY && mouseY<108){
                state = 3;
            }
            else if(56+325 < mouseX && mouseX<56+325+71 && 8<mouseY && mouseY<108){
                state = 4;
            }
            else if(465<mouseX && mouseX <465+107 && 8 < mouseY && mouseY<108){
                state = 5;
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
            if(Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))){
                if(game.getSun()>=50){
                game.plantSunflower(mouseX,mouseY);
                game.changeSun(-50);
                }
                else{
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
                if(Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))){
                    if(game.getSun()>=100){
                    game.plantPeashooter(mouseX,mouseY);
                    game.changeSun(-100);
                    }
                    else{
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
                if(Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))){
                    if(game.getSun()>=25){
                    game.plantPotatoMine(mouseX,mouseY);
                    game.changeSun(-25);
                    }
                    else{
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
                if(Grid.isInGame(mouseX, mouseY) && !game.isPlant(Grid.yToRow(mouseY), Grid.xToCol(mouseX))){
                    if(game.getSun()>=50){
                    game.plantWalnut(mouseX,mouseY);
                    game.changeSun(-50);
                    }
                    else{
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
                    if(Grid.isInGame(mouseX, mouseY)){
                        game.removePlant(Grid.yToRow(mouseY),Grid.xToCol(mouseX));
                    }
                    selectedState = 0;
                    isHolding = false;
                    break;
        }      
    }

    public void draw(Graphics g){
        if(inventoryImage != null){
            g.drawImage(inventoryImage,56,0, null);
            g.drawImage(shovelImage, 464, 0, null);
            if(!isHolding){
                g.drawImage(shovelOnlyImage, 480,10,null);
            }
            sunAmount.draw(g);
            
            g.setColor(new Color(139, 69, 19)); 
            g.fillRoundRect(845, 750, progressBarWidth + 10, 35, 15, 15);
            g.setColor(Color.BLACK);
            g.fillRect(850, 755, progressBarWidth, 25);  
            g.setColor(new Color(75, 190, 68));
            g.fillRect(850, 755, progress - 10, 25); 

            g.drawImage(zombieHead, 820+progress,745,null);
            if(isHolding){
                g.drawImage(shovelOnlyImage, mouseX-20, mouseY-20, null);
            }
        }
    }
}
