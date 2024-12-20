//Alexander Zhang
//2024-12-03
//Code for the plant class that handles plants

//imports
import java.awt.Graphics;
import java.awt.Rectangle;

//code for plant class
public class Plant extends Rectangle{
    //declares all variables
    int durability;
    int damage;
    int positionX;
    int row;
    int column;
    int positionY;
    int sunCost;
    int reloadTime;
    GamePanel game;

    //constructor
    Plant(int col, int rw, int dur, GamePanel gme){
        super(Grid.colToX(col),Grid.rowToY(rw),110,110);
        column = col;
        row = rw;
        durability = dur;
        game = gme;
    }
    public void draw(Graphics g){
    }
    public void setDurability(int d){
        durability = d;
    }
    public void die(){
        game.removePlant(this);
    }
    public void move(){

    }
}
