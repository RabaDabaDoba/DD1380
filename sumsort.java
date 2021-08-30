import java.util.Arrays;
import java.util.Scanner;

public class sumsort {

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);

        final int n = Integer.parseInt(in.nextLine());

        //The following line will have n integers that we have to read in.

        //An inefficient way is to load all numbers into an array, use some sorting algorithm. And then cut the array at the middle, sum of the upper side of the array.
        int [] array = new int[n];
        for (int i = 0; i < n; i++) {
            //Now we can add all the integers to the list
            int currentInt = Integer.parseInt(in.next());
            array[i] = currentInt;
        }

        
        for (int i = 0; i < n; i++) {
            System.err.print(array[i] + " ");
        }
        System.err.println();
        //Now when its filled we can sort it using some sorting algorithm
        Arrays.sort(array);
        //Now we sum the upper half
        for (int i = 0; i < n; i++) {
            System.err.print(array[i] + " ");
        }

        System.err.println();

        int sum = 0;
        
        if(n%2 == 0){
            //Its even
            for (int i = (n/2)-1; i < n; i++) {
                sum += array[i];
            }
        }else{
            for (int i = ((n+1)/2)-1; i < n; i++) {
                sum += array[i];
            }
        }

        System.out.println(sum);
    }
    
}