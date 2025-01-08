//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for the message class that draws text for other classes

//imports
import java.awt.*;
//code for message class

public class Message extends Rectangle {

    //declares all variables
    public int number;
    public String message;
    public int xPos;
    public int yPos;
    public int size;
    boolean isRed;

    //constructor which sets initial parameters of message
    public Message(int x, int y, int w, int h, String s, int si) {
        super(x, y, w, h);
        message = s;
        xPos = x;
        yPos = y;
        size = si;
    }

    //sets position of message
    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    //sets message itself
    public void setMessage(String s) {
        message = s;
    }

    //draws message
    public void draw(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, size));
        if(isRed){
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.BLACK);
        }
        g.drawString(message, xPos, yPos);
    }

    //sets color to red
    public void setColorRed(boolean t){
        isRed = t;
    }
}
