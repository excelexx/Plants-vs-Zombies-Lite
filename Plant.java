//Alexander Zhang and Stanley Zhou
//2024-12-17
//Code for the plant class that handles plants

//imports
import java.awt.Rectangle;

//code for plant class
public class Plant extends Rectangle{
    //declares all variables
    int durability;
    int damage;
    int positionX;
    int lane;
    int positionY;
    int sunCost;
    int reloadTime;

    //constructor
    Plant(int col, int ln, int dur){
        super(Grid.colToX(col),Grid.rowToY(ln),110,110);
    }
}
