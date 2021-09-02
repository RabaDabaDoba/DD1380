import java.util.Arrays;
import java.util.Scanner;

public class kvadranter {
    public static void main(String[] args) {

        //System.out.println(raknaKvadrat());

        //int[] pos = {3,3};
        //System.err.println(translateToNumber(pos, 3));
        //System.err.println("and now we go back:");


        //pos = translateToPos2(pos);
        //System.err.println(pos[0] + " " + pos[1]);

        raknaKvadrater();
    }

    private static void raknaKvadrater(){
        int [] s = {1,4,1}; //From a string
        int [] pos = translateToPos(s);
        System.err.println(pos[0] + " " + pos[1]);
        System.err.println(translateToNumber(new int[]{2,5}, s.length));
    }

    private static String raknaKvadrat() {
        // First we read the two lines of data
        Scanner in = new Scanner(System.in);
        String cell = in.nextLine();
        int x = in.nextInt();
        int y = in.nextInt();

        System.err.println(cell + " " + x + " " + y);

        // Now we split the cell to figure out how big the paper is:
        String[] posString = cell.split("\\.");
        int[] pos = new int[posString.length];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = Integer.parseInt(posString[i]);
        }
        System.err.println("Size of posString; " + posString.length);
        int size = pos.length;

        // Now we create the 2D matrix which will represent the paper;
        // To get the size of the paper we take 2 to the power of the size for both
        // dimensions
        size = (int) Math.pow(2, size);
        int[][] paper = new int[size][size];

        // Now we have to find a pattern between the way our matrix works and the cells
        // on the paper.
        // Since the x and y we are given can be just added into the values of the
        // matrix.
        System.err.println("This is the number:");
        for (int i : pos) {
            System.err.print(i + ".");
        }
        System.err.println("\n");
        int[] posXY = translateToPos2(pos);
        System.err.println("Translates into");
        for (int i : posXY) {
            System.err.print(i + " ");
        }
        System.err.println("\n");

        // Now we walk!

