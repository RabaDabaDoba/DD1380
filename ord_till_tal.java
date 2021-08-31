import java.util.Scanner;

public class ord_till_tal {
    public static void main(String[] args) {

        System.out.println(ordTillTal());

    }

    private static int ordTillTal() {
        final Scanner in = new Scanner(System.in, "utf-8");

        // The largest number we will have is 10^9
        // Each number comes in a pair, quantity and number
        // Hence we read the text in tuples, which we then decode

        /*
         * int sum = 0;
         * 
         * while (in.hasNext()) { String quantity_s = in.next(); int quantity_i =
         * parser(quantity_s); String number_s; int number_i; // Sometimes it's only
         * one, like "sju" if (in.hasNext()) { number_s = in.next(); number_i =
         * parser(quantity_s); } else { // number_s = quantity_s; number_i = quantity_i;
         * quantity_i = 99; }
         * 
         * if (quantity_i < 10) { sum += quantity_i * number_i; } else { int temp =
         * quantity_i + number_i;
         * 
         * // Special case if (in.hasNext()) { int nextNum = parser(in.next()); if
         * (nextNum > 10) { sum += temp * nextNum; } else { sum += temp; } } }
         * 
         * }
         */

        // As seen in the analasys below we can see how we have to add the numbers. The
        // larger number can have multipliers up to 999.
        // Therefore we can find these larger numbers, and then add up the multipliers.

        /*
         * String number = in.nextLine(); String [] numbers = number.split(" ");
         * 
         * int sum = 0; //Now we have an array with all the number, no we serach for the
         * given numbers int milj_index = listContains("1000000000", numbers);
         * if(milj_index > 0){ //Since we know how the multipliers works, we can
         * hardcode the structure. int first, second, third, fourth; if(milj_index-4 ==
         * 0){ first = Integer.parseInt(numbers[milj_index-4]); }else{ first = 0; }
         * 
         * if(milj_index-3 >= 0){ second = Integer.parseInt(numbers[milj_index-3]);
         * }else{ second = 0; }
         * 
         * if(milj_index-2 >= 0){ third = Integer.parseInt(numbers[milj_index-2]);
         * }else{ third = 0; }
         * 
         * if(milj_index-1 >= 0){ fourth = Integer.parseInt(numbers[milj_index-1]);
         * }else{ fourth = 0; }
         * 
         * int multiplier = first * second + third + fourth; sum += multiplier *
         * 1000000000; }
         * 
         * int mil_index = listContains("1000000", numbers); if(mil_index > 0){
         * if(milj_index > 0){ //If there exists a milj number }else{ //The largest
         * power will be 10^6
         * 
         * } }
         * 
         * 
         */

        String number = in.nextLine();
        String[] numbers = number.split(" ");
        // Since we know what the largest value can be we can hardcode the structure of
        // the answer since it will have a fixed format.
        // A multiplier can be at most 999 based of 4 numbers in the structure
        // (quantity_of_hundreds * 100 + tens + singulars). The answer will have the
        // following structure:
        // Multiplier_1 * billion + Multiplier_2 * million + Multiplier_3 * thousand +
        // Multiplier_4
        // So all we do is calculate these values and add them to the formula.

        // printArray(numbers);

        // First we check which large number we have.
        int bil_index = listContains("miljard", numbers);
        int mil_index = listContains("miljon", numbers);
        int thosand_index = listContains("tusen", numbers);

        // Here we have to calculate how many number there are inbetween each large
        // number, so we know how many numbers to read from the list.
        // Ex if we have one billion twenty five millions, we read 1 number for the
        // billions and two for the millions. Thanks to the indicies we got we can
        // calculate this.

        int bil_mul;
        if (bil_index == 0) {
            bil_mul = 0;
            System.err.println("No bil");
        } else {

            bil_mul = findMultiplier(numbers, bil_index, bil_index);
            System.err.println("bil mul = " + bil_mul);
        }

        int mil_mul;
        if (mil_index == 0) {
            mil_mul = 0;
            System.err.println("No mil");
        } else {
            mil_mul = findMultiplier(numbers, mil_index, mil_index - bil_index);
            System.err.println("mil mul = " + mil_mul);
        }

        int thosand_mul;
        if (thosand_index == 0) {
            thosand_mul = 0;
            System.err.println("No thousand");
        } else {
            thosand_mul = findMultiplier(numbers, thosand_index, thosand_index - mil_index);
            System.err.println("thousand mul = " + thosand_mul);
        }

        int last;
        if (numbers.length == 0 ||( thosand_index != 0 && thosand_index+1 == numbers.length)) {
            System.err.println("No last");
            last = 0;
        } else {
            if (thosand_index != 0) {
                last = findMultiplier(numbers, numbers.length, thosand_index - mil_index);
                System.err.println("thosand index != ");
            } else {
                last = findMultiplier(numbers, numbers.length, 4);
            }
            System.err.println("last mul = " + last);
        }
        System.err.println(bil_mul + " * b + " + mil_mul + " * m + " + thosand_mul + " * k + " + last);
        int finalSum = bil_mul * 1000000000 + mil_mul * 1000000 + thosand_mul * 1000 + last;
        return finalSum;
    }

