//Alexander Zhang and Stanley Zhou
//2024-12-17
//Code for the gameframe class that defines information about the window

//imports
import java.awt.*;
import javax.swing.*;

//code for gameframe class, which uses JFrame
public class GameFrame extends JFrame {

    //creates a new GamePanel
    GamePanel panel;

    public GameFrame() {
        panel = new GamePanel(); //runs GamePanel constructor
        this.add(panel);
        this.setTitle("Plants vs Zombies Lite"); //sets title for frame
        this.setResizable(false); //frame can't change size
        this.setBackground(Color.white); //sets background to white
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
        this.pack();//makes components fit in window
        this.setVisible(true); //makes window visible to user
        this.setLocationRelativeTo(null);//set window in middle of screen
    }

}
