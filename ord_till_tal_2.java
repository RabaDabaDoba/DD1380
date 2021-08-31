import java.util.Scanner;

public class ord_till_tal_2 {
    public static void main(String[] args) {

        System.out.println(ordTillTal_2());

    }

    private static int ordTillTal() {
        final Scanner in = new Scanner(System.in, "utf-8");

        String number = in.nextLine();
        String[] numbers_s = number.split(" ");

        int index_of_bil = indexOf("miljard", numbers_s, 0, numbers_s.length);
        if (indexOf("miljarder", numbers_s, 0, numbers_s.length) > index_of_bil)
            index_of_bil = indexOf("miljarder", numbers_s, 0, numbers_s.length);
        int index_of_mil = indexOf("miljon", numbers_s, 0, numbers_s.length);
        if (indexOf("miljoner", numbers_s, 0, numbers_s.length) > index_of_mil)
            index_of_mil = indexOf("miljoner", numbers_s, 0, numbers_s.length);
        int index_of_thousand = indexOf("tusen", numbers_s, 0, numbers_s.length);
        // Convert array to int

        int[] numbers = new int[numbers_s.length];
        for (int i = 0; i < numbers_s.length; i++) {
            numbers[i] = parser(numbers_s[i]);

        }

        printArray(numbers_s);
        printArray(numbers);
        // The list, index of numbers, possible indicies to the left
        // int temp = findMultiplier(numbers, numbers.length, numbers.length,
        // numbers_s);
        // System.err.println("temp " + temp);

        int total = 0;
        /*
         * int temp_stop; if(index_of_thousand != -1){ temp_stop = index_of_thousand+1;
         * }else{ temp_stop = numbers.length; } int rest = findMultiplier(numbers,
         * numbers.length,temp_stop , numbers_s);
         * 
         * total += rest; //////
         */

        // Find the intervals for each number
        int ibs, ibe;
        // If billions exist
        if (index_of_bil != -1) {
            // Starts at beginning and has index_of_bil - 0 numbers before it.
            System.err.println("Miljarder finns");
            ibs = 0;
            ibe = index_of_bil;

            total += 1000000000 * findMultiplier(numbers, ibe, ibe - ibs, numbers_s);
        } else {
            ibs = -1;
            ibe = -1;
        }

        int ims, ime;
        if (index_of_mil != -1) {
            // Exist millions
            if (index_of_bil != -1) {
                ims = ibe + 1;
            } else {
                ims = 0;
            }
            ime = index_of_mil;

            total += 1000000 * findMultiplier(numbers, ime, ime - ims, numbers_s);
        } else {
            ims = -1;
            ime = -1;
        }

        int its, ite;
        if (index_of_thousand != -1) {
            // Exist thousands
            // If bil exist, but not mil
            if (index_of_mil == -1 && index_of_bil != -1) {
                // Then the start of thousands is after billion
                its = ibe + 1;
            }
            // if mil exists
            else if (index_of_mil != -1) {
                // Then the start of thousands is after million
                its = ime + 1;
            } else {
                // If there are no million or billion, and this is the first
                its = 0;
            }
            ite = index_of_mil;

            total += 1000 * findMultiplier(numbers, ite, ite - its, numbers_s);
        }

        return total;

    }

    private static int ordTillTal_2() {
        final Scanner in = new Scanner(System.in, "utf-8");

        String number = in.nextLine();
        String[] numbers_s = number.split(" ");

        int[] numbers = new int[numbers_s.length];
        for (int i = 0; i < numbers_s.length; i++) {
            numbers[i] = parser(numbers_s[i]);

        }

        printArray(numbers_s);
        printArray(numbers);
        // Indicies for k,m,b units.
        int iot = -1;
        int iom = -1; 
        int iob = -1;
        int total = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            // We go backwards
            System.err.println("i = " + i);
            if (numbers[i] == 1000 || i == 0) {
                // Then we know the index of 1000, and how many number to our right we have.
                System.err.println("Case A");
                iot = i;
                if(i == 0){
                    System.err.println("Case A_1");
                    total += findMultiplier(numbers, numbers.length, numbers.length - 1, numbers_s);
                }
                else{
                    System.err.println("Case A_2");

                    if(iot == numbers.length-1){
                        //We have nothing since this is the last entry
                    }else{
                        total += findMultiplier(numbers, numbers.length, iot - 1, numbers_s);
                    }
                    
                }
                
            } else if (numbers[i] == 1000000) {
                System.err.println("Case B");
                // Then we know the index of 1000000, and how many number to our right we have.
                iom = i;
                
                if(iot != -1){
                    System.err.println("Case B_1");
                    total += findMultiplier(numbers, iot, iom - 1, numbers_s);
                }else{
                    System.err.println("Case B_2");
                    total += findMultiplier(numbers, numbers.length, numbers.length - 1, numbers_s);
                }
            }
        }

        return total;
    }

    private static int findMultiplier(int[] numbers, int index_of_number, int distance_till_previous_number,
            String[] numbers_s) {
        System.err.println("Finding multiplier with index: " + index_of_number + ", distance: " + distance_till_previous_number);
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
        int index_of_100 = indexOf("hundra", numbers_s, index_of_number - distance_till_previous_number,
                index_of_number);

        int multiplier = 0;

        if (index_of_100 != -1) {
            switch (index_of_number - index_of_100) {
                case 1:
                    // - - X * 100

                    multiplier = numbers[index_of_100 - 1] * 100;
                    System.err.println("Case 1:" + multiplier);
                    break;
                case 2:
                    // - X * 100 + Y/Z
                    multiplier = numbers[index_of_100 - 1] * 100 + numbers[index_of_100 + 1];
                    System.err.println("Case 2:" + multiplier);
                    break;

                case 3:
                    // X * 100 + Y + Z
                    multiplier = numbers[index_of_100 - 1] * 100 + numbers[index_of_100 + 1]
                            + numbers[index_of_100 + 2];
                    System.err.println("Case 3:" + multiplier);
                    break;

                default:
                    // - - Y + Z
                    // - - - Y/Z
                    System.err.println("Should not come here????");
                    break;
            }
        } else {
            // Case 4

            if (index_of_number - 2 >= 0 && numbers[index_of_number] > numbers[index_of_number - 2]) {
                multiplier += numbers[index_of_number - 2];
                System.err.println("Case 4.1:" + multiplier);
            }
            if (index_of_number - 1 >= 0 /* && numbers[index_of_number] > numbers[index_of_number-1] */) {
                // System.err.println(index_of_number);
                multiplier += numbers[index_of_number - 1];
                System.err.println("Case 4.2:" + multiplier);
            }
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

    private static int indexOf(String string, String[] array, int start, int end) {
        // This functions takes a list and a number, if this number exists in this array
        // we return the location of this number in the array, else -1

        for (int i = start; i < end; i++) {
            if (string.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    static int parser(String text) {
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
