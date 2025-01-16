//Alexander Zhang and Stanley Zhou
//2025-01-17
//Code for playing sounds for soundtrack

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
    GamePanel game;
    boolean isStopped=false;

    //constructor
    Zombie(int rw, int dur, GamePanel gme){
        super(GamePanel.GAME_WIDTH,Grid.rowToY(rw),110,110);
        row = rw;
        durability = dur;
        game = gme;
        positionX = GamePanel.GAME_WIDTH;
        positionY = Grid.rowToY(row) - 35 + (int)(Math.random() * -6 + 3);
    }
    //draws zombie
    public void draw(Graphics g){
    }
    //increments durability
    public void incrementDurability(int d){
        durability -= d;
    }
    //kills zombie
    public void die(){
    }
    //returns durability
    public int getDurability(){
        return durability;
    }
    //damages zombie
    public void peaDamage(){
        durability -= game.getPeaDamage();
    }
    //moves zombie
    public void move(){
        if(!isStopped){
            state++;
            if(state>=fps){
                positionX-=speed;
                state = 0;
            }
        }
    }
    //returns hitbox
    public int getXEat(){
        return positionX+xOffsetCenter;
    }
    //returns hitbox
    public int getXEating(){
        return positionX+xOffsetFront;
    }
    //stops zombie
    public void stop(){
        isStopped = true;
    }
    //makes zombie go
    public void go(){
        isStopped= false;
    }
    //returns row
    public int getRow(){
        return row;
    }
    //returns if the zombie is stopped
    public synchronized boolean isStopped(){
        return isStopped;
    }
    //returns x coordinate
    public int getXPosition(){
        return positionX;
    }
    //returns y coordinate
    public int getYPosition(){
        return positionY;
    }
    //checks durability
    public void potatoMineDamage(){
        durability -= GamePanel.POTATO_MINE_DAMAGE;
    }
}
