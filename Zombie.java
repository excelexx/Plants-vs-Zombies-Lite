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
    int positionX;
    int row;
    int positionY;
    int speed;
    int state = 1;
    int xOffsetCenter;
    int fps = 4;
    int xOffsetFront;
    boolean isStopped=false;
    //constructor
    Zombie(int rw, int dur, GamePanel gme){
        super(GamePanel.GAME_WIDTH,Grid.rowToY(rw),110,110);
        row = rw;
        durability = dur;
        positionX = GamePanel.GAME_WIDTH;
        positionY = Grid.rowToY(row) - 35 + (int)(Math.random() * -6 + 3);
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
        System.out.print(durability);
        durability -= GamePanel.PEA_DAMAGE;
    }
    public void move(){
        if(!isStopped){
            state++;
            if(state>=fps){
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
    public synchronized boolean isStopped(){
        return isStopped;
    }
    public String toString(){
        return isStopped+"";
    }
    public int getXPosition(){
        return positionX;
    }
    public int getYPosition(){
        return positionY;
    }
    public void potatoMineDamage(){
        durability -= GamePanel.POTATO_MINE_DAMAGE;
    }
}
