import java.util.Arrays;
import java.util.Scanner;

public class kvadranter {
    public static void main(String[] args) {

        System.out.println(raknaKvadrat());
        System.err.println("But as we can see this is wrooong");

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
        int[] posXY = translateToPos(pos);
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
            return translateToNumber(posXY);
        }

    }

    private static String translateToNumber(int[] posXY) {

        return null;
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
                    colMin = originalSize - currentSize;
                    colMax = (currentSize / 2);
                    rowMin = (currentSize / 2) + 1;
                    rowMax = currentSize;

                    currentSize /= 2;
                    break;

                case 2:
                    colMin = (currentSize / 2) + 1;
                    colMax = currentSize;
                    rowMin = (currentSize / 2) + 1;
                    rowMax = currentSize;

                    currentSize /= 2;
                    break;

                case 3:
                    colMin = originalSize - currentSize;
                    colMax = (currentSize / 2);
                    rowMin = originalSize - currentSize;
                    rowMax = (currentSize / 2);

                    currentSize /= 2;
                    break;

                case 4:
                    colMin = (currentSize / 2) + 1;
                    colMax = currentSize;
                    rowMin = originalSize - currentSize;
                    rowMax = (currentSize / 2);
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
