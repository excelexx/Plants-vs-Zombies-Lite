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
    int xOffsetEat;

    //constructor
    Plant(int col, int rw, int dur, GamePanel gme){
        super(Grid.colToX(col),Grid.rowToY(rw),110,110);
        column = col;
        row = rw;
        durability = dur;
        positionX = Grid.colToX(col);
        game = gme;
        //make plant being planted noise
        Sound.playMusic("Sounds\\Plant being Planted Sample 1 -Plants vs Zombies Sound Effect - Made with Clipchamp.wav");
    }
    public void draw(Graphics g){
    }
    public void setDurability(int d){
        durability = d;
    }
    public int getDurability(){
        return durability;
    }
    public void die(){
        game.removePlant(this);
        //make plant being eated noise (same as being planted)
        Sound.playMusic("Sounds\\Plant being Planted Sample 1 -Plants vs Zombies Sound Effect - Made with Clipchamp.wav");
    }
    public void move(){

    }
    public void regularEatPlant(){
        durability -= GamePanel.REGULAR_ZOMBIE_DAMAGE;
    }
    public int getXEat(){
        return positionX+xOffsetEat;
    }
}
