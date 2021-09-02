import java.util.Scanner;

public class ordTillTalFloat {
    public static void main(String[] args) {

        System.out.println(ordTillTal_3());

    }
    private static long ordTillTal_3() {

        final Scanner in = new Scanner(System.in, "utf-8");

        String number = in.nextLine();
        String[] numbers_s = number.split(" ");

        long[] numbers = new long[numbers_s.length+1];
        for (int i = 0; i < numbers_s.length; i++) {
            numbers[i] = parser(numbers_s[i]);
        }
        //Special case
        numbers[numbers_s.length] = 0;
        printArray(numbers_s);
        printArray(numbers);

        long total = 0;

        long[] tempArray = new long[4];
        // This array will be filled with the indicies of the numbers before b/m/k
        int tempIndex;
        // We try to locate bil,mil,k and rest. Then we can sum then up after finding
        // its multiplier.
        // 0 in this case is for the special case
        long[] quantities = { 1000000000, 1000000, 1000,0};

        for (int i = 0; i < quantities.length; i++) {
            tempArray = new long[4];
            System.err.println("at: " + i);
            if ((tempIndex = (int)indexOf(quantities[i], numbers)) != -1) {
                // If quantity exists
                System.err.println("found quantity " + quantities[i]);
                tempArray[3] = tempIndex - 1;
                tempArray[2] = tempIndex - 2;
                tempArray[1] = tempIndex - 3;
                tempArray[0] = tempIndex - 4;

                // Now we send this array to an controller which also returns the multiplier for
                // this quantity
                long multiplier = controllArray(tempArray, quantities[i], numbers, quantities);
                if (quantities[i] == 0) {
                    total += multiplier * 1;
                }else{
                    total += multiplier * quantities[i];
                }
                System.err.println("itteration: " + i + ", for quantity " + quantities[i] + ", we got mul = "
                        + multiplier + ", adding tot a total of " + total);
            } else {
                // No multiplying, so nothing gets added
                System.err.println("Nothing multipled for: " + quantities[i]);
            }
        }

        return total;

    }

    private static void printArray(long[] array) {
        for (int i = 0; i < array.length; i++) {
            System.err.print(array[i] + " ");
        }
        System.err.println();
    }
    private static long controllArray(long[] tempArray, long i, long[] numbers, long[] quantities) {
        System.err.println("Before controllArray with i = " + i);
        printArray(tempArray);
        
        // Now we go through this array and we want make sure that there are no negative
        // numbers,
        // but also that these 4 numbers are not larger than the number we're finding
        // the multiplier for
        // Ex: We are looking for the multiplier for millions, but tempArray as [1,
        // 1000000000, 70, 3].
        // Here we dont want anything that is to the left of 70.

    
        boolean zero = false;


        for (int j = tempArray.length - 1; j >= 0; j--) {
            System.err.println("j: " + j);
            if (!zero) {
                System.err.println("Havent found stop yet");
                if ((Math.signum(tempArray[j]) == -1 || (numbers[(int)tempArray[j]] > i && containsIn(numbers[(int)tempArray[j]], quantities)))) {
                    // If we have a negative number or larger number as mentioned above
                    // Then we want to stop and just keep what we've itterated through previously.
                    System.err.println("We found the stop at: " + j);
                    zero = true;
                    tempArray[j] = -1;
                }
            } else {
                
                tempArray[j] = -1;
                System.err.println("Setting j at " + j + ", to -1");
            }
        }

        System.err.println("Contents of tempArray before getting values");
        printArray(tempArray);
        // Now we get values and replace the values in tempArray
        for (int j = 0; j < tempArray.length; j++) {
            if (tempArray[j] != -1) {
                tempArray[j] = numbers[(int)tempArray[j]];
            } else {
                tempArray[j] = 0;
            }
        }

        System.err.println("Contents of tempARray before sending to mul");
        printArray(tempArray);

        // Now when we have a perfect 4 sized array with valid number, we send this to
        // find the multipler

        long multiplier = findMultiplier(tempArray);

        return multiplier;
    }

