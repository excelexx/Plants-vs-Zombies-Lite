//Alexander Zhang
//2025-01-17
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
    int state = 1;
    GamePanel game;
    int xOffsetEat;

    //constructor
    Plant(int col, int rw, int dur, GamePanel gme){
        super(Grid.colToX(col),Grid.rowToY(rw),110,110);
        column = col;
        row = rw;
        durability = dur;
        positionX = Grid.colToX(col);
        positionY = Grid.rowToY(row);
        game = gme;
        //make plant being planted noise
        Sound.playSingleSound("Sounds\\planted.wav", 0);
    }
    //draws plant
    public void draw(Graphics g){
    }
    //returns the row
    public int getRow(){
        return row;
    }
    //returns the column
    public int getCol(){
        return column;
    }
    //sets durability
    public void setDurability(int d){
        durability = d;
    }
    //returns the durability
    public int getDurability(){
        return durability;
    }
    //kills the plant
    public void die(){
        //make plant being eated noise (same as being planted)
        Sound.playSingleSound("Sounds\\planted.wav", 0);
    }
    //makes plant move
    public void move(){

    }
    //increments durability from plant
    public void regularEatPlant(){
        state++;
            if(state>=2){
                durability -= GamePanel.REGULAR_ZOMBIE_DAMAGE;
                state = 0;
            }
        
    }
    //returns hitbox
    public int getXEat(){
        return positionX+xOffsetEat;
    }
    //subtracts gargantuar damage from plant
    public void gargantuarEatPlant(){
        durability -= GamePanel.GARGANTUAR_DAMAGE;
    }
}
