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
        Sound.playSingleSound("Sounds\\Plant being Planted Sample 1 -Plants vs Zombies Sound Effect - Made with Clipchamp.wav", 0);
    }
    public void draw(Graphics g){
    }
    public int getRow(){
        return row;
    }
    public void setDurability(int d){
        durability = d;
    }
    public int getDurability(){
        return durability;
    }
    public void die(){
        //make plant being eated noise (same as being planted)
        Sound.playSingleSound("Sounds\\Plant being Planted Sample 1 -Plants vs Zombies Sound Effect - Made with Clipchamp.wav", 0);
    }
    public void move(){

    }
    public void regularEatPlant(){
        state++;
            if(state>=5){
                durability -= GamePanel.REGULAR_ZOMBIE_DAMAGE;
                state = 0;
            }
        
    }
    public int getXEat(){
        return positionX+xOffsetEat;
    }
}
