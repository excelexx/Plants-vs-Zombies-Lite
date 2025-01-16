//Alexander Zhang and Stanley Zhou
//2025-01-17
//Code for the Grid class that converts grid coordinates to x and y coordinates

public class Grid {
    //Returns the y coordinate based on the row number
    public static int rowToY(int ln){
        switch(ln){
            case 1:
                return 130;
            case 2:
                return 251;
            case 3:
                return 385;
            case 4:
                return 517;
            case 5:
                return 644;
        }
        return -1;
    }
    
    //Returns the row number based on y coordinate
    public static int yToRow(int y){
        if(130 <= y && y <= 250){
            return 1;
        }
        else if(251<= y && y<=384){
            return 2;
        }
        else if(385<= y && y<= 516){
            return 3;
        }
        else if(517 <= y && y<=644){
            return 4;
        }
        else if(645 <= y && y<= 773){
            return 5;
        }
    return -1;
    }

    //Returns the x coordinate based on column number
    public static int colToX(int col){
        switch(col){
            case 1:
                return 56;
            case 2:
                return 162;
            case 3:
                return 265;
            case 4:
                return 383;
            case 5:
                return 492;
            case 6:
                return 604;
            case 7:
                return 711;
            case 8:
                return 815;
            case 9:
                return 925;
        }
        return -1;
    }

    //Returns the column number based on x coordinate
    public static int xToCol(int x){
            if (x >= 56 && x <= 161) {
                return 1;
            } else if (x >= 162 && x <= 264) {
                return 2;
            } else if (x >= 265 && x <= 382) {
                return 3;
            } else if (x >= 383 && x <= 491) {
                return 4;
            } else if (x >= 492 && x <= 603) {
                return 5;
            } else if (x >= 604 && x <= 710) {
                return 6;
            } else if (x >= 711 && x <= 814) {
                return 7;
            } else if (x >= 815 && x <= 924) {
                return 8;
            } else if (x >= 925 && x<= 1045) {
                return 9;
            }
            return -1;
    }   
    //checks if a coordinate is on the grid
    public static boolean isInGame(int x, int y){
        if(56<=x && x<=1045 && y>=130 && y<=773){
            return true;
        }
        return false;
    }
}