        posXY[0] -= x;
        posXY[1] += y;
        System.err.println("Now we jump " + x + "rows and " + y + " columns");
        System.err.println(posXY[0] + ", " + posXY[1]);
        if (posXY[0] >= size || posXY[0] < 0 || posXY[1] >= size || posXY[1] < 0) {
            // Outside
            // System.err.println(posXY[0] + ", " + posXY[1]);
            return "outside";
        } else {
            // Now we convert back
            return translateToNumber(posXY, size);
        }

    }

    private static String translateToNumber(int[] posXY, int size) {
        // Given a postion (x,y) we should convert it to a number
        // Ex 4³, so a 8x8 = 64 cells. Given cell at [5][5] we should get a number with
        // 3 digits, being 2.3.2
        /*
         * What we do is that we break down each array by only looking on the values.
         * Lets look at it[X][Y] If X is larger then 2^n /2 we go right, otherwise left.
         * If Y is larger then 2^n /2 we go down, else up. This we do till we have one
         * cell left in a recursive way.
         */
        String returnNumber = "";
        int row = posXY[0];
        int col = posXY[1];

        int currentSize = (int) Math.pow(2, size); // Length of the sides
        System.err.println("Translating row: " + row + ", col: " + col + ", with currentSize: " + currentSize);
        if (row + 1 > currentSize / 2 && col + 1 <= currentSize / 2) {
            // This means we're in section 1
            posXY[0] = row - currentSize / 2;
            posXY[1] = col;
            returnNumber = "1";
        } else if (row + 1 > currentSize / 2 && col + 1 > currentSize / 2) {
            // This means we're in section 2
            posXY[0] = row - currentSize / 2;
            posXY[1] = col - currentSize / 2;
            returnNumber = "2";
        } else if (row + 1 <= currentSize / 2 && col + 1 <= currentSize / 2) {
            // This means we're in section 3
            posXY[0] = row;
            posXY[1] = col;
            returnNumber = "3";
        } else if (row + 1 <= currentSize / 2 && col + 1 > currentSize / 2) {
            // This means we're in section 4
            posXY[0] = row;
            posXY[1] = col - currentSize / 2;
            returnNumber = "4";
        } else {
            System.err.println("NO");
        }

        if (size == 1) {
            return returnNumber;
        } else {
            return returnNumber + "." + translateToNumber(posXY, size - 1);
        }

    }

    private static int[] translateToPos2(int[] number) {

        
        int rowMax = (int) Math.pow(2, number.length);
        int rowMin = 1;
        int colMax = (int) Math.pow(2, number.length);
        int colMin = 1;
        int os = (int)Math.pow(2, number.length);
        for (int i = 0; i < number.length; i++) {
            int currentNumber = number[i];
            int currentSize = (int)Math.pow(2, number.length - i);
            switch (currentNumber) {
                case 1:
                    //Then we know that it is in the bottom left part
                    //This means: row > currentSize/2, col <= currentSize/2
                

                    if(rowMin < 1 + currentSize/2)rowMin = 1 + currentSize/2;
                    if(colMax > currentSize/2)colMax = currentSize/2;
                    break;
    
                case 2:
                    if(rowMin < 1 + currentSize/2)rowMin = 1 + currentSize/2;
                    if(colMin < 1 + currentSize/2)colMin = 1 + currentSize/2;
                    break;
                case 3:
                    if(rowMax > currentSize/2) rowMax = currentSize/2;
                    if(colMax > currentSize/2) colMax = currentSize/2;
                    break;
                case 4:
                    
                    if(rowMax > currentSize/2)rowMax = currentSize/2;
                    if(colMin < 1 + currentSize/2) colMin = 1 + currentSize/2;
                    //colMin = 1 + currentSize/2;
                    break;
    
                default:
                    System.err.println("Should not come here");
                    break;
            }

            System.err.println("After calc for number: " + currentNumber + "minmax values, rowMin: " + rowMin + ", rowMax: " + rowMax + ", colMin: " + colMin
                    + ", colMax: " + colMax);
        }

        int [] pos = new int [2];
        pos[0] = rowMin-1;
        pos[1] = colMin -1;
        return pos;
    }

    private static int[] translateToPos(int[] pos) {
        // Given a name in format "x.y.z ... ", it returns a position (x,y) in the 2D
        // matrix

        // What we will do is to divied the paper based on the number we're given.
        // We always know the position of 1-4 in repsect to the current quadrant. Hence
        // we can
        // change our upper and lower boundaries for the rows and columns
        int[] posXY = new int[2];
        int rowMax = (int) Math.pow(2, pos.length);
        int rowMin = 1;
        int colMax = (int) Math.pow(2, pos.length);
        int colMin = 1;
        int currentSize = (int) Math.pow(2, pos.length);
        int originalSize = currentSize;
        System.err.println("Translating to Pos from number, with pos length: " + pos.length + ", and currentSize of "
                + currentSize);
        System.err.println("With minmax values, rowMin: " + rowMin + ", rowMax: " + rowMax + ", colMin: " + colMin
                + ", colMax: " + colMax);
        for (int i = 0; i < pos.length; i++) {
            System.err.println("Current itteration: " + i + ", with number: " + pos[i]);

            int currentNumber = pos[i];

            switch (currentNumber) {
                // Each case represent each quadrant, i.e. the number we are given
                case 1:
                    colMin = Math.max(colMin, originalSize - currentSize);
                    colMax = Math.max(colMax, (currentSize / 2));
                    rowMin = Math.max(rowMin, (currentSize / 2) + 1);
                    rowMax = Math.max(rowMax, currentSize);

                    currentSize /= 2;
                    break;

                case 2:
                    colMin = Math.max(colMin, (currentSize / 2) + 1);
                    colMax = Math.min(colMax, currentSize);
                    rowMin = Math.max(rowMin, (currentSize / 2) + 1);
                    rowMax = Math.min(rowMax, currentSize);

                    currentSize /= 2;
                    break;

                case 3:
                    colMin = Math.max(colMin, originalSize - currentSize);
                    colMax = Math.min(colMax, currentSize / 2);
                    rowMin = Math.max(rowMin, originalSize - currentSize);
                    rowMax = Math.min(rowMax, (currentSize / 2));

                    currentSize /= 2;
                    break;

                case 4:
                    colMin = Math.max(colMin, (currentSize / 2) + 1);
                    colMax = Math.min(colMax, currentSize);
                    rowMin = Math.max(rowMin, originalSize - currentSize);
                    rowMax = Math.min(rowMax, (currentSize / 2));
                    currentSize /= 2;
                    break;

                default:
                    System.err.println("Fail switch: translate to pos");
                    break;
            }
            System.err.println("With minmax values, rowMin: " + rowMin + ", rowMax: " + rowMax + ", colMin: " + colMin
                    + ", colMax: " + colMax);

        }
        System.err.println("Ending with minmax values, rowMin: " + rowMin + ", rowMax: " + rowMax + ", colMin: "
                + colMin + ", colMax: " + colMax);
        posXY[0] = Math.max(rowMax, rowMin) - 1;
        posXY[1] = Math.max(colMax, colMin) - 1;
        return posXY;
    }
}