    /*
     * åtta hundra åttio åtta = 888
     * 
     * 8*100 + 88
     * 
     * åtta miljoner åtta hundra åttio åtta tusen åtta = 8888008
     * 
     * 8 * 1000000 + (8 * 100 + 80+8) * 1000 + 8
     * 
     * nio hundra nittio sju miljarder en miljon fem hundra fyrtio tre tusen ett
     * hundra arton = 997001543118
     * 
     * (9 * 100 + 90 + 7) * 1000000000 + 1 * 1000000 + (5 * 100 + 40 +3) * 1000 + 1
     * *100 + 18
     * 
     */

    private static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.err.print(array[i] + " ");
        }
        System.err.println();
    }

    private static int findMultiplier(String[] numbers, int index, int distance) {
        // Given taht we have one billion and seven millions, and we want to calculate
        // the multipler for the million, we should only read the index before the
        // billion. I.e read index as long as array[i] < array[large number].
        int multiplier = 0;
        printArray(numbers);
        System.err.println("Finding multiplier with index: " + index + ", distance: " + distance);
        if (index == numbers.length - 1)
            //index++;
        if (index >= 0) {
            // Since we know how the multipliers works, we can hardcode the structure.
            int first, second, third, fourth;
            if (index - 4 >= 0 && distance >= 4) {
                first = parser(numbers[index - 4]);

            } else {
                first = 0;
            }
            System.err.println(first);

            if (index - 3 >= 0 && distance >= 3) {
                second = parser(numbers[index - 3]);
            } else {
                second = 0;
            }
            System.err.println(second);

            if (index - 2 >= 0 && distance >= 2) {
                third = parser(numbers[index - 2]);

                // Special
                if (third == 100) {
                    first = second;
                    second = third;
                    third = 0;
                }

            } else {
                third = 0;
            }
            System.err.println(third);

            //System.err.println("Calculating 4th with index: " + index + ", and distance: " + distance);
            if ((index - 1 >= 0 && distance >= 1 )|| index == 0) {
                System.err.println("PLATS:" + (index-1) + ", och värde: " + numbers[index-1]);
                fourth = parser(numbers[index-1]);
                //System.err.println(fourth + "askdj");
                System.err.println("print list");
                printArray(numbers);
                //Special
                if(fourth == 100){
                    first = third;
                    second = fourth;
                    third = 0;
                    fourth = 0;
                }

            } else {
                fourth = 0;
            }
            System.err.println(fourth);
            multiplier = first * second + third + fourth;
        }

        System.err.println("multi; " + multiplier);
        return multiplier;
    }

    private static int listContains(String string, String[] array) {
        // This functions takes a list and a number, if this number exists in this array
        // we return the location of this number in the array, else -1

        for (int i = 0; i < array.length; i++) {
            if (string.equals(array[i])) {
                return i;
            }
        }
        return 0;
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
                return -1;

        }

    }

}
