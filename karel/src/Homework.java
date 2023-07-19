import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {
    int length ,width,maxSide,minSide,SideLength,boxsize=0,steps;

    public void CountlengthNwidth(){
        //reset the number of steps, length and width
        steps=0;
        length=1;width=1;
        while(!frontIsBlocked()){
            width++;
            moves(1);
        }
        turnLeft();
        while(!frontIsBlocked()){
            length++;
            moves(1);
        }

        //used if the length > width to assumed that we flip map
        maxSide=Math.max(length,width);
        minSide=Math.min(length,width);
        //side length for outline of chambers
        SideLength = Math.min(length, width) - 2;
        //count the inner chamber side length
        if(minSide%2==0){
            boxsize=(minSide-6)/2;
        }
        else{
            boxsize=(minSide-5)/2;
        }
    }

    public void MoveToStartPoint(){
        //move to middle of right side
        if(width>=length){
            turnAround();
            int x=0;
            if(length%2==0)x=1;
            for (int i=0;i<length/2-x;i++){
                moves(1);
            }
            turnRight();
        }
        //move to middle of top side
        else{
            int x=0;
            if(width%2==0)x=1;
            turnLeft();
            for (int i=0;i<width/2-x;i++){
                moves(1);
            }
            if(width%2==0)moves(1);
            turnLeft();
        }
    }

    //draw the start line
    public void StartLine(){
        int SideLength=Math.min(length,width)-2;
        if(maxSide%2==0){
            SideLength++;
        }
        //draw 1 line
        if(minSide%2==1){
            movesWithBeepers((maxSide-SideLength)/2);
            moves(1);
        }
        //draw double lines
        else{
            drawDoubleLine((maxSide-SideLength)/2);
        }
        turnLeft();
    }
    //moves from up to down the go from right then go from down to up
    public void drawDoubleLine(int x){
        boolean flag =true;
        for (int i=0;i<=x;i++){
            if(flag) {
                putBeeper();turnLeft();moves(1);putBeeper();turnRight();moves(1);
                flag=false;
                if(frontIsBlocked()){
                    break;
                }
            }
            else {
                putBeeper();turnRight();moves(1);putBeeper();turnLeft();moves(1);
                flag=true;
                if(frontIsBlocked()){
                    break;
                }
            }
        }
    }
    //moves from down to up the go  left then go from down to up
    public void drawDoubleLineFromDown(int x){
        boolean flag =true;
        for (int i=0;i<=x;i++){
            if(flag) {
                putBeeper();turnRight();moves(1);putBeeper();turnLeft();moves(1);
                flag=false;
                if(frontIsBlocked()){
                    break;
                }
            }
            else {
                putBeeper();turnLeft();moves(1);putBeeper();turnRight();moves(1);
                flag=true;
                if(frontIsBlocked()){
                    break;
                }
            }
        }
    }
    //put beepers while moves
    public void movesWithBeepers(int x){
        for(int i=0;i<x-1;i++){
            putBeeper();
            moves(1);
        }
        putBeeper();
    }
    //move number of times
    public void moves(int x){
        for(int i=0;i<x;i++){
            move();
            steps++;
        }
    }
    //draw the one or two beepers that sticks to top and bottom sides
    public void DrawSmallPart(){
        if(maxSide%2==0){
            turnLeft();moves(1);
            putBeeper();
            turnRight();moves(1);
            putBeeper();
            turnRight();moves(1);turnLeft();
        }
        else{
            turnLeft();moves(1);
            putBeeper();
            turnAround();moves(1);turnLeft();moves(1);
        }
    }

    public void DrawChamberOutLine() {
        //drow the lower mid of right side
        int x=0;
        if(minSide%2!=0){
            x=1;
        }
        movesWithBeepers(SideLength/2+x);

        turnRight();moves(1);

        //drow the  lower side
        DrowUpperOrLowerLine();

        //draw the left side
        movesWithBeepers(SideLength-1);
        turnRight();
        moves(1);

        //drow the upper side
        DrowUpperOrLowerLine();

        //drow the right side
        x=0;
        if(minSide%2!=0){
            x=1;
        }
        movesWithBeepers(SideLength-(SideLength/2+x)-1);
        if(minSide%2!=0) {
            move();
        }
        turnRight();
        move();
    }

    public void DrowUpperOrLowerLine(){
        //odd odd
        if(maxSide%2==1 && minSide%2==1){
            movesWithBeepers(SideLength/2);
            DrawSmallPart();
            movesWithBeepers(SideLength/2);
        }

        //odd even
        if(maxSide%2==1 && minSide%2==0){
            movesWithBeepers((SideLength-1)/2);
            DrawSmallPart();
            movesWithBeepers((SideLength-1)/2);
        }

        //even odd
        if(maxSide%2==0 && minSide%2==1){
            movesWithBeepers(SideLength/2);
            DrawSmallPart();
            movesWithBeepers(SideLength/2+1);
        }

        //even even
        if(maxSide%2==0 && minSide%2==0){
            movesWithBeepers((SideLength-1)/2);
            DrawSmallPart();
            movesWithBeepers(SideLength/2);
        }

        turnRight();
        move();
    }
    //draw the right middle side of horizontal line
    public void DrawToCenterPoint(){
        if(minSide%2!=0){
            movesWithBeepers(boxsize);
            moves(1);
        }
        else{
            drawDoubleLine(boxsize-1);
        }
    }
    //put beepers in line and return to same point
    public void PutAndReturn(int x){
        moves(1);
        movesWithBeepers(x);
        turnAround();
        moves(x);
    }

    public void DrowVirticalLine() {
        if(maxSide%2==1){
            turnRight();
            int x=0;
            if(boxsize%2!=0 && minSide%2==0){
                x=1;
            }

            PutAndReturn(boxsize+x);

            if(minSide%2==0){
                putBeeper();
                moves(1);
                putBeeper();
            }
            PutAndReturn(boxsize-x);
            turnLeft();
        }

        else{
            int x=0;
            if(minSide%2==1){
                x=1;
            }

            if(boxsize%2==0){
                turnRight();
                movesWithBeepers(boxsize+1);
                turnLeft();moves(1);turnLeft();
                movesWithBeepers(boxsize*2+2-x);
                turnLeft();moves(1);turnLeft();
                movesWithBeepers(boxsize+1-x);
            }
            else{
                turnLeft();
                movesWithBeepers(boxsize+1);
                turnRight();moves(1);turnRight();
                movesWithBeepers(boxsize*2+2-x);
                turnRight();moves(1);turnRight();
                movesWithBeepers(boxsize-x);
                if(boxsize%2==1){
                    move();
                    putBeeper();
                }
                moves(1);turnRight();moves(2);
            }
        }
    }
    //draw the rest of horizontal line
    public void DrawHorizontalLine(){
        //odd odd
        if(maxSide%2==1 && minSide%2==1){
            movesWithBeepers(boxsize+1);
            moves(2);
            movesWithBeepers((maxSide-SideLength)/2);
        }

        //even odd
        else if( maxSide%2==0 && minSide%2==1){
            //if box size even
            if(boxsize%2==0){
                moves(1);turnLeft();moves(2);
            }
            movesWithBeepers(boxsize);
            moves(2);
            movesWithBeepers((maxSide-SideLength)/2);
        }

        //odd even
        else if(maxSide%2==1 && minSide%2==0 ){
            //box size even
            if(boxsize%2==0){
                moves(1);
                drawDoubleLineFromDown(boxsize-1);
                moves(1);
                drawDoubleLineFromDown((maxSide-SideLength)/2-1);
                putBeeper();
                turnRight();moves(1);
                putBeeper();
            }
            //box size odd
            else{
                turnRight();moves(1);turnLeft();moves(1);
                drawDoubleLineFromDown(boxsize-1);
                moves(1);
                drawDoubleLine((maxSide-SideLength)/2-1);
                putBeeper();
                turnLeft();moves(1);
                putBeeper();
            }
        }
        //even even
        else if(maxSide%2==0 && minSide%2==0){
            //box size even
            if(boxsize%2==0){
                turnLeft();
                moves(2);
                drawDoubleLineFromDown(boxsize-1);
                move();
                if(length!=width){
                    drawDoubleLineFromDown((maxSide-SideLength)/2-1);
                }
                putBeeper();
                turnRight();moves(1);
                putBeeper();
            }
            //box size odd
            else{
                drawDoubleLineFromDown(boxsize-1);
                move();
                if(length!=width){
                    drawDoubleLine((maxSide-SideLength)/2-1);
                }
                putBeeper();
                turnLeft();moves(1);
                putBeeper();
            }
        }
    }
    public void run() {
        //count width and length
        CountlengthNwidth();
        if(width<7 || length<7){
            showErrorMessage("I cant draw on this map , you need at least 7x7 map");

        } else {
            //move To start point
            MoveToStartPoint();

            //draw Start line
            StartLine();

            //draw chamber outline
            DrawChamberOutLine();

            //draw to center point
            DrawToCenterPoint();

            //draw virtical line
            DrowVirticalLine();

            //draw horizontal line
            DrawHorizontalLine();
            System.out.println("the number of steps = " + steps);
        }
    }
}