    private static boolean containsIn(long number, long [] array){
        for (long i : array) {
            if(i == number) {
                System.err.println(i + " contains in the array of size " + array.length);
                return true;
            }
        }
        return false;
    }

    private static long findMultiplier(long[] numbers) {
        // Find the multipliers. Since each multiplier can at most be 999, we can have
        // some cases of these 4 numbers:
        // X * 100 + Y + Z
        // - X * 100 + Y/Z
        // - - X * 100
        // - - Y + Z
        // - - - Y/Z
        // So what we could do is to search for the "100" and then just add the rest
        // lets make a switch where each case is the location of the "100" with respect
        // to these 4 numbers.
        // Ex: fem hundra sju miljoner: index of 100 = 1, index of miljoner = 3,
        // distance till previous = 3
        int index_of_100 = (int)indexOf(100, numbers);

        long multiplier = 0;
        //if (index_of_100 != -1) {
            switch (index_of_100) {
                case 3:
                    // - - X * 100

                    multiplier = numbers[2] * 100;
                    System.err.println("Case 1:" + multiplier);
                    break;
                case 2:
                    // - X * 100 + Y/Z
                    multiplier = numbers[1] * 100 + numbers[3];
                    System.err.println("Case 2:" + multiplier);
                    break;

                case 1:
                    // X * 100 + Y + Z
                    multiplier = numbers[0] * 100 + numbers[2]
                            + numbers[3];
                    System.err.println("Case 3:" + multiplier);
                    break;

                default:
                    // - - Y + Z
                    // - - - Y/Z
                    System.err.println("Should not come here????");
                    System.err.println("or maybe...");
                    multiplier = numbers[3] +  numbers[2];
                    break;
            }

        System.err.println("Done, final multiplier: " + multiplier);
        return multiplier;
    }

    private static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.err.print(array[i] + " ");
        }
        System.err.println();
    }

    private static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.err.print(array[i] + " ");
        }
        System.err.println();
    }

    // For integers
    private static long indexOf(long string, long[] array) {
        // This functions takes a list and a number, if this number exists in this array
        // we return the location of this number in the array, else -1

        for (int i = 0; i < array.length; i++) {
            if (string == (array[i])) {
                return i;
            }
        }
        return -1;
    }

    private static int indexOf(int string, int[] array) {
        // This functions takes a list and a number, if this number exists in this array
        // we return the location of this number in the array, else -1

        for (int i = 0; i < array.length; i++) {
            if (string == (array[i])) {
                return i;
            }
        }
        return -1;
    }

    static long parser(String text) {
        switch (text) {
            // special
            case "en":
                return 1;

            case "ett":
                return 1;

            case "två":
                return 2;

            case "tre":
                return 3;

            case "fyra":
                return 4;

            case "fem":
                return 5;

            case "sex":
                return 6;

            case "sju":
                return 7;

            case "åtta":
                return 8;

            case "nio":
                return 9;
            case "tio":
                return 10;
            case "elva":
                return 11;
            case "tolv":
                return 12;
            case "tretton":
                return 13;
            case "fjorton":
                return 14;
            case "femton":
                return 15;
            case "sexton":
                return 16;
            case "sjutton":
                return 17;
            case "arton":
                return 18;
            case "nitton":
                return 19;

            // Tens
            case "tjugio":
                return 20;

            case "trettio":
                return 30;

            case "fyrtio":
                return 40;

            case "femtio":
                return 50;

            case "sextio":
                return 60;

            case "sjuttio":
                return 70;

            case "åttio":
                return 80;

            case "nittio":
                return 90;

            // Larger numbers
            case "hundra":
                return 100;

            case "tusen":
                return 1000;

            case "miljon":
                return 1000000;

            case "miljoner":
                return 1000000;

            case "miljard":
                return 1000000000;

            case "miljarder":
                return 1000000000;

            default:
                return -10000;

        }

    }

}
