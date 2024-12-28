//Alexander Zhang
//2024-12-03
//Code for the plant class that handles plants

//imports
import java.awt.Graphics;
import java.awt.Rectangle;

//code for plant class
public class Zombie extends Rectangle{
    //declares all variables
    int durability;
    int damage;
    int positionX;
    int row;
    int column;
    int positionY;
    GamePanel game;
    int speed;
    int state = 1;
    int xOffsetCenter;
    int xOffsetFront;
    boolean isStopped=false;
    //constructor
    Zombie(int rw, int dur, GamePanel gme){
        super(GamePanel.GAME_WIDTH,Grid.rowToY(rw),110,110);
        row = rw;
        durability = dur;
        game = gme;
        positionX = GamePanel.GAME_WIDTH;
    }
    public void draw(Graphics g){
    }
    public void incrementDurability(int d){
        durability -= d;
    }
    public void die(){
    }
    public int getDurability(){
        return durability;
    }
    public void peaDamage(){
        durability -= GamePanel.PEA_DAMAGE;
    }
    public void move(){
        if(!isStopped){
            state++;
            if(state>=2){
                positionX-=speed;
                state = 0;
            }
        }
    }
    public int getXEat(){
        return positionX+xOffsetCenter;
    }
    public int getXEating(){
        return positionX+xOffsetFront;
    }
    public void stop(){
        isStopped = true;
    }
    public void go(){
        isStopped= false;
    }
    public int getRow(){
        return row;
    }
    public boolean isStopped(){
        return isStopped;
    }
}
